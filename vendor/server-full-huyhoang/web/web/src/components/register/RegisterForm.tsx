'use client';

import Link from 'next/link';
import { useState, useTransition } from 'react';

type ApiResponse = {
  ok: boolean;
  message: string;
};

const initialForm = {
  username: '',
  email: '',
  phone: '',
  password: '',
  confirmPassword: '',
  website: ''
};

export default function RegisterForm() {
  const [form, setForm] = useState(initialForm);
  const [startedAt, setStartedAt] = useState(() => Date.now());
  const [feedback, setFeedback] = useState<ApiResponse | null>(null);
  const [isPending, startTransition] = useTransition();

  const onFieldChange = (field: keyof typeof initialForm, value: string) => {
    setForm((current) => ({ ...current, [field]: value }));
  };

  const submitRegistration = async () => {
    setFeedback(null);

    if (form.password !== form.confirmPassword) {
      setFeedback({ ok: false, message: 'Mật khẩu nhập lại không khớp.' });
      return;
    }

    const response = await fetch('/api/register', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({
        ...form,
        startedAt
      })
    });

    const result = (await response.json().catch(() => ({
      ok: false,
      message: 'Không thể kết nối máy chủ. Vui lòng thử lại sau.'
    }))) as ApiResponse;

    setFeedback(result);
    if (response.ok && result.ok) {
      setForm(initialForm);
      setStartedAt(Date.now());
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

        <div className="register-page__card">
          <div className="register-page__intro">
            <p className="register-page__eyebrow">Đăng ký tài khoản mới</p>
            <h1>Tạo tài khoản mới</h1>
            <p className="register-page__lead">
              Vui lòng điền đầy đủ thông tin để gửi yêu cầu đăng ký tài khoản. Sau khi được xét duyệt,
              tài khoản của bạn sẽ được kích hoạt để đăng nhập vào máy chủ.
            </p>
            <div className="register-page__notice">
              Sau khi gửi yêu cầu thành công, vui lòng liên hệ quản trị viên để được kích hoạt tài khoản.
            </div>
          </div>

          <form
            className="register-form"
            onSubmit={(event) => {
              event.preventDefault();
              startTransition(() => {
                void submitRegistration();
              });
            }}
          >
            <div className="register-form__grid">
              <label className="register-form__field">
                <span>Tài khoản</span>
                <input
                  type="text"
                  value={form.username}
                  onChange={(event) => onFieldChange('username', event.target.value)}
                  placeholder="Ví dụ: kiemkhach_01"
                  autoComplete="username"
                  maxLength={20}
                  required
                />
              </label>

              <label className="register-form__field">
                <span>Email</span>
                <input
                  type="email"
                  value={form.email}
                  onChange={(event) => onFieldChange('email', event.target.value)}
                  placeholder="Nhập email đang sử dụng"
                  autoComplete="email"
                  maxLength={100}
                  required
                />
              </label>

              <label className="register-form__field">
                <span>Số điện thoại</span>
                <input
                  type="tel"
                  value={form.phone}
                  onChange={(event) => onFieldChange('phone', event.target.value)}
                  placeholder="Không bắt buộc"
                  autoComplete="tel"
                  maxLength={20}
                />
              </label>

              <label className="register-form__field">
                <span>Mật khẩu</span>
                <input
                  type="password"
                  value={form.password}
                  onChange={(event) => onFieldChange('password', event.target.value)}
                  placeholder="Tối thiểu 8 ký tự"
                  autoComplete="new-password"
                  maxLength={32}
                  required
                />
              </label>

              <label className="register-form__field register-form__field--full">
                <span>Nhập lại mật khẩu</span>
                <input
                  type="password"
                  value={form.confirmPassword}
                  onChange={(event) => onFieldChange('confirmPassword', event.target.value)}
                  placeholder="Nhập lại mật khẩu"
                  autoComplete="new-password"
                  maxLength={32}
                  required
                />
              </label>

              <label className="register-form__trap" aria-hidden="true">
                <span>Website</span>
                <input
                  type="text"
                  tabIndex={-1}
                  autoComplete="off"
                  value={form.website}
                  onChange={(event) => onFieldChange('website', event.target.value)}
                />
              </label>
            </div>

            <div className="register-form__meta">
              <p>
                Sau khi gửi yêu cầu, tài khoản của bạn sẽ ở trạng thái <strong>chờ xét duyệt</strong>. Khi được kích hoạt,
                bạn có thể đăng nhập vào máy chủ bình thường.
              </p>
            </div>

            {feedback ? (
              <div className={`register-form__feedback ${feedback.ok ? 'is-success' : 'is-error'}`} aria-live="polite">
                {feedback.message}
              </div>
            ) : null}

            <div className="register-form__actions">
              <button type="submit" className="register-form__submit" disabled={isPending}>
                {isPending ? 'Đang gửi yêu cầu...' : 'Gửi yêu cầu đăng ký'}
              </button>
              <Link href="/" className="register-form__ghost">
                Quay lại
              </Link>
            </div>
          </form>
        </div>
      </div>
    </div>
  );
}
