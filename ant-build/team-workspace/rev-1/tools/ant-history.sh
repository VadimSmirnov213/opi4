#!/usr/bin/env bash
set -euo pipefail

WORKDIR="${1:?workdir required}"
DIFF_OUT="${2:?diff file required}"
GRADLE_WRAPPER="${3:-./gradlew}"

cd "$WORKDIR"

if ! command -v svn >/dev/null 2>&1; then
  echo "svn не найден"
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

head_rev="$(svn info --show-item revision)"
current_rev="$head_rev"
last_failed=""
last_success=""

while true; do
  svn update -r "$current_rev" >/dev/null
  if "$GRADLE_WRAPPER" classes >/dev/null 2>&1; then
    last_success="$current_rev"
    break
  fi
  last_failed="$current_rev"
  if [[ "$current_rev" -le 1 ]]; then
    break
  fi
  current_rev=$((current_rev - 1))
done

if [[ -z "$last_success" ]]; then
  echo "Рабочая ревизия не найдена"
  exit 1
fi

if [[ -n "$last_failed" ]]; then
  svn diff -c "$last_failed" > "$DIFF_OUT"
else
  : > "$DIFF_OUT"
fi

echo "Последняя рабочая ревизия: $last_success"
echo "Diff сохранен в: $DIFF_OUT"
