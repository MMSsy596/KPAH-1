import 'server-only';

import {
  createHmac,
  randomBytes,
  scryptSync,
  timingSafeEqual
} from 'node:crypto';
import { existsSync, mkdirSync, writeFileSync } from 'node:fs';
import path from 'node:path';

import { cookies } from 'next/headers';
import { redirect } from 'next/navigation';
import { NextResponse } from 'next/server';
import type { ResultSetHeader, RowDataPacket } from 'mysql2/promise';

import { ensurePendingRegistrationSchema, mysqlPasswordHash } from '@/lib/account-register';
import { getAccountPool, readGameDbConfig, readGameServerSettings } from '@/lib/db';
import {
  type HomePost,
  deleteHomePost,
  ensureHomePostSchema,
  insertHomePost,
  isHomePostCategory,
  listHomePosts
} from '@/lib/home-posts';
import { ensureAnnouncementSchema } from '@/lib/player-portal';

export const ADMIN_ROUTE_SEGMENT = 'noi-vu-kpah-73x9';
export const ADMIN_BASE_PATH = `/${ADMIN_ROUTE_SEGMENT}`;
export const ADMIN_LOGIN_PATH = `${ADMIN_BASE_PATH}/login`;
export const ADMIN_DASHBOARD_PATH = `${ADMIN_BASE_PATH}/tong-quan`;
export const ADMIN_COOKIE_NAME = 'kpah_admin_session';

const ADMIN_SESSION_TTL_MS = 1000 * 60 * 60 * 12;

declare global {
  var kpahAdminSchemaReady: Promise<void> | undefined;
}

export type AdminSession = {
  adminId: number;
  username: string;
  displayName: string;
  role: string;
  issuedAt: number;
  expiresAt: number;
};

export type AdminUser = {
  id: number;
  username: string;
  displayName: string;
  role: string;
  allowedIps: string;
  isActive: boolean;
};

export type AdminLogEntry = {
  id: number;
  adminUsername: string;
  actionType: string;
  targetType: string;
  targetValue: string;
  detailText: string;
  requestIp: string;
  success: boolean;
  resultMessage: string;
  createdAt: string | null;
};

export type PendingRegistrationItem = {
  id: number;
  username: string;
  email: string;
  phone: string;
  requestIp: string;
  userAgent: string;
  requestedAt: string | null;
};

export type StoredAnnouncement = {
  id: number;
  title: string;
  content: string;
  publishedAt: string | null;
};

export type StoredHomePost = HomePost;

export type AdminOnlinePlayer = {
  index: number;
  username: string;
  charname: string;
  level: string;
  xu: string;
  luong: string;
  luongLock: string;
  location: string;
};

export type AdminPlayerRecord = {
  accountId: number;
  username: string;
  email: string;
  accountBan: number;
  regdate: string | null;
  charId: number | null;
  charname: string | null;
  lastLv: number | null;
  classId: number | null;
  gold: number | null;
  luong: number | null;
  luongLock: number | null;
  xp: number | null;
  charBan: number | null;
  lastLog: string | null;
  isOnline: boolean;
  onlineLocation: string | null;
};

export type AdminEventSetting = {
  key: string;
  label: string;
  value: number;
};

export type AdminEventManagement = {
  events: AdminEventSetting[];
  luckyBag: Record<string, string>;
};

export type AdminGrindingSettings = {
  dropRatePercent: number;
  monsterDamagePercent: number;
  monsterHpPercent: number;
  expPercent: number;
  monsterDensityPercent: number;
};

export type AdminDashboardData = {
  serverStatus: {
    onlinePlayers: number;
    playerLimit: number;
    serverState: string;
    uptimeText: string;
    memoryUsedMb: number;
    memoryTotalMb: number;
    maintenanceScheduled: boolean;
    maintenanceRemainingMinutes: number;
  };
  pendingCount: number;
  announcementCount: number;
  activeAdminCount: number;
  recentPending: PendingRegistrationItem[];
  recentLogs: AdminLogEntry[];
};

function toBase64Url(value: string): string {
  return Buffer.from(value, 'utf8').toString('base64url');
}

function fromBase64Url(value: string): string {
  return Buffer.from(value, 'base64url').toString('utf8');
}

function getAdminSessionSecret(): string {
  const settings = readGameServerSettings();

  return (
    process.env.KPAH_ADMIN_SESSION_SECRET
    || settings.get('sv.localAdminToken')
    || settings.get('sv.clientAuthSecret')
    || 'kpah-admin-session-secret'
  );
}

function useSecureCookies(): boolean {
  const configured = process.env.KPAH_COOKIE_SECURE;
  return configured == null ? process.env.NODE_ENV !== 'development' : configured === '1';
}

function signAdminPayload(payload: string): string {
  return createHmac('sha256', getAdminSessionSecret()).update(payload).digest('base64url');
}

function hashAdminPassword(password: string): string {
  const salt = randomBytes(16).toString('base64url');
  const derived = scryptSync(password, salt, 64).toString('base64url');
  return `${salt}:${derived}`;
}

function verifyAdminPassword(password: string, storedHash: string): boolean {
  const [salt, storedDerived] = storedHash.split(':');

  if (!salt || !storedDerived) {
    return false;
  }

  const actualDerived = scryptSync(password, salt, 64).toString('base64url');
  const left = Buffer.from(actualDerived, 'utf8');
  const right = Buffer.from(storedDerived, 'utf8');

  if (left.length !== right.length) {
    return false;
  }

  return timingSafeEqual(left, right);
}

function resolveBootstrapFilePath(): string {
  return process.env.KPAH_ADMIN_BOOTSTRAP_PATH
    || path.resolve(process.env.KPAH_RUNTIME_ROOT || path.resolve(process.cwd(), '..', '..'), 'ops', 'admin-bootstrap.txt');
}

