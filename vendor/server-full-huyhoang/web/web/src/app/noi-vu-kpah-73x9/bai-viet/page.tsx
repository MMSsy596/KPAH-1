import AdminFrame from '@/components/admin/AdminFrame';
import { ADMIN_BASE_PATH, listStoredHomePosts, requireAdminSession } from '@/lib/admin';
import { HOME_POST_CATEGORY_OPTIONS, getHomePostCategoryLabel } from '@/lib/home-posts';

export default async function AdminHomePostsPage(props: any) {
  const [{ user }, posts] = await Promise.all([
    requireAdminSession(),
    listStoredHomePosts(120)
  ]);

  const searchParams = props?.searchParams ? await props.searchParams : {};
  const notice = typeof searchParams?.notice === 'string' ? searchParams.notice : '';
  const noticeType = typeof searchParams?.noticeType === 'string' ? searchParams.noticeType : 'success';

  return (
    <AdminFrame
      user={user}
      title="Bài viết trang chủ"
      description="Đăng bài theo từng mục Bài đăng mới, Tính năng và Hướng dẫn để hiển thị trực tiếp trên trang chủ."
      currentPath={`${ADMIN_BASE_PATH}/bai-viet`}
      notice={notice}
      noticeType={noticeType}
    >
      <section className="admin-grid">
        <div className="admin-panel">
          <div className="admin-panel__header">
            <strong>Đăng bài mới</strong>
            <span>Bài đăng tại đây sẽ thay cho nội dung mẫu cũ trên trang chủ</span>
          </div>
          <form className="admin-form" action="/api/admin/home-posts" method="post">
            <input type="hidden" name="action" value="create" />
            <input type="hidden" name="returnTo" value={`${ADMIN_BASE_PATH}/bai-viet`} />
            <label className="register-form__field">
              <span>Chuyên mục</span>
              <select name="category" defaultValue="new" required>
                {HOME_POST_CATEGORY_OPTIONS.map((item) => (
                  <option key={item.value} value={item.value}>{item.label}</option>
                ))}
              </select>
            </label>
            <label className="register-form__field">
              <span>Tiêu đề</span>
              <input type="text" name="title" maxLength={180} required />
            </label>
            <label className="register-form__field">
              <span>Nội dung</span>
              <textarea name="content" className="admin-textarea" rows={10} required />
            </label>
            <label className="register-form__field">
              <span>Độ ưu tiên hiển thị</span>
              <input type="number" name="displayOrder" defaultValue={0} />
            </label>
            <button type="submit" className="admin-button">Đăng bài</button>
          </form>
        </div>

        <div className="admin-panel">
          <div className="admin-panel__header">
            <strong>Bài đang lưu</strong>
            <span>{posts.length} mục hiện có</span>
          </div>
          {posts.length > 0 ? (
            <div className="admin-announcements">
              {posts.map((item) => (
                <article key={item.id} className="admin-announcement-card">
                  <div className="admin-announcement-card__head">
                    <div>
                      <strong>{item.title}</strong>
                      <span>{getHomePostCategoryLabel(item.category)} • {item.publishedAt ?? 'N/A'}</span>
                    </div>
                    <form action="/api/admin/home-posts" method="post">
                      <input type="hidden" name="action" value="delete" />
                      <input type="hidden" name="id" value={item.id} />
                      <input type="hidden" name="returnTo" value={`${ADMIN_BASE_PATH}/bai-viet`} />
                      <button type="submit" className="admin-button admin-button--danger">Xóa</button>
                    </form>
                  </div>
                  <p>{item.content}</p>
                </article>
              ))}
            </div>
          ) : (
            <div className="admin-empty">Trang chủ hiện chưa có bài viết nào được đăng.</div>
          )}
        </div>
      </section>
    </AdminFrame>
  );
}
