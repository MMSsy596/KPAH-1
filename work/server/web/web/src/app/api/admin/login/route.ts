import { NextResponse } from 'next/server';

import {
  ADMIN_DASHBOARD_PATH,
  ADMIN_LOGIN_PATH,
  attachAdminSessionCookie,
  authenticateAdmin,
  buildAdminUrl,
  buildAdminRedirect,
  getClientIpFromHeaders,
  logAdminAction
} from '@/lib/admin';

export const runtime = 'nodejs';

export async function POST(request: Request) {
  const formData = await request.formData();
  const username = String(formData.get('username') ?? '');
  const password = String(formData.get('password') ?? '');
  const requestIp = getClientIpFromHeaders(request.headers);

  const result = await authenticateAdmin(username, password, requestIp);

  if (!result.ok) {
    return buildAdminRedirect(request, ADMIN_LOGIN_PATH, false, result.message);
  }

  const now = Date.now();
  const response = NextResponse.redirect(buildAdminUrl(request, ADMIN_DASHBOARD_PATH));
  attachAdminSessionCookie(response, {
    adminId: result.user.id,
    username: result.user.username,
    displayName: result.user.displayName,
    role: result.user.role,
    issuedAt: now,
    expiresAt: now + 1000 * 60 * 60 * 12
  });

  await logAdminAction({
    adminUserId: result.user.id,
    adminUsername: result.user.username,
    actionType: 'admin_login',
    targetType: 'admin_account',
    targetValue: result.user.username,
    requestIp,
    success: true,
    resultMessage: 'Đăng nhập quản trị thành công.'
  });

  return response;
}
