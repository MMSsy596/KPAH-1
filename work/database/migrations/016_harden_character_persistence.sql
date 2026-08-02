-- Xem trước các bảng/cột sẽ thay đổi trước khi chạy migration.
SELECT TABLE_NAME, ENGINE, TABLE_ROWS
FROM information_schema.TABLES
WHERE TABLE_SCHEMA = DATABASE()
  AND TABLE_NAME IN (
    'tob_char', 'tob_gem_new', 'tob_animal', 'tob_char_quest',
    'tob_char_fruit', 'tob_char_tubinh', 'tob_farm', 'tob_pet',
    'tob_char_luong', 'tob_event', 'tob_log_all_item', 'tob_server_finance'
  )
ORDER BY TABLE_NAME;

SELECT COLUMN_NAME, COLUMN_TYPE, CHARACTER_MAXIMUM_LENGTH
FROM information_schema.COLUMNS
WHERE TABLE_SCHEMA = DATABASE()
  AND TABLE_NAME = 'tob_gem_new'
  AND COLUMN_NAME IN ('listtemplate', 'soluong', 'slock')
ORDER BY ORDINAL_POSITION;

-- 280 template tạo chuỗi listtemplate dài 1.009 ký tự, vượt giới hạn VARCHAR(1000).
-- MEDIUMTEXT giữ tương thích dữ liệu CSV hiện tại và đủ chỗ khi bổ sung template sau này.
ALTER TABLE tob_gem_new
  ENGINE = InnoDB,
  MODIFY COLUMN listtemplate MEDIUMTEXT NOT NULL,
  MODIFY COLUMN soluong MEDIUMTEXT NOT NULL,
  MODIFY COLUMN slock MEDIUMTEXT NOT NULL;

-- Các bảng trạng thái quan trọng cần khả năng crash-recovery của InnoDB.
ALTER TABLE tob_char ENGINE = InnoDB;
ALTER TABLE tob_animal ENGINE = InnoDB;
ALTER TABLE tob_char_quest ENGINE = InnoDB;
ALTER TABLE tob_char_fruit ENGINE = InnoDB;
ALTER TABLE tob_char_tubinh ENGINE = InnoDB;
ALTER TABLE tob_farm ENGINE = InnoDB;
ALTER TABLE tob_pet ENGINE = InnoDB;
ALTER TABLE tob_char_luong ENGINE = InnoDB;
ALTER TABLE tob_event ENGINE = InnoDB;
ALTER TABLE tob_log_all_item ENGINE = InnoDB;
ALTER TABLE tob_server_finance ENGINE = InnoDB;

-- Kiểm tra sau migration; tất cả kết quả phải là InnoDB và ba cột phải là MEDIUMTEXT.
SELECT TABLE_NAME, ENGINE
FROM information_schema.TABLES
WHERE TABLE_SCHEMA = DATABASE()
  AND TABLE_NAME IN (
    'tob_char', 'tob_gem_new', 'tob_animal', 'tob_char_quest',
    'tob_char_fruit', 'tob_char_tubinh', 'tob_farm', 'tob_pet',
    'tob_char_luong', 'tob_event', 'tob_log_all_item', 'tob_server_finance'
  )
ORDER BY TABLE_NAME;

SELECT COLUMN_NAME, COLUMN_TYPE, CHARACTER_MAXIMUM_LENGTH
FROM information_schema.COLUMNS
WHERE TABLE_SCHEMA = DATABASE()
  AND TABLE_NAME = 'tob_gem_new'
  AND COLUMN_NAME IN ('listtemplate', 'soluong', 'slock')
ORDER BY ORDINAL_POSITION;
