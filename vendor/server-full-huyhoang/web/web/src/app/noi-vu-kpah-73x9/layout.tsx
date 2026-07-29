import type { Metadata } from 'next';
import type { ReactNode } from 'react';

export const metadata: Metadata = {
  title: 'KPAH Admin',
  description: 'Trang quản trị nội bộ KPAH',
  robots: {
    index: false,
    follow: false
  }
};

export default function HiddenAdminLayout({ children }: { children: ReactNode }) {
  return children;
}
