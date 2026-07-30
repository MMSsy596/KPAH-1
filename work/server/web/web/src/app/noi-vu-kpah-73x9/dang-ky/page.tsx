import AdminFrame from '@/components/admin/AdminFrame';
import { ADMIN_BASE_PATH, listPendingRegistrations, requireAdminSession } from '@/lib/admin';

export default async function AdminPendingPage(props: any) {
  const [{ user }, pendingList] = await Promise.all([
    requireAdminSession(),
    listPendingRegistrations(80)
  ]);

  const searchParams = props?.searchParams ? await props.searchParams : {};
  const notice = typeof searchParams?.notice === 'string' ? searchParams.notice : '';
  const noticeType = typeof searchParams?.noticeType === 'string' ? searchParams.noticeType : 'success';

  return (
    <AdminFrame
      user={user}
      title="Duyệt tài khoản đăng ký"
      description="Kích hoạt tài khoản sau khi kiểm tra xong để chỉ tài khoản được duyệt mới đi vào hệ thống chính."
      currentPath={`${ADMIN_BASE_PATH}/dang-ky`}
      notice={notice}
      noticeType={noticeType}
    >
      <section className="admin-panel">
        <div className="admin-panel__header">
          <strong>Hàng chờ xét duyệt</strong>
          <span>{pendingList.length} yêu cầu đang chờ xử lý</span>
        </div>

        {pendingList.length > 0 ? (
          <div className="admin-queue">
            {pendingList.map((item) => (
              <article key={item.id} className="admin-queue__item">
                <div className="admin-queue__info">
                  <div><span>Tài khoản</span><strong>{item.username}</strong></div>
                  <div><span>Email</span><strong>{item.email}</strong></div>
                  <div><span>Số điện thoại</span><strong>{item.phone || 'Không có'}</strong></div>
                  <div><span>IP gửi</span><strong>{item.requestIp}</strong></div>
                  <div><span>Thời gian</span><strong>{item.requestedAt ?? 'N/A'}</strong></div>
                  <div><span>User-Agent</span><strong>{item.userAgent || 'Không có'}</strong></div>
                </div>

                <form className="admin-form" action="/api/admin/pending" method="post">
                  <input type="hidden" name="id" value={item.id} />
                  <input type="hidden" name="returnTo" value={`${ADMIN_BASE_PATH}/dang-ky`} />
                  <label className="register-form__field">
                    <span>Ghi chú xử lý</span>
                    <textarea name="note" className="admin-textarea" rows={3} placeholder="Ví dụ: Đã xác minh, cho duyệt." />
                  </label>
                  <div className="admin-actions-row">
                    <button type="submit" name="decision" value="approve" className="admin-button">
                      Kích hoạt tài khoản
                    </button>
                    <button type="submit" name="decision" value="reject" className="admin-button admin-button--danger">
                      Từ chối yêu cầu
                    </button>
                  </div>
                </form>
              </article>
            ))}
          </div>
        ) : (
          <div className="admin-empty">Không còn yêu cầu nào cần duyệt.</div>
        )}
      </section>
    </AdminFrame>
  );
}