function decodePropertyValue(value: string): string {
  return value
    .replace(/\\u([0-9a-fA-F]{4})/g, (_, code: string) => String.fromCharCode(parseInt(code, 16)))
    .replace(/\\:/g, ':')
    .replace(/\\=/g, '=')
    .replace(/\\\\/g, '\\');
}

function parseProperties(content: string): Map<string, string> {
  const values = new Map<string, string>();

  for (const rawLine of content.split(/\r?\n/)) {
    if (!rawLine || rawLine.startsWith('#')) {
      continue;
    }

    const separatorIndex = rawLine.indexOf('=');
    if (separatorIndex <= 0) {
      continue;
    }

    const key = rawLine.slice(0, separatorIndex).trim();
    const value = rawLine.slice(separatorIndex + 1).trim();
    values.set(key, decodePropertyValue(value));
  }

  return values;
}

function matchAllowedIp(allowedIps: string, requestIp: string): boolean {
  const normalizedRequestIp = requestIp.trim().toLowerCase();
  const rules = allowedIps
    .split(/[,\r\n]+/)
    .map((item) => item.trim().toLowerCase())
    .filter(Boolean);

  if (rules.length === 0) {
    return true;
  }

  return rules.some((rule) => rule === '*' || rule === normalizedRequestIp);
}

function normalizeText(value: unknown): string {
  return typeof value === 'string' ? value.trim() : '';
}

function normalizeUsername(value: string): string {
  return value.trim().toLowerCase();
}

function createAdminSession(user: AdminUser): AdminSession {
  const issuedAt = Date.now();

  return {
    adminId: user.id,
    username: user.username,
    displayName: user.displayName,
    role: user.role,
    issuedAt,
    expiresAt: issuedAt + ADMIN_SESSION_TTL_MS
  };
}

function encodeAdminSession(session: AdminSession): string {
  const payload = toBase64Url(JSON.stringify(session));
  const signature = signAdminPayload(payload);
  return `${payload}.${signature}`;
}

function decodeAdminSession(token: string | undefined | null): AdminSession | null {
  if (!token) {
    return null;
  }

  const [payload, signature] = token.split('.');
  if (!payload || !signature) {
    return null;
  }

  const expectedSignature = signAdminPayload(payload);
  const left = Buffer.from(signature, 'utf8');
  const right = Buffer.from(expectedSignature, 'utf8');

  if (left.length !== right.length || !timingSafeEqual(left, right)) {
    return null;
  }

  try {
    const parsed = JSON.parse(fromBase64Url(payload)) as Partial<AdminSession>;

    if (
      typeof parsed.adminId !== 'number'
      || typeof parsed.username !== 'string'
      || typeof parsed.displayName !== 'string'
      || typeof parsed.role !== 'string'
      || typeof parsed.issuedAt !== 'number'
      || typeof parsed.expiresAt !== 'number'
    ) {
      return null;
    }

    if (parsed.expiresAt <= Date.now()) {
      return null;
    }

    return {
      adminId: parsed.adminId,
      username: parsed.username,
      displayName: parsed.displayName,
      role: parsed.role,
      issuedAt: parsed.issuedAt,
      expiresAt: parsed.expiresAt
    };
  } catch {
    return null;
  }
}

async function ensureBootstrapAdmin(): Promise<void> {
  const pool = getAccountPool();
  const [rows] = await pool.query<RowDataPacket[]>(
    `
      SELECT COUNT(*) AS total
      FROM web_admin_users
      WHERE is_active = 1
    `
  );

  if (Number(rows[0]?.total ?? 0) > 0) {
    return;
  }

  const username = 'masteradmin';
  const password = randomBytes(12).toString('base64url');
  const passwordHash = hashAdminPassword(password);

  await pool.query(
    `
      INSERT INTO web_admin_users (
        username,
        password_hash,
        display_name,
        role,
        allowed_ips,
        is_active
      )
      VALUES (?, ?, ?, 'super_admin', '', 1)
    `,
    [username, passwordHash, 'Quản trị tối cao']
  );

  const bootstrapPath = resolveBootstrapFilePath();
  mkdirSync(path.dirname(bootstrapPath), { recursive: true });

  const content = [
    'THONG TIN QUAN TRI KPAH',
    `Route admin: ${ADMIN_BASE_PATH}`,
    `Dang nhap: ${ADMIN_LOGIN_PATH}`,
    `Tai khoan: ${username}`,
    `Mat khau tam thoi: ${password}`,
    `Tao luc: ${new Date().toISOString()}`,
    'Vui long dang nhap va doi mat khau ngay sau khi vao he thong.'
  ].join('\r\n');

  writeFileSync(bootstrapPath, content, 'utf8');
}

