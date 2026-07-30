import { NextResponse } from 'next/server';

import {
  ADMIN_LOGIN_PATH,
  buildAdminRedirect,
  buildAdminUrl,
  createStoredHomePost,
  deleteStoredHomePost,
  getAdminUserById,
  getClientIpFromHeaders,
  getCurrentAdminSession
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

  const result = action === 'delete'
    ? await deleteStoredHomePost({
      id: Number(formData.get('id') ?? 0),
      admin: adminUser,
      requestIp
    })
    : await createStoredHomePost({
      category: String(formData.get('category') ?? ''),
      title: String(formData.get('title') ?? ''),
      content: String(formData.get('content') ?? ''),
      displayOrder: Number(formData.get('displayOrder') ?? 0),
      admin: adminUser,
      requestIp
    });

  return buildAdminRedirect(request, returnTo, result.ok, result.message);
}
