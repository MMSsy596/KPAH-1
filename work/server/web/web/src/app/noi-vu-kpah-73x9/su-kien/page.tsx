import AdminFrame from '@/components/admin/AdminFrame';
import { ADMIN_BASE_PATH, fetchEventManagement, requireAdminSession } from '@/lib/admin';

const luckyBagFields = [
  ['drop_rate_percent', 'Tỷ lệ rơi (%)'],
  ['max_open_per_day', 'Số túi tối đa/ngày'],
  ['weight_luong', 'Trọng số lượng'],
  ['amount_luong_min', 'Lượng tối thiểu'],
  ['amount_luong_max', 'Lượng tối đa'],
  ['weight_luong_lock', 'Trọng số lượng khóa'],
  ['amount_luong_lock_min', 'Lượng khóa tối thiểu'],
  ['amount_luong_lock_max', 'Lượng khóa tối đa'],
  ['weight_xu', 'Trọng số xu'],
  ['amount_xu_min', 'Xu tối thiểu'],
  ['amount_xu_max', 'Xu tối đa'],
  ['weight_hp', 'Trọng số HP'],
  ['amount_hp_min', 'HP tối thiểu'],
  ['amount_hp_max', 'HP tối đa'],
  ['weight_mp', 'Trọng số MP'],
  ['amount_mp_min', 'MP tối thiểu'],
  ['amount_mp_max', 'MP tối đa']
] as const;

export default async function AdminEventsPage(props: any) {
  const [{ user }, management] = await Promise.all([requireAdminSession(), fetchEventManagement()]);
  const searchParams = props?.searchParams ? await props.searchParams : {};
  const notice = typeof searchParams?.notice === 'string' ? searchParams.notice : '';
  const noticeType = typeof searchParams?.noticeType === 'string' ? searchParams.noticeType : 'success';
  const returnTo = `${ADMIN_BASE_PATH}/su-kien`;

  return (
    <AdminFrame
      user={user}
      title="Quản lý sự kiện"
      description="Bật, tắt hoặc trả sự kiện về lịch tự động; đồng thời điều chỉnh tỷ lệ và phần thưởng Túi may mắn."
      currentPath={returnTo}
      notice={notice}
      noticeType={noticeType}
    >
      <section className="admin-panel">
        <div className="admin-panel__header">
          <strong>Trạng thái sự kiện</strong>
          <span>Tự động dùng lịch gốc; Bật/Tắt sẽ ghi đè cấu hình server.</span>
        </div>
        {management.events.length > 0 ? (
          <form className="admin-form" action="/api/admin/event-config" method="post">
            <input type="hidden" name="action" value="events" />
            <input type="hidden" name="returnTo" value={returnTo} />
            <div className="admin-form__grid">
              {management.events.map((event) => (
                <label className="register-form__field" key={event.key}>
                  <span>{event.label || event.key}</span>
                  <select className="admin-input" name={`event_${event.key}`} defaultValue={String(event.value)}>
                    <option value="-1">Theo lịch tự động</option>
                    <option value="1">Bật</option>
                    <option value="0">Tắt</option>
                  </select>
                </label>
              ))}
            </div>
            <button className="admin-button" type="submit">Áp dụng sự kiện</button>
          </form>
        ) : <div className="admin-empty">Không kết nối được API cấu hình sự kiện.</div>}
      </section>

      <section className="admin-panel">
        <div className="admin-panel__header">
          <strong>Túi may mắn</strong>
          <span>Các giá trị được kiểm tra lại bởi game server trước khi lưu.</span>
        </div>
        {Object.keys(management.luckyBag).length > 0 ? (
          <form className="admin-form" action="/api/admin/event-config" method="post">
            <input type="hidden" name="action" value="lucky_bag" />
            <input type="hidden" name="returnTo" value={returnTo} />
            <div className="admin-form__grid">
              {luckyBagFields.map(([key, label]) => (
                <label className="register-form__field" key={key}>
                  <span>{label}</span>
                  <input className="admin-input" type="number" min={0} step={key === 'drop_rate_percent' ? '0.01' : '1'} name={key} defaultValue={management.luckyBag[key] ?? '0'} required />
                </label>
              ))}
            </div>
            <button className="admin-button" type="submit">Lưu Túi may mắn</button>
          </form>
        ) : <div className="admin-empty">Không kết nối được API Túi may mắn.</div>}
      </section>
    </AdminFrame>
  );
}
