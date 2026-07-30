import React from 'react';
import type { Metadata, Viewport } from 'next';
import '../styles/index.css';

export const viewport: Viewport = {
  width: 'device-width',
  initialScale: 1
};

export const metadata: Metadata = {
  title: 'Khi Phach Anh Hung - KPAH',
  description: 'Khi Phach Anh Hung. Tai game mien phi, nap the online, xem bang xep hang va nhan thong bao moi nhat.',
  keywords: 'khi phach anh hung, kpah, tai game kpah, ngude, nqsh, nam quoc son ha',
  icons: {
    icon: [{ url: '/logo/logo.png', type: 'image/png' }]
  }
};

export default function RootLayout({
  children
}: Readonly<{children: React.ReactNode;}>) {
  return (
    <html lang="vi">
      <head>
        <link rel="preconnect" href="https://fonts.googleapis.com" />
        <link href="https://fonts.googleapis.com/css2?family=Roboto&family=Dosis:wght@400;600;700&display=swap" rel="stylesheet" />
      </head>
      <body>{children}</body>
    </html>
  );
}
