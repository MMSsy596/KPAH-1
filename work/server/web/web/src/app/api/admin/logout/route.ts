import { NextResponse } from 'next/server';

import { ADMIN_LOGIN_PATH, buildAdminUrl, clearAdminSessionCookie } from '@/lib/admin';

export const runtime = 'nodejs';

export async function POST(request: Request) {
  const response = NextResponse.redirect(buildAdminUrl(request, ADMIN_LOGIN_PATH));
  clearAdminSessionCookie(response);
  return response;
}