export async function ensureAdminSchema(): Promise<void> {
  if (!globalThis.kpahAdminSchemaReady) {
    globalThis.kpahAdminSchemaReady = (async () => {
      const pool = getAccountPool();

      await pool.query(`
        CREATE TABLE IF NOT EXISTS web_admin_users (
          id BIGINT NOT NULL AUTO_INCREMENT,
          username VARCHAR(40) NOT NULL,
          password_hash VARCHAR(255) NOT NULL,
          display_name VARCHAR(100) NOT NULL,
          role VARCHAR(40) NOT NULL DEFAULT 'super_admin',
          allowed_ips VARCHAR(255) NOT NULL DEFAULT '',
          is_active TINYINT(1) NOT NULL DEFAULT 1,
          last_login_at DATETIME DEFAULT NULL,
          created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
          updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
          PRIMARY KEY (id),
          UNIQUE KEY uniq_web_admin_username (username),
          KEY idx_web_admin_active (is_active, username)
        ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci
      `);

      await pool.query(`
        CREATE TABLE IF NOT EXISTS web_admin_action_logs (
          id BIGINT NOT NULL AUTO_INCREMENT,
          admin_user_id BIGINT DEFAULT NULL,
          admin_username VARCHAR(40) NOT NULL DEFAULT '',
          action_type VARCHAR(80) NOT NULL,
          target_type VARCHAR(80) NOT NULL DEFAULT '',
          target_value VARCHAR(160) NOT NULL DEFAULT '',
          detail_text TEXT DEFAULT NULL,
          request_ip VARCHAR(64) NOT NULL DEFAULT '',
          success TINYINT(1) NOT NULL DEFAULT 1,
          result_message VARCHAR(255) NOT NULL DEFAULT '',
          created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
          PRIMARY KEY (id),
          KEY idx_admin_logs_time (created_at),
          KEY idx_admin_logs_actor (admin_username, created_at),
          KEY idx_admin_logs_action (action_type, created_at)
        ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci
      `);

      await ensureAnnouncementSchema();
      await ensureHomePostSchema();
      await ensurePendingRegistrationSchema();
      await ensureBootstrapAdmin();
    })();
  }

  await globalThis.kpahAdminSchemaReady;
}

export function getClientIpFromHeaders(headers: Headers): string {
  const forwarded = headers.get('cf-connecting-ip')
    ?? headers.get('x-real-ip')
    ?? headers.get('x-forwarded-for')
    ?? 'unknown';

  return forwarded.split(',')[0]?.trim().slice(0, 64) || 'unknown';
}

export async function getCurrentAdminSession(): Promise<AdminSession | null> {
  const cookieStore = await cookies();
  return decodeAdminSession(cookieStore.get(ADMIN_COOKIE_NAME)?.value);
}

export function attachAdminSessionCookie(response: NextResponse, session: AdminSession): void {
  response.cookies.set({
    name: ADMIN_COOKIE_NAME,
    value: encodeAdminSession(session),
    httpOnly: true,
    sameSite: 'strict',
    secure: useSecureCookies(),
    path: '/',
    expires: new Date(session.expiresAt)
  });
}

export function clearAdminSessionCookie(response: NextResponse): void {
  response.cookies.set({
    name: ADMIN_COOKIE_NAME,
    value: '',
    httpOnly: true,
    sameSite: 'strict',
    secure: useSecureCookies(),
    path: '/',
    expires: new Date(0)
  });
}

export async function getAdminUserById(adminId: number): Promise<AdminUser | null> {
  await ensureAdminSchema();

  const pool = getAccountPool();
  const [rows] = await pool.query<RowDataPacket[]>(
    `
      SELECT id, username, display_name, role, allowed_ips, is_active
      FROM web_admin_users
      WHERE id = ?
      LIMIT 1
    `,
    [adminId]
  );

  const row = rows[0];
  if (!row || Number(row.is_active ?? 0) !== 1) {
    return null;
  }

  return {
    id: Number(row.id),
    username: String(row.username),
    displayName: String(row.display_name ?? row.username),
    role: String(row.role ?? 'super_admin'),
    allowedIps: String(row.allowed_ips ?? ''),
    isActive: Number(row.is_active ?? 0) === 1
  };
}

export async function requireAdminSession(): Promise<{ session: AdminSession; user: AdminUser }> {
  await ensureAdminSchema();

  const session = await getCurrentAdminSession();
  if (!session) {
    redirect(ADMIN_LOGIN_PATH);
  }

  const user = await getAdminUserById(session.adminId);
  if (!user) {
    redirect(ADMIN_LOGIN_PATH);
  }

  return {
    session,
    user
  };
}

export async function authenticateAdmin(
  username: string,
  password: string,
  requestIp: string
): Promise<{ ok: true; user: AdminUser } | { ok: false; message: string }> {
  await ensureAdminSchema();

  const normalizedUsername = normalizeUsername(username);
  const normalizedPassword = normalizeText(password);

  if (!/^[a-z0-9_]{4,40}$/.test(normalizedUsername)) {
    return { ok: false, message: 'Tên đăng nhập admin không hợp lệ.' };
  }

  if (normalizedPassword.length < 8) {
    return { ok: false, message: 'Mật khẩu admin không hợp lệ.' };
  }

  const pool = getAccountPool();
  const [rows] = await pool.query<RowDataPacket[]>(
    `
      SELECT id, username, password_hash, display_name, role, allowed_ips, is_active
      FROM web_admin_users
      WHERE LOWER(username) = LOWER(?)
      LIMIT 1
    `,
    [normalizedUsername]
  );

  const row = rows[0];

  if (!row || Number(row.is_active ?? 0) !== 1) {
    return { ok: false, message: 'Thông tin đăng nhập admin không chính xác.' };
  }

  if (!verifyAdminPassword(normalizedPassword, String(row.password_hash ?? ''))) {
    return { ok: false, message: 'Thông tin đăng nhập admin không chính xác.' };
  }

  if (!matchAllowedIp(String(row.allowed_ips ?? ''), requestIp)) {
    return { ok: false, message: 'IP hiện tại không nằm trong danh sách cho phép.' };
  }

  await pool.query(
    `
      UPDATE web_admin_users
      SET last_login_at = NOW()
      WHERE id = ?
    `,
    [row.id]
  );

  return {
    ok: true,
    user: {
      id: Number(row.id),
      username: String(row.username),
      displayName: String(row.display_name ?? row.username),
      role: String(row.role ?? 'super_admin'),
      allowedIps: String(row.allowed_ips ?? ''),
      isActive: true
    }
  };
}

