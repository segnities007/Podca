#!/usr/bin/env python3
import re
import sys
import tomllib
import zipfile
from collections import defaultdict
from pathlib import Path


ROOT = Path(sys.argv[1]) if len(sys.argv) > 1 else Path.cwd()
PROTO_ROOT = ROOT / "sdui/protocol/src/commonMain/proto/sdui"
CACHE_ROOT = Path.home() / ".gradle/caches/modules-2/files-2.1"


def load_versions() -> tuple[str, str]:
    versions_path = ROOT / "gradle/libs.versions.toml"
    versions = tomllib.loads(versions_path.read_text())["versions"]
    compose_version = versions["composeMultiplatform"]
    material3_version = versions["material3"]
    return compose_version, material3_version


COMPOSE_VERSION, MATERIAL3_VERSION = load_versions()


def pick_sources(group_path: str, version: str) -> Path:
    all_candidates = [
        p
        for p in CACHE_ROOT.rglob("*-sources.jar")
        if group_path in str(p) and not p.name.endswith("samples-sources.jar")
    ]
    candidates = [
        p
        for p in all_candidates
        if f"/{version}/" in str(p)
    ]
    if candidates:
        return sorted(candidates)[0]

    major_minor = ".".join(version.split(".")[:2])
    family_candidates = [
        p
        for p in all_candidates
        if f"/{major_minor}." in str(p)
    ]
    if family_candidates:
        return sorted(family_candidates)[-1]

    if all_candidates:
        return sorted(all_candidates)[-1]

    raise FileNotFoundError(f"{group_path} {version}")


SOURCE_JARS = {
    "ui": pick_sources("androidx.compose.ui/ui-android", COMPOSE_VERSION),
    "ui_graphics": pick_sources("androidx.compose.ui/ui-graphics", COMPOSE_VERSION),
    "ui_text": pick_sources("androidx.compose.ui/ui-text-android", COMPOSE_VERSION),
    "foundation_layout": pick_sources(
        "androidx.compose.foundation/foundation-layout-android",
        COMPOSE_VERSION,
    ),
    "material3": pick_sources("androidx.compose.material3/material3-android", MATERIAL3_VERSION),
}

SOURCE_PREFIXES = {
    "ui": (
        "commonMain/androidx/compose/ui/Alignment.kt",
        "commonMain/androidx/compose/ui/AtomicReference.kt",
        "commonMain/androidx/compose/ui/ComposeUiFlags.kt",
        "commonMain/androidx/compose/ui/ComposedModifier.kt",
        "commonMain/androidx/compose/ui/Expect.kt",
        "commonMain/androidx/compose/ui/FrameRate.kt",
        "commonMain/androidx/compose/ui/FrameRateCategory.kt",
        "commonMain/androidx/compose/ui/KeepScreenOn.kt",
        "commonMain/androidx/compose/ui/Modifier.kt",
        "commonMain/androidx/compose/ui/MotionDurationScale.kt",
        "commonMain/androidx/compose/ui/SensitiveContent.kt",
        "commonMain/androidx/compose/ui/SessionMutex.kt",
        "commonMain/androidx/compose/ui/UiComposable.kt",
        "commonMain/androidx/compose/ui/ZIndexModifier.kt",
        "commonMain/androidx/compose/ui/alignment/",
        "commonMain/androidx/compose/ui/unit/",
    ),
    "ui_graphics": (
        "commonMain/androidx/compose/ui/geometry/",
        "commonMain/androidx/compose/ui/graphics/",
    ),
    "ui_text": (
        "commonMain/androidx/compose/ui/text/",
    ),
    "foundation_layout": (
        "commonMain/androidx/compose/foundation/layout/",
    ),
    "material3": (
        "commonMain/androidx/compose/material3/",
    ),
}


EXCLUDE_NAME_PREFIXES = (
    "Android",
    "Platform",
)

EXCLUDE_EXACT = {
    "Actual",
    "JvmDefaultWithCompatibility",
    "Visible",
    "AtomicInt",
    "ClassHelpers",
    "StringHelpers",
    "InlineClassUtils",
}

EXCLUDE_NAME_SUFFIXES = (
    "Defaults",
    "Tokens",
    "Override",
    "OverrideScope",
    "Node",
    "ModifierNode",
    "Modifier",
    "Helper",
    "Helpers",
    "Painter",
    "Manager",
    "State",
    "Scope",
    "Info",
    "Result",
    "Delegate",
    "Controller",
    "Observer",
    "Provider",
    "Policy",
    "Owner",
)

KEEP_EXACT = {
    "Modifier",
    "ComposedModifier",
    "ZIndexModifier",
    "AspectRatio",
    "Offset",
    "Size",
    "Padding",
    "WindowInsetsPadding",
    "WindowInsetsSize",
    "WindowInsets",
}


