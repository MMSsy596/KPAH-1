import { NextResponse } from 'next/server';

import {
  ADMIN_LOGIN_PATH,
  buildAdminRedirect,
  buildAdminUrl,
  getAdminUserById,
  getClientIpFromHeaders,
  getCurrentAdminSession,
  logAdminAction
} from '@/lib/admin';
import {
  createGiftCode,
  deleteUnusedGiftCode,
  setGiftCodeActive,
  updateGiftCode,
  type GiftCodeInput
} from '@/lib/gift-codes';

export const runtime = 'nodejs';

function toInteger(value: FormDataEntryValue | null, defaultValue = 0): number {
  const parsed = Number(value ?? defaultValue);
  return Number.isInteger(parsed) ? parsed : Number.NaN;
}

function readGiftCodeInput(formData: FormData): GiftCodeInput {
  return {
    code: String(formData.get('code') ?? ''),
    xu: toInteger(formData.get('xu')),
    luong: toInteger(formData.get('luong')),
    luongLock: toInteger(formData.get('luongLock')),
    item: String(formData.get('item') ?? ''),
    limitUse: toInteger(formData.get('limitUse'), -1),
    startsAt: String(formData.get('startsAt') ?? ''),
    expiresAt: String(formData.get('expiresAt') ?? '')
  };
}

export async function POST(request: Request) {
  const session = await getCurrentAdminSession();
  const admin = session ? await getAdminUserById(session.adminId) : null;
  if (!session || !admin) {
    return NextResponse.redirect(buildAdminUrl(request, ADMIN_LOGIN_PATH));
  }

  const formData = await request.formData();
  const action = String(formData.get('action') ?? '');
  const returnTo = String(formData.get('returnTo') ?? '');
  const id = toInteger(formData.get('id'));
  const requestIp = getClientIpFromHeaders(request.headers);
  let code = String(formData.get('code') ?? '').trim().toLowerCase();

  try {
    if (action === 'create') {
      const created = await createGiftCode(readGiftCodeInput(formData));
      code = created.code;
    } else if (action === 'update') {
      code = await updateGiftCode(id, readGiftCodeInput(formData));
    } else if (action === 'activate' || action === 'deactivate') {
      code = await setGiftCodeActive(id, action === 'activate');
    } else if (action === 'delete') {
      code = await deleteUnusedGiftCode(id);
    } else {
      throw new Error('Thao tác gift code không hợp lệ.');
    }

    const message = action === 'create' ? 'Đã tạo gift code.'
      : action === 'update' ? 'Đã cập nhật gift code.'
        : action === 'activate' ? 'Đã bật gift code.'
          : action === 'deactivate' ? 'Đã tắt gift code.' : 'Đã xóa gift code chưa sử dụng.';
    await logAdminAction({
      adminUserId: admin.id,
      adminUsername: admin.username,
      actionType: `${action}_gift_code`,
      targetType: 'gift_code',
      targetValue: code,
      requestIp,
      success: true,
      resultMessage: message
    });
    return buildAdminRedirect(request, returnTo, true, message);
  } catch (error) {
    const rawMessage = error instanceof Error ? error.message : 'Không thể xử lý gift code.';
    const message = rawMessage.includes('Duplicate') ? 'Gift code này đã tồn tại.' : rawMessage;
    await logAdminAction({
      adminUserId: admin.id,
      adminUsername: admin.username,
      actionType: `${action || 'unknown'}_gift_code`,
      targetType: 'gift_code',
      targetValue: code,
      requestIp,
      success: false,
      resultMessage: message
    });
    return buildAdminRedirect(request, returnTo, false, message);
  }
}
