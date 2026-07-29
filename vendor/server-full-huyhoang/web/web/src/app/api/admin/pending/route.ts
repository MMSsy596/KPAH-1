import { NextResponse } from 'next/server';

import {
  ADMIN_LOGIN_PATH,
  buildAdminUrl,
  buildAdminRedirect,
  getAdminUserById,
  getClientIpFromHeaders,
  getCurrentAdminSession,
  reviewPendingRegistration
} from '@/lib/admin';

export const runtime = 'nodejs';

export async function POST(request: Request) {
  const session = await getCurrentAdminSession();
  const adminUser = session ? await getAdminUserById(session.adminId) : null;

  if (!session || !adminUser) {
    return NextResponse.redirect(buildAdminUrl(request, ADMIN_LOGIN_PATH));
  }

  const formData = await request.formData();
  const id = Number(formData.get('id') ?? 0);
  const decision = String(formData.get('decision') ?? '') === 'approve' ? 'approve' : 'reject';
  const note = String(formData.get('note') ?? '');
  const returnTo = String(formData.get('returnTo') ?? '');
  const result = await reviewPendingRegistration({
    id,
    decision,
    note,
    admin: adminUser,
    requestIp: getClientIpFromHeaders(request.headers)
  });

  return buildAdminRedirect(request, returnTo, result.ok, result.message);
}
