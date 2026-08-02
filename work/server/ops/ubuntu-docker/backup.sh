#!/usr/bin/env bash
set -euo pipefail

project_root="/opt/kpah"
backup_root="${project_root}/backups"
timestamp="$(date '+%Y%m%d-%H%M%S')"
work_dir="${backup_root}/.partial-${timestamp}"
archive_path="${backup_root}/kpah-${timestamp}.tar.gz"

case "${work_dir}" in
  /opt/kpah/backups/.partial-*) ;;
  *) echo "Từ chối thư mục backup không an toàn: ${work_dir}" >&2; exit 1 ;;
esac

umask 077
install -d -m 0700 "${backup_root}" "${work_dir}"
trap 'rm -rf -- "${work_dir}"' EXIT

cd "${project_root}"

# Khóa đọc ngắn trong lúc dump để dữ liệu MyISAM và InnoDB cùng một thời điểm.
docker compose exec -T db sh -lc \
  'exec mariadb-dump -uroot -p"$MARIADB_ROOT_PASSWORD" --quick --lock-all-tables --skip-extended-insert --hex-blob --routines --events --triggers --databases account kpah2' \
  | gzip -9 > "${work_dir}/databases.sql.gz"

install -m 0600 runtime/server.ini "${work_dir}/server.ini"
install -m 0600 runtime/loginServer/server.ini "${work_dir}/login-server.ini"
install -m 0600 secrets/db.env "${work_dir}/db.env"
sha256sum "${work_dir}"/* > "${work_dir}/SHA256SUMS"

tar -czf "${archive_path}" -C "${work_dir}" .
sha256sum "${archive_path}" > "${archive_path}.sha256"

# Chỉ xóa các gói KPAH đúng mẫu và cũ hơn 14 ngày.
find "${backup_root}" -maxdepth 1 -type f \
  \( -name 'kpah-*.tar.gz' -o -name 'kpah-*.tar.gz.sha256' \) \
  -mtime +14 -delete

echo "Backup OK: ${archive_path}"