export async function logAdminAction(input: {
  adminUserId?: number | null;
  adminUsername: string;
  actionType: string;
  targetType?: string;
  targetValue?: string;
  detailText?: string;
  requestIp?: string;
  success: boolean;
  resultMessage: string;
}): Promise<void> {
  await ensureAdminSchema();

  const pool = getAccountPool();
  await pool.query(
    `
      INSERT INTO web_admin_action_logs (
        admin_user_id,
        admin_username,
        action_type,
        target_type,
        target_value,
        detail_text,
        request_ip,
        success,
        result_message
      )
      VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
    `,
    [
      input.adminUserId ?? null,
      input.adminUsername,
      input.actionType,
      input.targetType ?? '',
      input.targetValue ?? '',
      input.detailText ?? '',
      input.requestIp ?? '',
      input.success ? 1 : 0,
      input.resultMessage
    ]
  );
}

async function callLocalAdmin(
  endpoint: string,
  options?: {
    method?: 'GET' | 'POST';
    form?: Record<string, string>;
  }
): Promise<{ ok: boolean; message: string; props: Map<string, string> }> {
  const settings = readGameServerSettings();
  const host = settings.get('sv.localAdminHost') ?? '127.0.0.1';
  const port = settings.get('sv.localAdminPort') ?? '18023';
  const token = settings.get('sv.localAdminToken') ?? '';

  const method = options?.method ?? 'POST';
  const headers: Record<string, string> = {
    'X-Admin-Token': token
  };

  let body: string | undefined;
  if (method === 'POST') {
    headers['Content-Type'] = 'application/x-www-form-urlencoded;charset=UTF-8';
    body = new URLSearchParams(options?.form ?? {}).toString();
  }

  const response = await fetch(`http://${host}:${port}${endpoint}`, {
    method,
    headers,
    body,
    cache: 'no-store'
  });

  const props = parseProperties(await response.text());

  return {
    ok: props.get('ok') === '1',
    message: props.get('message') ?? '',
    props
  };
}

export async function fetchAdminStatus(): Promise<AdminDashboardData['serverStatus']> {
  try {
    const result = await callLocalAdmin('/api/status', { method: 'GET' });
    const props = result.props;

    return {
      onlinePlayers: Number(props.get('online_players') ?? 0),
      playerLimit: Number(props.get('player_limit') ?? 0),
      serverState: props.get('server_state') ?? 'unknown',
      uptimeText: props.get('uptime_text') ?? '',
      memoryUsedMb: Number(props.get('memory_used_mb') ?? 0),
      memoryTotalMb: Number(props.get('memory_total_mb') ?? 0),
      maintenanceScheduled: props.get('maintenance_scheduled') === '1',
      maintenanceRemainingMinutes: Number(props.get('maintenance_remaining_minutes') ?? 0)
    };
  } catch {
    return {
      onlinePlayers: 0,
      playerLimit: 0,
      serverState: 'local-admin-unreachable',
      uptimeText: 'Không lấy được dữ liệu',
      memoryUsedMb: 0,
      memoryTotalMb: 0,
      maintenanceScheduled: false,
      maintenanceRemainingMinutes: 0
    };
  }
}

export async function fetchOnlinePlayersDetailed(): Promise<AdminOnlinePlayer[]> {
  try {
    const result = await callLocalAdmin('/api/players/online', { method: 'GET' });
    const props = result.props;
    const total = Number(props.get('player_count') ?? 0);
    const players: AdminOnlinePlayer[] = [];

    for (let index = 0; index < total; index += 1) {
      players.push({
        index: Number(props.get(`player_${index}_index`) ?? index + 1),
        username: props.get(`player_${index}_username`) ?? '',
        charname: props.get(`player_${index}_name`) ?? '',
        level: props.get(`player_${index}_level`) ?? '',
        xu: props.get(`player_${index}_xu`) ?? '0',
        luong: props.get(`player_${index}_luong`) ?? '0',
        luongLock: props.get(`player_${index}_luong_lock`) ?? '0',
        location: props.get(`player_${index}_location`) ?? ''
      });
    }

    return players;
  } catch {
    return [];
  }
}

export async function fetchEventManagement(): Promise<AdminEventManagement> {
  try {
    const [eventResult, luckyBagResult] = await Promise.all([
      callLocalAdmin('/api/config/events', { method: 'GET' }),
      callLocalAdmin('/api/config/lucky-bag', { method: 'GET' })
    ]);
    const eventCount = Number(eventResult.props.get('event_count') ?? 0);
    const events: AdminEventSetting[] = [];
    for (let index = 0; index < eventCount; index += 1) {
      events.push({
        key: eventResult.props.get(`event_${index}_key`) ?? '',
        label: eventResult.props.get(`event_${index}_label`) ?? '',
        value: Number(eventResult.props.get(`event_${index}_value`) ?? -1)
      });
    }

    const luckyBag: Record<string, string> = {};
    for (const key of [
      'drop_rate_percent', 'weight_luong', 'weight_luong_lock', 'weight_xu', 'weight_hp', 'weight_mp',
      'amount_luong_min', 'amount_luong_lock_min', 'amount_xu_min', 'amount_hp_min', 'amount_mp_min',
      'amount_luong_max', 'amount_luong_lock_max', 'amount_xu_max', 'amount_hp_max', 'amount_mp_max',
      'max_open_per_day'
    ]) {
      luckyBag[key] = luckyBagResult.props.get(key) ?? '0';
    }

    return { events, luckyBag };
  } catch {
    return { events: [], luckyBag: {} };
  }
}

