import 'server-only';

import type { ResultSetHeader, RowDataPacket } from 'mysql2/promise';

import { getAccountPool } from '@/lib/db';

export type HomePostCategory = 'new' | 'feature' | 'guide';

export type HomePost = {
  id: number;
  category: HomePostCategory;
  title: string;
  content: string;
  displayOrder: number;
  publishedAt: string | null;
};

declare global {
  var kpahHomePostSchemaReady: Promise<void> | undefined;
}

export const HOME_POST_CATEGORY_OPTIONS: Array<{ value: HomePostCategory; label: string }> = [
  { value: 'new', label: 'Bài đăng mới' },
  { value: 'feature', label: 'Tính năng' },
  { value: 'guide', label: 'Hướng dẫn' }
];

export function isHomePostCategory(value: string): value is HomePostCategory {
  return value === 'new' || value === 'feature' || value === 'guide';
}

export function getHomePostCategoryLabel(category: HomePostCategory): string {
  const matched = HOME_POST_CATEGORY_OPTIONS.find((item) => item.value === category);
  return matched?.label ?? 'Bài viết';
}

export async function ensureHomePostSchema(): Promise<void> {
  if (!globalThis.kpahHomePostSchemaReady) {
    globalThis.kpahHomePostSchemaReady = (async () => {
      const pool = getAccountPool();
      await pool.query(`
        CREATE TABLE IF NOT EXISTS web_home_posts (
          id BIGINT NOT NULL AUTO_INCREMENT,
          category ENUM('new', 'feature', 'guide') NOT NULL,
          title VARCHAR(180) NOT NULL,
          content LONGTEXT NOT NULL,
          is_published TINYINT(1) NOT NULL DEFAULT 1,
          display_order INT NOT NULL DEFAULT 0,
          published_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
          created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
          updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
          PRIMARY KEY (id),
          KEY idx_web_home_posts_visible (category, is_published, display_order, published_at)
        ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci
      `);
    })();
  }

  await globalThis.kpahHomePostSchemaReady;
}

function mapRowToHomePost(row: RowDataPacket): HomePost {
  return {
    id: Number(row.id),
    category: String(row.category ?? 'new') as HomePostCategory,
    title: String(row.title ?? ''),
    content: String(row.content ?? ''),
    displayOrder: Number(row.display_order ?? 0),
    publishedAt: row.published_at ? String(row.published_at) : null
  };
}

export async function listPublishedHomePostsByCategory(): Promise<Record<HomePostCategory, HomePost[]>> {
  await ensureHomePostSchema();

  const pool = getAccountPool();
  const [rows] = await pool.query<RowDataPacket[]>(`
    SELECT id, category, title, content, display_order, published_at
    FROM web_home_posts
    WHERE is_published = 1
    ORDER BY category ASC, display_order DESC, published_at DESC, id DESC
  `);

  const grouped: Record<HomePostCategory, HomePost[]> = {
    new: [],
    feature: [],
    guide: []
  };

  for (const row of rows) {
    const post = mapRowToHomePost(row);
    grouped[post.category].push(post);
  }

  return grouped;
}

export async function listHomePosts(limit = 100): Promise<HomePost[]> {
  await ensureHomePostSchema();

  const pool = getAccountPool();
  const [rows] = await pool.query<RowDataPacket[]>(
    `
      SELECT id, category, title, content, display_order, published_at
      FROM web_home_posts
      ORDER BY category ASC, display_order DESC, published_at DESC, id DESC
      LIMIT ?
    `,
    [limit]
  );

  return rows.map(mapRowToHomePost);
}

export async function getPublishedHomePostById(id: number): Promise<HomePost | null> {
  await ensureHomePostSchema();

  const pool = getAccountPool();
  const [rows] = await pool.query<RowDataPacket[]>(
    `
      SELECT id, category, title, content, display_order, published_at
      FROM web_home_posts
      WHERE id = ? AND is_published = 1
      LIMIT 1
    `,
    [id]
  );

  return rows[0] ? mapRowToHomePost(rows[0]) : null;
}

export async function insertHomePost(input: {
  category: HomePostCategory;
  title: string;
  content: string;
  displayOrder: number;
}): Promise<number> {
  await ensureHomePostSchema();

  const pool = getAccountPool();
  const [result] = await pool.query<ResultSetHeader>(
    `
      INSERT INTO web_home_posts (category, title, content, is_published, display_order)
      VALUES (?, ?, ?, 1, ?)
    `,
    [input.category, input.title, input.content, input.displayOrder]
  );

  return Number(result.insertId);
}

export async function deleteHomePost(id: number): Promise<boolean> {
  await ensureHomePostSchema();

  const pool = getAccountPool();
  const [result] = await pool.query<ResultSetHeader>(
    'DELETE FROM web_home_posts WHERE id = ? LIMIT 1',
    [id]
  );

  return result.affectedRows > 0;
}
