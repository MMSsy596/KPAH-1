import 'server-only';

import type { RowDataPacket } from 'mysql2/promise';

import { getAccountPool, getGamePool, readGameServerSettings } from '@/lib/db';

export type PlayerAnnouncement = {
  id: number;
  title: string;
  content: string;
  publishedAt: string | null;
};

export type PlayerPortalData = {
  account: {
    id: number;
    username: string;
    email: string;
    regdate: string | null;
    statusLabel: string;
  };
  character: {
    id: number;
    charname: string;
    level: number;
    className: string;
    xp: number;
    gold: number;
    luong: number;
    luongLock: number;
    statusLabel: string;
    location: string | null;
    lastLog: string | null;
    powerRank: number;
    wealthRank: number;
    totalRankedPlayers: number;
    powerPercentile: number;
  } | null;
  announcements: PlayerAnnouncement[];
};

declare global {
  var kpahAnnouncementSchemaReady: Promise<void> | undefined;
}

const CLASS_NAMES = ['Kiáº¿m khÃ¡ch', 'Chiáº¿n binh', 'PhÃ¡p sÆ°', 'Äáº¥u sÄ©', 'Cung thá»§'];

type OnlinePlayerInfo = {
  username: string;
  charname: string;
  location: string;
};

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

function formatAccountStatus(ban: number): string {
  if (ban === 1) {
    return 'ChÆ°a kÃ­ch hoáº¡t';
  }

  if (ban === 3) {
    return 'Táº¡m khÃ³a';
  }

  return 'Hoáº¡t Ä‘á»™ng';
}

function formatCharacterStatus(ban: number, online: boolean): string {
  if (ban === 1) {
    return 'NhÃ¢n váº­t Ä‘ang bá»‹ khÃ³a';
  }

  if (ban === 3) {
    return 'NhÃ¢n váº­t bá»‹ háº¡n cháº¿';
  }

  return online ? 'Äang trá»±c tuyáº¿n' : 'Äang ngoáº¡i tuyáº¿n';
}

function getClassName(classId: number): string {
  return CLASS_NAMES[classId] ?? 'ChÆ°a xÃ¡c Ä‘á»‹nh';
}

async function fetchOnlinePlayers(): Promise<Map<string, OnlinePlayerInfo>> {
  const settings = readGameServerSettings();
  const host = settings.get('sv.localAdminHost') ?? '127.0.0.1';
  const port = settings.get('sv.localAdminPort') ?? '18023';
  const token = settings.get('sv.localAdminToken') ?? '';

  if (!token) {
    return new Map<string, OnlinePlayerInfo>();
  }

  const response = await fetch(`http://${host}:${port}/api/players/online`, {
    headers: {
      'X-Admin-Token': token
    },
    cache: 'no-store'
  }).catch(() => null);

  if (!response?.ok) {
    return new Map<string, OnlinePlayerInfo>();
  }

  const props = parseProperties(await response.text());
  const total = Number(props.get('player_count') ?? 0);
  const onlinePlayers = new Map<string, OnlinePlayerInfo>();

  for (let index = 0; index < total; index += 1) {
    const username = props.get(`player_${index}_username`) ?? '';
    const charname = props.get(`player_${index}_name`) ?? '';
    const location = props.get(`player_${index}_location`) ?? '';

    if (!username) {
      continue;
    }

    onlinePlayers.set(username.toLowerCase(), {
      username,
      charname,
      location
    });
  }

  return onlinePlayers;
}

export async function ensureAnnouncementSchema(): Promise<void> {
  if (!globalThis.kpahAnnouncementSchemaReady) {
    globalThis.kpahAnnouncementSchemaReady = (async () => {
      const pool = getAccountPool();
      await pool.query(`
        CREATE TABLE IF NOT EXISTS web_admin_announcements (
          id BIGINT NOT NULL AUTO_INCREMENT,
          title VARCHAR(160) NOT NULL,
          content TEXT NOT NULL,
          is_published TINYINT(1) NOT NULL DEFAULT 1,
          display_order INT NOT NULL DEFAULT 0,
          published_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
          expires_at DATETIME DEFAULT NULL,
          created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
          updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
          PRIMARY KEY (id),
          KEY idx_web_announce_visible (is_published, published_at, expires_at, display_order)
        ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci
      `);
    })();
  }

  await globalThis.kpahAnnouncementSchemaReady;
}

