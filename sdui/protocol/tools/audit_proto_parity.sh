#!/usr/bin/env bash
set -euo pipefail

ROOT="${1:-$(pwd)}"
PROTO_ROOT="$ROOT/sdui/protocol/src/commonMain/proto/sdui"
CACHE_ROOT="${HOME}/.gradle/caches/modules-2/files-2.1"

pick_latest_sources() {
  find "$CACHE_ROOT" -type f -path "*$1*" -name '*-sources.jar' \
    | rg -v 'samples-sources\.jar$' \
    | sort -V \
    | tail -n 1
}

UI_JAR="$(pick_latest_sources '/androidx.compose.ui/ui-android/')"
UI_GRAPHICS_JAR="$(pick_latest_sources '/androidx.compose.ui/ui-graphics/')"
UI_TEXT_JAR="$(pick_latest_sources '/androidx.compose.ui/ui-text-android/')"
FOUNDATION_JAR="$(pick_latest_sources '/androidx.compose.foundation/foundation-android/')"
FOUNDATION_LAYOUT_JAR="$(pick_latest_sources '/androidx.compose.foundation/foundation-layout-android/')"
MATERIAL3_JAR="$(pick_latest_sources '/androidx.compose.material3/material3-android/')"

echo "Using:"
echo "  ui=$UI_JAR"
echo "  ui-graphics=$UI_GRAPHICS_JAR"
echo "  ui-text=$UI_TEXT_JAR"
echo "  foundation=$FOUNDATION_JAR"
echo "  foundation-layout=$FOUNDATION_LAYOUT_JAR"
echo "  material3=$MATERIAL3_JAR"
echo

jar_tf() {
  jar tf "$1" 2>/dev/null || true
}

source_list() {
  jar_tf "$1" | rg '^commonMain/androidx/compose/.+\.kt$|^androidMain/androidx/compose/.+\.kt$' -N || true
}

normalize_source_name() {
  sed -E 's#^.*/##; s#\.commonStubs\.kt$##; s#\.android\.kt$##; s#\.kt$##'
}

normalize_proto_name() {
  sed -E 's#^.*/##; s#\.proto$##'
}

TMP_DIR="$(mktemp -d)"
trap 'rm -rf "$TMP_DIR"' EXIT

{
  source_list "$UI_JAR"
  source_list "$UI_GRAPHICS_JAR"
  source_list "$UI_TEXT_JAR"
  source_list "$FOUNDATION_JAR"
  source_list "$FOUNDATION_LAYOUT_JAR"
  source_list "$MATERIAL3_JAR"
} | normalize_source_name | sort -u > "$TMP_DIR/source_names.txt"

find "$PROTO_ROOT" -type f -name '*.proto' | normalize_proto_name | sort -u > "$TMP_DIR/proto_names.txt"

echo "Missing proto names for source files:"
comm -23 "$TMP_DIR/source_names.txt" "$TMP_DIR/proto_names.txt" || true
echo

echo "Proto names without matching source file:"
comm -13 "$TMP_DIR/source_names.txt" "$TMP_DIR/proto_names.txt" || true
echo

echo "Empty proto messages:"
rg -n 'message .*\{\s*\}' "$PROTO_ROOT" -g '*.proto' || true
