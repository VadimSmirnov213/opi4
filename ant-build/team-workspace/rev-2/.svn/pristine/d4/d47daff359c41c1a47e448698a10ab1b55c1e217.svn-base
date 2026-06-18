#!/usr/bin/env bash
set -euo pipefail

WORKDIR="${1:?workdir required}"
TEAM_WORKDIR="${2:?team workdir required}"
REV_COUNT="${3:?revision count required}"
ZIP_OUT="${4:?zip output required}"
ANT_BIN="${5:-ant}"

cd "$WORKDIR"

if ! command -v svn >/dev/null 2>&1; then
  echo "svn не найден"
  exit 1
fi

if ! command -v zip >/dev/null 2>&1; then
  echo "zip не найден"
  exit 1
fi

if svn info >/dev/null 2>&1; then
  SVN_WC="$WORKDIR"
elif [[ -n "${SVN_WC_DIR:-}" && -d "${SVN_WC_DIR}/.svn" ]]; then
  SVN_WC="$SVN_WC_DIR"
else
  echo "Текущая директория не является рабочей копией SVN"
  exit 1
fi

cd "$SVN_WC"

repo_url="$(svn info --show-item url)"
head_rev="$(svn info --show-item revision "$repo_url")"

rm -rf "$TEAM_WORKDIR"
mkdir -p "$TEAM_WORKDIR/jars"

for i in $(seq 1 "$REV_COUNT"); do
  rev=$((head_rev - i))
  if [[ "$rev" -lt 1 ]]; then
    break
  fi

  rev_dir="$TEAM_WORKDIR/rev-${rev}"
  svn checkout -r "$rev" "$repo_url" "$rev_dir" >/dev/null
  (
    cd "$rev_dir"
    "$ANT_BIN" build >/dev/null
    jar_file="$TEAM_WORKDIR/jars/rev-${rev}.jar"
    cp ant-build/dist/lab3.jar "$jar_file"
  )
done

if ! ls "$TEAM_WORKDIR/jars"/*.jar >/dev/null 2>&1; then
  echo "Не удалось получить предыдущие ревизии для сборки team"
  exit 1
fi

mkdir -p "$(dirname "$ZIP_OUT")"
rm -f "$ZIP_OUT"
(
  cd "$TEAM_WORKDIR/jars"
  zip -r "$ZIP_OUT" . >/dev/null
)

echo "Team archive: $ZIP_OUT"