export async function fetchGrindingSettings(): Promise<AdminGrindingSettings | null> {
  try {
    const result = await callLocalAdmin('/api/config/grinding', { method: 'GET' });
    if (!result.ok) {
      return null;
    }
    return {
      dropRatePercent: Number(result.props.get('drop_rate_percent') ?? 100),
      monsterDamagePercent: Number(result.props.get('monster_damage_percent') ?? 100),
      monsterHpPercent: Number(result.props.get('monster_hp_percent') ?? 100),
      expPercent: Number(result.props.get('exp_percent') ?? 100),
      monsterDensityPercent: Number(result.props.get('monster_density_percent') ?? 100)
    };
  } catch {
    return null;
  }
}

export async function listPendingRegistrations(limit = 50): Promise<PendingRegistrationItem[]> {
  await ensureAdminSchema();

  const pool = getAccountPool();
  const [rows] = await pool.query<RowDataPacket[]>(
    `
      SELECT id, username, email, phone, request_ip, user_agent, requested_at
      FROM pending_account_registrations
      WHERE status = 'pending'
      ORDER BY requested_at ASC
      LIMIT ?
    `,
    [limit]
  );

  return rows.map((row) => ({
    id: Number(row.id),
    username: String(row.username ?? ''),
    email: String(row.email ?? ''),
    phone: String(row.phone ?? ''),
    requestIp: String(row.request_ip ?? ''),
    userAgent: String(row.user_agent ?? ''),
    requestedAt: row.requested_at ? String(row.requested_at) : null
  }));
}

export async function reviewPendingRegistration(input: {
  id: number;
  decision: 'approve' | 'reject';
  note: string;
  admin: AdminUser;
  requestIp: string;
}): Promise<{ ok: boolean; message: string }> {
  await ensureAdminSchema();

  const pool = getAccountPool();
  const connection = await pool.getConnection();

  try {
    await connection.beginTransaction();

    const [pendingRows] = await connection.query<RowDataPacket[]>(
      `
        SELECT *
        FROM pending_account_registrations
        WHERE id = ?
        FOR UPDATE
      `,
      [input.id]
    );

    const pending = pendingRows[0];
    if (!pending) {
      await connection.rollback();
      return { ok: false, message: 'Không tìm thấy yêu cầu đăng ký.' };
    }

    if (String(pending.status) !== 'pending') {
      await connection.rollback();
      return { ok: false, message: 'Yêu cầu này đã được xử lý trước đó.' };
    }

    if (input.decision === 'approve') {
      const [existingRows] = await connection.query<RowDataPacket[]>(
        `
          SELECT id
          FROM team_user
          WHERE LOWER(username) = LOWER(?) OR LOWER(email) = LOWER(?)
          LIMIT 1
        `,
        [pending.username, pending.email]
      );

      if (existingRows.length > 0) {
        await connection.rollback();
        return { ok: false, message: 'Tài khoản hoặc email đã tồn tại trong hệ thống.' };
      }

      const [insertResult] = await connection.query<ResultSetHeader>(
        `
          INSERT INTO team_user (
            username,
            password,
            phone,
            regdate,
            ban,
            provider,
            fromgame,
            email
          )
          VALUES (?, ?, ?, NOW(), 0, 0, 0, ?)
        `,
        [
          pending.username,
          pending.password_hash,
          pending.phone ?? '',
          pending.email
        ]
      );

      await connection.query(
        `
          UPDATE pending_account_registrations
          SET status = 'approved',
              review_note = ?,
              approved_account_id = ?,
              reviewed_at = NOW()
          WHERE id = ?
        `,
        [input.note || 'Đã kích hoạt từ trang admin.', insertResult.insertId, input.id]
      );

      await connection.commit();

      await logAdminAction({
        adminUserId: input.admin.id,
        adminUsername: input.admin.username,
        actionType: 'approve_pending_registration',
        targetType: 'account_request',
        targetValue: String(pending.username),
        detailText: input.note,
        requestIp: input.requestIp,
        success: true,
        resultMessage: 'Đã kích hoạt tài khoản đăng ký chờ duyệt.'
      });

      return { ok: true, message: 'Đã kích hoạt tài khoản và đưa vào hệ thống chính.' };
    }

    await connection.query(
      `
        UPDATE pending_account_registrations
        SET status = 'rejected',
            review_note = ?,
            reviewed_at = NOW()
        WHERE id = ?
      `,
      [input.note || 'Yêu cầu không được duyệt.', input.id]
    );

    await connection.commit();

    await logAdminAction({
      adminUserId: input.admin.id,
      adminUsername: input.admin.username,
      actionType: 'reject_pending_registration',
      targetType: 'account_request',
      targetValue: String(pending.username),
      detailText: input.note,
      requestIp: input.requestIp,
      success: true,
      resultMessage: 'Đã từ chối yêu cầu đăng ký.'
    });

    return { ok: true, message: 'Đã từ chối yêu cầu đăng ký.' };
  } catch (error) {
    await connection.rollback();

    await logAdminAction({
      adminUserId: input.admin.id,
      adminUsername: input.admin.username,
      actionType: `${input.decision}_pending_registration`,
      targetType: 'account_request',
      targetValue: String(input.id),
      detailText: input.note,
      requestIp: input.requestIp,
      success: false,
      resultMessage: error instanceof Error ? error.message : 'Lỗi chưa xác định'
    });

    return { ok: false, message: 'Không thể xử lý yêu cầu đăng ký lúc này.' };
  } finally {
    connection.release();
  }
}

export async function listStoredAnnouncements(limit = 30): Promise<StoredAnnouncement[]> {
  await ensureAdminSchema();

  const pool = getAccountPool();
  const [rows] = await pool.query<RowDataPacket[]>(
    `
      SELECT id, title, content, published_at
      FROM web_admin_announcements
      ORDER BY display_order DESC, published_at DESC, id DESC
      LIMIT ?
    `,
    [limit]
  );

  return rows.map((row) => ({
    id: Number(row.id),
    title: String(row.title ?? ''),
    content: String(row.content ?? ''),
    publishedAt: row.published_at ? String(row.published_at) : null
  }));
}

