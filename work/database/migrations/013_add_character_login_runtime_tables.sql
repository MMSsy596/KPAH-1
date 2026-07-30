-- KPAH local migration 013
-- Runtime tables queried during character selection and account attribution.
-- Schemas are taken from the supplemental kpah1 database dump.
SET NAMES utf8;
USE `kpah2`;

CREATE TABLE IF NOT EXISTS `5h_notify` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `owner` varchar(255) NOT NULL,
  `content` text NOT NULL,
  `time_end` int(11) NOT NULL DEFAULT '1702874482',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `board_created` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `xu` bigint(20) unsigned NOT NULL DEFAULT '0',
  `luong` bigint(20) unsigned NOT NULL DEFAULT '0',
  `luongK` bigint(20) unsigned NOT NULL DEFAULT '0',
  `svID` int(11) NOT NULL DEFAULT '-1',
  `username` varchar(45) NOT NULL,
  `ve` int(10) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `Index_2` (`username`) USING BTREE,
  KEY `Index_3` (`svID`) USING BTREE
) ENGINE=MyISAM DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

CREATE TABLE IF NOT EXISTS `board_naptien` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `xu` bigint(20) unsigned NOT NULL DEFAULT '0',
  `luong` bigint(20) unsigned NOT NULL DEFAULT '0',
  `svID` int(11) NOT NULL DEFAULT '-1',
  `username` varchar(45) NOT NULL,
  `ve` int(10) unsigned NOT NULL DEFAULT '0',
  `luongKhoa` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `Index_2` (`username`) USING BTREE,
  KEY `Index_3` (`svID`) USING BTREE
) ENGINE=MyISAM DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

CREATE TABLE IF NOT EXISTS `team_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userid` int(10) unsigned NOT NULL,
  `provider` int(10) unsigned NOT NULL,
  `agent` int(11) NOT NULL DEFAULT '-1',
  `newagent` varchar(45) NOT NULL DEFAULT '-1',
  `newprovider` varchar(45) NOT NULL DEFAULT '-1',
  `username` varchar(20) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `phone` varchar(20) NOT NULL DEFAULT '',
  `regdate` datetime DEFAULT NULL,
  `ban` tinyint(1) NOT NULL DEFAULT '0',
  `fromgame` tinyint(4) NOT NULL DEFAULT '0',
  `email` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `Index_2` (`userid`) USING BTREE,
  UNIQUE KEY `uk_team_user_username` (`username`),
  KEY `Index_3` (`provider`),
  KEY `Index_4` (`agent`),
  KEY `newagent` (`newagent`),
  KEY `newprovider` (`newprovider`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
