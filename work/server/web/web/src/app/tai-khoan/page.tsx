import type { Metadata } from 'next';
import { redirect } from 'next/navigation';

import AccountPortal from '@/components/account/AccountPortal';
import { getCurrentSession } from '@/lib/auth';
import { getPlayerPortalData } from '@/lib/player-portal';

export const metadata: Metadata = {
  title: 'Tài Khoản - KPAH',
  description: 'Theo dõi trạng thái nhân vật, tài sản và thông báo mới nhất từ máy chủ KPAH.'
};

export const dynamic = 'force-dynamic';

export default async function AccountPage() {
  const session = await getCurrentSession();

  if (!session) {
    redirect('/login');
  }

  const data = await getPlayerPortalData(session.userId, session.username);

  if (!data) {
    redirect('/login');
  }

  return <AccountPortal data={data} />;
}