export async function createStoredAnnouncement(input: {
  title: string;
  content: string;
  admin: AdminUser;
  requestIp: string;
}): Promise<{ ok: boolean; message: string }> {
  await ensureAdminSchema();

  const title = normalizeText(input.title);
  const content = normalizeText(input.content);

  if (title.length < 4) {
    return { ok: false, message: 'Tiêu đề thông báo quá ngắn.' };
  }

  if (content.length < 8) {
    return { ok: false, message: 'Nội dung thông báo quá ngắn.' };
  }

  const pool = getAccountPool();
  await pool.query(
    `
      INSERT INTO web_admin_announcements (title, content, is_published, display_order)
      VALUES (?, ?, 1, 0)
    `,
    [title, content]
  );

  await logAdminAction({
    adminUserId: input.admin.id,
    adminUsername: input.admin.username,
    actionType: 'create_announcement',
    targetType: 'announcement',
    targetValue: title,
    detailText: content,
    requestIp: input.requestIp,
    success: true,
    resultMessage: 'Đã tạo thông báo mới.'
  });

  return { ok: true, message: 'Đã đăng thông báo mới lên khu tài khoản.' };
}

export async function deleteStoredAnnouncement(input: {
  id: number;
  admin: AdminUser;
  requestIp: string;
}): Promise<{ ok: boolean; message: string }> {
  await ensureAdminSchema();

  const pool = getAccountPool();
  const [rows] = await pool.query<RowDataPacket[]>(
    `
      SELECT title
      FROM web_admin_announcements
      WHERE id = ?
      LIMIT 1
    `,
    [input.id]
  );

  if (!rows[0]) {
    return { ok: false, message: 'Không tìm thấy thông báo cần xóa.' };
  }

  await pool.query('DELETE FROM web_admin_announcements WHERE id = ? LIMIT 1', [input.id]);

  await logAdminAction({
    adminUserId: input.admin.id,
    adminUsername: input.admin.username,
    actionType: 'delete_announcement',
    targetType: 'announcement',
    targetValue: String(rows[0].title ?? input.id),
    requestIp: input.requestIp,
    success: true,
    resultMessage: 'Đã xóa thông báo.'
  });

  return { ok: true, message: 'Đã xóa thông báo.' };
}

export async function listStoredHomePosts(limit = 100): Promise<StoredHomePost[]> {
  await ensureAdminSchema();
  return listHomePosts(limit);
}

export async function createStoredHomePost(input: {
  category: string;
  title: string;
  content: string;
  displayOrder: number;
  admin: AdminUser;
  requestIp: string;
}): Promise<{ ok: boolean; message: string }> {
  await ensureAdminSchema();

  const category = normalizeText(input.category);
  const title = normalizeText(input.title);
  const content = normalizeText(input.content);
  const displayOrder = Number.isFinite(input.displayOrder) ? input.displayOrder : 0;

  if (!isHomePostCategory(category)) {
    return { ok: false, message: 'ChuyÃªn má»¥c bÃ i viáº¿t khÃ´ng há»£p lá»‡.' };
  }

  if (title.length < 4) {
    return { ok: false, message: 'TiÃªu Ä‘á» bÃ i viáº¿t quÃ¡ ngáº¯n.' };
  }

  if (content.length < 12) {
    return { ok: false, message: 'Ná»™i dung bÃ i viáº¿t quÃ¡ ngáº¯n.' };
  }

  const insertId = await insertHomePost({
    category,
    title,
    content,
    displayOrder
  });

  await logAdminAction({
    adminUserId: input.admin.id,
    adminUsername: input.admin.username,
    actionType: 'create_home_post',
    targetType: 'home_post',
    targetValue: `${category}:${title}`,
    detailText: `id=${insertId}; order=${displayOrder}; ${content}`,
    requestIp: input.requestIp,
    success: true,
    resultMessage: 'ÄÃ£ Ä‘Äƒng bÃ i viáº¿t má»›i lÃªn trang chá»§.'
  });

  return { ok: true, message: 'ÄÃ£ Ä‘Äƒng bÃ i viáº¿t má»›i lÃªn trang chá»§.' };
}

export async function deleteStoredHomePost(input: {
  id: number;
  admin: AdminUser;
  requestIp: string;
}): Promise<{ ok: boolean; message: string }> {
  await ensureAdminSchema();

  const posts = await listHomePosts(200);
  const matched = posts.find((item) => item.id === input.id);

  if (!matched) {
    return { ok: false, message: 'KhÃ´ng tÃ¬m tháº¥y bÃ i viáº¿t cáº§n xÃ³a.' };
  }

  const deleted = await deleteHomePost(input.id);
  if (!deleted) {
    return { ok: false, message: 'KhÃ´ng thá»ƒ xÃ³a bÃ i viáº¿t lÃºc nÃ y.' };
  }

  await logAdminAction({
    adminUserId: input.admin.id,
    adminUsername: input.admin.username,
    actionType: 'delete_home_post',
    targetType: 'home_post',
    targetValue: `${matched.category}:${matched.title}`,
    requestIp: input.requestIp,
    success: true,
    resultMessage: 'ÄÃ£ xÃ³a bÃ i viáº¿t trang chá»§.'
  });

  return { ok: true, message: 'ÄÃ£ xÃ³a bÃ i viáº¿t trang chá»§.' };
}

