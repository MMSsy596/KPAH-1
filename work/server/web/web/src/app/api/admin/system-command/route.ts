import { NextResponse } from 'next/server';

import {
  ADMIN_LOGIN_PATH,
  buildAdminUrl,
  buildAdminRedirect,
  changeAdminPassword,
  getAdminUserById,
  getClientIpFromHeaders,
  getCurrentAdminSession,
  runLocalAdminCommand
} from '@/lib/admin';

export const runtime = 'nodejs';

export async function POST(request: Request) {
  const session = await getCurrentAdminSession();
  const adminUser = session ? await getAdminUserById(session.adminId) : null;

  if (!session || !adminUser) {
    return NextResponse.redirect(buildAdminUrl(request, ADMIN_LOGIN_PATH));
  }

  const formData = await request.formData();
  const action = String(formData.get('action') ?? '');
  const returnTo = String(formData.get('returnTo') ?? '');
  const requestIp = getClientIpFromHeaders(request.headers);

  let result: { ok: boolean; message: string };

  if (action === 'announce_live') {
    const message = String(formData.get('message') ?? '');
    const type = String(formData.get('type') ?? 'top');
    result = await runLocalAdminCommand({
      admin: adminUser,
      requestIp,
      actionType: 'announce_live',
      endpoint: '/api/command/announce',
      form: { message, type },
      targetType: 'server',
      targetValue: type,
      detailText: message
    });
  } else if (action === 'schedule_maintenance') {
    const minutes = String(formData.get('minutes') ?? '5');
    result = await runLocalAdminCommand({
      admin: adminUser,
      requestIp,
      actionType: 'schedule_maintenance',
      endpoint: '/api/command/maintenance/start',
      form: { minutes },
      targetType: 'server',
      targetValue: 'maintenance',
      detailText: `${minutes} phút`
    });
  } else if (action === 'cancel_maintenance') {
    result = await runLocalAdminCommand({
      admin: adminUser,
      requestIp,
      actionType: 'cancel_maintenance',
      endpoint: '/api/command/maintenance/cancel',
      form: {},
      targetType: 'server',
      targetValue: 'maintenance'
    });
  } else if (action === 'change_admin_password') {
    result = await changeAdminPassword({
      adminId: adminUser.id,
      currentPassword: String(formData.get('currentPassword') ?? ''),
      newPassword: String(formData.get('newPassword') ?? ''),
      requestIp
    });
  } else {
    result = { ok: false, message: 'Thao tác hệ thống không hợp lệ.' };
  }

  return buildAdminRedirect(request, returnTo, result.ok, result.message);
}
