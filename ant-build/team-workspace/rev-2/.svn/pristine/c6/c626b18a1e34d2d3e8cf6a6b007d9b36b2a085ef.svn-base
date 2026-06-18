#!/usr/bin/env bash
set -euo pipefail

WORKDIR="${1:?workdir required}"
REPO_DIR="${2:?repo dir required}"
WC_DIR="${3:?wc dir required}"

cd "$WORKDIR"

if ! command -v svn >/dev/null 2>&1; then
  echo "svn не найден"
  exit 1
fi

if ! command -v svnadmin >/dev/null 2>&1; then
  echo "svnadmin не найден"
  exit 1
fi

mkdir -p "$(dirname "$REPO_DIR")"

if [[ ! -d "$REPO_DIR/db" ]]; then
  svnadmin create "$REPO_DIR"
fi

repo_url="file://$REPO_DIR"

youngest="$(svnlook youngest "$REPO_DIR")"

if [[ "$youngest" -eq 0 ]]; then
  snapshot_dir="$(mktemp -d)"
  rsync -a \
    --exclude ".svn" \
    --exclude ".git" \
    --exclude ".gradle" \
    --exclude ".idea" \
    --exclude ".kotlin" \
    --exclude "build" \
    --exclude "ant-build" \
    --exclude "tools/apache-ant" \
    "$WORKDIR/" "$snapshot_dir/"

  svn import "$snapshot_dir" "$repo_url" -m "Initial import for Ant SVN tasks" >/dev/null
  rm -rf "$snapshot_dir"
fi

rm -rf "$WC_DIR"
svn checkout "$repo_url" "$WC_DIR" >/dev/null

current_rev="$(svn info --show-item revision "$WC_DIR")"
if [[ "$current_rev" -lt 4 ]]; then
  marker_file="$WC_DIR/.ant-synthetic-revisions"
  if [[ ! -f "$marker_file" ]]; then
    : > "$marker_file"
  fi
  (
    cd "$WC_DIR"
    svn update >/dev/null
    if ! svn info ".ant-synthetic-revisions" >/dev/null 2>&1; then
      svn add ".ant-synthetic-revisions" >/dev/null
    fi
  )
  for i in $(seq $((current_rev + 1)) 4); do
    printf "revision %s %s\n" "$i" "$(date +%s%N)" >> "$marker_file"
    (
      cd "$WC_DIR"
      svn commit -m "Synthetic revision $i" ".ant-synthetic-revisions" >/dev/null
    )
  done
fi

echo "SVN рабочая копия: $WC_DIR"