export async function listAdminActionLogs(limit = 100): Promise<AdminLogEntry[]> {
  await ensureAdminSchema();

  const pool = getAccountPool();
  const [rows] = await pool.query<RowDataPacket[]>(
    `
      SELECT id, admin_username, action_type, target_type, target_value, detail_text, request_ip, success, result_message, created_at
      FROM web_admin_action_logs
      ORDER BY id DESC
      LIMIT ?
    `,
    [limit]
  );

  return rows.map((row) => ({
    id: Number(row.id),
    adminUsername: String(row.admin_username ?? ''),
    actionType: String(row.action_type ?? ''),
    targetType: String(row.target_type ?? ''),
    targetValue: String(row.target_value ?? ''),
    detailText: String(row.detail_text ?? ''),
    requestIp: String(row.request_ip ?? ''),
    success: Number(row.success ?? 0) === 1,
    resultMessage: String(row.result_message ?? ''),
    createdAt: row.created_at ? String(row.created_at) : null
  }));
}

export async function getAdminDashboardData(): Promise<AdminDashboardData> {
  await ensureAdminSchema();

  const pool = getAccountPool();

  const [status, pendingRows, announcementRows, adminRows, recentPending, recentLogs] = await Promise.all([
    fetchAdminStatus(),
    pool.query<RowDataPacket[]>("SELECT COUNT(*) AS total FROM pending_account_registrations WHERE status = 'pending'"),
    pool.query<RowDataPacket[]>("SELECT COUNT(*) AS total FROM web_admin_announcements WHERE is_published = 1"),
    pool.query<RowDataPacket[]>("SELECT COUNT(*) AS total FROM web_admin_users WHERE is_active = 1"),
    listPendingRegistrations(5),
    listAdminActionLogs(8)
  ]);

  return {
    serverStatus: status,
    pendingCount: Number(pendingRows[0][0]?.total ?? 0),
    announcementCount: Number(announcementRows[0][0]?.total ?? 0),
    activeAdminCount: Number(adminRows[0][0]?.total ?? 0),
    recentPending,
    recentLogs
  };
}

export async function searchPlayers(query: string): Promise<AdminPlayerRecord[]> {
  await ensureAdminSchema();

  const normalizedQuery = normalizeText(query).toLowerCase();
  const pool = getAccountPool();
  const gameDbName = readGameDbConfig().database;
  const onlinePlayers = await fetchOnlinePlayersDetailed();

  const onlineByUsername = new Map<string, AdminOnlinePlayer>();
  for (const player of onlinePlayers) {
    onlineByUsername.set(player.username.toLowerCase(), player);
  }

  const sql = normalizedQuery
    ? `
      SELECT
        u.id AS account_id,
        u.username,
        u.email,
        u.ban AS account_ban,
        u.regdate,
        c.id AS char_id,
        c.charname,
        c.lastLv,
        c.class,
        c.gold,
        c.luong,
        c.luonglock,
        c.xp,
        c.ban AS char_ban,
        c.lastLog
      FROM team_user u
      LEFT JOIN ${gameDbName}.tob_char c ON c.userId = u.id
      WHERE LOWER(u.username) LIKE ?
         OR LOWER(COALESCE(u.email, '')) LIKE ?
         OR LOWER(COALESCE(c.charname, '')) LIKE ?
      ORDER BY u.id DESC
      LIMIT 40
    `
    : `
      SELECT
        u.id AS account_id,
        u.username,
        u.email,
        u.ban AS account_ban,
        u.regdate,
        c.id AS char_id,
        c.charname,
        c.lastLv,
        c.class,
        c.gold,
        c.luong,
        c.luonglock,
        c.xp,
        c.ban AS char_ban,
        c.lastLog
      FROM team_user u
      LEFT JOIN ${gameDbName}.tob_char c ON c.userId = u.id
      ORDER BY u.id DESC
      LIMIT 20
    `;

  const params = normalizedQuery ? [`%${normalizedQuery}%`, `%${normalizedQuery}%`, `%${normalizedQuery}%`] : [];
  const [rows] = await pool.query<RowDataPacket[]>(sql, params);

  return rows.map((row) => {
    const username = String(row.username ?? '');
    const onlineInfo = onlineByUsername.get(username.toLowerCase());

    return {
      accountId: Number(row.account_id),
      username,
      email: String(row.email ?? ''),
      accountBan: Number(row.account_ban ?? 0),
      regdate: row.regdate ? String(row.regdate) : null,
      charId: row.char_id != null ? Number(row.char_id) : null,
      charname: row.charname ? String(row.charname) : null,
      lastLv: row.lastLv != null ? Number(row.lastLv) : null,
      classId: row.class != null ? Number(row.class) : null,
      gold: row.gold != null ? Number(row.gold) : null,
      luong: row.luong != null ? Number(row.luong) : null,
      luongLock: row.luonglock != null ? Number(row.luonglock) : null,
      xp: row.xp != null ? Number(row.xp) : null,
      charBan: row.char_ban != null ? Number(row.char_ban) : null,
      lastLog: row.lastLog ? String(row.lastLog) : null,
      isOnline: Boolean(onlineInfo),
      onlineLocation: onlineInfo?.location ?? null
    };
  });
}

