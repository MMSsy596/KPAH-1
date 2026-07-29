import type { Metadata } from 'next';
import { redirect } from 'next/navigation';

import LoginForm from '@/components/login/LoginForm';
import { getCurrentSession } from '@/lib/auth';

export const metadata: Metadata = {
  title: 'Đăng Nhập - KPAH',
  description: 'Đăng nhập trung tâm tài khoản KPAH để xem trạng thái nhân vật, tài sản và thông báo máy chủ.'
};

export default async function LoginPage() {
  const session = await getCurrentSession();

  if (session) {
    redirect('/tai-khoan');
  }

  return <LoginForm />;
}
