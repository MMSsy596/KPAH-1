import { redirect } from 'next/navigation';

import { ADMIN_DASHBOARD_PATH, ADMIN_LOGIN_PATH, getCurrentAdminSession } from '@/lib/admin';

export default async function HiddenAdminIndexPage() {
  const session = await getCurrentAdminSession();
  redirect(session ? ADMIN_DASHBOARD_PATH : ADMIN_LOGIN_PATH);
}
