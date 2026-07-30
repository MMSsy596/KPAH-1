-- KPAH local migration 012
-- Source: selected table sections from vendor/server-kdev/DATABASE/kpah1.sql.
-- Added after runtime reported missing 5h_systems and tob_log_use_luong.
SET NAMES utf8;
USE `kpah2`;

-- BEGIN 5h_systems
-- Dumping structure for table kpah.5h_systems
CREATE TABLE IF NOT EXISTS `5h_systems` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `command` varchar(255) DEFAULT NULL,
  `status` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4;

-- Dumping data for table kpah.5h_systems: ~6 rows (approximately)
DELETE FROM `5h_systems`;
INSERT INTO `5h_systems` (`id`, `command`, `status`) VALUES
	(1, 'restart', 1),
	(2, 'tangexp', 2),
	(5, 'tangnap', 2),
	(6, 'maxccu', 5000),
	(7, 'percentDrop', 70),
	(8, 'tileDrop', 1);

-- END 5h_systems

-- BEGIN tob_log_use_luong
-- Dumping structure for table kpah.tob_log_use_luong
CREATE TABLE IF NOT EXISTS `tob_log_use_luong` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `idtemplate` int(10) unsigned NOT NULL DEFAULT '0',
  `soluong` bigint(20) unsigned NOT NULL DEFAULT '1',
  `solanmua` int(10) unsigned NOT NULL DEFAULT '1',
  `totalmoney` bigint(20) unsigned NOT NULL DEFAULT '0',
  `moneytype` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `Index_2` (`name`) USING BTREE,
  KEY `Index_3` (`idtemplate`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- Dumping data for table kpah.tob_log_use_luong: 0 rows
DELETE FROM `tob_log_use_luong`;
/*!40000 ALTER TABLE `tob_log_use_luong` DISABLE KEYS */;
/*!40000 ALTER TABLE `tob_log_use_luong` ENABLE KEYS */;

-- END tob_log_use_luong

