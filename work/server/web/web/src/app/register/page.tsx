import type { Metadata } from 'next';

import RegisterForm from '@/components/register/RegisterForm';

export const metadata: Metadata = {
  title: 'Đăng Ký Tài Khoản - KPAH',
  description: 'Đăng ký tài khoản KPAH với quy trình xét duyệt để hạn chế tài khoản ảo và bảo vệ máy chủ.'
};

export default function RegisterPage() {
  return <RegisterForm />;
}
