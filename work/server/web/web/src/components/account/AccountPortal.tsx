import Link from 'next/link';

import type { PlayerPortalData } from '@/lib/player-portal';

type AccountPortalProps = {
  data: PlayerPortalData;
};

const numberFormatter = new Intl.NumberFormat('vi-VN');

function formatNumber(value: number): string {
  return numberFormatter.format(value);
}

function formatDateTime(value: string | null): string {
  if (!value) {
    return 'Chưa có dữ liệu';
  }

  const normalized = value.replace(' ', 'T');
  const parsed = new Date(normalized);

  if (Number.isNaN(parsed.getTime())) {
    return value;
  }

  return new Intl.DateTimeFormat('vi-VN', {
    dateStyle: 'medium',
    timeStyle: 'short'
  }).format(parsed);
}

export default function AccountPortal({ data }: AccountPortalProps) {
  const character = data.character;

  return (
    <div className="account-page">
      <div className="register-page__backdrop" />
      <div className="register-page__shell">
        <div className="register-page__topbar">
          <Link href="/" className="register-page__brand">
            <img src="/logo/logo.png" alt="KPAH" />
            <span>KPAH</span>
          </Link>
          <form action="/api/logout" method="post">
            <button type="submit" className="register-page__backlink account-page__logout">
              Đăng xuất
            </button>
          </form>
        </div>

        <div className="account-page__hero">
          <div>
            <p className="register-page__eyebrow">Khu tài khoản</p>
            <h1>Xin chào, {data.account.username}</h1>
            <p className="register-page__lead">
              Theo dõi nhanh tình trạng nhân vật, tài sản hiện có và các cập nhật mới từ máy chủ tại một nơi duy nhất.
            </p>
          </div>

          <div className="account-page__hero-status">
            <span>Trạng thái tài khoản</span>
            <strong>{data.account.statusLabel}</strong>
            <small>Ngày tạo: {formatDateTime(data.account.regdate)}</small>
          </div>
        </div>

        <div className="account-page__grid">
          <section className="account-card">
            <div className="account-card__header">
              <span>Nhân vật đang dùng</span>
              <strong>{character ? character.charname : 'Chưa có nhân vật'}</strong>
            </div>

            {character ? (
              <div className="account-card__body">
                <div className="account-stat-list">
                  <div className="account-stat-list__item">
                    <span>Trạng thái</span>
                    <strong>{character.statusLabel}</strong>
                  </div>
                  <div className="account-stat-list__item">
                    <span>Cấp độ</span>
                    <strong>Lv {character.level}</strong>
                  </div>
                  <div className="account-stat-list__item">
                    <span>Môn phái</span>
                    <strong>{character.className}</strong>
                  </div>
                  <div className="account-stat-list__item">
                    <span>Lần hoạt động gần nhất</span>
                    <strong>{formatDateTime(character.lastLog)}</strong>
                  </div>
                </div>

                <div className="account-inline-note">
                  {character.location ? `Vị trí hiện tại: ${character.location}` : 'Hiện chưa có vị trí trực tuyến để hiển thị.'}
                </div>
              </div>
            ) : (
              <div className="account-empty">
                Tài khoản của bạn chưa có nhân vật. Khi vào game tạo nhân vật xong, dữ liệu sẽ hiển thị tại đây.
              </div>
            )}
          </section>

          <section className="account-card">
            <div className="account-card__header">
              <span>Tài sản nhân vật</span>
              <strong>Cập nhật gần nhất</strong>
            </div>

            {character ? (
              <div className="account-metric-grid">
                <div className="account-metric">
                  <span>Xu</span>
                  <strong>{formatNumber(character.gold)}</strong>
                </div>
                <div className="account-metric">
                  <span>Lượng</span>
                  <strong>{formatNumber(character.luong)}</strong>
                </div>
                <div className="account-metric">
                  <span>Lượng khóa</span>
                  <strong>{formatNumber(character.luongLock)}</strong>
                </div>
                <div className="account-metric">
                  <span>Kinh nghiệm</span>
                  <strong>{formatNumber(character.xp)}</strong>
                </div>
              </div>
            ) : (
              <div className="account-empty">Chưa có dữ liệu tài sản để hiển thị.</div>
            )}
          </section>

          <section className="account-card">
            <div className="account-card__header">
              <span>Xếp hạng máy chủ</span>
              <strong>So với toàn server</strong>
            </div>

            {character ? (
              <div className="account-ranking">
                <div className="account-ranking__badge">
                  <span>Thực lực</span>
                  <strong>#{formatNumber(character.powerRank)}</strong>
                  <small>Top {character.powerPercentile}% máy chủ</small>
                </div>

                <div className="account-stat-list">
                  <div className="account-stat-list__item">
                    <span>Xếp hạng thực lực</span>
                    <strong>
                      #{formatNumber(character.powerRank)} / {formatNumber(character.totalRankedPlayers)}
                    </strong>
                  </div>
                  <div className="account-stat-list__item">
                    <span>Xếp hạng tài phú</span>
                    <strong>
                      #{formatNumber(character.wealthRank)} / {formatNumber(character.totalRankedPlayers)}
                    </strong>
                  </div>
                </div>
              </div>
            ) : (
              <div className="account-empty">Chưa có dữ liệu xếp hạng để hiển thị.</div>
            )}
          </section>

          <section className="account-card account-card--announcements">
            <div className="account-card__header">
              <span>Thông báo từ admin</span>
              <strong>{data.announcements.length} mục mới nhất</strong>
            </div>

            {data.announcements.length > 0 ? (
              <div className="account-announcements">
                {data.announcements.map((announcement) => (
                  <article key={announcement.id} className="account-announcement">
                    <div className="account-announcement__meta">
                      <strong>{announcement.title}</strong>
                      <span>{formatDateTime(announcement.publishedAt)}</span>
                    </div>
                    <p>{announcement.content}</p>
                  </article>
                ))}
              </div>
            ) : (
              <div className="account-empty">Hiện chưa có thông báo mới từ quản trị viên.</div>
            )}
          </section>
        </div>
      </div>
    </div>
  );
}
