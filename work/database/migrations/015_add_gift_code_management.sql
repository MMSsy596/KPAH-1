-- KPAH migration 015: gift code dùng chung và lịch sử nhận quà.
SET NAMES utf8mb4;
USE `kpah2`;

CREATE TABLE IF NOT EXISTS `giftcode` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `giftcode` varchar(64) NOT NULL,
  `xu` int(11) NOT NULL DEFAULT 0,
  `luong` int(11) NOT NULL DEFAULT 0,
  `luongLock` int(11) NOT NULL DEFAULT 0,
  `item` text DEFAULT NULL,
  `expire` int(11) NOT NULL DEFAULT 0,
  `limit_use` int(11) NOT NULL DEFAULT -1,
  `type` int(11) NOT NULL DEFAULT 0,
  `is_active` tinyint(1) NOT NULL DEFAULT 1,
  `starts_at` datetime DEFAULT NULL,
  `expires_at` datetime DEFAULT NULL,
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_giftcode_code` (`giftcode`),
  KEY `idx_giftcode_status_time` (`is_active`, `starts_at`, `expires_at`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS `giftcode_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `giftcode` varchar(64) NOT NULL,
  `player` varchar(45) NOT NULL,
  `account_id` int(11) DEFAULT NULL,
  `character_id` int(11) DEFAULT NULL,
  `item` text DEFAULT NULL,
  `xu` int(11) NOT NULL DEFAULT 0,
  `luong` int(11) NOT NULL DEFAULT 0,
  `luongK` int(11) NOT NULL DEFAULT 0,
  `status` enum('reserved','success','failed') NOT NULL DEFAULT 'success',
  `error_message` varchar(255) DEFAULT NULL,
  `redeemed_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_giftcode_player` (`giftcode`, `player`),
  KEY `idx_giftcode_log_time` (`redeemed_at`),
  KEY `idx_giftcode_log_account` (`account_id`, `redeemed_at`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
