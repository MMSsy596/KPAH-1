import { redirect } from 'next/navigation';

import { ADMIN_DASHBOARD_PATH, ADMIN_ROUTE_SEGMENT, ensureAdminSchema, getCurrentAdminSession } from '@/lib/admin';

export default async function HiddenAdminLoginPage(props: any) {
  await ensureAdminSchema();

  const session = await getCurrentAdminSession();
  if (session) {
    redirect(ADMIN_DASHBOARD_PATH);
  }

  const searchParams = props?.searchParams ? await props.searchParams : {};
  const notice = typeof searchParams?.notice === 'string' ? searchParams.notice : '';
  const noticeType = typeof searchParams?.noticeType === 'string' ? searchParams.noticeType : 'error';

  return (
    <div className="admin-login-page">
      <div className="register-page__backdrop" />
      <div className="admin-login-page__shell">
        <div className="admin-login-page__card">
          <div className="register-page__intro">
            <p className="register-page__eyebrow">Khu quản trị nội bộ</p>
            <h1>Đăng nhập quản trị</h1>
            <p className="register-page__lead">
              Trang này không được hiển thị trên website công khai. Chỉ tài khoản quản trị hợp lệ mới được phép truy cập.
            </p>
            <div className="register-page__notice">
              Sau lần đăng nhập đầu tiên, hãy đổi ngay mật khẩu admin và chỉ sử dụng tại môi trường vận hành.
            </div>
          </div>

          <form className="register-form" action="/api/admin/login" method="post">
            <input type="hidden" name="returnTo" value={`/${ADMIN_ROUTE_SEGMENT}/login`} />

            <div className="login-form__stack">
              <label className="register-form__field">
                <span>Tên đăng nhập admin</span>
                <input type="text" name="username" placeholder="Nhập tên đăng nhập quản trị" autoComplete="username" required />
              </label>

              <label className="register-form__field">
                <span>Mật khẩu admin</span>
                <input type="password" name="password" placeholder="Nhập mật khẩu quản trị" autoComplete="current-password" required />
              </label>
            </div>

            {notice ? (
              <div className={`register-form__feedback ${noticeType === 'error' ? 'is-error' : 'is-success'}`}>{notice}</div>
            ) : null}

            <div className="register-form__actions">
              <button type="submit" className="register-form__submit">
                Vào khu quản trị
              </button>
            </div>
          </form>
        </div>
      </div>
    </div>
  );
}
