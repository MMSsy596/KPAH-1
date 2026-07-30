import AdminFrame from '@/components/admin/AdminFrame';
import { ADMIN_BASE_PATH, listAdminActionLogs, requireAdminSession } from '@/lib/admin';

export default async function AdminLogsPage(props: any) {
  const [{ user }, logs] = await Promise.all([
    requireAdminSession(),
    listAdminActionLogs(200)
  ]);

  const searchParams = props?.searchParams ? await props.searchParams : {};
  const notice = typeof searchParams?.notice === 'string' ? searchParams.notice : '';
  const noticeType = typeof searchParams?.noticeType === 'string' ? searchParams.noticeType : 'success';

  return (
    <AdminFrame
      user={user}
      title="Nhật ký quản trị"
      description="Theo dõi toàn bộ thao tác quản trị đã đi qua trang admin, bao gồm kết quả thành công hoặc thất bại."
      currentPath={`${ADMIN_BASE_PATH}/nhat-ky`}
      notice={notice}
      noticeType={noticeType}
    >
      <section className="admin-panel">
        <div className="admin-panel__header">
          <strong>Lịch sử thao tác</strong>
          <span>{logs.length} bản ghi mới nhất</span>
        </div>
        {logs.length > 0 ? (
          <div className="admin-log-table">
            {logs.map((item) => (
              <div key={item.id} className="admin-log-table__row">
                <div><span>ID</span><strong>#{item.id}</strong></div>
                <div><span>Admin</span><strong>{item.adminUsername}</strong></div>
                <div><span>Hành động</span><strong>{item.actionType}</strong></div>
                <div><span>Mục tiêu</span><strong>{item.targetType}: {item.targetValue || 'N/A'}</strong></div>
                <div><span>Kết quả</span><strong>{item.success ? 'Thành công' : 'Thất bại'}</strong></div>
                <div><span>Phản hồi</span><strong>{item.resultMessage}</strong></div>
                <div><span>IP</span><strong>{item.requestIp || 'N/A'}</strong></div>
                <div><span>Thời gian</span><strong>{item.createdAt ?? 'N/A'}</strong></div>
                {item.detailText ? <div className="admin-log-table__detail">{item.detailText}</div> : null}
              </div>
            ))}
          </div>
        ) : (
          <div className="admin-empty">Chưa có thao tác quản trị nào được ghi nhận.</div>
        )}
      </section>
    </AdminFrame>
  );
}
