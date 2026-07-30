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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

INSERT INTO web_admin_announcements (title, content, display_order)
SELECT
  'Chào mừng đến với KPAH',
  'Khu tài khoản đã sẵn sàng. Các thông báo mới từ admin sẽ được hiển thị tại đây.',
  1
WHERE NOT EXISTS (
  SELECT 1
  FROM web_admin_announcements
  WHERE title = 'Chào mừng đến với KPAH'
);
