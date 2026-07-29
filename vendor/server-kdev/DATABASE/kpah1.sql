-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               5.7.26 - MySQL Community Server (GPL)
-- Server OS:                    Win64
-- HeidiSQL Version:             12.8.0.6908
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Dumping database structure for kpah
CREATE DATABASE IF NOT EXISTS `kpah` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;
USE `kpah`;

-- Dumping structure for table kpah.5h_active
CREATE TABLE IF NOT EXISTS `5h_active` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userID` int(11) NOT NULL,
  `username` varchar(255) NOT NULL,
  `time_end` varchar(50) DEFAULT '-1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Dumping data for table kpah.5h_active: ~0 rows (approximately)
DELETE FROM `5h_active`;

-- Dumping structure for table kpah.5h_notify
CREATE TABLE IF NOT EXISTS `5h_notify` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `owner` varchar(255) NOT NULL,
  `content` text NOT NULL,
  `time_end` int(11) NOT NULL DEFAULT '1702874482',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Dumping data for table kpah.5h_notify: ~0 rows (approximately)
DELETE FROM `5h_notify`;

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

-- Dumping structure for table kpah.admin_msg
CREATE TABLE IF NOT EXISTS `admin_msg` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `msg` varchar(1000) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- Dumping data for table kpah.admin_msg: 1 rows
DELETE FROM `admin_msg`;
/*!40000 ALTER TABLE `admin_msg` DISABLE KEYS */;
INSERT INTO `admin_msg` (`id`, `msg`) VALUES
	(5, 'setinfo hệ thống gặp lỗi không thể khởi động lại, vui lòng thoát game trước 10h sáng.');
/*!40000 ALTER TABLE `admin_msg` ENABLE KEYS */;

-- Dumping structure for table kpah.bad_word
CREATE TABLE IF NOT EXISTS `bad_word` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `word` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=170 DEFAULT CHARSET=utf8;

-- Dumping data for table kpah.bad_word: 147 rows
DELETE FROM `bad_word`;
/*!40000 ALTER TABLE `bad_word` DISABLE KEYS */;
INSERT INTO `bad_word` (`id`, `word`) VALUES
	(48, '*sc'),
	(47, '.sc'),
	(46, 'vvap'),
	(45, 'wáp'),
	(5, 'dm'),
	(6, 'dkm'),
	(62, 'háck'),
	(61, 'http'),
	(10, 'iwin'),
	(65, '*mw'),
	(12, 'fuck'),
	(13, 'lon`'),
	(14, 'l0n`'),
	(15, 'lam tinh'),
	(28, 'dis'),
	(63, '.mw'),
	(19, 'www'),
	(20, 'wap'),
	(21, '.com'),
	(22, '.vn'),
	(23, 'sex'),
	(56, ',lt'),
	(55, ',yn'),
	(54, ',sh'),
	(53, '.lt'),
	(29, 'hack'),
	(50, '*in'),
	(31, 'dcm'),
	(51, 'wáp'),
	(33, 'tinhduc'),
	(52, '.yn'),
	(35, 'hak'),
	(44, '*sh'),
	(37, 'xtgem'),
	(38, '.sh'),
	(49, '.in'),
	(40, '.tk'),
	(41, '.net'),
	(42, 'cặc'),
	(43, 'lồn'),
	(57, ',in'),
	(58, ',sc'),
	(59, '*yn'),
	(60, 'nhan qua'),
	(66, 'du ma'),
	(67, 'du ma may'),
	(69, 'lon`'),
	(70, 'l0n`'),
	(71, 'lam tinh'),
	(72, 'test'),
	(73, 'dis'),
	(74, '.mw'),
	(75, 'www'),
	(76, 'wap'),
	(77, '.com'),
	(78, '.vn'),
	(79, 'sex'),
	(80, 'dit'),
	(81, ',yn'),
	(82, '.it'),
	(83, '.sh'),
	(84, ',sh'),
	(85, 'hack'),
	(86, 'dcm'),
	(87, 'wáp'),
	(88, 'tinhduc'),
	(89, 'tinh duc'),
	(90, '.yn'),
	(91, 'hak'),
	(92, '*sh'),
	(93, 'xtgem'),
	(94, '.in'),
	(95, '.tk'),
	(96, '.net'),
	(97, 'cặc'),
	(98, 'lồn'),
	(99, 'giftcode'),
	(100, ',in'),
	(101, ',sc'),
	(102, 'giftc0de'),
	(103, 'daily'),
	(104, 'dai ly'),
	(105, 'đại lý'),
	(106, 'đại lí'),
	(107, 'dai li'),
	(108, 'banxu'),
	(109, 'ban xu'),
	(110, 'bán xu'),
	(111, 'giá rẻ'),
	(112, 'giare'),
	(113, 'gia re'),
	(114, 'gja re'),
	(115, 'team0bi'),
	(116, 'teamobj'),
	(117, 'team0bj'),
	(118, 'admjn'),
	(119, 'admin'),
	(120, 'teamobi'),
	(121, 'mua bán'),
	(122, 'muaban'),
	(123, 'mua ban'),
	(124, 'nhận thưởng'),
	(125, 'nhan thuong'),
	(126, 'nhan thuong'),
	(127, 'nhan thu0ng'),
	(128, 'nhậnthưởng'),
	(129, 'nhận quà'),
	(130, 'nhanqua'),
	(131, '.mobi'),
	(132, '.m0bi'),
	(133, '.mobj'),
	(134, '.m0bj'),
	(135, 'tặng quà'),
	(136, 'tang qua'),
	(137, 'tangqua'),
	(138, 'mod'),
	(139, 'm0d'),
	(140, 'xuandiem'),
	(141, 'xuandjem'),
	(142, 'xuan diem'),
	(143, 'xuân điềm'),
	(144, 'ngao binh'),
	(145, 'ngaobinh'),
	(146, 'ngaobjnh'),
	(147, 'ngao bjnh'),
	(148, 'hangkt'),
	(149, 'topxu'),
	(150, 'top xu'),
	(151, 'liên hệ'),
	(152, 'ngu'),
	(153, 'phong'),
	(154, 'puaru'),
	(155, 'ad'),
	(156, 'adm'),
	(157, 'a.d'),
	(158, 'pua'),
	(159, 'kpah'),
	(160, 'gino'),
	(161, 'suc'),
	(162, 'vat'),
	(163, 'sv'),
	(164, 'vv'),
	(165, 'lau'),
	(166, 'game'),
	(167, 'tay'),
	(168, 'chay'),
	(169, 'chai');
/*!40000 ALTER TABLE `bad_word` ENABLE KEYS */;

-- Dumping structure for table kpah.board_created
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

-- Dumping data for table kpah.board_created: 0 rows
DELETE FROM `board_created`;
/*!40000 ALTER TABLE `board_created` DISABLE KEYS */;
/*!40000 ALTER TABLE `board_created` ENABLE KEYS */;

-- Dumping structure for table kpah.board_naptien
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

-- Dumping data for table kpah.board_naptien: 0 rows
DELETE FROM `board_naptien`;
/*!40000 ALTER TABLE `board_naptien` DISABLE KEYS */;
/*!40000 ALTER TABLE `board_naptien` ENABLE KEYS */;

-- Dumping structure for table kpah.data_attribute
CREATE TABLE IF NOT EXISTS `data_attribute` (
  `id` int(10) unsigned NOT NULL,
  `name` varchar(45) NOT NULL,
  `idcolor` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `ispercent` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `decript` varchar(100) NOT NULL,
  `namein` varchar(45) DEFAULT '""',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- Dumping data for table kpah.data_attribute: 141 rows
DELETE FROM `data_attribute`;
/*!40000 ALTER TABLE `data_attribute` DISABLE KEYS */;
INSERT INTO `data_attribute` (`id`, `name`, `idcolor`, `ispercent`, `decript`, `namein`) VALUES
	(0, 'Tấn công', 0, 0, '', 'Serangan'),
	(1, 'Thủ vật', 0, 0, '', 'Hewan'),
	(2, 'Né tránh', 0, 2, '', 'Menghindar'),
	(3, 'Chính xác', 0, 2, '', 'Benar sekali'),
	(4, 'Chí mạng', 0, 2, '', 'Kritis'),
	(5, 'Sức khỏe', 0, 0, '', 'Kesehatan'),
	(6, 'Thủ ma', 0, 0, '', 'Hantu Utama'),
	(7, 'chỉ số 7', 0, 0, '', 'Indikator 7'),
	(8, 'chỉ số 8', 0, 0, '', 'Indikator 8'),
	(9, 'chỉ số 9', 0, 0, '', 'Indeks 9'),
	(10, 'Tăng sức mạnh', 3, 0, '', 'Tambah kekuatan'),
	(11, 'Tăng nhanh nhẹn', 3, 0, '', 'Tambah kelincahan'),
	(12, 'Tăng tinh thần', 3, 0, '', 'Tambah semangat'),
	(13, 'Tăng sức khoẻ', 3, 0, '', 'Tambah kesehatan'),
	(14, 'Gây mù', 3, 1, '', 'Kebutaan'),
	(15, 'Đóng băng', 3, 1, '', 'Lapisan Es'),
	(16, 'Trúng độc', 3, 1, '', 'Meracun'),
	(17, 'Gây choáng', 3, 1, '', 'Membius'),
	(18, 'Hoá thạch', 3, 1, '', 'Fosil'),
	(19, 'Giảm tốc', 3, 1, '', 'Melambat'),
	(20, 'Kháng giảm tốc', 3, 1, '', 'Anti pelambat'),
	(21, 'Kháng trúng độc', 3, 1, '', 'Anti racun'),
	(22, 'Kháng gây mù', 3, 1, '', 'Anti kebutaan'),
	(23, 'Kháng đóng băng', 3, 1, '', 'Anti beku'),
	(24, 'Kháng gây choáng', 3, 1, '', 'Anti setrum'),
	(25, 'Kháng hoá thạch', 3, 1, '', 'Anti fosil'),
	(26, 'Tăng x2 mỗi lần đánh', 3, 1, '', 'Meningkatkan hit 2x lipat'),
	(27, 'Tăng tỷ lệ rớt bảo vật', 3, 1, '', 'Tambah penurunan kadar harta'),
	(28, 'Giảm st vật', 1, 1, '', 'Bahan kurangi kerusakan'),
	(29, 'Giảm st ma', 1, 1, '', 'Kurangi kerusakan hantu'),
	(30, 'Tăng tấn công', 1, 1, '', 'Meningkatkan serangan'),
	(31, 'Xuyên giáp', 1, 1, '', 'Baja'),
	(32, 'Phản st', 1, 1, '', 'Respon Kerusakan'),
	(33, 'Tăng HP', 0, 0, '', 'Tingkatkan HP'),
	(34, 'Tăng MP', 0, 0, '', 'Tingkatkan MP'),
	(35, 'Cộng sức mạnh', 0, 0, '', 'Tingkatkan kekuatan'),
	(36, 'Cộng khéo léo', 0, 0, '', 'Tingkatkan kelincahan'),
	(37, 'Cộng tinh thần', 0, 0, '', 'Tingkatkan moral'),
	(38, 'Cộng sức khoẻ', 0, 0, '', 'Tingkatkan kesehatan'),
	(39, 'Cộng kỹ năng', 0, 0, '', 'Tingkatkan skil'),
	(40, 'Tăng chí mạng', 0, 0, '', 'Tingkatkan fatal'),
	(41, 'Tăng st chí mạng', 0, 0, '', 'Tingkatkan kritis'),
	(42, 'chưa dùng', 0, 0, '', 'berisi'),
	(43, 'kỹ năng 1 cộng thêm', 0, 0, '', '1 Jurus tambahan'),
	(44, 'kỹ năng 2 cộng thêm', 0, 0, '', '2 Jurus tambahan'),
	(45, 'kỹ năng 3 cộng thêm', 0, 0, '', '3 Jurus tambahan'),
	(46, 'kỹ năng 4 cộng thêm', 0, 0, '', '4 Jurus tambahan'),
	(47, 'kỹ năng 5 cộng thêm', 0, 0, '', '5 Jurus tambahan'),
	(48, 'kỹ năng 6 cộng thêm', 0, 0, '', 'Jurus 6 ditambah'),
	(49, 'kỹ năng 7 cộng thêm', 0, 0, '', 'Jurus 7 ditambah'),
	(50, 'kỹ năng 8 cộng thêm', 0, 0, '', 'Jurus 8 ditambah'),
	(51, 'kỹ năng 9 cộng thêm', 0, 0, '', '9 Jurus tambahan'),
	(52, 'kỹ năng 10 cộng thêm', 0, 0, '', 'Jurus 10 ditambah'),
	(53, 'kỹ năng 11 cộng thêm', 0, 0, '', 'Jurus 11 ditambah'),
	(54, 'kỹ năng 12 cộng thêm', 0, 0, '', 'Jurus 12 ditambah'),
	(55, 'kỹ năng 13 cộng thêm', 0, 0, '', 'Jurus 13 ditambah'),
	(56, 'kỹ năng 14 cộng thêm', 0, 0, '', 'Jurus 14 ditambah'),
	(57, 'kỹ năng 15 cộng thêm', 0, 0, '', 'Jurus 15 ditambah'),
	(58, 'Tăng công', 0, 1, '', 'Tambah'),
	(59, 'Tăng thủ ma', 0, 1, '', 'Tambah pelindung ajaib'),
	(60, 'Tăng thủ vật', 0, 1, '', 'Tambah pelindung'),
	(61, 'Bỏ qua tc ma', 1, 1, '', 'Abaikan Serangan Fantom'),
	(62, 'Bỏ qua tc vật', 1, 1, '', 'Lewati Serangan Hewan'),
	(63, 'Tăng thủ ma trang bị', 0, 1, '""', 'Tambah alat perlindungan'),
	(64, 'Tăng thủ vật trang bị', 0, 1, ' ', 'Tambah alat perlindungan'),
	(65, 'Bạo kích', 0, 2, ' ', 'Klik pertama'),
	(66, 'Sét lan', 2, 2, 'set danh 5 muc tieu xung quanh', ''),
	(67, 'Độc lan', 2, 2, 'ban doc cho 5 muc tieu xung quanh', ''),
	(68, 'Băng lan', 2, 2, 'dong bang 5 muc tieu xung quanh', ''),
	(70, 'Tăng độc lan', 0, 2, 'tang % xuat hien doc lan', ''),
	(69, 'Tăng sét lan', 0, 2, 'tang % xuat hien set lan', ''),
	(71, 'Tăng băng lan', 0, 2, 'tang % xuat hien bang lan', ''),
	(72, 'Tăng st băng lan', 0, 1, 'tang sat thuong bang lan', ''),
	(73, 'Tăng st sét lan', 0, 1, 'tang sat thuong set lan', ''),
	(74, 'Tăng st độc lan', 0, 1, 'tang sat thuong doc lan', ''),
	(76, 'Kháng bạo kích', 0, 0, 'khang bao kick', ''),
	(77, 'Hút máu', 0, 0, 'hut mau doi phuong sau moi nhat danh, min 20, max 55', ''),
	(78, 'Hồi HP', 0, 0, 'hoi hp moi 5s max 10 giot', ''),
	(79, 'Hồi MP', 0, 0, 'hoi mp moi 5s max 10 giot', ''),
	(80, 'Tàn phế', 0, 2, 'giam 50% toc do max 3%, tong vuot 10 se =10, trong 10s', ''),
	(81, 'Hấp thu sát thương', 0, 0, 'hap thu mot phan sat thuong', ''),
	(82, 'Câm lặng', 0, 2, 'không thể chơi skill max 3% trong 5s', ''),
	(75, 'Hoá người tuyết', 0, 2, 'hoa ng choi thanh nguoi tuyet trong 8s', ''),
	(83, 'Tăng tỷ lệ rớt vật phẩm', 0, 2, 'tang ty le rot item, max 10%, ko tinh trang bi tan cong', ''),
	(84, 'Tăng tỷ lệ rớt xu', 0, 2, 'tang ty le rot xu, max 10%, ko tinh trang bi tan cong', ''),
	(85, 'Kháng băng lan', 0, 2, 'khang bang lan, max 4%', ''),
	(86, 'Kháng sét lan', 0, 2, 'khang set lan, max 4%, giap', ''),
	(87, 'Kháng độc lan', 0, 2, 'khang doc lan, max 4%, giap', ''),
	(88, 'Tăng thủ trang bị', 0, 2, 'tăng phòng thủ trang tinh tren trang bi nguoi max 3%', ''),
	(89, 'Lửa lan', 2, 2, 'làm tàn phế + câm lặng 5 muc tieu xung quanh', ''),
	(90, 'Tăng lửa lan', 2, 2, 'tang % xuat hien lua lan', ''),
	(91, 'Tăng st lửa lan', 0, 1, 'tang sat thuong set lan', ''),
	(92, 'Kháng lửa lan', 0, 2, 'khang bang lan, max 4%', ''),
	(93, 'St hắc ám', 0, 0, 'dam hắc ám', ''),
	(94, 'St ánh sáng', 0, 0, 'dam ánh sáng', ''),
	(95, 'Xh hắc ám', 0, 2, 'ty le xuat hien', ''),
	(96, 'Xh ánh sáng', 0, 2, 'ty le xuat hien', ''),
	(97, 'Gây hoảng sợ:', 0, 0, 'gây hoang so cho doi phuong trong 5s', ''),
	(98, 'Xh hoảng sợ', 0, 1, 'ty le xuat hien hoang so', ''),
	(99, 'St theo chủ', 0, 1, 'st theo chu nhan', ''),
	(100, 'Sức khoẻ', 0, 0, 'suc khoe', ''),
	(101, 'St max Hp', 0, 1, 'St max Hp', '""'),
	(102, 'Ru ngủ', 0, 2, 'ru ngu', '""'),
	(103, 'Choáng', 0, 2, 'choang', '""'),
	(104, 'Bỏng', 0, 2, 'bong', '""'),
	(105, 'Trúng độc', 0, 2, 'trung doc', '""'),
	(106, 'Suy yếu', 0, 2, 'suy yeu', '""'),
	(107, 'Chuyển st qua mp', 0, 0, 'chuyen st qua mp', '""'),
	(108, 'Gia tăng tc', 0, 0, 'tang tan cong', '""'),
	(109, 'Hút hp', 0, 0, 'hut mau', '""'),
	(110, 'Sát thương độc', 0, 0, 'Sát thương độc,   otherAtt', '""'),
	(111, 'Tăng exp', 0, 1, 'Tăng exp, otheratt', '""'),
	(112, 'Tác động suy yếu', 0, 1, 'Tác động suy yếu', '""'),
	(113, 'Tl hút hp', 0, 2, 'Tl hút hp', '""'),
	(114, 'Nhân thêm st khi bỏng', 0, 1, 'Nhân thêm st khi bỏng', '""'),
	(115, 'Sát thương chuẩn', 1, 2, 'Sát thương chuẩn', '""'),
	(116, 'Chỉ số vũ khí tăng', 1, 2, 'Chỉ số vũ khí tăng', '""'),
	(117, 'Tỷ lệ gây sát thương chuẩn', 1, 2, 'Tỷ lệ gây sát thương chuẩn', '""'),
	(118, 'Giảm sát thương cuối', 2, 2, 'Giảm sát thương cuối', '""'),
	(119, 'Nguyệt ảnh gây st(10%-15%) tl xuất hiện', 2, 2, 'Nguyệt ảnh gây st(10%-15%) tỷ lệ xuất hiện', '""'),
	(120, 'Kháng hoả', 1, 2, 'Kháng hoả', ''),
	(121, 'Kháng thuỷ', 1, 2, 'Kháng thuỷ', ''),
	(122, 'Kháng mộc', 1, 2, 'Kháng mộc', ''),
	(123, 'Kháng kim', 1, 2, 'Kháng kim', ''),
	(124, 'Kháng thổ', 1, 2, 'Kháng thổ', ''),
	(125, 'Giá lượng', 2, 0, 'Giá lượng', ''),
	(126, 'Số lượng', 2, 0, 'Số lượng', ''),
	(128, 'Hồi hp theo maxhp', 2, 0, '', '""'),
	(129, 'Chuyển sát thương thành hp', 0, 1, '', '""'),
	(127, 'Kháng st lan', 2, 0, '', '""'),
	(130, 'Giảm thủ đối phương', 0, 0, '', '""'),
	(131, 'Né tránh', 2, 0, '', '""'),
	(132, 'Hoá giải kỹ năng pet', 0, 1, '', '""'),
	(133, 'Tăng hiệu quả bình hp mp khi hp dưới 30%', 2, 0, '', '""'),
	(134, 'Sát thương theo hp', 1, 1, '', '""'),
	(135, 'Điều kiện chủ nhận đủ 500k st từ hvl', 1, 0, '', '""'),
	(136, 'Hấp thu sát thương', 1, 1, '', '""'),
	(137, 'Tăng tốc', 1, 1, '', '""'),
	(138, 'Tiềm năng', 0, 1, '', '""'),
	(139, 'HP', 0, 0, '', '""'),
	(140, 'Trí Mạng', 0, 1, '', '""');
/*!40000 ALTER TABLE `data_attribute` ENABLE KEYS */;

-- Dumping structure for table kpah.data_item
CREATE TABLE IF NOT EXISTS `data_item` (
  `id` int(10) unsigned NOT NULL,
  `name` text NOT NULL,
  `type` int(10) unsigned NOT NULL,
  `style` int(10) unsigned NOT NULL DEFAULT '0',
  `he` int(11) NOT NULL DEFAULT '0',
  `gender` int(10) unsigned NOT NULL DEFAULT '0',
  `level` int(10) unsigned NOT NULL DEFAULT '0',
  `durable` int(10) unsigned NOT NULL DEFAULT '0',
  `atb0` int(11) NOT NULL DEFAULT '0',
  `atb1` int(11) NOT NULL DEFAULT '0',
  `atb2` int(11) NOT NULL DEFAULT '0',
  `atb3` int(11) NOT NULL DEFAULT '0',
  `atb4` int(11) NOT NULL DEFAULT '0',
  `atb5` int(11) NOT NULL DEFAULT '0',
  `atb6` int(11) NOT NULL DEFAULT '0',
  `atb7` int(11) NOT NULL DEFAULT '0',
  `atb8` int(11) NOT NULL DEFAULT '0',
  `atb9` int(11) NOT NULL DEFAULT '0',
  `price` int(11) NOT NULL DEFAULT '0',
  `clazz` int(11) NOT NULL DEFAULT '-1',
  `xstart` int(10) unsigned NOT NULL DEFAULT '0',
  `ystart` int(10) unsigned NOT NULL DEFAULT '0',
  `colortype` int(11) NOT NULL DEFAULT '-1',
  `nloan` int(10) unsigned NOT NULL DEFAULT '0',
  `idUpLevel` int(11) NOT NULL DEFAULT '-1',
  `namein` varchar(45) DEFAULT NULL,
  `ideff` int(11) DEFAULT '-1',
  `pricecu` int(10) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- Dumping data for table kpah.data_item: 904 rows
DELETE FROM `data_item`;
/*!40000 ALTER TABLE `data_item` DISABLE KEYS */;
INSERT INTO `data_item` (`id`, `name`, `type`, `style`, `he`, `gender`, `level`, `durable`, `atb0`, `atb1`, `atb2`, `atb3`, `atb4`, `atb5`, `atb6`, `atb7`, `atb8`, `atb9`, `price`, `clazz`, `xstart`, `ystart`, `colortype`, `nloan`, `idUpLevel`, `namein`, `ideff`, `pricecu`) VALUES
	(1, 'Áo bà ba', 0, 1, 0, 2, 1, 200, 0, 10, 1, 1, 0, 0, 0, 0, 0, 0, 500, -1, 176, 1, 0, 0, -1, NULL, -1, 0),
	(2, '(ở trần)', 0, 0, 0, 1, 1, 200, 0, 10, 1, 1, 0, 0, 0, 0, 0, 0, 500, -1, 177, 2, 0, 0, -1, NULL, -1, 0),
	(3, 'Áo đai thô (nữ)', 0, 3, 0, 2, 4, 220, 0, 15, 1, 2, 0, 0, 0, 0, 0, 0, 1000, -1, 178, 3, 0, 0, -1, NULL, -1, 0),
	(4, 'Áo đai thô (nam)', 0, 2, 0, 1, 4, 220, 0, 15, 1, 2, 0, 0, 0, 0, 0, 0, 1000, -1, 179, 4, 0, 0, -1, NULL, -1, 0),
	(5, 'Áo thổ cẩm (nữ)', 0, 3, 0, 2, 9, 250, 0, 20, 2, 2, 0, 0, 0, 0, 0, 0, 2000, -1, 180, 5, 0, 0, -1, NULL, -1, 0),
	(6, 'Áo thổ cẩm (nam)', 0, 2, 0, 1, 9, 250, 0, 20, 2, 2, 0, 0, 0, 0, 0, 0, 2000, -1, 181, 6, 0, 0, -1, NULL, -1, 0),
	(7, 'Áo bông (nữ)', 0, 5, 0, 2, 14, 280, 0, 25, 2, 3, 0, 0, 0, 0, 0, 0, 5000, -1, 182, 7, 0, 0, -1, NULL, -1, 0),
	(8, 'Áo bông (nam)', 0, 4, 0, 1, 14, 280, 0, 25, 2, 3, 0, 0, 0, 0, 0, 0, 5000, -1, 183, 8, 0, 0, -1, NULL, -1, 0),
	(9, 'Áo lụa (nữ)', 0, 5, 0, 2, 19, 300, 0, 30, 3, 3, 0, 0, 0, 0, 0, 0, 10000, -1, 184, 9, 0, 0, -1, NULL, -1, 0),
	(10, 'Áo lụa (nam)', 0, 4, 0, 1, 19, 300, 0, 30, 3, 3, 0, 0, 0, 0, 0, 0, 10000, -1, 185, 10, 0, 0, -1, NULL, -1, 0),
	(11, 'Áo tơ tằm (nữ)', 0, 7, 0, 2, 24, 320, 0, 35, 0, 3, 0, 0, 0, 0, 0, 0, 20000, -1, 186, 11, 0, 0, -1, NULL, -1, 0),
	(12, 'Áo tơ tằm (nam)', 0, 6, 0, 1, 24, 320, 0, 35, 0, 3, 0, 0, 0, 0, 0, 0, 20000, -1, 187, 12, 0, 0, -1, NULL, -1, 0),
	(13, 'Áo nhung (nữ)', 0, 7, 0, 2, 29, 350, 0, 40, 1, 4, 0, 0, 0, 0, 0, 0, 25000, -1, 188, 13, 0, 0, -1, NULL, -1, 0),
	(14, 'Áo nhung (nam)', 0, 6, 0, 1, 29, 350, 0, 40, 1, 4, 0, 0, 0, 0, 0, 0, 25000, -1, 189, 14, 0, 0, -1, NULL, -1, 0),
	(15, 'Áo gấm (nữ)', 0, 9, 0, 2, 34, 390, 0, 45, 1, 4, 0, 0, 0, 0, 0, 0, 34000, -1, 190, 15, 0, 0, -1, NULL, -1, 0),
	(16, 'Áo gấm (nam)', 0, 8, 0, 1, 34, 390, 0, 45, 1, 4, 0, 0, 0, 0, 0, 0, 34000, -1, 191, 16, 0, 0, -1, NULL, -1, 0),
	(17, 'Khoái linh thượng y (nữ)', 0, 9, 0, 2, 39, 420, 0, 50, 0, 4, 0, 0, 0, 0, 0, 0, 40000, -1, 192, 17, 0, 0, -1, NULL, -1, 0),
	(18, 'Khoái linh thượng y (nam)', 0, 8, 0, 1, 39, 420, 0, 50, 0, 4, 0, 0, 0, 0, 0, 0, 40000, -1, 193, 18, 0, 0, -1, NULL, -1, 0),
	(19, 'Cẩm trác y (nữ)', 0, 11, 0, 2, 44, 440, 0, 55, 0, 4, 0, 0, 0, 0, 0, 0, 44000, -1, 194, 19, 0, 0, -1, NULL, -1, 0),
	(20, 'Cẩm trác y (nam)', 0, 10, 0, 1, 44, 440, 0, 55, 0, 4, 0, 0, 0, 0, 0, 0, 44000, -1, 195, 20, 0, 0, -1, NULL, -1, 0),
	(21, 'Hổ bào (nữ)', 0, 11, 0, 2, 49, 460, 0, 60, 0, 5, 0, 0, 0, 0, 0, 0, 49000, -1, 196, 21, 0, 0, -1, NULL, -1, 0),
	(22, 'Hổ bào (nam)', 0, 10, 0, 1, 49, 460, 0, 60, 0, 5, 0, 0, 0, 0, 0, 0, 49000, -1, 197, 22, 0, 0, -1, NULL, -1, 0),
	(23, 'Sư vương bào (nữ)', 0, 13, 0, 2, 54, 470, 0, 65, 0, 5, 0, 0, 0, 0, 0, 0, 54000, -1, 198, 23, 0, 0, -1, NULL, -1, 0),
	(24, 'Sư vương bào (nam)', 0, 12, 0, 1, 54, 470, 0, 65, 0, 5, 0, 0, 0, 0, 0, 0, 54000, -1, 199, 24, 0, 0, -1, NULL, -1, 0),
	(25, 'Kim long bào (nữ)', 0, 13, 0, 2, 59, 480, 0, 70, 0, 5, 0, 0, 0, 0, 0, 0, 59000, -1, 200, 25, 0, 0, -1, NULL, -1, 0),
	(26, 'Kim long bào (nam)', 0, 12, 0, 1, 59, 480, 0, 70, 0, 5, 0, 0, 0, 0, 0, 0, 59000, -1, 201, 26, 0, 0, -1, NULL, -1, 0),
	(27, 'Quần bà ba', 1, 1, 0, 2, 1, 200, 0, 7, 0, 0, 0, 0, 0, 0, 0, 0, 500, -1, 202, 27, 0, 0, -1, NULL, -1, 0),
	(28, 'Quần đùi', 1, 0, 0, 1, 1, 200, 0, 7, 0, 0, 0, 0, 0, 0, 0, 0, 500, -1, 203, 28, 0, 0, -1, NULL, -1, 0),
	(29, 'Quần đai thô (nữ)', 1, 3, 0, 2, 4, 250, 0, 12, 0, 1, 0, 0, 0, 0, 0, 0, 800, -1, 204, 29, 0, 0, -1, NULL, -1, 0),
	(30, 'Quần đai thô (nam)', 1, 2, 0, 1, 4, 250, 0, 12, 0, 1, 0, 0, 0, 0, 0, 0, 800, -1, 205, 30, 0, 0, -1, NULL, -1, 0),
	(31, 'Quần thổ cẩm (nữ)', 1, 3, 0, 2, 9, 270, 0, 17, 0, 1, 0, 0, 0, 0, 0, 0, 1000, -1, 206, 31, 0, 0, -1, NULL, -1, 0),
	(32, 'Quần thổ cẩm (nam)', 1, 2, 0, 1, 9, 270, 0, 17, 0, 1, 0, 0, 0, 0, 0, 0, 1000, -1, 207, 32, 0, 0, -1, NULL, -1, 0),
	(33, 'Quần bông (nữ)', 1, 5, 0, 2, 14, 290, 0, 22, 0, 2, 0, 0, 0, 0, 0, 0, 3000, -1, 208, 33, 0, 0, -1, NULL, -1, 0),
	(34, 'Quần bông (nam)', 1, 4, 0, 1, 14, 290, 0, 22, 0, 2, 0, 0, 0, 0, 0, 0, 3000, -1, 209, 34, 0, 0, -1, NULL, -1, 0),
	(35, 'Quần lụa (nữ)', 1, 5, 0, 2, 19, 310, 0, 27, 0, 2, 0, 0, 0, 0, 0, 0, 8000, -1, 210, 35, 0, 0, -1, NULL, -1, 0),
	(36, 'Quần lụa (nam)', 1, 4, 0, 1, 19, 310, 0, 27, 0, 2, 0, 0, 0, 0, 0, 0, 8000, -1, 211, 36, 0, 0, -1, NULL, -1, 0),
	(37, 'Quần tơ tằm (nữ)', 1, 7, 0, 2, 24, 330, 0, 32, 0, 2, 0, 0, 0, 0, 0, 0, 14000, -1, 212, 37, 0, 0, -1, NULL, -1, 0),
	(38, 'Quần tơ tằm (nam)', 1, 6, 0, 1, 24, 330, 0, 32, 0, 2, 0, 0, 0, 0, 0, 0, 14000, -1, 213, 38, 0, 0, -1, NULL, -1, 0),
	(39, 'Quần nhung (nữ)', 1, 7, 0, 2, 29, 360, 0, 37, 0, 3, 0, 0, 0, 0, 0, 0, 19000, -1, 214, 39, 0, 0, -1, NULL, -1, 0),
	(40, 'Quần nhung (nam)', 1, 6, 0, 1, 29, 360, 0, 37, 0, 3, 0, 0, 0, 0, 0, 0, 19000, -1, 215, 40, 0, 0, -1, NULL, -1, 0),
	(41, 'Quần gấm (nữ)', 1, 9, 0, 2, 34, 400, 0, 42, 0, 3, 0, 0, 0, 0, 0, 0, 24000, -1, 216, 41, 0, 0, -1, NULL, -1, 0),
	(42, 'Quần gấm (nam)', 1, 8, 0, 1, 34, 400, 0, 42, 0, 3, 0, 0, 0, 0, 0, 0, 24000, -1, 217, 42, 0, 0, -1, NULL, -1, 0),
	(43, 'Quần khoái linh (nữ)', 1, 9, 0, 2, 39, 420, 0, 47, 0, 3, 0, 0, 0, 0, 0, 0, 29000, -1, 218, 43, 0, 0, -1, NULL, -1, 0),
	(44, 'Quần khoái linh (nam)', 1, 8, 0, 1, 39, 420, 0, 47, 0, 3, 0, 0, 0, 0, 0, 0, 29000, -1, 219, 44, 0, 0, -1, NULL, -1, 0),
	(45, 'Quần cẩm trác (nữ)', 1, 11, 0, 2, 44, 440, 0, 52, 0, 3, 0, 0, 0, 0, 0, 0, 34000, -1, 220, 45, 0, 0, -1, NULL, -1, 0),
	(46, 'Quần cẩm trác (nam)', 1, 10, 0, 1, 44, 440, 0, 52, 0, 3, 0, 0, 0, 0, 0, 0, 34000, -1, 221, 46, 0, 0, -1, NULL, -1, 0),
	(47, 'Hổ mao (nữ)', 1, 11, 0, 2, 49, 450, 0, 57, 0, 4, 0, 0, 0, 0, 0, 0, 40000, -1, 222, 47, 0, 0, -1, NULL, -1, 0),
	(48, 'Hổ mao (nam)', 1, 10, 0, 1, 49, 450, 0, 57, 0, 4, 0, 0, 0, 0, 0, 0, 40000, -1, 223, 48, 0, 0, -1, NULL, -1, 0),
	(49, 'Sư vương mao (nữ)', 1, 13, 0, 2, 54, 460, 0, 62, 0, 4, 0, 0, 0, 0, 0, 0, 45000, -1, 224, 49, 0, 0, -1, NULL, -1, 0),
	(50, 'Sư vương mao (nam)', 1, 12, 0, 1, 54, 460, 0, 62, 0, 4, 0, 0, 0, 0, 0, 0, 45000, -1, 225, 50, 0, 0, -1, NULL, -1, 0),
	(51, 'Quần kim long (nữ)', 1, 13, 0, 2, 59, 480, 0, 67, 0, 4, 0, 0, 0, 0, 0, 0, 50000, -1, 226, 51, 0, 0, -1, NULL, -1, 0),
	(52, 'Quần kim long (nam)', 1, 12, 0, 1, 59, 480, 0, 67, 0, 4, 0, 0, 0, 0, 0, 0, 50000, -1, 227, 52, 0, 0, -1, NULL, -1, 0),
	(53, 'Băng đô', 2, 1, 0, 2, 1, 200, 0, 5, 0, 1, 0, 0, 0, 0, 0, 0, 500, -1, 228, 53, 0, 0, -1, NULL, -1, 0),
	(54, 'Khăn', 2, 0, 0, 1, 1, 200, 0, 5, 0, 1, 0, 0, 0, 0, 0, 0, 500, -1, 229, 54, 0, 0, -1, NULL, -1, 0),
	(55, 'Nón dây gai (nữ)', 2, 3, 0, 2, 4, 250, 0, 10, 0, 2, 0, 0, 0, 0, 0, 0, 800, -1, 230, 55, 0, 0, -1, NULL, -1, 0),
	(56, 'Nón dây gai (nam)', 2, 2, 0, 1, 4, 250, 0, 10, 0, 2, 0, 0, 0, 0, 0, 0, 800, -1, 231, 56, 0, 0, -1, NULL, -1, 0),
	(57, 'Nón lam trúc (nữ)', 2, 3, 0, 2, 9, 280, 0, 15, 0, 2, 0, 0, 0, 0, 0, 0, 1000, -1, 232, 57, 0, 0, -1, NULL, -1, 0),
	(58, 'Nón lam trúc (nam)', 2, 2, 0, 1, 9, 280, 0, 15, 0, 2, 0, 0, 0, 0, 0, 0, 1000, -1, 233, 58, 0, 0, -1, NULL, -1, 0),
	(59, 'Nón hắc trúc (nữ)', 2, 5, 0, 2, 14, 300, 0, 20, 0, 2, 0, 0, 0, 0, 0, 0, 2000, -1, 234, 59, 0, 0, -1, NULL, -1, 0),
	(60, 'Nón hắc trúc (nam)', 2, 4, 0, 1, 14, 300, 0, 20, 0, 2, 0, 0, 0, 0, 0, 0, 2000, -1, 235, 60, 0, 0, -1, NULL, -1, 0),
	(61, 'Nón mộc trúc (nữ)', 2, 5, 0, 2, 19, 320, 0, 25, 0, 2, 0, 0, 0, 0, 0, 0, 6000, -1, 236, 61, 0, 0, -1, NULL, -1, 0),
	(62, 'Nón mộc trúc (nam)', 2, 4, 0, 1, 19, 320, 0, 25, 0, 2, 0, 0, 0, 0, 0, 0, 6000, -1, 237, 62, 0, 0, -1, NULL, -1, 0),
	(63, 'Nón thanh vải (nữ)', 2, 7, 0, 2, 24, 350, 0, 30, 0, 3, 0, 0, 0, 0, 0, 0, 12000, -1, 238, 63, 0, 0, -1, NULL, -1, 0),
	(64, 'Nón thanh vải (nam)', 2, 6, 0, 1, 24, 350, 0, 30, 0, 3, 0, 0, 0, 0, 0, 0, 12000, -1, 239, 64, 0, 0, -1, NULL, -1, 0),
	(65, 'Nón đồng đen (nữ)', 2, 7, 0, 2, 29, 370, 0, 35, 0, 3, 0, 0, 0, 0, 0, 0, 18000, -1, 240, 65, 0, 0, -1, NULL, -1, 0),
	(66, 'Nón đồng đen (nam)', 2, 6, 0, 1, 29, 370, 0, 35, 0, 3, 0, 0, 0, 0, 0, 0, 18000, -1, 241, 66, 0, 0, -1, NULL, -1, 0),
	(67, 'Nón đồng đỏ (nữ)', 2, 9, 0, 2, 34, 390, 0, 40, 0, 3, 0, 0, 0, 0, 0, 0, 22000, -1, 242, 67, 0, 0, -1, NULL, -1, 0),
	(68, 'Nón đồng đỏ (nam)', 2, 8, 0, 1, 34, 390, 0, 40, 0, 3, 0, 0, 0, 0, 0, 0, 22000, -1, 243, 68, 0, 0, -1, NULL, -1, 0),
	(69, 'Nón sắt đen (nữ)', 2, 9, 0, 2, 39, 410, 0, 45, 0, 3, 0, 0, 0, 0, 0, 0, 26000, -1, 244, 69, 0, 0, -1, NULL, -1, 0),
	(70, 'Nón sắt đen (nam)', 2, 8, 0, 1, 39, 410, 0, 45, 0, 3, 0, 0, 0, 0, 0, 0, 26000, -1, 245, 70, 0, 0, -1, NULL, -1, 0),
	(71, 'Nón sắt xám (nữ)', 2, 11, 0, 2, 44, 430, 0, 50, 0, 3, 0, 0, 0, 0, 0, 0, 34000, -1, 246, 71, 0, 0, -1, NULL, -1, 0),
	(72, 'Nón sắt xám (nam)', 2, 10, 0, 1, 44, 430, 0, 50, 0, 3, 0, 0, 0, 0, 0, 0, 34000, -1, 247, 72, 0, 0, -1, NULL, -1, 0),
	(73, 'Tuyệt tâm mão (nữ)', 2, 11, 0, 2, 49, 450, 0, 55, 0, 3, 0, 0, 0, 0, 0, 0, 36000, -1, 248, 73, 0, 0, -1, NULL, -1, 0),
	(74, 'Tuyệt tâm mão (nam)', 2, 10, 0, 1, 49, 450, 0, 55, 0, 4, 0, 0, 0, 0, 0, 0, 36000, -1, 249, 74, 0, 0, -1, NULL, -1, 0),
	(75, 'Tùng linh mão (nữ)', 2, 13, 0, 2, 54, 480, 0, 60, 0, 4, 0, 0, 0, 0, 0, 0, 40000, -1, 250, 75, 0, 0, -1, NULL, -1, 0),
	(76, 'Tùng linh mão (nam)', 2, 12, 0, 1, 54, 480, 0, 60, 0, 4, 0, 0, 0, 0, 0, 0, 40000, -1, 251, 76, 0, 0, -1, NULL, -1, 0),
	(77, 'Kim long mão (nữ)', 2, 13, 0, 2, 59, 510, 0, 65, 0, 4, 0, 0, 0, 0, 0, 0, 45000, -1, 252, 77, 0, 0, -1, NULL, -1, 0),
	(78, 'Kim long mão (nam)', 2, 12, 0, 1, 59, 510, 0, 65, 0, 4, 0, 0, 0, 0, 0, 0, 45000, -1, 253, 78, 0, 0, -1, NULL, -1, 0),
	(79, 'Kiếm tre', 3, 0, 0, 0, 1, 500, 30, 0, 0, 1, 0, 0, 0, 0, 0, 0, 500, 0, 0, 0, 0, 0, -1, NULL, -1, 0),
	(80, 'Kiếm gỗ', 3, 0, 0, 0, 6, 550, 50, 0, 0, 4, 0, 0, 0, 0, 0, 0, 5000, 0, 25, 0, 1, 0, -1, NULL, -1, 0),
	(81, 'Kiếm đồng', 3, 0, 0, 0, 11, 600, 70, 0, 0, 4, 0, 0, 0, 0, 0, 0, 15000, 0, 50, 0, 2, 0, -1, NULL, -1, 0),
	(82, 'Kiếm sắt', 3, 1, 0, 0, 16, 650, 90, 0, 0, 8, 0, 0, 0, 0, 0, 0, 30000, 0, 1, 1, 0, 0, -1, NULL, -1, 0),
	(83, 'Mộ khúc kiếm', 3, 1, 0, 0, 21, 700, 110, 0, 0, 8, 0, 0, 0, 0, 0, 0, 45000, 0, 26, 1, 1, 0, -1, NULL, -1, 0),
	(86, 'Đao tre', 4, 0, 0, 0, 1, 400, 50, 0, 0, 1, 0, 0, 0, 0, 0, 0, 500, 1, 5, 5, 0, 0, -1, NULL, -1, 0),
	(87, 'Đao gỗ', 4, 0, 0, 0, 6, 450, 70, 0, 0, 2, 0, 0, 0, 0, 0, 0, 5000, 1, 30, 5, 1, 0, -1, NULL, -1, 0),
	(88, 'Đao đồng', 4, 0, 0, 0, 11, 500, 90, 0, 0, 2, 0, 0, 0, 0, 0, 0, 15000, 1, 55, 5, 2, 0, -1, NULL, -1, 0),
	(89, 'Đao sắt', 4, 1, 0, 0, 16, 600, 110, 0, 0, 3, 0, 0, 0, 0, 0, 0, 30000, 1, 6, 6, 0, 0, -1, NULL, -1, 0),
	(90, 'Đà đao', 4, 1, 0, 0, 21, 650, 130, 0, 0, 3, 0, 0, 0, 0, 0, 0, 45000, 1, 31, 6, 1, 0, -1, NULL, -1, 0),
	(93, 'Bút tre', 5, 0, 0, 0, 1, 1000, 70, 0, 0, 0, 0, 0, 0, 0, 0, 0, 500, 2, 10, 10, 0, 0, -1, NULL, -1, 0),
	(94, 'Bút gỗ', 5, 0, 0, 0, 6, 1100, 90, 0, 0, 1, 0, 0, 0, 0, 0, 0, 5000, 2, 35, 10, 1, 0, -1, NULL, -1, 0),
	(95, 'Bút đồng', 5, 0, 0, 0, 11, 1200, 110, 0, 0, 1, 0, 0, 0, 0, 0, 0, 15000, 2, 60, 10, 2, 0, -1, NULL, -1, 0),
	(96, 'Bút sắt', 5, 1, 0, 0, 16, 1300, 130, 0, 0, 2, 0, 0, 0, 0, 0, 0, 30000, 2, 11, 11, 0, 0, -1, NULL, -1, 0),
	(97, 'Cơ duyên bút', 5, 1, 0, 0, 21, 1400, 150, 0, 0, 2, 0, 0, 0, 0, 0, 0, 45000, 2, 36, 11, 1, 0, -1, NULL, -1, 0),
	(100, 'Thổ búa', 6, 0, 0, 0, 1, 450, 90, 0, 0, 0, 0, 0, 0, 0, 0, 0, 500, 3, 15, 15, 0, 0, -1, NULL, -1, 0),
	(101, 'Mộc búa', 6, 0, 0, 0, 6, 550, 110, 0, 0, 1, 0, 0, 0, 0, 0, 0, 5000, 3, 40, 15, 1, 0, -1, NULL, -1, 0),
	(102, 'Búa đồng', 6, 0, 0, 0, 11, 600, 130, 0, 0, 1, 0, 0, 0, 0, 0, 0, 15000, 3, 65, 15, 2, 0, -1, NULL, -1, 0),
	(103, 'Búa sắt', 6, 1, 0, 0, 16, 700, 150, 0, 0, 2, 0, 0, 0, 0, 0, 0, 30000, 3, 16, 16, 0, 0, -1, NULL, -1, 0),
	(104, 'Búa thủ ngưu', 6, 1, 0, 0, 21, 750, 170, 0, 0, 2, 0, 0, 0, 0, 0, 0, 45000, 3, 41, 16, 1, 0, -1, NULL, -1, 0),
	(107, 'Cung tre', 7, 0, 0, 0, 1, 800, 20, 0, 0, 5, 1, 0, 0, 0, 0, 0, 500, 4, 20, 20, 0, 0, -1, NULL, -1, 0),
	(108, 'Cung gỗ', 7, 0, 0, 0, 6, 900, 40, 0, 0, 7, 2, 0, 0, 0, 0, 0, 5000, 4, 45, 20, 1, 0, -1, NULL, -1, 0),
	(109, 'Cung đồng', 7, 0, 0, 0, 11, 1000, 60, 0, 0, 7, 3, 0, 0, 0, 0, 0, 15000, 4, 70, 20, 2, 0, -1, NULL, -1, 0),
	(110, 'Cung sắt', 7, 1, 0, 0, 16, 1100, 80, 0, 0, 10, 4, 0, 0, 0, 0, 0, 30000, 4, 21, 21, 0, 0, -1, NULL, -1, 0),
	(111, 'Bán nguyệt cung', 7, 1, 0, 0, 21, 1200, 100, 0, 0, 10, 5, 0, 0, 0, 0, 0, 45000, 4, 46, 21, 1, 0, -1, NULL, -1, 0),
	(105, 'Búa Mã Diện', 6, 1, 0, 0, 26, 800, 190, 0, 0, 2, 0, 0, 0, 0, 0, 0, 55000, 3, 66, 16, 2, 0, -1, NULL, -1, 0),
	(84, 'Lưu Chỉ Kiếm', 3, 1, 0, 0, 26, 800, 130, 0, 0, 8, 0, 0, 0, 0, 0, 0, 60000, 0, 51, 1, 2, 0, -1, NULL, -1, 0),
	(85, 'Khổng Tú Kiếm', 3, 2, 0, 0, 31, 900, 150, 0, 0, 8, 0, 0, 0, 0, 0, 0, 80000, 0, 2, 2, 0, 0, -1, NULL, -1, 0),
	(91, 'Tuệ Đao', 4, 1, 0, 0, 26, 700, 150, 0, 0, 4, 0, 0, 0, 0, 0, 0, 60000, 1, 56, 6, 2, 0, -1, NULL, -1, 0),
	(92, 'Khổng Trác Đao', 4, 2, 0, 0, 31, 800, 170, 0, 0, 4, 0, 0, 0, 0, 0, 0, 80000, 1, 7, 7, 0, 0, -1, NULL, -1, 0),
	(98, 'Mộ Danh Bút', 5, 1, 0, 0, 26, 1500, 170, 0, 0, 3, 0, 0, 0, 0, 0, 0, 60000, 2, 61, 11, 2, 0, -1, NULL, -1, 0),
	(99, 'Bãn Nhãn Bút', 5, 2, 0, 0, 31, 1600, 190, 0, 0, 3, 0, 0, 0, 0, 0, 0, 80000, 2, 12, 12, 0, 0, -1, NULL, -1, 0),
	(106, 'Búa Địa Tinh', 6, 2, 0, 0, 31, 850, 210, 0, 0, 3, 0, 0, 0, 0, 0, 0, 75000, 3, 17, 17, 0, 0, -1, NULL, -1, 0),
	(112, 'Kim Loan Cung', 7, 1, 0, 0, 26, 1250, 110, 0, 0, 12, 6, 0, 0, 0, 0, 0, 55000, 4, 71, 21, 2, 0, -1, NULL, -1, 0),
	(113, 'Lĩnh Nam Cung', 7, 2, 0, 0, 31, 1300, 120, 0, 0, 12, 7, 0, 0, 0, 0, 0, 75000, 4, 22, 22, 0, 0, -1, NULL, -1, 0),
	(138, 'Giày đai thô', 10, 12, 0, 0, 4, 250, 0, 12, 2, 0, 0, 0, 0, 0, 0, 0, 500, -1, 266, 12, 0, 0, -1, NULL, -1, 0),
	(115, 'Nhẫn lam ngọc ', 8, 0, 0, 0, 9, 280, 10, 0, 0, 1, 0, 0, 0, 0, 0, 0, 2000, -1, 254, 0, 0, 0, -1, NULL, -1, 0),
	(116, 'Nhẫn hoàng bảo ', 8, 1, 0, 0, 14, 300, 15, 0, 0, 2, 0, 0, 0, 0, 0, 0, 4000, -1, 255, 1, 0, 0, -1, NULL, -1, 0),
	(117, 'Nhẫn lam bảo', 8, 1, 0, 0, 19, 320, 20, 0, 0, 2, 0, 0, 0, 0, 0, 0, 10000, -1, 255, 1, 0, 0, -1, NULL, -1, 0),
	(118, 'Nhẫn cảm lãm ', 8, 2, 0, 0, 24, 350, 25, 0, 0, 3, 0, 0, 0, 0, 0, 0, 19000, -1, 256, 2, 0, 0, -1, NULL, -1, 0),
	(119, 'Nhẫn phù dung ', 8, 2, 0, 0, 29, 370, 30, 0, 0, 3, 0, 0, 0, 0, 0, 0, 31000, -1, 256, 2, 0, 0, -1, NULL, -1, 0),
	(120, 'Nhẫn phỉ thúy', 8, 3, 0, 0, 34, 390, 35, 0, 0, 4, 0, 0, 0, 0, 0, 0, 39000, -1, 257, 3, 0, 0, -1, NULL, -1, 0),
	(121, 'Nhẫn thúy lựu', 8, 3, 0, 0, 39, 410, 40, 0, 0, 4, 0, 0, 0, 0, 0, 0, 45000, -1, 257, 3, 0, 0, -1, NULL, -1, 0),
	(122, 'Nhẫn tổ mẫu', 8, 4, 0, 0, 44, 430, 45, 0, 0, 5, 0, 0, 0, 0, 0, 0, 50000, -1, 258, 4, 0, 0, -1, NULL, -1, 0),
	(123, 'Nhẫn hải lam', 8, 4, 0, 0, 49, 450, 50, 0, 0, 5, 0, 0, 0, 0, 0, 0, 59000, -1, 258, 4, 0, 0, -1, NULL, -1, 0),
	(124, 'Nhẫn hồng bảo', 8, 5, 0, 0, 54, 480, 55, 0, 0, 6, 0, 0, 0, 0, 0, 0, 65000, -1, 259, 5, 0, 0, -1, NULL, -1, 0),
	(125, 'Nhẫn sa thạch', 8, 5, 0, 0, 59, 510, 60, 0, 0, 6, 0, 0, 0, 0, 0, 0, 71000, -1, 259, 5, 0, 0, -1, NULL, -1, 0),
	(126, 'Dây chuyền đồng', 9, 6, 0, 0, 4, 250, 7, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1500, -1, 260, 6, 0, 0, -1, NULL, -1, 0),
	(127, 'Dây chuyền san hô', 9, 6, 0, 0, 9, 280, 12, 0, 0, 0, 2, 0, 0, 0, 0, 0, 2500, -1, 260, 6, 0, 0, -1, NULL, -1, 0),
	(128, 'Dây chuyền lục tùng', 9, 7, 0, 0, 14, 300, 17, 0, 0, 0, 2, 0, 0, 0, 0, 0, 4500, -1, 261, 7, 0, 0, -1, NULL, -1, 0),
	(129, 'Dây chuyền miêu nhãn', 9, 7, 0, 0, 19, 320, 22, 0, 0, 0, 3, 0, 0, 0, 0, 0, 12000, -1, 261, 7, 0, 0, -1, NULL, -1, 0),
	(130, 'Dây chuyền bạch kim', 9, 8, 0, 0, 24, 350, 27, 0, 0, 0, 3, 0, 0, 0, 0, 0, 21000, -1, 262, 8, 0, 0, -1, NULL, -1, 0),
	(131, 'Dây chuyền thủy tinh', 9, 8, 0, 0, 29, 370, 32, 0, 0, 0, 3, 0, 0, 0, 0, 0, 34000, -1, 262, 8, 0, 0, -1, NULL, -1, 0),
	(132, 'Dây chuyền ngọc châu', 9, 9, 0, 0, 34, 390, 37, 0, 0, 0, 4, 0, 0, 0, 0, 0, 41000, -1, 263, 9, 0, 0, -1, NULL, -1, 0),
	(133, 'Dây chuyền hổ phách', 9, 9, 0, 0, 39, 410, 42, 0, 0, 0, 4, 0, 0, 0, 0, 0, 47000, -1, 263, 9, 0, 0, -1, NULL, -1, 0),
	(134, 'Dây chuyền ngân châu', 9, 10, 0, 0, 44, 430, 47, 0, 0, 0, 4, 0, 0, 0, 0, 0, 52000, -1, 264, 10, 0, 0, -1, NULL, -1, 0),
	(135, 'Dây chuyền phỉ thúy', 9, 10, 0, 0, 49, 450, 52, 0, 0, 0, 5, 0, 0, 0, 0, 0, 61000, -1, 264, 10, 0, 0, -1, NULL, -1, 0),
	(136, 'Dây chuyền toàn thạch', 9, 11, 0, 0, 54, 480, 57, 0, 0, 0, 5, 0, 0, 0, 0, 0, 67000, -1, 265, 11, 0, 0, -1, NULL, -1, 0),
	(137, 'Dây chuyền khổng tước', 9, 11, 0, 0, 59, 510, 62, 0, 0, 0, 5, 0, 0, 0, 0, 0, 75000, -1, 265, 11, 0, 0, -1, NULL, -1, 0),
	(114, 'Nhẫn hoàng ngọc ', 8, 0, 0, 0, 4, 250, 5, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1000, -1, 254, 0, 0, 0, -1, NULL, -1, 0),
	(139, 'Giày thổ cẩm', 10, 12, 0, 0, 9, 280, 0, 17, 2, 0, 0, 0, 0, 0, 0, 0, 1000, -1, 266, 12, 0, 0, -1, NULL, -1, 0),
	(140, 'Giày bông', 10, 13, 0, 0, 14, 300, 0, 22, 3, 0, 0, 0, 0, 0, 0, 0, 2000, -1, 267, 13, 0, 0, -1, NULL, -1, 0),
	(141, 'Giày lụa', 10, 13, 0, 0, 19, 320, 0, 27, 3, 0, 0, 0, 0, 0, 0, 0, 5000, -1, 267, 13, 0, 0, -1, NULL, -1, 0),
	(142, 'Giày tơ tằm', 10, 14, 0, 0, 24, 350, 0, 32, 4, 0, 0, 0, 0, 0, 0, 0, 13000, -1, 268, 14, 0, 0, -1, NULL, -1, 0),
	(143, 'Giày nhung', 10, 14, 0, 0, 29, 370, 0, 37, 4, 0, 0, 0, 0, 0, 0, 0, 19000, -1, 268, 14, 0, 0, -1, NULL, -1, 0),
	(144, 'Giày gấm', 10, 15, 0, 0, 34, 390, 0, 42, 5, 0, 0, 0, 0, 0, 0, 0, 23000, -1, 269, 15, 0, 0, -1, NULL, -1, 0),
	(145, 'Giày khoái linh', 10, 15, 0, 0, 39, 410, 0, 47, 5, 0, 0, 0, 0, 0, 0, 0, 29000, -1, 269, 15, 0, 0, -1, NULL, -1, 0),
	(146, 'Giày cẩm trác', 10, 16, 0, 0, 44, 430, 0, 52, 6, 0, 0, 0, 0, 0, 0, 0, 34000, -1, 270, 16, 0, 0, -1, NULL, -1, 0),
	(147, 'Giày da hổ', 10, 16, 0, 0, 49, 450, 0, 57, 6, 0, 0, 0, 0, 0, 0, 0, 39000, -1, 270, 16, 0, 0, -1, NULL, -1, 0),
	(148, 'Giày sư vương', 10, 17, 0, 0, 54, 480, 0, 62, 7, 0, 0, 0, 0, 0, 0, 0, 45000, -1, 271, 17, 0, 0, -1, NULL, -1, 0),
	(149, 'Giày kim long', 10, 17, 0, 0, 59, 510, 0, 67, 7, 0, 0, 0, 0, 0, 0, 0, 50000, -1, 271, 17, 0, 0, -1, NULL, -1, 0),
	(150, 'Găng đai thô', 11, 18, 0, 0, 4, 250, 0, 10, 0, 1, 1, 0, 0, 0, 0, 0, 500, -1, 272, 18, 0, 0, -1, NULL, -1, 0),
	(151, 'Găng thổ cẩm', 11, 18, 0, 0, 9, 280, 0, 15, 0, 1, 1, 0, 0, 0, 0, 0, 1000, -1, 272, 18, 0, 0, -1, NULL, -1, 0),
	(152, 'Găng bông', 11, 19, 0, 0, 14, 300, 0, 20, 0, 2, 2, 0, 0, 0, 0, 0, 2000, -1, 273, 19, 0, 0, -1, NULL, -1, 0),
	(153, 'Găng lụa', 11, 19, 0, 0, 19, 320, 0, 25, 0, 2, 2, 0, 0, 0, 0, 0, 6000, -1, 273, 19, 0, 0, -1, NULL, -1, 0),
	(154, 'Găng tơ tằm', 11, 20, 0, 0, 24, 350, 0, 30, 0, 3, 2, 0, 0, 0, 0, 0, 12000, -1, 274, 20, 0, 0, -1, NULL, -1, 0),
	(155, 'Găng nhung', 11, 20, 0, 0, 29, 370, 0, 35, 0, 3, 3, 0, 0, 0, 0, 0, 18000, -1, 274, 20, 0, 0, -1, NULL, -1, 0),
	(156, 'Găng gấm', 11, 21, 0, 0, 34, 390, 0, 40, 0, 4, 3, 0, 0, 0, 0, 0, 22000, -1, 275, 21, 0, 0, -1, NULL, -1, 0),
	(157, 'Găng khoái linh', 11, 21, 0, 0, 39, 410, 0, 45, 0, 4, 3, 0, 0, 0, 0, 0, 26000, -1, 275, 21, 0, 0, -1, NULL, -1, 0),
	(158, 'Găng cẩm trác', 11, 22, 0, 0, 44, 430, 0, 50, 0, 5, 4, 0, 0, 0, 0, 0, 34000, -1, 276, 22, 0, 0, -1, NULL, -1, 0),
	(159, 'Găng da hổ', 11, 22, 0, 0, 49, 450, 0, 55, 0, 5, 4, 0, 0, 0, 0, 0, 36000, -1, 276, 22, 0, 0, -1, NULL, -1, 0),
	(160, 'Găng sư vương', 11, 23, 0, 0, 54, 480, 0, 60, 0, 6, 4, 0, 0, 0, 0, 0, 40000, -1, 277, 23, 0, 0, -1, NULL, -1, 0),
	(161, 'Găng kim long', 11, 23, 0, 0, 59, 510, 0, 65, 0, 6, 5, 0, 0, 0, 0, 0, 45000, -1, 277, 23, 0, 0, -1, NULL, -1, 0),
	(162, 'Ngọc Bích ngọc', 12, 25, 0, 0, 4, 250, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 2000, -1, 279, 25, 0, 0, -1, NULL, -1, 0),
	(163, 'Ngọc Ngân bích', 12, 25, 0, 0, 9, 280, 0, 0, 0, 1, 2, 2, 0, 0, 0, 0, 3000, -1, 279, 25, 0, 0, -1, NULL, -1, 0),
	(164, 'Ngọc Tứ tuyệt', 12, 26, 0, 0, 14, 300, 0, 0, 0, 2, 2, 3, 0, 0, 0, 0, 5000, -1, 280, 26, 0, 0, -1, NULL, -1, 0),
	(165, 'Ngọc Hoàng phủ', 12, 26, 0, 0, 19, 320, 0, 0, 0, 2, 3, 4, 0, 0, 0, 0, 15000, -1, 280, 26, 0, 0, -1, NULL, -1, 0),
	(166, 'Ngọc Hồ điệp', 12, 27, 0, 0, 24, 350, 0, 0, 0, 3, 3, 5, 0, 0, 0, 0, 25000, -1, 281, 27, 0, 0, -1, NULL, -1, 0),
	(167, 'Ngọc Lam hoa', 12, 27, 0, 0, 29, 370, 0, 0, 0, 3, 3, 6, 0, 0, 0, 0, 35000, -1, 281, 27, 0, 0, -1, NULL, -1, 0),
	(168, 'Ngọc Túy tiên', 12, 28, 0, 0, 34, 390, 0, 0, 0, 4, 4, 7, 0, 0, 0, 0, 45000, -1, 282, 28, 0, 0, -1, NULL, -1, 0),
	(169, 'Ngọc Viên xích', 12, 28, 0, 0, 39, 410, 0, 0, 0, 4, 4, 8, 0, 0, 0, 0, 55000, -1, 282, 28, 0, 0, -1, NULL, -1, 0),
	(170, 'Ngọc Ấu nguyệt', 12, 29, 0, 0, 44, 430, 0, 0, 0, 5, 4, 9, 0, 0, 0, 0, 65000, -1, 283, 29, 0, 0, -1, NULL, -1, 0),
	(171, 'Ngọc Đào hoa', 12, 29, 0, 0, 49, 450, 0, 0, 0, 5, 5, 10, 0, 0, 0, 0, 75000, -1, 283, 29, 0, 0, -1, NULL, -1, 0),
	(172, 'Ngọc Mai hoa', 12, 30, 0, 0, 54, 480, 0, 0, 0, 6, 5, 11, 0, 0, 0, 0, 85000, -1, 284, 30, 0, 0, -1, NULL, -1, 0),
	(173, 'Ngọc Long tiên', 12, 30, 0, 0, 59, 510, 0, 0, 0, 6, 5, 12, 0, 0, 0, 0, 95000, -1, 284, 30, 0, 0, -1, NULL, -1, 0),
	(174, 'Thanh Phong Kiếm', 3, 2, 0, 0, 36, 900, 180, 0, 0, 8, 0, 0, 0, 0, 0, 0, 110000, 0, 27, 2, 1, 0, -1, NULL, -1, 0),
	(175, 'Long Tuyền Kiếm', 3, 2, 0, 0, 41, 900, 200, 0, 0, 8, 0, 0, 0, 0, 0, 0, 140000, 0, 52, 2, 2, 0, -1, NULL, -1, 0),
	(176, 'Thất Xích Kiếm', 3, 3, 0, 0, 46, 900, 230, 0, 0, 8, 0, 0, 0, 0, 0, 0, 170000, 0, 3, 3, 0, 0, -1, NULL, -1, 0),
	(177, 'Vạn Hoa Kiếm', 3, 3, 0, 0, 51, 900, 240, 0, 0, 8, 0, 0, 0, 0, 0, 0, 200000, 0, 28, 3, 1, 0, -1, NULL, -1, 0),
	(178, 'Huyền Thiết Kiếm', 3, 3, 0, 0, 56, 900, 260, 0, 0, 8, 0, 0, 0, 0, 0, 0, 230000, 0, 53, 3, 2, 0, -1, NULL, -1, 0),
	(179, 'Hoàn Tử Kiếm', 3, 4, 0, 0, 61, 900, 280, 0, 0, 8, 0, 0, 0, 0, 0, 0, 260000, 0, 4, 4, 0, 0, -1, NULL, -1, 0),
	(180, 'Báo Vĩ Kiếm', 3, 4, 0, 0, 66, 900, 300, 0, 0, 8, 0, 0, 0, 0, 0, 0, 290000, 0, 29, 4, 1, 0, -1, NULL, -1, 0),
	(181, 'Nhạn Linh Kiếm', 3, 4, 0, 0, 71, 900, 320, 0, 0, 8, 0, 0, 0, 0, 0, 0, 320000, 0, 54, 4, 2, 0, -1, NULL, -1, 0),
	(182, 'Quỷ Đầu Đao', 4, 2, 0, 0, 36, 800, 190, 0, 0, 4, 0, 0, 0, 0, 0, 0, 120000, 1, 32, 7, 1, 0, -1, NULL, -1, 0),
	(183, 'Nguyệt Nha Đao', 4, 2, 0, 0, 41, 800, 210, 0, 0, 4, 0, 0, 0, 0, 0, 0, 150000, 1, 57, 7, 2, 0, -1, NULL, -1, 0),
	(184, 'Phá Phong Đao', 4, 3, 0, 0, 46, 800, 230, 0, 0, 4, 0, 0, 0, 0, 0, 0, 180000, 1, 8, 8, 0, 0, -1, NULL, -1, 0),
	(185, 'Vạn Linh Đao', 4, 3, 0, 0, 51, 800, 250, 0, 0, 4, 0, 0, 0, 0, 0, 0, 210000, 1, 33, 8, 1, 0, -1, NULL, -1, 0),
	(186, 'Cổn Châu Đao', 4, 3, 0, 0, 56, 800, 270, 0, 0, 4, 0, 0, 0, 0, 0, 0, 240000, 1, 58, 8, 2, 0, -1, NULL, -1, 0),
	(187, 'Đại Phong Đao', 4, 4, 0, 0, 61, 800, 290, 0, 0, 4, 0, 0, 0, 0, 0, 0, 270000, 1, 9, 9, 0, 0, -1, NULL, -1, 0),
	(188, 'Điểm Cang Đao', 4, 4, 0, 0, 66, 800, 310, 0, 0, 4, 0, 0, 0, 0, 0, 0, 300000, 1, 34, 9, 1, 0, -1, NULL, -1, 0),
	(189, 'Hỏa Diệm Đao', 4, 4, 0, 0, 71, 800, 330, 0, 0, 4, 0, 0, 0, 0, 0, 0, 330000, 1, 59, 9, 2, 0, -1, NULL, -1, 0),
	(190, 'Kim Qua Bút', 5, 2, 0, 0, 36, 1600, 200, 0, 0, 3, 0, 0, 0, 0, 0, 0, 120000, 2, 37, 12, 1, 0, -1, NULL, -1, 0),
	(191, 'Đào Hoa Bút', 5, 2, 0, 0, 41, 1600, 225, 0, 0, 3, 0, 0, 0, 0, 0, 0, 155000, 2, 62, 12, 2, 0, -1, NULL, -1, 0),
	(192, 'Tâm Ý Bút', 5, 3, 0, 0, 46, 1600, 245, 0, 0, 3, 0, 0, 0, 0, 0, 0, 190000, 2, 13, 13, 0, 0, -1, NULL, -1, 0),
	(193, 'Độc Cơ Bút', 5, 3, 0, 0, 51, 1600, 265, 0, 0, 3, 0, 0, 0, 0, 0, 0, 225000, 2, 38, 13, 1, 0, -1, NULL, -1, 0),
	(194, 'Bạo Vũ Bút', 5, 3, 0, 0, 56, 1600, 285, 0, 0, 3, 0, 0, 0, 0, 0, 0, 260000, 2, 63, 13, 2, 0, -1, NULL, -1, 0),
	(195, 'Tề Mi Bút', 5, 4, 0, 0, 61, 1600, 305, 0, 0, 3, 0, 0, 0, 0, 0, 0, 295000, 2, 14, 14, 0, 0, -1, NULL, -1, 0),
	(196, 'Hỗn Thiết Bút', 5, 4, 0, 0, 66, 1600, 325, 0, 0, 3, 0, 0, 0, 0, 0, 0, 320000, 2, 39, 14, 1, 0, -1, NULL, -1, 0),
	(197, 'Thu Vũ Bút', 5, 4, 0, 0, 71, 1600, 345, 0, 0, 3, 0, 0, 0, 0, 0, 0, 355000, 2, 64, 14, 2, 0, -1, NULL, -1, 0),
	(198, 'Tú Cốt Búa', 6, 2, 0, 0, 36, 850, 230, 0, 0, 3, 0, 0, 0, 0, 0, 0, 105000, 3, 42, 17, 1, 0, -1, NULL, -1, 0),
	(199, 'Toán Đầu Búa', 6, 2, 0, 0, 41, 850, 250, 0, 0, 3, 0, 0, 0, 0, 0, 0, 135000, 3, 67, 17, 2, 0, -1, NULL, -1, 0),
	(200, 'Bát Lăng Búa', 6, 3, 0, 0, 46, 850, 270, 0, 0, 3, 0, 0, 0, 0, 0, 0, 165000, 3, 18, 18, 0, 0, -1, NULL, -1, 0),
	(201, 'Ngũ Tinh Búa', 6, 3, 0, 0, 51, 850, 290, 0, 0, 3, 0, 0, 0, 0, 0, 0, 195000, 3, 43, 18, 1, 0, -1, NULL, -1, 0),
	(202, 'Tích Lịch Búa', 6, 3, 0, 0, 56, 850, 310, 0, 0, 3, 0, 0, 0, 0, 0, 0, 225000, 3, 68, 18, 2, 0, -1, NULL, -1, 0),
	(203, 'Lượng Ngân Búa', 6, 4, 0, 0, 61, 850, 330, 0, 0, 3, 0, 0, 0, 0, 0, 0, 255000, 3, 19, 19, 0, 0, -1, NULL, -1, 0),
	(204, 'Liệt Tâm Búa', 6, 4, 0, 0, 66, 850, 350, 0, 0, 3, 0, 0, 0, 0, 0, 0, 285000, 3, 44, 19, 1, 0, -1, NULL, -1, 0),
	(205, 'Nghiệt Long Búa', 6, 4, 0, 0, 71, 850, 370, 0, 0, 3, 0, 0, 0, 0, 0, 0, 315000, 3, 69, 19, 2, 0, -1, NULL, -1, 0),
	(206, 'Kim Tiền Cung', 7, 2, 0, 0, 36, 1300, 150, 0, 0, 12, 0, 0, 0, 0, 0, 0, 100000, 4, 47, 22, 1, 0, -1, NULL, -1, 0),
	(207, 'Yến Tử Cung', 7, 2, 0, 0, 41, 1300, 170, 0, 0, 12, 0, 0, 0, 0, 0, 0, 130000, 4, 72, 22, 2, 0, -1, NULL, -1, 0),
	(208, 'Phi Hoàng Cung', 7, 3, 0, 0, 46, 1300, 190, 0, 0, 12, 0, 0, 0, 0, 0, 0, 160000, 4, 23, 23, 0, 0, -1, NULL, -1, 0),
	(209, 'Liễu Diệp Cung', 7, 3, 0, 0, 51, 1300, 210, 0, 0, 12, 0, 0, 0, 0, 0, 0, 190000, 4, 48, 23, 1, 0, -1, NULL, -1, 0),
	(210, 'Lượng Ngân Cung', 7, 3, 0, 0, 56, 1300, 230, 0, 0, 12, 0, 0, 0, 0, 0, 0, 220000, 4, 73, 23, 2, 0, -1, NULL, -1, 0),
	(211, 'Hồ Điệp Cung', 7, 4, 0, 0, 61, 1300, 240, 0, 0, 12, 0, 0, 0, 0, 0, 0, 250000, 4, 24, 24, 0, 0, -1, NULL, -1, 0),
	(212, 'Nhạn Vĩ Cung', 7, 4, 0, 0, 66, 1300, 260, 0, 0, 12, 0, 0, 0, 0, 0, 0, 280000, 4, 49, 24, 1, 0, -1, NULL, -1, 0),
	(213, 'Phá Thiên Cung ', 7, 4, 0, 0, 71, 1300, 280, 0, 0, 12, 0, 0, 0, 0, 0, 0, 310000, 4, 74, 24, 2, 0, -1, NULL, -1, 0),
	(214, 'Mộ khúc kiếm_thuê', 3, 1, 0, 0, 21, 700, 193, 10, 10, 13, 10, 10, 0, 0, 0, 0, 10, 0, 100, 0, 4, 4320, -1, NULL, -1, 0),
	(215, 'Lưu Chỉ Kiếm_thuê', 3, 1, 0, 0, 26, 800, 219, 10, 10, 14, 10, 10, 0, 0, 0, 0, 15, 0, 100, 0, 4, 4320, -1, NULL, -1, 0),
	(216, 'Khổng Tú Kiếm_thuê', 3, 2, 0, 0, 31, 900, 244, 10, 10, 15, 10, 10, 0, 0, 0, 0, 20, 0, 101, 1, 4, 4320, -1, NULL, -1, 0),
	(217, 'Thanh Phong Kiếm_thuê', 3, 2, 0, 0, 36, 900, 281, 10, 10, 15, 10, 10, 0, 0, 0, 0, 25, 0, 101, 1, 4, 4320, -1, NULL, -1, 0),
	(218, 'Long Tuyền Kiếm_thuê', 3, 2, 0, 0, 41, 900, 306, 10, 10, 15, 10, 10, 0, 0, 0, 0, 30, 0, 102, 2, 4, 4320, -1, NULL, -1, 0),
	(219, 'Thất Xích Kiếm_thuê', 3, 3, 0, 0, 46, 900, 343, 10, 10, 15, 10, 10, 0, 0, 0, 0, 40, 0, 102, 2, 4, 4320, -1, NULL, -1, 0),
	(220, 'Vạn Hoa Kiếm_thuê', 3, 3, 0, 0, 51, 900, 356, 10, 10, 15, 10, 10, 0, 0, 0, 0, 50, 0, 103, 3, 4, 4320, -1, NULL, -1, 0),
	(221, 'Huyền Thiết Kiếm_thuê', 3, 3, 0, 0, 56, 900, 381, 10, 10, 15, 10, 10, 0, 0, 0, 0, 60, 0, 103, 3, 4, 4320, -1, NULL, -1, 0),
	(222, 'Hoàn Tử Kiếm_thuê', 3, 4, 0, 0, 61, 900, 406, 10, 10, 15, 10, 10, 0, 0, 0, 0, 70, 0, 104, 4, 4, 4320, -1, NULL, -1, 0),
	(223, 'Báo Vĩ Kiếm_thuê', 3, 4, 0, 0, 66, 900, 431, 10, 10, 15, 10, 10, 0, 0, 0, 0, 80, 0, 104, 4, 4, 4320, -1, NULL, -1, 0),
	(224, 'Đà đao_thuê', 4, 1, 0, 0, 21, 650, 218, 10, 10, 13, 10, 10, 0, 0, 0, 0, 10, 1, 105, 5, 4, 4320, -1, NULL, -1, 0),
	(225, 'Tuệ Đao_thuê', 4, 1, 0, 0, 26, 700, 243, 10, 10, 14, 10, 10, 0, 0, 0, 0, 15, 1, 105, 5, 4, 4320, -1, NULL, -1, 0),
	(226, 'Khổng Trác Đao_thuê', 4, 2, 0, 0, 31, 800, 268, 10, 10, 15, 10, 10, 0, 0, 0, 0, 20, 1, 106, 6, 4, 4320, -1, NULL, -1, 0),
	(227, 'Quỷ Đầu Đao_thuê', 4, 2, 0, 0, 36, 800, 293, 10, 10, 15, 10, 10, 0, 0, 0, 0, 25, 1, 106, 6, 4, 4320, -1, NULL, -1, 0),
	(228, 'Nguyệt Nha Đao_thuê', 4, 2, 0, 0, 41, 800, 318, 10, 10, 15, 10, 10, 0, 0, 0, 0, 30, 1, 107, 7, 4, 4320, -1, NULL, -1, 0),
	(229, 'Phá Phong Đao_thuê', 4, 3, 0, 0, 46, 800, 343, 10, 10, 15, 10, 10, 0, 0, 0, 0, 40, 1, 107, 7, 4, 4320, -1, NULL, -1, 0),
	(230, 'Vạn Linh Đao_thuê', 4, 3, 0, 0, 51, 800, 368, 10, 10, 15, 10, 10, 0, 0, 0, 0, 50, 1, 108, 8, 4, 4320, -1, NULL, -1, 0),
	(231, 'Cổn Châu Đao_thuê', 4, 3, 0, 0, 56, 800, 393, 10, 10, 15, 10, 10, 0, 0, 0, 0, 60, 1, 108, 8, 4, 4320, -1, NULL, -1, 0),
	(232, 'Đại Phong Đao_thuê', 4, 4, 0, 0, 61, 800, 418, 10, 10, 15, 10, 10, 0, 0, 0, 0, 70, 1, 109, 9, 4, 4320, -1, NULL, -1, 0),
	(233, 'Điểm Cang Đao_thuê', 4, 4, 0, 0, 66, 800, 443, 10, 10, 15, 10, 10, 0, 0, 0, 0, 80, 1, 109, 9, 4, 4320, -1, NULL, -1, 0),
	(234, 'Cơ duyên bút_thuê', 5, 1, 0, 0, 21, 1400, 243, 10, 10, 13, 10, 10, 0, 0, 0, 0, 10, 2, 110, 10, 4, 4320, -1, NULL, -1, 0),
	(235, 'Mộ Danh Bút_thuê', 5, 1, 0, 0, 26, 1500, 268, 10, 10, 10, 10, 10, 0, 0, 0, 0, 15, 2, 110, 10, 4, 4320, -1, NULL, -1, 0),
	(236, 'Bãn Nhãn Bút_thuê', 5, 2, 0, 0, 31, 1600, 293, 10, 10, 15, 10, 10, 0, 0, 0, 0, 20, 2, 111, 11, 4, 4320, -1, NULL, -1, 0),
	(237, 'Kim Qua Bút_thuê', 5, 2, 0, 0, 36, 1600, 306, 10, 10, 15, 10, 10, 0, 0, 0, 0, 25, 2, 111, 11, 4, 4320, -1, NULL, -1, 0),
	(238, 'Đào Hoa Bút_thuê', 5, 2, 0, 0, 41, 1600, 337, 10, 10, 15, 10, 10, 0, 0, 0, 0, 30, 2, 112, 12, 4, 4320, -1, NULL, -1, 0),
	(239, 'Tâm Ý Bút_thuê', 5, 3, 0, 0, 46, 1600, 372, 10, 10, 15, 10, 10, 0, 0, 0, 0, 40, 2, 112, 12, 4, 4320, -1, NULL, -1, 0),
	(240, 'Độc Cơ Bút_thuê', 5, 3, 0, 0, 51, 1600, 387, 10, 10, 15, 10, 10, 0, 0, 0, 0, 50, 2, 113, 13, 4, 4320, -1, NULL, -1, 0),
	(241, 'Bạo Vũ Bút_thuê', 5, 3, 0, 0, 56, 1600, 412, 10, 10, 15, 10, 10, 0, 0, 0, 0, 60, 2, 113, 13, 4, 4320, -1, NULL, -1, 0),
	(242, 'Tề Mi Bút_thuê', 5, 4, 0, 0, 61, 1600, 437, 10, 10, 15, 10, 10, 0, 0, 0, 0, 70, 2, 114, 14, 4, 4320, -1, NULL, -1, 0),
	(243, 'Hỗn Thiết Bút_thuê', 5, 4, 0, 0, 66, 1600, 472, 10, 10, 15, 10, 10, 0, 0, 0, 0, 80, 2, 114, 14, 4, 4320, -1, NULL, -1, 0),
	(244, 'Búa thủ ngưu_thuê', 6, 1, 0, 0, 21, 750, 268, 10, 10, 13, 10, 10, 0, 0, 0, 0, 10, 3, 115, 15, 4, 4320, -1, NULL, -1, 0),
	(245, 'Búa Mã Diện_thuê', 6, 1, 0, 0, 26, 800, 293, 10, 10, 14, 10, 10, 0, 0, 0, 0, 15, 3, 115, 15, 4, 4320, -1, NULL, -1, 0),
	(246, 'Búa Địa Tinh_thuê', 6, 2, 0, 0, 31, 8500, 318, 10, 10, 15, 10, 10, 0, 0, 0, 0, 20, 3, 116, 16, 4, 4320, -1, NULL, -1, 0),
	(247, 'Tú Cốt Búa_thuê', 6, 2, 0, 0, 36, 8500, 343, 10, 10, 13, 10, 10, 0, 0, 0, 0, 25, 3, 116, 16, 4, 4320, -1, NULL, -1, 0),
	(248, 'Toán Đầu Búa_thuê', 6, 2, 0, 0, 41, 8500, 368, 10, 10, 10, 10, 10, 0, 0, 0, 0, 30, 3, 117, 17, 4, 4320, -1, NULL, -1, 0),
	(249, 'Bát Lăng Búa_thuê', 6, 3, 0, 0, 46, 8500, 393, 10, 10, 10, 10, 10, 0, 0, 0, 0, 40, 3, 117, 17, 4, 4320, -1, NULL, -1, 0),
	(250, 'Ngũ Tinh Búa_thuê', 6, 3, 0, 0, 51, 8500, 418, 10, 10, 10, 10, 10, 0, 0, 0, 0, 50, 3, 118, 18, 4, 4320, -1, NULL, -1, 0),
	(251, 'Tích Lịch Búa_thuê', 6, 3, 0, 0, 56, 850, 443, 10, 10, 10, 10, 10, 0, 0, 0, 0, 60, 3, 118, 18, 4, 4320, -1, NULL, -1, 0),
	(252, 'Lượng Ngân Búa_thuê', 6, 4, 0, 0, 61, 8500, 468, 10, 10, 10, 10, 10, 0, 0, 0, 0, 70, 3, 119, 19, 4, 4320, -1, NULL, -1, 0),
	(253, 'Liệt Tâm Búa_thuê', 6, 4, 0, 0, 66, 8500, 493, 10, 10, 10, 10, 10, 0, 0, 0, 0, 80, 3, 119, 19, 4, 4320, -1, NULL, -1, 0),
	(254, 'Bán nguyệt cung_thuê', 7, 1, 0, 0, 21, 1200, 181, 10, 10, 15, 10, 10, 0, 0, 0, 0, 10, 4, 120, 20, 4, 4320, -1, NULL, -1, 0),
	(255, 'Kim Loan Cung_thuê', 7, 1, 0, 0, 26, 1250, 194, 10, 10, 16, 10, 10, 0, 0, 0, 0, 15, 4, 120, 20, 4, 4320, -1, NULL, -1, 0),
	(256, 'Lĩnh Nam Cung_thuê', 7, 2, 0, 0, 31, 1300, 206, 10, 10, 17, 10, 10, 0, 0, 0, 0, 20, 4, 121, 21, 4, 4320, -1, NULL, -1, 0),
	(257, 'Kim Tiền Cung_thuê', 7, 2, 0, 0, 36, 1300, 244, 10, 10, 17, 10, 10, 0, 0, 0, 0, 25, 4, 121, 21, 4, 4320, -1, NULL, -1, 0),
	(258, 'Yến Tử Cung_thuê', 7, 2, 0, 0, 41, 1300, 269, 10, 10, 17, 10, 10, 0, 0, 0, 0, 30, 4, 122, 22, 4, 4320, -1, NULL, -1, 0),
	(259, 'Phi Hoàng Cung_thuê', 7, 3, 0, 0, 46, 1300, 294, 10, 10, 17, 10, 10, 0, 0, 0, 0, 40, 4, 122, 22, 4, 4320, -1, NULL, -1, 0),
	(260, 'Liễu Diệp Cung_thuê', 7, 3, 0, 0, 51, 1300, 318, 10, 10, 17, 10, 10, 0, 0, 0, 0, 50, 4, 123, 23, 4, 4320, -1, NULL, -1, 0),
	(261, 'Lượng Ngân Cung_thuê', 7, 3, 0, 0, 56, 1300, 343, 10, 10, 17, 10, 10, 0, 0, 0, 0, 60, 4, 123, 23, 4, 4320, -1, NULL, -1, 0),
	(262, 'Hồ Điệp Cung_thuê', 7, 4, 0, 0, 61, 1300, 356, 10, 10, 17, 10, 10, 0, 0, 0, 0, 70, 4, 124, 24, 4, 4320, -1, NULL, -1, 0),
	(263, 'Nhạn Vĩ Cung_thuê', 7, 4, 0, 0, 66, 1300, 381, 10, 10, 17, 10, 10, 0, 0, 0, 0, 80, 4, 124, 24, 4, 4320, -1, NULL, -1, 0),
	(267, 'Áo dài', 0, 37, 0, 2, 1, 5000, 6, 6, 3000, 3000, 14, 14, 14, 14, 14, 1, 100, -1, 285, 31, 0, 10080, -1, NULL, -1, 0),
	(264, 'Áo tiên nữ', 0, 38, 0, 2, 1, 5000, 6, 6, 3000, 3000, 19, 19, 19, 19, 19, 1, 100, -1, 286, 32, 0, 10080, -1, NULL, -1, 0),
	(265, 'Áo Lạc long quân', 0, 39, 0, 1, 1, 5000, 10, 10, 3000, 3000, 15, 15, 15, 15, 15, 1, 100, -1, 287, 33, 0, 10080, -1, NULL, -1, 0),
	(266, 'Áo thầy đồ', 0, 40, 0, 1, 1, 5000, 6, 6, 3000, 3000, 18, 18, 18, 18, 18, 1, 100, -1, 288, 34, 0, 10080, -1, NULL, -1, 0),
	(268, 'Áo Thanh Tuyệt (nữ)', 0, 22, 0, 2, 20, 1300, 0, 75, 0, 6, 0, 0, 0, 0, 0, 0, 25000, -1, 289, 35, 0, 0, -1, NULL, -1, 0),
	(269, 'Áo Thanh Tuyệt (nam)', 0, 34, 0, 1, 20, 1300, 0, 75, 0, 6, 0, 0, 0, 0, 0, 0, 25000, -1, 290, 36, 0, 0, -1, NULL, -1, 0),
	(270, 'Áo Thạch Lựu (nữ)', 0, 22, 0, 2, 25, 1300, 0, 80, 0, 6, 0, 0, 0, 0, 0, 0, 25000, -1, 289, 35, 0, 0, -1, NULL, -1, 0),
	(271, 'Áo Thạch Lựu (nam)', 0, 34, 0, 1, 25, 1300, 0, 80, 0, 6, 0, 0, 0, 0, 0, 0, 25000, -1, 290, 36, 0, 0, -1, NULL, -1, 0),
	(272, 'Áo Dật Hiệp (nữ)', 0, 22, 0, 2, 30, 1300, 0, 85, 0, 7, 0, 0, 0, 0, 0, 0, 25000, -1, 289, 35, 0, 0, -1, NULL, -1, 0),
	(273, 'Áo Dật Hiệp (nam)', 0, 34, 0, 1, 30, 1300, 0, 85, 0, 7, 0, 0, 0, 0, 0, 0, 25000, -1, 290, 36, 0, 0, -1, NULL, -1, 0),
	(274, 'Áo Linh Xà (nữ)', 0, 22, 0, 2, 35, 1300, 0, 90, 0, 7, 0, 0, 0, 0, 0, 0, 25000, -1, 291, 37, 0, 0, -1, NULL, -1, 0),
	(275, 'Áo Linh Xà (nam)', 0, 34, 0, 1, 35, 1300, 0, 90, 0, 7, 0, 0, 0, 0, 0, 0, 25000, -1, 292, 38, 0, 0, -1, NULL, -1, 0),
	(276, 'Áo Vô sắc (nữ)', 0, 23, 0, 2, 40, 1300, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 25000, -1, 291, 37, 0, 0, -1, NULL, -1, 0),
	(277, 'Áo Vô sắc (nam)', 0, 35, 0, 1, 40, 1300, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 25000, -1, 292, 38, 0, 0, -1, NULL, -1, 0),
	(278, 'Áo Tâm ma (nữ)', 0, 23, 0, 2, 45, 1300, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 25000, -1, 291, 37, 0, 0, -1, NULL, -1, 0),
	(279, 'Áo Tâm ma (nam)', 0, 35, 0, 1, 45, 1300, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 25000, -1, 292, 38, 0, 0, -1, NULL, -1, 0),
	(280, 'Áo  Hắc báo (nữ)', 0, 23, 0, 2, 50, 1300, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 25000, -1, 293, 39, 0, 0, -1, NULL, -1, 0),
	(281, 'Áo Hắc báo (nam)', 0, 35, 0, 1, 50, 1300, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 25000, -1, 294, 40, 0, 0, -1, NULL, -1, 0),
	(282, 'Áo Băng tinh (nữ)', 0, 24, 0, 2, 55, 1300, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 25000, -1, 293, 39, 0, 0, -1, NULL, -1, 0),
	(283, 'Áo Băng tinh (nam)', 0, 36, 0, 1, 55, 1300, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 25000, -1, 294, 40, 0, 0, -1, NULL, -1, 0),
	(284, 'Áo Thất bảo (nữ)', 0, 24, 0, 2, 60, 1300, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 25000, -1, 293, 39, 0, 0, -1, NULL, -1, 0),
	(285, 'Áo Thất bảo (nam)', 0, 36, 0, 1, 60, 1300, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 25000, -1, 294, 40, 0, 0, -1, NULL, -1, 0),
	(286, 'Áo Liên vân (nữ)', 0, 24, 0, 2, 65, 1300, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 25000, -1, 295, 41, 0, 0, -1, NULL, -1, 0),
	(287, 'Áo Liên vân (nam)', 0, 36, 0, 1, 65, 1300, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 25000, -1, 296, 42, 0, 0, -1, NULL, -1, 0),
	(288, 'Áo Ngân tơ (nữ)', 0, 25, 0, 2, 70, 1300, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 25000, -1, 295, 41, 0, 0, -1, NULL, -1, 0),
	(289, 'Áo Ngân tơ (nam)', 0, 37, 0, 1, 70, 1300, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 25000, -1, 296, 42, 0, 0, -1, NULL, -1, 0),
	(290, 'Áo Vô cực (nữ)', 0, 25, 0, 2, 75, 1300, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 25000, -1, 295, 41, 0, 0, 640, NULL, -1, 0),
	(291, 'Áo Vô cực (nam)', 0, 37, 0, 1, 75, 1300, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 25000, -1, 296, 42, 0, 0, 639, NULL, -1, 0),
	(292, 'Quần Thanh tuyệt (nữ)', 1, 22, 0, 2, 20, 1300, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 25000, -1, 313, 59, 0, 0, -1, NULL, -1, 0),
	(293, 'Quần Thanh tuyệt (nam)', 1, 34, 0, 1, 20, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, -1, 314, 60, 0, 0, -1, NULL, -1, 0),
	(294, 'Quần Thạch lựu (nữ)', 1, 22, 0, 2, 25, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, -1, 313, 59, 0, 0, -1, NULL, -1, 0),
	(295, 'Quần Thạch lựu (nam)', 1, 34, 0, 1, 25, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, -1, 314, 60, 0, 0, -1, NULL, -1, 0),
	(296, 'Quần Dật Hiệp (nữ)', 1, 22, 0, 2, 30, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, -1, 313, 59, 0, 0, -1, NULL, -1, 0),
	(297, 'Quần Dật Hiệp (nam)', 1, 34, 0, 1, 30, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, -1, 314, 60, 0, 0, -1, NULL, -1, 0),
	(298, 'Quần Linh xà (nữ)', 1, 22, 0, 2, 35, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, -1, 315, 61, 0, 0, -1, NULL, -1, 0),
	(299, 'Quần Linh xà (nam)', 1, 34, 0, 1, 35, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, -1, 316, 62, 0, 0, -1, NULL, -1, 0),
	(300, 'Quần Vô sắc (nữ)', 1, 23, 0, 2, 40, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, -1, 315, 61, 0, 0, -1, NULL, -1, 0),
	(301, 'Quần Vô sắc (nam)', 1, 35, 0, 1, 40, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, -1, 316, 62, 0, 0, -1, NULL, -1, 0),
	(302, 'Quần Tâm ma (nữ)', 1, 23, 0, 2, 45, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, -1, 315, 61, 0, 0, -1, NULL, -1, 0),
	(303, 'Quần Tâm ma (nam)', 1, 35, 0, 1, 45, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, -1, 316, 62, 0, 0, -1, NULL, -1, 0),
	(304, 'Quần Hắc báo (nữ)', 1, 23, 0, 2, 50, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, -1, 317, 63, 0, 0, -1, NULL, -1, 0),
	(305, 'Quần Hắc báo (nam)', 1, 35, 0, 1, 50, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, -1, 318, 64, 0, 0, -1, NULL, -1, 0),
	(306, 'Quần Băng tinh (nữ)', 1, 24, 0, 2, 55, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, -1, 317, 63, 0, 0, -1, NULL, -1, 0),
	(307, 'Quần Băng tinh (nam)', 1, 36, 0, 1, 55, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, -1, 318, 64, 0, 0, -1, NULL, -1, 0),
	(308, 'Quần Thất bảo (nữ)', 1, 24, 0, 2, 60, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, -1, 317, 63, 0, 0, -1, NULL, -1, 0),
	(309, 'Quần Thất bảo (nam)', 1, 36, 0, 1, 60, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, -1, 318, 64, 0, 0, -1, NULL, -1, 0),
	(310, 'Quần Liên vân (nữ)', 1, 24, 0, 2, 65, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, -1, 319, 65, 0, 0, -1, NULL, -1, 0),
	(311, 'Quần Liên vân (nam)', 1, 36, 0, 1, 65, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, -1, 320, 66, 0, 0, -1, NULL, -1, 0),
	(312, 'Quần Ngân tơ (nữ)', 1, 25, 0, 2, 70, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, -1, 319, 65, 0, 0, -1, NULL, -1, 0),
	(313, 'Quần Ngân tơ (nam)', 1, 37, 0, 1, 70, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, -1, 320, 66, 0, 0, -1, NULL, -1, 0),
	(314, 'Quần Vô cưc (nữ)', 1, 25, 0, 2, 75, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, -1, 319, 65, 0, 0, 644, NULL, -1, 0),
	(315, 'Quần Vô cưc (nam)', 1, 37, 0, 1, 75, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, -1, 320, 66, 0, 0, 643, NULL, -1, 0),
	(316, 'Nón Thanh tuyệt (nữ)', 2, 22, 0, 2, 20, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, -1, 337, 5, 0, 0, -1, NULL, -1, 0),
	(317, 'Nón Thanh tuyệt (nam)', 2, 34, 0, 1, 20, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, -1, 338, 6, 0, 0, -1, NULL, -1, 0),
	(318, 'Nón Thạch lựu (nữ)', 2, 22, 0, 2, 25, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, -1, 337, 5, 0, 0, -1, NULL, -1, 0),
	(319, 'Nón Thạch lựu (nam)', 2, 34, 0, 1, 25, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, -1, 338, 6, 0, 0, -1, NULL, -1, 0),
	(320, 'Nón Dật hiệp (nữ)', 2, 22, 0, 2, 30, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, -1, 337, 5, 0, 0, -1, NULL, -1, 0),
	(321, 'Nón Dật hiệp (nam)', 2, 34, 0, 1, 30, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, -1, 338, 6, 0, 0, -1, NULL, -1, 0),
	(322, 'Nón Linh xà (nữ)', 2, 22, 0, 2, 35, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, -1, 339, 7, 0, 0, -1, NULL, -1, 0),
	(323, 'Nón Linh xà (nam)', 2, 34, 0, 1, 35, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, -1, 340, 8, 0, 0, -1, NULL, -1, 0),
	(324, 'Nón vô sắc (nữ)', 2, 23, 0, 2, 40, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, -1, 339, 7, 0, 0, -1, NULL, -1, 0),
	(325, 'Nón vô sắc (nam)', 2, 35, 0, 1, 40, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, -1, 340, 8, 0, 0, -1, NULL, -1, 0),
	(326, 'Nón Tâm ma (nữ)', 2, 23, 0, 2, 45, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, -1, 339, 7, 0, 0, -1, NULL, -1, 0),
	(327, 'Nón Tâm ma (nam)', 2, 35, 0, 1, 45, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, -1, 340, 8, 0, 0, -1, NULL, -1, 0),
	(328, 'Nón Hắc báo (nữ)', 2, 23, 0, 2, 50, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, -1, 341, 9, 0, 0, -1, NULL, -1, 0),
	(329, 'Nón Hắc báo (nam)', 2, 35, 0, 1, 50, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, -1, 342, 10, 0, 0, -1, NULL, -1, 0),
	(330, 'Nón Băng tinh (nữ)', 2, 24, 0, 2, 55, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, -1, 341, 9, 0, 0, -1, NULL, -1, 0),
	(331, 'Nón Băng tinh (nam)', 2, 36, 0, 1, 55, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, -1, 342, 10, 0, 0, -1, NULL, -1, 0),
	(332, 'Nón Thất bảo (nữ)', 2, 24, 0, 2, 60, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, -1, 341, 9, 0, 0, -1, NULL, -1, 0),
	(333, 'Nón Thất bảo (nam)', 2, 36, 0, 1, 60, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, -1, 342, 10, 0, 0, -1, NULL, -1, 0),
	(334, 'Nón Liên vân (nữ)', 2, 24, 0, 2, 65, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, -1, 343, 11, 0, 0, -1, NULL, -1, 0),
	(335, 'Nón Liên vân (nam)', 2, 36, 0, 1, 65, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, -1, 344, 12, 0, 0, -1, NULL, -1, 0),
	(336, 'Nón Ngân tơ (nữ)', 2, 25, 0, 2, 70, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, -1, 343, 11, 0, 0, -1, NULL, -1, 0),
	(337, 'Nón Ngân tơ (nam)', 2, 37, 0, 1, 70, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, -1, 344, 12, 0, 0, -1, NULL, -1, 0),
	(338, 'Nón Vô cực (nữ)', 2, 25, 0, 2, 75, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, -1, 343, 11, 0, 0, 648, NULL, -1, 0),
	(339, 'Nón Vô cực (nam)', 2, 37, 0, 1, 75, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, -1, 344, 12, 0, 0, 647, NULL, -1, 0),
	(352, 'Găng Thanh Tuyệt', 11, 18, 0, 0, 20, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, -1, 373, 41, 0, 0, -1, NULL, -1, 0),
	(357, 'Găng Tâm ma', 11, 20, 0, 0, 45, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, -1, 374, 42, 0, 0, -1, NULL, -1, 0),
	(356, 'Găng Vô sắc', 11, 20, 0, 0, 40, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, -1, 374, 42, 0, 0, -1, NULL, -1, 0),
	(355, 'Găng Linh xà', 11, 19, 0, 0, 35, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, -1, 374, 42, 0, 0, -1, NULL, -1, 0),
	(354, 'Găng Dật Hiệp', 11, 19, 0, 0, 30, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, -1, 373, 41, 0, 0, -1, NULL, -1, 0),
	(353, 'Găng Thạch lựu', 11, 18, 0, 0, 25, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, -1, 373, 41, 0, 0, -1, NULL, -1, 0),
	(358, 'Găng Hắc báo', 11, 21, 0, 0, 50, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, -1, 375, 43, 0, 0, -1, NULL, -1, 0),
	(359, 'Găng Băng tinh', 11, 21, 0, 0, 55, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, -1, 375, 43, 0, 0, -1, NULL, -1, 0),
	(360, 'Găng Thất bảo', 11, 22, 0, 0, 60, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, -1, 375, 43, 0, 0, -1, NULL, -1, 0),
	(361, 'Găng Liên vân', 11, 22, 0, 0, 65, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, -1, 376, 44, 0, 0, -1, NULL, -1, 0),
	(362, 'Găng Ngân tơ', 11, 23, 0, 0, 70, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, -1, 376, 44, 0, 0, -1, NULL, -1, 0),
	(363, 'Găng Vô cực', 11, 23, 0, 0, 75, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, -1, 376, 44, 0, 0, 663, NULL, -1, 0),
	(340, 'Giày Thanh Tuyệt', 10, 12, 0, 0, 20, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, -1, 361, 29, 0, 0, -1, NULL, -1, 0),
	(341, 'Giày Thạch lựu', 10, 12, 0, 0, 25, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, -1, 361, 29, 0, 0, -1, NULL, -1, 0),
	(342, 'Giày Dật Hiệp', 10, 23, 0, 0, 30, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, -1, 361, 29, 0, 0, -1, NULL, -1, 0),
	(343, 'Giày Linh xà', 10, 13, 0, 0, 35, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, -1, 362, 30, 0, 0, -1, NULL, -1, 0),
	(344, 'Giày Vô sắc', 10, 14, 0, 0, 40, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, -1, 362, 30, 0, 0, -1, NULL, -1, 0),
	(345, 'Giày Tâm ma', 10, 14, 0, 0, 45, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, -1, 362, 30, 0, 0, -1, NULL, -1, 0),
	(346, 'Giày Hắc báo', 10, 15, 0, 0, 50, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, -1, 363, 31, 0, 0, -1, NULL, -1, 0),
	(347, 'Giày Băng tinh', 10, 15, 0, 0, 55, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, -1, 363, 31, 0, 0, -1, NULL, -1, 0),
	(348, 'Giày Thất bảo', 10, 16, 0, 0, 60, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, -1, 363, 31, 0, 0, -1, NULL, -1, 0),
	(349, 'Giày Liên vân', 10, 16, 0, 0, 65, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, -1, 364, 32, 0, 0, -1, NULL, -1, 0),
	(350, 'Giày Ngân tơ', 10, 17, 0, 0, 70, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, -1, 364, 32, 0, 0, -1, NULL, -1, 0),
	(351, 'Giày Vô cực', 10, 17, 0, 0, 75, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, -1, 364, 32, 0, 0, 659, NULL, -1, 0),
	(364, 'Nhẫn Toàn Thạch', 8, 0, 0, 0, 20, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, -1, 385, 53, 0, 0, -1, NULL, -1, 0),
	(365, 'Nhẫn Linh xà', 8, 0, 0, 0, 25, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, -1, 385, 53, 0, 0, -1, NULL, -1, 0),
	(366, 'Nhẫn Ngọc châu', 8, 1, 0, 0, 30, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, -1, 385, 53, 0, 0, -1, NULL, -1, 0),
	(367, 'Nhẫn Ngân châu', 8, 1, 0, 0, 35, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, -1, 386, 54, 0, 0, -1, NULL, -1, 0),
	(368, 'Nhẫn San hô', 8, 2, 0, 0, 40, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, -1, 386, 54, 0, 0, -1, NULL, -1, 0),
	(369, 'Nhẫn Ngũ sắc', 8, 2, 0, 0, 45, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, -1, 386, 54, 0, 0, -1, NULL, -1, 0),
	(370, 'Nhẫn Huyền tê', 8, 3, 0, 0, 50, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, -1, 387, 55, 0, 0, -1, NULL, -1, 0),
	(371, 'Nhẫn Khổng tượng', 8, 3, 0, 0, 55, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, -1, 387, 55, 0, 0, -1, NULL, -1, 0),
	(372, 'Nhẫn Âm dương', 8, 4, 0, 0, 60, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, -1, 387, 55, 0, 0, -1, NULL, -1, 0),
	(373, 'Nhẫn Nhật nguyệt', 8, 4, 0, 0, 65, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, -1, 388, 56, 0, 0, -1, NULL, -1, 0),
	(374, 'Nhẫn Song phụng', 8, 5, 0, 0, 70, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, -1, 388, 56, 0, 0, -1, NULL, -1, 0),
	(375, 'Nhẫn Song long', 8, 5, 0, 0, 75, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, -1, 388, 56, 0, 0, 651, NULL, -1, 0),
	(376, 'Dây chuyền Toàn Thạch', 9, 6, 0, 0, 20, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, -1, 397, 65, 0, 0, -1, NULL, -1, 0),
	(377, 'Dây chuyền Linh xà', 9, 6, 0, 0, 25, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, -1, 397, 65, 0, 0, -1, NULL, -1, 0),
	(378, 'Dây chuyền Ngọc châu', 9, 7, 0, 0, 30, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, -1, 397, 65, 0, 0, -1, NULL, -1, 0),
	(379, 'Dây chuyền Ngân châu', 9, 7, 0, 0, 35, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, -1, 398, 66, 0, 0, -1, NULL, -1, 0),
	(380, 'Dây chuyền San hô', 9, 8, 0, 0, 40, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, -1, 398, 66, 0, 0, -1, NULL, -1, 0),
	(381, 'Dây chuyền Ngũ sắc', 9, 8, 0, 0, 45, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, -1, 398, 66, 0, 0, -1, NULL, -1, 0),
	(382, 'Dây chuyền Huyền tê', 9, 9, 0, 0, 50, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, -1, 399, 67, 0, 0, -1, NULL, -1, 0),
	(383, 'Dây chuyền Khổng tượng', 9, 9, 0, 0, 55, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, -1, 399, 67, 0, 0, -1, NULL, -1, 0),
	(384, 'Dây chuyền Âm dương', 9, 10, 0, 0, 60, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, -1, 399, 67, 0, 0, -1, NULL, -1, 0),
	(385, 'Dây chuyền Nhật nguyệt', 9, 10, 0, 0, 65, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, -1, 400, 68, 0, 0, -1, NULL, -1, 0),
	(386, 'Dây chuyền Song phụng', 9, 11, 0, 0, 70, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, -1, 400, 68, 0, 0, -1, NULL, -1, 0),
	(387, 'Dây chuyền Song long', 9, 11, 0, 0, 75, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, -1, 400, 68, 0, 0, 655, NULL, -1, 0),
	(388, 'Ngọc Toàn Thạch', 12, 25, 0, 0, 20, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, -1, 409, 0, 0, 0, -1, NULL, -1, 0),
	(389, 'Ngọc Linh xà', 12, 25, 0, 0, 25, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, -1, 409, 0, 0, 0, -1, NULL, -1, 0),
	(390, 'Ngọc Ngọc châu', 12, 26, 0, 0, 30, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, -1, 409, 0, 0, 0, -1, NULL, -1, 0),
	(391, 'Ngọc Ngân châu', 12, 26, 0, 0, 35, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, -1, 410, 1, 0, 0, -1, NULL, -1, 0),
	(392, 'Ngọc San hô', 12, 27, 0, 0, 40, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, -1, 410, 1, 0, 0, -1, NULL, -1, 0),
	(393, 'Ngọc Ngũ sắc', 12, 27, 0, 0, 45, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, -1, 410, 1, 0, 0, -1, NULL, -1, 0),
	(394, 'Ngọc Huyền tê', 12, 28, 0, 0, 50, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, -1, 411, 2, 0, 0, -1, NULL, -1, 0),
	(395, 'Ngọc Khổng tượng', 12, 28, 0, 0, 55, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, -1, 411, 2, 0, 0, -1, NULL, -1, 0),
	(396, 'Ngọc Âm dương', 12, 29, 0, 0, 60, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, -1, 411, 2, 0, 0, -1, NULL, -1, 0),
	(397, 'Ngọc Nhật nguyệt', 12, 29, 0, 0, 65, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, -1, 412, 3, 0, 0, -1, NULL, -1, 0),
	(398, 'Ngọc Song phụng', 12, 30, 0, 0, 70, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, -1, 412, 3, 0, 0, -1, NULL, -1, 0),
	(399, 'Ngọc Song long', 12, 30, 0, 0, 75, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, -1, 412, 3, 0, 0, 667, NULL, -1, 0),
	(413, 'Trảm mã đao', 4, 1, 0, 0, 21, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, 1, 106, 1, 4, 0, -1, NULL, -1, 0),
	(414, 'Điểm cang đao', 4, 1, 0, 0, 26, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, 1, 106, 1, 4, 0, -1, NULL, -1, 0),
	(415, 'Lang nha đao', 4, 1, 0, 0, 31, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, 1, 131, 1, 5, 0, -1, NULL, -1, 0),
	(416, 'Thập tự đao', 4, 1, 0, 0, 36, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, 1, 131, 1, 5, 0, -1, NULL, -1, 0),
	(417, 'Tinh trang đao', 4, 2, 0, 0, 41, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, 1, 107, 1, 4, 0, -1, NULL, -1, 0),
	(418, 'Đới y đao', 4, 2, 0, 0, 46, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, 1, 107, 1, 4, 0, -1, NULL, -1, 0),
	(419, 'Quan cán đao', 4, 2, 0, 0, 51, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, 1, 132, 1, 5, 0, -1, NULL, -1, 0),
	(420, 'Toái nguyệt đao', 4, 2, 0, 0, 56, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, 1, 132, 1, 5, 0, -1, NULL, -1, 0),
	(421, 'Thanh phong đao', 4, 3, 0, 0, 61, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, 1, 108, 1, 4, 0, -1, NULL, -1, 0),
	(422, 'Túc đế đao', 4, 3, 0, 0, 66, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, 1, 108, 1, 4, 0, -1, NULL, -1, 0),
	(423, 'Bôi trang đao', 4, 3, 0, 0, 71, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, 1, 133, 1, 5, 0, -1, NULL, -1, 0),
	(424, 'Bá vương đao', 4, 3, 0, 0, 76, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, 1, 133, 1, 5, 0, -1, NULL, -1, 0),
	(425, 'Gia cát đao', 4, 4, 0, 0, 81, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, 1, 109, 5, 4, 0, -1, NULL, -1, 0),
	(400, 'Khổng tước kiếm', 3, 1, 0, 0, 21, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, 0, 101, 1, 4, 0, -1, NULL, -1, 0),
	(401, 'Phù du kiếm', 3, 1, 0, 0, 26, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, 0, 101, 1, 4, 0, -1, NULL, -1, 0),
	(402, 'Thiêu hoả kiếm', 3, 1, 0, 0, 31, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, 0, 126, 1, 5, 0, -1, NULL, -1, 0),
	(403, 'Bàn hoa kiếm', 3, 1, 0, 0, 36, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, 0, 126, 1, 5, 0, -1, NULL, -1, 0),
	(404, 'Hồ vĩ kiếm', 3, 2, 0, 0, 41, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, 0, 102, 1, 4, 0, -1, NULL, -1, 0),
	(405, 'Giác hải kiếm', 3, 2, 0, 0, 46, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, 0, 102, 1, 4, 0, -1, NULL, -1, 0),
	(406, 'Kim cô kiếm', 3, 2, 0, 0, 51, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, 0, 127, 1, 5, 0, -1, NULL, -1, 0),
	(407, 'Tấn thiết kiếm', 3, 2, 0, 0, 56, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, 0, 127, 1, 5, 0, -1, NULL, -1, 0),
	(408, 'Hắc bạch kiếm', 3, 3, 0, 0, 61, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, 0, 103, 1, 4, 0, -1, NULL, -1, 0),
	(409, 'Tuyết hoa kiếm', 3, 3, 0, 0, 66, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, 0, 103, 1, 4, 0, -1, NULL, -1, 0),
	(411, 'Phù dung kiếm', 3, 3, 0, 0, 76, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, 0, 128, 1, 5, 0, -1, NULL, -1, 0),
	(412, 'Phá giáp kiếm', 3, 4, 0, 0, 81, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, 0, 79, 5, 3, 0, -1, NULL, -1, 0),
	(410, 'Uyên ương kiếm', 3, 3, 0, 0, 71, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, 0, 128, 1, 5, 0, -1, NULL, -1, 0),
	(439, 'Thôn nhật búa', 6, 1, 0, 0, 21, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, 3, 116, 1, 4, 0, -1, NULL, -1, 0),
	(440, 'Bán diện búa', 6, 1, 0, 0, 26, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, 3, 116, 1, 4, 0, -1, NULL, -1, 0),
	(441, 'Ngân liêm búa', 6, 1, 0, 0, 31, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, 3, 141, 1, 5, 0, -1, NULL, -1, 0),
	(442, 'Tim đồng búa', 6, 1, 0, 0, 36, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, 3, 141, 1, 5, 0, -1, NULL, -1, 0),
	(443, 'Hoàng bố búa', 6, 2, 0, 0, 41, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, 3, 117, 1, 4, 0, -1, NULL, -1, 0),
	(444, 'Hoàng đồng búa', 6, 2, 0, 0, 46, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, 3, 117, 1, 4, 0, -1, NULL, -1, 0),
	(445, 'Càn khôn búa', 6, 2, 0, 0, 51, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, 3, 142, 1, 5, 0, -1, NULL, -1, 0),
	(446, 'Thẩm lộ búa', 6, 2, 0, 0, 56, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, 3, 142, 1, 5, 0, -1, NULL, -1, 0),
	(447, 'Song diện búa', 6, 3, 0, 0, 61, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, 3, 118, 1, 4, 0, -1, NULL, -1, 0),
	(448, 'Tiêu diêu búa', 6, 3, 0, 0, 66, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, 3, 118, 1, 4, 0, -1, NULL, -1, 0),
	(449, 'Bố nhung búa', 6, 3, 0, 0, 71, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, 3, 143, 1, 5, 0, -1, NULL, -1, 0),
	(450, 'Phật ấn búa', 6, 3, 0, 0, 76, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, 3, 143, 1, 5, 0, -1, NULL, -1, 0),
	(451, 'Viên đỉnh búa', 6, 4, 0, 0, 81, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, 3, 94, 1, 3, 0, -1, NULL, -1, 0),
	(426, 'Cân quản bút', 5, 1, 0, 0, 21, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, 2, 111, 1, 4, 0, -1, NULL, -1, 0),
	(427, 'Phi yến bút', 5, 1, 0, 0, 26, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, 2, 111, 1, 4, 0, -1, NULL, -1, 0),
	(428, 'Lộ thiên bút', 5, 1, 0, 0, 31, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, 2, 136, 1, 5, 0, -1, NULL, -1, 0),
	(429, 'Thiết khôi bút', 5, 1, 0, 0, 36, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, 2, 136, 1, 5, 0, -1, NULL, -1, 0),
	(430, 'Nhạn vũ bút', 5, 2, 0, 0, 41, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, 2, 112, 1, 4, 0, -1, NULL, -1, 0),
	(431, 'Ngân hoàn bút', 5, 2, 0, 0, 46, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, 2, 112, 1, 4, 0, -1, NULL, -1, 0),
	(432, 'Xích đồn bút', 5, 2, 0, 0, 51, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, 2, 137, 1, 5, 0, -1, NULL, -1, 0),
	(433, 'Tứ giác bút', 5, 2, 0, 0, 56, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, 2, 137, 1, 5, 0, -1, NULL, -1, 0),
	(434, 'Thanh văn bút', 5, 3, 0, 0, 61, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, 2, 113, 1, 4, 0, -1, NULL, -1, 0),
	(435, 'Khôn lộc bút', 5, 3, 0, 0, 66, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, 2, 113, 1, 4, 0, -1, NULL, -1, 0),
	(436, 'Huyền sa bút', 5, 3, 0, 0, 71, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, 2, 138, 1, 5, 0, -1, NULL, -1, 0),
	(437, 'Bạch trực bút', 5, 3, 0, 0, 76, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, 2, 138, 1, 5, 0, -1, NULL, -1, 0),
	(438, 'Y tơ bút', 5, 4, 0, 0, 81, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, 2, 89, 1, 3, 0, -1, NULL, -1, 0),
	(452, 'Kê vĩ cung', 7, 1, 0, 0, 21, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, 4, 121, 1, 4, 0, -1, NULL, -1, 0),
	(453, 'Thanh phụng cung', 7, 1, 0, 0, 26, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, 4, 121, 1, 4, 0, -1, NULL, -1, 0),
	(454, 'Hồng anh cung', 7, 1, 0, 0, 31, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, 4, 146, 1, 5, 0, -1, NULL, -1, 0),
	(455, 'Lâm liên cung', 7, 1, 0, 0, 36, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, 4, 146, 1, 5, 0, -1, NULL, -1, 0),
	(456, 'Càn khảm cung', 7, 2, 0, 0, 41, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, 4, 122, 1, 4, 0, -1, NULL, -1, 0),
	(457, 'Thanh sát cung', 7, 2, 0, 0, 46, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, 4, 122, 1, 4, 0, -1, NULL, -1, 0),
	(458, 'Lang tà cung', 7, 2, 0, 0, 51, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, 4, 147, 1, 5, 0, -1, NULL, -1, 0),
	(459, 'Lưu ly cung', 7, 2, 0, 0, 56, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, 4, 147, 1, 5, 0, -1, NULL, -1, 0),
	(460, 'Cang khôi cung', 7, 3, 0, 0, 61, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, 4, 123, 1, 4, 0, -1, NULL, -1, 0),
	(461, 'Kim tơ cung', 7, 3, 0, 0, 66, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, 4, 123, 1, 4, 0, -1, NULL, -1, 0),
	(462, 'Thanh anh cung', 7, 3, 0, 0, 71, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, 4, 148, 1, 5, 0, -1, NULL, -1, 0),
	(463, 'Tử cốt cung', 7, 3, 0, 0, 76, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, 4, 148, 1, 5, 0, -1, NULL, -1, 0),
	(464, 'Anh vũ cung', 7, 4, 0, 0, 81, 1300, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, 4, 99, 1, 3, 0, -1, NULL, -1, 0),
	(466, 'Cuốc xịn', 13, 0, 0, 0, 1, 1300, 5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 0, 278, 24, 0, 1440, -1, NULL, -1, 0),
	(465, 'Cuốc thường', 13, 0, 0, 0, 1, 1300, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 278, 24, 0, 1440, -1, NULL, -1, 0),
	(467, 'Phi phong bào(nữ)', 0, 13, 0, 2, 64, 480, 0, 75, 0, 5, 0, 0, 0, 0, 0, 0, 80000, -1, 200, 25, 0, 0, -1, NULL, -1, 0),
	(468, 'Phi phong bào(nam)', 0, 12, 0, 1, 64, 480, 0, 75, 0, 5, 0, 0, 0, 0, 0, 0, 80000, -1, 201, 26, 0, 0, -1, NULL, -1, 0),
	(469, 'Tử quang bào(nữ)', 0, 13, 0, 2, 69, 480, 0, 80, 0, 5, 0, 0, 0, 0, 0, 0, 100000, -1, 200, 25, 0, 0, -1, NULL, -1, 0),
	(470, 'Tử quang bào(nam)', 0, 12, 0, 1, 69, 480, 0, 80, 0, 5, 0, 0, 0, 0, 0, 0, 100000, -1, 201, 26, 0, 0, -1, NULL, -1, 0),
	(471, 'Đại hùng bào(nữ)', 0, 13, 0, 2, 74, 480, 0, 85, 0, 5, 0, 0, 0, 0, 0, 0, 120000, -1, 200, 25, 0, 0, -1, NULL, -1, 0),
	(472, 'Đại hùng bào(nữ)', 0, 12, 0, 1, 74, 480, 0, 85, 0, 5, 0, 0, 0, 0, 0, 0, 120000, -1, 201, 26, 0, 0, -1, NULL, -1, 0),
	(473, 'Phi yến bào (nam)', 0, 13, 0, 2, 79, 480, 0, 90, 0, 5, 0, 0, 0, 0, 0, 0, 140000, -1, 200, 25, 0, 0, -1, NULL, -1, 0),
	(474, 'Phi yến bào (nam)', 0, 12, 0, 1, 79, 480, 0, 90, 0, 5, 0, 0, 0, 0, 0, 0, 140000, -1, 201, 26, 0, 0, -1, NULL, -1, 0),
	(475, 'Quần phi long(nữ)', 1, 13, 0, 2, 64, 500, 0, 72, 0, 4, 0, 0, 0, 0, 0, 0, 70000, -1, 226, 51, 0, 0, -1, NULL, -1, 0),
	(476, 'Quần phi long(nam)', 1, 12, 0, 1, 64, 500, 0, 72, 0, 4, 0, 0, 0, 0, 0, 0, 70000, -1, 227, 52, 0, 0, -1, NULL, -1, 0),
	(477, 'Quần Tử quang(nữ)', 1, 13, 0, 2, 69, 500, 0, 77, 0, 4, 0, 0, 0, 0, 0, 0, 90000, -1, 226, 51, 0, 0, -1, NULL, -1, 0),
	(478, 'Quần Tử quang(nam)', 1, 12, 0, 1, 69, 500, 0, 77, 0, 4, 0, 0, 0, 0, 0, 0, 90000, -1, 227, 52, 0, 0, -1, NULL, -1, 0),
	(479, 'Quần Đại hùng(nữ)', 1, 13, 0, 2, 74, 500, 0, 82, 0, 4, 0, 0, 0, 0, 0, 0, 110000, -1, 226, 51, 0, 0, -1, NULL, -1, 0),
	(480, 'Quần Đại hùng(nam)', 1, 12, 0, 1, 74, 500, 0, 82, 0, 4, 0, 0, 0, 0, 0, 0, 110000, -1, 227, 52, 0, 0, -1, NULL, -1, 0),
	(481, 'Quần Phi yến(nữ)', 1, 13, 0, 2, 79, 500, 0, 87, 0, 4, 0, 0, 0, 0, 0, 0, 130000, -1, 226, 51, 0, 0, -1, NULL, -1, 0),
	(482, 'Quần Phi yến(nam)', 1, 12, 0, 1, 79, 500, 0, 87, 0, 4, 0, 0, 0, 0, 0, 0, 130000, -1, 227, 52, 0, 0, -1, NULL, -1, 0),
	(483, 'Phi long mão(nữ)', 2, 13, 0, 2, 64, 530, 0, 70, 0, 4, 0, 0, 0, 0, 0, 0, 65000, -1, 252, 77, -1, 0, -1, NULL, -1, 0),
	(484, 'Phi long mão(nam)', 2, 12, 0, 1, 64, 530, 0, 70, 0, 4, 0, 0, 0, 0, 0, 0, 65000, -1, 253, 78, -1, 0, -1, NULL, -1, 0),
	(485, 'Tử quang mão(nữ)', 2, 13, 0, 2, 69, 530, 0, 75, 0, 4, 0, 0, 0, 0, 0, 0, 85000, -1, 252, 77, -1, 0, -1, NULL, -1, 0),
	(486, 'Tử quang mão(nam)', 2, 12, 0, 1, 69, 530, 0, 75, 0, 4, 0, 0, 0, 0, 0, 0, 85000, -1, 253, 78, -1, 0, -1, NULL, -1, 0),
	(487, 'Đại hùng mão(nữ)', 2, 13, 0, 2, 74, 530, 0, 80, 0, 4, 0, 0, 0, 0, 0, 0, 110000, -1, 252, 77, -1, 0, -1, NULL, -1, 0),
	(488, 'Đại hùng mão(nam)', 2, 12, 0, 1, 74, 530, 0, 80, 0, 4, 0, 0, 0, 0, 0, 0, 110000, -1, 253, 78, -1, 0, -1, NULL, -1, 0),
	(489, 'Phi yến mão(nữ)', 2, 13, 0, 2, 79, 530, 0, 85, 0, 4, 0, 0, 0, 0, 0, 0, 130000, -1, 252, 77, -1, 0, -1, NULL, -1, 0),
	(490, 'Phi yến mão(nam)', 2, 12, 0, 1, 79, 530, 0, 85, 0, 4, 0, 0, 0, 0, 0, 0, 130000, -1, 253, 78, -1, 0, -1, NULL, -1, 0),
	(491, 'Giày phi long', 10, 17, 0, 0, 64, 530, 0, 72, 7, 0, 0, 0, 0, 0, 0, 0, 70000, -1, 271, 17, -1, 0, -1, NULL, -1, 0),
	(492, 'Giày Tử quang', 10, 17, 0, 0, 69, 530, 0, 77, 7, 0, 0, 0, 0, 0, 0, 0, 90000, -1, 271, 17, -1, 0, -1, NULL, -1, 0),
	(493, 'Giày Đại hùng', 10, 17, 0, 0, 74, 530, 0, 82, 7, 0, 0, 0, 0, 0, 0, 0, 110000, -1, 271, 17, -1, 0, -1, NULL, -1, 0),
	(494, 'Giày phi yến', 10, 17, 0, 0, 79, 530, 0, 87, 7, 0, 0, 0, 0, 0, 0, 0, 130000, -1, 271, 17, -1, 0, -1, NULL, -1, 0),
	(495, 'Găng phi long', 11, 23, 0, 0, 64, 530, 0, 70, 0, 6, 5, 0, 0, 0, 0, 0, 65000, -1, 277, 23, -1, 0, -1, NULL, -1, 0),
	(496, 'Găng Tử quang', 11, 23, 0, 0, 69, 530, 0, 75, 0, 6, 5, 0, 0, 0, 0, 0, 85000, -1, 277, 23, -1, 0, -1, NULL, -1, 0),
	(497, 'Găng Đại hùng', 11, 23, 0, 0, 74, 530, 0, 80, 0, 6, 5, 0, 0, 0, 0, 0, 105000, -1, 277, 23, -1, 0, -1, NULL, -1, 0),
	(498, 'Găng Phi yến', 11, 23, 0, 0, 79, 530, 0, 85, 0, 6, 5, 0, 0, 0, 0, 0, 125000, -1, 277, 23, -1, 0, -1, NULL, -1, 0),
	(499, 'Nhẫn Phi long', 8, 5, 0, 0, 64, 530, 65, 0, 0, 6, 0, 0, 0, 0, 0, 0, 91000, -1, 259, 5, -1, 0, -1, NULL, -1, 0),
	(500, 'Nhẫn Tử quang', 8, 5, 0, 0, 69, 530, 70, 0, 0, 6, 0, 0, 0, 0, 0, 0, 115000, -1, 259, 5, -1, 0, -1, NULL, -1, 0),
	(501, 'Nhẫn Đại hùng', 8, 5, 0, 0, 74, 530, 75, 0, 0, 6, 0, 0, 0, 0, 0, 0, 135000, -1, 259, 5, -1, 0, -1, NULL, -1, 0),
	(502, 'Nhẫn phi yến', 8, 5, 0, 0, 79, 530, 80, 0, 0, 6, 0, 0, 0, 0, 0, 0, 155000, -1, 259, 5, -1, 0, -1, NULL, -1, 0),
	(503, 'Dây chuyền phi long', 9, 11, 0, 0, 64, 530, 67, 0, 0, 0, 5, 0, 0, 0, 0, 0, 95000, -1, 265, 11, -1, 0, -1, NULL, -1, 0),
	(504, 'Dây chuyền Tử quang', 9, 11, 0, 0, 69, 530, 72, 0, 0, 0, 5, 0, 0, 0, 0, 0, 115000, -1, 265, 11, -1, 0, -1, NULL, -1, 0),
	(505, 'Dây chuyền Đại hùng', 9, 11, 0, 0, 74, 530, 77, 0, 0, 0, 5, 0, 0, 0, 0, 0, 135000, -1, 265, 11, -1, 0, -1, NULL, -1, 0),
	(506, 'Dây chuyền phi yến', 9, 11, 0, 0, 79, 530, 82, 0, 0, 0, 5, 0, 0, 0, 0, 0, 155000, -1, 265, 11, -1, 0, -1, NULL, -1, 0),
	(507, 'Áo yếm đào', 0, 38, 0, 2, 1, 5000, 10, 10, 3000, 3000, 20, 20, 20, 20, 20, 1, 100, -1, 471, 32, 0, 10080, -1, NULL, -1, 0),
	(508, 'Áo thạch sanh', 0, 39, 0, 1, 1, 5000, 8, 8, 2000, 2000, 17, 17, 17, 17, 17, 1, 100, -1, 472, 32, 0, 10080, -1, NULL, -1, 0),
	(509, 'Nón Vủ dũng', 16, 0, 0, 0, 30, 500, 0, 5, 0, 0, 0, 0, 0, 0, 0, 0, 60000, -1, 485, 0, -1, 0, -1, NULL, -1, 0),
	(510, 'Nón Băng tâm', 16, 0, 0, 0, 35, 500, 0, 8, 0, 0, 0, 0, 0, 0, 0, 0, 85000, -1, 485, 0, -1, 0, -1, NULL, -1, 0),
	(511, 'Nón Linh động', 16, 0, 0, 0, 40, 500, 0, 11, 0, 0, 0, 0, 0, 0, 0, 0, 110000, -1, 485, 0, -1, 0, -1, NULL, -1, 0),
	(512, 'Nón Hóa thần', 16, 0, 0, 0, 45, 500, 0, 13, 0, 0, 0, 0, 0, 0, 0, 0, 135000, -1, 486, 0, -1, 0, -1, NULL, -1, 0),
	(513, 'Nón Phá không', 16, 0, 0, 0, 50, 500, 0, 15, 0, 0, 0, 0, 0, 0, 0, 0, 160000, -1, 486, 0, -1, 0, -1, NULL, -1, 0),
	(514, 'Nón Quy hư', 16, 0, 0, 0, 55, 500, 0, 18, 0, 0, 0, 0, 0, 0, 0, 0, 185000, -1, 486, 0, -1, 0, -1, NULL, -1, 0),
	(515, 'Nón Diệt tuyệt', 16, 0, 0, 0, 60, 500, 0, 21, 0, 0, 0, 0, 0, 0, 0, 0, 210000, -1, 487, 0, -1, 0, -1, NULL, -1, 0),
	(516, 'Nón Không lô', 16, 0, 0, 0, 65, 500, 0, 23, 0, 0, 0, 0, 0, 0, 0, 0, 235000, -1, 487, 0, -1, 0, -1, NULL, -1, 0),
	(517, 'Nón Đại lực', 16, 0, 0, 0, 70, 500, 0, 25, 0, 0, 0, 0, 0, 0, 0, 0, 260000, -1, 487, 0, -1, 0, -1, NULL, -1, 0),
	(518, 'Nón Ngũ vân', 16, 0, 0, 0, 75, 500, 0, 28, 0, 0, 0, 0, 0, 0, 0, 0, 285000, -1, 488, 0, -1, 0, -1, NULL, -1, 0),
	(519, 'Nón Hùng bá', 16, 0, 0, 0, 80, 500, 0, 31, 0, 0, 0, 0, 0, 0, 0, 0, 310000, -1, 488, 0, -1, 0, -1, NULL, -1, 0),
	(520, 'Nón Vô song', 16, 0, 0, 0, 85, 600, 0, 90, 0, 0, 0, 0, 0, 0, 0, 0, 335000, -1, 488, 0, -1, 0, -1, NULL, -1, 0),
	(521, 'Giáp Vủ dũng', 14, 0, 0, 0, 30, 500, 0, 6, 0, 0, 0, 0, 0, 0, 0, 0, 70000, -1, 477, 0, -1, 0, -1, NULL, -1, 0),
	(522, 'Giáp Băng tâm', 14, 0, 0, 0, 35, 500, 0, 9, 0, 0, 0, 0, 0, 0, 0, 0, 95000, -1, 477, 0, -1, 0, -1, NULL, -1, 0),
	(523, 'Giáp Linh động', 14, 0, 0, 0, 40, 500, 0, 12, 0, 0, 0, 0, 0, 0, 0, 0, 120000, -1, 477, 0, -1, 0, -1, NULL, -1, 0),
	(524, 'Giáp Hóa thần', 14, 0, 0, 0, 45, 500, 0, 14, 0, 0, 0, 0, 0, 0, 0, 0, 145000, -1, 478, 0, -1, 0, -1, NULL, -1, 0),
	(525, 'Giáp Phá không', 14, 0, 0, 0, 50, 500, 0, 16, 0, 0, 0, 0, 0, 0, 0, 0, 170000, -1, 478, 0, -1, 0, -1, NULL, -1, 0),
	(526, 'Giáp Quy hư', 14, 0, 0, 0, 55, 500, 0, 19, 0, 0, 0, 0, 0, 0, 0, 0, 195000, -1, 478, 0, -1, 0, -1, NULL, -1, 0),
	(527, 'Giáp Diệt tuyệt', 14, 0, 0, 0, 60, 500, 0, 22, 0, 0, 0, 0, 0, 0, 0, 0, 220000, -1, 479, 0, -1, 0, -1, NULL, -1, 0),
	(528, 'Giáp Không lô', 14, 0, 0, 0, 65, 500, 0, 24, 0, 0, 0, 0, 0, 0, 0, 0, 245000, -1, 479, 0, -1, 0, -1, NULL, -1, 0),
	(529, 'Giáp Đại lực', 14, 0, 0, 0, 70, 500, 0, 26, 0, 0, 0, 0, 0, 0, 0, 0, 270000, -1, 479, 0, -1, 0, -1, NULL, -1, 0),
	(530, 'Giáp Ngũ vân', 14, 0, 0, 0, 75, 500, 0, 29, 0, 0, 0, 0, 0, 0, 0, 0, 295000, -1, 480, 0, -1, 0, -1, NULL, -1, 0),
	(531, 'Giáp Hùng bá', 14, 0, 0, 0, 80, 500, 0, 32, 0, 0, 0, 0, 0, 0, 0, 0, 320000, -1, 480, 0, -1, 0, -1, NULL, -1, 0),
	(532, 'Giáp Vô song', 14, 0, 0, 0, 85, 500, 0, 34, 0, 0, 0, 0, 0, 0, 0, 0, 345000, -1, 480, 0, -1, 0, -1, NULL, -1, 0),
	(533, 'Yên cương Vũ Dũng', 18, 0, 0, 0, 30, 500, 0, 6, 5, 0, 0, 0, 0, 0, 0, 0, 50000, -1, 489, 0, -1, 0, -1, NULL, -1, 0),
	(534, 'Yên cương Băng Tâm', 18, 0, 0, 0, 35, 500, 0, 9, 5, 0, 0, 0, 0, 0, 0, 0, 75000, -1, 489, 0, -1, 0, -1, NULL, -1, 0),
	(535, 'Yên cương Linh Động', 18, 0, 0, 0, 40, 500, 0, 12, 6, 0, 0, 0, 0, 0, 0, 0, 100000, -1, 489, 0, -1, 0, -1, NULL, -1, 0),
	(536, 'Yên cương Hóa Thần', 18, 0, 0, 0, 45, 500, 0, 14, 6, 0, 0, 0, 0, 0, 0, 0, 125000, -1, 490, 0, -1, 0, -1, NULL, -1, 0),
	(537, 'Yên cương Phá Không', 18, 0, 0, 0, 50, 500, 0, 16, 7, 0, 0, 0, 0, 0, 0, 0, 150000, -1, 490, 0, -1, 0, -1, NULL, -1, 0),
	(538, 'Yên cương Quy Hư', 18, 0, 0, 0, 55, 500, 0, 19, 7, 0, 0, 0, 0, 0, 0, 0, 175000, -1, 490, 0, -1, 0, -1, NULL, -1, 0),
	(539, 'Yên cương Diệt Tuyệt', 18, 0, 0, 0, 60, 500, 0, 22, 8, 0, 0, 0, 0, 0, 0, 0, 200000, -1, 491, 0, -1, 0, -1, NULL, -1, 0),
	(540, 'Yên cương Khổng Lô', 18, 0, 0, 0, 65, 500, 0, 24, 8, 0, 0, 0, 0, 0, 0, 0, 225000, -1, 491, 0, -1, 0, -1, NULL, -1, 0),
	(541, 'Yên cương Đại Lực', 18, 0, 0, 0, 70, 500, 0, 26, 9, 0, 0, 0, 0, 0, 0, 0, 250000, -1, 491, 0, -1, 0, -1, NULL, -1, 0),
	(542, 'Yên cương Ngũ Vân', 18, 0, 0, 0, 75, 500, 0, 29, 9, 0, 0, 0, 0, 0, 0, 0, 275000, -1, 492, 0, -1, 0, -1, NULL, -1, 0),
	(543, 'Yên cương Hùng Bá', 18, 0, 0, 0, 80, 500, 0, 32, 10, 0, 0, 0, 0, 0, 0, 0, 300000, -1, 492, 0, -1, 0, -1, NULL, -1, 0),
	(544, 'Yên cương Vô Song', 18, 0, 0, 0, 85, 500, 0, 34, 10, 0, 0, 0, 0, 0, 0, 0, 325000, -1, 492, 0, -1, 0, -1, NULL, -1, 0),
	(545, 'Bàn đạp Vũ Dũng', 17, 0, 0, 0, 30, 500, 5, 0, 0, 0, 3, 0, 0, 0, 0, 0, 55000, -1, 473, 0, -1, 0, -1, NULL, -1, 0),
	(546, 'Bàn đạp Băng Tâm', 17, 0, 0, 0, 35, 500, 8, 0, 0, 0, 3, 0, 0, 0, 0, 0, 80000, -1, 473, 0, -1, 0, -1, NULL, -1, 0),
	(547, 'Bàn đạp Linh Động', 17, 0, 0, 0, 40, 500, 11, 0, 0, 0, 4, 0, 0, 0, 0, 0, 105000, -1, 473, 0, -1, 0, -1, NULL, -1, 0),
	(548, 'Bàn đạp Hóa Thần', 17, 0, 0, 0, 45, 500, 14, 0, 0, 0, 4, 0, 0, 0, 0, 0, 130000, -1, 474, 0, -1, 0, -1, NULL, -1, 0),
	(549, 'Bàn đạp Phá Không', 17, 0, 0, 0, 50, 500, 17, 0, 0, 0, 5, 0, 0, 0, 0, 0, 155000, -1, 474, 0, -1, 0, -1, NULL, -1, 0),
	(550, 'Bàn đạp Quy Hư', 17, 0, 0, 0, 55, 500, 20, 0, 0, 0, 5, 0, 0, 0, 0, 0, 180000, -1, 474, 0, -1, 0, -1, NULL, -1, 0),
	(551, 'Bàn đạp Diệt Tuyệt', 17, 0, 0, 0, 60, 500, 23, 0, 0, 0, 6, 0, 0, 0, 0, 0, 205000, -1, 475, 0, -1, 0, -1, NULL, -1, 0),
	(552, 'Bàn đạp Khổng Lô', 17, 0, 0, 0, 65, 500, 26, 0, 0, 0, 6, 0, 0, 0, 0, 0, 230000, -1, 475, 0, -1, 0, -1, NULL, -1, 0),
	(553, 'Bàn đạp Đại Lực', 17, 0, 0, 0, 70, 500, 29, 0, 0, 0, 7, 0, 0, 0, 0, 0, 255000, -1, 475, 0, -1, 0, -1, NULL, -1, 0),
	(554, 'Bàn đạp Ngũ Vân', 17, 0, 0, 0, 75, 500, 32, 0, 0, 0, 7, 0, 0, 0, 0, 0, 280000, -1, 476, 0, -1, 0, -1, NULL, -1, 0),
	(555, 'Bàn đạp Hùng Bá', 17, 0, 0, 0, 80, 500, 35, 0, 0, 0, 8, 0, 0, 0, 0, 0, 305000, -1, 476, 0, -1, 0, -1, NULL, -1, 0),
	(556, 'Bàn đạp Vô Song', 17, 0, 0, 0, 85, 500, 38, 0, 0, 0, 8, 0, 0, 0, 0, 0, 330000, -1, 476, 0, -1, 0, -1, NULL, -1, 0),
	(557, 'Hộ uyển Vũ Dũng', 15, 0, 0, 0, 30, 500, 6, 0, 0, 5, 0, 0, 0, 0, 0, 0, 65000, -1, 481, 0, -1, 0, -1, NULL, -1, 0),
	(558, 'Hộ uyển Băng Tâm', 15, 0, 0, 0, 35, 500, 9, 0, 0, 5, 0, 0, 0, 0, 0, 0, 90000, -1, 481, 0, -1, 0, -1, NULL, -1, 0),
	(559, 'Hộ uyển Linh Động', 15, 0, 0, 0, 40, 500, 12, 0, 0, 6, 0, 0, 0, 0, 0, 0, 115000, -1, 481, 0, -1, 0, -1, NULL, -1, 0),
	(560, 'Hộ uyển Hóa Thần', 15, 0, 0, 0, 45, 500, 15, 0, 0, 6, 0, 0, 0, 0, 0, 0, 140000, -1, 482, 0, -1, 0, -1, NULL, -1, 0),
	(561, 'Hộ uyển Phá Không', 15, 0, 0, 0, 50, 500, 18, 0, 0, 7, 0, 0, 0, 0, 0, 0, 165000, -1, 482, 0, -1, 0, -1, NULL, -1, 0),
	(562, 'Hộ uyển Quy Hư', 15, 0, 0, 0, 55, 500, 21, 0, 0, 7, 0, 0, 0, 0, 0, 0, 190000, -1, 482, 0, -1, 0, -1, NULL, -1, 0),
	(563, 'Hộ uyển Diệt Tuyệt', 15, 0, 0, 0, 60, 500, 24, 0, 0, 8, 0, 0, 0, 0, 0, 0, 215000, -1, 483, 0, -1, 0, -1, NULL, -1, 0),
	(564, 'Hộ uyển Khổng Lô', 15, 0, 0, 0, 65, 500, 27, 0, 0, 8, 0, 0, 0, 0, 0, 0, 240000, -1, 483, 0, -1, 0, -1, NULL, -1, 0),
	(565, 'Hộ uyển Đại Lực', 15, 0, 0, 0, 70, 500, 30, 0, 0, 9, 0, 0, 0, 0, 0, 0, 265000, -1, 483, 0, -1, 0, -1, NULL, -1, 0),
	(566, 'Hộ uyển Ngũ Vân', 15, 0, 0, 0, 75, 500, 33, 0, 0, 9, 0, 0, 0, 0, 0, 0, 290000, -1, 484, 0, -1, 0, -1, NULL, -1, 0),
	(567, 'Hộ uyển Hùng Bá', 15, 0, 0, 0, 80, 500, 36, 0, 0, 10, 0, 0, 0, 0, 0, 0, 315000, -1, 484, 0, -1, 0, -1, NULL, -1, 0),
	(568, 'Hộ uyển Vô Song', 15, 0, 0, 0, 85, 500, 39, 0, 0, 10, 0, 0, 0, 0, 0, 0, 340000, -1, 484, 0, -1, 0, -1, NULL, -1, 0),
	(571, 'Ngọc đại hùng', 12, 30, 0, 0, 74, 480, 0, 0, 0, 8, 7, 13, 0, 0, 0, 0, 150000, -1, 284, 30, -1, 0, -1, NULL, -1, 0),
	(572, 'Ngọc phi yến', 12, 30, 0, 0, 79, 480, 0, 0, 0, 8, 7, 14, 0, 0, 0, 0, 170000, -1, 284, 30, -1, 0, -1, NULL, -1, 0),
	(569, 'Ngọc phi long', 12, 30, 0, 0, 64, 480, 0, 0, 0, 7, 6, 15, 0, 0, 0, 0, 110000, -1, 284, 30, -1, 0, -1, NULL, -1, 0),
	(570, 'Ngọc tử quang', 12, 30, 0, 0, 69, 480, 0, 0, 0, 7, 6, 16, 0, 0, 0, 0, 130, -1, 284, 30, -1, 0, -1, NULL, -1, 0),
	(573, 'Áo Dracula nữ', 0, 0, 0, 2, 0, 480, 10, 10, 3000, 3000, 21, 25, 49, 49, 49, 1, 0, -1, 493, 0, -1, 10080, -1, NULL, -1, 0),
	(574, 'Áo Hằng Nga', 0, 0, 0, 2, 0, 480, 10, 10, 3000, 3000, 22, 23, 47, 47, 47, 1, 0, -1, 494, 0, -1, 10080, -1, NULL, -1, 0),
	(575, 'Áo Dracula nam', 0, 0, 0, 1, 0, 480, 10, 10, 3000, 3000, 23, 24, 48, 48, 48, 1, 0, -1, 495, 0, -1, 10080, -1, NULL, -1, 0),
	(576, 'Áo Chú cuội', 0, 0, 0, 1, 0, 480, 10, 10, 3000, 3000, 24, 22, 46, 46, 46, 1, 0, -1, 496, 0, -1, 10080, -1, NULL, -1, 0),
	(577, 'Đầu bí ngô', 1, 0, 0, 0, 0, 480, 0, 0, 3000, 3000, 25, 26, 0, 0, 0, 1, 0, -1, 497, 0, -1, 10080, -1, NULL, -1, 0),
	(578, 'Nón phù thủy', 2, 0, 0, 0, 0, 480, 0, 0, 3000, 3000, 26, 0, 50, 0, 0, 1, 0, -1, 498, 0, -1, 10080, -1, NULL, -1, 0),
	(579, 'áo noel nữ', 0, 0, 0, 2, 0, 480, 10, 10, 5000, 5000, 16, 16, 0, 16, 16, 1, 0, -1, 500, 0, -1, 10080, -1, NULL, -1, 0),
	(580, 'áo noel nam', 0, 0, 0, 1, 0, 480, 10, 10, 5000, 5000, 21, 21, 0, 21, 21, 1, 0, -1, 501, 0, -1, 10080, -1, NULL, -1, 0),
	(581, 'nón noel', 2, 0, 0, 0, 0, 480, 5, 5, 2000, 2000, 22, 0, 16, 0, 0, 1, 0, -1, 499, 0, -1, 10080, -1, NULL, -1, 0),
	(582, 'áo dài nữ', 0, 0, 0, 2, 0, 480, 10, 10, 3000, 3000, 27, 0, 52, 51, 51, 1, 0, -1, 502, 0, -1, 10080, -1, NULL, -1, 0),
	(583, 'áo dài nam', 0, 0, 0, 1, 0, 480, 10, 10, 3000, 3000, 28, 0, 51, 50, 50, 1, 0, -1, 503, 0, -1, 10080, -1, NULL, -1, 0),
	(584, 'Phi phong Lăng Vân', 19, 0, 0, 0, 35, 480, 0, 100, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 504, 0, -1, 0, -1, NULL, -1, 0),
	(585, 'Phi phong Tuyệt Thế', 19, 1, 0, 0, 40, 480, 0, 110, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 505, 0, -1, 0, -1, NULL, -1, 0),
	(586, 'Phi phong Phá Quân', 19, 2, 0, 0, 45, 480, 0, 120, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 506, 0, -1, 0, -1, NULL, -1, 0),
	(587, 'Phi phong Ngạo Tuyết', 19, 3, 0, 0, 50, 480, 0, 130, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 507, 0, -1, 0, -1, NULL, -1, 0),
	(588, 'Phi phong Kình Lôi', 19, 4, 0, 0, 55, 480, 0, 140, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 508, 0, -1, 0, -1, NULL, -1, 0),
	(589, 'Phi phong Siêu Phàm', 19, 5, 0, 0, 60, 480, 0, 150, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 509, 0, -1, 0, -1, NULL, -1, 0),
	(590, 'Phi phong Xuất Trần', 19, 6, 0, 0, 65, 480, 0, 160, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 511, 0, -1, 0, -1, NULL, -1, 0),
	(591, 'Phi phong Lăng Tuyệt', 19, 7, 0, 0, 70, 480, 0, 170, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 510, 0, -1, 0, -1, NULL, -1, 0),
	(592, 'Phi phong Kinh Thế', 19, 8, 0, 0, 75, 480, 0, 180, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 512, 0, -1, 0, -1, NULL, -1, 0),
	(593, 'Phi phong Ngự Không', 19, 9, 0, 0, 80, 480, 0, 190, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 513, 0, -1, 0, -1, NULL, -1, 0),
	(594, 'Phi phong Hỗn Thiên', 19, 10, 0, 0, 85, 480, 0, 200, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 514, 0, -1, 0, -1, NULL, -1, 0),
	(595, 'Phi phong Tiềm Long', 19, 11, 0, 0, 90, 480, 0, 210, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 515, 0, -1, 0, -1, NULL, -1, 0),
	(596, 'Phi phong Lăng Vân', 19, 12, 0, 0, 95, 480, 0, 220, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 516, 0, -1, 0, -1, NULL, -1, 0),
	(597, 'Phi phong Lăng Vân', 19, 13, 0, 0, 100, 480, 0, 230, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 517, 0, -1, 0, -1, NULL, -1, 0),
	(598, 'Chuyên Chư kiếm', 3, 4, 0, 0, 76, 480, 340, 0, 0, 8, 0, 0, 0, 0, 0, 0, 370000, 0, 29, 0, 1, 0, -1, NULL, -1, 0),
	(599, 'Vu Điện kiếm', 3, 4, 0, 0, 81, 480, 360, 0, 0, 8, 0, 0, 0, 0, 0, 0, 380000, 0, 54, 0, 2, 0, -1, NULL, -1, 0),
	(600, 'Hàn Khôi đao', 4, 4, 0, 0, 76, 480, 350, 0, 0, 4, 0, 0, 0, 0, 0, 0, 370000, 1, 34, 0, 1, 0, -1, NULL, -1, 0),
	(601, 'Minh Nguyệt đao', 4, 4, 0, 0, 81, 480, 370, 0, 0, 4, 0, 0, 0, 0, 0, 0, 380000, 1, 59, 0, 2, 0, -1, NULL, -1, 0),
	(602, 'Bạch Hồng bút', 5, 4, 0, 0, 76, 480, 365, 0, 0, 3, 0, 0, 0, 0, 0, 0, 370000, 2, 39, 0, 1, 0, -1, NULL, -1, 0),
	(603, 'Nhiếp Chính bút', 5, 4, 0, 0, 81, 480, 385, 0, 0, 3, 0, 0, 0, 0, 0, 0, 380000, 2, 64, 0, 2, 0, -1, NULL, -1, 0),
	(604, 'Tuệ Tinh búa', 6, 4, 0, 0, 76, 480, 390, 0, 0, 3, 0, 0, 0, 0, 0, 0, 370000, 3, 44, 0, 1, 0, -1, NULL, -1, 0),
	(605, 'Lôi Động búa', 6, 4, 0, 0, 81, 480, 401, 0, 0, 3, 0, 0, 0, 0, 0, 0, 380000, 3, 69, 0, 2, 0, -1, NULL, -1, 0),
	(606, 'Yếu Ly cung', 7, 4, 0, 0, 76, 480, 300, 0, 0, 12, 0, 0, 0, 0, 0, 0, 370000, 4, 49, 0, 1, 0, -1, NULL, -1, 0),
	(607, 'Thương Ưng cung', 7, 4, 0, 0, 81, 480, 320, 0, 0, 12, 0, 0, 0, 0, 0, 0, 380000, 4, 74, 0, 2, 0, -1, NULL, -1, 0),
	(608, 'Phi phong world cup', 19, 14, 0, 0, 1, 480, 0, 170, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 518, 0, -1, 0, -1, NULL, -1, 0),
	(609, 'áo brazin nam,tăng100% băng lan, sét lan, độc lan', 0, 52, 0, 1, 1, 480, 10, 10, 3000, 3000, 0, 0, 0, 52, 52, 1, 0, -1, 519, 0, -1, 10080, -1, NULL, -1, 0),
	(610, 'áo brazin nữ,tăng100% băng lan, sét lan, độc lan', 0, 53, 0, 2, 1, 480, 10, 10, 3000, 3000, 0, 0, 0, 53, 53, 1, 0, -1, 519, 0, -1, 10080, -1, NULL, -1, 0),
	(611, 'Rương bảo tiêu', 20, 3, 0, 0, 1, 480, 10, 10, 3000, 3000, 0, 0, 0, 0, 0, 0, 0, -1, 519, 0, -1, 0, -1, NULL, -1, 0),
	(612, 'Rương cướp tiêu', 20, 3, 0, 0, 1, 480, 10, 10, 3000, 3000, 0, 0, 0, 0, 0, 0, 0, -1, 520, 0, -1, 0, -1, NULL, -1, 0),
	(613, 'Chổi băng ánh', 13, 0, 0, 0, 1, 480, 0, 0, 0, 0, 1, 0, 0, 0, 10, 2, 0, 0, 522, 0, -1, 10080, -1, NULL, -1, 0),
	(614, 'Chổi điện ánh', 13, 0, 0, 0, 1, 480, 0, 0, 0, 0, 1, 0, 0, 0, 9, 2, 0, 0, 521, 0, -1, 10080, -1, NULL, -1, 0),
	(615, 'Chổi độc ánh', 13, 0, 0, 0, 1, 480, 0, 0, 0, 0, 1, 0, 0, 0, 11, 2, 0, 0, 523, 0, -1, 10080, -1, NULL, -1, 0),
	(616, 'Phi phong lông thú', 19, 15, 0, 0, 1, 480, 0, 170, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 529, 0, -1, 10080, -1, NULL, -1, 0),
	(617, 'Chổi băng', 13, 0, 0, 0, 1, 480, 0, 0, 0, 0, 1, 0, 0, 0, 12, 2, 0, 0, 526, 0, -1, 0, -1, NULL, -1, 0),
	(618, 'Chổi set', 13, 0, 0, 0, 1, 480, 0, 0, 0, 0, 1, 0, 0, 0, 13, 2, 0, 0, 527, 0, -1, 0, -1, NULL, -1, 0),
	(619, 'Chổi độc', 13, 0, 0, 0, 1, 480, 0, 0, 0, 0, 1, 0, 0, 0, 14, 2, 0, 0, 528, 0, -1, 0, -1, NULL, -1, 0),
	(620, 'Thời trang băng tuyết(nam)\\ntăng100% băng ,sét ,độc ,lửa lan', 0, 55, 0, 1, 1, 480, 10, 10, 5000, 5000, 55, 0, 57, 55, 55, 1, 0, -1, 530, 0, -1, 0, -1, NULL, -1, 0),
	(621, 'Thời trang băng tuyết\\ntăng100% băng ,sét ,độc ,lửa lan', 0, 56, 0, 2, 1, 480, 10, 10, 5000, 5000, 56, 19, 58, 56, 56, 1, 0, -1, 531, 0, -1, 0, -1, NULL, -1, 0),
	(622, 'Hồng hài nhi nam \\ntăng 100% băng ,sét ,độc ,lửa lan', 0, 63, 0, 1, 1, 480, 10, 10, 5000, 5000, 63, 70, 0, 63, 63, 1, 0, -1, 541, 0, -1, 10080, -1, NULL, -1, 0),
	(623, 'Hồng hài nhi nữ \\ntăng 100% băng ,sét ,độc ,lửa lan', 0, 64, 0, 2, 1, 480, 10, 10, 5000, 5000, 64, 71, 0, 64, 64, 1, 0, -1, 541, 0, -1, 10080, -1, NULL, -1, 0),
	(624, 'Đệ tử cấp 1(nam)', 0, 59, 0, 1, 1, 480, 10, 10, 5000, 5000, 59, 0, 0, 59, 59, 1, 0, -1, 531, 0, -1, 0, -1, NULL, -1, 0),
	(625, 'Đệ tử cấp 2(nam)', 0, 58, 0, 1, 1, 480, 10, 10, 5000, 5000, 58, 0, 0, 58, 58, 1, 0, -1, 531, 0, -1, 0, -1, NULL, -1, 0),
	(626, 'Đệ tử cấp 3(nam)', 0, 57, 0, 1, 1, 480, 10, 10, 5000, 5000, 57, 0, 0, 57, 57, 1, 0, -1, 531, 0, -1, 0, -1, NULL, -1, 0),
	(627, 'Đệ tử cấp 1(nữ)', 0, 62, 0, 2, 1, 480, 10, 10, 5000, 5000, 62, 0, 0, 62, 62, 1, 0, -1, 531, 0, -1, 0, -1, NULL, -1, 0),
	(628, 'Đệ tử cấp 2(nữ)', 0, 61, 0, 2, 1, 480, 10, 10, 5000, 5000, 61, 0, 0, 61, 61, 1, 0, -1, 531, 0, -1, 0, -1, NULL, -1, 0),
	(629, 'Đệ tử cấp 3(nữ)', 0, 60, 0, 2, 1, 480, 10, 10, 5000, 5000, 60, 0, 0, 60, 60, 1, 0, -1, 531, 0, -1, 0, -1, NULL, -1, 0),
	(630, 'Hộ vệ lệnh 4x lv 1', 21, 0, 0, 0, 45, 480, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 15, -1, 532, 0, -1, 10080, -1, NULL, -1, 0),
	(631, 'Hộ vệ lệnh 4x lv 2', 21, 0, 0, 0, 45, 480, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 20, -1, 533, 0, -1, 10080, -1, NULL, -1, 0),
	(632, 'Hộ vệ lệnh 4x lv 3', 21, 0, 0, 0, 45, 480, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25, -1, 534, 0, -1, 10080, -1, NULL, -1, 0),
	(633, 'Hộ vệ lệnh 5x lv 1', 21, 0, 0, 0, 55, 480, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 30, -1, 535, 0, -1, 10080, -1, NULL, -1, 0),
	(634, 'Hộ vệ lệnh 5x lv 2', 21, 0, 0, 0, 55, 480, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 35, -1, 536, 0, -1, 10080, -1, NULL, -1, 0),
	(635, 'Hộ vệ lệnh 5x lv 3', 21, 0, 0, 0, 55, 480, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 40, -1, 537, 0, -1, 10080, -1, NULL, -1, 0),
	(636, 'Hộ vệ lệnh 6x lv 1', 21, 0, 0, 0, 65, 480, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 45, -1, 538, 0, -1, 10080, -1, NULL, -1, 0),
	(637, 'Hộ vệ lệnh 6x lv 2', 21, 0, 0, 0, 65, 480, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 50, -1, 539, 0, -1, 10080, -1, NULL, -1, 0),
	(638, 'Hộ vệ lệnh 6x lv 3', 21, 0, 0, 0, 65, 480, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 55, -1, 540, 0, -1, 10080, -1, NULL, -1, 0),
	(639, 'ô quy bào ( nam)', 0, 65, 0, 1, 80, 480, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, -1, 549, 0, -1, 0, 641, NULL, -1, 0),
	(640, 'Bạch hạc bào (nữ)', 0, 66, 0, 2, 80, 480, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, -1, 544, 0, -1, 0, 642, NULL, -1, 0),
	(641, 'Hoàng long bào (nam)', 0, 67, 0, 1, 85, 480, 0, 95, 0, 5, 0, 0, 0, 0, 0, 0, 25000, -1, 546, 25, -1, 0, -1, NULL, -1, 0),
	(642, 'Hoả phụng bào (nữ)', 0, 68, 0, 2, 85, 480, 0, 95, 0, 5, 0, 0, 0, 0, 0, 0, 25000, -1, 551, 26, -1, 0, -1, NULL, -1, 0),
	(643, 'Quần Ô quy (nam)', 1, 65, 0, 1, 80, 480, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, -1, 548, 0, -1, 0, 645, NULL, -1, 0),
	(644, 'Quần Bạch hạc (nữ)', 1, 66, 0, 2, 80, 480, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, -1, 542, 0, -1, 0, 646, NULL, -1, 0),
	(645, 'Quần Hoàng long (nam)', 1, 67, 0, 1, 85, 480, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, -1, 545, 0, -1, 0, -1, NULL, -1, 0),
	(646, 'Quần Hoả phụng (nữ)', 1, 68, 0, 2, 85, 600, 0, 90, 0, 4, 0, 0, 0, 0, 0, 0, 25000, -1, 552, 51, 0, 0, -1, NULL, -1, 0),
	(647, 'ô quy mão (nam)', 2, 59, 0, 1, 80, 480, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, -1, 550, 0, -1, 0, 649, NULL, -1, 0),
	(648, 'Bạch hạc mão (nữ)', 2, 60, 0, 2, 80, 480, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, -1, 543, 0, -1, 0, 650, NULL, -1, 0),
	(649, 'Hoàng long mão(nam)', 2, 61, 0, 1, 85, 600, 0, 90, 0, 4, 0, 0, 0, 0, 0, 0, 25000, -1, 547, 52, 0, 0, -1, NULL, -1, 0),
	(650, 'Hoả phụng mão (nữ)', 2, 62, 0, 2, 85, 480, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, -1, 553, 0, -1, 0, -1, NULL, -1, 0),
	(651, 'Nhẫn ô quy', 8, 30, 0, 0, 80, 480, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, -1, 389, 0, -1, 0, 652, NULL, -1, 0),
	(652, 'Nhẫn Bạch hạc ', 8, 30, 0, 0, 85, 480, 85, 0, 0, 6, 0, 0, 0, 0, 0, 0, 25000, -1, 389, 5, -1, 0, 653, NULL, -1, 0),
	(653, 'Nhẫn Hoàng long', 8, 30, 0, 0, 90, 480, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, -1, 390, 0, -1, 0, 654, NULL, -1, 0),
	(654, 'Nhẫn Hoả phụng', 8, 30, 0, 0, 95, 480, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, -1, 390, 0, -1, 0, -1, NULL, -1, 0),
	(655, 'Dây chuyền Ô quy', 9, 30, 0, 0, 80, 480, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, -1, 401, 0, -1, 0, 656, NULL, -1, 0),
	(656, 'Dây chuyền Bạch hạc ', 9, 30, 0, 0, 85, 480, 85, 0, 0, 0, 5, 0, 0, 0, 0, 0, 25000, -1, 401, 11, -1, 0, 657, NULL, -1, 0),
	(657, 'Dây chuyền Hoàng long', 9, 30, 0, 0, 90, 480, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, -1, 402, 0, -1, 0, 658, NULL, -1, 0),
	(658, 'Dây chuyền Hoả phụng', 9, 30, 0, 0, 95, 480, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, -1, 402, 0, -1, 0, -1, NULL, -1, 0),
	(659, 'Giày Ô quy', 10, 30, 0, 0, 80, 480, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, -1, 365, 0, -1, 0, 660, NULL, -1, 0),
	(660, 'Giày Bạch hạc ', 10, 30, 0, 0, 85, 480, 0, 87, 7, 0, 0, 0, 0, 0, 0, 0, 25000, -1, 365, 17, -1, 0, 661, NULL, -1, 0),
	(661, 'Giày Hoàng long', 10, 30, 0, 0, 90, 480, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, -1, 366, 0, -1, 0, 662, NULL, -1, 0),
	(662, 'Giày Hoả phụng', 10, 30, 0, 0, 95, 480, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, -1, 366, 0, -1, 0, -1, NULL, -1, 0),
	(663, 'Găng Ô quy', 11, 30, 0, 0, 80, 480, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, -1, 377, 0, -1, 0, 664, NULL, -1, 0),
	(664, 'Găng Bạch hạc ', 11, 30, 0, 0, 85, 480, 0, 90, 0, 6, 5, 0, 0, 0, 0, 0, 25000, -1, 377, 23, -1, 0, 665, NULL, -1, 0),
	(665, 'Găng Hoàng long', 11, 30, 0, 0, 90, 480, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, -1, 378, 0, -1, 0, 666, NULL, -1, 0),
	(666, 'Găng Hoả phụng', 11, 30, 0, 0, 95, 480, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, -1, 378, 0, -1, 0, -1, NULL, -1, 0),
	(667, 'Ngọc Ô quy', 12, 30, 0, 0, 80, 480, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, -1, 413, 0, -1, 0, 668, NULL, -1, 0),
	(668, 'Ngọc Bạch hạc ', 12, 30, 0, 0, 85, 480, 0, 0, 0, 8, 7, 15, 0, 0, 0, 0, 25000, -1, 413, 63, -1, 0, 669, NULL, -1, 0),
	(669, 'Ngọc Hoàng long', 12, 30, 0, 0, 90, 480, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, -1, 414, 0, -1, 0, 670, NULL, -1, 0),
	(670, 'Ngọc Hoả phụng', 12, 30, 0, 0, 95, 480, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25000, -1, 414, 0, -1, 0, -1, NULL, -1, 0),
	(671, 'Freddy\r\nTăng100% băng ,sét ,độc ,lửa lan', 0, 69, 0, 0, 1, 480, 10, 10, 5000, 5000, 69, 72, 63, 69, 69, 1, 0, 0, 555, 0, -1, 10080, -1, NULL, -1, 0),
	(672, 'Jason\r\nTăng100% băng ,sét ,độc ,lửa lan', 0, 70, 0, 0, 1, 480, 10, 10, 5000, 5000, 70, 74, 0, 71, 71, 1, 0, 0, 554, 0, -1, 10080, -1, NULL, -1, 0),
	(673, 'Harry potter\r\nTăng100% băng ,sét ,độc ,lửa lan', 0, 71, 0, 0, 1, 480, 10, 10, 5000, 5000, 71, 73, 0, 70, 70, 1, 0, 0, 556, 0, -1, 10080, -1, NULL, -1, 0),
	(674, 'Chúa tể Voldemort\r\nTăng100% băng ,sét ,độc ,lửa lan', 0, 72, 0, 0, 1, 480, 10, 10, 5000, 5000, 72, 75, 0, 72, 72, 1, 0, 0, 557, 0, -1, 10080, -1, NULL, -1, 0),
	(675, 'Thẻ mua bán', 22, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 10, -1, 558, 0, -1, 10080, -1, NULL, -1, 0),
	(676, 'Xa tiêu lệnh 2x', 23, 0, 0, 0, 40, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 10, -1, 562, 0, -1, 0, -1, NULL, -1, 0),
	(677, 'Xa tiêu lệnh 3x', 23, 0, 0, 0, 40, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 10, -1, 563, 0, -1, 0, -1, NULL, -1, 0),
	(678, 'Lệnh bài trấn yêu 5x', 24, 0, 0, 0, 40, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 10, -1, 559, 0, -1, 0, -1, NULL, -1, 0),
	(679, 'Lệnh bài trấn yêu 6x', 24, 0, 0, 0, 40, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 10, -1, 560, 0, -1, 0, -1, NULL, -1, 0),
	(680, 'Lệnh bài trấn yêu 7x', 24, 0, 0, 0, 40, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 10, -1, 561, 0, -1, 0, -1, NULL, -1, 0),
	(681, 'Hoả kỳ lân', 25, 0, 0, 0, 40, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 10, -1, 564, 0, -1, 0, 682, NULL, -1, 0),
	(682, 'Hoả kỳ lân cấp 2', 25, 0, 0, 0, 40, 1, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 565, 0, -1, 0, 683, NULL, -1, 0),
	(683, 'Hoả kỳ lân cấp 3', 25, 0, 0, 0, 40, 1, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 566, 0, -1, 0, -1, NULL, -1, 0),
	(684, 'Trứng Cú', 26, 0, 0, 0, 1, 0, 1, 23, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 572, 0, -1, 0, 689, NULL, -1, 0),
	(685, 'Trứng Rồng', 26, 0, 0, 0, 1, 0, 1, 24, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 573, 0, -1, 0, 694, NULL, -1, 0),
	(686, 'Trứng Yêu Tinh', 26, 0, 0, 0, 1, 0, 1, 25, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 574, 0, -1, 0, 699, NULL, -1, 0),
	(687, 'Trứng Dơi', 26, 0, 0, 0, 1, 0, 1, 26, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 575, 0, -1, 0, 704, NULL, -1, 0),
	(688, 'Trứng đại bàng', 26, 0, 0, 0, 1, 0, 1, 27, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 576, 0, -1, 0, 709, NULL, -1, 0),
	(689, 'Cú cấp 1', 27, 0, 0, 0, 0, 0, 1, 24, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 567, 0, -1, 0, 690, NULL, -1, 0),
	(690, 'Cú cấp 2', 27, 0, 0, 0, 0, 0, 2, 24, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 567, 0, -1, 0, 691, NULL, -1, 0),
	(691, 'Cú cấp 3', 27, 0, 0, 0, 0, 0, 3, 24, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 567, 0, -1, 0, 692, NULL, -1, 0),
	(692, 'Cú cấp 4', 27, 0, 0, 0, 0, 0, 4, 24, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 567, 0, -1, 0, 693, NULL, -1, 0),
	(693, 'Cú cấp 5', 27, 0, 0, 0, 0, 0, 5, 24, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 567, 0, -1, 0, -1, NULL, -1, 0),
	(694, 'Rồng cấp 1', 27, 0, 0, 0, 0, 0, 1, 25, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 568, 0, -1, 0, 695, NULL, -1, 0),
	(695, 'Rồng cấp 2', 27, 0, 0, 0, 0, 0, 2, 25, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 568, 0, -1, 0, 696, NULL, -1, 0),
	(696, 'Rồng cấp 3', 27, 0, 0, 0, 0, 0, 3, 25, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 568, 0, -1, 0, 697, NULL, -1, 0),
	(697, 'Rồng cấp 4', 27, 0, 0, 0, 0, 0, 4, 25, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 568, 0, -1, 0, 698, NULL, -1, 0),
	(698, 'Rồng cấp 5', 27, 0, 0, 0, 0, 0, 5, 25, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 568, 0, -1, 0, -1, NULL, -1, 0),
	(699, 'Yêu Tinh cấp 1', 27, 0, 0, 0, 0, 0, 1, 26, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 569, 0, -1, 0, 700, NULL, -1, 0),
	(700, 'Yêu Tinh cấp 2', 27, 0, 0, 0, 0, 0, 2, 26, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 569, 0, -1, 0, 701, NULL, -1, 0),
	(701, 'Yêu Tinh cấp 3', 27, 0, 0, 0, 0, 0, 3, 26, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 569, 0, -1, 0, 702, NULL, -1, 0),
	(702, 'Yêu Tinhcấp 4', 27, 0, 0, 0, 0, 0, 4, 26, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 569, 0, -1, 0, 703, NULL, -1, 0),
	(703, 'Yêu Tinh cấp 5', 27, 0, 0, 0, 0, 0, 5, 26, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 569, 0, -1, 0, -1, NULL, -1, 0),
	(704, 'Dơi cấp 1', 27, 0, 0, 0, 0, 0, 1, 27, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 570, 0, -1, 0, 705, NULL, -1, 0),
	(705, 'Dơi cấp 2', 27, 0, 0, 0, 0, 0, 2, 27, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 570, 0, -1, 0, 706, NULL, -1, 0),
	(706, 'Dơi cấp 3', 27, 0, 0, 0, 0, 0, 3, 27, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 570, 0, -1, 0, 707, NULL, -1, 0),
	(707, 'Dơi cấp 4', 27, 0, 0, 0, 0, 0, 4, 27, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 570, 0, -1, 0, 708, NULL, -1, 0),
	(708, 'Dơi cấp 5', 27, 0, 0, 0, 0, 0, 5, 27, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 570, 0, -1, 0, -1, NULL, -1, 0),
	(709, 'Đại Bàng cấp 1', 27, 0, 0, 0, 0, 0, 1, 28, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 571, 0, -1, 0, 710, NULL, -1, 0),
	(710, 'Đại Bàng cấp 2', 27, 0, 0, 0, 0, 0, 2, 28, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 571, 0, -1, 0, 711, NULL, -1, 0),
	(711, 'Đại Bàng cấp 3', 27, 0, 0, 0, 0, 0, 3, 28, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 571, 0, -1, 0, 712, NULL, -1, 0),
	(712, 'Đại Bàng cấp 4', 27, 0, 0, 0, 0, 0, 4, 28, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 571, 0, -1, 0, 713, NULL, -1, 0),
	(713, 'Đại Bàng cấp 5', 27, 0, 0, 0, 0, 0, 5, 28, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 571, 0, -1, 0, -1, NULL, -1, 0),
	(714, 'Tụ hồn đan', 28, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 577, 0, -1, 0, -1, NULL, -1, 0),
	(715, 'Huyền Thiết Kiếm', 3, 4, 0, 0, 40, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 154, 0, 6, 0, -1, NULL, -1, 0),
	(716, 'Yển Nguyệt Đao', 4, 4, 0, 0, 40, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 159, 0, 6, 0, -1, NULL, -1, 0),
	(717, 'Ngọc Tỷ Bút', 5, 4, 0, 0, 40, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 164, 0, 6, 0, -1, NULL, -1, 0),
	(718, 'Trấn Hồn Búa', 6, 4, 0, 0, 40, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 169, 0, 6, 0, -1, NULL, -1, 0),
	(719, 'Khổng Tước Cung', 7, 4, 0, 0, 40, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 174, 0, 6, 0, -1, NULL, -1, 0),
	(720, 'Huyết linh thảo.\r\nTiến hoá trạng thái thú nuôi', 28, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 578, 0, -1, 0, -1, NULL, -1, 0),
	(721, 'Huyết bồ đề\r\nThay đổi độ thông thạo của thú nuôi', 28, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 579, 0, -1, 0, -1, NULL, -1, 0),
	(722, 'Sách kỹ năng pet', 28, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 580, 0, -1, 0, -1, NULL, -1, 0),
	(723, 'Hắc Lang\n- Tăng 50% băng ,sét ,độc ,lửa lan', 0, 73, 0, 1, 1, 480, 10, 0, 5000, 5000, 73, 76, 0, 73, 73, 1, 0, -1, 581, 0, -1, 10080, -1, NULL, 8754, 0),
	(724, 'Thần nữ\n- Tăng 50% băng ,sét ,độc ,lửa lan', 0, 74, 0, 2, 1, 480, 10, 0, 5000, 5000, 74, 77, 64, 74, 74, 1, 0, -1, 582, 0, -1, 10080, -1, NULL, 8887, 0),
	(725, 'Bạch Mi\n- Tăng 50% băng ,sét ,độc ,lửa lan', 0, 75, 0, 1, 1, 480, 10, 0, 5000, 5000, 75, 78, 0, 75, 75, 1, 0, -1, 584, 0, -1, 10080, -1, NULL, 8758, 0),
	(726, 'Thuý Vũ Hồng Sam\n- Tăng 50% băng ,sét ,độc ,lửa lan', 0, 76, 0, 2, 1, 480, 10, 0, 5000, 5000, 76, 79, 0, 76, 76, 1, 0, -1, 583, 0, -1, 10080, -1, NULL, 8759, 0),
	(727, 'Bạch Thử', 27, 0, 0, 0, 5, 0, 5, 60, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 585, 0, -1, 10080, -1, NULL, -1, 0),
	(728, 'Gậy Giáng Sinh', 27, 0, 0, 0, 10, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 586, 587, -1, 0, -1, NULL, 8757, 0),
	(729, 'Chổi lửa', 28, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 589, 588, -1, 0, -1, NULL, 8766, 0),
	(730, 'Đồng xu bạc\r\nDùng đổi Lân sư tử vĩnh viễn', 28, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 591, 0, -1, 0, -1, NULL, -1, 0),
	(731, 'Đồng xu xanh\r\nDùng đổi trang bị thời trang vĩnh viễn', 28, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 592, 0, -1, 0, -1, NULL, -1, 0),
	(732, 'Đồng xu vàng\r\nDùng đổi pet Bạch thử vĩnh viễn', 28, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 590, 0, -1, 0, -1, NULL, -1, 0),
	(733, 'Đồng xu đỏ\r\nDùng đổi lồng đèn hoa sen vĩnh viễn', 28, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 594, 0, -1, 0, -1, NULL, -1, 0),
	(734, 'Đồng xu xanh lá\r\nDùng đổi chổi lửa lan vĩnh viễn', 28, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 595, 0, -1, 0, -1, NULL, -1, 0),
	(735, 'Rương may mắn', 28, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 596, 0, -1, 15, -1, NULL, -1, 0),
	(736, 'Sơn Tinh\r\nTăng 50% băng ,sét ,độc ,lửa lan', 0, 77, -1, 0, 1, 480, 10, 0, 5000, 5000, 77, 80, 65, 77, 77, 1, 0, -1, 602, 0, -1, 10080, -1, NULL, 8782, 0),
	(737, 'Thuỷ Tinh\r\nTăng 50% băng ,sét ,độc ,lửa lan', 0, 78, -1, 0, 1, 480, 10, 0, 5000, 5000, 78, 81, 66, 78, 78, 1, 0, -1, 603, 0, -1, 10080, -1, NULL, 8781, 0),
	(738, 'Rương bát bảo', 28, 0, -1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 601, 0, -1, 0, -1, NULL, -1, 0),
	(739, 'Tiên đơn\r\nHồi sinh sau khi chết', 28, 0, -1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 604, 0, -1, 0, -1, NULL, -1, 0),
	(740, 'Tiểu long nữ\r\nTăng 50% băng ,sét ,độc ,lửa lan', 0, 80, -1, 2, 1, 480, 10, 0, 5000, 5000, 80, 83, 0, 80, 80, 1, 0, -1, 605, 0, -1, 10080, -1, NULL, -1, 0),
	(741, 'Dương Quá\r\nTăng 50% băng ,sét ,độc ,lửa lan', 0, 79, -1, 1, 1, 480, 10, 0, 5000, 5000, 79, 82, 0, 79, 79, 1, 0, -1, 606, 0, -1, 10080, -1, NULL, -1, 0),
	(742, 'Phi phong Khổng Tước', 19, 15, 0, 0, 1, 480, 0, 170, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 619, 0, -1, 10080, -1, '', 8791, 0),
	(743, 'Áo Tiên Nữ\r\nTăng 50% băng ,sét ,độc ,lửa lan', 0, 0, -1, 2, 1, 480, 10, 10, 5000, 5000, 0, 0, 0, 0, 0, 1, 0, -1, 617, 0, -1, 10080, -1, NULL, -1, 0),
	(744, 'Áo Tinh Quân\r\nTăng 50% băng ,sét ,độc ,lửa lan', 0, 0, -1, 1, 1, 480, 10, 10, 5000, 5000, 0, 0, 0, 0, 0, 1, 0, -1, 616, 0, -1, 10080, -1, NULL, -1, 0),
	(745, 'Mặt nạ bí ngô', 1, 0, 0, 0, 1, 480, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, -1, 620, 0, -1, 10080, -1, NULL, 8800, 0),
	(746, 'Mặt nạ ngạ quỷ', 1, 0, 0, 0, 1, 480, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, -1, 621, 0, -1, 10080, -1, NULL, 8801, 0),
	(747, 'Mặt nạ ngạ khuyển', 1, 0, 0, 0, 1, 480, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, -1, 622, 0, -1, 10080, -1, NULL, 8802, 0),
	(748, 'Mặt nạ thi khiển', 1, 0, 0, 0, 1, 480, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, -1, 623, 0, -1, 10080, -1, NULL, 8803, 0),
	(749, 'Mặt nạ dạ xoa', 1, 0, 0, 0, 1, 480, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, -1, 624, 0, -1, 10080, -1, NULL, 8804, 0),
	(750, 'Phi Phong Huyền Vũ', 19, 15, 0, 0, 1, 480, 0, 170, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 626, 0, -1, 10080, -1, '', 8806, 0),
	(751, 'Phù phu thê', 28, 0, -1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 627, 0, -1, 0, -1, '', -1, 0),
	(752, 'Rương nguyên liệu cao cấp', 28, 0, -1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 596, 0, -1, 0, -1, '', -1, 0),
	(753, 'Rương nguyên liệu sơ cấp', 28, 0, -1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 596, 0, -1, 0, -1, '', -1, 0),
	(754, 'Áo dài nam \nTăng 100% băng ,sét ,độc ,lửa lan', 0, 0, 0, 1, 1, 480, 10, 10, 5000, 5000, 0, 0, 0, 0, 0, 1, 0, -1, 630, 0, -1, 10080, -1, '', -1, 0),
	(755, 'Áo dài nữ \nTăng 100% băng ,sét ,độc ,lửa lan', 0, 0, 0, 2, 1, 480, 10, 10, 5000, 5000, 0, 0, 0, 0, 0, 1, 0, -1, 631, 0, -1, 10080, -1, '', -1, 0),
	(790, 'Bóng vàng', 27, 0, 0, 0, 5, 0, 5, 159, 0, 0, 0, 0, 159, 0, 0, 0, 0, 0, 698, 0, -1, 10080, -1, '', -1, 0),
	(791, 'Bóng đá nam\r\ntăng 100% băng ,sét ,độc ,lửa lan\r\nrơi thẻ khi giết quái và x2 tl rơi bóng bạc', 0, 0, 0, 1, 1, 480, 10, 10, 5000, 5000, 0, 0, 0, 0, 0, 1, 0, -1, 702, 0, -1, 10080, -1, '', -1, 0),
	(789, 'Bóng đá nữ\r\ntăng 100% băng ,sét ,độc ,lửa lan', 0, 0, 0, 2, 1, 480, 10, 10, 5000, 5000, 0, 0, 0, 0, 0, 1, 0, -1, 700, 0, -1, 10080, -1, '', -1, 0),
	(788, 'Bóng đá nam\r\ntăng 100% băng ,sét ,độc ,lửa lan', 0, 0, 0, 1, 1, 480, 10, 10, 5000, 5000, 0, 0, 0, 0, 0, 1, 0, -1, 699, 0, -1, 10080, -1, '', -1, 0),
	(787, 'Đoạt mệnh cung', 7, 4, 0, 0, 80, 1500, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 50000, 4, 669, 668, 6, 0, -1, '', 138, 0),
	(786, 'Bách linh búa', 6, 4, 0, 0, 80, 1500, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 50000, 3, 645, 644, 6, 0, -1, '', 126, 0),
	(785, 'Minh Đạo bút', 5, 4, 0, 0, 80, 1500, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 50000, 2, 657, 656, 6, 0, -1, '', 132, 0),
	(784, 'Tử mộc đao', 4, 4, 0, 0, 80, 1500, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 50000, 1, 680, 679, 6, 0, -1, '', 145, 0),
	(783, 'Vô ngân kiếm', 3, 5, 0, 0, 80, 1500, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 50000, 0, 693, 692, 2, 0, -1, '', 151, 0),
	(782, 'Địa sát cung', 7, 4, 0, 0, 70, 1500, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 50000, 4, 665, 664, 6, 0, -1, '', 136, 0),
	(781, 'Hóa hồn búa', 6, 4, 0, 0, 70, 1500, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 50000, 3, 641, 640, 6, 0, -1, '', 124, 0),
	(780, 'Định thiên bút', 5, 4, 0, 0, 70, 1500, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 50000, 2, 653, 652, 6, 0, -1, '', 130, 0),
	(779, 'Long phụng đao', 4, 4, 0, 0, 70, 1500, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 50000, 1, 676, 675, 6, 0, -1, '', 142, 0),
	(778, 'Hiên viên kiếm', 3, 5, 0, 0, 70, 1500, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 50000, 0, 689, 688, 2, 0, -1, '', 149, 0),
	(777, 'Thí thần cung', 7, 4, 0, 0, 60, 1500, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 50000, 4, 661, 660, 6, 0, -1, '', 134, 0),
	(776, 'Ngũ Lôi Búa', 6, 4, 0, 0, 60, 1500, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 50000, 3, 637, 636, 6, 0, -1, '', 122, 0),
	(775, 'Dao quang bút', 5, 4, 0, 0, 60, 1500, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 50000, 2, 649, 648, 6, 0, -1, '', 128, 0),
	(774, 'Xà cốt đao', 4, 4, 0, 0, 60, 1500, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 50000, 1, 672, 671, 6, 0, -1, '', 142, 0),
	(773, 'Huyền thiên kiếm', 3, 5, 0, 0, 60, 1500, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 50000, 0, 685, 684, 2, 0, -1, '', 147, 0),
	(772, 'Đoạt mệnh cung', 7, 4, 0, 0, 80, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 671, 670, 6, 0, -1, '', 139, 0),
	(771, 'Bách linh búa', 6, 4, 0, 0, 80, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 647, 646, 6, 0, -1, '', 127, 0),
	(770, 'Minh Đạo bút', 5, 4, 0, 0, 80, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 659, 658, 6, 0, -1, '', 133, 0),
	(769, 'Tử mộc đao', 4, 4, 0, 0, 80, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 681, 680, 6, 0, -1, '', 146, 0),
	(768, 'Vô ngân kiếm', 3, 5, 0, 0, 80, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 695, 694, 2, 0, -1, '', 152, 0),
	(767, 'Địa sát cung', 7, 4, 0, 0, 70, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 667, 666, 6, 0, -1, '', 137, 0),
	(766, 'Hóa hồn búa', 6, 4, 0, 0, 70, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 643, 652, 6, 0, -1, '', 125, 0),
	(764, 'Long phụng đao', 4, 4, 0, 0, 70, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 677, 676, 6, 0, -1, '', 143, 0),
	(765, 'Định thiên bút', 5, 4, 0, 0, 70, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 655, 654, 6, 0, -1, '', 133, 0),
	(763, 'Hiên viên kiếm', 3, 5, 0, 0, 70, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 691, 690, 2, 0, -1, '', 135, 0),
	(761, 'Ngũ Lôi Búa', 6, 4, 0, 0, 60, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 639, 638, 6, 0, -1, '', 123, 0),
	(762, 'Thí thần cung', 7, 4, 0, 0, 60, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 663, 660, 6, 0, -1, '', 134, 0),
	(759, 'Xà cốt đao', 4, 4, 0, 0, 60, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 673, 672, 6, 0, -1, '', 143, 0),
	(760, 'Dao quang bút', 5, 4, 0, 0, 60, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 651, 650, 6, 0, -1, '', 129, 0),
	(758, 'Huyền thiên kiếm', 3, 5, 0, 0, 60, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 687, 686, 2, 0, -1, '', 148, 0),
	(757, 'Áo Âu cơ\r\nTăng 50% băng ,sét ,độc ,lửa lan\r\nCòn 1hp khi bị quái cắn hoặc bị đồ sát', 0, 0, 0, 2, 1, 480, 10, 10, 5000, 5000, 0, 0, 0, 0, 0, 1, 0, -1, 633, 0, -1, 10080, -1, '', -1, 0),
	(792, 'Bóng đá nữ\r\ntăng 100% băng ,sét ,độc ,lửa lan\r\nrơi thẻ khi giết quái và x2 tl rơi bóng bạc', 0, 0, 0, 2, 1, 480, 10, 10, 5000, 5000, 0, 0, 0, 0, 0, 1, 0, -1, 704, 0, -1, 10080, -1, '', -1, 0),
	(793, 'Thẻ vàng', 28, 0, -1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 706, 0, -1, 0, -1, '', -1, 0),
	(794, 'Thẻ đỏ', 28, 0, -1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 705, 0, -1, 0, -1, '', -1, 0),
	(795, 'Phi Phong bóng đá', 19, 15, 0, 0, 1, 480, 0, 170, 0, 0, 0, 0, 170, 0, 0, 0, 0, -1, 707, 0, -1, 10080, -1, '', -1, 0),
	(796, 'Nguyệt Linh Trượng', 5, 0, 0, 0, 10, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 716, 715, 2, 0, -1, '', 170, 0),
	(797, 'Áo Nghê Thường\r\n- Tăng 50% băng ,sét ,độc ,lửa lan\r\n- Quyền năng ma thuật:\r\n. Tỷ lệ xh: 5%\r\n. Gây st 1%-10% hp\r\n- Ám Khí:\r\n. Gây st 1%-5% hp\r\n. Hồi 1%-10%hp', 0, 0, -1, 2, 1, 480, 10, 10, 5000, 5000, 0, 0, 0, 0, 0, 1, 0, -1, 723, 0, -1, 10080, -1, '', 8886, 0),
	(798, 'Áo Ngọc Nữ\r\n- Tăng 50% băng ,sét ,độc ,lửa lan\r\n- Quyền năng ma thuật:\r\n. Tỷ lệ xh: 5%\r\n. Gây st 1%-10% hp\r\n- Ám Khí:\r\n. Gây st 1%-5% hp\r\n. Hồi 1%-10%hp', 0, 0, -1, 2, 1, 480, 10, 10, 5000, 5000, 0, 0, 0, 0, 0, 1, 0, -1, 724, 0, -1, 10080, -1, '', 8887, 0),
	(799, 'Áo Xích Cước\n- Tăng 50% băng ,sét ,độc ,lửa lan\n- Quyền năng ma thuật:\n. Tỷ lệ xh: 5%\n. Gây st 1%-10% hp\n- Ám Khí:\n. Gây st 1%-5% hp\n. Hồi 1%-10%hp', 0, 0, -1, 1, 1, 480, 10, 10, 5000, 5000, 0, 0, 0, 0, 0, 1, 0, -1, 725, 0, -1, 10080, -1, '', 8889, 0),
	(800, 'Áo Kim Đồng\r\n- Tăng 50% băng ,sét ,độc ,lửa lan\r\n- Quyền năng ma thuật:\r\n. Tỷ lệ xh: 5%\r\n. Gây st 1%-10% hp\r\n- Ám Khí:\r\n. Gây st 1%-5% hp\r\n. Hồi 1%-10%hp', 0, 0, -1, 1, 1, 480, 10, 10, 5000, 5000, 0, 0, 0, 0, 0, 1, 0, -1, 722, 0, -1, 10080, -1, '', 8889, 0),
	(801, 'Áo Lôi Công\n- Tăng 50% băng ,sét ,độc ,lửa lan\n- Quyền năng ma thuật:\n. Tỷ lệ xh: 5%\n. Gây st 1%-10% hp\n- Ám Khí:\n. Gây st 1%-5% hp\n. Hồi 1%-10%hp', 0, 0, -1, 1, 1, 480, 10, 10, 5000, 5000, 0, 0, 0, 0, 0, 1, 0, -1, 726, 0, -1, 10080, -1, '', 8890, 0),
	(802, 'Kim hổ bảo bảo', 27, 0, 0, 0, 5, 0, 5, 199, 0, 0, 0, 0, 199, 0, 0, 0, 0, -1, 733, 0, -1, 0, -1, '', -1, 0),
	(803, 'Xích hổ bảo bảo', 27, 0, 0, 0, 1, 0, 5, 197, 0, 0, 0, 0, 197, 0, 0, 0, 0, -1, 731, 0, -1, 0, -1, '', -1, 0),
	(804, 'Cọp cam', 27, 0, 0, 0, 0, 0, 5, 198, 0, 0, 0, 0, 198, 0, 0, 0, 0, -1, 732, 0, -1, 0, -1, '', -1, 0),
	(805, 'Bá Thiên Hổ Nam', 0, 0, -1, 0, 1, 480, 10, 10, 5000, 5000, 0, 0, 0, 0, 0, 1, 0, -1, 729, 0, -1, 10080, -1, '', -1, 0),
	(806, 'Bá Thiên Hổ Nữ', 0, 0, -1, 0, 1, 480, 10, 10, 5000, 5000, 0, 0, 0, 0, 0, 1, 0, -1, 730, 0, -1, 10080, -1, '', -1, 0),
	(807, 'Tơ lụa cấp 4', 28, 0, -1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, -1, 735, 0, -1, 0, -1, '', -1, 3),
	(808, 'Tơ lụa cấp 5', 28, 0, -1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 8, -1, 735, 0, -1, 0, -1, '', -1, 8),
	(809, 'Bạc cấp 4', 28, 0, -1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, -1, 736, 0, -1, 0, -1, '', -1, 3),
	(810, 'Bạc cấp 5', 28, 0, -1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 8, -1, 736, 0, -1, 0, -1, '', -1, 8),
	(811, 'Thủy tinh cấp 4', 28, 0, -1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, -1, 737, 0, -1, 0, -1, '', -1, 3),
	(812, 'Thủy tinh cấp 5', 28, 0, -1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 8, -1, 737, 0, -1, 0, -1, '', -1, 8),
	(813, 'Gỗ sưa cấp 4', 28, 0, -1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, -1, 738, 0, -1, 0, -1, '', -1, 3),
	(814, 'Gỗ sưa cấp 5', 28, 0, -1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 8, -1, 738, 0, -1, 0, -1, '', -1, 8),
	(815, 'Da cứng cấp 4', 28, 0, -1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, -1, 739, 0, -1, 0, -1, '', -1, 3),
	(816, 'Da cứng cấp 5', 28, 0, -1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 8, -1, 739, 0, -1, 0, -1, '', -1, 8),
	(817, 'Đá ngũ hợp tinh khiết lv 1', 28, 0, -1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 8, -1, 734, 0, -1, 0, -1, '', -1, 8),
	(818, 'Đá ngũ hợp tinh khiết lv 2', 28, 0, -1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 10, -1, 734, 0, -1, 0, -1, '', -1, 10),
	(819, 'Đá ngũ hợp tinh khiết lv 3', 28, 0, -1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 13, -1, 734, 0, -1, 0, -1, '', -1, 13),
	(820, 'Đá ngũ hợp tinh khiết lv 4', 28, 0, -1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 15, -1, 734, 0, -1, 0, -1, '', -1, 15),
	(821, 'Đá ngũ hợp tinh khiết lv 5', 28, 0, -1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 20, -1, 734, 0, -1, 0, -1, '', -1, 20),
	(822, 'Đá ngũ hợp tinh khiết lv 6', 28, 0, -1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25, -1, 734, 0, -1, 0, -1, '', -1, 25),
	(823, 'Đá tách nguyên liệu 5x', 28, 0, -1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 8, -1, 740, 0, -1, 0, -1, '', -1, 8),
	(824, 'Đá tách nguyên liệu 6x', 28, 0, -1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 10, -1, 741, 0, -1, 0, -1, '', -1, 10),
	(825, 'Đá tách nguyên liệu 7x', 28, 0, -1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 13, -1, 742, 0, -1, 0, -1, '', -1, 13),
	(826, 'Đá thuộc tính thường', 28, 0, -1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, -1, 743, 0, -1, 0, -1, '', -1, 5),
	(827, 'Đá thuộc tính cao cấp', 28, 0, -1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 10, -1, 744, 0, -1, 0, -1, '', -1, 10),
	(828, 'Xương cấp 6', 28, 0, -1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 13, -1, 745, 0, -1, 0, -1, '', -1, 13),
	(829, 'Ngọc huyền minh cấp 6', 28, 0, -1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 125, -1, 746, 0, -1, 0, -1, '', -1, 125),
	(830, 'Đá tiến giai', 28, 0, -1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, -1, 747, 0, -1, 0, -1, '', -1, 5),
	(831, 'Hỏa thạch cấp 1', 28, 0, -1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, -1, 748, 0, -1, 0, -1, '', -1, 5),
	(832, 'Pet Sao la', 27, 0, 0, 0, 1, 0, 5, 212, 0, 0, 0, 0, 212, 0, 0, 0, 0, -1, 750, 0, -1, 10080, -1, '', -1, 0),
	(833, 'Miêu mắt xanh-Tăng 100% băng ,sét ,độc ,lửa lan-Kháng 90% sát thương lan-Tỷ lệ xh:15%', 0, 0, -1, 2, 1, 480, 10, 10, 5000, 5000, 0, 0, 0, 0, 0, 1, 0, -1, 764, 0, -1, 10080, -1, '', -1, 0),
	(834, 'Miêu quý tộc-Tăng 100% băng ,sét ,độc ,lửa lan-Kháng 90% sát thương lan-Tỷ lệ xh:15%', 0, 0, -1, 1, 1, 480, 10, 10, 5000, 5000, 0, 0, 0, 0, 0, 1, 0, -1, 762, 0, -1, 10080, -1, '', -1, 0),
	(835, 'Nhánh hoa đào', 28, 0, 0, 0, 10, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 766, 0, -1, 0, -1, '', -1, 0),
	(836, 'Thẻ đổi tên', 28, 0, -1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 767, 0, -1, 0, -1, '', -1, 0),
	(837, 'Mặt nạ thỏ', 1, 0, 0, 0, 1, 480, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, -1, 769, 0, -1, 10080, -1, '', -1, 0),
	(838, 'Mặt nạ cáo', 1, 0, 0, 0, 1, 480, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, -1, 768, 0, -1, 10080, -1, '', -1, 0),
	(839, 'Giáp rồng-Tăng 100% băng ,sét ,độc ,lửa lan-Kháng 90% sát thương lan-Tỷ lệ xh:15%', 0, 73, 0, 0, 1, 480, 10, 10, 5000, 5000, 80, 0, 0, 73, 73, 1, 0, -1, 771, 0, -1, 10080, -1, '', 8932, 0),
	(756, 'Áo Lạc long quân\r\nTăng 50% băng ,sét ,độc ,lửa lan\r\nCòn 1hp khi bị quái cắn hoặc bị đồ sát', 0, 0, 0, 1, 1, 480, 10, 10, 5000, 5000, 0, 0, 0, 0, 0, 1, 0, -1, 635, 0, -1, 10080, -1, '', -1, 0),
	(841, 'Áo sa tăng nam cấp 1', 0, 0, -1, 0, 1, 480, 10, 10, 5000, 5000, 0, 0, 10, 0, 0, 0, 0, -1, 772, 0, -1, 10080, -1, '', -1, 0),
	(842, 'Áo sa tăng nam cấp 2', 0, 0, -1, 0, 1, 480, 10, 10, 5000, 5000, 0, 0, 10, 0, 0, 0, 0, -1, 773, 0, -1, 10080, -1, '', -1, 0),
	(843, 'Áo sa tăng nữ cấp 1', 0, 0, -1, 0, 1, 480, 10, 10, 5000, 5000, 0, 0, 10, 0, 0, 0, 0, -1, 774, 0, -1, 10080, -1, '', -1, 0),
	(844, 'Áo sa tăng nữ cấp 2', 0, 0, -1, 0, 1, 480, 10, 10, 5000, 5000, 0, 0, 10, 0, 0, 0, 0, -1, 775, 0, -1, 10080, -1, '', -1, 0),
	(845, 'Áo bát giới nam cấp 2', 0, 0, -1, 0, 1, 480, 10, 10, 5000, 5000, 0, 0, 10, 0, 0, 0, 0, -1, 777, 0, -1, 10080, -1, '', -1, 0),
	(846, 'Áo bát giới nữ cấp 1', 0, 0, -1, 0, 1, 480, 10, 10, 5000, 5000, 0, 0, 10, 0, 0, 0, 0, -1, 778, 0, -1, 10080, -1, '', -1, 0),
	(847, 'Áo bát giới nữ cấp 2', 0, 0, -1, 0, 1, 480, 10, 10, 5000, 5000, 0, 0, 10, 0, 0, 0, 0, -1, 779, 0, -1, 10080, -1, '', -1, 0),
	(848, 'Mảnh áo bát giới', 29, 0, -1, 0, 1, 480, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 788, 0, -1, 10080, -1, '', -1, 0),
	(849, 'Mảnh quần bát giới', 29, 0, -1, 0, 1, 480, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 789, 0, -1, 10080, -1, '', -1, 0),
	(850, 'Mảnh kim cang bát giới', 29, 0, -1, 0, 1, 480, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 790, 0, -1, 10080, -1, '', -1, 0),
	(851, 'Mảnh kim cô bát giới', 29, 0, -1, 0, 1, 480, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 791, 0, -1, 10080, -1, '', -1, 0),
	(852, 'Mảnh giày bát giới', 29, 0, -1, 0, 1, 480, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 792, 0, -1, 10080, -1, '', -1, 0),
	(853, 'Mảnh găng bát giới', 29, 0, -1, 0, 1, 480, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 793, 0, -1, 10080, -1, '', -1, 0),
	(854, 'Mảnh áo sa tăng', 29, 0, -1, 0, 1, 480, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 794, 0, -1, 10080, -1, '', -1, 0),
	(855, 'Mảnh quần sa tăng', 29, 0, -1, 0, 1, 480, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 795, 0, -1, 10080, -1, '', -1, 0),
	(856, 'Mảnh kim cang sa tăng', 29, 0, -1, 0, 1, 480, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 796, 0, -1, 10080, -1, '', -1, 0),
	(857, 'Mảnh kim cô sa tăng', 29, 0, -1, 0, 1, 480, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 797, 0, -1, 10080, -1, '', -1, 0),
	(858, 'Mảnh giày sa tăng', 29, 0, -1, 0, 1, 480, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 798, 0, -1, 10080, -1, '', -1, 0),
	(859, 'Mảnh găng sa tăng', 29, 0, -1, 0, 1, 480, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 799, 0, -1, 10080, -1, '', -1, 0),
	(860, 'Áo bát giới', 29, 0, -1, 0, 1, 480, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 788, 0, -1, 10080, -1, '', -1, 0),
	(861, 'Quần bát giới', 29, 0, -1, 0, 1, 480, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 789, 0, -1, 10080, -1, '', -1, 0),
	(862, 'Kim cang bát giới', 29, 0, -1, 0, 1, 480, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 790, 0, -1, 10080, -1, '', -1, 0),
	(863, 'Kim cô bát giới', 29, 0, -1, 0, 1, 480, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 791, 0, -1, 10080, -1, '', -1, 0),
	(864, 'Giày bát giới', 29, 0, -1, 0, 1, 480, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 792, 0, -1, 10080, -1, '', -1, 0),
	(865, 'Găng bát giới', 29, 0, -1, 0, 1, 480, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 793, 0, -1, 10080, -1, '', -1, 0),
	(866, 'Áo sa tăng', 29, 0, -1, 0, 1, 480, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 794, 0, -1, 10080, -1, '', -1, 0),
	(867, 'Quần sa tăng', 29, 0, -1, 0, 1, 480, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 795, 0, -1, 10080, -1, '', -1, 0),
	(868, 'Kim cang sa tăng', 29, 0, -1, 0, 1, 480, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 796, 0, -1, 10080, -1, '', -1, 0),
	(869, 'Kim cô sa tăng', 29, 0, -1, 0, 1, 480, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 797, 0, -1, 10080, -1, '', -1, 0),
	(870, 'Giày sa tăng', 29, 0, -1, 0, 1, 480, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 798, 0, -1, 10080, -1, '', -1, 0),
	(871, 'Găng sa tăng', 29, 0, -1, 0, 1, 480, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 799, 0, -1, 10080, -1, '', -1, 0),
	(872, 'Mặt nạ ông địa', 1, 0, 0, 0, 1, 480, 10, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, -1, 800, 0, -1, 10080, -1, '', -1, 0),
	(873, 'Capybara', 27, 0, 0, 0, 5, 0, 5, 245, 0, 0, 0, 0, 245, 0, 0, 0, 0, 0, 801, 0, -1, 10080, -1, '', -1, 0),
	(874, 'Hộ vệ lân sư tử 4x lv 1', 21, 0, 0, 0, 45, 480, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 55, -1, 802, 0, -1, 10080, -1, '', -1, 55),
	(875, 'Hộ vệ lân sư tử 4x lv 2', 21, 0, 0, 0, 45, 480, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 55, -1, 803, 0, -1, 10080, -1, '', -1, 55),
	(876, 'Hộ vệ lân sư tử 4x lv 3', 21, 0, 0, 0, 45, 480, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 55, -1, 804, 0, -1, 10080, -1, '', -1, 55),
	(877, 'Hộ vệ lân sư tử 5x lv 1', 21, 0, 0, 0, 55, 480, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 55, -1, 805, 0, -1, 10080, -1, '', -1, 55),
	(878, 'Hộ vệ lân sư tử 5x lv 2', 21, 0, 0, 0, 55, 480, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 55, -1, 806, 0, -1, 10080, -1, '', -1, 55),
	(879, 'Hộ vệ lân sư tử 5x lv 3', 21, 0, 0, 0, 55, 480, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 55, -1, 807, 0, -1, 10080, -1, '', -1, 55),
	(880, 'Hộ vệ lân sư tử 6x lv 1', 21, 0, 0, 0, 65, 480, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 55, -1, 808, 0, -1, 10080, -1, '', -1, 55),
	(881, 'Hộ vệ lân sư tử 6x lv 2', 21, 0, 0, 0, 65, 480, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 55, -1, 809, 0, -1, 10080, -1, '', -1, 55),
	(882, 'Hộ vệ lân sư tử 6x lv 3', 21, 0, 0, 0, 64, 480, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 55, -1, 810, 0, -1, 10080, -1, '', -1, 55),
	(840, 'Test', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 0, 0, -1, 0, -1, NULL, -1, 0),
	(883, 'Trứng hoả kỳ lân', 21, 0, 0, 0, 65, 480, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 55, -1, 839, 0, -1, 0, -1, '', -1, 55),
	(884, 'Trứng đương khang', 21, 0, 0, 0, 65, 480, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 55, -1, 842, 0, -1, 0, -1, '', -1, 55),
	(885, 'Trứng lân', 21, 0, 0, 0, 65, 480, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 55, -1, 841, 0, -1, 0, -1, '', -1, 55),
	(886, 'Trứng bạch cốt', 21, 0, 0, 0, 65, 480, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 55, -1, 843, 0, -1, 0, -1, '', -1, 55),
	(887, 'Trứng rồng băng', 21, 0, 0, 0, 65, 480, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 55, -1, 840, 0, -1, 0, -1, '', -1, 55),
	(888, 'Áo noel nam 2024\n- Bỏng lạnh trong [10s]- Thời gian hồi: [30s]- Tỷ lệ xh: 5%', 0, 0, -1, 1, 1, 480, 10, 10, 5000, 5000, 0, 0, 0, 0, 0, 1, 0, -1, 844, 0, -1, 10080, -1, '', -1, 0),
	(889, 'Áo noel nữ 2024\n- Bỏng lạnh trong [10s]- Thời gian hồi: [30s]- Tỷ lệ xh: 5%', 0, 0, -1, 2, 1, 480, 10, 10, 5000, 5000, 0, 0, 0, 0, 0, 1, 0, -1, 846, 0, -1, 10080, -1, '', -1, 0),
	(890, 'Quạt ba tiêu', 4, 0, 0, 0, 10, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 822, 822, 2, 0, -1, '', 267, 0),
	(891, 'Gậy như ý', 4, 0, 0, 0, 10, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 821, 821, 2, 0, -1, '', 268, 0),
	(892, 'Lưỡng nhận đao', 4, 0, 0, 0, 10, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 820, 820, 2, 0, -1, '', 269, 0),
	(893, 'Huyền Long Kiếm', 3, 4, 0, 0, 86, 1500, 380, 0, 0, 0, 0, 0, 0, 0, 0, 0, 12500, 0, 422, 5, 3, 0, -1, NULL, -1, 0),
	(894, 'Thanh Long Đao', 4, 4, 0, 0, 86, 1500, 380, 0, 0, 0, 0, 0, 0, 0, 0, 0, 12500, 1, 424, 5, 3, 0, -1, NULL, -1, 0),
	(895, 'Thiên Long Bút', 5, 4, 0, 0, 86, 1500, 380, 0, 0, 0, 0, 0, 0, 0, 0, 0, 12500, 2, 425, 5, 3, 0, -1, NULL, -1, 0),
	(896, 'Kim Long Búa', 6, 4, 0, 0, 86, 1500, 380, 0, 0, 0, 0, 0, 0, 0, 0, 0, 12500, 3, 427, 5, 3, 0, -1, NULL, -1, 0),
	(897, 'Phương Thiên Cung', 7, 4, 0, 0, 86, 1500, 380, 0, 0, 0, 0, 0, 0, 0, 0, 0, 12500, 4, 429, 5, 3, 0, -1, NULL, -1, 0),
	(903, 'Cải Trang Bát Giới', 0, 0, -1, 0, 1, 480, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 776, 0, -1, 10080, -1, '', -1, 0),
	(898, 'Cánh Lam Băng', 29, 0, 0, 0, 10, 1500, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 12500, -1, 834, 0, 0, 0, -1, NULL, 270, 0),
	(899, 'Cánh Bạch Linh', 29, 0, 0, 0, 10, 1500, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 12500, -1, 835, 0, 0, 0, -1, NULL, 271, 0),
	(900, 'Cánh Xích Vũ', 29, 0, 0, 0, 10, 1500, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 12500, -1, 836, 0, 0, 0, -1, NULL, 272, 0),
	(901, 'Cánh Hồng Vân', 29, 0, 0, 0, 10, 1500, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 12500, -1, 837, 0, 0, 0, -1, NULL, 273, 0),
	(902, 'Cánh Tử ảnh', 29, 0, 0, 0, 10, 1500, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 12500, -1, 838, 0, 0, 0, -1, NULL, 274, 0),
	(904, 'Cải Trang Sa Tăng', 0, 0, -1, 0, 1, 480, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 777, 0, -1, 10080, -1, '', -1, 0);
/*!40000 ALTER TABLE `data_item` ENABLE KEYS */;

-- Dumping structure for table kpah.data_monster
CREATE TABLE IF NOT EXISTS `data_monster` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `style` int(10) unsigned NOT NULL,
  `he` int(11) NOT NULL,
  `level` int(11) NOT NULL,
  `maxhp` int(11) NOT NULL,
  `attack` int(11) NOT NULL DEFAULT '0',
  `defent` int(11) NOT NULL DEFAULT '0',
  `accurate` int(11) NOT NULL DEFAULT '0',
  `dodge` int(11) NOT NULL DEFAULT '0',
  `rcvxp` int(10) unsigned NOT NULL DEFAULT '0',
  `rcvgold` int(10) unsigned NOT NULL DEFAULT '0',
  `active` int(10) unsigned NOT NULL DEFAULT '0',
  `palate` int(10) unsigned NOT NULL DEFAULT '0',
  `spalate` int(10) unsigned NOT NULL DEFAULT '0',
  `isuse` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `move` tinyint(3) unsigned NOT NULL DEFAULT '1',
  `speed` int(10) unsigned NOT NULL DEFAULT '1',
  `h` int(10) unsigned NOT NULL DEFAULT '20',
  `w` int(10) unsigned NOT NULL DEFAULT '20',
  `xcenter` int(10) unsigned NOT NULL DEFAULT '8',
  `ycenter` int(10) unsigned NOT NULL DEFAULT '8',
  `height` int(10) unsigned NOT NULL DEFAULT '20',
  `isnew` int(10) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=141 DEFAULT CHARSET=utf8;

-- Dumping data for table kpah.data_monster: 138 rows
DELETE FROM `data_monster`;
/*!40000 ALTER TABLE `data_monster` DISABLE KEYS */;
INSERT INTO `data_monster` (`id`, `name`, `style`, `he`, `level`, `maxhp`, `attack`, `defent`, `accurate`, `dodge`, `rcvxp`, `rcvgold`, `active`, `palate`, `spalate`, `isuse`, `move`, `speed`, `h`, `w`, `xcenter`, `ycenter`, `height`, `isnew`) VALUES
	(1, 'Nhím', 1, 3, 1, 200, 8, 2, 690, 5, 20, 10, 0, 1, 0, 1, 1, 1, 20, 20, 8, 8, 20, 0),
	(2, 'Sâu', 2, 1, 2, 300, 11, 2, 690, 5, 30, 15, 1, 2, 0, 1, 1, 1, 20, 20, 8, 8, 20, 0),
	(3, 'Giọt nước', 3, 0, 3, 450, 17, 3, 690, 5, 45, 20, 0, 3, 0, 1, 1, 1, 20, 20, 8, 8, 20, 0),
	(4, 'Gà điên', 4, 2, 4, 650, 25, 5, 690, 10, 65, 30, 1, 4, 0, 1, 1, 1, 20, 20, 8, 8, 20, 0),
	(5, 'Rắn lục', 5, 4, 5, 900, 34, 6, 690, 15, 90, 40, 0, 5, 0, 1, 1, 1, 20, 20, 8, 8, 20, 0),
	(6, 'Ma trơi', 6, 2, 6, 1200, 45, 8, 690, 30, 120, 50, 1, 6, 0, 1, 1, 1, 20, 20, 8, 8, 20, 0),
	(7, 'Nắp ấm', 7, 1, 7, 1550, 57, 10, 690, 10, 155, 60, 0, 7, 0, 1, 1, 1, 20, 20, 8, 8, 20, 0),
	(8, 'Rệp quỷ', 8, 3, 8, 1950, 74, 13, 690, 0, 195, 70, 1, 8, 0, 1, 1, 1, 20, 20, 8, 8, 20, 0),
	(9, 'Chuột cống', 9, 4, 9, 2400, 90, 24, 690, 5, 240, 80, 0, 9, 0, 1, 1, 1, 20, 20, 8, 8, 20, 0),
	(10, 'Quỷ hoa', 10, 1, 10, 3000, 113, 30, 690, 20, 300, 100, 1, 10, 0, 1, 1, 1, 20, 20, 8, 8, 20, 0),
	(11, 'Sâu róm', 11, 1, 11, 3660, 125, 37, 690, 20, 366, 120, 0, 11, 0, 1, 1, 1, 20, 20, 8, 8, 20, 0),
	(12, 'Lửa ma', 12, 2, 12, 4380, 139, 44, 690, 20, 438, 120, 1, 12, 0, 1, 1, 1, 20, 20, 8, 8, 20, 0),
	(13, 'Giọt nước lớn', 13, 0, 13, 5160, 150, 52, 690, 20, 516, 150, 0, 13, 0, 1, 1, 1, 20, 20, 8, 8, 20, 0),
	(14, 'Gà con', 14, 2, 18, 9960, 184, 100, 690, 10, 996, 400, 1, 14, 0, 1, 1, 1, 20, 20, 8, 8, 20, 0),
	(15, 'Heo mọi', 15, 3, 14, 6000, 154, 60, 690, 10, 600, 200, 1, 15, 0, 1, 1, 1, 20, 20, 8, 8, 20, 0),
	(16, 'Bọ cạp', 16, 4, 15, 6900, 165, 63, 690, 15, 690, 240, 1, 16, 0, 1, 1, 1, 20, 20, 8, 8, 20, 0),
	(17, 'Rùa đỏ', 17, 2, 13, 5160, 150, 52, 690, 5, 516, 170, 0, 17, 0, 1, 1, 1, 20, 20, 8, 8, 20, 0),
	(18, 'Nhện tím', 18, 3, 24, 18600, 638, 220, 690, 15, 1390, 450, 1, 18, 0, 1, 1, 1, 20, 20, 8, 8, 20, 0),
	(19, 'Nấm ma', 19, 1, 16, 7860, 188, 78, 690, 10, 786, 280, 0, 19, 0, 1, 1, 1, 20, 20, 8, 8, 20, 0),
	(20, 'Quỷ sinh hoa', 20, 1, 17, 8880, 225, 88, 690, 20, 888, 320, 1, 20, 0, 1, 1, 1, 20, 20, 8, 8, 20, 0),
	(21, 'Rết', 21, 4, 15, 6900, 263, 69, 690, 10, 690, 240, 0, 21, 0, 1, 1, 1, 20, 20, 8, 8, 20, 0),
	(22, 'Gà khổng lồ', 22, 2, 30, 30150, 1088, 340, 690, 10, 1990, 610, 1, 22, 0, 1, 1, 1, 20, 20, 8, 8, 20, 0),
	(23, 'Rắn khổng lồ', 23, 1, 31, 32630, 1163, 360, 690, 15, 2090, 630, 1, 23, 0, 1, 1, 1, 20, 20, 8, 8, 20, 0),
	(24, 'Bướm khổng lồ', 24, 3, 18, 9960, 184, 100, 690, 10, 996, 400, 1, 24, 0, 1, 1, 1, 20, 20, 8, 8, 20, 0),
	(25, 'Bóng ma', 25, 0, 22, 15310, 563, 180, 690, 15, 1190, 400, 1, 25, 0, 1, 1, 1, 20, 20, 8, 8, 20, 0),
	(26, 'Thủy nhãn', 26, 0, 21, 13770, 488, 160, 690, 15, 1090, 360, 1, 26, 0, 1, 1, 1, 20, 20, 8, 8, 20, 0),
	(27, 'Skeleton', 27, 1, 24, 18600, 638, 220, 690, 10, 1390, 450, 1, 27, 0, 1, 1, 1, 20, 20, 8, 8, 20, 0),
	(28, 'Cọp khổng lồ', 28, 2, 20, 12300, 413, 160, 690, 5, 890, 320, 1, 28, 0, 1, 1, 1, 20, 20, 8, 8, 20, 0),
	(29, 'Cá sấu', 29, 0, 26, 22170, 750, 260, 690, 10, 1590, 500, 1, 29, 0, 1, 1, 1, 20, 20, 8, 8, 20, 0),
	(30, 'ếch quỷ', 30, 0, 27, 24060, 938, 280, 690, 15, 1690, 530, 1, 30, 0, 1, 1, 1, 20, 20, 8, 8, 20, 0),
	(31, 'Dế khổng lồ', 31, 3, 33, 37830, 1313, 365, 690, 10, 2290, 660, 1, 31, 0, 1, 1, 1, 20, 20, 8, 8, 20, 0),
	(32, 'Nấm tinh', 32, 1, 32, 35190, 1238, 360, 690, 10, 2190, 590, 1, 32, 0, 1, 1, 1, 20, 20, 8, 8, 20, 0),
	(33, 'Trâu núi', 33, 3, 34, 40550, 1463, 380, 690, 20, 2390, 720, 1, 33, 0, 1, 1, 1, 20, 20, 8, 8, 20, 0),
	(34, 'Sơn tặc', 34, 1, 35, 43350, 1575, 420, 690, 20, 3000, 800, 1, 34, 0, 1, 1, 1, 20, 20, 8, 8, 20, 0),
	(35, 'Hải tặc', 35, 0, 36, 46230, 1733, 420, 690, 15, 3500, 900, 1, 35, 0, 1, 1, 1, 20, 20, 8, 8, 20, 0),
	(36, 'Long Trụ', 36, 0, 33, 50000000, 1, 28000, 690, 10, 2290, 680, 1, 66, 0, 1, 0, 1, 20, 20, 8, 8, 20, 0),
	(37, 'Long trụ phụ', 37, 3, 37, 25000000, 1, 25000, 690, 20, 3800, 950, 1, 67, 0, 1, 0, 1, 20, 20, 8, 8, 20, 0),
	(38, 'Thuồng luồng', 38, 0, 38, 500000, 2250, 900, 690, 30, 50000, 100000, 1, 38, 0, 2, 1, 1, 20, 20, 8, 8, 20, 0),
	(39, 'Thằn lằn', 39, 0, 39, 200000000, 30000, 25500, 690, 30, 400000, 200000, 1, 39, 0, 2, 1, 1, 20, 20, 8, 8, 20, 0),
	(40, 'Rết đỏ', 40, 0, 38, 53678, 1778, 494, 690, 20, 3800, 1100, 1, 21, 1, 1, 1, 1, 20, 20, 8, 8, 20, 0),
	(41, 'Rết tía', 41, 0, 49, 106654, 3000, 650, 690, 20, 5736, 2200, 1, 21, 2, 1, 1, 1, 20, 20, 8, 8, 20, 0),
	(42, 'Rết xanh', 42, 0, 43, 75098, 2280, 600, 690, 20, 4620, 1600, 1, 21, 3, 1, 1, 1, 20, 20, 8, 8, 20, 0),
	(43, 'Liên hoa trụ', 43, 0, 39, 20000000, 20000, 23000, 690, 20, 3956, 1200, 1, 68, 0, 1, 0, 1, 20, 20, 8, 8, 20, 0),
	(44, 'Tử kê', 44, 0, 51, 118730, 3015, 670, 690, 20, 6140, 2400, 1, 22, 2, 1, 1, 1, 20, 20, 8, 8, 20, 0),
	(45, 'Hồng kê', 45, 0, 39, 57634, 1793, 534, 690, 20, 3956, 1200, 1, 22, 3, 1, 1, 1, 20, 20, 8, 8, 20, 0),
	(46, 'Tướng thủ thành', 46, 0, 42, 80000000, 22000, 22000, 690, 20, 4448, 1500, 1, 69, 0, 2, 1, 1, 20, 20, 8, 8, 20, 0),
	(134, 'Rắn mang bành', 47, 0, 48, 100918, 2318, 640, 690, 20, 5540, 2100, 1, 23, 2, 1, 1, 1, 20, 20, 8, 8, 20, 0),
	(48, 'Sư tử', 48, 0, 60, 193830, 6810, 900, 690, 20, 9020, 5200, 1, 49, 0, 1, 1, 1, 20, 20, 8, 8, 20, 0),
	(49, 'ốc ma', 49, 0, 72, 460530, 14000, 16000, 690, 20, 16000, 8100, 1, 62, 0, 1, 1, 1, 20, 20, 8, 8, 20, 0),
	(50, 'Bướm nâu', 50, 0, 44, 79894, 2288, 610, 690, 20, 4796, 1700, 1, 24, 2, 1, 1, 1, 20, 20, 8, 8, 20, 0),
	(51, 'Ma cây', 51, 0, 74, 560530, 17000, 18000, 690, 20, 18000, 10000, 1, 63, 0, 1, 1, 1, 20, 20, 8, 8, 20, 0),
	(133, 'Huyết ma', 52, 0, 46, 90030, 2303, 620, 690, 20, 5160, 1900, 1, 25, 1, 1, 1, 1, 20, 20, 8, 8, 20, 0),
	(53, 'Cá Thòi lòi', 53, 0, 68, 330530, 12000, 6400, 690, 20, 12848, 5600, 1, 50, 0, 1, 1, 1, 20, 20, 8, 8, 20, 0),
	(54, 'Cua càng to', 54, 0, 70, 360530, 12000, 7100, 690, 20, 14048, 7600, 1, 51, 0, 1, 1, 1, 20, 20, 8, 8, 20, 0),
	(55, 'độc nhãn', 55, 0, 37, 49878, 1763, 456, 690, 20, 3648, 1000, 1, 26, 1, 1, 1, 1, 20, 20, 8, 8, 20, 0),
	(56, 'Cá ma', 56, 0, 76, 680530, 20000, 20000, 690, 20, 20000, 11000, 1, 64, 0, 1, 1, 1, 20, 20, 8, 8, 20, 0),
	(57, 'Huyết nhãn', 57, 0, 45, 84870, 2295, 615, 690, 20, 4976, 1800, 1, 26, 3, 1, 1, 1, 20, 20, 8, 8, 20, 0),
	(58, 'Quỷ hoa', 58, 0, 78, 800530, 23000, 22000, 690, 20, 22000, 14000, 1, 65, 0, 1, 1, 1, 20, 20, 8, 8, 20, 0),
	(60, 'Tê giác', 60, 0, 62, 240878, 7040, 1000, 690, 20, 9622, 2900, 1, 52, 0, 1, 1, 1, 20, 20, 8, 8, 20, 0),
	(61, 'Cọp tím', 61, 0, 57, 160078, 3045, 710, 690, 20, 7448, 3000, 1, 28, 1, 0, 1, 1, 20, 20, 8, 8, 20, 0),
	(62, 'Cọp xanh', 62, 0, 50, 112590, 3008, 660, 690, 20, 5936, 2300, 1, 28, 2, 1, 1, 1, 20, 20, 8, 8, 20, 0),
	(63, 'Cọp đỏ', 63, 0, 40, 61750, 2258, 550, 690, 20, 4116, 1300, 1, 28, 3, 1, 1, 1, 20, 20, 8, 8, 20, 0),
	(64, 'Cá sấu đỏ', 64, 0, 60, 183830, 3053, 720, 690, 20, 8156, 3300, 1, 29, 1, 0, 1, 1, 20, 20, 8, 8, 20, 0),
	(65, 'Cua đinh', 65, 0, 58, 172230, 6590, 900, 690, 20, 8400, 3400, 1, 53, 0, 1, 1, 1, 20, 20, 8, 8, 20, 0),
	(66, 'Cá sấu xanh', 66, 0, 62, 200878, 3068, 740, 690, 20, 8648, 3500, 1, 29, 3, 0, 1, 1, 20, 20, 8, 8, 20, 0),
	(67, 'Cóc lam', 67, 0, 63, 209778, 3750, 750, 690, 20, 8900, 3600, 1, 30, 1, 0, 1, 1, 20, 20, 8, 8, 20, 0),
	(68, 'Cóc lửa', 68, 0, 42, 70478, 2273, 590, 690, 20, 4448, 1500, 1, 30, 2, 1, 1, 1, 20, 20, 8, 8, 20, 0),
	(69, 'Cóc xanh', 69, 0, 65, 228350, 3758, 760, 690, 20, 9416, 3800, 1, 30, 3, 0, 1, 1, 20, 20, 8, 8, 20, 0),
	(70, 'Dế lửa', 70, 0, 66, 238030, 3765, 770, 690, 20, 9680, 3900, 1, 31, 1, 0, 1, 1, 20, 20, 8, 8, 20, 0),
	(71, 'Dế cam', 71, 0, 41, 66030, 2265, 570, 690, 20, 4280, 1400, 1, 31, 2, 1, 1, 1, 20, 20, 8, 8, 20, 0),
	(72, 'Dế lam', 72, 0, 68, 258198, 3773, 780, 690, 20, 10220, 4100, 1, 31, 3, 0, 1, 1, 20, 20, 8, 8, 20, 0),
	(73, 'trâu đỏ', 73, 0, 69, 268694, 3780, 790, 690, 20, 10496, 4200, 1, 33, 1, 0, 1, 1, 20, 20, 8, 8, 20, 0),
	(74, 'Người đá', 74, 0, 54, 158414, 6130, 800, 690, 20, 6776, 4300, 1, 54, 0, 1, 1, 1, 20, 20, 8, 8, 20, 0),
	(75, 'trâu xanh', 75, 0, 71, 290530, 3795, 810, 690, 20, 11060, 4400, 1, 33, 3, 0, 1, 1, 20, 20, 8, 8, 20, 0),
	(76, 'ốc sên', 76, 0, 56, 152630, 6360, 820, 690, 20, 7220, 4500, 1, 55, 0, 1, 1, 1, 20, 20, 8, 8, 20, 0),
	(77, 'Bọ hung', 77, 0, 64, 268350, 10500, 5000, 690, 20, 11640, 4600, 1, 56, 0, 1, 1, 1, 20, 20, 8, 8, 20, 0),
	(78, 'Sơn tặc 3', 78, 0, 74, 325454, 3818, 840, 690, 20, 11936, 4700, 1, 34, 3, 0, 1, 1, 20, 20, 8, 8, 20, 0),
	(79, 'Hải tặc 1', 79, 0, 75, 337690, 4500, 850, 690, 20, 12236, 4800, 1, 35, 1, 0, 1, 1, 20, 20, 8, 8, 20, 0),
	(80, 'Nấm độc', 80, 0, 52, 125078, 5900, 800, 690, 20, 6348, 4900, 1, 57, 0, 1, 1, 1, 20, 20, 8, 8, 20, 0),
	(81, 'Cánh cam', 81, 0, 66, 290530, 11000, 5700, 690, 20, 11848, 5000, 1, 58, 0, 1, 1, 1, 20, 20, 8, 8, 20, 0),
	(82, 'đại bàng', 82, 0, 40, 200000000, 30000, 25500, 690, 20, 200000, 100000, 1, 40, 0, 2, 1, 1, 20, 20, 8, 8, 20, 0),
	(83, 'cổng thành', 83, 0, 40, 100000000, 1, 27000, 690, 20, 0, 0, 1, 41, 0, 2, 0, 1, 20, 20, 8, 8, 20, 0),
	(84, 'Cương thi', 84, 0, 40, 5000, 3750, 500, 690, 20, 5000, 5000, 1, 42, 0, 2, 1, 1, 20, 20, 8, 8, 20, 0),
	(90, 'Người tuyết', 90, 0, 40, 150000000, 28000, 27000, 690, 20, 2000000, 900000, 1, 48, 0, 2, 1, 1, 20, 20, 8, 8, 20, 0),
	(85, 'Đá', 85, 0, 40, 50, 0, 0, 0, 0, 0, 0, 1, 46, 0, 2, 0, 1, 20, 20, 8, 8, 20, 0),
	(86, 'Bông', 86, 0, 40, 50, 0, 0, 0, 0, 0, 0, 1, 43, 0, 2, 0, 1, 20, 20, 8, 8, 20, 0),
	(87, 'Gỗ', 87, 0, 40, 50, 0, 0, 0, 0, 0, 0, 1, 45, 0, 2, 0, 1, 20, 20, 8, 8, 20, 0),
	(88, 'Da', 88, 0, 40, 50, 0, 0, 0, 0, 0, 0, 1, 44, 0, 2, 0, 1, 20, 20, 8, 8, 20, 0),
	(89, 'Sắt', 89, 0, 40, 50, 0, 0, 0, 0, 0, 0, 1, 47, 0, 2, 0, 1, 20, 20, 8, 8, 20, 0),
	(91, 'Rắn chị', 91, 0, 50, 150000000, 27000, 25000, 690, 20, 1000000, 500000, 1, 59, 1, 2, 1, 1, 20, 20, 8, 8, 20, 0),
	(92, 'Rắn em', 92, 0, 50, 150000000, 27000, 25000, 690, 20, 1000000, 500000, 1, 59, 0, 2, 1, 1, 20, 20, 8, 8, 20, 0),
	(93, 'Mắt quỷ', 93, 0, 55, 100000000, 24000, 20000, 690, 20, 1000000, 500000, 1, 60, 0, 2, 1, 1, 20, 20, 8, 8, 20, 0),
	(94, 'Bạch cốt tướng quân', 94, 0, 60, 150000000, 28000, 27000, 690, 20, 2000000, 900000, 1, 61, 0, 2, 1, 1, 20, 20, 8, 8, 20, 0),
	(95, 'Ngựa ca nhan trang', 95, 0, 1, 158414, 1, 1, 1, 1, 1, 1, 0, 70, 0, 1, 1, 1, 20, 20, 8, 8, 20, 0),
	(96, 'Ngựa ca nhan xanh', 96, 0, 1, 158414, 1, 1, 1, 1, 1, 1, 0, 71, 0, 1, 1, 1, 20, 20, 8, 8, 20, 0),
	(97, 'Ngựa ca nhan đỏ', 97, 0, 1, 158414, 1, 1, 1, 1, 1, 1, 0, 72, 0, 1, 1, 1, 20, 20, 8, 8, 20, 0),
	(98, 'Ngựa ca nhan xanh la', 98, 0, 1, 158414, 1, 0, 0, 0, 0, 0, 0, 73, 0, 1, 1, 1, 20, 20, 8, 8, 20, 0),
	(99, 'Ngựa ca nhan vàng', 99, 0, 1, 158414, 1, 0, 0, 0, 0, 0, 0, 74, 0, 1, 1, 1, 20, 20, 8, 8, 20, 0),
	(100, 'Ngựa ca nhan tím', 100, 0, 1, 158414, 1, 0, 0, 0, 0, 0, 0, 75, 0, 1, 1, 1, 20, 20, 8, 8, 20, 0),
	(101, 'Ngựa bang trắng', 101, 0, 1, 158414, 1, 0, 0, 0, 0, 0, 0, 76, 1, 1, 1, 1, 20, 20, 8, 8, 20, 0),
	(102, 'Ngựa bang xanh', 102, 0, 1, 158414, 1, 0, 0, 0, 0, 0, 0, 77, 1, 1, 1, 1, 20, 20, 8, 8, 20, 0),
	(103, 'Ngựa bang đỏ', 103, 0, 1, 158414, 1, 0, 0, 0, 0, 0, 0, 78, 1, 1, 1, 1, 20, 20, 8, 8, 20, 0),
	(104, 'Ngựa bang xanh lá', 104, 0, 1, 158414, 1, 0, 0, 0, 0, 0, 0, 79, 1, 1, 1, 1, 20, 20, 8, 8, 20, 0),
	(105, 'Ngựa bang vàng', 105, 0, 1, 158414, 1, 0, 0, 0, 0, 0, 0, 80, 1, 1, 1, 1, 20, 20, 8, 8, 20, 0),
	(106, 'Ngựa bang tím', 106, 0, 1, 158414, 1, 0, 0, 0, 0, 0, 0, 81, 1, 1, 1, 1, 20, 20, 8, 8, 20, 0),
	(107, 'Ngựa quốc gia trắng', 107, 0, 1, 158414, 1, 0, 0, 0, 0, 0, 0, 82, 2, 1, 1, 1, 20, 20, 8, 8, 20, 0),
	(108, 'Ngựa quốc gia xanh', 108, 0, 1, 158414, 1, 0, 0, 0, 0, 0, 0, 83, 2, 1, 1, 1, 20, 20, 8, 8, 20, 0),
	(109, 'Ngựa quốc gia đỏ', 109, 0, 1, 158414, 1, 0, 0, 0, 0, 0, 0, 84, 2, 1, 1, 1, 20, 20, 8, 8, 20, 0),
	(110, 'Ngựa quốc gia xanh lá', 110, 0, 1, 158414, 1, 0, 0, 0, 0, 0, 0, 85, 2, 1, 1, 1, 20, 20, 8, 8, 20, 0),
	(111, 'Ngựa quốc gia vàng', 111, 0, 1, 158414, 1, 0, 0, 0, 0, 0, 0, 86, 2, 1, 1, 1, 20, 20, 8, 8, 20, 0),
	(112, 'Ngựa quốc gia tím', 112, 0, 1, 158414, 1, 0, 0, 0, 0, 0, 0, 87, 2, 1, 1, 1, 20, 20, 8, 8, 20, 0),
	(113, 'Boss thỏ điên', 113, 0, 1, 200000000, 27000, 25000, 690, 20, 1000000, 500000, 1, 88, 3, 2, 1, 1, 20, 20, 8, 8, 20, 0),
	(114, 'Khô lâu', 114, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 89, 0, 0, 1, 1, 20, 20, 8, 8, 20, 0),
	(115, 'Boss Gấu xám', 115, 0, 1, 400000000, 27000, 25000, 690, 20, 1000000, 500000, 1, 90, 4, 2, 1, 1, 20, 20, 8, 8, 20, 0),
	(116, 'Dracula', 116, 0, 1, 500000000, 35000, 50000, 690, 20, 20000000, 10000000, 1, 91, 5, 2, 1, 1, 20, 20, 8, 8, 20, 0),
	(117, 'Bí ngô', 117, 0, 1, 500000000, 35000, 50000, 690, 20, 20000000, 10000000, 1, 92, 6, 2, 1, 1, 20, 20, 8, 8, 20, 0),
	(118, 'Nhện ma', 118, 0, 80, 900530, 25000, 25000, 690, 20, 32000, 15000, 1, 93, 0, 1, 1, 1, 20, 20, 8, 8, 20, 0),
	(119, 'cong thanh', 119, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 94, 0, 1, 1, 1, 20, 20, 8, 8, 20, 0),
	(120, 'Trụ rồng', 120, 0, 1, 1, 35000, 50000, 690, 20, 1000000, 1, 1, 96, 7, 2, 1, 1, 20, 20, 8, 8, 20, 1),
	(121, 'Rương ma quái', 121, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 97, 8, 2, 0, 0, 20, 20, 8, 8, 30, 1),
	(122, 'Ngọc 1 sao', 122, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 98, 9, 2, 1, 1, 20, 20, 8, 8, 20, 1),
	(123, 'Ngọc 2 sao', 123, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 99, 9, 2, 1, 1, 20, 20, 8, 8, 20, 1),
	(124, 'Ngọc 3 sao', 124, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 100, 9, 2, 1, 1, 20, 20, 8, 8, 20, 1),
	(125, 'Ngọc 4 sao', 125, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 101, 9, 2, 1, 1, 20, 20, 8, 8, 20, 1),
	(126, 'Ngọc 5 sao', 126, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 102, 9, 2, 1, 1, 20, 20, 8, 8, 20, 1),
	(127, 'Ngọc 6 sao', 127, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 103, 9, 2, 1, 1, 20, 20, 8, 8, 20, 1),
	(128, 'Ngọc 7 sao', 128, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 104, 9, 2, 1, 1, 20, 20, 8, 8, 20, 1),
	(129, 'Boss thuỷ tinh', 129, 0, 1, 1, 0, 0, 0, 20, 0, 0, 1, 105, 11, 0, 1, 1, 20, 20, 8, 8, 20, 1),
	(130, 'Boss sơn tinh', 130, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1, 106, 10, 0, 1, 1, 20, 20, 8, 8, 20, 1),
	(131, 'Thùng gỗ', 131, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 107, 12, 0, 0, 0, 20, 20, 0, 0, 0, 1),
	(59, 'Skeleton lam', 59, 0, 47, 95378, 2310, 630, 690, 20, 5348, 2000, 1, 27, 2, 1, 1, 1, 20, 20, 8, 8, 20, 0),
	(52, 'Huyết ma', 52, 0, 46, 90030, 2303, 620, 690, 20, 5160, 1900, 1, 25, 1, 1, 1, 1, 20, 20, 8, 8, 20, 0),
	(135, 'Gấu xám', 115, 0, 90, 2000530, 75000, 75000, 1290, 20, 75000, 35000, 1, 93, 0, 1, 1, 1, 20, 20, 8, 8, 20, 0),
	(47, 'Rắn mang bành', 47, 0, 48, 100918, 2318, 640, 690, 20, 5540, 2100, 1, 23, 2, 1, 1, 1, 20, 20, 8, 8, 20, 0),
	(136, 'Cọp tím', 136, 0, 82, 1000000, 26000, 25000, 690, 20, 53000, 15000, 1, 28, 1, 0, 1, 1, 20, 20, 8, 8, 20, 0),
	(137, 'Cọp xanh', 137, 0, 84, 5000000, 27000, 26000, 690, 20, 54000, 15000, 1, 28, 2, 1, 1, 1, 20, 20, 8, 8, 20, 0),
	(138, 'Cọp đỏ', 138, 0, 86, 10000000, 28000, 27000, 690, 20, 55000, 15000, 1, 28, 3, 1, 1, 1, 20, 20, 8, 8, 20, 0),
	(132, 'Skeleton lam', 59, 0, 47, 95378, 2310, 630, 690, 20, 5348, 2000, 1, 27, 2, 1, 1, 1, 20, 20, 8, 8, 20, 0);
/*!40000 ALTER TABLE `data_monster` ENABLE KEYS */;

-- Dumping structure for table kpah.data_potion
CREATE TABLE IF NOT EXISTS `data_potion` (
  `id` smallint(6) NOT NULL,
  `idImage` smallint(6) DEFAULT NULL,
  `delay` smallint(6) DEFAULT NULL,
  `isTrade` tinyint(1) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `name2` varchar(255) DEFAULT NULL,
  `price` int(11) DEFAULT NULL,
  `recovered` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Dumping data for table kpah.data_potion: ~162 rows (approximately)
DELETE FROM `data_potion`;
INSERT INTO `data_potion` (`id`, `idImage`, `delay`, `isTrade`, `name`, `name2`, `price`, `recovered`) VALUES
	(0, 0, 0, 0, 'Xu', 'xu', 0, 0),
	(1, 1, 350, 1, 'HP nhỏ', 'hpnho', 100, 80),
	(2, 2, 350, 1, 'HP vừa', 'hpvua', 500, 300),
	(3, 3, 350, 1, 'HP to', 'hpto', 800, 1000),
	(4, 4, 350, 1, 'MP nhỏ', 'mpnho', 50, 160),
	(5, 5, 350, 1, 'MP vừa', 'mpvua', 150, 600),
	(6, 6, 350, 1, 'MP to', 'mpto', 500, 2000),
	(7, 22, 350, 0, 'Bình tăng tốc thường', 'binhtangtocthuong', 500, 1),
	(8, 88, 350, 0, 'Bình tăng tốc vàng.\nGiảm 20% thời gian chín của trái vàng', 'binhtangtocvanggiamthoigianchincuatraivang', 500, 2),
	(9, 26, 350, 0, 'Nhân sâm', 'nhansam', 500, 1),
	(10, 62, 350, 0, 'Tiên dược thường: cộng 100k exp', 'tienduocthuongcongkexp', 500, 2),
	(11, 40, 0, 0, 'Tiên dược cao cấp: cộng 500k exp', 'tienduoccaocapcongkexp', 500, 1),
	(12, 36, 0, 0, 'Tiên dược đặc biệt: cộng 1t exp', 'tienduocdacbietcongtexp', 500, 0),
	(13, 23, 0, 0, 'Bình tăng tốc bạc.\nGiảm 20% thời gian chín của trái bạc', 'binhtangtocbacgiamthoigianchincuatraibac', 500, 0),
	(14, 15, 0, 0, 'Khăn đỏ', 'khando', 500, 0),
	(15, 16, 0, 0, 'Khăn xanh', 'khanxanh', 500, 0),
	(16, 17, 0, 0, 'Khăn vàng', 'khanvang', 500, 0),
	(17, 18, 0, 0, 'Khăn xanh lá', 'khanxanhla', 500, 0),
	(18, 19, 0, 0, 'Khăn tím', 'khantim', 500, 0),
	(19, 13, 0, 0, 'Thổ địa phù', 'thodiaphu', 1000, -1),
	(20, 13, 0, 0, 'Vé tàu', 'vetau', 15000, -1),
	(21, 9, 350, 1, 'HP đ.biệt vừa', 'hpdbietvua', 0, 0),
	(22, 10, 350, 1, 'HP đ.biệt to', 'hpdbietto', 0, 0),
	(23, 7, 350, 1, 'MP đ.biệt vừa', 'mpdbietvua', 0, 0),
	(24, 8, 350, 1, 'MP đ.biệt to', 'mpdbietto', 0, 0),
	(25, 20, 350, 0, 'Thuốc khôi phục tiềm năng', 'thuockhoiphuctiemnang', 0, 0),
	(26, 21, 350, 0, 'Thuốc khôi phục', 'thuockhoiphuc', 0, 0),
	(27, 32, 0, 0, 'Thần hành phù', 'thanhanhphu', 0, 0),
	(28, 32, 0, 0, 'Thẻ cấm chat', 'thecamchat', 0, 0),
	(29, 32, 0, 0, 'Phiếu công ích', 'phieucongich', 0, 0),
	(30, 27, 0, 1, 'Thiên lý mã', 'thienlyma', 0, 0),
	(31, 33, 0, 0, 'Kim bài', 'kimbai', 0, 0),
	(32, 13, 0, 0, 'Thẻ đấu trường', 'thedautruong', 0, 0),
	(33, 39, 0, 0, 'Thẻ chiếm thành', 'thechiemthanh', 0, 0),
	(34, 37, 0, 0, 'Xích thố', 'xichtho', 0, 0),
	(35, 11, 0, 0, 'Vé giờ vàng 100, x2 kinh nghiệm trong một giờ chơi. Tính theo thời gian chơi', 'vegiovangxkinhnghiemtrongmotgiochoitinhtheothoigianchoi', 0, 0),
	(36, 35, 0, 0, 'Tuần lộc', 'tuanloc', 0, 0),
	(37, 113, 0, 1, 'Đầu đinh', 'daudinh', 0, 0),
	(38, 114, 0, 1, 'Tóc búi', 'tocbui', 0, 0),
	(39, 115, 0, 1, 'Tóc cột cao', 'toccotcao', 0, 0),
	(40, 116, 0, 1, 'Tóc chéo', 'toccheo', 0, 0),
	(41, 117, 0, 1, 'Tóc xù', 'tocxu', 0, 0),
	(42, 118, 0, 1, 'Tóc ngang vai', 'tocngangvai', 0, 0),
	(43, 119, 0, 1, 'Tóc đinh so le', 'tocdinhsole', 0, 0),
	(44, 120, 0, 1, 'Tóc so le', 'tocsole', 0, 0),
	(45, 121, 0, 1, 'Tóc thư sinh', 'tocthusinh', 0, 0),
	(46, 122, 0, 1, 'Tóc cài hoa', 'toccaihoa', 0, 0),
	(47, 123, 0, 1, 'Tóc bờm ngựa', 'tocbomngua', 0, 0),
	(48, 124, 0, 1, 'Tóc tiên đồng', 'toctiendong', 0, 0),
	(49, 125, 0, 1, 'Tóc dựng đứng', 'tocdungdung', 0, 0),
	(50, 43, 0, 0, 'Hoa mai', 'hoamai', 0, 0),
	(51, 43, 0, 0, 'Hoa mai', 'hoamai', 0, 0),
	(52, 43, 0, 0, 'Hoa mai', 'hoamai', 0, 0),
	(53, 43, 0, 0, 'Hoa mai', 'hoamai', 0, 0),
	(54, 44, 0, 0, 'Hoa đào', 'hoadao', 0, 0),
	(55, 44, 0, 0, 'Hoa đào', 'hoadao', 0, 0),
	(56, 44, 0, 0, 'Hoa đào', 'hoadao', 0, 0),
	(57, 44, 0, 0, 'Hoa đào', 'hoadao', 0, 0),
	(58, 45, 0, 0, 'Hoa hồng', 'hoahong', 0, 0),
	(59, 45, 0, 0, 'Hoa hồng', 'hoahong', 0, 0),
	(60, 45, 0, 0, 'Hoa hồng', 'hoahong', 0, 0),
	(61, 45, 0, 0, 'Hoa hồng', 'hoahong', 0, 0),
	(62, 40, 0, 0, 'Bí ma', 'bima', 0, 0),
	(63, 42, 0, 0, 'Hộp may mắn', 'hopmayman', 0, 0),
	(64, 47, 0, 1, 'Hắc Ngưu', 'hacnguu', 0, 0),
	(65, 46, 0, 1, 'Mãnh hổ', 'manhho', 0, 0),
	(66, 48, 0, 1, 'Sói xám', 'soixam', 0, 0),
	(67, 49, 0, 1, 'Tiên Hạc', 'tienhac', 0, 0),
	(68, 38, 0, 1, 'Bạch mã', 'bachma', 0, 0),
	(69, 32, 0, 0, 'Vé quay số', 'vequayso', 0, 0),
	(70, 24, 0, 1, 'Trứng phượng hoàng băng', 'trungphuonghoangbang', 0, 0),
	(71, 227, 0, 1, 'Trứng Hoả kỳ lân', 'trunghoakylan', 0, 0),
	(72, 146, 0, 1, 'Trứng đương khang', 'trungduongkhang', 0, 0),
	(73, 141, 0, 0, 'Túi nhặt ngọc', 'tuinhatngoc', 0, 0),
	(74, 145, 0, 1, 'Trứng bạch cốt', 'trungbachcot', 0, 0),
	(75, 11, 1000, 0, 'Vé giờ vàng 100, x2 kinh nghiệm trong 3 giờ chơi. Tính theo thời gian chơi', 'vegiovangxkinhnghiemtronggiochoitinhtheothoigianchoi', 0, 0),
	(76, 32, 0, 0, 'Vé quay số đặc biệt', 'vequaysodacbiet', 0, 0),
	(77, 213, 0, 0, 'Mảnh bản đồ 1', 'manhbando', 0, 0),
	(78, 32, 0, 0, 'Thẻ liên hoa', 'thelienhoa', 0, 0),
	(79, 42, 0, 0, 'Hộp thần kỳ', 'hopthanky', 0, 0),
	(80, 60, 0, 0, 'Bình tăng lực 5%/n. Tăng 5% các chỉ số trong 3 ngày', 'binhtanglucntangcacchisotrongngay', 0, 0),
	(81, 11, 0, 0, 'Vé giờ vàng tăng 150% kinh nghiệm.', 'vegiovangtangkinhnghiem', 0, 0),
	(82, 107, 0, 0, 'Thẻ vận tiêu. Thêm một lượt vận tiêu khi sử dụng', 'thevantieuthemmotluotvantieukhisudung', 0, 0),
	(83, 245, 0, 0, 'Tụ hồn đan. Tăng 1% kinh nghiệm cho thú nuôi khi cho ăn.', 'tuhondantangkinhnghiemchothunuoikhichoan', 0, 0),
	(84, 31, 0, 0, 'Thuốc cường hoá', 'thuoccuonghoa', 0, 0),
	(85, 32, 0, 0, 'Thẻ triệu hồi', 'thetrieuhoi', 0, 0),
	(86, 65, 0, 1, 'Phượng hoàng', 'phuonghoang', 0, 0),
	(87, 32, 0, 0, 'Thẻ bài phong chức', 'thebaiphongchuc', 0, 0),
	(88, 73, 0, 0, 'Nhẫn Bách niên giai lão 2%pt', 'nhanbachniengiailaopt', 0, 0),
	(89, 74, 0, 0, 'Nhẫn Vĩnh kết đồng tâm 4%pt', 'nhanvinhketdongtampt', 0, 0),
	(90, 75, 0, 0, 'Nhẫn Loan phụng hòa minh 7%pt', 'nhanloanphunghoaminhpt', 0, 0),
	(91, 32, 0, 0, 'Vé vào mỏ', 'vevaomo', 0, 0),
	(92, 76, 0, 0, 'Thiệp cưới', 'thiepcuoi', 0, 0),
	(93, 10, 350, 1, 'HP7k', 'hpk', 0, 0),
	(94, 10, 350, 1, 'HP15k', 'hpk', 0, 0),
	(95, 8, 350, 1, 'MP7k', 'mpk', 0, 0),
	(96, 8, 350, 1, 'MP15k', 'mpk', 0, 0),
	(97, 105, 0, 0, 'Lệnh bài đổi tiêu', 'lenhbaidoitieu', 0, 0),
	(98, 93, 0, 0, 'Phù phu thê', 'phuphuthe', 0, 0),
	(99, 64, 0, 0, 'Pháo hoa', 'phaohoa', 0, 0),
	(100, 77, 0, 0, 'loa', 'loa', 0, 0),
	(101, 67, 0, 0, 'Rương vàng', 'ruongvang', 0, 0),
	(102, 68, 0, 0, 'Rương bạc', 'ruongbac', 0, 0),
	(103, 43, 0, 0, 'Lá vàng', 'lavang', 0, 0),
	(104, 246, 0, 0, 'Dây thừng. Dùng để bắt thú nuôi', 'daythungdungdebatthunuoi', 0, 0),
	(105, 214, 0, 0, 'Mảnh bản đồ 2', 'manhbando', 0, 0),
	(106, 0, 0, 0, '', '', 0, 0),
	(107, 0, 0, 0, '', '', 0, 0),
	(108, 0, 0, 0, '', '', 0, 0),
	(109, 83, 0, 0, '', '', 0, 0),
	(110, 0, 0, 0, '', '', 0, 0),
	(111, 0, 0, 0, '', '', 0, 0),
	(112, 85, 0, 0, 'Thức ăn tươi', 'thucantuoi', 0, 0),
	(113, 86, 0, 0, 'Thức ăn khô', 'thucankho', 0, 0),
	(114, 87, 0, 0, 'Thức ăn tổng hợp', 'thucantonghop', 0, 0),
	(115, 166, 0, 1, 'Trứng Lân sư tử', 'trunglansutu', 0, 0),
	(116, 106, 0, 0, 'Lệnh bài đổi tiêu cao cấp', 'lenhbaidoitieucaocap', 0, 0),
	(117, 215, 0, 0, 'Mảnh bản đồ 3', 'manhbando', 0, 0),
	(118, 32, 0, 0, 'Vé phượng hoàng.\nDùng quay số phượng hoàng', 'vephuonghoangdungquaysophuonghoang', 0, 0),
	(119, 152, 0, 0, 'Tiên đan\nHồi sinh bản thân.', 'tiendanhoisinhbanthan', 0, 0),
	(120, 91, 0, 0, 'Thịt', 'thit', 0, 0),
	(121, 89, 0, 0, 'Trứng', 'trung', 0, 0),
	(122, 199, 0, 0, 'Đậu xanh', 'dauxanh', 0, 0),
	(123, 31, 0, 0, 'Thuốc cường hóa siêu cấp', 'thuoccuonghoasieucap', 0, 0),
	(124, 66, 0, 0, 'Bánh dẻo', 'banhdeo', 0, 0),
	(125, 92, 0, 0, 'Bánh nướng', 'banhnuong', 0, 0),
	(126, 162, 0, 0, 'Hp chiến trường.\nHồi 10.000 hp', 'hpchientruonghoihp', 0, 0),
	(127, 174, 0, 0, 'Đoạn cốt.\nHồi 10.000 mp', 'doancothoimp', 0, 0),
	(128, 163, 0, 0, 'Thuốc cường hoá chiến trường', 'thuoccuonghoachientruong', 0, 0),
	(129, 156, 0, 0, 'Hỗn độn.\nTăng 20% giáp trong 90s', 'hondontanggiaptrongs', 0, 0),
	(130, 158, 0, 0, 'Hỗn Nguyên.\nTăng 20% sát thương.', 'honnguyentangsatthuong', 0, 0),
	(131, 159, 0, 0, 'Hoá công.\nTăng 50% sát thương boss.', 'hoacongtangsatthuongboss', 0, 0),
	(132, 157, 0, 0, 'Hoả dược. Gây sát thương theo x10% nội lực bản thân lên toàn bộ mục tiêu trong bán kính 5m.', 'hoaduocgaysatthuongtheoxnoilucbanthanlentoanbomuctieutrongbankinhm', 0, 0),
	(133, 171, 0, 0, 'Hộp bánh thập cẩm', 'hopbanhthapcam', 0, 0),
	(134, 212, 0, 0, 'Hộp bánh đặc biệt', 'hopbanhdacbiet', 0, 0),
	(135, 108, 0, 0, 'Hạt mầm', 'hatmam', 0, 0),
	(136, 28, 0, 0, 'Nến', 'nen', 0, 0),
	(137, 208, 0, 0, 'Kẹo', 'keo', 0, 0),
	(138, 207, 0, 0, 'Bí ngô', 'bingo', 0, 0),
	(139, 35, 0, 0, 'Hộp quà', 'hopqua', 0, 0),
	(140, 222, 0, 0, 'Hộp quà halloween', 'hopquahalloween', 0, 0),
	(141, 223, 0, 0, 'Hộp quà tặng halloween', 'hopquatanghalloween', 0, 0),
	(142, 30, 0, 0, 'Bột mỳ', 'botmy', 0, 0),
	(143, 0, 0, 0, 'Hộp quà bí mật', 'hopquabimat', 0, 0),
	(144, 230, 0, 0, 'Sao vàng', 'saovang', 0, 0),
	(145, 14, 0, 0, 'Dưa Hấu', 'duahau', 0, 0),
	(146, 99, 0, 0, 'Kem', 'kem', 0, 0),
	(147, 220, 0, 0, 'Túi quà may mắn', 'tuiquamayman', 0, 0),
	(148, 177, 0, 0, 'Gạo nếp', 'gaonep', 0, 0),
	(149, 233, 0, 0, 'Thịt mỡ', 'thitmo', 0, 0),
	(150, 172, 0, 0, 'Lá dong', 'ladong', 0, 0),
	(151, 180, 0, 0, 'Bánh chưng', 'banhchung', 0, 0),
	(152, 175, 0, 0, 'Bánh tét', 'banhtet', 0, 0),
	(153, 170, 0, 0, 'Giấy', 'giay', 0, 0),
	(154, 174, 0, 0, 'Thuốc pháo', 'thuocphao', 0, 0),
	(155, 64, 0, 0, 'Pháo hoa tết', 'phaohoatet', 0, 0),
	(156, 200, 0, 0, 'Lửa thường', 'luathuong', 0, 0),
	(157, 201, 0, 0, 'Lửa vừa', 'luavua', 0, 0),
	(158, 202, 0, 0, 'Lửa to', 'luato', 0, 0),
	(159, 76, 0, 0, 'Bao lì xì', 'baolixi', 0, 0),
	(160, 247, 0, 0, 'Quả bóng bạc', 'quabongbac', 0, 0),
	(161, 248, 0, 0, 'Quả bóng vàng', 'quabongvang', 0, 0);

-- Dumping structure for table kpah.data_quest
CREATE TABLE IF NOT EXISTS `data_quest` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `npcrcv` int(11) NOT NULL,
  `npcres` int(11) NOT NULL,
  `lv` int(10) unsigned NOT NULL DEFAULT '1',
  `mainsub` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '0-main-1sub',
  `content` varchar(1000) NOT NULL COMMENT 'noi dung oi chuyen nhan nhiem vu',
  `scontent` varchar(500) NOT NULL DEFAULT '32000' COMMENT 'mo ta ngan gon nhiem vu',
  `rescontent` varchar(500) NOT NULL DEFAULT '32000',
  `idItemget` varchar(45) NOT NULL DEFAULT '32000',
  `idMonskill` varchar(45) NOT NULL DEFAULT '32000',
  `type` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `nitemget` varchar(45) NOT NULL DEFAULT '32000' COMMENT 'so luong item can lay tuong ung',
  `nmonskill` varchar(45) NOT NULL DEFAULT '32000' COMMENT 'so luong quai can giet tuong ung',
  `gold` bigint(20) unsigned NOT NULL DEFAULT '0',
  `exp` bigint(20) unsigned NOT NULL DEFAULT '0',
  `potiongift` varchar(45) NOT NULL DEFAULT '32000',
  `npotion` varchar(45) NOT NULL DEFAULT '32000',
  `itemgift` varchar(45) NOT NULL DEFAULT '32000',
  `deltaLv` int(10) unsigned NOT NULL DEFAULT '0',
  `infogift` varchar(200) DEFAULT NULL,
  `infofinish` varchar(200) NOT NULL DEFAULT 'Nhiệm vụ hoàn thành',
  `pactive` int(10) unsigned NOT NULL DEFAULT '0',
  `luong` int(11) DEFAULT '0',
  `luongLock` int(11) DEFAULT '100',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=50 DEFAULT CHARSET=utf8;

-- Dumping data for table kpah.data_quest: 49 rows
DELETE FROM `data_quest`;
/*!40000 ALTER TABLE `data_quest` DISABLE KEYS */;
INSERT INTO `data_quest` (`id`, `name`, `npcrcv`, `npcres`, `lv`, `mainsub`, `content`, `scontent`, `rescontent`, `idItemget`, `idMonskill`, `type`, `nitemget`, `nmonskill`, `gold`, `exp`, `potiongift`, `npotion`, `itemgift`, `deltaLv`, `infogift`, `infofinish`, `pactive`, `luong`, `luongLock`) VALUES
	(1, 'Nhiệm vụ tân thủ', 5, 32000, 1, 0, '1Chào con, ta sẽ cho con tiền trải nghiệm game>0Chào Trưởng làng>1con là cháu ông Ba Lực trên vùng Lâm Viên phải không>0Dạ phải ạ>1như đã hứa với ông ấy>1ta sẽ giới thiệu con gia nhập làng nghĩa sỹ Sơn Nam", "1Ta luôn hoan nghênh các thanh niên có nhiệt huyết với giang sơn đất nước>1bênh vực kẻ yếu, sống vì lẽ phải>1Con hãy đi chào hỏi các bà con cô bác trong làng>1và làm một số việc giúp họ coi như là chào hỏi>1và biết được nhiều hơn về mọi thứ nhé.>1Đầu tiên con hãy đi gặp anh Hắc ngưu thợ rèn.>1Anh ta là chuyên gia vũ khí của làng này đó.>0Dạ, chào trưởng làng con đi>1Uhm', '32000', '32000', '32000', '32000', 3, '32000', '32000', 1000, 100, '32000', '32000', '32000', 0, 'Nhiệm vụ:|chào tất cả NPC trong làng|Địa điểm:|trong làng|NPC nv:|tất cả NPC|Phần thưởng:|999999999 xu và 100 exp', '32000', 0, 0, 0),
	(2, 'Nhiệm vụ tân thủ', 2, 32000, 1, 0, '1Khà khà !!>1Ta đã được nghe qua câu chuyện của ngươi từ trưởng làng>1ngươi còn trẻ mà đã biết ý thức sống vì lẽ phải>1Đó là điều rất đáng quý !>1Ta là Hắc Ngưu>1niềm đam mê của ta là chế tạo và buôn bán vũ khí>1Nếu có thắc mắc điều gì liên quan đến vũ khí ngươi cứ đến tìm ta>1Giờ thì ngươi hãy đến ra mắt lão Thiết Bì đi nào.', '32000', '32000', '32000', '32000', 3, '32000', '32000', 100, 100, '32000', '32000', '32000', 0, 'Nhiệm vụ:|chào tất cả NPC trong làng|Địa điểm:|trong làng|NPC nv:|tất cả NPC|Phần thưởng:|100 xu và 100 exp', '32000', 0, 0, 0),
	(3, 'Nhiệm vụ tân thủ', 3, 32000, 1, 0, '1Anh hùng xuất thiếu niên, anh hùng xuất thiếu niên !>1Ta là Thiết Bì, người chế tạo giáp duy nhất của làng này>1mọi loại giáp trụ ta đều am hiểu>1Cần gì về giáp thì cứ đến tìm ta nhé !>1Cũng xế trưa rồi.>1giờ này chắc bà Tám tạp hóa cũng đang chờ ngươi đấy>1hãy qua mà chào hỏi bà ấy đi', '32000', '32000', '32000', '32000', 3, '32000', '32000', 100, 100, '32000', '32000', '32000', 0, 'Nhiệm vụ:|chào tất cả NPC trong làng|Địa điểm:|trong làng|NPC nv:|tất cả NPC|Phần thưởng:|100 xu và 100 exp', '32000', 0, 0, 0),
	(4, 'Nhiệm vụ tân thủ', 1, 32000, 1, 0, '1Phù ! Ta đã đợi con từ sáng sớm đến giờ>1Nghe chuyện về hoàn cảnh của con mà ta thương quá>1Nhà ta chuyên kinh doanh các loại tạp phẩm có thể phục vụ cho cả làng.>1. Anh Bảy thủ kho đang háo hức được gặp mặt con>1hãy đến nhà anh ta sau khi ăn xong nhé.', '32000', '32000', '32000', '32000', 3, '32000', '32000', 100, 100, '32000', '32000', '32000', 0, 'Nhiệm vụ:|chào tất cả NPC trong làng|Địa điểm:|trong làng|NPC nv:|tất cả NPC|Phần thưởng:|100 xu và 100 exp', '32000', 0, 0, 0),
	(5, 'Nhiệm vụ tân thủ', 9, 32000, 1, 0, '1Ha ha ! Tiểu đệ đây rồi>1Lạ thay, tuy chưa gặp mặt lần nào>1nhưng huynh cảm thấy như đã quen tiểu đệ từ lâu lắm rồi vậy>1Thật là sảng khoái>1Tính huynh vốn ít nói nên đệ đừng ngại>1Sau này trên đường bôn tẩu giang hồ>1nếu sưu tầm được thứ gì trân quí mà không tiện mang bên mình>1cứ đưa ta bảo quản>1Bảo đảm không ai có thể lấy được của đệ.>1Giờ thì đệ hãy đến ra mắt Phú ông>1cứ tìm nhà nào to và đẹp nhất làng chính là nhà ông ấy.', '32000', '32000', '32000', '32000', 3, '32000', '32000', 100, 100, '32000', '32000', '32000', 0, 'Nhiệm vụ:|chào tất cả NPC trong làng|Địa điểm:|trong làng|NPC nv:|tất cả NPC|Phần thưởng:|100 xu và 100 exp', '32000', 0, 0, 0),
	(6, 'Nhiệm vụ tân thủ', 6, 32000, 1, 0, '1Rất vui khi được gặp ngươi>1Ngươi tuy mồ côi nhưng lại ngoan hiền và có chí lớn>1thật là tốt khi ngươi đứng vào hàng ngũ nghĩa sỹ Sơn Nam>1Nếu cần các loại vật liệu luyện thần binh thì cứ đến tìm ta>1Ta đã chuyển lời giới thiệu ngươi với Nhật thương nhân bạn ta>1hãy đến ra mắt ông ta nhé.', '32000', '32000', '32000', '32000', 3, '32000', '32000', 100, 100, '32000', '32000', '32000', 0, 'Nhiệm vụ:|chào tất cả NPC trong làng|Địa điểm:|trong làng|NPC nv:|tất cả NPC|Phần thưởng:|100 xu và 100 exp', '32000', 0, 0, 0),
	(7, 'Nhiệm vụ tân thủ', 22, 32000, 1, 0, '1Chào chàng trai trẻ !>1Ta thường đi giao thương khắp nơi>1Ta và ngươi thật là có duyên tương ngộ>1Ha ha ha >1Khi bôn ba giang hồ>1cần phải có một chút bản lĩnh.>1Hãy đến gặp Lâm tướng quân để học hỏi nha.>1Lâm tướng quân đang chờ ngươi tại doanh trại của ông ấy>1Hãy nhanh chân đến gặp ông ấy đi nào.', '32000', '32000', '32000', '32000', 3, '32000', '32000', 100, 100, '32000', '32000', '32000', 0, 'Nhiệm vụ:|chào tất cả NPC trong làng|Địa điểm:|trong làng|NPC nv:|tất cả NPC|Phần thưởng:|100 xu và 100 exp', '32000', 0, 0, 0),
	(8, 'Nhiệm vụ tân thủ', 21, 32000, 1, 0, '1Hây dà ! Cuối cùng cũng đã đến !>1Có thực sự ngươi muốn gia nhập làng nghĩa sỹ Sơn Nam không?>1Nơi đây không có chổ dung thân cho kẻ lười biếng và vô dụng nhé>1Hãy cố gắng luyện tập nhiều>1ta thấy tiền đồ ngươi có vẻ sáng lạn đấy>1Ta luôn có những nhiệm vụ đang cần người giải quyết>1khi nào thực sự tự tin để làm hãy đến tìm ta.>1Nhân đây ta sẽ dạy ngươi 1 chiêu thức>1Nó sẽ giúp ngươi khi bắt đầu cuộc hành tẩu.>1Bây giờ ngươi hãy quay lại gặp trưởng làng>1Ông ấy có 1 vài điều muốn nói với ngươi trước khi ngươi lên đường.', '32000', '32000', '32000', '32000', 3, '32000', '32000', 100, 100, '32000', '32000', '32000', 0, 'Nhiệm vụ:|chào tất cả NPC trong làng|Địa điểm:|trong làng|NPC nv:|tất cả NPC|Phần thưởng:|100 xu và 100 exp', '32000', 0, 0, 0),
	(9, 'Nhiệm vụ tân thủ', 5, 32000, 1, 0, '1Chúc mừng con>1Từ giờ con đã là thành viên của làng nghĩa sỹ Sơn Nam>1Hãy cố gắng đừng phụ lòng mọi người kỳ vọng vào con>0Dạ vâng. Con sẽ cố gắng.', '32000', '32000', '32000', '32000', 3, '32000', '32000', 100, 100, '32000', '32000', '32000', 0, 'Nhiệm vụ:|chào tất cả NPC trong làng|Địa điểm:|trong làng|NPC nv:|tất cả NPC|Phần thưởng:|100 xu và 100 exp', '32000', 0, 0, 0),
	(10, 'Ra mắt thành hoàng', 1, 1, 2, 0, '1Con biết không,Sơn Nam là một ngôi làng đã có lịch sử lâu đời.>1Nơi đây không chỉ là nơi ta sinh sống>1mà còn là nơi lưu giữ ý chí kiêu hùng và tôn chỉ của những người đầu tiên đi mở cõi và giữ nước>1Nơi đây cũng là quê hương thứ 2 của bao bậc anh hùng hào kiệt nhân sỹ tứ phương>1ai trong họ cũng tự hào là thành viên nơi đây và tự hào là con cháu của xứ sở Rồng Tiên>1Họ là tấm gương và là nguồn động viên cho các lớp người đi sau>1con hãy cố gắng để nối tiếp truyền thống làng ta nhé>1Để ta làm bữa cơm cho con cúng ra mắt thành hoàng làng>1con hãy kiếm 20 con nhím về đây cho ta nhé>Con có muốn giúp ta việc này không ?', 'Sắp qua giờ tốt rồi, nhanh con nhé.', '1Tốt lắm, ta cảm ơn con !', '32000', '1', 0, '32000', '20', 200, 200, '32000', '32000', '32000', 0, 'Nhiệm vụ:|bắt 20 con nhím|Địa điểm:|Tiên Du|NPC nv:|Bà Tám|Phần thưởng:|200 xu và 200 exp', 'Nhiệm vụ hoàn thành|Quay về gặp Bà Tám|Để nhận thưởng', 0, 0, 0),
	(11, 'Giết sâu bọ', 6, 6, 2, 0, '1Nhà ta có mấy thửa ruộng trước cổng làng đang bị dịch sâu hại lúa>1ngươi hãy giúp ta diệt sâu>1để chúng không phá lúa nhà ta nữa nhé>Con có muốn giúp ta làm việc này không ?', 'Ôi lúa của ta, ngươi hãy đi nhanh đi.', '1Chà,ngươi làm nhanh thế!>1Ta nghĩ cũng phải hơn nửa ngày mới xong chứ>1ngươi làm ta thật bất ngờ !', '32000', '2', 0, '32000', '40', 200, 300, '32000', '32000', '32000', 0, 'Nhiệm vụ:|diệt 40 con sâu|Địa điểm:|Tiên Du|NPC nv:|Phú Ồng|Phần thưởng:|200 xu và 300 exp', 'Nhiệm vụ hoàn thành|Quay về gặp Phú Ông|Để nhận thưởng', 0, 0, 0),
	(12, 'Bắt rắn ngâm rượu', 6, 6, 5, 0, '1À, ta muốn ngâm một hủ rượu rắn để tiếp đãi các anh hùng nghĩa sĩ khi có dịp>1Ta nghe nói vùng Tiên Du lân cận làng ta>1cách đây chừng 5 dặm đường có rất nhiều rắn lục>1ngươi hãy đến đó và bắt dùm ta 60 con rắn>Ngươi có muốn bắt rắn giúp ta không ?', 'Cẩn thận đừng để rắn cắn, chết chứ chả chơi !', '1Thật cảm ơn ngươi!>1Ngươi thật tốt bụng.', '1', '5', 2, '60', '32000', 300, 300, '32000', '32000', '32000', 0, 'Nhiệm vụ:|bắt 60 con rắn lục|Địa điểm:|Tiên Du|NPC nv:|Phú Ông|Phần thưởng:|300 xu và 300 exp', 'Nhiệm vụ hoàn thành|Quay về gặp Phú Ông|Để nhận thưởng', 0, 0, 0),
	(13, 'Bắt chuột phá lúa', 6, 6, 9, 0, '1Haiiiiii !>1Mùa màng năm nay của làng ta ở ruộng xa Phù Liệt thất bát quá>1Do bọn chuột từ đâu kéo về sinh sôi nảy nở quá nhiều>1ngươi hãy giúp dân làng diệt lũ chuột để chúng khỏi phá hại cây lúa>Ngươi có muốn giúp ta không ?', 'Hãy mau đi đi, kẻo chuột ăn hết lúa của ta.', '1Nhờ có ngươi mà bọn chuột đã bớt phá hại>1thu hoạch cũng được khá hơn>Hãy nhận chút quà mọn của ta nhé.', '32000', '9', 0, '32000', '50', 500, 50000, '32000', '32000', '32000', 0, 'Nhiệm vụ:|diệt 50 con chuột|Địa điểm:|Phù Liệt|NPC nv:|Phú Ông|Phần thưởng:|500 xu và 50000 exp', 'Nhiệm vụ hoàn thành|Quay về gặp Phú Ông|Để nhận thưởng', 0, 0, 0),
	(14, 'Đưa thư cho Hỏa Xích', 3, 23, 12, 0, '1Ta có một người bạn tâm giao cùng học nghệ năm xưa sống tại vùng Kỳ Bố>1lão tên là Hỏa Xích.>1Ta và lão tình như thủ túc>1lâu ngày không gặp sinh lòng nhớ thương bạn hiền>1ta muốn gửi thư hỏi thăm xem lão độ này sinh sống thế nào>Ngươi có thể giúp ta mang phong thư này trao tận tay lão không ?', 'Hãy mang lá thư của ta đến vùng Kỳ Bố giao cho Hỏa Xích', '1Oh,Thiết Bì huynh gửi thư cho ta àh?>1Thật quá bất ngờ !>1Ta cũng định ít bữa nữa thu xếp đến thăm huynh ấy>1chưa kịp đi thì huynh ấy đã cho người đưa thư đến hỏi thăm.>1ta đã xem xong thư để ta hồi âm cho huynh ấy vài chữ, ngươi đợi nhé !>1Hãy giúp ta chuyển lời cảm ơn đến Thiết Bì huynh nhé>1Phiền ngươi nhắn với huynh ấy>1vài bữa nữa ta sẽ đến thăm huynh ấy sau>1Thật cảm ơn ngươi rất nhiều.', '4', '32000', 1, '32000', '32000', 0, 0, '32000', '32000', '32000', 0, '32000', 'Nhiệm vụ hoàn thành|Quay về gặp Thiết Bì|Để nhận thưởng', 0, 0, 0),
	(15, 'Đưa thư cho Hỏa Xích', 3, 32000, 12, 0, '1Được tin lão Hỏa Xích khỏe mạnh ta thật an lòng>1Thật vất vả cho ngươi>1Đây ta tặng ngươi cái áo>1để cảm ơn ngươi đã giúp ta.', '', '', '32000', '32000', 3, '32000', '32000', 500, 60000, '32000', '32000', '81,88,95,102,109,10', 0, 'Nhiệm vụ:|đưa thư|Địa điểm:|Kỳ Bố|NPC nv:|Hỏa Xích|Phần thưởng:|500 xu và 60000 exp,|vk lv11 +10', '', 0, 0, 0),
	(16, 'Làm bánh bao', 1, 1, 14, 0, '1Cả làng rất thích ăn bánh bao do ta làm nên hôm nay ta muốn đãi họ một bữa>1Sáng này ta ra chợ mua hết thịt heo ngoài đó về để làm nhân bánh nhưng vẫn chưa đủ>1con hãy tìm về đây cho ta 60 con heo nhé>Con có sẵn sàng giúp ta làm bánh bao không ?', 'Mọi người đang đợi bánh bao ta làm đấy, đi sớm về sớm.', '1Con về hơi trễ so với dự kiến của ta>1nhưng không sao vẫn còn kịp>1ta có chút quà cho con đây.', '5', '15', 2, '50', '32000', 800, 150000, '32000', '32000', '32000', 0, 'Nhiệm vụ:|cắt 50 miếng thịt heo|Địa điểm:|Thạch Giang|NPC nv:|Bà Tám|Phần thưởng:|800 xu và 150000 exp', 'Nhiệm vụ hoàn thành|Quay về gặp Bà Tám|Để nhận thưởng', 0, 0, 0),
	(17, 'Điều chế dược phẩm', 1, 1, 15, 0, '1Ta đang cần nọc độc của bò cạp, rết để điều chế một số dược phẩm>1con hãy đến đông sơn tìm 30 nọc bò cạp mang về cho ta nhé>Con có muốn giúp ta không ?', 'Có dược liệu cho ta chưa ?', '1Cảm ơn con !>1Hãy nhận chút quà của ta.', '0', '16', 2, '30', '32000', 1000, 500000, '32000', '32000', '32000', 0, 'Nhiệm vụ:|lấy 30 đuôi bò cạp|30 con rết|Địa điểm:|Đông Sơn, Tử Quan|NPC nv:|Bà Tám|Phần thưởng:|1000 xu và 500000 exp', 'Nhiệm vụ hoàn thành|Quay về gặp Bà Tám|Để nhận thưởng', 0, 0, 0),
	(18, 'Sửa nhà cho trưởng làng', 5, 5, 17, 0, '1Nhà của ta đã lâu lắm không tu sửa gì nên nó đã quá cũ kĩ>1đã có nhiều chổ bị mối mọt ăn mục>1Con hãy giúp ta  sửa lại căn nhà cho kịp mùa mưa đang đến>1vùng Tử Quan nổi tiếng nhiều gỗ tốt>1con hãy đến đấy tìm gỗ giúp ta nhé>1Rừng thiêng nước độc>1trên núi có rất nhiều hoa ăn thịt và quái thú con phải hết sức cẩn thận>Con có muốn giúp ta việc này không ?', 'Con còn chậm chạp như thế thì nhà ta sẽ sập mất !', '1Nhà ta đã kiên cố và đẹp hơn rồi>1Thật vất vả cho con.', '2', '20', 2, '50', '32000', 2000, 900000, '21,23', '10,10', '32000', 0, 'Nhiệm vụ:|nhặt 50 khúc gỗ|từ Quỷ sinh hoa|Địa điểm:|Tử Quan|NPC nv:|Trưởng Làng|Phần thưởng:|2000 xu, 900000 exp,|10HP vừa, 10MP vừa', 'Nhiệm vụ hoàn thành|Quay về gặp Trưởng Làng|Để nhận thưởng', 0, 0, 0),
	(19, 'Rèn luyện bản thân (dễ)', 21, 21, 20, 1, '1Ta thấy ngươi tiền đồ sáng lạn<0Tướng quân quá khen>1Hãy cố rèn luyện bản thân sau này sẽ nên người tài mà giúp ích cho đời.>1Nếu muốn rèn luyện thì đây là cách>1Đánh đủ số 50 con quái (+-5Lv nhân vật) rồi về đây gặp ta.>Ngươi có muốn rèn luyện không ?', 'Đã đủ số quái ta yêu cầu chưa ?', '1Ngươi hoàn thành tốt đấy, cố gắng hơn nữa nhé.>0Dạ ', '32000', '1', 4, '32000', '50', 2000, 500000, '32000', '32000', '32000', 5, 'Nhiệm vụ:|Giết 50 quái +-5Lv|NPC nv:|Lâm Tướng Quân|Phần thưởng:|500000 exp', 'Nhiệm vụ hoàn thành|Quay về gặp Lâm Tướng Quân|Để nhận thưởng', 30, 0, 0),
	(20, 'Rèn luyện bản thân (vừa)', 21, 21, 20, 1, '1Ta thấy ngươi tiền đồ sáng lạn>0Tướng quân quá khen>1Hãy cố rèn luyện bản thân sau này sẽ nên người tài mà giúp ích cho đời.>1Nếu muốn rèn luyện thì đây là cách>1Đánh đủ số 100 con quái (+-5Lv nhân vật) rồi về đây gặp ta.>Ngươi có muốn rèn luyện không ?', 'Đã đủ số quái ta yêu cầu chưa ?', '1Ngươi hoàn thành tốt đấy, cố gắng hơn nữa nhé.>0Dạ ', '32000', '32000', 4, '32000', '100', 0, 1200000, '32000', '32000', '32000', 5, 'Nhiệm vụ:|Giết 100 quái +-5Lv|NPC nv:|Lâm Tướng Quân|Phần thưởng:|1200000 exp', 'Nhiệm vụ hoàn thành|Quay về gặp Lâm Tướng Quân|Để nhận thưởng', 60, 0, 0),
	(21, 'Rèn luyện bản thân (khó)', 21, 21, 20, 1, '1Ta thấy ngươi tiền đồ sáng lạn<0Tướng quân quá khen>1Hãy cố rèn luyện bản thân sau này sẽ nên người tài mà giúp ích cho đời.>1Nếu muốn rèn luyện thì đây là cách>1Đánh đủ số 150 con quái (+-5Lv nhân vật) rồi về đây gặp ta.>Ngươi có muốn rèn luyện không ?', 'Đã đủ số quái ta yêu cầu chưa ?', '1Ngươi hoàn thành tốt đấy, cố gắng hơn nữa nhé.>0Dạ ', '32000', '32000', 4, '32000', '150', 0, 2000000, '32000', '32000', '32000', 5, 'Nhiệm vụ:|Giết 150 quái +-5Lv|NPC nv:|Lâm Tướng Quân|Phần thưởng:|2000000 exp', 'Nhiệm vụ hoàn thành|Quay về gặp Lâm Tướng Quân|Để nhận thưởng', 100, 0, 0),
	(22, 'Cường thân kiện thể (dễ)', 21, 21, 30, 1, '1Giang hồ hiểm ác biết bao nhiêu nguy hiểm tiềm ẩn>1muốn bôn tẩu cần phải có một cơ thể khỏe mạnh>1Hãy tiếp tục rèn luyện bản thân đi.>0Dạ!<1Đi đánh 85 con quái (+-5 Lv nhân vật) rồi về đây ta gặp ta.>Ngươi có muốn thực hiện việc này không?', 'Cố gắng lên, ngươi vẫn chưa hoàn thành nhiệm vụ đâu !', '1Tốt rồi, nhận chút quà của ta xem như khích lệ nhé>0Vâng xin cảm ơn tướng quân', '32000', '32000', 4, '32000', '85', 0, 1200000, '32000', '32000', '32000', 5, 'Nhiệm vụ:|Giết 85 quái +-5Lv|NPC nv:|Lâm Tướng Quân|Phần thưởng:|1200000 exp', 'Nhiệm vụ hoàn thành|Quay về gặp Lâm Tướng Quân|Để nhận thưởng', 30, 0, 0),
	(23, 'Cường thân kiện thể (vừa)', 21, 21, 30, 1, '1Giang hồ hiểm ác biết bao nhiêu nguy hiểm tiềm ẩn>1muốn bôn tẩu cần phải có một cơ thể khỏe mạnh>1Hãy tiếp tục rèn luyện bản thân đi.>0Dạ!<1Đi đánh 170 con quái (+-5 Lv nhân vật) rồi về đây ta gặp ta.>Ngươi có muốn thực hiện việc này không?', 'Cố gắng lên, ngươi vẫn chưa hoàn thành nhiệm vụ đâu !', '1Tốt rồi, nhận chút quà của ta xem như khích lệ nhé>0Vâng xin cảm ơn tướng quân', '32000', '32000', 4, '32000', '170', 0, 2500000, '32000', '32000', '32000', 5, 'Nhiệm vụ:|Giết 170 quái +-5Lv|NPC nv:|Lâm Tướng Quân|Phần thưởng:|2500000 exp', 'Nhiệm vụ hoàn thành|Quay về gặp Lâm Tướng Quân|Để nhận thưởng', 60, 0, 0),
	(24, 'Cường thân kiện thể (khó)', 21, 21, 30, 1, '1Giang hồ hiểm ác biết bao nhiêu nguy hiểm tiềm ẩn>1muốn bôn tẩu cần phải có một cơ thể khỏe mạnh>1Hãy tiếp tục rèn luyện bản thân đi.>0Dạ!<1Đi đánh 250 con quái (+-5 Lv nhân vật) rồi về đây ta gặp ta.>Ngươi có muốn thực hiện việc này không?', 'Cố gắng lên, ngươi vẫn chưa hoàn thành nhiệm vụ đâu !', '1Tốt rồi, nhận chút quà của ta xem như khích lệ nhé>0Vâng xin cảm ơn tướng quân', '32000', '32000', 4, '32000', '250', 0, 4000000, '32000', '32000', '32000', 5, 'Nhiệm vụ:|Giết 250 quái +-5Lv|NPC nv:|Lâm Tướng Quân|Phần thưởng:|4000000 exp', 'Nhiệm vụ hoàn thành|Quay về gặp Lâm Tướng Quân|Để nhận thưởng', 100, 0, 0),
	(25, 'Trừ gian diệt ác (dễ)', 21, 21, 40, 1, '1Cướp bóc, quái vật đang nổi lên hoành hành khắp mọi nơi>1thiết nghĩ phải có người ra tay tiêu diệt.>0Đó là điều nên làm ạ>1Ta thấy ngươi có thể đảm đương việc này>1Tiêu diệt 117 con quái (+-5Lv nhân vật) quay về ta trọng thưởng>Ngươi có muốn làm việc này không ?', 'Hoàn thành sớm đi còn về nhận thưởng', '1Hãy nhận lấy chút phần thưởng nhé', '32000', '32000', 4, '32000', '117', 0, 3000000, '32000', '32000', '32000', 5, 'Nhiệm vụ:|Giết 117 quái +-5Lv|NPC nv:|Lâm Tướng Quân|Phần thưởng:|3000000 exp', 'Nhiệm vụ hoàn thành|Quay về gặp Lâm Tướng Quân|Để nhận thưởng', 30, 0, 0),
	(26, 'Trừ gian diệt ác (vừa)', 21, 21, 40, 1, '1Cướp bóc, quái vật đang nổi lên hoành hành khắp mọi nơi>1thiết nghĩ phải có người ra tay tiêu diệt.>0Đó là điều nên làm ạ>1Ta thấy ngươi có thể đảm đương việc này>1Tiêu diệt 234 con quái (+-5Lv nhân vật) quay về ta trọng thưởng>Ngươi có muốn làm việc này không ?', 'Hoàn thành sớm đi còn về nhận thưởng', '1Hãy nhận lấy chút phần thưởng nhé', '32000', '32000', 4, '32000', '234', 0, 6000000, '32000', '32000', '32000', 5, 'Nhiệm vụ:|Giết 234 quái +-5Lv|NPC nv:|Lâm Tướng Quân|Phần thưởng:|6000000 exp', 'Nhiệm vụ hoàn thành|Quay về gặp Lâm Tướng Quân|Để nhận thưởng', 60, 0, 0),
	(27, 'Trừ gian diệt ác (khó)', 21, 21, 40, 1, '1Cướp bóc, quái vật đang nổi lên hoành hành khắp mọi nơi>1thiết nghĩ phải có người ra tay tiêu diệt.>0Đó là điều nên làm ạ>1Ta thấy ngươi có thể đảm đương việc này>1Tiêu diệt 350 con quái (+-5Lv nhân vật) quay về ta trọng thưởng>Ngươi có muốn làm việc này không ?', 'Hoàn thành sớm đi còn về nhận thưởng', '1Hãy nhận lấy chút phần thưởng nhé', '32000', '32000', 4, '32000', '350', 0, 10000000, '32000', '32000', '32000', 5, 'Nhiệm vụ:|Giết 350 quái +-5Lv|NPC nv:|Lâm Tướng Quân|Phần thưởng:|10000000 exp', 'Nhiệm vụ hoàn thành|Quay về gặp Lâm Tướng Quân|Để nhận thưởng', 100, 0, 0),
	(28, 'Hành hiệp trượng nghĩa (dễ)', -29, -29, 50, 1, '1Trên đường bôn tẩu cố gắng tiêu diệt quái vật càng nhiều càng tốt>1đó cũng là một cách trừ hại cho dân>0Tôi xin ghi nhớ lời tướng quân dặn>1Khi nào đủ số 135 con quái (+-5Lv nhân vật) về gặp ta nhận thưởng>Ngươi có muốn nhận nhiệm vụ này không?', 'Ta vừa xem qua, vẫn chưa đủ số quái so với yêu cầu. Tiếp tục đi nhé!', '1Ngươi thật có lòng, ta xin thay mặt mọi người cảm ơn ngươi', '32000', '32000', 4, '32000', '135', 0, 6000000, '32000', '32000', '32000', 5, 'Nhiệm vụ:|Giết 135 quái +-5Lv|NPC nv:|Trần Thống Lĩnh|Phần thưởng:|6000000 exp', 'Nhiệm vụ hoàn thành|Quay về gặp Trần Thống Lĩnh|Để nhận thưởng', 30, 0, 0),
	(29, 'Hành hiệp trượng nghĩa (vừa)', -29, -29, 50, 1, '1Trên đường bôn tẩu cố gắng tiêu diệt quái vật càng nhiều càng tốt>1đó cũng là một cách trừ hại cho dân>0Tôi xin ghi nhớ lời tướng quân dặn>1Khi nào đủ số 270 con quái (+-5Lv nhân vật) về gặp ta nhận thưởng>Ngươi có muốn nhận nhiệm vụ này không?', 'Ta vừa xem qua, vẫn chưa đủ số quái so với yêu cầu. Tiếp tục đi nhé!', '1Ngươi thật có lòng, ta xin thay mặt mọi người cảm ơn ngươi', '32000', '32000', 4, '32000', '270', 0, 16000000, '32000', '32000', '32000', 5, 'Nhiệm vụ:|Giết 270 quái +-5Lv|NPC nv:|Trần Thống Lĩnh|Phần thưởng:|16000000 exp', 'Nhiệm vụ hoàn thành|Quay về gặp Trần Thống Lĩnh|Để nhận thưởng', 60, 0, 0),
	(30, 'Hành hiệp trượng nghĩa (khó)', -29, -29, 50, 1, '1Trên đường bôn tẩu cố gắng tiêu diệt quái vật càng nhiều càng tốt>1đó cũng là một cách trừ hại cho dân>0Tôi xin ghi nhớ lời tướng quân dặn>1Khi nào đủ số 400 con quái (+-5Lv nhân vật) về gặp ta nhận thưởng>Ngươi có muốn nhận nhiệm vụ này không?', 'Ta vừa xem qua, vẫn chưa đủ số quái so với yêu cầu. Tiếp tục đi nhé!', '1Ngươi thật có lòng, ta xin thay mặt mọi người cảm ơn ngươi', '32000', '32000', 4, '32000', '400', 0, 25000000, '32000', '32000', '32000', 5, 'Nhiệm vụ:|Giết 400 quái +-5Lv|NPC nv:|Trần Thống Lĩnh|Phần thưởng:|25000000 exp', 'Nhiệm vụ hoàn thành|Quay về gặp Trần Thống Lĩnh|Để nhận thưởng', 100, 0, 0),
	(31, 'Trừ hại cho dân (dễ)', -29, -29, 60, 1, '1Ngươi hãy đi giúp những người dân ở vùng sâu vùng xa, quái vật yêu ma còn nhiều>Có như vậy họ mới an cư lạc nghiệp được>0Chí phải ạ!>1Tiêu diệt 150 con quái (+-5Lv nhân vật) sau đó về đây báo công nhận thưởng.>Việc này rất gian nan, ngươi có muốn thực hiện không?', 'Đi sớm cho kịp, họ đang đợi ngươi đến giúp đó.', '1Ngươi thật vất vả, sẽ có phần thưởng xứng đáng cho ngươi>0Vâng, xin cảm ơn!', '32000', '32000', 4, '32000', '150', 0, 24000000, '32000', '32000', '32000', 5, 'Nhiệm vụ:|Giết 150 quái +-5Lv|NPC nv:|Trần Thống Lĩnh|Phần thưởng:|24000000 exp', 'Nhiệm vụ hoàn thành|Quay về gặp Trần Thống Lĩnh|Để nhận thưởng', 30, 0, 0),
	(32, 'Trừ hại cho dân (vừa)', -29, -29, 60, 1, '1Ngươi hãy đi giúp những người dân ở vùng sâu vùng xa, quái vật yêu ma còn nhiều>Có như vậy họ mới an cư lạc nghiệp được>0Chí phải ạ!>1Tiêu diệt 300 con quái (+-5Lv nhân vật) sau đó về đây báo công nhận thưởng.>Việc này rất gian nan, ngươi có muốn thực hiện không?', 'Đi sớm cho kịp, họ đang đợi ngươi đến giúp đó.', '1Ngươi thật vất vả, sẽ có phần thưởng xứng đáng cho ngươi>0Vâng, xin cảm ơn!', '32000', '32000', 4, '32000', '300', 0, 50000000, '32000', '32000', '32000', 5, 'Nhiệm vụ:|Giết 300 quái +-5Lv|NPC nv:|Trần Thống Lĩnh|Phần thưởng:|50000000 exp', 'Nhiệm vụ hoàn thành|Quay về gặp Trần Thống Lĩnh|Để nhận thưởng', 60, 0, 0),
	(33, 'Trừ hại cho dân (khó)', -29, -29, 60, 1, '1Ngươi hãy đi giúp những người dân ở vùng sâu vùng xa, quái vật yêu ma còn nhiều>Có như vậy họ mới an cư lạc nghiệp được>0Chí phải ạ!>1Tiêu diệt 450 con quái (+-5Lv nhân vật) sau đó về đây báo công nhận thưởng.>Việc này rất gian nan, ngươi có muốn thực hiện không?', 'Đi sớm cho kịp, họ đang đợi ngươi đến giúp đó.', '1Ngươi thật vất vả, sẽ có phần thưởng xứng đáng cho ngươi>0Vâng, xin cảm ơn!', '32000', '32000', 4, '32000', '450', 0, 80000000, '32000', '32000', '32000', 5, 'Nhiệm vụ:|Giết 450 quái +-5Lv|NPC nv:|Trần Thống Lĩnh|Phần thưởng:|80000000 exp', 'Nhiệm vụ hoàn thành|Quay về gặp Trần Thống Lĩnh|Để nhận thưởng', 100, 0, 0),
	(34, 'Trừ khử yêu ma (dễ)', -29, -29, 70, 1, '1Yêu ma quỷ quái xuất hiện khắp nơi, việc tiêu diệt không thể chậm trễ>1Nếu để chúng sinh sôi nãy nở thì sẽ thành họa cho muôn dân>0Dạ!>1Giúp ta giết 167 con quái (+-5Lv nhân vật) ta sẽ trọng thưởng>Ngươi có muốn làm việc này không ?', 'Sao còn đứng đây ?', '1Ngươi đã lập công to rồi đấy, cố gắng phát huy nhé.>0Xin vâng!', '32000', '32000', 4, '32000', '167', 0, 40000000, '32000', '32000', '32000', 5, 'Nhiệm vụ:|Giết 167 quái +-5Lv|NPC nv:|Trần Thống Lĩnh|Phần thưởng:|40000000 exp', 'Nhiệm vụ hoàn thành|Quay về gặp Trần Thống Lĩnh|Để nhận thưởng', 30, 0, 0),
	(35, 'Trừ khử yêu ma (vừa)', -29, -29, 70, 1, '1Yêu ma quỷ quái xuất hiện khắp nơi, việc tiêu diệt không thể chậm trễ>1Nếu để chúng sinh sôi nãy nở thì sẽ thành họa cho muôn dân>0Dạ!>1Giúp ta giết 334 con quái (+-5Lv nhân vật) ta sẽ trọng thưởng>Ngươi có muốn làm việc này không ?', 'Sao còn đứng đây ?', '1Ngươi đã lập công to rồi đấy, cố gắng phát huy nhé.>0Xin vâng!', '32000', '32000', 4, '32000', '334', 0, 90000000, '32000', '32000', '32000', 5, 'Nhiệm vụ:|Giết 334 quái +-5Lv|NPC nv:|Trần Thống Lĩnh|Phần thưởng:|90000000 exp', 'Nhiệm vụ hoàn thành|Quay về gặp Trần Thống Lĩnh|Để nhận thưởng', 60, 0, 0),
	(36, 'Trừ khử yêu ma (khó)', -29, -29, 70, 1, '1Yêu ma quỷ quái xuất hiện khắp nơi, việc tiêu diệt không thể chậm trễ>1Nếu để chúng sinh sôi nãy nở thì sẽ thành họa cho muôn dân>0Dạ!>1Giúp ta giết 500 con quái (+-5Lv nhân vật) ta sẽ trọng thưởng>Ngươi có muốn làm việc này không ?', 'Sao còn đứng đây ?', '1Ngươi đã lập công to rồi đấy, cố gắng phát huy nhé.>0Xin vâng!', '32000', '32000', 4, '32000', '500', 0, 150000000, '32000', '32000', '32000', 5, 'Nhiệm vụ:|Giết 500 quái +-5Lv|NPC nv:|Trần Thống Lĩnh|Phần thưởng:|150000000 exp', 'Nhiệm vụ hoàn thành|Quay về gặp Trần Thống Lĩnh|Để nhận thưởng', 100, 0, 0),
	(37, 'Móng cá sấu', -3, -3, 1, 2, '1Ta muốn làm 1 sợi dây chuyền từ móng cá sấu>1nhưng hiện tại ta đang bận việc.>1Vậy ngươi hãy đi tìm cho ta 100 móng cá sâu để ta làm dây chuyền.>Ngươi có đồng ý không?', 'Sao còn đứng đây ?', '1Ngươi đã lập công to rồi đấy, cố gắng phát huy nhé.>0Xin vâng!', '7', '29', 2, '100', '32000', 0, 5, '32000', '32000', '32000', 0, 'Nhiệm vụ:|Giết 100 con cá sấu|Địa điểm:|Trường Giang|NPC nv:|Tả Phó Thống|Phần thưởng:|5exp bang hội', 'Nhiệm vụ hoàn thành|Về gặp Tả Phó Thống|Nhận thưởng', 0, 0, 0),
	(38, 'Tìm da ếch', -3, -3, 1, 2, '1Ta đang cần tìm 100 da ếch quỷ đề làm thuốc.> Ngươi có thể giúp ta không?', 'Sao còn đứng đây ?', '1Ngươi đã lập công to rồi đấy, cố gắng phát huy nhé.>0Xin vâng!', '8', '30', 2, '100', '32000', 0, 5, '32000', '32000', '32000', 0, 'Nhiệm vụ:|Giết 100 con ếch quỷ|Địa điểm:|Trường Giang|NPC nv:|Tả Phó Thống|Phần thưởng:|5exp bang hội', 'Nhiệm vụ hoàn thành|Về gặp Tả Phó Thống|Nhận thưởng', 0, 0, 0),
	(39, 'Tìm cựa gà', -3, -3, 1, 2, '1Nghe nói cựa Gà khổng lồ ở vùng đảo Lộc Trĩ nhọn như kim châm,>1sắc như dao cạo rất phù hợp để chế tạo vũ khí. >1Ta cần khoảng 100 cái cựa.>Ngươi có thể đi tìm về cho ta không?', 'Sao còn đứng đây ?', '1Ngươi đã lập công to rồi đấy, cố gắng phát huy nhé.>0Xin vâng!', '9', '22', 2, '100', '32000', 0, 5, '32000', '32000', '32000', 0, 'Nhiệm vụ:|Giết 100 con gà khổng lồ|Địa điểm:|Lộc Trĩ|NPC nv:|Tả Phó Thống|Phần thưởng:|5exp bang hội', 'Nhiệm vụ hoàn thành|Về gặp Tả Phó Thống|Nhận thưởng', 0, 0, 0),
	(40, 'Bắt dế khổng lồ', -3, -3, 1, 2, '1Thịt Dế khổng lồ ở vùng Sơn Lâm> 1là một món ngon hiếm có, >1sắp tới có tiệc chiêu đãi anh hùng các bang hội. >Ngươi có thể mang 100 con Dế khổng lồ về đây cho ta không?', 'Sao còn đứng đây ?', '1Ngươi đã lập công to rồi đấy, cố gắng phát huy nhé.>0Xin vâng!', '32000', '31', 0, '32000', '100', 0, 5, '32000', '32000', '32000', 0, 'Nhiệm vụ:|Giết 100 con dế khổng lồ|Địa điểm:|Sơn Lâm|NPC nv:|Tả Phó Thống|Phần thưởng:|5exp bang hội', 'Nhiệm vụ hoàn thành|Về gặp Tả Phó Thống|Nhận thưởng', 0, 0, 0),
	(41, 'Thuần hoá trâu núi', -3, -3, 1, 2, '1Ta nghe nói một con Trâu núi khoẻ gấp 3 lần con trâu nhà,> 1giá mà mang được loài này về thuần hoá phục vụ cho việc cầy bừa thì thật là tốt.> Ngươi có thể thuần hoá 100 con Trâu núi cho ta không ?', 'Sao còn đứng đây ?', '1Ngươi đã lập công to rồi đấy, cố gắng phát huy nhé.>0Xin vâng!', '32000', '33', 0, '32000', '100', 0, 5, '32000', '32000', '32000', 0, 'Nhiệm vụ:|Giết 100 con trâu núi|Địa điểm:|Sơn Lâm|NPC nv:|Tả Phó Thống|Phần thưởng:|5exp bang hội', 'Nhiệm vụ hoàn thành|Về gặp Tả Phó Thống|Nhận thưởng', 0, 0, 0),
	(42, 'Trừng trị sơn tặc', -3, -3, 1, 2, '1Bọn Sơn tặc dạo này rất hoành hành, >1chúng thường chặn đường cướp bóc người qua lại >1trên các con đường trọng yếu giữa các vùng.>1Đây đang là một vấn nạn cho mọi người,>1buộc phải dẹp yên.>1Theo nguồn tin ta có được>1sào huyệt của bọn chúng tại phía bắc Sơn Lâm,>1chúng có khoảng 100 tên.>Ngươi có sẵn sàng tiêu diệt hết bọn chúng cho ta không ?', 'Sao còn đứng đây ?', '1Ngươi đã lập công to rồi đấy, cố gắng phát huy nhé.>0Xin vâng!', '32000', '34', 0, '32000', '100', 0, 5, '32000', '32000', '32000', 0, 'Nhiệm vụ:|Giết 100 tên sơn tặc|Địa điểm:|Sơn Lâm|NPC nv:|Tả Phó Thống|Phần thưởng:|5exp bang hội', 'Nhiệm vụ hoàn thành|Về gặp Tả Phó Thống|Nhận thưởng', 0, 0, 0),
	(43, 'Trừng trị hải tặc', -3, -3, 1, 2, '1Vùng biển Lộc Trĩ rất hay xuất hiện Hải Tặc,>1chúng thường tấn công vào các tàu đánh cá của ngư dân>1cướp bóc không thương tiếc.>1Hãy diệt hết bọn chúng >1mang bình yên lại cho ngư dân lương thiện nhé.>1Ít nhất phải mang về đây cho ta 100 tên đấy>Ngươi có muốn làm việc này không ?', 'Sao còn đứng đây ?', '1Ngươi đã lập công to rồi đấy, cố gắng phát huy nhé.>0Xin vâng!', '32000', '35', 0, '32000', '100', 0, 5, '32000', '32000', '32000', 0, 'Nhiệm vụ:|Giết 100 con hải tặc|Địa điểm:|Sơn Lâm|NPC nv:|Tả Phó Thống|Phần thưởng:|5exp bang hội', 'Nhiệm vụ hoàn thành|Về gặp Tả Phó Thống|Nhận thưởng', 0, 0, 0),
	(44, 'Tìm rết đỏ', -3, -3, 1, 2, '1Loài Rết đỏ kết hợp với một số thảo dược >1sẽ mang lại hiệu quả thần kỳ cho việc chữa trị nội thương. >1Ngươi hãy giúp ta tìm 100 con Rết đỏ >1để ngâm rượu thuốc nhé.>Liệu ngươi có giúp ta không ?', 'Sao còn đứng đây ?', '1Ngươi đã lập công to rồi đấy, cố gắng phát huy nhé.>0Xin vâng!', '3', '40', 2, '100', '32000', 0, 10, '32000', '32000', '32000', 0, 'Nhiệm vụ:|Giết 100 con rết đỏ|Địa điểm:|Phong Châu|NPC nv:|Tả Phó Thống|Phần thưởng:|5exp bang hội', 'Nhiệm vụ hoàn thành|Về gặp Tả Phó Thống|Nhận thưởng', 0, 0, 0),
	(45, 'Tìm hồng kê', -3, -3, 1, 2, '1Các loại áo giáp trân quý ngoài bảo thạch thì đều cần lông đuôi Hồng kê >1để điểm xuyết nhằm tăng thêm giá trị. >1Ngươi hãy giúp ta mang về đây 100 lông đuôi Hồng kê, >1tang đang rất cần. >1Cẩn thận kẻo bỏ mạng, >1loài này rất hung dữ chúng thường xuất hiện ở vùng Phong Châu>Ngươi có muốn làm việc này không ?', 'Sao còn đứng đây ?', '1Ngươi đã lập công to rồi đấy, cố gắng phát huy nhé.>0Xin vâng!', '6', '45', 2, '100', '32000', 0, 10, '32000', '32000', '32000', 0, 'Nhiệm vụ:|Giết 100 con hồng kê|Địa điểm:|Phong Châu|NPC nv:|Tả Phó Thống|Phần thưởng:|5exp bang hội', 'Nhiệm vụ hoàn thành|Về gặp Tả Phó Thống|Nhận thưởng', 0, 0, 0),
	(46, 'Tìm hổ đỏ nấu cao', -3, -3, 1, 2, '1Khi thời tiết thay đổi ta thường có những cơn đau khắp người,>1thật sự ta đã già rồi. >1Tương truyền cao Hổ đỏ có tác dụng giảm đau rất hữu hiệu. >1Ngươi hãy đi bắt 100 con Hổ đỏ để ta chế biến cao hổ nhé>Ngươi có muốn giúp ta không ?', 'Sao còn đứng đây ?', '1Ngươi đã lập công to rồi đấy, cố gắng phát huy nhé.>0Xin vâng!', '32000', '63', 0, '32000', '100', 0, 10, '32000', '32000', '32000', 0, 'Nhiệm vụ:|Giết 100 con hổ đỏ|Địa điểm:|Bình Lục|NPC nv:|Tả Phó Thống|Phần thưởng:|5exp bang hội', 'Nhiệm vụ hoàn thành|Về gặp Tả Phó Thống|Nhận thưởng', 0, 0, 0),
	(47, 'Trừ khử yêu ma (dễ)', -29, -29, 80, 1, '1Yêu ma quỷ quái xuất hiện khắp nơi, việc tiêu diệt không thể chậm trễ>1Nếu để chúng sinh sôi nãy nở thì sẽ thành họa cho muôn dân>0Dạ!>1Giúp ta giết 5000 con quái (+-5Lv nhân vật) ta sẽ trọng thưởng>Ngươi có muốn làm việc này không ?', 'Sao còn đứng đây ?', '1Ngươi đã lập công to rồi đấy, cố gắng phát huy nhé.>0Xin vâng!', '32000', '32000', 4, '32000', '5000', 0, 150000000, '32000', '32000', '32000', 0, 'Nhiệm vụ:|Giết 5000 quái +-5Lv|NPC nv:|Trần Thống Lĩnh|Phần thưởng:|150000000 exp', 'Nhiệm vụ hoàn thành|Quay về gặp Trần Thống Lĩnh|Để nhận thưởng', 30, 0, 0),
	(48, 'Trừ khử yêu ma (vừa)', -29, -29, 80, 1, '1Yêu ma quỷ quái xuất hiện khắp nơi, việc tiêu diệt không thể chậm trễ>1Nếu để chúng sinh sôi nãy nở thì sẽ thành họa cho muôn dân>0Dạ!>1Giúp ta giết 6000 con quái (+-5Lv nhân vật) ta sẽ trọng thưởng>Ngươi có muốn làm việc này không ?', 'Sao còn đứng đây ?', '1Ngươi đã lập công to rồi đấy, cố gắng phát huy nhé.>0Xin vâng!', '32000', '32000', 4, '32000', '6000', 0, 200000000, '32000', '32000', '32000', 0, 'Nhiệm vụ:|Giết 6000 quái +-5Lv|NPC nv:|Trần Thống Lĩnh|Phần thưởng:|200000000 exp', 'Nhiệm vụ hoàn thành|Quay về gặp Trần Thống Lĩnh|Để nhận thưởng', 60, 0, 0),
	(49, 'Trừ khử yêu ma (khó)', -29, -29, 80, 1, '1Yêu ma quỷ quái xuất hiện khắp nơi, việc tiêu diệt không thể chậm trễ>1Nếu để chúng sinh sôi nãy nở thì sẽ thành họa cho muôn dân>0Dạ!>1Giúp ta giết 7000 con quái (+-5Lv nhân vật) ta sẽ trọng thưởng>Ngươi có muốn làm việc này không ?', 'Sao còn đứng đây ?', '1Ngươi đã lập công to rồi đấy, cố gắng phát huy nhé.>0Xin vâng!', '32000', '32000', 4, '32000', '7000', 0, 250000000, '32000', '32000', '32000', 0, 'Nhiệm vụ:|Giết 7000 quái +-5Lv|NPC nv:|Trần Thống Lĩnh|Phần thưởng:|250000000 exp', 'Nhiệm vụ hoàn thành|Quay về gặp Trần Thống Lĩnh|Để nhận thưởng', 100, 0, 0);
/*!40000 ALTER TABLE `data_quest` ENABLE KEYS */;

-- Dumping structure for table kpah.data_shop
CREATE TABLE IF NOT EXISTS `data_shop` (
  `id` int(11) NOT NULL,
  `idImg` int(11) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `decript` varchar(255) DEFAULT NULL,
  `value` int(11) DEFAULT NULL,
  `price` int(11) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `tab` int(11) DEFAULT NULL,
  `sell` int(11) DEFAULT NULL,
  `priceSale` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Dumping data for table kpah.data_shop: ~148 rows (approximately)
DELETE FROM `data_shop`;
INSERT INTO `data_shop` (`id`, `idImg`, `name`, `decript`, `value`, `price`, `type`, `tab`, `sell`, `priceSale`) VALUES
	(0, 11, 'Vé giờ vàng 100', 'Tăng 100% kinh nghiệm', 2, 15, 0, 0, 1, 10),
	(1, 12, 'Vé giờ vàng 50', 'Tăng 50% kinh nghiệm', 1, 10, 0, 0, 1, 7),
	(2, 9, '200 bình hp vừa', 'Hồi phục 1500 hp', 200, 10, 0, 0, 1, 7),
	(3, 10, '200 bình hp to', 'Hồi phục 3000 hp', 200, 15, 0, 0, 1, 10),
	(4, 7, '200 bình mp vừa', 'Hồi phục 2500 mp', 200, 10, 0, 0, 1, 7),
	(5, 8, '200 bình mp to', 'Hồi phục 3500 mp', 200, 15, 0, 0, 1, 10),
	(6, 20, 'Tiềm năng đan', 'Khôi phục lại tiềm năng', 1, 50, 0, 0, 1, 35),
	(7, 21, 'Kỹ năng đan', 'Khôi phục lại kỹ năng', 1, 50, 0, 0, 1, 35),
	(8, 32, 'Thần hành phù', 'Cho phép di chuyển tức thời đến 1 vị trí giặc trên lãnh thổ của mình', 10, 100000, 1, 1, 1, 100000),
	(9, 22, 'Tự động lượm đồ', '', 1, 15, 0, 1, 0, 10),
	(10, 32, 'Phiếu công ích', 'Giảm trừ 100 điểm đồ sát', 1, 1, 0, 1, 1, 1),
	(11, 27, 'Thiên lý mã', 'Tốc độ tăng 1 và tăng 700 MP và 700 HP', 1, 10, 0, 1, 1, 7),
	(12, 33, 'Kim bài', 'Kim bài tạo bang hội', 1, 250, 0, 1, 1, 175),
	(13, 13, 'Thẻ đấu trường', 'Thẻ vào đấu trường', 10, 1000, 1, 1, 0, 1000),
	(14, 39, 'Thẻ xác nhận', 'Thẻ bài xác nhận chiếm thành', 5, 10, 0, 1, 1, 7),
	(15, 37, 'Xích Thô', 'Cộng 3 điểm vào tất cả tiềm năng và 1000 HP MP', 1, 70, 0, 1, 1, 49),
	(18, 113, 'Đầu đinh', 'Đổi thành đầu đinh', 1, 3, 0, 2, 1, 2),
	(19, 114, 'Tóc búi', 'Đổi thành tóc búi', 1, 3, 0, 2, 1, 2),
	(20, 115, 'Tóc cột cao', 'Đổi thành tóc cột cao', 1, 3, 0, 2, 1, 2),
	(21, 116, 'Tóc chéo', 'Đổi thành tóc chéo', 1, 3, 0, 2, 1, 2),
	(22, 117, 'Tóc xù', 'Đổi thành tóc xù', 1, 3, 0, 2, 1, 2),
	(23, 118, 'Tóc ngang vai', 'Đổi thành tóc ngang vai', 1, 3, 0, 2, 1, 2),
	(24, 119, 'Tóc đinh so le', 'Đổi thành tóc đinh so le', 1, 3, 0, 2, 1, 2),
	(25, 120, 'Tóc so le', 'Đổi thành tóc so le', 1, 3, 0, 2, 1, 2),
	(26, 121, 'Tóc thư sinh', 'Đổi thành tóc thư sinh', 1, 3, 0, 2, 1, 2),
	(27, 122, 'Tóc cài hoa', 'Đổi thành tóc cài hoa', 1, 3, 0, 2, 1, 2),
	(28, 123, 'Tóc bờm ngựa', 'Đổi thành tóc bờm ngựa', 1, 3, 0, 2, 1, 2),
	(29, 124, 'Tóc tiên đồng', 'Đổi thành tóc tiên đồng', 1, 3, 0, 2, 1, 2),
	(30, 125, 'Tóc dựng đứng', 'Đổi thành tóc dựng đứng', 1, 3, 0, 2, 1, 2),
	(31, 43, 'Hoa Mai', 'Tặng 1 Hoa mai cho người khác', 1, 2, 0, 2, 0, 2),
	(32, 43, 'Hoa Mai', 'Tặng 10 Hoa mai cho người khác', 1, 15, 0, 2, 0, 8),
	(33, 43, 'Hoa Mai', 'Tặng 39 Hoa mai cho người khác', 1, 50, 0, 2, 0, 25),
	(34, 43, 'Hoa Mai', 'Tặng 99 Hoa mai cho người khác', 1, 99, 0, 2, 0, 50),
	(35, 44, 'Hoa đào', 'Hoa dùng để tặng bạn', 1, 2, 0, 2, 0, 2),
	(36, 44, 'Hoa đào', 'Hoa dùng để tặng bạn', 1, 15, 0, 2, 0, 8),
	(37, 44, 'Hoa đào', 'Hoa dùng để tặng bạn', 1, 50, 0, 2, 0, 25),
	(38, 44, 'Hoa đào', 'Hoa dùng để tặng bạn', 1, 99, 0, 2, 0, 50),
	(39, 45, 'Hoa hồng', 'Tặng 1 Hoa hồng cho người khác', 1, 2, 0, 2, 1, 2),
	(40, 45, 'Hoa hồng', 'Tặng 9 Hoa hồng cho người khác', 1, 15, 0, 2, 1, 8),
	(41, 45, 'Hoa hồng', 'Tặng 39 Hoa hồng cho người khác', 1, 60, 0, 2, 1, 30),
	(42, 45, 'Hoa hồng', 'Tặng 99 Hoa hồng cho người khác', 1, 120, 0, 2, 1, 60),
	(45, 47, 'Hăc ngưu', 'Vật phẩm tạo linh thú Hắc ngưu', 1, 300, 0, 1, 1, 210),
	(46, 46, 'Mãnh hổ', 'Vật phẩm tạo linh thú Mãnh hổ', 1, 300, 0, 1, 1, 210),
	(47, 48, 'Sói xám', 'Vật phẩm tạo linh thú Sói xám', 1, 300, 0, 1, 1, 210),
	(48, 49, 'Tiên hạc', 'Vật phẩm tạo linh thú Tiên hạc', 1, 300, 0, 1, 1, 210),
	(49, 38, 'Bạch mã', 'Vật phẩm tạo linh thú Bạch mã', 1, 300, 0, 1, 1, 210),
	(50, 52, 'Áo dài', 'Cộng 6 điểm vào tất cả tiềm năng và 3000 HP MP. Thời hạn 7 ngày', 1, 70, 0, 1, 0, 35),
	(51, 55, 'Lạc long quân', 'Cộng 6 điểm vào tất cả tiềm năng và 3000 HP MP. Thời hạn 7 ngày', 1, 70, 0, 1, 0, 70),
	(52, 58, 'Noel nữ', 'Cộng 10%tc 10%pt và 5000 HP MP', 1, 70, 0, 1, 0, 70),
	(53, 54, 'Thạch sanh', 'Cộng 8%tc 8%pt và 2000 HP MP. Thời hạn 7 ngày', 1, 100, 0, 1, 0, 70),
	(54, 56, 'Thầy đồ', 'Cộng 6 điểm vào tất cả tiềm năng và 3000 HP MP. Thời hạn 7 ngày', 1, 70, 0, 1, 0, 49),
	(55, 53, 'Tiên nữ', 'Cộng 6 điểm vào tất cả tiềm năng và 3000 HP MP. Thời hạn 7 ngày', 1, 70, 0, 1, 0, 49),
	(56, 51, 'Yếm đào', 'Cộng 8%tc 8%pt và 2000 HP MP. Thời hạn 7 ngày', 1, 100, 0, 1, 0, 70),
	(57, 59, 'Noel nam', 'Cộng 10%tc 10%pt và 5000 HP MP', 1, 70, 0, 1, 0, 70),
	(58, 50, 'Nón noel', 'Cộng 5%tc 5%pt và 3000 HP MP', 1, 70, 0, 1, 0, 70),
	(65, 31, 'Thuốc cường hoá', 'Tăng 100% các chỉ số trong 1 phút', 1, 500000, 1, 0, 1, 500000),
	(72, 32, 'Vé vào khu mỏ', 'Vé vào khu mỏ để đào nguyện liệu.', 1, 50000, 1, 0, 1, 50000),
	(74, 10, '200 bình HP 7000', 'Hồi phục  7000 hp', 200, 20, 0, 0, 1, 14),
	(75, 10, '200 bình HP 15000', 'Hồi phục  15000 hp', 200, 25, 0, 0, 1, 17),
	(76, 8, '200 bình MP 7000', 'Hồi phục  7000 mp', 200, 20, 0, 0, 1, 14),
	(77, 8, '200 bình MP 15000', 'Hồi phục  15000 mp', 200, 25, 0, 0, 1, 17),
	(81, 77, 'Loa', 'Loa', 10, 30, 0, 1, 1, 15),
	(86, 80, 'Áo Dracula nữ \n -Cộng 10%tc 10%pt và 3000 HP MP. Thời hạn 15 ngày', '', 1, 700, 0, 1, 0, 700),
	(87, 79, 'Áo Hằng Nga \n -Cộng 10%tc 10%pt và 3000 HP MP. Thời hạn 7 ngày', '', 1, 800, 0, 1, 0, 800),
	(88, 81, 'Áo Dracula nam \n -Cộng 10%tc 10%pt và 3000 HP MP. Thời hạn 15 ngày', '', 1, 700, 0, 1, 0, 700),
	(89, 78, 'Áo Chú cuội \n -Cộng 10%tc 10%pt  và 3000 HP MP. Thời hạn 7 ngày', '', 1, 800, 0, 1, 0, 800),
	(90, 83, 'Đầu bí ngô \n-Tăng 50% tỷ lệ xuất hiện thuộc tính lan và 3000 HP MP. Thời hạn 10 ngày', '', 1, 100, 0, 1, 0, 100),
	(91, 82, 'Nón phù thủy \n -Tăng 50% tỷ lệ xuất hiện thuộc tính lan và 3000 HP MP. Thời hạn 10 ngày', '', 1, 100, 0, 1, 0, 100),
	(92, 84, 'Dơi quỷ \n -Cộng 30% tấn công. Thời hạn 7 ngày', '', 1, 90, 0, 1, 0, 90),
	(93, 85, 'Thức ăn tươi', 'tăng 2% phòng thủ. Thời hạn 4h', 1, 5, 0, 1, 0, 3),
	(94, 86, 'Thức ăn khô', 'tăng 2% phòng tấn công. Thời hạn 4h', 1, 5, 0, 1, 0, 3),
	(95, 87, 'Thức ăn tổng hợp', 'tăng 2% phòng tấn công,phòng thủ, thuộc tính đặc biệt. Thời hạn 4h', 1, 5, 0, 1, 0, 3),
	(104, 31, 'Thuốc cường hóa siêu cấp', 'Tăng 150% các chỉ số trong 30s', 1, 5000000, 1, 0, 0, 5000000),
	(105, 216, 'Áo dài nữ \n -Cộng 10%tc 10%pt và 3000 HP MP. Thời hạn 7 ngày', '', 1, 100, 0, 1, 0, 100),
	(106, 217, 'Áo dài nam \n -Cộng 10%tc 10%pt và 3000 HP MP. Thời hạn 7 ngày', '', 1, 100, 0, 1, 0, 100),
	(107, 94, 'Pet Armadillo băng \n -Tăng 10%exp,10% tấn công,10% phòng thủ, x3 thuộc tính băng lan. Thời hạn 7 ngày', '', 1, 100, 0, 1, 0, 50),
	(108, 23, 'Bình tăng tốc bạc.', 'Giảm 20% thời gian chín của trái bạc', 1, 5, 0, 0, 1, 3),
	(109, 88, 'Bình tăng tốc vàng.', 'Giảm 20% thời gian chín của trái vàng', 1, 10, 0, 0, 1, 5),
	(110, 95, 'Pet Armadillo sét \n -Tăng 10%exp,10% tấn công,10% phòng thủ, x3 thuộc tính sét lan. Thời hạn 7 ngày', '', 1, 100, 0, 1, 0, 50),
	(111, 96, 'Pet Armadillo độc \n -Tăng 10%exp,10% tấn công,10% phòng thủ, x3 thuộc tính độc lan. Thời hạn 7 ngày', '', 1, 100, 0, 1, 0, 50),
	(112, 97, 'Phi phong world cup ma pháp', 'Chỉ số max. Thời hạn 7 ngày', 1, 90, 0, 1, 0, 90),
	(113, 98, 'áo brazin nam \n -Cộng 10%tc 10%pt và 3000 HP MP. Tăng 100% băng lan, sét lan, độc lan. Thời hạn 7 ngày', '', 1, 100, 0, 1, 0, 50),
	(114, 98, 'áo brazin nữ \n -Cộng 10%tc 10%pt và 3000 HP MP. Tăng 100% băng lan, sét lan, độc lan. Thời hạn 7 ngày', '', 1, 100, 0, 1, 0, 50),
	(115, 97, 'Phi phong world cup vật lý \n -Chỉ số max. Thời hạn 7 ngày', '', 1, 90, 0, 1, 0, 90),
	(116, 100, 'Vải cấp 4 khoá \n - Dùng chế tạo trang bị', '', 1, 15, 0, 2, 1, 15),
	(117, 100, 'Vải cấp 5 khoá \n - Dùng chế tạo trang bị', '', 1, 45, 0, 2, 1, 45),
	(118, 101, 'Sắt cấp 4 khoá \n - Dùng chế tạo trang bị', '', 1, 15, 0, 2, 1, 15),
	(119, 101, 'Sắt cấp 5 khoá \n - Dùng chế tạo trang bị', '', 1, 45, 0, 2, 1, 45),
	(120, 102, 'Ngọc cấp 4 khoá \n - Dùng chế tạo trang bị', '', 1, 15, 0, 2, 1, 15),
	(121, 102, 'Ngọc cấp 5 khoá \n - Dùng chế tạo trang bị', '', 1, 45, 0, 2, 1, 45),
	(122, 103, 'Gỗ thường cấp 4 khoá \n - Dùng chế tạo trang bị', '', 1, 15, 0, 2, 1, 15),
	(123, 103, 'Gỗ thường cấp 5 khoá \n - Dùng chế tạo trang bị', '', 1, 45, 0, 2, 1, 45),
	(124, 104, 'Da mềm cấp 4 khoá \n - Dùng chế tạo trang bị', '', 1, 15, 0, 2, 1, 15),
	(125, 104, 'Da mềm cấp 5 khoá \n - Dùng chế tạo trang bị', '', 1, 45, 0, 2, 1, 45),
	(126, 11, 'Vé giờ vàng  \n - Tăng 100% exp trong 1 giờ chơi. Tính theo thời gian chơi', '', 1, 25, 0, 2, 1, 25),
	(127, 10, '200 bình HP 15k \n - Hồi phục 15000 hp', '', 200, 50, 0, 2, 1, 50),
	(128, 8, '200 bình MP 15k \n - Hồi phục 15000 mp', '', 200, 50, 0, 2, 1, 50),
	(129, 105, 'Lệnh bài đổi tiêu \n - Đổi màu tiêu đang nhận', '', 5, 2000000, 1, 0, 1, 2000000),
	(130, 106, 'Lệnh bài đổi tiêu cao cấp \n - Đổi màu tiêu đang nhận', '', 10, 20, 0, 0, 1, 20),
	(131, 107, 'Thẻ vận tiêu \n - Thêm 1 lượt vận tiêu khi sử dụng', '', 1, 1000000, 1, 0, 1, 1000000),
	(132, 251, 'Huyết bồ đề \n - Thay đổi phẩm chất thú cưng', '', 1, 10, 0, 0, 1, 10),
	(133, 169, 'Pet chim cánh cụt \n -Tăng 10%exp,10% tấn công,10% phòng thủ, tăng 100% thuộc tính độc lan, băng lan, sét lan, 2% bạo kích. Thời hạn 7 ngày', '', 1, 100, 0, 0, 0, 100),
	(134, 181, 'Nhánh hoa hồng \n - Gửi lời chúc đến bạn bè nhân dịp 8/3', '', 1, 10, 0, 0, 0, 10),
	(135, 112, 'Socola \n - Dùng tặng cho con nít.', '', 10, 125, 0, 0, 0, 125),
	(136, 186, 'Lá dong', '', 1, 10000, 1, 0, 0, 10000),
	(137, 231, 'Băng tuyết nam \n -Cộng 10%tc 10%pt và 5000 HP MP.Tăng 100% băng lan, sét lan, độc lan, lửa lan', '', 1, 70, 0, 1, 0, 70),
	(138, 232, 'Băng tuyết nữ \n -Cộng 10%tc 10%pt và 5000 HP MP.Tăng 100% băng lan, sét lan, độc lan, lửa lan', '', 1, 70, 0, 1, 0, 70),
	(139, 192, 'Hồng hài nhi nam \n -Cộng 10%tc 10%pt và 5000 HP MP.Tăng 100% băng lan, sét lan, độc lan, lửa lan. Thời hạn 60 ngày', '', 1, 111, 0, 0, 0, 111),
	(140, 192, 'Hồng hài nhi nữ \n -Cộng 10%tc 10%pt và 5000 HP MP.Tăng 100% băng lan, sét lan, độc lan, lửa lan. Thời hạn 60 ngày', '', 1, 111, 0, 0, 0, 111),
	(141, 198, 'Lồng đèn rồng \n -Tăng 100% băng,sét,độc,lửa lan + 10% exp. Thời hạn 7 ngày', '', 1, 700, 0, 1, 0, 700),
	(142, 253, 'Freddy \n -Cộng 10%tc 10%pt và 5000 HP MP.Tăng 100% băng lan, sét lan, độc lan, lửa lan', '', 1, 70, 0, 1, 0, 70),
	(143, 252, 'Jason \n -Cộng 10%tc 10%pt và 5000 HP MP.Tăng 100% băng lan, sét lan, độc lan, lửa lan', '', 1, 70, 0, 1, 0, 70),
	(144, 254, 'Harry potter \n -Cộng 10%tc 10%pt và 5000 HP MP.Tăng 100% băng lan, sét lan, độc lan, lửa lan', '', 1, 70, 0, 1, 0, 70),
	(145, 255, 'Chúa tể voldermort \n -Cộng 10%tc 10%pt và 5000 HP MP.Tăng 100% băng lan, sét lan, độc lan, lửa lan', '', 1, 70, 0, 1, 0, 70),
	(146, 246, 'Dây thừng \n - Dùng bắt thú nuôi', '', 1, 100, 0, 0, 0, 100),
	(147, 246, 'Dây thừng \n - Dùng bắt thú nuôi', '', 3, 1000000000, 1, 0, 0, 10000000),
	(148, 64, 'Pháo hoa tết\n- Vật phẩm sự kiện', '', 1, 100, 0, 0, 1, 100),
	(149, 64, 'Pháo hoa tết\n- Vật phẩm sự kiện', '', 1, 10000000, 1, 0, 1, 10000000),
	(150, 141, 'Túi nhặt ngọc \n -Vật phẩm sự kiện', '', 1, 50, 0, 2, 0, 50),
	(151, 142, 'Gậy giáng sinh \n -Vật phẩm sự kiện. Thời hạn đến hết ngày tính từ lúc mua', '', 1, 5000000, 1, 0, 0, 5000000),
	(152, 143, 'Lân sư tử \n - Vật phẩm sự kiện. Thời hạn đến hết ngày tính từ lúc mua', '', 1, 2, 0, 0, 0, 2),
	(153, 144, 'Rương may mắn \n - Vật phẩm sự kiện. Thời hạn 15 phút', '', 1, 250, 0, 0, 0, 250),
	(154, 128, 'Tóc thời trang nữ \n - Dùng thay đổi kiểu tóc', '', 29, 5, 0, 2, 1, 5),
	(155, 130, 'Tóc thời trang nữ \n - Dùng thay đổi kiểu tóc', '', 31, 5, 0, 2, 1, 5),
	(156, 132, 'Tóc thời trang nữ \n - Dùng thay đổi kiểu tóc', '', 33, 5, 0, 2, 1, 5),
	(157, 134, 'Tóc thời trang nữ \n - Dùng thay đổi kiểu tóc', '', 35, 5, 0, 2, 1, 5),
	(158, 136, 'Tóc thời trang nữ \n - Dùng thay đổi kiểu tóc', '', 37, 5, 0, 2, 1, 5),
	(159, 138, 'Tóc thời trang nữ \n - Dùng thay đổi kiểu tóc', '', 39, 5, 0, 2, 1, 5),
	(160, 140, 'Tóc thời trang nữ \n - Dùng thay đổi kiểu tóc', '', 41, 5, 0, 2, 1, 5),
	(161, 131, 'Tóc thời trang nam \n - Dùng thay đổi kiểu tóc', '', 32, 5, 0, 2, 1, 5),
	(162, 133, 'Tóc thời trang nam \n - Dùng thay đổi kiểu tóc', '', 34, 5, 0, 2, 1, 5),
	(163, 137, 'Tóc thời trang nam \n - Dùng thay đổi kiểu tóc', '', 38, 5, 0, 2, 1, 5),
	(164, 139, 'Tóc thời trang nam \n - Dùng thay đổi kiểu tóc', '', 40, 5, 0, 2, 1, 5),
	(165, 250, 'Huyết linh thảo \n -Vật phẩm nuôi thú cưng', '', 1, 0, 0, 0, 0, 0),
	(166, 150, 'Sơn tinh \n -Tăng 50% băng-sét-độc-lửa lan, tăng công 10% , tăng 5.000 hp-mp, có hiệu ứng. Thời hạn 24h', '', 1, 3, 0, 0, 0, 2),
	(167, 150, 'Sơn tinh \n -Tăng 50% băng-sét-độc-lửa lan, tăng công 10% , tăng 5.000 hp-mp, có hiệu ứng. Thời hạn 24h', '', 1, 3000000, 1, 0, 0, 3000000),
	(168, 151, 'Thuỷ tinh \n -Tăng 50% băng-sét-độc-lửa lan, tăng công 10% , tăng 5.000 hp-mp, có hiệu ứng. Thời hạn 24h', '', 1, 3, 0, 0, 0, 2),
	(169, 151, 'Thuỷ tinh \n -Tăng 50% băng-sét-độc-lửa lan, tăng công 10% , tăng 5.000 hp-mp, có hiệu ứng. Thời hạn 24h', '', 1, 3000000, 1, 0, 0, 3000000),
	(170, 154, 'Tiểu Long Nữ \n -Tăng 50% băng-sét-độc-lửa lan, tăng công 10% , tăng 5.000 hp-mp, có hiệu ứng. Thời hạn 24h', '', 1, 3, 0, 0, 0, 3),
	(171, 155, 'Dương Quá\n -Tăng 50% băng-sét-độc-lửa lan, tăng công 10% , tăng 5.000 hp-mp, có hiệu ứng. Thời hạn 24h', '', 1, 3, 0, 0, 0, 3),
	(172, 63, 'Lá thư của Ngưu Lang', '', 10, 25, 0, 0, 0, 25),
	(173, 143, 'Lân sư tử', 'Vật phẩm sự kiện. Thời hạn 7 ngày', 1, 10000, 0, 0, 0, 10000),
	(174, 164, 'Tinh Quân \n - Tăng 50% băng-sét-độc-lửa lan, tăng công 10% , tăng 5.000 hp-mp.\n- Nguyệt ảnh gây st 10%-15% maxhp.\n- Tỷ lệ xh :10%\n- Thời hạn 7 ngày', '', 1, 1000, 0, 0, 0, 1000),
	(175, 165, 'Tiên Nữ \n - Tăng 50% băng-sét-độc-lửa lan, tăng công 10% , tăng 5.000 hp-mp.\n- Nguyệt ảnh gây st 10%-15% maxhp.\n- Tỷ lệ xh :10%\n- Thời hạn 7 ngày', '', 1, 1000, 0, 0, 0, 1000),
	(176, 191, 'Bản đồ khoá \n -Dùng vào Núi châu báu', '', 1, 100, 0, 2, 0, 100),
	(177, 214, 'Mảnh bản đồ 2 \n -Vật phẩm sự kiện', '', 10, 20, 0, 0, 0, 20),
	(178, 167, 'Áo dài nam \n -Cộng 10%tc 10%pt và 5000 HP MP.Tăng 100% băng lan, sét lan, độc lan, lửa lan. Thời hạn 7 ngày', '', 1, 70, 0, 0, 0, 70),
	(179, 168, 'áo dài nữ \n -Cộng 10%tc 10%pt và 5000 HP MP.Tăng 100% băng lan, sét lan, độc lan, lửa lan. Thời hạn 7 ngày', '', 1, 70, 0, 0, 0, 70);

-- Dumping structure for procedure kpah.DeleteFromMatchingTables
DELIMITER //
CREATE PROCEDURE `DeleteFromMatchingTables`()
BEGIN
  DECLARE done INT DEFAULT FALSE;
  DECLARE tbl_name VARCHAR(255);
  DECLARE cur CURSOR FOR
    SELECT table_name
    FROM information_schema.tables
    WHERE table_schema = 'gamedb7'
      AND (table_name LIKE '%2025%' OR table_name LIKE '%log%');

  DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;

  OPEN cur;

  read_loop: LOOP
    FETCH cur INTO tbl_name;
    IF done THEN
      LEAVE read_loop;
    END IF;

    SET @s = CONCAT('DELETE FROM `gamedb7`.`', tbl_name, '`');
    PREPARE stmt FROM @s;
    EXECUTE stmt;
    DEALLOCATE PREPARE stmt;
  END LOOP;

  CLOSE cur;
END//
DELIMITER ;

-- Dumping structure for procedure kpah.DropMatchingTables
DELIMITER //
CREATE PROCEDURE `DropMatchingTables`()
BEGIN
  DECLARE done INT DEFAULT FALSE;
  DECLARE tbl_name VARCHAR(255);
  DECLARE cur CURSOR FOR
    SELECT table_name
    FROM information_schema.tables
    WHERE table_schema = 'gamedb7'
      AND (table_name LIKE '%2025%' OR table_name LIKE '%log%');
  
  DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;

  OPEN cur;

  read_loop: LOOP
    FETCH cur INTO tbl_name;
    IF done THEN
      LEAVE read_loop;
    END IF;

    SET @s = CONCAT('DROP TABLE `gamedb7`.`', tbl_name, '`');
    PREPARE stmt FROM @s;
    EXECUTE stmt;
    DEALLOCATE PREPARE stmt;
  END LOOP;

  CLOSE cur;
END//
DELIMITER ;

-- Dumping structure for table kpah.even_online_game
CREATE TABLE IF NOT EXISTS `even_online_game` (
  `playerId` int(11) NOT NULL,
  `lastLoginTime` datetime DEFAULT NULL,
  `totalOnlineTimeInSeconds` bigint(20) DEFAULT '0',
  `lastResetTime` datetime DEFAULT NULL,
  `nhanQuaOnline` text,
  PRIMARY KEY (`playerId`),
  UNIQUE KEY `unique_player` (`playerId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Dumping data for table kpah.even_online_game: ~2 rows (approximately)
DELETE FROM `even_online_game`;
INSERT INTO `even_online_game` (`playerId`, `lastLoginTime`, `totalOnlineTimeInSeconds`, `lastResetTime`, `nhanQuaOnline`) VALUES
	(1, '2026-05-24 11:12:19', 2667, '2026-05-24 05:00:00', '0,0,0,0'),
	(2, '2026-05-25 08:31:25', 18, '2026-05-25 05:00:00', NULL);

-- Dumping structure for table kpah.giftcode
CREATE TABLE IF NOT EXISTS `giftcode` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `giftcode` varchar(255) NOT NULL,
  `xu` int(11) NOT NULL DEFAULT '0',
  `luong` int(11) NOT NULL DEFAULT '0',
  `luongLock` int(11) NOT NULL DEFAULT '0',
  `item` varchar(255) DEFAULT NULL,
  `expire` int(11) NOT NULL DEFAULT '0',
  `limit_use` int(11) NOT NULL DEFAULT '0',
  `type` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table kpah.giftcode: ~0 rows (approximately)
DELETE FROM `giftcode`;

-- Dumping structure for table kpah.giftcode_log
CREATE TABLE IF NOT EXISTS `giftcode_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `giftcode` text,
  `player` text,
  `item` text,
  `xu` int(11) DEFAULT '0',
  `luong` int(11) DEFAULT '0',
  `luongK` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Dumping data for table kpah.giftcode_log: ~0 rows (approximately)
DELETE FROM `giftcode_log`;

-- Dumping structure for table kpah.ngude_giftcode
CREATE TABLE IF NOT EXISTS `ngude_giftcode` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(255) NOT NULL,
  `type` varchar(255) NOT NULL DEFAULT 'dangky',
  `xu` int(11) NOT NULL DEFAULT '0',
  `luong` int(11) NOT NULL DEFAULT '0',
  `luongLock` int(11) NOT NULL DEFAULT '0',
  `item` varchar(255) DEFAULT NULL,
  `charname` varchar(255) NOT NULL,
  `status` int(11) NOT NULL DEFAULT '0',
  `level` int(11) DEFAULT '40',
  `userId` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table kpah.ngude_giftcode: ~0 rows (approximately)
DELETE FROM `ngude_giftcode`;

-- Dumping structure for table kpah.nin_xoso
CREATE TABLE IF NOT EXISTS `nin_xoso` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `charname` varchar(45) NOT NULL,
  `money` bigint(20) unsigned NOT NULL DEFAULT '0',
  `win` int(10) unsigned NOT NULL DEFAULT '0',
  `moneywin` bigint(20) unsigned NOT NULL DEFAULT '0',
  `moneyBid` bigint(20) unsigned NOT NULL DEFAULT '0',
  `moneyTax` bigint(20) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `Index_2` (`charname`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- Dumping data for table kpah.nin_xoso: 0 rows
DELETE FROM `nin_xoso`;
/*!40000 ALTER TABLE `nin_xoso` DISABLE KEYS */;
/*!40000 ALTER TABLE `nin_xoso` ENABLE KEYS */;

-- Dumping structure for table kpah.nin_xoso_low
CREATE TABLE IF NOT EXISTS `nin_xoso_low` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `charname` varchar(45) NOT NULL,
  `money` bigint(20) unsigned NOT NULL DEFAULT '0',
  `win` int(10) unsigned NOT NULL DEFAULT '0',
  `moneywin` bigint(20) unsigned NOT NULL DEFAULT '0',
  `moneyBid` bigint(20) unsigned NOT NULL DEFAULT '0',
  `moneyTax` bigint(20) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `Index_2` (`charname`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- Dumping data for table kpah.nin_xoso_low: 0 rows
DELETE FROM `nin_xoso_low`;
/*!40000 ALTER TABLE `nin_xoso_low` DISABLE KEYS */;
/*!40000 ALTER TABLE `nin_xoso_low` ENABLE KEYS */;

-- Dumping structure for table kpah.team_user
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
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- Dumping data for table kpah.team_user: 2 rows
DELETE FROM `team_user`;
/*!40000 ALTER TABLE `team_user` DISABLE KEYS */;
INSERT INTO `team_user` (`id`, `userid`, `provider`, `agent`, `newagent`, `newprovider`, `username`, `password`, `phone`, `regdate`, `ban`, `fromgame`, `email`) VALUES
	(1, 1, 0, -1, '0', '0', 'admin', '*6BB4837EB74329105EE4568DDA7DC67ED2CA2AD9', '', '2026-05-24 09:42:14', 0, 0, NULL),
	(2, 2, 0, -1, '0', '0', 'admin1312', '*AAD21AAA9498F7A37E7A641602A263186F7BCB89', '', '2026-05-25 08:31:06', 0, 0, NULL);
/*!40000 ALTER TABLE `team_user` ENABLE KEYS */;

-- Dumping structure for table kpah.tob_animal
CREATE TABLE IF NOT EXISTS `tob_animal` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `owner` int(10) unsigned NOT NULL,
  `att` varchar(500) NOT NULL,
  `place` int(10) unsigned NOT NULL DEFAULT '0',
  `id_img` int(10) unsigned NOT NULL,
  `lv` int(10) unsigned NOT NULL DEFAULT '1',
  `name` varchar(45) NOT NULL,
  `texpire` bigint(20) unsigned NOT NULL DEFAULT '0',
  `del` int(10) unsigned NOT NULL DEFAULT '0',
  `isHoaHinh` int(11) DEFAULT '0',
  `timeHoaHinh` bigint(20) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `Index_2` (`owner`),
  KEY `Index_4` (`place`),
  KEY `Index_5` (`id_img`),
  KEY `Index_6` (`lv`),
  KEY `Index_7` (`name`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 PACK_KEYS=1;

-- Dumping data for table kpah.tob_animal: 0 rows
DELETE FROM `tob_animal`;
/*!40000 ALTER TABLE `tob_animal` DISABLE KEYS */;
/*!40000 ALTER TABLE `tob_animal` ENABLE KEYS */;

-- Dumping structure for table kpah.tob_buy_ruong
CREATE TABLE IF NOT EXISTS `tob_buy_ruong` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `charname` varchar(100) NOT NULL,
  `dateLog` varchar(100) DEFAULT NULL,
  `timeBuy` datetime DEFAULT NULL,
  `soluong` int(10) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- Dumping data for table kpah.tob_buy_ruong: 0 rows
DELETE FROM `tob_buy_ruong`;
/*!40000 ALTER TABLE `tob_buy_ruong` DISABLE KEYS */;
/*!40000 ALTER TABLE `tob_buy_ruong` ENABLE KEYS */;

-- Dumping structure for table kpah.tob_char
CREATE TABLE IF NOT EXISTS `tob_char` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `charname` varchar(20) NOT NULL,
  `userId` int(11) NOT NULL,
  `xp` bigint(20) unsigned NOT NULL DEFAULT '0',
  `lastLv` int(10) unsigned NOT NULL DEFAULT '0',
  `hp` int(11) NOT NULL DEFAULT '0',
  `mp` int(11) NOT NULL DEFAULT '0',
  `map` int(11) NOT NULL DEFAULT '-1',
  `x` int(11) NOT NULL DEFAULT '0',
  `y` int(11) NOT NULL DEFAULT '0',
  `gold` bigint(20) unsigned NOT NULL DEFAULT '0',
  `headStyle` int(11) NOT NULL DEFAULT '0',
  `class` int(11) NOT NULL DEFAULT '0',
  `nHP1` int(11) NOT NULL DEFAULT '0',
  `nHP2` int(11) NOT NULL DEFAULT '0',
  `nHP3` int(11) NOT NULL DEFAULT '0',
  `nMP1` int(11) NOT NULL DEFAULT '0',
  `nMP2` int(11) NOT NULL DEFAULT '0',
  `nMP3` int(11) NOT NULL DEFAULT '0',
  `nAr1` int(11) NOT NULL DEFAULT '0',
  `nAr2` int(11) NOT NULL DEFAULT '0',
  `nAr3` int(11) NOT NULL DEFAULT '0',
  `skill1` int(11) NOT NULL DEFAULT '0',
  `skill2` int(11) NOT NULL DEFAULT '0',
  `skill3` int(11) NOT NULL DEFAULT '0',
  `skill4` int(11) NOT NULL DEFAULT '0',
  `skill5` int(11) NOT NULL DEFAULT '0',
  `strength` int(11) NOT NULL DEFAULT '0',
  `agitity` int(11) NOT NULL DEFAULT '0',
  `spirit` int(11) NOT NULL DEFAULT '0',
  `dexterity` int(11) NOT NULL DEFAULT '0',
  `luck` int(11) NOT NULL DEFAULT '0',
  `basepoint` int(11) NOT NULL DEFAULT '3',
  `skillpoint` int(11) NOT NULL DEFAULT '0',
  `itemq1` int(10) unsigned NOT NULL DEFAULT '0',
  `itemq2` int(10) unsigned NOT NULL DEFAULT '0',
  `itemq3` int(10) unsigned NOT NULL DEFAULT '0',
  `itemq4` int(10) unsigned NOT NULL DEFAULT '0',
  `skill6` int(10) unsigned NOT NULL DEFAULT '0',
  `skill7` int(10) unsigned NOT NULL DEFAULT '0',
  `skill8` int(10) unsigned NOT NULL DEFAULT '0',
  `skill9` int(10) unsigned NOT NULL DEFAULT '0',
  `skill10` int(10) unsigned NOT NULL DEFAULT '0',
  `skill11` int(10) unsigned NOT NULL DEFAULT '0',
  `skill12` int(10) unsigned NOT NULL DEFAULT '0',
  `skill13` int(10) unsigned NOT NULL DEFAULT '0',
  `skill14` int(10) unsigned NOT NULL DEFAULT '0',
  `skill15` int(10) unsigned NOT NULL DEFAULT '0',
  `killer` int(10) unsigned NOT NULL DEFAULT '0',
  `khan1` int(10) unsigned NOT NULL DEFAULT '0',
  `khan2` int(10) unsigned NOT NULL DEFAULT '0',
  `khan3` int(10) unsigned NOT NULL DEFAULT '0',
  `khan4` int(10) unsigned NOT NULL DEFAULT '0',
  `khan5` int(10) unsigned NOT NULL DEFAULT '0',
  `book` int(10) unsigned NOT NULL DEFAULT '0',
  `timeKiller` bigint(20) unsigned NOT NULL DEFAULT '0',
  `nPKill` int(10) unsigned NOT NULL DEFAULT '0',
  `gender` int(10) unsigned NOT NULL DEFAULT '0',
  `ticket` int(10) unsigned NOT NULL DEFAULT '0',
  `totalTimePlay` int(10) unsigned NOT NULL DEFAULT '0',
  `fullGoldTime` bigint(20) unsigned NOT NULL DEFAULT '0',
  `halfGoldTime` bigint(20) unsigned NOT NULL DEFAULT '0',
  `luong` int(10) unsigned NOT NULL DEFAULT '0',
  `isAdmin` int(10) unsigned NOT NULL DEFAULT '0',
  `nHP4` int(10) unsigned NOT NULL DEFAULT '0',
  `nHP5` int(10) unsigned NOT NULL DEFAULT '0',
  `nMP4` int(10) unsigned NOT NULL DEFAULT '0',
  `nMP5` int(10) unsigned NOT NULL DEFAULT '0',
  `potion` varchar(2000) DEFAULT NULL,
  `skill` varchar(2000) DEFAULT NULL,
  `basic` varchar(2000) DEFAULT NULL,
  `pInfo` varchar(2000) DEFAULT NULL,
  `del` int(10) unsigned NOT NULL DEFAULT '1',
  `tDelChar` bigint(20) unsigned NOT NULL DEFAULT '0',
  `blacklist` int(10) unsigned NOT NULL DEFAULT '0',
  `nhack` int(10) unsigned NOT NULL DEFAULT '0',
  `newiddb` int(10) unsigned NOT NULL DEFAULT '0',
  `newuserid` int(10) unsigned NOT NULL DEFAULT '0',
  `ban` int(10) unsigned NOT NULL DEFAULT '0',
  `idClan` int(11) NOT NULL DEFAULT '-1',
  `reupclass` int(10) unsigned NOT NULL DEFAULT '0',
  `lastLog` datetime NOT NULL DEFAULT '2010-01-01 00:00:00',
  `infotop` varchar(2000) NOT NULL DEFAULT '-1',
  `itemquest` varchar(200) DEFAULT NULL,
  `allitem` text,
  `equip` text,
  `inven` text,
  `bag` text,
  `killme` varchar(600) DEFAULT NULL,
  `luonglock` int(10) unsigned DEFAULT '0',
  `idserver` int(10) unsigned NOT NULL DEFAULT '0',
  `tuido` text,
  `tichluy` int(11) DEFAULT '0',
  `tichluy_bosung` int(11) NOT NULL DEFAULT '0',
  `tichluy_tuan` int(11) DEFAULT '0',
  `topNap` int(11) NOT NULL DEFAULT '0',
  `qua_npc` int(11) DEFAULT '0',
  `isPP` int(11) DEFAULT '0',
  `isMN` int(11) NOT NULL DEFAULT '0',
  `diemluong` int(11) DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `charname` (`charname`),
  KEY `Index_3` (`userId`),
  KEY `Index_6` (`idClan`),
  KEY `Index_5` (`pInfo`(333)),
  KEY `Index_7` (`gold`),
  KEY `Index_8` (`xp`),
  KEY `Index_9` (`ticket`),
  KEY `Index_10` (`basepoint`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- Dumping data for table kpah.tob_char: 2 rows
DELETE FROM `tob_char`;
/*!40000 ALTER TABLE `tob_char` DISABLE KEYS */;
INSERT INTO `tob_char` (`id`, `charname`, `userId`, `xp`, `lastLv`, `hp`, `mp`, `map`, `x`, `y`, `gold`, `headStyle`, `class`, `nHP1`, `nHP2`, `nHP3`, `nMP1`, `nMP2`, `nMP3`, `nAr1`, `nAr2`, `nAr3`, `skill1`, `skill2`, `skill3`, `skill4`, `skill5`, `strength`, `agitity`, `spirit`, `dexterity`, `luck`, `basepoint`, `skillpoint`, `itemq1`, `itemq2`, `itemq3`, `itemq4`, `skill6`, `skill7`, `skill8`, `skill9`, `skill10`, `skill11`, `skill12`, `skill13`, `skill14`, `skill15`, `killer`, `khan1`, `khan2`, `khan3`, `khan4`, `khan5`, `book`, `timeKiller`, `nPKill`, `gender`, `ticket`, `totalTimePlay`, `fullGoldTime`, `halfGoldTime`, `luong`, `isAdmin`, `nHP4`, `nHP5`, `nMP4`, `nMP5`, `potion`, `skill`, `basic`, `pInfo`, `del`, `tDelChar`, `blacklist`, `nhack`, `newiddb`, `newuserid`, `ban`, `idClan`, `reupclass`, `lastLog`, `infotop`, `itemquest`, `allitem`, `equip`, `inven`, `bag`, `killme`, `luonglock`, `idserver`, `tuido`, `tichluy`, `tichluy_bosung`, `tichluy_tuan`, `topNap`, `qua_npc`, `isPP`, `isMN`, `diemluong`) VALUES
	(1, 'boysida', 1, 508785, 15, 34, 0, -1, 0, 0, 102776, 2, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 42, 0, 0, 4976, 0, 0, 0, 0, 0, '102776,5,0,0,5,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,100,0,100,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,0,0,0,0,5,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0', '1,0,0,0,0,0,0,-1,-1,-1,-1,-1,-1,-1,-1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0', '44,34,24,34,24,70,14', '2,1,boysida,1,508785,15,2380,480,342,389,188,0,0,0,0,0,0,0,1,1,-5,0,0,-1,0,1,0,3,1,1,0,0,260424,1,1,1,0,15503,64,0,0,0,0,2026-05-24,0,0,0,0,1,0,-1,,0,,0,200,-1,1,9,100,100,14,0,1,0,0,-1,1779593124943,0,4976,0,3,3,3,0,1,0,0,-1,0,0,0,0,0,45,0,0,0,0,0,0,0,,0,0,0,0,0,0,0,0,0,0,-1,26,0,-1,1,0,0,0,0', 1, 0, 0, 0, 0, 0, 0, -1, 1, '2026-05-24 11:15:24', '0,2,1,1,0,28,1,1,0,86,1,1,0', NULL, NULL, '1,2,1,200,2000,0,1,0,0,2,0,0,-1,-1,0,,-1,0,0,2026-05-24 09:43:52,-1,0,0,-1,0,-1,0,0,-1,1,0,5,0|0,15,1,1,0,0,15,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0>1,28,1,200,2000,0,1,0,0,2,0,0,-1,-1,0,,-1,0,0,2026-05-24 09:43:52,-1,0,0,-1,0,-1,0,0,-1,1,0,5,0|0,10,0,0,0,0,10,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0>1,86,1,400,3998,0,1,0,0,2,0,0,-1,-1,0,,-1,0,0,2026-05-24 09:43:52,-1,0,0,-1,0,-1,0,0,-1,1,0,5,0|50,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0', '', '', '', 4976, 0, '', 0, 0, 0, 0, 0, 0, 0, 0),
	(2, 'ascascac', 2, 0, 1, 25, 0, -1, 0, 0, 100000, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, '100000,1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0', '0,0,0,0,0,0,0,-1,-1,-1,-1,-1,-1,-1,-1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0', '25,20,10,25,10,0,0', '0,2,ascascac,0,0,1,2000,200,1701,533,149,0,0,0,0,0,0,0,1,1,-5,0,0,-1,0,1,0,1,1,1,0,0,260425,1,1,1,0,15503,64,0,0,0,0,2026-05-25,0,0,0,0,1,0,-1,,0,,0,200,-1,1,0,100,100,14,0,1,0,0,-1,1779673303211,0,0,0,3,3,3,0,1,0,0,-1,0,0,0,0,0,45,0,0,0,0,0,0,0,,0,0,0,0,0,0,0,0,0,0,-1,0,0,-1,0,0,0,1,0', 1, 0, 0, 0, 0, 0, 0, -1, 1, '2026-05-25 08:31:43', '0,1,0,1,0,27,0,1,0,79,0,1,0', NULL, NULL, '0,1,1,200,2000,0,1,0,0,2,0,0,-1,-1,0,,-1,0,0,2026-05-25 08:31:23,-1,0,0,-1,0,-1,0,0,-1,1,0,5,0|0,12,1,1,0,0,12,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0>0,27,1,200,2000,0,1,0,0,2,0,0,-1,-1,0,,-1,0,0,2026-05-25 08:31:23,-1,0,0,-1,0,-1,0,0,-1,1,0,5,0|0,8,0,0,0,0,8,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0>0,79,1,500,5000,0,1,0,0,2,0,0,-1,-1,0,,-1,0,0,2026-05-25 08:31:23,-1,0,0,-1,0,-1,0,0,-1,1,0,5,0|30,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0', '', '', '', 0, 0, '', 0, 0, 0, 0, 0, 0, 0, 0);
/*!40000 ALTER TABLE `tob_char` ENABLE KEYS */;

-- Dumping structure for table kpah.tob_char_fruit
CREATE TABLE IF NOT EXISTS `tob_char_fruit` (
  `id_char` int(10) unsigned NOT NULL,
  `listidfruit` varchar(200) NOT NULL,
  `stFruit` varchar(100) NOT NULL,
  `tFruit` varchar(500) NOT NULL,
  `gift` varchar(500) NOT NULL COMMENT 'vi tri ve tren cay',
  `theo` varchar(500) NOT NULL,
  `tkho` varchar(500) NOT NULL,
  PRIMARY KEY (`id_char`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- Dumping data for table kpah.tob_char_fruit: 0 rows
DELETE FROM `tob_char_fruit`;
/*!40000 ALTER TABLE `tob_char_fruit` DISABLE KEYS */;
/*!40000 ALTER TABLE `tob_char_fruit` ENABLE KEYS */;

-- Dumping structure for table kpah.tob_char_luong
CREATE TABLE IF NOT EXISTS `tob_char_luong` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `charname` varchar(45) NOT NULL,
  `country` tinyint(4) NOT NULL DEFAULT '-1',
  `exp` bigint(20) unsigned NOT NULL DEFAULT '0',
  `idclan` int(11) NOT NULL DEFAULT '-1',
  `honor` int(10) unsigned NOT NULL,
  `lv` int(10) unsigned NOT NULL DEFAULT '1',
  `nPK` bigint(20) unsigned NOT NULL DEFAULT '0',
  `wearing` varchar(500) NOT NULL DEFAULT '""',
  `luong` int(10) unsigned NOT NULL DEFAULT '0',
  `gold` bigint(20) unsigned NOT NULL DEFAULT '0',
  `headStyle` int(10) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `charname` (`charname`) USING BTREE,
  KEY `country` (`country`),
  KEY `exp` (`exp`),
  KEY `Index_5` (`honor`),
  KEY `Index_6` (`nPK`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- Dumping data for table kpah.tob_char_luong: 2 rows
DELETE FROM `tob_char_luong`;
/*!40000 ALTER TABLE `tob_char_luong` DISABLE KEYS */;
INSERT INTO `tob_char_luong` (`id`, `charname`, `country`, `exp`, `idclan`, `honor`, `lv`, `nPK`, `wearing`, `luong`, `gold`, `headStyle`) VALUES
	(1, 'boysida', 0, 508785, -1, 0, 15, 0, '0,2,1,1,0,28,1,1,0,86,1,1,0', 0, 102776, 2),
	(2, 'ascascac', 0, 0, -1, 0, 1, 0, '0,1,0,1,0,27,0,1,0,79,0,1,0', 0, 0, 100000);
/*!40000 ALTER TABLE `tob_char_luong` ENABLE KEYS */;

-- Dumping structure for table kpah.tob_char_quest
CREATE TABLE IF NOT EXISTS `tob_char_quest` (
  `id_char` int(10) unsigned NOT NULL,
  `itemget` varchar(200) NOT NULL COMMENT 'cac loai item cach nhau = dau , item cua cac nhiem vu khac nhau cach nhau bang dau |',
  `monskilled` varchar(200) NOT NULL COMMENT 'so luong quai da giet',
  `isfinish` varchar(200) NOT NULL COMMENT 'da hoan thanh nhiem vu hay chua',
  `idsubRcv` varchar(200) DEFAULT NULL,
  `itemquest` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id_char`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- Dumping data for table kpah.tob_char_quest: 2 rows
DELETE FROM `tob_char_quest`;
/*!40000 ALTER TABLE `tob_char_quest` DISABLE KEYS */;
INSERT INTO `tob_char_quest` (`id_char`, `itemget`, `monskilled`, `isfinish`, `idsubRcv`, `itemquest`) VALUES
	(1, '0', '20', '9,1,0', NULL, '0,0,0,0,0,0,0,0,0,0'),
	(2, '0', '0', '0,0,0', NULL, '0,0,0,0,0,0,0,0,0,0');
/*!40000 ALTER TABLE `tob_char_quest` ENABLE KEYS */;

-- Dumping structure for table kpah.tob_char_thach_dau
CREATE TABLE IF NOT EXISTS `tob_char_thach_dau` (
  `id` int(10) unsigned NOT NULL,
  `charname` varchar(45) NOT NULL,
  `point` int(10) unsigned NOT NULL DEFAULT '0',
  `lv` int(10) unsigned NOT NULL DEFAULT '1',
  `timeupdate` datetime NOT NULL,
  `timeReg` datetime NOT NULL,
  `nhom` int(10) unsigned NOT NULL DEFAULT '0',
  `pointrank` int(10) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `Index_2` (`charname`),
  KEY `Index_3` (`point`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- Dumping data for table kpah.tob_char_thach_dau: 0 rows
DELETE FROM `tob_char_thach_dau`;
/*!40000 ALTER TABLE `tob_char_thach_dau` DISABLE KEYS */;
/*!40000 ALTER TABLE `tob_char_thach_dau` ENABLE KEYS */;

-- Dumping structure for table kpah.tob_char_tubinh
CREATE TABLE IF NOT EXISTS `tob_char_tubinh` (
  `id_char` int(10) unsigned NOT NULL,
  `listid` varchar(200) NOT NULL,
  `listmanh` varchar(200) NOT NULL,
  PRIMARY KEY (`id_char`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- Dumping data for table kpah.tob_char_tubinh: 0 rows
DELETE FROM `tob_char_tubinh`;
/*!40000 ALTER TABLE `tob_char_tubinh` DISABLE KEYS */;
/*!40000 ALTER TABLE `tob_char_tubinh` ENABLE KEYS */;

-- Dumping structure for table kpah.tob_char_vip
CREATE TABLE IF NOT EXISTS `tob_char_vip` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `charname` varchar(100) NOT NULL,
  `vip` int(10) unsigned DEFAULT '0',
  `timeRcv` datetime NOT NULL,
  `reset` int(10) unsigned DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- Dumping data for table kpah.tob_char_vip: 1 rows
DELETE FROM `tob_char_vip`;
/*!40000 ALTER TABLE `tob_char_vip` DISABLE KEYS */;
INSERT INTO `tob_char_vip` (`id`, `charname`, `vip`, `timeRcv`, `reset`) VALUES
	(1, 'boysida', 1, '2026-05-24 10:24:52', 0);
/*!40000 ALTER TABLE `tob_char_vip` ENABLE KEYS */;

-- Dumping structure for table kpah.tob_chiemthanh
CREATE TABLE IF NOT EXISTS `tob_chiemthanh` (
  `clan` varchar(255) NOT NULL,
  `bangchu` varchar(255) NOT NULL,
  `win` int(11) NOT NULL DEFAULT '0',
  `xu` varchar(255) NOT NULL DEFAULT '0',
  PRIMARY KEY (`clan`,`bangchu`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Dumping data for table kpah.tob_chiemthanh: ~0 rows (approximately)
DELETE FROM `tob_chiemthanh`;

-- Dumping structure for table kpah.tob_chien_truong
CREATE TABLE IF NOT EXISTS `tob_chien_truong` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `charname` varchar(45) NOT NULL,
  `sttru` bigint(20) unsigned NOT NULL DEFAULT '0',
  `lientram` int(10) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `Index_2` (`charname`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- Dumping data for table kpah.tob_chien_truong: 0 rows
DELETE FROM `tob_chien_truong`;
/*!40000 ALTER TABLE `tob_chien_truong` DISABLE KEYS */;
/*!40000 ALTER TABLE `tob_chien_truong` ENABLE KEYS */;

-- Dumping structure for table kpah.tob_clan
CREATE TABLE IF NOT EXISTS `tob_clan` (
  `id_clan` int(10) unsigned NOT NULL,
  `clanName` varchar(45) DEFAULT NULL,
  `charMaster` varchar(45) DEFAULT NULL,
  `lvClan` int(10) unsigned DEFAULT NULL,
  `member` varchar(8000) DEFAULT NULL,
  `money` bigint(20) unsigned DEFAULT NULL,
  `xp` bigint(20) unsigned DEFAULT NULL,
  `datecreate` datetime NOT NULL,
  `slogan` varchar(300) NOT NULL,
  `isdel` int(10) unsigned NOT NULL DEFAULT '0',
  `timedel` bigint(20) unsigned NOT NULL DEFAULT '0',
  `town` int(10) unsigned NOT NULL DEFAULT '0',
  `tax` int(10) unsigned NOT NULL DEFAULT '0',
  `devote` int(10) unsigned NOT NULL DEFAULT '0',
  `otherinfo` varchar(800) NOT NULL DEFAULT '""',
  `skillclan` varchar(800) NOT NULL DEFAULT '""',
  `country` int(11) NOT NULL DEFAULT '-1',
  `tcheckdel` bigint(20) unsigned NOT NULL DEFAULT '0',
  `id_cu` int(10) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id_clan`) USING BTREE,
  KEY `Index_2` (`lvClan`),
  KEY `Index_3` (`clanName`),
  KEY `Index_4` (`charMaster`),
  KEY `Index_5` (`money`),
  KEY `Index_6` (`xp`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- Dumping data for table kpah.tob_clan: 0 rows
DELETE FROM `tob_clan`;
/*!40000 ALTER TABLE `tob_clan` DISABLE KEYS */;
/*!40000 ALTER TABLE `tob_clan` ENABLE KEYS */;

-- Dumping structure for table kpah.tob_clan_msg
CREATE TABLE IF NOT EXISTS `tob_clan_msg` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `charname` varchar(45) NOT NULL,
  `info` varchar(1000) NOT NULL,
  `datesend` datetime NOT NULL,
  `id_clan` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `Index_2` (`charname`),
  KEY `Index_3` (`id_clan`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- Dumping data for table kpah.tob_clan_msg: 0 rows
DELETE FROM `tob_clan_msg`;
/*!40000 ALTER TABLE `tob_clan_msg` DISABLE KEYS */;
/*!40000 ALTER TABLE `tob_clan_msg` ENABLE KEYS */;

-- Dumping structure for table kpah.tob_code_lixi
CREATE TABLE IF NOT EXISTS `tob_code_lixi` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `code` varchar(100) NOT NULL,
  `nhan` int(10) unsigned DEFAULT '0',
  `timeNhan` datetime DEFAULT NULL,
  `charnhan` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `Index_2` (`code`) USING BTREE,
  KEY `Index_3` (`charnhan`),
  KEY `Index_4` (`nhan`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- Dumping data for table kpah.tob_code_lixi: 0 rows
DELETE FROM `tob_code_lixi`;
/*!40000 ALTER TABLE `tob_code_lixi` DISABLE KEYS */;
/*!40000 ALTER TABLE `tob_code_lixi` ENABLE KEYS */;

-- Dumping structure for table kpah.tob_danh_hieu
CREATE TABLE IF NOT EXISTS `tob_danh_hieu` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `charname` varchar(80) NOT NULL,
  `iddh` text,
  PRIMARY KEY (`id`),
  UNIQUE KEY `Index_2` (`charname`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- Dumping data for table kpah.tob_danh_hieu: 0 rows
DELETE FROM `tob_danh_hieu`;
/*!40000 ALTER TABLE `tob_danh_hieu` DISABLE KEYS */;
/*!40000 ALTER TABLE `tob_danh_hieu` ENABLE KEYS */;

-- Dumping structure for table kpah.tob_dau_gia
CREATE TABLE IF NOT EXISTS `tob_dau_gia` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `charname` varchar(100) NOT NULL,
  `luongbid` int(10) unsigned NOT NULL DEFAULT '0',
  `luongcoc` int(10) unsigned NOT NULL DEFAULT '0',
  `timebid` datetime DEFAULT NULL,
  `hang` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '0 la chua xep hang, 1 la dc quyen mua, 2 la nhan lai tien',
  PRIMARY KEY (`id`),
  UNIQUE KEY `Index_2` (`charname`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- Dumping data for table kpah.tob_dau_gia: 0 rows
DELETE FROM `tob_dau_gia`;
/*!40000 ALTER TABLE `tob_dau_gia` DISABLE KEYS */;
/*!40000 ALTER TABLE `tob_dau_gia` ENABLE KEYS */;

-- Dumping structure for table kpah.tob_dau_gia2
CREATE TABLE IF NOT EXISTS `tob_dau_gia2` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `charname` varchar(100) NOT NULL,
  `luongbid` int(10) unsigned NOT NULL DEFAULT '0',
  `luongcoc` int(10) unsigned NOT NULL DEFAULT '0',
  `timebid` datetime DEFAULT NULL,
  `hang` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '0 la chua xep hang, 1 la dc quyen mua, 2 la nhan lai tien',
  PRIMARY KEY (`id`),
  UNIQUE KEY `Index_2` (`charname`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- Dumping data for table kpah.tob_dau_gia2: 0 rows
DELETE FROM `tob_dau_gia2`;
/*!40000 ALTER TABLE `tob_dau_gia2` DISABLE KEYS */;
/*!40000 ALTER TABLE `tob_dau_gia2` ENABLE KEYS */;

-- Dumping structure for table kpah.tob_event
CREATE TABLE IF NOT EXISTS `tob_event` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `part1` varchar(450) NOT NULL,
  `part2` varchar(450) NOT NULL,
  `part3` varchar(850) NOT NULL,
  `part4` varchar(450) NOT NULL,
  `shield` varchar(45) NOT NULL DEFAULT '0,0,0,0,0,0,0,0,0,0,0,0',
  `gov1` varchar(200) NOT NULL DEFAULT '""',
  `gov2` varchar(200) NOT NULL DEFAULT '""',
  `gov3` varchar(200) NOT NULL DEFAULT '""',
  `quayso` varchar(200) NOT NULL,
  `topluong` varchar(500) NOT NULL DEFAULT '0',
  `topNapLuong` varchar(500) DEFAULT NULL,
  `phaohoa` varchar(100) NOT NULL DEFAULT '0,0',
  `iditemsell` int(10) unsigned NOT NULL DEFAULT '0',
  `openphuonghoang` varchar(100) NOT NULL DEFAULT '5000,5,0,0',
  `trung` varchar(45) NOT NULL DEFAULT '0|10000|0',
  `ruongmm` varchar(200) DEFAULT NULL,
  `lixi` varchar(100) DEFAULT '100,18,30',
  `vip` varchar(100) DEFAULT '500,100,5',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- Dumping data for table kpah.tob_event: 1 rows
DELETE FROM `tob_event`;
/*!40000 ALTER TABLE `tob_event` DISABLE KEYS */;
INSERT INTO `tob_event` (`id`, `part1`, `part2`, `part3`, `part4`, `shield`, `gov1`, `gov2`, `gov3`, `quayso`, `topluong`, `topNapLuong`, `phaohoa`, `iditemsell`, `openphuonghoang`, `trung`, `ruongmm`, `lixi`, `vip`) VALUES
	(1, '0,0,0,0,0,0,0,0,0,0', '0,0,0,0,0,0,0,0', ',-1,-1,,-1,-1,,-1,-1,-1,-1,-1,0,0,0,3,3,3,0,0,0,0,0,0,0,0,0', '0,7,0,-1', '0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2026-05-25', ',,,,,,', ',,,,,,', ',,,,,,', '2026-05-25,0,0,0,5000', '0', ',0', '0,0', 0, '10000,0,0,0', '0|5300|4067', '2,20,20,20,20,3,3,5|2,5,5,5,5,3,3,5|1580144452356|300|242', '100,18,30', '500,100,5');
/*!40000 ALTER TABLE `tob_event` ENABLE KEYS */;

-- Dumping structure for table kpah.tob_event_top
CREATE TABLE IF NOT EXISTS `tob_event_top` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `charname` varchar(100) NOT NULL,
  `luongnap` bigint(20) unsigned NOT NULL DEFAULT '0',
  `toppoint` bigint(20) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `Index_2` (`charname`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- Dumping data for table kpah.tob_event_top: 0 rows
DELETE FROM `tob_event_top`;
/*!40000 ALTER TABLE `tob_event_top` DISABLE KEYS */;
/*!40000 ALTER TABLE `tob_event_top` ENABLE KEYS */;

-- Dumping structure for table kpah.tob_farm
CREATE TABLE IF NOT EXISTS `tob_farm` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `owner` int(11) NOT NULL,
  `farmId` tinyint(4) NOT NULL,
  `treeId` int(11) NOT NULL DEFAULT '-1',
  `timeBegin` bigint(20) unsigned NOT NULL DEFAULT '0',
  `lv` tinyint(4) NOT NULL,
  `buy` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `lvPlot` tinyint(3) unsigned NOT NULL DEFAULT '1',
  `rank` tinyint(3) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `Index_2` (`treeId`),
  KEY `Index_3` (`lv`) USING BTREE,
  KEY `Index_4` (`lvPlot`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- Dumping data for table kpah.tob_farm: 0 rows
DELETE FROM `tob_farm`;
/*!40000 ALTER TABLE `tob_farm` DISABLE KEYS */;
/*!40000 ALTER TABLE `tob_farm` ENABLE KEYS */;

-- Dumping structure for table kpah.tob_friendlist
CREATE TABLE IF NOT EXISTS `tob_friendlist` (
  `charId` int(11) NOT NULL,
  `friendId` varchar(200) NOT NULL,
  `name` varchar(1000) NOT NULL,
  `ischange` int(10) unsigned NOT NULL DEFAULT '1',
  PRIMARY KEY (`charId`) USING BTREE
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- Dumping data for table kpah.tob_friendlist: 0 rows
DELETE FROM `tob_friendlist`;
/*!40000 ALTER TABLE `tob_friendlist` DISABLE KEYS */;
/*!40000 ALTER TABLE `tob_friendlist` ENABLE KEYS */;

-- Dumping structure for table kpah.tob_fruit
CREATE TABLE IF NOT EXISTS `tob_fruit` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `id1` int(10) unsigned DEFAULT NULL,
  `id2` int(10) unsigned DEFAULT NULL,
  `id3` int(10) unsigned DEFAULT NULL,
  `id4` int(10) unsigned DEFAULT NULL,
  `id5` int(10) unsigned DEFAULT NULL,
  `decript` varchar(200) DEFAULT NULL,
  `time1` int(10) unsigned DEFAULT NULL,
  `time2` int(10) unsigned DEFAULT NULL,
  `time3` int(10) unsigned DEFAULT NULL,
  `time4` int(10) unsigned DEFAULT NULL,
  `time5` int(10) unsigned DEFAULT NULL,
  `tfruit` int(10) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- Dumping data for table kpah.tob_fruit: 7 rows
DELETE FROM `tob_fruit`;
/*!40000 ALTER TABLE `tob_fruit` DISABLE KEYS */;
INSERT INTO `tob_fruit` (`id`, `name`, `id1`, `id2`, `id3`, `id4`, `id5`, `decript`, `time1`, `time2`, `time3`, `time4`, `time5`, `tfruit`) VALUES
	(2, 'Quả lkd', 9, 10, NULL, NULL, NULL, 'Quả lkd', 360, 120, 72, NULL, NULL, 5),
	(3, 'Quả bạc', 2, 4, NULL, NULL, NULL, 'Quả bạc', 4320, 0, 864, NULL, NULL, 2),
	(4, 'Quả nguyên liệu', 0, 1, NULL, NULL, NULL, 'Quả nguyên liệu', 480, 120, 96, NULL, NULL, 3),
	(5, 'Quả may mắn', 7, 8, NULL, NULL, NULL, 'Quả may mắn', 360, 120, 72, NULL, NULL, 4),
	(6, 'Quả vàng', 2, 3, NULL, NULL, NULL, 'Quả vàng', 10080, 0, 2016, NULL, NULL, 1),
	(7, 'Quả ngũ hợp', 11, 12, NULL, NULL, NULL, 'Quả ngũ hợp', 360, 120, 72, NULL, NULL, 6),
	(8, 'Quả exp', 5, 6, NULL, NULL, NULL, 'Quả exp', 360, 120, 72, NULL, NULL, 0);
/*!40000 ALTER TABLE `tob_fruit` ENABLE KEYS */;

-- Dumping structure for table kpah.tob_gem_new
CREATE TABLE IF NOT EXISTS `tob_gem_new` (
  `owner` int(10) unsigned NOT NULL,
  `listtemplate` varchar(1000) NOT NULL,
  `soluong` varchar(1000) NOT NULL,
  `slock` varchar(1000) NOT NULL DEFAULT '0',
  PRIMARY KEY (`owner`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- Dumping data for table kpah.tob_gem_new: 2 rows
DELETE FROM `tob_gem_new`;
/*!40000 ALTER TABLE `tob_gem_new` DISABLE KEYS */;
INSERT INTO `tob_gem_new` (`owner`, `listtemplate`, `soluong`, `slock`) VALUES
	(1, '0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59,60,61,62,63,64,65,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,86,87,88,89,90,91,92,93,94,95,96,97,98,99,100,101,102,103,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,134,135,136,137,138,139,140,141,142,143,144,145,146,147,148,149,150,151,152,153,154,155,156,157,158,159,160,161,162,163,164,165,166,167,168,169,170,171,172,173,174,175,176,177,178,179,180,181,182,183,184,185,186,187,188,189,190,191,192,193,194,195,196,197,198,199,200,201,202,203,204,205,206,207,208,209,210,211,212,213,214,215,216,217,218,219,220,221,222,223,224,225,226,227,228,229,230,231,232,233,234,235,236,237,238,239,240,241,242,243,244,245,246,247,248,249,250,251,252,253,254,255,256,257,258,259,260,261,262,263,264,265,266,267,268', '0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0', '0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0'),
	(2, '0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59,60,61,62,63,64,65,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,86,87,88,89,90,91,92,93,94,95,96,97,98,99,100,101,102,103,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,134,135,136,137,138,139,140,141,142,143,144,145,146,147,148,149,150,151,152,153,154,155,156,157,158,159,160,161,162,163,164,165,166,167,168,169,170,171,172,173,174,175,176,177,178,179,180,181,182,183,184,185,186,187,188,189,190,191,192,193,194,195,196,197,198,199,200,201,202,203,204,205,206,207,208,209,210,211,212,213,214,215,216,217,218,219,220,221,222,223,224,225,226,227,228,229,230,231,232,233,234,235,236,237,238,239,240,241,242,243,244,245,246,247,248,249,250,251,252,253,254,255,256,257,258,259,260,261,262,263,264,265,266,267,268', '0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0', '0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0');
/*!40000 ALTER TABLE `tob_gem_new` ENABLE KEYS */;

-- Dumping structure for table kpah.tob_get_board_nap
CREATE TABLE IF NOT EXISTS `tob_get_board_nap` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `charname` varchar(45) NOT NULL,
  `info` varchar(45) NOT NULL,
  `dateGet` varchar(100) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `Index_2` (`charname`) USING BTREE
) ENGINE=MyISAM DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- Dumping data for table kpah.tob_get_board_nap: 0 rows
DELETE FROM `tob_get_board_nap`;
/*!40000 ALTER TABLE `tob_get_board_nap` DISABLE KEYS */;
/*!40000 ALTER TABLE `tob_get_board_nap` ENABLE KEYS */;

-- Dumping structure for table kpah.tob_inbox
CREATE TABLE IF NOT EXISTS `tob_inbox` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `charname` varchar(45) NOT NULL,
  `sender` varchar(45) NOT NULL,
  `info` varchar(800) NOT NULL,
  `dateSend` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `Index_2` (`charname`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 ROW_FORMAT=FIXED;

-- Dumping data for table kpah.tob_inbox: 0 rows
DELETE FROM `tob_inbox`;
/*!40000 ALTER TABLE `tob_inbox` DISABLE KEYS */;
/*!40000 ALTER TABLE `tob_inbox` ENABLE KEYS */;

-- Dumping structure for table kpah.tob_log_all_gem
CREATE TABLE IF NOT EXISTS `tob_log_all_gem` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `charname` varchar(45) NOT NULL,
  `aclog` varchar(45) NOT NULL DEFAULT '""',
  `info` varchar(8000) NOT NULL DEFAULT '""',
  `timelog` datetime NOT NULL,
  `charname2` varchar(45) NOT NULL DEFAULT '""',
  PRIMARY KEY (`id`),
  KEY `charname` (`charname`),
  KEY `aclog` (`aclog`),
  KEY `timelog` (`timelog`),
  KEY `charname2` (`charname2`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- Dumping data for table kpah.tob_log_all_gem: 0 rows
DELETE FROM `tob_log_all_gem`;
/*!40000 ALTER TABLE `tob_log_all_gem` DISABLE KEYS */;
/*!40000 ALTER TABLE `tob_log_all_gem` ENABLE KEYS */;

-- Dumping structure for table kpah.tob_log_all_item
CREATE TABLE IF NOT EXISTS `tob_log_all_item` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `charname` varchar(45) NOT NULL,
  `aclog` varchar(45) NOT NULL DEFAULT '""',
  `info` varchar(8000) NOT NULL DEFAULT '""',
  `timelog` datetime NOT NULL,
  `charname2` varchar(45) NOT NULL DEFAULT '""',
  `idchar` int(10) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `charname` (`charname`),
  KEY `aclog` (`aclog`),
  KEY `timelog` (`timelog`),
  KEY `charname2` (`charname2`),
  KEY `idchar` (`idchar`)
) ENGINE=MyISAM AUTO_INCREMENT=45 DEFAULT CHARSET=utf8;

-- Dumping data for table kpah.tob_log_all_item: 44 rows
DELETE FROM `tob_log_all_item`;
/*!40000 ALTER TABLE `tob_log_all_item` DISABLE KEYS */;
INSERT INTO `tob_log_all_item` (`id`, `charname`, `aclog`, `info`, `timelog`, `charname2`, `idchar`) VALUES
	(1, 'admin', 'idruongmm', 'id ruong trung 243', '2026-05-24 09:39:17', '""', 0),
	(2, 'boysida', 'lenlv', 'char initpinfo olv:0 oexp: 0 >> lv: 1 exp: 0 >> addthem: 0', '2026-05-24 09:43:54', '""', 0),
	(3, 'boysida', 'lenlv', 'addxpcharevent map olv:1 oexp: 0 >> lv: 2 exp: 505 >> addthem: 505', '2026-05-24 09:44:11', '""', 0),
	(4, 'boysida', 'lenlevel', '1_2 len lv:  xp cu: 505 >> xpmoi: 505 >> expadd: 500 > vi tri set: infoquesttemplate', '2026-05-24 09:44:11', '""', 0),
	(5, 'boysida', 'nhanxu', '101000 nhan 1000 tu vi tri addgift infoquesttemplate', '2026-05-24 09:44:11', '""', 0),
	(6, 'boysida', 'lenlv', 'addxpcharevent map olv:2 oexp: 505 >> lv: 3 exp: 1010 >> addthem: 1010', '2026-05-24 09:44:22', '""', 0),
	(7, 'boysida', 'lenlevel', '2_3 len lv:  xp cu: 1010 >> xpmoi: 1010 >> expadd: 500 > vi tri set: infoquesttemplate', '2026-05-24 09:44:22', '""', 0),
	(8, 'boysida', 'nhanxu', '101100 nhan 100 tu vi tri addgift infoquesttemplate', '2026-05-24 09:44:22', '""', 0),
	(9, 'boysida', 'nhanxu', '101200 nhan 100 tu vi tri addgift infoquesttemplate', '2026-05-24 09:44:38', '""', 0),
	(10, 'boysida', 'lenlv', 'addxpcharevent map olv:3 oexp: 1515 >> lv: 4 exp: 2020 >> addthem: 2020', '2026-05-24 09:44:51', '""', 0),
	(11, 'boysida', 'lenlevel', '3_4 len lv:  xp cu: 2020 >> xpmoi: 2020 >> expadd: 500 > vi tri set: infoquesttemplate', '2026-05-24 09:44:51', '""', 0),
	(12, 'boysida', 'nhanxu', '101300 nhan 100 tu vi tri addgift infoquesttemplate', '2026-05-24 09:44:51', '""', 0),
	(13, 'boysida', 'nhanxu', '101400 nhan 100 tu vi tri addgift infoquesttemplate', '2026-05-24 09:46:02', '""', 0),
	(14, 'boysida', 'nhanxu', '101500 nhan 100 tu vi tri addgift infoquesttemplate', '2026-05-24 09:46:38', '""', 0),
	(15, 'boysida', 'nhanxu', '101600 nhan 100 tu vi tri addgift infoquesttemplate', '2026-05-24 09:46:54', '""', 0),
	(16, 'boysida', 'lenlv', 'addxpcharevent map olv:4 oexp: 3535 >> lv: 5 exp: 4040 >> addthem: 4040', '2026-05-24 09:47:08', '""', 0),
	(17, 'boysida', 'lenlevel', '4_5 len lv:  xp cu: 4040 >> xpmoi: 4040 >> expadd: 500 > vi tri set: infoquesttemplate', '2026-05-24 09:47:08', '""', 0),
	(18, 'boysida', 'nhanxu', '101700 nhan 100 tu vi tri addgift infoquesttemplate', '2026-05-24 09:47:08', '""', 0),
	(19, 'boysida', 'nhanxu', '101800 nhan 100 tu vi tri addgift infoquesttemplate', '2026-05-24 09:47:19', '""', 0),
	(20, 'boysida', 'lenlv', 'addxpforchar map olv:5 oexp: 7975 >> lv: 6 exp: 8180 >> addthem: 8180', '2026-05-24 09:48:17', '""', 0),
	(21, 'boysida', 'lenlevel', '5_6 len lv:  xp cu: 8180 >> xpmoi: 8180 >> expadd: 200 > vi tri: map doattackmonster3', '2026-05-24 09:48:17', '""', 0),
	(22, 'boysida', 'tanthu', '0 nhan goi qua 0', '2026-05-24 09:48:54', '""', 0),
	(23, 'boysida', 'lenlv', 'addxpcharevent map olv:6 oexp: 8215 >> lv: 15 exp: 508220 >> addthem: 508220', '2026-05-24 09:50:44', '""', 0),
	(24, 'boysida', 'lenlevel', '6_15 len lv:  xp cu: 508220 >> xpmoi: 508220 >> expadd: 500000 > vi tri set: dousepotion type_hop_than_ky', '2026-05-24 09:50:44', '""', 0),
	(25, 'boysida', 'tanthu', '0 nhan goi qua 1', '2026-05-24 10:03:54', '""', 0),
	(26, 'boysida', 'logxu', '101800_0_1800', '2026-05-24 10:12:53', '""', 0),
	(27, 'admin', 'idruongmm', 'id ruong trung 171', '2026-05-24 10:24:41', '""', 0),
	(28, 'boysida', 'viplevel', 'sync vip theo cap 0->1 lv 15', '2026-05-24 10:24:52', '""', 0),
	(29, 'boysida', 'truxu', '97800 tru 4000 tu vi tri map 31', '2026-05-24 10:24:57', '""', 0),
	(30, 'boysida', 'hs', '4000', '2026-05-24 10:24:57', '""', 0),
	(31, 'boysida', 'logxu', '97800_4000_0', '2026-05-24 10:25:26', '""', 0),
	(32, 'boysida', 'logxu', '97800_0_0', '2026-05-24 10:37:47', '""', 0),
	(33, 'admin', 'idruongmm', 'id ruong trung 170', '2026-05-24 10:55:23', '""', 0),
	(34, 'admin', 'idruongmm', 'id ruong trung 155', '2026-05-24 11:12:04', '""', 0),
	(35, 'boysida', 'nhanxu', '102776 nhan 4976 tu vi tri farm_monster_reward', '2026-05-24 11:15:05', '""', 0),
	(36, 'boysida', 'logxu', '102776_0_4976', '2026-05-24 11:15:24', '""', 0),
	(37, 'admin', 'idruongmm', 'id ruong trung 110', '2026-05-25 08:03:36', '""', 0),
	(38, 'admin', 'idruongmm', 'id ruong trung 272', '2026-05-25 08:06:31', '""', 0),
	(39, 'admin', 'idruongmm', 'id ruong trung 160', '2026-05-25 08:17:41', '""', 0),
	(40, 'admin', 'idruongmm', 'id ruong trung 208', '2026-05-25 08:19:39', '""', 0),
	(41, 'admin', 'idruongmm', 'id ruong trung 264', '2026-05-25 08:24:32', '""', 0),
	(42, 'admin', 'idruongmm', 'id ruong trung 185', '2026-05-25 08:29:03', '""', 0),
	(43, 'ascascac', 'lenlv', 'char initpinfo olv:0 oexp: 0 >> lv: 1 exp: 0 >> addthem: 0', '2026-05-25 08:31:24', '""', 0),
	(44, 'ascascac', 'logxu', '100000_0_0', '2026-05-25 08:31:43', '""', 0);
/*!40000 ALTER TABLE `tob_log_all_item` ENABLE KEYS */;

-- Dumping structure for table kpah.tob_log_buy_shop
CREATE TABLE IF NOT EXISTS `tob_log_buy_shop` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `charname` varchar(45) NOT NULL,
  `info` varchar(450) NOT NULL,
  `timeBuy` datetime NOT NULL,
  `typeitem` int(10) unsigned NOT NULL DEFAULT '0',
  `price` int(10) unsigned NOT NULL,
  `money` varchar(450) NOT NULL DEFAULT '""',
  PRIMARY KEY (`id`),
  KEY `Index_2` (`charname`),
  KEY `Index_4` (`timeBuy`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- Dumping data for table kpah.tob_log_buy_shop: 0 rows
DELETE FROM `tob_log_buy_shop`;
/*!40000 ALTER TABLE `tob_log_buy_shop` DISABLE KEYS */;
/*!40000 ALTER TABLE `tob_log_buy_shop` ENABLE KEYS */;

-- Dumping structure for table kpah.tob_log_buy_store
CREATE TABLE IF NOT EXISTS `tob_log_buy_store` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `charname` varchar(45) NOT NULL,
  `info` varchar(500) NOT NULL,
  `timeBuy` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- Dumping data for table kpah.tob_log_buy_store: 0 rows
DELETE FROM `tob_log_buy_store`;
/*!40000 ALTER TABLE `tob_log_buy_store` DISABLE KEYS */;
/*!40000 ALTER TABLE `tob_log_buy_store` ENABLE KEYS */;

-- Dumping structure for table kpah.tob_log_clan
CREATE TABLE IF NOT EXISTS `tob_log_clan` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `charname` varchar(45) NOT NULL,
  `actioninfo` varchar(45) NOT NULL,
  `timeLog` datetime NOT NULL,
  `info` varchar(8000) NOT NULL DEFAULT '""',
  PRIMARY KEY (`id`),
  KEY `Index_2` (`charname`),
  KEY `Index_3` (`actioninfo`),
  KEY `Index_4` (`timeLog`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- Dumping data for table kpah.tob_log_clan: 0 rows
DELETE FROM `tob_log_clan`;
/*!40000 ALTER TABLE `tob_log_clan` DISABLE KEYS */;
/*!40000 ALTER TABLE `tob_log_clan` ENABLE KEYS */;

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

-- Dumping structure for table kpah.tob_log_use_luong_3_5_2026
CREATE TABLE IF NOT EXISTS `tob_log_use_luong_3_5_2026` (
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

-- Dumping data for table kpah.tob_log_use_luong_3_5_2026: 0 rows
DELETE FROM `tob_log_use_luong_3_5_2026`;
/*!40000 ALTER TABLE `tob_log_use_luong_3_5_2026` DISABLE KEYS */;
/*!40000 ALTER TABLE `tob_log_use_luong_3_5_2026` ENABLE KEYS */;

-- Dumping structure for table kpah.tob_log_use_luong_4_3_2026
CREATE TABLE IF NOT EXISTS `tob_log_use_luong_4_3_2026` (
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

-- Dumping data for table kpah.tob_log_use_luong_4_3_2026: 0 rows
DELETE FROM `tob_log_use_luong_4_3_2026`;
/*!40000 ALTER TABLE `tob_log_use_luong_4_3_2026` DISABLE KEYS */;
/*!40000 ALTER TABLE `tob_log_use_luong_4_3_2026` ENABLE KEYS */;

-- Dumping structure for table kpah.tob_log_use_luong_4_4_2026
CREATE TABLE IF NOT EXISTS `tob_log_use_luong_4_4_2026` (
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
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- Dumping data for table kpah.tob_log_use_luong_4_4_2026: 3 rows
DELETE FROM `tob_log_use_luong_4_4_2026`;
/*!40000 ALTER TABLE `tob_log_use_luong_4_4_2026` DISABLE KEYS */;
INSERT INTO `tob_log_use_luong_4_4_2026` (`id`, `name`, `idtemplate`, `soluong`, `solanmua`, `totalmoney`, `moneytype`) VALUES
	(1, 'mo rong hanh trang', 0, 2, 2, 650, 'luong'),
	(2, 'Tho dia phu', 0, 120, 1, 120000, 'xu'),
	(3, 'Vai cap 4 khoa \n - Dung che tao trang bi', 0, 1, 1, 15, 'luong_lock');
/*!40000 ALTER TABLE `tob_log_use_luong_4_4_2026` ENABLE KEYS */;

-- Dumping structure for table kpah.tob_log_use_luong_4_5_2026
CREATE TABLE IF NOT EXISTS `tob_log_use_luong_4_5_2026` (
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

-- Dumping data for table kpah.tob_log_use_luong_4_5_2026: 0 rows
DELETE FROM `tob_log_use_luong_4_5_2026`;
/*!40000 ALTER TABLE `tob_log_use_luong_4_5_2026` DISABLE KEYS */;
/*!40000 ALTER TABLE `tob_log_use_luong_4_5_2026` ENABLE KEYS */;

-- Dumping structure for table kpah.tob_log_use_luong_5_4_2026
CREATE TABLE IF NOT EXISTS `tob_log_use_luong_5_4_2026` (
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

-- Dumping data for table kpah.tob_log_use_luong_5_4_2026: 0 rows
DELETE FROM `tob_log_use_luong_5_4_2026`;
/*!40000 ALTER TABLE `tob_log_use_luong_5_4_2026` DISABLE KEYS */;
/*!40000 ALTER TABLE `tob_log_use_luong_5_4_2026` ENABLE KEYS */;

-- Dumping structure for table kpah.tob_log_use_luong_5_5_2026
CREATE TABLE IF NOT EXISTS `tob_log_use_luong_5_5_2026` (
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

-- Dumping data for table kpah.tob_log_use_luong_5_5_2026: 0 rows
DELETE FROM `tob_log_use_luong_5_5_2026`;
/*!40000 ALTER TABLE `tob_log_use_luong_5_5_2026` DISABLE KEYS */;
/*!40000 ALTER TABLE `tob_log_use_luong_5_5_2026` ENABLE KEYS */;

-- Dumping structure for table kpah.tob_log_use_luong_6_3_2026
CREATE TABLE IF NOT EXISTS `tob_log_use_luong_6_3_2026` (
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
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- Dumping data for table kpah.tob_log_use_luong_6_3_2026: 4 rows
DELETE FROM `tob_log_use_luong_6_3_2026`;
/*!40000 ALTER TABLE `tob_log_use_luong_6_3_2026` DISABLE KEYS */;
INSERT INTO `tob_log_use_luong_6_3_2026` (`id`, `name`, `idtemplate`, `soluong`, `solanmua`, `totalmoney`, `moneytype`) VALUES
	(1, 'Tho dia phu', 0, 170, 2, 170000, 'xu'),
	(2, '200 binh HP 15000', 0, 200, 1, 25, 'luong'),
	(3, '200 binh MP 15000', 0, 600, 3, 75, 'luong'),
	(4, 'Tiem nang dan', 0, 1, 1, 50, 'luong');
/*!40000 ALTER TABLE `tob_log_use_luong_6_3_2026` ENABLE KEYS */;

-- Dumping structure for table kpah.tob_log_xoso
CREATE TABLE IF NOT EXISTS `tob_log_xoso` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `win` int(10) unsigned NOT NULL,
  `timelog` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `Index_2` (`win`),
  KEY `Index_3` (`timelog`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- Dumping data for table kpah.tob_log_xoso: 0 rows
DELETE FROM `tob_log_xoso`;
/*!40000 ALTER TABLE `tob_log_xoso` DISABLE KEYS */;
/*!40000 ALTER TABLE `tob_log_xoso` ENABLE KEYS */;

-- Dumping structure for table kpah.tob_market
CREATE TABLE IF NOT EXISTS `tob_market` (
  `charname` varchar(45) NOT NULL,
  `itemsell` text NOT NULL COMMENT 'danh sach item dang ban',
  `money` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT 'tien ban duoc',
  `items` text NOT NULL,
  `listBid` text NOT NULL,
  PRIMARY KEY (`charname`) USING BTREE,
  KEY `money` (`money`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- Dumping data for table kpah.tob_market: 0 rows
DELETE FROM `tob_market`;
/*!40000 ALTER TABLE `tob_market` DISABLE KEYS */;
/*!40000 ALTER TABLE `tob_market` ENABLE KEYS */;

-- Dumping structure for table kpah.tob_nap_xai_luong
CREATE TABLE IF NOT EXISTS `tob_nap_xai_luong` (
  `int` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `charname` varchar(100) NOT NULL,
  `luongxai` int(10) unsigned NOT NULL DEFAULT '0',
  `luongnap` int(10) unsigned NOT NULL DEFAULT '0',
  `tnap` datetime DEFAULT NULL,
  `txai` datetime DEFAULT NULL,
  PRIMARY KEY (`int`),
  UNIQUE KEY `Index_2` (`charname`),
  KEY `Index_3` (`luongxai`),
  KEY `Index_4` (`luongnap`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- Dumping data for table kpah.tob_nap_xai_luong: 0 rows
DELETE FROM `tob_nap_xai_luong`;
/*!40000 ALTER TABLE `tob_nap_xai_luong` DISABLE KEYS */;
/*!40000 ALTER TABLE `tob_nap_xai_luong` ENABLE KEYS */;

-- Dumping structure for table kpah.tob_nap_xai_luong_32026
CREATE TABLE IF NOT EXISTS `tob_nap_xai_luong_32026` (
  `int` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `charname` varchar(100) NOT NULL,
  `luongxai` int(10) unsigned NOT NULL DEFAULT '0',
  `luongnap` int(10) unsigned NOT NULL DEFAULT '0',
  `tnap` datetime DEFAULT NULL,
  `txai` datetime DEFAULT NULL,
  PRIMARY KEY (`int`),
  UNIQUE KEY `Index_2` (`charname`),
  KEY `Index_3` (`luongxai`),
  KEY `Index_4` (`luongnap`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- Dumping data for table kpah.tob_nap_xai_luong_32026: 0 rows
DELETE FROM `tob_nap_xai_luong_32026`;
/*!40000 ALTER TABLE `tob_nap_xai_luong_32026` DISABLE KEYS */;
/*!40000 ALTER TABLE `tob_nap_xai_luong_32026` ENABLE KEYS */;

-- Dumping structure for table kpah.tob_nap_xai_luong_42026
CREATE TABLE IF NOT EXISTS `tob_nap_xai_luong_42026` (
  `int` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `charname` varchar(100) NOT NULL,
  `luongxai` int(10) unsigned NOT NULL DEFAULT '0',
  `luongnap` int(10) unsigned NOT NULL DEFAULT '0',
  `tnap` datetime DEFAULT NULL,
  `txai` datetime DEFAULT NULL,
  PRIMARY KEY (`int`),
  UNIQUE KEY `Index_2` (`charname`),
  KEY `Index_3` (`luongxai`),
  KEY `Index_4` (`luongnap`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- Dumping data for table kpah.tob_nap_xai_luong_42026: 0 rows
DELETE FROM `tob_nap_xai_luong_42026`;
/*!40000 ALTER TABLE `tob_nap_xai_luong_42026` DISABLE KEYS */;
/*!40000 ALTER TABLE `tob_nap_xai_luong_42026` ENABLE KEYS */;

-- Dumping structure for table kpah.tob_nap_xai_luong_52026
CREATE TABLE IF NOT EXISTS `tob_nap_xai_luong_52026` (
  `int` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `charname` varchar(100) NOT NULL,
  `luongxai` int(10) unsigned NOT NULL DEFAULT '0',
  `luongnap` int(10) unsigned NOT NULL DEFAULT '0',
  `tnap` datetime DEFAULT NULL,
  `txai` datetime DEFAULT NULL,
  PRIMARY KEY (`int`),
  UNIQUE KEY `Index_2` (`charname`),
  KEY `Index_3` (`luongxai`),
  KEY `Index_4` (`luongnap`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- Dumping data for table kpah.tob_nap_xai_luong_52026: 0 rows
DELETE FROM `tob_nap_xai_luong_52026`;
/*!40000 ALTER TABLE `tob_nap_xai_luong_52026` DISABLE KEYS */;
/*!40000 ALTER TABLE `tob_nap_xai_luong_52026` ENABLE KEYS */;

-- Dumping structure for table kpah.tob_nap_xai_luong_62025
CREATE TABLE IF NOT EXISTS `tob_nap_xai_luong_62025` (
  `int` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `charname` varchar(100) NOT NULL,
  `luongxai` int(10) unsigned NOT NULL DEFAULT '0',
  `luongnap` int(10) unsigned NOT NULL DEFAULT '0',
  `tnap` datetime DEFAULT NULL,
  `txai` datetime DEFAULT NULL,
  PRIMARY KEY (`int`),
  UNIQUE KEY `Index_2` (`charname`),
  KEY `Index_3` (`luongxai`),
  KEY `Index_4` (`luongnap`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- Dumping data for table kpah.tob_nap_xai_luong_62025: 0 rows
DELETE FROM `tob_nap_xai_luong_62025`;
/*!40000 ALTER TABLE `tob_nap_xai_luong_62025` DISABLE KEYS */;
/*!40000 ALTER TABLE `tob_nap_xai_luong_62025` ENABLE KEYS */;

-- Dumping structure for table kpah.tob_nap_xai_luong_72025
CREATE TABLE IF NOT EXISTS `tob_nap_xai_luong_72025` (
  `int` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `charname` varchar(100) NOT NULL,
  `luongxai` int(10) unsigned NOT NULL DEFAULT '0',
  `luongnap` int(10) unsigned NOT NULL DEFAULT '0',
  `tnap` datetime DEFAULT NULL,
  `txai` datetime DEFAULT NULL,
  PRIMARY KEY (`int`),
  UNIQUE KEY `Index_2` (`charname`),
  KEY `Index_3` (`luongxai`),
  KEY `Index_4` (`luongnap`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- Dumping data for table kpah.tob_nap_xai_luong_72025: 0 rows
DELETE FROM `tob_nap_xai_luong_72025`;
/*!40000 ALTER TABLE `tob_nap_xai_luong_72025` DISABLE KEYS */;
/*!40000 ALTER TABLE `tob_nap_xai_luong_72025` ENABLE KEYS */;

-- Dumping structure for table kpah.tob_nau_banh
CREATE TABLE IF NOT EXISTS `tob_nau_banh` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `listchar` text NOT NULL,
  `timecook` int(10) unsigned NOT NULL DEFAULT '60',
  `water` int(10) unsigned NOT NULL DEFAULT '0',
  `chamnc` int(10) unsigned NOT NULL DEFAULT '0',
  `listCharGift` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- Dumping data for table kpah.tob_nau_banh: 0 rows
DELETE FROM `tob_nau_banh`;
/*!40000 ALTER TABLE `tob_nau_banh` DISABLE KEYS */;
/*!40000 ALTER TABLE `tob_nau_banh` ENABLE KEYS */;

-- Dumping structure for table kpah.tob_pet
CREATE TABLE IF NOT EXISTS `tob_pet` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(300) NOT NULL,
  `idImg` tinyint(3) unsigned NOT NULL,
  `owner` int(10) unsigned NOT NULL,
  `info` varchar(200) NOT NULL COMMENT 'thong tin ve thoi gian mua, tong thoi gian ton tai',
  `place` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `itPet` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `name` (`name`),
  KEY `idimg` (`idImg`),
  KEY `owner` (`owner`),
  KEY `place` (`place`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- Dumping data for table kpah.tob_pet: 0 rows
DELETE FROM `tob_pet`;
/*!40000 ALTER TABLE `tob_pet` DISABLE KEYS */;
/*!40000 ALTER TABLE `tob_pet` ENABLE KEYS */;

-- Dumping structure for table kpah.tob_qua
CREATE TABLE IF NOT EXISTS `tob_qua` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `charname` varchar(100) NOT NULL,
  `qua` bigint(20) unsigned NOT NULL DEFAULT '1',
  `typeQua` bigint(20) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `Index_2` (`charname`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- Dumping data for table kpah.tob_qua: 0 rows
DELETE FROM `tob_qua`;
/*!40000 ALTER TABLE `tob_qua` DISABLE KEYS */;
/*!40000 ALTER TABLE `tob_qua` ENABLE KEYS */;

-- Dumping structure for table kpah.tob_qua_ct
CREATE TABLE IF NOT EXISTS `tob_qua_ct` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `charname` varchar(45) NOT NULL,
  `soluong` int(10) unsigned NOT NULL DEFAULT '1',
  `nhan` int(10) unsigned NOT NULL DEFAULT '0',
  `tlog` datetime DEFAULT NULL,
  `tset` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `Index_2` (`charname`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- Dumping data for table kpah.tob_qua_ct: 0 rows
DELETE FROM `tob_qua_ct`;
/*!40000 ALTER TABLE `tob_qua_ct` DISABLE KEYS */;
/*!40000 ALTER TABLE `tob_qua_ct` ENABLE KEYS */;

-- Dumping structure for table kpah.tob_qua_ct_bk
CREATE TABLE IF NOT EXISTS `tob_qua_ct_bk` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `charname` varchar(45) NOT NULL,
  `soluong` int(10) unsigned NOT NULL DEFAULT '1',
  `nhan` int(10) unsigned NOT NULL DEFAULT '0',
  `tlog` datetime DEFAULT NULL,
  `tset` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- Dumping data for table kpah.tob_qua_ct_bk: 0 rows
DELETE FROM `tob_qua_ct_bk`;
/*!40000 ALTER TABLE `tob_qua_ct_bk` DISABLE KEYS */;
/*!40000 ALTER TABLE `tob_qua_ct_bk` ENABLE KEYS */;

-- Dumping structure for table kpah.tob_qua_ct_top
CREATE TABLE IF NOT EXISTS `tob_qua_ct_top` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `charname` varchar(45) NOT NULL,
  `soluong` int(10) unsigned NOT NULL DEFAULT '1' COMMENT '1-lien tram, 2 dam tru',
  `nhan` int(10) unsigned NOT NULL DEFAULT '0',
  `tlog` datetime DEFAULT NULL,
  `tset` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- Dumping data for table kpah.tob_qua_ct_top: 0 rows
DELETE FROM `tob_qua_ct_top`;
/*!40000 ALTER TABLE `tob_qua_ct_top` DISABLE KEYS */;
/*!40000 ALTER TABLE `tob_qua_ct_top` ENABLE KEYS */;

-- Dumping structure for table kpah.tob_qua_ct_top_bk
CREATE TABLE IF NOT EXISTS `tob_qua_ct_top_bk` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `charname` varchar(45) NOT NULL,
  `soluong` int(10) unsigned NOT NULL DEFAULT '1',
  `nhan` int(10) unsigned NOT NULL DEFAULT '0',
  `tlog` datetime DEFAULT NULL,
  `tset` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- Dumping data for table kpah.tob_qua_ct_top_bk: 0 rows
DELETE FROM `tob_qua_ct_top_bk`;
/*!40000 ALTER TABLE `tob_qua_ct_top_bk` DISABLE KEYS */;
/*!40000 ALTER TABLE `tob_qua_ct_top_bk` ENABLE KEYS */;

-- Dumping structure for table kpah.tob_qua_loi_dai
CREATE TABLE IF NOT EXISTS `tob_qua_loi_dai` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `charname` varchar(100) NOT NULL,
  `qua` int(10) unsigned NOT NULL DEFAULT '0',
  `typeQua` int(10) unsigned NOT NULL DEFAULT '0',
  `nhom` int(10) unsigned NOT NULL DEFAULT '0',
  `diem` int(10) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- Dumping data for table kpah.tob_qua_loi_dai: 0 rows
DELETE FROM `tob_qua_loi_dai`;
/*!40000 ALTER TABLE `tob_qua_loi_dai` DISABLE KEYS */;
/*!40000 ALTER TABLE `tob_qua_loi_dai` ENABLE KEYS */;

-- Dumping structure for table kpah.tob_quest
CREATE TABLE IF NOT EXISTS `tob_quest` (
  `idchar` int(10) unsigned NOT NULL,
  `idquest` int(10) unsigned NOT NULL,
  `state` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '0 chua nhan, 1 nhan, 2 hoan thanh',
  `totalmonskill` varchar(100) NOT NULL DEFAULT '0',
  `totalitemget` varchar(100) NOT NULL DEFAULT '0',
  PRIMARY KEY (`idchar`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- Dumping data for table kpah.tob_quest: 0 rows
DELETE FROM `tob_quest`;
/*!40000 ALTER TABLE `tob_quest` DISABLE KEYS */;
/*!40000 ALTER TABLE `tob_quest` ENABLE KEYS */;

-- Dumping structure for table kpah.tob_quest_clan
CREATE TABLE IF NOT EXISTS `tob_quest_clan` (
  `id_char` int(10) unsigned NOT NULL,
  `idquest` int(10) unsigned NOT NULL,
  `rcv` int(10) unsigned NOT NULL DEFAULT '0',
  `finish` int(10) unsigned NOT NULL DEFAULT '0',
  `nitem1` int(10) unsigned NOT NULL DEFAULT '0',
  `nitem2` int(10) unsigned NOT NULL DEFAULT '0',
  `nitem3` int(10) unsigned NOT NULL DEFAULT '0',
  `nmons1` int(10) unsigned NOT NULL DEFAULT '0',
  `nmons2` int(10) unsigned NOT NULL DEFAULT '0',
  `nmons3` int(10) unsigned NOT NULL DEFAULT '0',
  `id_clan` int(10) unsigned NOT NULL DEFAULT '0',
  `n` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `timeEnd` bigint(20) unsigned zerofill NOT NULL DEFAULT '00000000000000000000',
  PRIMARY KEY (`id_char`) USING BTREE,
  KEY `Index_2` (`idquest`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- Dumping data for table kpah.tob_quest_clan: 0 rows
DELETE FROM `tob_quest_clan`;
/*!40000 ALTER TABLE `tob_quest_clan` DISABLE KEYS */;
/*!40000 ALTER TABLE `tob_quest_clan` ENABLE KEYS */;

-- Dumping structure for table kpah.tob_reg_chientruong
CREATE TABLE IF NOT EXISTS `tob_reg_chientruong` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `charname` varchar(45) NOT NULL,
  `timeReg` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `Index_2` (`charname`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- Dumping data for table kpah.tob_reg_chientruong: 0 rows
DELETE FROM `tob_reg_chientruong`;
/*!40000 ALTER TABLE `tob_reg_chientruong` DISABLE KEYS */;
/*!40000 ALTER TABLE `tob_reg_chientruong` ENABLE KEYS */;

-- Dumping structure for table kpah.tob_smsnap
CREATE TABLE IF NOT EXISTS `tob_smsnap` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `text` varchar(50) NOT NULL,
  `syntax` varchar(100) NOT NULL,
  `port` varchar(10) NOT NULL,
  `provider` int(11) NOT NULL,
  `game` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `agent` int(10) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- Dumping data for table kpah.tob_smsnap: 0 rows
DELETE FROM `tob_smsnap`;
/*!40000 ALTER TABLE `tob_smsnap` DISABLE KEYS */;
/*!40000 ALTER TABLE `tob_smsnap` ENABLE KEYS */;

-- Dumping structure for table kpah.tob_sp_new
CREATE TABLE IF NOT EXISTS `tob_sp_new` (
  `owner` int(10) unsigned NOT NULL,
  `listtemplate` varchar(300) NOT NULL,
  `soluong` varchar(500) NOT NULL,
  PRIMARY KEY (`owner`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- Dumping data for table kpah.tob_sp_new: 2 rows
DELETE FROM `tob_sp_new`;
/*!40000 ALTER TABLE `tob_sp_new` DISABLE KEYS */;
INSERT INTO `tob_sp_new` (`owner`, `listtemplate`, `soluong`) VALUES
	(1, '0,1', '1,0'),
	(2, '0,1', '0,0');
/*!40000 ALTER TABLE `tob_sp_new` ENABLE KEYS */;

-- Dumping structure for table kpah.tob_top_event
CREATE TABLE IF NOT EXISTS `tob_top_event` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `charname` varchar(50) NOT NULL,
  `point1` int(11) NOT NULL DEFAULT '0',
  `timeupdate` datetime DEFAULT CURRENT_TIMESTAMP,
  `xu` bigint(20) DEFAULT '0',
  `luong` int(11) DEFAULT '0',
  `datexu` datetime DEFAULT CURRENT_TIMESTAMP,
  `dateluong` datetime DEFAULT CURRENT_TIMESTAMP,
  `crtime` bigint(20) DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `charname` (`charname`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Dumping data for table kpah.tob_top_event: ~0 rows (approximately)
DELETE FROM `tob_top_event`;

-- Dumping structure for table kpah.tob_top_event_wc
CREATE TABLE IF NOT EXISTS `tob_top_event_wc` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `charname` varchar(200) NOT NULL,
  `point1` int(10) unsigned NOT NULL DEFAULT '1',
  `timeupdate` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `xu` bigint(20) unsigned NOT NULL DEFAULT '0',
  `luong` bigint(20) unsigned NOT NULL DEFAULT '0',
  `datexu` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `dateluong` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `crtime` bigint(20) unsigned DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `Index_2` (`charname`) USING BTREE
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- Dumping data for table kpah.tob_top_event_wc: 0 rows
DELETE FROM `tob_top_event_wc`;
/*!40000 ALTER TABLE `tob_top_event_wc` DISABLE KEYS */;
/*!40000 ALTER TABLE `tob_top_event_wc` ENABLE KEYS */;

-- Dumping structure for table kpah.tob_tubinh
CREATE TABLE IF NOT EXISTS `tob_tubinh` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `attack` int(10) unsigned NOT NULL,
  `def` int(10) unsigned NOT NULL,
  `dog` int(10) unsigned NOT NULL,
  `crit` int(10) unsigned NOT NULL,
  `hp` int(10) unsigned NOT NULL,
  `mp` int(10) unsigned NOT NULL,
  `level` int(10) unsigned NOT NULL,
  `nmanh` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

-- Dumping data for table kpah.tob_tubinh: 16 rows
DELETE FROM `tob_tubinh`;
/*!40000 ALTER TABLE `tob_tubinh` DISABLE KEYS */;
INSERT INTO `tob_tubinh` (`id`, `name`, `attack`, `def`, `dog`, `crit`, `hp`, `mp`, `level`, `nmanh`) VALUES
	(1, 'Mai', 15, 24, 2, 2, 100, 30, 40, 5),
	(2, 'Lan', 30, 45, 4, 4, 200, 60, 40, 5),
	(3, 'Cúc', 45, 69, 6, 6, 300, 90, 40, 5),
	(4, 'Trúc', 60, 90, 8, 8, 400, 120, 40, 5),
	(5, 'Sáo', 30, 45, 4, 4, 200, 60, 50, 10),
	(6, 'Quạt', 60, 90, 8, 8, 400, 120, 50, 10),
	(7, 'Sênh', 90, 135, 12, 12, 600, 180, 50, 10),
	(8, 'Đàn', 120, 180, 16, 16, 800, 240, 50, 10),
	(9, 'Ngư', 45, 69, 6, 6, 600, 90, 60, 15),
	(10, 'Tiều', 90, 135, 12, 12, 700, 180, 60, 15),
	(11, 'Canh', 135, 204, 18, 18, 800, 270, 60, 15),
	(12, 'Độc', 180, 270, 24, 24, 900, 360, 60, 15),
	(13, 'Long', 60, 90, 8, 8, 850, 120, 70, 20),
	(14, 'Lân', 120, 180, 16, 16, 950, 240, 70, 20),
	(15, 'Quy', 180, 270, 24, 24, 1050, 360, 70, 20),
	(16, 'Phụng', 240, 360, 32, 32, 1150, 480, 70, 20);
/*!40000 ALTER TABLE `tob_tubinh` ENABLE KEYS */;

-- Dumping structure for table kpah.tob_vantieu
CREATE TABLE IF NOT EXISTS `tob_vantieu` (
  `idchar` int(10) unsigned NOT NULL,
  `namechar` varchar(45) NOT NULL,
  `typetieu` int(10) unsigned NOT NULL,
  `tReceive` bigint(20) unsigned NOT NULL,
  `lv` int(10) unsigned NOT NULL,
  `mapid` int(10) unsigned NOT NULL,
  `x` int(10) unsigned NOT NULL,
  `y` int(10) unsigned NOT NULL,
  `idcountry` tinyint(3) unsigned NOT NULL,
  `hp` bigint(20) unsigned NOT NULL DEFAULT '0',
  `maxhp` bigint(20) unsigned NOT NULL DEFAULT '0',
  `count` int(11) DEFAULT '0',
  PRIMARY KEY (`idchar`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- Dumping data for table kpah.tob_vantieu: 0 rows
DELETE FROM `tob_vantieu`;
/*!40000 ALTER TABLE `tob_vantieu` DISABLE KEYS */;
/*!40000 ALTER TABLE `tob_vantieu` ENABLE KEYS */;

-- Dumping structure for table kpah.tob_web_info_char
CREATE TABLE IF NOT EXISTS `tob_web_info_char` (
  `charname` varchar(45) NOT NULL,
  `infoWearing` varchar(150) NOT NULL,
  `otherInfo` varchar(300) NOT NULL,
  `userid` int(10) unsigned NOT NULL,
  `lv` int(10) unsigned NOT NULL DEFAULT '0',
  `acountname` varchar(45) NOT NULL,
  PRIMARY KEY (`charname`) USING BTREE,
  KEY `userid` (`userid`) USING BTREE,
  KEY `lv` (`lv`),
  KEY `acountname` (`acountname`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- Dumping data for table kpah.tob_web_info_char: 2 rows
DELETE FROM `tob_web_info_char`;
/*!40000 ALTER TABLE `tob_web_info_char` DISABLE KEYS */;
INSERT INTO `tob_web_info_char` (`charname`, `infoWearing`, `otherInfo`, `userid`, `lv`, `acountname`) VALUES
	('boysida', '0,0,1,0,2,2,-1,-1,1,0,0,0,1,', '', 1, 15, 'admin'),
	('ascascac', '0,1,1,1,2,0,-1,-1,0,0,0,0,0,', '', 2, 1, 'admin1312');
/*!40000 ALTER TABLE `tob_web_info_char` ENABLE KEYS */;

-- Dumping structure for table kpah.tob_wedding
CREATE TABLE IF NOT EXISTS `tob_wedding` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `idhusband` int(10) unsigned NOT NULL,
  `idwife` int(10) unsigned NOT NULL,
  `married` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `timeParty` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `dayparty` varchar(45) NOT NULL DEFAULT '""',
  `typeparty` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `idwedding` int(10) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `husband` (`idhusband`),
  KEY `idwife` (`idwife`),
  KEY `Index_4` (`married`),
  KEY `Index_5` (`typeparty`),
  KEY `Index_6` (`idwedding`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- Dumping data for table kpah.tob_wedding: 0 rows
DELETE FROM `tob_wedding`;
/*!40000 ALTER TABLE `tob_wedding` DISABLE KEYS */;
/*!40000 ALTER TABLE `tob_wedding` ENABLE KEYS */;

-- Dumping structure for table kpah.tob_x2exp
CREATE TABLE IF NOT EXISTS `tob_x2exp` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `dayopenx2` varchar(500) DEFAULT NULL,
  `dayopenx3` varchar(500) DEFAULT NULL,
  `dayopenx50` varchar(500) DEFAULT NULL,
  `badwod` text NOT NULL,
  `tradepet` int(10) unsigned NOT NULL DEFAULT '0',
  `thamdinh` int(10) unsigned NOT NULL DEFAULT '0',
  `banchoi` int(11) NOT NULL DEFAULT '20',
  `itemevent` varchar(100) DEFAULT NULL,
  `pause` int(10) unsigned NOT NULL DEFAULT '1',
  `pauseT` int(10) unsigned NOT NULL DEFAULT '0',
  `vxmm` int(10) unsigned NOT NULL DEFAULT '1',
  `gopbang` int(10) unsigned NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- Dumping data for table kpah.tob_x2exp: 0 rows
DELETE FROM `tob_x2exp`;
/*!40000 ALTER TABLE `tob_x2exp` DISABLE KEYS */;
/*!40000 ALTER TABLE `tob_x2exp` ENABLE KEYS */;

-- Dumping structure for table kpah.tob_xoso
CREATE TABLE IF NOT EXISTS `tob_xoso` (
  `owner` int(10) unsigned NOT NULL,
  `vsxu` varchar(500) NOT NULL,
  `vsl` varchar(500) NOT NULL,
  `open` tinyint(3) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`owner`) USING BTREE
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- Dumping data for table kpah.tob_xoso: 0 rows
DELETE FROM `tob_xoso`;
/*!40000 ALTER TABLE `tob_xoso` DISABLE KEYS */;
/*!40000 ALTER TABLE `tob_xoso` ENABLE KEYS */;

-- Dumping structure for table kpah.top_char_quest
CREATE TABLE IF NOT EXISTS `top_char_quest` (
  `id_char` int(10) unsigned NOT NULL,
  `id_quest` int(10) unsigned NOT NULL DEFAULT '1',
  `receive` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT 'cho biet da nhan quest hay chua',
  `finish` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT 'cho biet da hoan thanh quest hay chua, neu roi thi gui thong tin reponse ',
  `nmonster1` int(10) unsigned NOT NULL DEFAULT '0' COMMENT 'so luogn quai da giet',
  `nmonster2` int(10) unsigned NOT NULL DEFAULT '0',
  `nmonster3` int(10) unsigned NOT NULL DEFAULT '0',
  `nitem1` int(10) unsigned NOT NULL DEFAULT '0' COMMENT 'so luong item da lay',
  `nitem2` int(10) unsigned NOT NULL DEFAULT '0',
  `nitem3` int(10) unsigned NOT NULL DEFAULT '0',
  `currentnpc` int(10) unsigned NOT NULL DEFAULT '0',
  `ischeck` int(10) unsigned NOT NULL DEFAULT '0' COMMENT 'da kt roi thi ko kiem tra nua',
  PRIMARY KEY (`id_char`),
  KEY `Index_2` (`id_quest`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- Dumping data for table kpah.top_char_quest: 0 rows
DELETE FROM `top_char_quest`;
/*!40000 ALTER TABLE `top_char_quest` DISABLE KEYS */;
/*!40000 ALTER TABLE `top_char_quest` ENABLE KEYS */;

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
