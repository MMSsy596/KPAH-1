import 'server-only';

import type { ResultSetHeader, RowDataPacket } from 'mysql2/promise';

import { getGamePool } from '@/lib/db';

declare global {
  var kpahGiftCodeSchemaReady: Promise<void> | undefined;
}

const REWARD_TYPES = new Set([
  'AOSHOP', 'AOTT', 'BT', 'CHOI', 'DANHHIEU2', 'DB', 'DBT', 'DH', 'DK', 'DKB',
  'DKT', 'DN', 'EGG', 'GEM', 'HKL', 'HLT', 'HOVELAN', 'ITEM', 'LANT', 'MATNA',
  'MATNA2', 'MN', 'MNC', 'MNT', 'NLT', 'NLTMAX', 'PET', 'PETV', 'PHBT', 'PHDS',
  'PHDST', 'PHKT', 'PHMT', 'PHT', 'PHTT', 'PP', 'PP8X', 'PPLUCK', 'PPR', 'PPRK',
  'PPV', 'PPVK', 'PT', 'RONGBANG', 'SUTU', 'TK', 'TKMAX', 'TL', 'TRANGPHUC',
  'VKID', 'VKMAX', 'VKTB', 'VKTBLV', 'VKTBMAX', 'VKTT'
]);

export type GiftCodeRecord = {
  id: number;
  code: string;
  xu: number;
  luong: number;
  luongLock: number;
  item: string;
  limitUse: number;
  isActive: boolean;
  startsAt: string;
  expiresAt: string;
  redeemedCount: number;
  createdAt: string;
  updatedAt: string;
};

export type GiftCodeLogRecord = {
  id: number;
  code: string;
  player: string;
  accountId: number | null;
  characterId: number | null;
  item: string;
  xu: number;
  luong: number;
  luongLock: number;
  status: string;
  errorMessage: string;
  redeemedAt: string;
};

export type GiftCodeInput = {
  code: string;
  xu: number;
  luong: number;
  luongLock: number;
  item: string;
  limitUse: number;
  startsAt: string;
  expiresAt: string;
};

function normalizeCode(value: string): string {
  return value.trim().toLowerCase();
}

function normalizeDate(value: string): string | null {
  const normalized = value.trim();
  if (!normalized) {
    return null;
  }

  const parsed = new Date(normalized);
  if (Number.isNaN(parsed.getTime())) {
    throw new Error('Thời gian hiệu lực không hợp lệ.');
  }

  return normalized.replace('T', ' ').slice(0, 19);
}

function validateRewardItems(value: string): string {
  const normalized = value.trim().toUpperCase();
  if (!normalized) {
    return '';
  }

  const rewards = normalized.split(',').map((item) => item.trim()).filter(Boolean);
  for (const reward of rewards) {
    const parts = reward.split(':');
    const type = parts.shift() ?? '';
    if (!REWARD_TYPES.has(type) || parts.length > 5 || parts.some((part) => !/^-?\d+$/.test(part))) {
      throw new Error(`Cấu hình vật phẩm không hợp lệ: ${reward}`);
    }
  }

  return rewards.join(',');
}

function validateInput(input: GiftCodeInput): GiftCodeInput & { startsAtValue: string | null; expiresAtValue: string | null } {
  const code = normalizeCode(input.code);
  if (!/^[a-z0-9_-]{4,40}$/.test(code)) {
    throw new Error('Gift code chỉ gồm 4-40 ký tự chữ thường, số, gạch ngang hoặc gạch dưới.');
  }

  const values = [input.xu, input.luong, input.luongLock];
  if (values.some((value) => !Number.isInteger(value) || value < 0 || value > 2_000_000_000)) {
    throw new Error('Giá trị tiền thưởng không hợp lệ.');
  }

  if (!Number.isInteger(input.limitUse) || (input.limitUse !== -1 && (input.limitUse < 1 || input.limitUse > 1_000_000))) {
    throw new Error('Giới hạn lượt phải là -1 (không giới hạn) hoặc từ 1 đến 1.000.000.');
  }

  const item = validateRewardItems(input.item);
  if (values.every((value) => value === 0) && !item) {
    throw new Error('Gift code cần có ít nhất một phần quà.');
  }

  const startsAtValue = normalizeDate(input.startsAt);
  const expiresAtValue = normalizeDate(input.expiresAt);
  if (startsAtValue && expiresAtValue && new Date(startsAtValue).getTime() >= new Date(expiresAtValue).getTime()) {
    throw new Error('Thời gian kết thúc phải sau thời gian bắt đầu.');
  }

  return { ...input, code, item, startsAtValue, expiresAtValue };
}