export async function getPlayerPortalData(accountId: number, username: string): Promise<PlayerPortalData | null> {
  await ensureAnnouncementSchema();

  const accountPool = getAccountPool();
  const gamePool = getGamePool();

  const [accountRows] = await accountPool.query<RowDataPacket[]>(
    `
      SELECT id, username, email, regdate, ban
      FROM team_user
      WHERE id = ?
      LIMIT 1
    `,
    [accountId]
  );
  const account = accountRows[0];

  if (!account) {
    return null;
  }

  const [characterQuery, announcementQuery, onlinePlayers] = await Promise.all([
    gamePool.query<RowDataPacket[]>(
      `
        SELECT id, charname, userId, xp, lastLv, class, gold, luong, luonglock, ban, idClan, lastLog
        FROM tob_char
        WHERE userId = ?
        ORDER BY id DESC
        LIMIT 1
      `,
      [accountId]
    ),
    accountPool.query<RowDataPacket[]>(
      `
        SELECT id, title, content, published_at
        FROM web_admin_announcements
        WHERE is_published = 1
          AND (expires_at IS NULL OR expires_at >= NOW())
        ORDER BY display_order DESC, published_at DESC, id DESC
        LIMIT 5
      `
    ),
    fetchOnlinePlayers()
  ]);

  const [characterRows] = characterQuery;
  const [announcementRows] = announcementQuery;
  const character = characterRows[0];
  const announcements = announcementRows.map((row) => ({
    id: Number(row.id),
    title: String(row.title ?? ''),
    content: String(row.content ?? ''),
    publishedAt: row.published_at ? String(row.published_at) : null
  }));

  if (!character) {
    return {
      account: {
        id: Number(account.id),
        username: String(account.username),
        email: String(account.email ?? ''),
        regdate: account.regdate ? String(account.regdate) : null,
        statusLabel: formatAccountStatus(Number(account.ban ?? 0))
      },
      character: null,
      announcements
    };
  }

  const [rankingRows] = await gamePool.query<RowDataPacket[]>(
    `
      SELECT
        1 + (
          SELECT COUNT(*)
          FROM tob_char
          WHERE charname <> 'chienthan' AND ban = 0 AND xp > ?
        ) AS powerRank,
        1 + (
          SELECT COUNT(*)
          FROM tob_char
          WHERE charname <> 'chienthan' AND ban = 0 AND gold > ?
        ) AS wealthRank,
        (
          SELECT COUNT(*)
          FROM tob_char
          WHERE charname <> 'chienthan' AND ban = 0
        ) AS totalRankedPlayers
    `,
    [Number(character.xp ?? 0), Number(character.gold ?? 0)]
  );

  const onlineInfo = onlinePlayers.get(username.toLowerCase());
  const totalRankedPlayers = Math.max(1, Number(rankingRows[0]?.totalRankedPlayers ?? 1));
  const powerRank = Number(rankingRows[0]?.powerRank ?? 1);

  return {
    account: {
      id: Number(account.id),
      username: String(account.username),
      email: String(account.email ?? ''),
      regdate: account.regdate ? String(account.regdate) : null,
      statusLabel: formatAccountStatus(Number(account.ban ?? 0))
    },
    character: {
      id: Number(character.id),
      charname: String(character.charname ?? ''),
      level: Number(character.lastLv ?? 0),
      className: getClassName(Number(character.class ?? 0)),
      xp: Number(character.xp ?? 0),
      gold: Number(character.gold ?? 0),
      luong: Number(character.luong ?? 0),
      luongLock: Number(character.luonglock ?? 0),
      statusLabel: formatCharacterStatus(Number(character.ban ?? 0), Boolean(onlineInfo)),
      location: onlineInfo?.location ?? null,
      lastLog: character.lastLog ? String(character.lastLog) : null,
      powerRank,
      wealthRank: Number(rankingRows[0]?.wealthRank ?? 1),
      totalRankedPlayers,
      powerPercentile: Math.max(1, Math.round((1 - (powerRank - 1) / totalRankedPlayers) * 100))
    },
    announcements
  };
}
