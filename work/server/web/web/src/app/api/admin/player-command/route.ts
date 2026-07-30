import { NextResponse } from 'next/server';

import {
  ADMIN_LOGIN_PATH,
  buildAdminUrl,
  buildAdminRedirect,
  getAdminUserById,
  getClientIpFromHeaders,
  getCurrentAdminSession,
  runLocalAdminCommand,
  setAccountLocked
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

  if (action === 'lock_account' || action === 'unlock_account') {
    result = await setAccountLocked({
      username: String(formData.get('username') ?? ''),
      locked: action === 'lock_account',
      admin: adminUser,
      requestIp
    });
  } else if (action === 'reset_password') {
    const username = String(formData.get('username') ?? '');
    const newPassword = String(formData.get('newPassword') ?? '');

    result = await runLocalAdminCommand({
      admin: adminUser,
      requestIp,
      actionType: 'reset_player_password',
      endpoint: '/api/command/account/change-password',
      form: {
        username,
        password: newPassword
      },
      targetType: 'account',
      targetValue: username
    });
  } else if (action === 'kick_character') {
    const charname = String(formData.get('charname') ?? '');
    result = await runLocalAdminCommand({
      admin: adminUser,
      requestIp,
      actionType: 'kick_character',
      endpoint: '/api/command/kick',
      form: { playerName: charname },
      targetType: 'character',
      targetValue: charname
    });
  } else if (action === 'ban_character' || action === 'unban_character') {
    const charname = String(formData.get('charname') ?? '');
    result = await runLocalAdminCommand({
      admin: adminUser,
      requestIp,
      actionType: action,
      endpoint: action === 'ban_character' ? '/api/command/account/ban' : '/api/command/account/unban',
      form: { playerName: charname },
      targetType: 'character',
      targetValue: charname
    });
  } else if (action === 'grant_resources') {
    const charname = String(formData.get('charname') ?? '');
    result = await runLocalAdminCommand({
      admin: adminUser,
      requestIp,
      actionType: 'grant_resources',
      endpoint: '/api/command/grant-player',
      form: {
        playerName: charname,
        xu: String(formData.get('xu') ?? '0'),
        luong: String(formData.get('luong') ?? '0'),
        luongLock: String(formData.get('luongLock') ?? '0'),
        materialId: String(formData.get('materialId') ?? '0'),
        materialQty: String(formData.get('materialQty') ?? '0')
      },
      targetType: 'character',
      targetValue: charname
    });
  } else if (action === 'buff_character') {
    const charname = String(formData.get('charname') ?? '');
    result = await runLocalAdminCommand({
      admin: adminUser,
      requestIp,
      actionType: 'buff_character',
      endpoint: '/api/command/player/buff-named',
      form: {
        playerName: charname,
        targetLevel: String(formData.get('targetLevel') ?? '1'),
        xu: String(formData.get('xu') ?? '0'),
        luong: String(formData.get('luong') ?? '0'),
        luongLock: String(formData.get('luongLock') ?? '0'),
        skillPoint: String(formData.get('skillPoint') ?? '0'),
        basePoint: String(formData.get('basePoint') ?? '0')
      },
      targetType: 'character',
      targetValue: charname
    });
  } else {
    result = { ok: false, message: 'Thao tác người chơi không hợp lệ.' };
  }

  return buildAdminRedirect(request, returnTo, result.ok, result.message);
}
