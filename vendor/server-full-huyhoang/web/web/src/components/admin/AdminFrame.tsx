import Link from 'next/link';
import type { ReactNode } from 'react';

import {
  ADMIN_BASE_PATH,
  ADMIN_DASHBOARD_PATH,
  type AdminUser
} from '@/lib/admin';

type AdminFrameProps = {
  user: AdminUser;
  title: string;
  description: string;
  currentPath: string;
  notice?: string;
  noticeType?: string;
  children: ReactNode;
};

const navItems = [
  { href: `${ADMIN_DASHBOARD_PATH}`, label: 'Tổng quan' },
  { href: `${ADMIN_BASE_PATH}/dang-ky`, label: 'Duyệt đăng ký' },
  { href: `${ADMIN_BASE_PATH}/bai-viet`, label: 'Bài trang chủ' },
  { href: `${ADMIN_BASE_PATH}/thong-bao`, label: 'Thông báo' },
  { href: `${ADMIN_BASE_PATH}/nguoi-choi`, label: 'Người chơi' },
  { href: `${ADMIN_BASE_PATH}/nhat-ky`, label: 'Nhật ký' }
];

export default function AdminFrame({
  user,
  title,
  description,
  currentPath,
  notice,
  noticeType,
  children
}: AdminFrameProps) {
  return (
    <div className="admin-page">
      <div className="register-page__backdrop" />
      <div className="admin-page__shell">
        <aside className="admin-sidebar">
          <Link href={ADMIN_DASHBOARD_PATH} className="admin-sidebar__brand">
            <img src="/logo/logo.png" alt="KPAH Admin" />
            <div>
              <strong>KPAH Admin</strong>
              <span>Điều hành nội bộ</span>
            </div>
          </Link>

          <div className="admin-sidebar__user">
            <span>Đăng nhập bởi</span>
            <strong>{user.displayName}</strong>
            <small>{user.username}</small>
          </div>

          <nav className="admin-sidebar__nav">
            {navItems.map((item) => (
              <Link
                key={item.href}
                href={item.href}
                className={`admin-sidebar__link ${currentPath === item.href ? 'is-active' : ''}`}
              >
                {item.label}
              </Link>
            ))}
          </nav>

          <div className="admin-sidebar__actions">
            <form action="/api/admin/logout" method="post">
              <button type="submit" className="admin-sidebar__button">
                Đăng xuất
              </button>
            </form>
          </div>
        </aside>

        <main className="admin-main">
          <header className="admin-header">
            <div>
              <p className="register-page__eyebrow">Trang quản trị ẩn</p>
              <h1>{title}</h1>
              <p className="register-page__lead">{description}</p>
            </div>
          </header>

          {notice ? (
            <div className={`admin-notice ${noticeType === 'error' ? 'is-error' : 'is-success'}`}>
              {notice}
            </div>
          ) : null}

          <div className="admin-content">{children}</div>
        </main>
      </div>
    </div>
  );
}
