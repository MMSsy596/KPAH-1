import AdminFrame from '@/components/admin/AdminFrame';
import {
  ADMIN_BASE_PATH,
  getAdminDashboardData,
  requireAdminSession
} from '@/lib/admin';

export default async function AdminDashboardPage(props: any) {
  const [{ user }, dashboard] = await Promise.all([
    requireAdminSession(),
    getAdminDashboardData()
  ]);

  const searchParams = props?.searchParams ? await props.searchParams : {};
  const notice = typeof searchParams?.notice === 'string' ? searchParams.notice : '';
  const noticeType = typeof searchParams?.noticeType === 'string' ? searchParams.noticeType : 'success';

  return (
    <AdminFrame
      user={user}
      title="Tổng quan vận hành"
      description="Theo dõi nhanh trạng thái server, hàng chờ đăng ký, thông báo và các thao tác quản trị gần nhất."
      currentPath={`${ADMIN_BASE_PATH}/tong-quan`}
      notice={notice}
      noticeType={noticeType}
    >
      <section className="admin-grid admin-grid--summary">
        <div className="admin-card">
          <span className="admin-card__label">Server</span>
          <strong className="admin-card__value">{dashboard.serverStatus.serverState}</strong>
          <p>{dashboard.serverStatus.onlinePlayers} / {dashboard.serverStatus.playerLimit} người chơi đang online</p>
        </div>
        <div className="admin-card">
          <span className="admin-card__label">Chờ duyệt</span>
          <strong className="admin-card__value">{dashboard.pendingCount}</strong>
          <p>Tài khoản đang chờ kích hoạt từ web</p>
        </div>
        <div className="admin-card">
          <span className="admin-card__label">Thông báo</span>
          <strong className="admin-card__value">{dashboard.announcementCount}</strong>
          <p>Thông báo đang hiển thị trong khu tài khoản</p>
        </div>
        <div className="admin-card">
          <span className="admin-card__label">Admin hoạt động</span>
          <strong className="admin-card__value">{dashboard.activeAdminCount}</strong>
          <p>Tài khoản quản trị đang bật trong hệ thống</p>
        </div>
      </section>

      <section className="admin-grid">
        <div className="admin-panel">
          <div className="admin-panel__header">
            <strong>Trạng thái máy chủ</strong>
            <span>Cập nhật thời gian thực qua local admin</span>
          </div>
          <div className="admin-stat-list">
            <div className="admin-stat-list__item"><span>Uptime</span><strong>{dashboard.serverStatus.uptimeText || 'Đang cập nhật'}</strong></div>
            <div className="admin-stat-list__item"><span>Bộ nhớ</span><strong>{dashboard.serverStatus.memoryUsedMb} / {dashboard.serverStatus.memoryTotalMb} MB</strong></div>
            <div className="admin-stat-list__item"><span>Bảo trì</span><strong>{dashboard.serverStatus.maintenanceScheduled ? `Đang hẹn sau ${dashboard.serverStatus.maintenanceRemainingMinutes} phút` : 'Chưa lên lịch'}</strong></div>
          </div>
        </div>

        <div className="admin-panel">
          <div className="admin-panel__header">
            <strong>Thông báo live tới server</strong>
            <span>Gửi trực tiếp cho người chơi đang online</span>
          </div>
          <form className="admin-form" action="/api/admin/system-command" method="post">
            <input type="hidden" name="action" value="announce_live" />
            <input type="hidden" name="returnTo" value={`${ADMIN_BASE_PATH}/tong-quan`} />
            <label className="register-form__field">
              <span>Vị trí hiển thị</span>
              <select name="type" className="admin-input" defaultValue="top">
                <option value="top">Phía trên</option>
                <option value="middle">Giữa màn hình</option>
                <option value="bottom">Chạy dưới cùng</option>
              </select>
            </label>
            <label className="register-form__field">
              <span>Nội dung</span>
              <textarea name="message" className="admin-textarea" rows={4} placeholder="Nhập nội dung cần gửi..." required />
            </label>
            <button type="submit" className="admin-button">Gửi thông báo live</button>
          </form>
        </div>
      </section>

      <section className="admin-grid">
        <div className="admin-panel">
          <div className="admin-panel__header">
            <strong>Lịch bảo trì</strong>
            <span>Tạo hoặc hủy lịch bảo trì toàn server</span>
          </div>
          <div className="admin-actions-row">
            <form className="admin-form admin-form--inline" action="/api/admin/system-command" method="post">
              <input type="hidden" name="action" value="schedule_maintenance" />
              <input type="hidden" name="returnTo" value={`${ADMIN_BASE_PATH}/tong-quan`} />
              <label className="register-form__field">
                <span>Số phút</span>
                <input type="number" min={1} max={60} name="minutes" defaultValue={5} />
              </label>
              <button type="submit" className="admin-button">Lên lịch bảo trì</button>
            </form>

            <form action="/api/admin/system-command" method="post">
              <input type="hidden" name="action" value="cancel_maintenance" />
              <input type="hidden" name="returnTo" value={`${ADMIN_BASE_PATH}/tong-quan`} />
              <button type="submit" className="admin-button admin-button--ghost">Hủy lịch bảo trì</button>
            </form>
          </div>
        </div>

        <div className="admin-panel">
          <div className="admin-panel__header">
            <strong>Đổi mật khẩu admin</strong>
            <span>Nên đổi ngay sau lần đăng nhập đầu tiên</span>
          </div>
          <form className="admin-form" action="/api/admin/system-command" method="post">
            <input type="hidden" name="action" value="change_admin_password" />
            <input type="hidden" name="returnTo" value={`${ADMIN_BASE_PATH}/tong-quan`} />
            <label className="register-form__field">
              <span>Mật khẩu hiện tại</span>
              <input type="password" name="currentPassword" required />
            </label>
            <label className="register-form__field">
              <span>Mật khẩu mới</span>
              <input type="password" name="newPassword" minLength={10} required />
            </label>
            <button type="submit" className="admin-button">Đổi mật khẩu admin</button>
          </form>
        </div>
      </section>

      <section className="admin-grid">
        <div className="admin-panel">
          <div className="admin-panel__header">
            <strong>Yêu cầu chờ gần nhất</strong>
            <span>5 tài khoản cần duyệt đầu tiên</span>
          </div>
          <div className="admin-list">
            {dashboard.recentPending.length > 0 ? dashboard.recentPending.map((item) => (
              <div key={item.id} className="admin-list__item">
                <div>
                  <strong>{item.username}</strong>
                  <span>{item.email}</span>
                </div>
                <small>{item.requestedAt ?? 'N/A'}</small>
              </div>
            )) : <div className="admin-empty">Hiện không có yêu cầu nào chờ duyệt.</div>}
          </div>
        </div>

        <div className="admin-panel">
          <div className="admin-panel__header">
            <strong>Nhật ký gần nhất</strong>
            <span>8 thao tác quản trị vừa thực hiện</span>
          </div>
          <div className="admin-list">
            {dashboard.recentLogs.length > 0 ? dashboard.recentLogs.map((item) => (
              <div key={item.id} className="admin-list__item">
                <div>
                  <strong>{item.actionType}</strong>
                  <span>{item.adminUsername} • {item.targetValue || 'không có mục tiêu'}</span>
                </div>
                <small>{item.createdAt ?? 'N/A'}</small>
              </div>
            )) : <div className="admin-empty">Chưa có nhật ký thao tác nào.</div>}
          </div>
        </div>
      </section>
    </AdminFrame>
  );
}
