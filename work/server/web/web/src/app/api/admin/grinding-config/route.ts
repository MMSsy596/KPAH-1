import { NextResponse } from 'next/server';

import {
  ADMIN_LOGIN_PATH,
  buildAdminRedirect,
  buildAdminUrl,
  getAdminUserById,
  getClientIpFromHeaders,
  getCurrentAdminSession,
  runLocalAdminCommand
} from '@/lib/admin';

export const runtime = 'nodejs';

const GRINDING_FIELDS = new Set([
  'drop_rate_percent',
  'monster_damage_percent',
  'monster_hp_percent',
  'exp_percent',
  'monster_density_percent'
]);

export async function POST(request: Request) {
  const session = await getCurrentAdminSession();
  const admin = session ? await getAdminUserById(session.adminId) : null;
  if (!session || !admin) {
    return NextResponse.redirect(buildAdminUrl(request, ADMIN_LOGIN_PATH));
  }

  const formData = await request.formData();
  const returnTo = String(formData.get('returnTo') ?? '');
  const form: Record<string, string> = {};
  for (const [key, value] of formData.entries()) {
    if (GRINDING_FIELDS.has(key)) {
      form[key] = String(value);
    }
  }

  const result = await runLocalAdminCommand({
    admin,
    requestIp: getClientIpFromHeaders(request.headers),
    actionType: 'update_grinding_settings',
    endpoint: '/api/config/grinding/apply',
    form,
    targetType: 'server_config',
    targetValue: 'grinding'
  });
  return buildAdminRedirect(request, returnTo, result.ok, result.message);
}