def parse_kotlin_api(text: str, source_name: str) -> dict[str, set[str]]:
    text = re.sub(r"/\*.*?\*/", "", text, flags=re.S)
    text = re.sub(r"//.*", "", text)

    api: dict[str, set[str]] = defaultdict(set)
    lines = text.splitlines()
    pending_internal = False
    current_type = None
    current_is_enum = False
    brace_depth = 0
    global_depth = 0

    for line in lines:
        stripped = line.strip()
        if not stripped:
            continue
        if stripped.startswith("internal ") or " internal " in f" {stripped} ":
            pending_internal = True
        if "private " in f" {stripped} ":
            pending_internal = True

        if global_depth == 0:
            type_match = re.match(
                r"(?:@[A-Za-z0-9_.]+(?:\([^)]*\))?\s+)*(?:public\s+)?(?:expect\s+|actual\s+)?(?:(data|value|sealed|enum|annotation)\s+)?(class|interface|object)\s+([A-Za-z0-9_]+)",
                stripped,
            )
            if type_match:
                modifier = type_match.group(1)
                name = type_match.group(3)
                if should_keep_symbol(name, pending_internal):
                    current_type = name
                    current_is_enum = modifier == "enum"
                    api[current_type]
                    brace_depth = stripped.count("{") - stripped.count("}")
                else:
                    current_type = None
                    current_is_enum = False
                    brace_depth = 0
                global_depth += stripped.count("{") - stripped.count("}")
                pending_internal = False
                continue

        if current_type:
            prop_match = re.match(
                r"(?:override\s+)?(?:val|var)\s+([A-Za-z0-9_]+)\s*:\s*([^=({]+)",
                stripped,
            )
            if prop_match and not pending_internal:
                api[current_type].add(f"prop:{prop_match.group(1)}")

            enum_entry_match = re.match(r"([A-Z][A-Za-z0-9_]*)\s*[,;(]?", stripped)
            if enum_entry_match and current_is_enum:
                api[current_type].add(f"enum:{enum_entry_match.group(1)}")

            brace_depth += stripped.count("{") - stripped.count("}")
            if brace_depth <= 0:
                current_type = None
                current_is_enum = False
                brace_depth = 0
        pending_internal = False
        global_depth += stripped.count("{") - stripped.count("}")

    top_level_key = Path(source_name).name.removesuffix(".kt").removesuffix(".android").removesuffix(".commonStubs")
    if should_keep_symbol(top_level_key, False):
        api[top_level_key]

    return api


def should_keep_symbol(name: str, internal_or_private: bool) -> bool:
    if internal_or_private:
        return False
    if name in EXCLUDE_EXACT:
        return False
    if name in KEEP_EXACT:
        return True
    if name.endswith(EXCLUDE_NAME_SUFFIXES):
        return False
    return not name.startswith(EXCLUDE_NAME_PREFIXES)


def parse_proto_schema(path: Path) -> dict[str, set[str]]:
    text = path.read_text()
    schema: dict[str, set[str]] = defaultdict(set)

    for match in re.finditer(r"message\s+([A-Za-z0-9_]+)\s*\{(.*?)\n\}", text, flags=re.S):
        name = match.group(1)
        body = match.group(2)
        base = name.removesuffix("Proto")
        schema[base]
        for field_match in re.finditer(
            r"(?:repeated\s+)?[A-Za-z0-9_.]+\s+([A-Za-z0-9_]+)\s*=\s*\d+;",
            body,
        ):
            schema[base].add(f"prop:{snake_to_lower_camel(field_match.group(1))}")

    for match in re.finditer(r"enum\s+([A-Za-z0-9_]+)\s*\{(.*?)\n\}", text, flags=re.S):
        name = match.group(1)
        body = match.group(2)
        base = name.removesuffix("Proto")
        schema[base]
        for enum_match in re.finditer(r"([A-Z][A-Z0-9_]+)\s*=\s*\d+;", body):
            schema[base].add(f"enum:{enum_match.group(1)}")

    return schema


def snake_to_lower_camel(value: str) -> str:
    parts = value.split("_")
    return parts[0] + "".join(part.capitalize() for part in parts[1:])


def load_source_api() -> dict[str, set[str]]:
    api: dict[str, set[str]] = defaultdict(set)
    for layer, jar_path in SOURCE_JARS.items():
        prefixes = SOURCE_PREFIXES[layer]
        with zipfile.ZipFile(jar_path) as jar:
            for name in jar.namelist():
                if not name.startswith("commonMain/") or not name.endswith(".kt"):
                    continue
                if prefixes and not name.startswith(prefixes):
                    continue
                if "/internal/" in name:
                    continue
                source_name = Path(name).name.removesuffix(".kt")
                if source_name.endswith(".commonStubs"):
                    source_name = source_name.removesuffix(".commonStubs")
                if not should_keep_symbol(source_name, False):
                    continue
                parsed = parse_kotlin_api(jar.read(name).decode("utf-8"), name)
                for key, values in parsed.items():
                    api[key].update(values)
                if should_keep_symbol(source_name, False):
                    api[source_name]
    return api


def load_proto_schema() -> dict[str, set[str]]:
    schema: dict[str, set[str]] = defaultdict(set)
    for path in PROTO_ROOT.rglob("*.proto"):
        parsed = parse_proto_schema(path)
        for key, values in parsed.items():
            schema[key].update(values)
    return schema


def main() -> int:
    source_api = load_source_api()
    proto_schema = load_proto_schema()

    missing_types = sorted(set(source_api) - set(proto_schema))
    extra_types = sorted(set(proto_schema) - set(source_api))

    print("SOURCE_JARS")
    for layer, path in SOURCE_JARS.items():
        print(f"{layer}: {path}")
    print()

    print("MISSING_TYPES")
    for name in missing_types:
        print(name)
    print()

    print("EXTRA_TYPES")
    for name in extra_types:
        print(name)
    print()

    print("FIELD_DIFFS")
    for name in sorted(set(source_api) & set(proto_schema)):
        src = source_api[name]
        proto = proto_schema[name]
        missing_members = sorted(src - proto)
        if missing_members:
            print(f"[{name}]")
            for item in missing_members:
                print(f"  missing_in_proto: {item}")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
