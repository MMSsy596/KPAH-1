#!/usr/bin/env bash
set -euo pipefail

project_root="$(cd "$(dirname "${BASH_SOURCE[0]}")/../.." && pwd -P)"
build_dir="${project_root}/build"
dist_dir="${project_root}/dist"

# Chỉ dọn đúng hai thư mục kết quả nằm trực tiếp trong runtime của KPAH.
case "${build_dir}:${dist_dir}" in
  "${project_root}/build:${project_root}/dist") ;;
  *) echo "Từ chối dọn thư mục build không an toàn." >&2; exit 1 ;;
esac

cd "${project_root}"
rm -rf -- "${build_dir}" "${dist_dir}"
mkdir -p "${build_dir}/classes" "${dist_dir}/libs"

find src -type f -name '*.java' -print | LC_ALL=C sort > build/sources.txt

javac \
  -encoding UTF-8 \
  -source 1.8 \
  -target 1.8 \
  -cp 'libs/jxl-2.6.jar:libs/mysql-connector-java-5.1.49.jar:libs/NQSH_5h.jar' \
  -d build/classes \
  @build/sources.txt

cat > build/manifest.mf <<'EOF'
Manifest-Version: 1.0
Main-Class: server.TeamServer
Class-Path: libs/jxl-2.6.jar libs/mysql-connector-java-5.1.49.jar libs/NQSH_5h.jar

EOF

jar cfm dist/KPAH2.jar build/manifest.mf -C build/classes .
cp libs/jxl-2.6.jar libs/mysql-connector-java-5.1.49.jar libs/NQSH_5h.jar dist/libs/

sha256sum dist/KPAH2.jar