export async function setAccountLocked(input: {
  username: string;
  locked: boolean;
  admin: AdminUser;
  requestIp: string;
}): Promise<{ ok: boolean; message: string }> {
  await ensureAdminSchema();

  const username = normalizeUsername(input.username);
  if (!username) {
    return { ok: false, message: 'Thiếu tài khoản cần xử lý.' };
  }

  const pool = getAccountPool();
  const [result] = await pool.query<ResultSetHeader>(
    `
      UPDATE team_user
      SET ban = ?
      WHERE LOWER(username) = LOWER(?)
    `,
    [input.locked ? 1 : 0, username]
  );

  if (result.affectedRows <= 0) {
    return { ok: false, message: 'Không tìm thấy tài khoản cần xử lý.' };
  }

  const message = input.locked ? 'Đã khóa tài khoản.' : 'Đã mở khóa tài khoản.';

  await logAdminAction({
    adminUserId: input.admin.id,
    adminUsername: input.admin.username,
    actionType: input.locked ? 'lock_account' : 'unlock_account',
    targetType: 'account',
    targetValue: username,
    requestIp: input.requestIp,
    success: true,
    resultMessage: message
  });

  return { ok: true, message };
}

export async function runLocalAdminCommand(input: {
  admin: AdminUser;
  requestIp: string;
  actionType: string;
  endpoint: string;
  form?: Record<string, string>;
  targetType: string;
  targetValue: string;
  detailText?: string;
  method?: 'GET' | 'POST';
}): Promise<{ ok: boolean; message: string }> {
  try {
    const result = await callLocalAdmin(input.endpoint, {
      method: input.method ?? 'POST',
      form: input.form
    });

    await logAdminAction({
      adminUserId: input.admin.id,
      adminUsername: input.admin.username,
      actionType: input.actionType,
      targetType: input.targetType,
      targetValue: input.targetValue,
      detailText: input.detailText,
      requestIp: input.requestIp,
      success: result.ok,
      resultMessage: result.message || (result.ok ? 'Thành công.' : 'Thất bại.')
    });

    return {
      ok: result.ok,
      message: result.message || (result.ok ? 'Thao tác thành công.' : 'Thao tác thất bại.')
    };
  } catch (error) {
    const message = error instanceof Error ? error.message : 'Không thể kết nối local admin.';

    await logAdminAction({
      adminUserId: input.admin.id,
      adminUsername: input.admin.username,
      actionType: input.actionType,
      targetType: input.targetType,
      targetValue: input.targetValue,
      detailText: input.detailText,
      requestIp: input.requestIp,
      success: false,
      resultMessage: message
    });

    return { ok: false, message: 'Không thể kết nối local admin để thực hiện thao tác.' };
  }
}

export function resolveRequestUrl(request: Request): URL {
  const fallbackUrl = new URL(request.url);
  const forwardedProto = request.headers.get('x-forwarded-proto')?.split(',')[0]?.trim();
  const forwardedHost = request.headers.get('x-forwarded-host')?.split(',')[0]?.trim();
  const host = forwardedHost || request.headers.get('host') || fallbackUrl.host;
  const protocol = forwardedProto || fallbackUrl.protocol.replace(':', '');

  return new URL(`${protocol}://${host}`);
}

export function buildAdminUrl(request: Request, pathname: string): URL {
  return new URL(pathname, resolveRequestUrl(request));
}

export function buildAdminRedirect(request: Request, returnTo: string, ok: boolean, message: string): NextResponse {
  const url = buildAdminUrl(request, returnTo || ADMIN_DASHBOARD_PATH);
  url.searchParams.set('notice', message);
  url.searchParams.set('noticeType', ok ? 'success' : 'error');
  return NextResponse.redirect(url);
}

export async function changeAdminPassword(input: {
  adminId: number;
  currentPassword: string;
  newPassword: string;
  requestIp: string;
}): Promise<{ ok: boolean; message: string }> {
  await ensureAdminSchema();

  const currentPassword = normalizeText(input.currentPassword);
  const newPassword = normalizeText(input.newPassword);

  if (newPassword.length < 10) {
    return { ok: false, message: 'Mật khẩu mới cần có ít nhất 10 ký tự.' };
  }

  const pool = getAccountPool();
  const [rows] = await pool.query<RowDataPacket[]>(
    `
      SELECT id, username, password_hash
      FROM web_admin_users
      WHERE id = ?
      LIMIT 1
    `,
    [input.adminId]
  );

  const admin = rows[0];
  if (!admin || !verifyAdminPassword(currentPassword, String(admin.password_hash ?? ''))) {
    return { ok: false, message: 'Mật khẩu hiện tại không chính xác.' };
  }

  await pool.query(
    `
      UPDATE web_admin_users
      SET password_hash = ?
      WHERE id = ?
    `,
    [hashAdminPassword(newPassword), input.adminId]
  );

  await logAdminAction({
    adminUserId: Number(admin.id),
    adminUsername: String(admin.username),
    actionType: 'change_admin_password',
    targetType: 'admin_account',
    targetValue: String(admin.username),
    requestIp: input.requestIp,
    success: true,
    resultMessage: 'Đã đổi mật khẩu admin.'
  });

  return { ok: true, message: 'Đã đổi mật khẩu admin.' };
}

export function formatAccountBanStatus(ban: number): string {
  if (ban === 1) {
    return 'Đang khóa';
  }

  if (ban === 3) {
    return 'Chặn đặc biệt';
  }

  return 'Hoạt động';
}

export function formatCharacterBanStatus(ban: number | null, isOnline: boolean): string {
  if (ban == null) {
    return 'Chưa có nhân vật';
  }

  if (ban === 1) {
    return 'Nhân vật đang khóa';
  }

  if (ban === 3) {
    return 'Nhân vật bị hạn chế';
  }

  return isOnline ? 'Đang trực tuyến' : 'Ngoại tuyến';
}

export function mapClassName(classId: number | null): string {
  const classNames = ['Kiếm khách', 'Chiến binh', 'Pháp sư', 'Đấu sĩ', 'Cung thủ'];
  return classId == null ? 'Chưa có' : (classNames[classId] ?? 'Chưa xác định');
}

export function createGamePasswordHash(password: string): string {
  return mysqlPasswordHash(password);
}
