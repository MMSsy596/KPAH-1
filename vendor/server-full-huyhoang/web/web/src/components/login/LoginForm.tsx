'use client';

import Link from 'next/link';
import { useRouter } from 'next/navigation';
import { useState, useTransition } from 'react';

type ApiResponse = {
  ok: boolean;
  message: string;
};

const initialForm = {
  username: '',
  password: ''
};

export default function LoginForm() {
  const router = useRouter();
  const [form, setForm] = useState(initialForm);
  const [feedback, setFeedback] = useState<ApiResponse | null>(null);
  const [isPending, startTransition] = useTransition();

  const onFieldChange = (field: keyof typeof initialForm, value: string) => {
    setForm((current) => ({ ...current, [field]: value }));
  };

  const submitLogin = async () => {
    setFeedback(null);

    const response = await fetch('/api/login', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(form)
    });

    const result = (await response.json().catch(() => ({
      ok: false,
      message: 'Không thể kết nối máy chủ. Vui lòng thử lại sau.'
    }))) as ApiResponse;

    setFeedback(result);

    if (response.ok && result.ok) {
      router.push('/tai-khoan');
      router.refresh();
    }
  };

  return (
    <div className="register-page">
      <div className="register-page__backdrop" />
      <div className="register-page__shell">
        <div className="register-page__topbar">
          <Link href="/" className="register-page__brand">
            <img src="/logo/logo.png" alt="KPAH" />
            <span>KPAH</span>
          </Link>
          <Link href="/" className="register-page__backlink">
            Về trang chủ
          </Link>
        </div>

        <div className="login-page__card">
          <div className="register-page__intro">
            <p className="register-page__eyebrow">Đăng nhập tài khoản</p>
            <h1>Trung tâm tài khoản</h1>
            <p className="register-page__lead">
              Đăng nhập để xem trạng thái nhân vật, tài sản hiện có, thứ hạng thực lực và thông báo mới từ quản trị viên.
            </p>

            <div className="register-page__notice">
              Nếu tài khoản của bạn chưa được kích hoạt, vui lòng liên hệ admin để được mở quyền đăng nhập vào máy chủ.
            </div>

            <div className="login-page__highlights">
              <div className="login-page__highlight">
                <strong>Theo dõi nhân vật</strong>
                <span>Xem nhanh trạng thái online, cấp độ và lớp nhân vật hiện tại.</span>
              </div>
              <div className="login-page__highlight">
                <strong>Tài sản và thứ hạng</strong>
                <span>Kiểm tra xu, lượng, lượng khóa và vị trí của bạn trên máy chủ.</span>
              </div>
              <div className="login-page__highlight">
                <strong>Thông báo mới</strong>
                <span>Nhận cập nhật quan trọng từ admin ngay trong khu tài khoản.</span>
              </div>
            </div>
          </div>

          <form
            className="register-form"
            onSubmit={(event) => {
              event.preventDefault();
              startTransition(() => {
                void submitLogin();
              });
            }}
          >
            <div className="login-form__stack">
              <label className="register-form__field">
                <span>Tài khoản</span>
                <input
                  type="text"
                  value={form.username}
                  onChange={(event) => onFieldChange('username', event.target.value)}
                  placeholder="Nhập tài khoản của bạn"
                  autoComplete="username"
                  maxLength={20}
                  required
                />
              </label>

              <label className="register-form__field">
                <span>Mật khẩu</span>
                <input
                  type="password"
                  value={form.password}
                  onChange={(event) => onFieldChange('password', event.target.value)}
                  placeholder="Nhập mật khẩu"
                  autoComplete="current-password"
                  maxLength={32}
                  required
                />
              </label>
            </div>

            <div className="register-form__meta">
              <p>
                Sau khi đăng nhập thành công, bạn sẽ được chuyển tới khu tài khoản để theo dõi nhân vật và các thông báo mới.
              </p>
            </div>

            {feedback ? (
              <div className={`register-form__feedback ${feedback.ok ? 'is-success' : 'is-error'}`} aria-live="polite">
                {feedback.message}
              </div>
            ) : null}

            <div className="register-form__actions">
              <button type="submit" className="register-form__submit" disabled={isPending}>
                {isPending ? 'Đang đăng nhập...' : 'Vào trung tâm tài khoản'}
              </button>
              <Link href="/register" className="register-form__ghost">
                Tạo tài khoản mới
              </Link>
            </div>
          </form>
        </div>
      </div>
    </div>
  );
}
