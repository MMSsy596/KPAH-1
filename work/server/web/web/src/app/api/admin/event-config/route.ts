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

const LUCKY_BAG_FIELDS = new Set([
  'drop_rate_percent', 'weight_luong', 'weight_luong_lock', 'weight_xu', 'weight_hp', 'weight_mp',
  'amount_luong_min', 'amount_luong_lock_min', 'amount_xu_min', 'amount_hp_min', 'amount_mp_min',
  'amount_luong_max', 'amount_luong_lock_max', 'amount_xu_max', 'amount_hp_max', 'amount_mp_max',
  'max_open_per_day'
]);

export async function POST(request: Request) {
  const session = await getCurrentAdminSession();
  const admin = session ? await getAdminUserById(session.adminId) : null;
  if (!session || !admin) {
    return NextResponse.redirect(buildAdminUrl(request, ADMIN_LOGIN_PATH));
  }

  const formData = await request.formData();
  const action = String(formData.get('action') ?? '');
  const returnTo = String(formData.get('returnTo') ?? '');
  const form: Record<string, string> = {};

  for (const [key, value] of formData.entries()) {
    if ((action === 'events' && key.startsWith('event_')) || (action === 'lucky_bag' && LUCKY_BAG_FIELDS.has(key))) {
      form[key] = String(value);
    }
  }

  const endpoint = action === 'events' ? '/api/config/events/apply'
    : action === 'lucky_bag' ? '/api/config/lucky-bag/apply' : '';
  if (!endpoint) {
    return buildAdminRedirect(request, returnTo, false, 'Thao tác cấu hình sự kiện không hợp lệ.');
  }

  const result = await runLocalAdminCommand({
    admin,
    requestIp: getClientIpFromHeaders(request.headers),
    actionType: action === 'events' ? 'update_event_settings' : 'update_lucky_bag_settings',
    endpoint,
    form,
    targetType: 'server_config',
    targetValue: action
  });
  return buildAdminRedirect(request, returnTo, result.ok, result.message);
}
