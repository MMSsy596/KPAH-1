import AdminFrame from '@/components/admin/AdminFrame';
import { ADMIN_BASE_PATH, listStoredAnnouncements, requireAdminSession } from '@/lib/admin';

export default async function AdminAnnouncementsPage(props: any) {
  const [{ user }, announcements] = await Promise.all([
    requireAdminSession(),
    listStoredAnnouncements(40)
  ]);

  const searchParams = props?.searchParams ? await props.searchParams : {};
  const notice = typeof searchParams?.notice === 'string' ? searchParams.notice : '';
  const noticeType = typeof searchParams?.noticeType === 'string' ? searchParams.noticeType : 'success';

  return (
    <AdminFrame
      user={user}
      title="Quản lý thông báo"
      description="Tạo thông báo hiển thị trong khu tài khoản người chơi và dọn các thông báo cũ khi cần."
      currentPath={`${ADMIN_BASE_PATH}/thong-bao`}
      notice={notice}
      noticeType={noticeType}
    >
      <section className="admin-grid">
        <div className="admin-panel">
          <div className="admin-panel__header">
            <strong>Tạo thông báo mới</strong>
            <span>Thông báo này sẽ xuất hiện trong khu tài khoản người chơi</span>
          </div>
          <form className="admin-form" action="/api/admin/announcements" method="post">
            <input type="hidden" name="action" value="create" />
            <input type="hidden" name="returnTo" value={`${ADMIN_BASE_PATH}/thong-bao`} />
            <label className="register-form__field">
              <span>Tiêu đề</span>
              <input type="text" name="title" maxLength={160} required />
            </label>
            <label className="register-form__field">
              <span>Nội dung</span>
              <textarea name="content" className="admin-textarea" rows={7} required />
            </label>
            <button type="submit" className="admin-button">Đăng thông báo</button>
          </form>
        </div>

        <div className="admin-panel">
          <div className="admin-panel__header">
            <strong>Thông báo đang lưu</strong>
            <span>{announcements.length} mục gần nhất</span>
          </div>
          {announcements.length > 0 ? (
            <div className="admin-announcements">
              {announcements.map((item) => (
                <article key={item.id} className="admin-announcement-card">
                  <div className="admin-announcement-card__head">
                    <div>
                      <strong>{item.title}</strong>
                      <span>{item.publishedAt ?? 'N/A'}</span>
                    </div>
                    <form action="/api/admin/announcements" method="post">
                      <input type="hidden" name="action" value="delete" />
                      <input type="hidden" name="id" value={item.id} />
                      <input type="hidden" name="returnTo" value={`${ADMIN_BASE_PATH}/thong-bao`} />
                      <button type="submit" className="admin-button admin-button--danger">Xóa</button>
                    </form>
                  </div>
                  <p>{item.content}</p>
                </article>
              ))}
            </div>
          ) : (
            <div className="admin-empty">Chưa có thông báo nào được tạo.</div>
          )}
        </div>
      </section>
    </AdminFrame>
  );
}