export async function ensureGiftCodeSchema(): Promise<void> {
  if (!globalThis.kpahGiftCodeSchemaReady) {
    globalThis.kpahGiftCodeSchemaReady = (async () => {
      const pool = getGamePool();
      await pool.query(`
        CREATE TABLE IF NOT EXISTS giftcode (
          id BIGINT NOT NULL AUTO_INCREMENT,
          giftcode VARCHAR(64) NOT NULL,
          xu INT NOT NULL DEFAULT 0,
          luong INT NOT NULL DEFAULT 0,
          luongLock INT NOT NULL DEFAULT 0,
          item TEXT DEFAULT NULL,
          expire INT NOT NULL DEFAULT 0,
          limit_use INT NOT NULL DEFAULT -1,
          type INT NOT NULL DEFAULT 0,
          is_active TINYINT(1) NOT NULL DEFAULT 1,
          starts_at DATETIME DEFAULT NULL,
          expires_at DATETIME DEFAULT NULL,
          created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
          updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
          PRIMARY KEY (id),
          UNIQUE KEY uniq_giftcode_code (giftcode),
          KEY idx_giftcode_status_time (is_active, starts_at, expires_at)
        ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci
      `);
      await pool.query(`
        CREATE TABLE IF NOT EXISTS giftcode_log (
          id BIGINT NOT NULL AUTO_INCREMENT,
          giftcode VARCHAR(64) NOT NULL,
          player VARCHAR(45) NOT NULL,
          account_id INT DEFAULT NULL,
          character_id INT DEFAULT NULL,
          item TEXT DEFAULT NULL,
          xu INT NOT NULL DEFAULT 0,
          luong INT NOT NULL DEFAULT 0,
          luongK INT NOT NULL DEFAULT 0,
          status ENUM('reserved','success','failed') NOT NULL DEFAULT 'success',
          error_message VARCHAR(255) DEFAULT NULL,
          redeemed_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
          PRIMARY KEY (id),
          UNIQUE KEY uniq_giftcode_player (giftcode, player),
          KEY idx_giftcode_log_time (redeemed_at),
          KEY idx_giftcode_log_account (account_id, redeemed_at)
        ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci
      `);
    })();
  }

  await globalThis.kpahGiftCodeSchemaReady;
}

export async function listGiftCodes(query = ''): Promise<GiftCodeRecord[]> {
  await ensureGiftCodeSchema();
  const pool = getGamePool();
  const normalizedQuery = normalizeCode(query);
  const [rows] = await pool.query<RowDataPacket[]>(
    `
      SELECT g.*,
        (SELECT COUNT(*) FROM giftcode_log l WHERE l.giftcode = g.giftcode AND l.status = 'success') AS redeemed_count
      FROM giftcode g
      WHERE ? = '' OR g.giftcode LIKE ?
      ORDER BY g.id DESC
      LIMIT 200
    `,
    [normalizedQuery, `%${normalizedQuery}%`]
  );

  return rows.map((row) => ({
    id: Number(row.id),
    code: String(row.giftcode ?? ''),
    xu: Number(row.xu ?? 0),
    luong: Number(row.luong ?? 0),
    luongLock: Number(row.luongLock ?? 0),
    item: String(row.item ?? ''),
    limitUse: Number(row.limit_use ?? -1),
    isActive: Number(row.is_active ?? 0) === 1,
    startsAt: row.starts_at ? String(row.starts_at).replace(' ', 'T').slice(0, 16) : '',
    expiresAt: row.expires_at ? String(row.expires_at).replace(' ', 'T').slice(0, 16) : '',
    redeemedCount: Number(row.redeemed_count ?? 0),
    createdAt: String(row.created_at ?? ''),
    updatedAt: String(row.updated_at ?? '')
  }));
}

