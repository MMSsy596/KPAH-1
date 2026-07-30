import 'server-only';

import { existsSync, readFileSync } from 'node:fs';
import path from 'node:path';

import mysql, { type Pool } from 'mysql2/promise';

export type DatabaseConfig = {
  host: string;
  port: number;
  database: string;
  user: string;
  password: string;
};

declare global {
  var kpahAccountPool: Pool | undefined;
  var kpahGamePool: Pool | undefined;
  var kpahAccountConfig: DatabaseConfig | undefined;
  var kpahGameConfig: DatabaseConfig | undefined;
  var kpahGameServerSettings: Map<string, string> | undefined;
}

function resolveRepoPath(...segments: string[]): string {
  return path.resolve(process.cwd(), '..', '..', ...segments);
}

export function parseIni(content: string): Map<string, string> {
  const values = new Map<string, string>();

  for (const rawLine of content.split(/\r?\n/)) {
    const line = rawLine.trim();
    if (!line || line.startsWith('#') || line.startsWith('//') || !line.includes('=')) {
      continue;
    }

    const separatorIndex = line.indexOf('=');
    const key = line.slice(0, separatorIndex).trim();
    const value = line.slice(separatorIndex + 1).trim();

    if (key) {
      values.set(key, value);
    }
  }

  return values;
}

function readIniFile(filePath: string): Map<string, string> {
  if (!existsSync(filePath)) {
    throw new Error(`Khong tim thay file cau hinh: ${filePath}`);
  }

  return parseIni(readFileSync(filePath, 'utf8'));
}

function parseJdbcUrl(jdbcUrl: string): Pick<DatabaseConfig, 'host' | 'port' | 'database'> {
  const normalized = jdbcUrl.replace(/^jdbc:/i, '');
  const parsed = new URL(normalized);

  return {
    host: parsed.hostname || '127.0.0.1',
    port: parsed.port ? Number(parsed.port) : 3306,
    database: parsed.pathname.replace(/^\//, '') || 'account'
  };
}

function parseHostPort(raw: string): Pick<DatabaseConfig, 'host' | 'port'> {
  const [hostPart, portPart] = raw.split(':');

  return {
    host: hostPart?.trim() || '127.0.0.1',
    port: portPart ? Number(portPart) : 3306
  };
}

function createPool(config: DatabaseConfig): Pool {
  return mysql.createPool({
    host: config.host,
    port: config.port,
    user: config.user,
    password: config.password,
    database: config.database,
    waitForConnections: true,
    connectionLimit: 2,
    maxIdle: 2,
    idleTimeout: 60_000,
    queueLimit: 0,
    charset: 'utf8mb4'
  });
}

export function readAccountDbConfig(): DatabaseConfig {
  if (!globalThis.kpahAccountConfig) {
    const iniPath = resolveRepoPath('loginServer', 'server.ini');
    const values = readIniFile(iniPath);
    const jdbcUrl = values.get('db.url');
    const user = values.get('db.user');
    const password = values.get('db.password') ?? '';

    if (!jdbcUrl || !user) {
      throw new Error('Thieu db.url hoac db.user trong loginServer/server.ini');
    }

    globalThis.kpahAccountConfig = {
      ...parseJdbcUrl(jdbcUrl),
      user,
      password
    };
  }

  return globalThis.kpahAccountConfig;
}

export function readGameDbConfig(): DatabaseConfig {
  if (!globalThis.kpahGameConfig) {
    const values = readGameServerSettings();
    const host = values.get('db.host');
    const database = values.get('db.name');
    const user = values.get('db.user');
    const password = values.get('db.password') ?? '';

    if (!host || !database || !user) {
      throw new Error('Thieu db.host, db.name hoac db.user trong server.ini');
    }

    globalThis.kpahGameConfig = {
      ...parseHostPort(host),
      database,
      user,
      password
    };
  }

  return globalThis.kpahGameConfig;
}

export function readGameServerSettings(): Map<string, string> {
  if (!globalThis.kpahGameServerSettings) {
    const iniPath = resolveRepoPath('server.ini');
    globalThis.kpahGameServerSettings = readIniFile(iniPath);
  }

  return globalThis.kpahGameServerSettings;
}

export function getAccountPool(): Pool {
  if (!globalThis.kpahAccountPool) {
    globalThis.kpahAccountPool = createPool(readAccountDbConfig());
  }

  return globalThis.kpahAccountPool;
}

export function getGamePool(): Pool {
  if (!globalThis.kpahGamePool) {
    globalThis.kpahGamePool = createPool(readGameDbConfig());
  }

  return globalThis.kpahGamePool;
}
