# Contributing to Podca

開発の厳格なルールは **[AGENTS.md](AGENTS.md)** を参照してください。

## すぐ使うコマンド

```bash
./gradlew remoteVerifyJvm
./gradlew :sdui:player:engine:jvmTest
./gradlew :server:build
./gradlew :composeApp:compileKotlinJvm
```

## PR

[`.github/pull_request_template.md`](.github/pull_request_template.md) に従い、特に **Remote** は **proto → player → creation** の順で拡張してください。

## プロダクト完成計画

**[docs/design/PRODUCT_COMPLETION_PLAN_v1.md](docs/design/PRODUCT_COMPLETION_PLAN_v1.md)**（**v1.3.1 文書完成**、Track R・フェーズ・CI・付録 A–F）を実行のマスタとしてください。