export async function listGiftCodeLogs(limit = 100): Promise<GiftCodeLogRecord[]> {
  await ensureGiftCodeSchema();
  const pool = getGamePool();
  const [rows] = await pool.query<RowDataPacket[]>(
    'SELECT * FROM giftcode_log ORDER BY id DESC LIMIT ?',
    [Math.max(1, Math.min(limit, 500))]
  );

  return rows.map((row) => ({
    id: Number(row.id),
    code: String(row.giftcode ?? ''),
    player: String(row.player ?? ''),
    accountId: row.account_id == null ? null : Number(row.account_id),
    characterId: row.character_id == null ? null : Number(row.character_id),
    item: String(row.item ?? ''),
    xu: Number(row.xu ?? 0),
    luong: Number(row.luong ?? 0),
    luongLock: Number(row.luongK ?? 0),
    status: String(row.status ?? ''),
    errorMessage: String(row.error_message ?? ''),
    redeemedAt: String(row.redeemed_at ?? '')
  }));
}

export async function createGiftCode(input: GiftCodeInput): Promise<{ id: number; code: string }> {
  const validated = validateInput(input);
  await ensureGiftCodeSchema();
  const pool = getGamePool();
  const [result] = await pool.query<ResultSetHeader>(
    `
      INSERT INTO giftcode (giftcode, xu, luong, luongLock, item, limit_use, type, is_active, starts_at, expires_at)
      VALUES (?, ?, ?, ?, ?, ?, 0, 1, ?, ?)
    `,
    [validated.code, validated.xu, validated.luong, validated.luongLock, validated.item || null,
      validated.limitUse, validated.startsAtValue, validated.expiresAtValue]
  );
  return { id: result.insertId, code: validated.code };
}

export async function updateGiftCode(id: number, input: GiftCodeInput): Promise<string> {
  if (!Number.isInteger(id) || id <= 0) {
    throw new Error('Gift code không hợp lệ.');
  }
  const validated = validateInput(input);
  await ensureGiftCodeSchema();
  const pool = getGamePool();
  const [currentRows] = await pool.query<RowDataPacket[]>(
    `SELECT g.giftcode, (SELECT COUNT(*) FROM giftcode_log l WHERE l.giftcode = g.giftcode) AS log_count
     FROM giftcode g WHERE g.id = ? LIMIT 1`,
    [id]
  );
  if (!currentRows[0]) {
    throw new Error('Không tìm thấy gift code cần cập nhật.');
  }
  if (String(currentRows[0].giftcode) !== validated.code && Number(currentRows[0].log_count ?? 0) > 0) {
    throw new Error('Không thể đổi tên mã đã có lịch sử sử dụng.');
  }
  const [result] = await pool.query<ResultSetHeader>(
    `
      UPDATE giftcode
      SET giftcode = ?, xu = ?, luong = ?, luongLock = ?, item = ?, limit_use = ?, starts_at = ?, expires_at = ?
      WHERE id = ?
    `,
    [validated.code, validated.xu, validated.luong, validated.luongLock, validated.item || null,
      validated.limitUse, validated.startsAtValue, validated.expiresAtValue, id]
  );
  if (result.affectedRows === 0) {
    throw new Error('Không tìm thấy gift code cần cập nhật.');
  }
  return validated.code;
}

export async function setGiftCodeActive(id: number, active: boolean): Promise<string> {
  await ensureGiftCodeSchema();
  const pool = getGamePool();
  const [rows] = await pool.query<RowDataPacket[]>('SELECT giftcode FROM giftcode WHERE id = ? LIMIT 1', [id]);
  if (!rows[0]) {
    throw new Error('Không tìm thấy gift code.');
  }
  await pool.query('UPDATE giftcode SET is_active = ? WHERE id = ?', [active ? 1 : 0, id]);
  return String(rows[0].giftcode);
}

export async function deleteUnusedGiftCode(id: number): Promise<string> {
  await ensureGiftCodeSchema();
  const pool = getGamePool();
  const [rows] = await pool.query<RowDataPacket[]>(
    `SELECT g.giftcode, (SELECT COUNT(*) FROM giftcode_log l WHERE l.giftcode = g.giftcode) AS log_count
     FROM giftcode g WHERE g.id = ? LIMIT 1`,
    [id]
  );
  if (!rows[0]) {
    throw new Error('Không tìm thấy gift code.');
  }
  if (Number(rows[0].log_count ?? 0) > 0) {
    throw new Error('Gift code đã có lịch sử sử dụng; hãy tắt mã thay vì xóa.');
  }
  await pool.query('DELETE FROM giftcode WHERE id = ?', [id]);
  return String(rows[0].giftcode);
}
