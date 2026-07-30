import Link from 'next/link';

import AdminFrame from '@/components/admin/AdminFrame';
import {
  ADMIN_BASE_PATH,
  formatAccountBanStatus,
  formatCharacterBanStatus,
  mapClassName,
  requireAdminSession,
  searchPlayers
} from '@/lib/admin';

const ADMIN_MATERIAL_REFERENCE = [
  { group: 'Nguyên liệu sơ cấp cấp 6', items: ['73 - Vải 6', '80 - Sắt cấp 6', '87 - Ngọc cấp 6', '94 - Gỗ thường cấp 6', '101 - Da mềm cấp 6'] },
  { group: 'Nguyên liệu cao cấp cấp 6', items: ['108 - Tơ lụa cấp 6', '115 - Bạc cấp 6', '122 - Thủy tinh cấp 6', '129 - Gỗ sưa cấp 6', '136 - Da cứng cấp 6'] },
  { group: 'Bột hỗ trợ', items: ['247 - Bột xanh', '249 - Bột xanh lá'] }
];

export default async function AdminPlayersPage(props: any) {
  const { user } = await requireAdminSession();
  const searchParams = props?.searchParams ? await props.searchParams : {};
  const q = typeof searchParams?.q === 'string' ? searchParams.q : '';
  const selectedAccount = typeof searchParams?.account === 'string' ? searchParams.account.toLowerCase() : '';
  const notice = typeof searchParams?.notice === 'string' ? searchParams.notice : '';
  const noticeType = typeof searchParams?.noticeType === 'string' ? searchParams.noticeType : 'success';

  const players = q ? await searchPlayers(q) : [];
  const selectedPlayer = players.find((player) => player.username.toLowerCase() === selectedAccount) ?? players[0] ?? null;

  const returnToBase = q ? `${ADMIN_BASE_PATH}/nguoi-choi?q=${encodeURIComponent(q)}` : `${ADMIN_BASE_PATH}/nguoi-choi`;
  const returnTo = selectedPlayer
    ? `${returnToBase}&account=${encodeURIComponent(selectedPlayer.username)}`
    : returnToBase;

  return (
    <AdminFrame
      user={user}
      title="Quản lý người chơi"
      description="Tra cứu tài khoản hoặc nhân vật, sau đó mở đúng hồ sơ cần xử lý để khóa, đổi mật khẩu hoặc cộng tài nguyên."
      currentPath={`${ADMIN_BASE_PATH}/nguoi-choi`}
      notice={notice}
      noticeType={noticeType}
    >
      <section className="admin-panel">
        <div className="admin-panel__header">
          <strong>Tra cứu tài khoản</strong>
          <span>Tìm theo tài khoản, email hoặc tên nhân vật</span>
        </div>
        <form className="admin-search" action={`${ADMIN_BASE_PATH}/nguoi-choi`} method="get">
          <input
            type="text"
            name="q"
            defaultValue={q}
            className="admin-input"
            placeholder="Nhập tài khoản, email hoặc tên nhân vật..."
          />
          <button type="submit" className="admin-button">Tìm kiếm</button>
        </form>
      </section>

      {!q ? (
        <section className="admin-panel">
          <div className="admin-empty">
            Nhập tài khoản hoặc tên nhân vật để mở hồ sơ người chơi. Trang này sẽ chỉ hiện đúng tài khoản bạn đang cần thao tác.
          </div>
        </section>
      ) : null}

      {q && players.length > 0 ? (
        <>
          <section className="admin-panel">
            <div className="admin-panel__header">
              <strong>Chọn tài khoản cần xử lý</strong>
              <span>{players.length} kết quả phù hợp</span>
            </div>
            <div className="admin-player-picker">
              {players.map((player) => {
                const href = `${ADMIN_BASE_PATH}/nguoi-choi?q=${encodeURIComponent(q)}&account=${encodeURIComponent(player.username)}`;
                const isActive = selectedPlayer?.accountId === player.accountId && selectedPlayer?.charId === player.charId;

                return (
                  <Link
                    key={`${player.accountId}-${player.charId ?? 'na'}`}
                    href={href}
                    className={`admin-player-picker__item ${isActive ? 'is-active' : ''}`}
                  >
                    <strong>{player.username}</strong>
                    <span>{player.charname ?? 'Chưa có nhân vật'}</span>
                    <small>
                      {player.lastLv != null ? `Lv ${player.lastLv} • ${mapClassName(player.classId)}` : formatAccountBanStatus(player.accountBan)}
                    </small>
                  </Link>
                );
              })}
            </div>
          </section>

          {selectedPlayer ? (
            <section className="admin-panel">
              <div className="admin-panel__header">
                <strong>Hồ sơ đang chọn</strong>
                <span>{selectedPlayer.username}</span>
              </div>

              <article className="admin-player-card">
                <div className="admin-player-card__summary">
                  <div>
                    <span>Tài khoản</span>
                    <strong>{selectedPlayer.username}</strong>
                  </div>
                  <div>
                    <span>Trạng thái tài khoản</span>
                    <strong>{formatAccountBanStatus(selectedPlayer.accountBan)}</strong>
                  </div>
                  <div>
                    <span>Nhân vật</span>
                    <strong>{selectedPlayer.charname ?? 'Chưa có'}</strong>
                  </div>
                  <div>
                    <span>Trạng thái nhân vật</span>
                    <strong>{formatCharacterBanStatus(selectedPlayer.charBan, selectedPlayer.isOnline)}</strong>
                  </div>
                  <div>
                    <span>Email</span>
                    <strong>{selectedPlayer.email || 'Không có'}</strong>
                  </div>
                  <div>
                    <span>Vị trí online</span>
                    <strong>{selectedPlayer.onlineLocation || 'Không có'}</strong>
                  </div>
                  <div>
                    <span>Cấp / Môn phái</span>
                    <strong>{selectedPlayer.lastLv != null ? `Lv ${selectedPlayer.lastLv} • ${mapClassName(selectedPlayer.classId)}` : 'Chưa có'}</strong>
                  </div>
                  <div>
                    <span>Tài sản</span>
                    <strong>
                      {selectedPlayer.gold != null
                        ? `${selectedPlayer.gold} xu • ${selectedPlayer.luong ?? 0} lượng • ${selectedPlayer.luongLock ?? 0} khóa`
                        : 'Chưa có'}
                    </strong>
                  </div>
                </div>

                <div className="admin-player-card__actions">
                  <div className="admin-actions-row">
                    <form className="admin-form admin-form--inline" action="/api/admin/player-command" method="post">
                      <input type="hidden" name="action" value={selectedPlayer.accountBan === 1 ? 'unlock_account' : 'lock_account'} />
                      <input type="hidden" name="username" value={selectedPlayer.username} />
                      <input type="hidden" name="returnTo" value={returnTo} />
                      <button type="submit" className={`admin-button ${selectedPlayer.accountBan === 1 ? '' : 'admin-button--danger'}`}>
                        {selectedPlayer.accountBan === 1 ? 'Mở khóa tài khoản' : 'Khóa tài khoản'}
                      </button>
                    </form>

                    <form className="admin-form admin-form--inline admin-form--password" action="/api/admin/player-command" method="post">
                      <input type="hidden" name="action" value="reset_password" />
                      <input type="hidden" name="username" value={selectedPlayer.username} />
                      <input type="hidden" name="returnTo" value={returnTo} />
                      <input type="password" name="newPassword" className="admin-input" placeholder="Mật khẩu mới" minLength={6} required />
                      <button type="submit" className="admin-button">Đổi mật khẩu</button>
                    </form>
                  </div>

                  {selectedPlayer.charname ? (
                    <>
                      <div className="admin-actions-row">
                        <form action="/api/admin/player-command" method="post">
                          <input type="hidden" name="action" value="kick_character" />
                          <input type="hidden" name="charname" value={selectedPlayer.charname} />
                          <input type="hidden" name="returnTo" value={returnTo} />
                          <button type="submit" className="admin-button admin-button--ghost">Kick nhân vật</button>
                        </form>

                        <form action="/api/admin/player-command" method="post">
                          <input type="hidden" name="action" value={selectedPlayer.charBan === 1 ? 'unban_character' : 'ban_character'} />
                          <input type="hidden" name="charname" value={selectedPlayer.charname} />
                          <input type="hidden" name="returnTo" value={returnTo} />
                          <button type="submit" className={`admin-button ${selectedPlayer.charBan === 1 ? 'admin-button--ghost' : 'admin-button--danger'}`}>
                            {selectedPlayer.charBan === 1 ? 'Mở khóa nhân vật' : 'Khóa nhân vật'}
                          </button>
                        </form>
                      </div>

                      <form className="admin-form admin-form--compact" action="/api/admin/player-command" method="post">
                        <input type="hidden" name="action" value="grant_resources" />
                        <input type="hidden" name="charname" value={selectedPlayer.charname} />
                        <input type="hidden" name="returnTo" value={returnTo} />
                        <div className="admin-material-reference">
                          <strong>Danh sách ID được phép cộng</strong>
                          <div className="admin-material-reference__groups">
                            {ADMIN_MATERIAL_REFERENCE.map((group) => (
                              <div key={group.group} className="admin-material-reference__group">
                                <span>{group.group}</span>
                                <ul>
                                  {group.items.map((item) => (
                                    <li key={item}>{item}</li>
                                  ))}
                                </ul>
                              </div>
                            ))}
                          </div>
                        </div>
                        <div className="admin-form__grid">
                          <label className="register-form__field"><span>Xu</span><input type="number" min={0} name="xu" defaultValue={0} /></label>
                          <label className="register-form__field"><span>Lượng</span><input type="number" min={0} name="luong" defaultValue={0} /></label>
                          <label className="register-form__field"><span>Lượng khóa</span><input type="number" min={0} name="luongLock" defaultValue={0} /></label>
                          <label className="register-form__field"><span>ID nguyên liệu</span><input type="number" min={0} name="materialId" defaultValue={0} /></label>
                          <label className="register-form__field"><span>Số lượng NL</span><input type="number" min={0} name="materialQty" defaultValue={0} /></label>
                        </div>
                        <button type="submit" className="admin-button">Cộng tài nguyên</button>
                      </form>

                    </>
                  ) : (
                    <div className="admin-empty">
                      Tài khoản này chưa có nhân vật, nên chưa thể kick, khóa nhân vật hoặc cộng tài nguyên theo nhân vật.
                    </div>
                  )}
                </div>
              </article>
            </section>
          ) : null}
        </>
      ) : null}

      {q && players.length === 0 ? (
        <section className="admin-panel">
          <div className="admin-empty">Không tìm thấy tài khoản hoặc nhân vật phù hợp.</div>
        </section>
      ) : null}
    </AdminFrame>
  );
}
