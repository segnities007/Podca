## 概要

<!-- 何を・なぜ変更したか -->

## チェックリスト（Podca）

- [ ] **Remote** を触った場合: **proto (`remote-core`) → `remote-player-compose` → `remote-creation` → テスト** の順を守った（[AGENTS.md](../AGENTS.md)）
- [ ] **Remote** を触った場合: [ANDROIDX_REMOTE_MAP.md](../sdui/remote/ANDROIDX_REMOTE_MAP.md) を更新した（または変更なしの理由を PR 説明に書いた）
- [ ] **広い SDUI**（`material3.*` / `foundation.*`）を触った場合: **protocol → studio → player** を揃えた
- [ ] **マーケ／デモ**を触った場合: ツリーは **`sdui/marketing`** に置き、[計画書 Track R R3](../docs/design/PRODUCT_COMPLETION_PLAN_v1.md#11-track-r--リリース完成必須) に反していない
- [ ] `./gradlew remoteVerifyJvm` がローカルで通る（Remote 変更時は必須）
- [ ] 関連する `:sdui:player:engine:jvmTest` 等を実行した（該当時）

## 計画・ドキュメント

- [ ] 変更が [PRODUCT_COMPLETION_PLAN_v1.md](../docs/design/PRODUCT_COMPLETION_PLAN_v1.md) の Track R / Phase に影響する場合、PR 説明に記載した
