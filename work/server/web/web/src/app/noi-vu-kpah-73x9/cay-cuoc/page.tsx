import AdminFrame from '@/components/admin/AdminFrame';
import { ADMIN_BASE_PATH, fetchGrindingSettings, requireAdminSession } from '@/lib/admin';

const grindingFields = [
  {
    key: 'drop_rate_percent',
    valueKey: 'dropRatePercent',
    label: 'Tỷ lệ rơi đồ thường',
    min: 0,
    description: 'Xu, bình HP/MP, ngọc và trang bị thường. Không đổi đồ nhiệm vụ, Túi may mắn hoặc vật phẩm sự kiện.'
  },
  {
    key: 'monster_damage_percent',
    valueKey: 'monsterDamagePercent',
    label: 'Sát thương quái thường',
    min: 0,
    description: 'Tác động lên sát thương cuối quái gây ra sau khi tính phòng thủ của nhân vật.'
  },
  {
    key: 'monster_hp_percent',
    valueKey: 'monsterHpPercent',
    label: 'HP quái thường',
    min: 10,
    description: 'Quái đang sống giữ nguyên phần trăm HP hiện tại; quái sinh lại và quái mới dùng giá trị mới.'
  },
  {
    key: 'exp_percent',
    valueKey: 'expPercent',
    label: 'EXP khi đánh quái',
    min: 0,
    description: 'Chỉ nhân EXP nhận từ sát thương lên quái thường, không nhân thưởng nhiệm vụ hoặc sự kiện.'
  },
  {
    key: 'monster_density_percent',
    valueKey: 'monsterDensityPercent',
    label: 'Mật độ quái tại bãi train',
    min: 25,
    max: 300,
    description: 'Áp dụng cho 11 bãi train cấp 40+. Khi giảm, hệ thống chỉ gỡ quái bổ sung và luôn giữ nguyên quái gốc của bản đồ.'
  }
] as const;

export default async function AdminGrindingPage(props: any) {
  const [{ user }, settings] = await Promise.all([requireAdminSession(), fetchGrindingSettings()]);
  const searchParams = props?.searchParams ? await props.searchParams : {};
  const notice = typeof searchParams?.notice === 'string' ? searchParams.notice : '';
  const noticeType = typeof searchParams?.noticeType === 'string' ? searchParams.noticeType : 'success';
  const returnTo = `${ADMIN_BASE_PATH}/cay-cuoc`;

  return (
    <AdminFrame
      user={user}
      title="Cấu hình cày cuốc"
      description="Điều chỉnh hệ số quái thường và áp dụng ngay, không cần khởi động lại game server. 100% là thông số gốc."
      currentPath={returnTo}
      notice={notice}
      noticeType={noticeType}
    >
      <section className="admin-panel">
        <div className="admin-panel__header">
          <strong>Hệ số đang áp dụng</strong>
          <span>Các hệ số thường cho phép 0–1000%; HP tối thiểu 10%, mật độ quái 25–300%.</span>
        </div>
        {settings ? (
          <form className="admin-form" action="/api/admin/grinding-config" method="post">
            <input type="hidden" name="returnTo" value={returnTo} />
            <div className="admin-form__grid">
              {grindingFields.map((field) => (
                <label className="register-form__field" key={field.key}>
                  <span>{field.label} (%)</span>
                  <input
                    className="admin-input"
                    type="number"
                    name={field.key}
                    min={field.min}
                    max={'max' in field ? field.max : 1000}
                    step={1}
                    defaultValue={settings[field.valueKey]}
                    required
                  />
                  <small>{field.description}</small>
                </label>
              ))}
            </div>
            <button className="admin-button" type="submit">Lưu và áp dụng ngay</button>
          </form>
        ) : (
          <div className="admin-empty">Không kết nối được API cấu hình cày cuốc.</div>
        )}
      </section>

      <section className="admin-panel">
        <div className="admin-panel__header">
          <strong>Phạm vi bảo vệ cân bằng</strong>
          <span>Boss, công thành, quái nguyên liệu đặc biệt, PvP, vật phẩm nhiệm vụ và phần thưởng sự kiện giữ nguyên.</span>
        </div>
      </section>
    </AdminFrame>
  );
}
