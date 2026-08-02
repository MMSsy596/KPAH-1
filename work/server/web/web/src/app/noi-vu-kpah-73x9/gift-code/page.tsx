import AdminFrame from '@/components/admin/AdminFrame';
import { ADMIN_BASE_PATH, requireAdminSession } from '@/lib/admin';
import { listGiftCodeLogs, listGiftCodes } from '@/lib/gift-codes';

export default async function AdminGiftCodesPage(props: any) {
  const { user } = await requireAdminSession();
  const searchParams = props?.searchParams ? await props.searchParams : {};
  const q = typeof searchParams?.q === 'string' ? searchParams.q : '';
  const notice = typeof searchParams?.notice === 'string' ? searchParams.notice : '';
  const noticeType = typeof searchParams?.noticeType === 'string' ? searchParams.noticeType : 'success';
  const [codes, logs] = await Promise.all([listGiftCodes(q), listGiftCodeLogs(100)]);
  const returnTo = q
    ? `${ADMIN_BASE_PATH}/gift-code?q=${encodeURIComponent(q)}`
    : `${ADMIN_BASE_PATH}/gift-code`;

  return (
    <AdminFrame
      user={user}
      title="Quản lý gift code"
      description="Tạo mã quà, đặt giới hạn và thời gian hiệu lực, tắt mã khẩn cấp và theo dõi nhân vật đã nhận."
      currentPath={`${ADMIN_BASE_PATH}/gift-code`}
      notice={notice}
      noticeType={noticeType}
    >
      <section className="admin-panel">
        <div className="admin-panel__header">
          <strong>Tạo gift code</strong>
          <span>Mã được chuẩn hóa về chữ thường; -1 là không giới hạn lượt.</span>
        </div>
        <form className="admin-form" action="/api/admin/gift-codes" method="post">
          <input type="hidden" name="action" value="create" />
          <input type="hidden" name="returnTo" value={returnTo} />
          <div className="admin-form__grid">
            <label className="register-form__field"><span>Mã</span><input className="admin-input" name="code" minLength={4} maxLength={40} required /></label>
            <label className="register-form__field"><span>Giới hạn lượt</span><input className="admin-input" type="number" name="limitUse" min={-1} defaultValue={-1} required /></label>
            <label className="register-form__field"><span>Xu</span><input className="admin-input" type="number" name="xu" min={0} defaultValue={0} required /></label>
            <label className="register-form__field"><span>Lượng</span><input className="admin-input" type="number" name="luong" min={0} defaultValue={0} required /></label>
            <label className="register-form__field"><span>Lượng khóa</span><input className="admin-input" type="number" name="luongLock" min={0} defaultValue={0} required /></label>
            <label className="register-form__field"><span>Bắt đầu</span><input className="admin-input" type="datetime-local" name="startsAt" /></label>
            <label className="register-form__field"><span>Kết thúc</span><input className="admin-input" type="datetime-local" name="expiresAt" /></label>
          </div>
          <label className="register-form__field">
            <span>Vật phẩm</span>
            <textarea className="admin-textarea" name="item" placeholder="Ví dụ: ITEM:69:5,GEM:249:10:-1,TRANGPHUC:725:1:10080" />
          </label>
          <div className="admin-material-reference">
            <strong>Mẫu thường dùng</strong>
            <div className="admin-material-reference__groups">
              <div className="admin-material-reference__group"><span>Vật phẩm</span><code>ITEM:ID:SỐ_LƯỢNG</code></div>
              <div className="admin-material-reference__group"><span>Đá/nguyên liệu</span><code>GEM:ID:SỐ_LƯỢNG:-1</code></div>
              <div className="admin-material-reference__group"><span>Trang phục</span><code>TRANGPHUC:ID:SỐ_LƯỢNG:PHÚT</code></div>
            </div>
          </div>
          <button type="submit" className="admin-button">Tạo gift code</button>
        </form>
      </section>

      <section className="admin-panel">
        <div className="admin-panel__header"><strong>Danh sách mã</strong><span>{codes.length} mã phù hợp</span></div>
        <form className="admin-search" action={`${ADMIN_BASE_PATH}/gift-code`} method="get">
          <input className="admin-input" name="q" defaultValue={q} placeholder="Tìm theo mã..." />
          <button className="admin-button" type="submit">Tìm kiếm</button>
        </form>
      </section>

      {codes.length > 0 ? codes.map((code) => (
        <section className="admin-panel" key={code.id}>
          <div className="admin-panel__header">
            <strong>{code.code}</strong>
            <span>{code.isActive ? 'Đang bật' : 'Đang tắt'} • Đã nhận {code.redeemedCount} • Còn {code.limitUse < 0 ? 'không giới hạn' : code.limitUse}</span>
          </div>
          <form className="admin-form" action="/api/admin/gift-codes" method="post">
            <input type="hidden" name="action" value="update" />
            <input type="hidden" name="id" value={code.id} />
            <input type="hidden" name="returnTo" value={returnTo} />
            <div className="admin-form__grid">
              <label className="register-form__field"><span>Mã</span><input className="admin-input" name="code" defaultValue={code.code} required /></label>
              <label className="register-form__field"><span>Lượt còn lại</span><input className="admin-input" type="number" name="limitUse" min={-1} defaultValue={code.limitUse} required /></label>
              <label className="register-form__field"><span>Xu</span><input className="admin-input" type="number" name="xu" min={0} defaultValue={code.xu} required /></label>
              <label className="register-form__field"><span>Lượng</span><input className="admin-input" type="number" name="luong" min={0} defaultValue={code.luong} required /></label>
              <label className="register-form__field"><span>Lượng khóa</span><input className="admin-input" type="number" name="luongLock" min={0} defaultValue={code.luongLock} required /></label>
              <label className="register-form__field"><span>Bắt đầu</span><input className="admin-input" type="datetime-local" name="startsAt" defaultValue={code.startsAt} /></label>
              <label className="register-form__field"><span>Kết thúc</span><input className="admin-input" type="datetime-local" name="expiresAt" defaultValue={code.expiresAt} /></label>
            </div>
            <label className="register-form__field"><span>Vật phẩm</span><textarea className="admin-textarea" name="item" defaultValue={code.item} /></label>
            <button type="submit" className="admin-button">Lưu thay đổi</button>
          </form>
          <div className="admin-actions-row admin-section-actions">
            <form action="/api/admin/gift-codes" method="post">
              <input type="hidden" name="action" value={code.isActive ? 'deactivate' : 'activate'} />
              <input type="hidden" name="id" value={code.id} />
              <input type="hidden" name="returnTo" value={returnTo} />
              <button className="admin-button admin-button--ghost" type="submit">{code.isActive ? 'Tắt mã' : 'Bật mã'}</button>
            </form>
            <form action="/api/admin/gift-codes" method="post">
              <input type="hidden" name="action" value="delete" />
              <input type="hidden" name="id" value={code.id} />
              <input type="hidden" name="returnTo" value={returnTo} />
              <button className="admin-button admin-button--danger" type="submit">Xóa nếu chưa dùng</button>
            </form>
          </div>
        </section>
      )) : <section className="admin-panel"><div className="admin-empty">Chưa có gift code nào.</div></section>}

      <section className="admin-panel">
        <div className="admin-panel__header"><strong>Lịch sử nhận quà</strong><span>100 lượt gần nhất</span></div>
        <div className="admin-log-table">
          {logs.length > 0 ? logs.map((log) => (
            <div className="admin-log-table__row" key={log.id}>
              <div><span>Mã</span><strong>{log.code}</strong></div>
              <div><span>Nhân vật</span><strong>{log.player}</strong></div>
              <div><span>Quà tiền</span><strong>{log.xu} xu / {log.luong} L / {log.luongLock} LK</strong></div>
              <div><span>Trạng thái</span><strong>{log.status}</strong></div>
              <div><span>Thời gian</span><strong>{log.redeemedAt}</strong></div>
              {log.item ? <div className="admin-log-table__detail">{log.item}</div> : null}
              {log.errorMessage ? <div className="admin-log-table__detail">Lỗi: {log.errorMessage}</div> : null}
            </div>
          )) : <div className="admin-empty">Chưa có lượt nhận gift code.</div>}
        </div>
      </section>
    </AdminFrame>
  );
}
