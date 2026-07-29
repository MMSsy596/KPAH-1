import 'server-only';

import { createHash } from 'node:crypto';
import type { Pool } from 'mysql2/promise';

import { getAccountPool } from '@/lib/db';

export { getAccountPool } from '@/lib/db';

declare global {
  var kpahPendingSchemaReady: Promise<void> | undefined;
}

export async function ensurePendingRegistrationSchema(): Promise<void> {
  if (!globalThis.kpahPendingSchemaReady) {
    globalThis.kpahPendingSchemaReady = (async () => {
      const pool = getAccountPool();
      await pool.query(`
        CREATE TABLE IF NOT EXISTS pending_account_registrations (
          id BIGINT NOT NULL AUTO_INCREMENT,
          username VARCHAR(20) NOT NULL,
          password_hash VARCHAR(41) NOT NULL,
          email VARCHAR(100) NOT NULL,
          phone VARCHAR(20) NOT NULL DEFAULT '',
          request_ip VARCHAR(64) NOT NULL,
          user_agent VARCHAR(255) NOT NULL DEFAULT '',
          status ENUM('pending','approved','rejected','expired') NOT NULL DEFAULT 'pending',
          review_note VARCHAR(255) DEFAULT NULL,
          approved_account_id INT DEFAULT NULL,
          submission_fingerprint CHAR(64) NOT NULL,
          requested_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
          reviewed_at DATETIME DEFAULT NULL,
          PRIMARY KEY (id),
          KEY idx_pending_status_time (status, requested_at),
          KEY idx_pending_username (username),
          KEY idx_pending_email (email),
          KEY idx_pending_ip_time (request_ip, requested_at),
          KEY idx_pending_fingerprint (submission_fingerprint)
        ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci
      `);
    })();
  }

  await globalThis.kpahPendingSchemaReady;
}

export function mysqlPasswordHash(password: string): string {
  const first = createHash('sha1').update(password, 'utf8').digest();
  const second = createHash('sha1').update(first).digest('hex').toUpperCase();
  return `*${second}`;
}

export function createSubmissionFingerprint(parts: string[]): string {
  return createHash('sha256').update(parts.join('|'), 'utf8').digest('hex');
}
