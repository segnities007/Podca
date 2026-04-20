# AI 一括実行用マスタープロンプト（Podca / Track R 想定）

以下のブロックを **そのままコピー**して AI に渡す。前提: **1 セッションで可能な範囲を順序実行**し、無理なターゲット（例: 現状壊れている `compileKotlinJs`）は **スキップして理由を報告**する。

---

## コピー用プロンプト（ここから）

あなたは **Podca** リポジトリの実装エージェントである。次を **この順序で** 実行し、各フェーズ末に **検証コマンド**を走らせること。**並列より順序**を優先する（後段が前段に依存）。

### 絶対に読む（実行前）

1. リポジトリルートの **`AGENTS.md`** 全文（特に **「プロダクトを完璧に近づけるための指示」** と Demo app・Remote 拡張順）。
2. **`docs/design/PRODUCT_COMPLETION_PLAN_v1.md`** の §1.1 **Track R** と **§8 付録 A**（受入チェックリスト）。
3. **`docs/design/PRODUCT_STATUS_INCOMPLETE_v1.md`**（現状ギャップ）。
4. **`docs/design/DETAILED_DESIGN_v1.md`** の §14（`loadDocument` / decode と例外）。

### グローバル制約

- **変更は Track R と直接関係する範囲に限定**する。無関係リファクタ・大規模整形をしない。
- **SDUI ファースト**: マーケ UI を `composeApp` の Material3 で増やさない。`sdui/marketing` とサーバー本流を壊さない。
- **Remote 変更時**は必ず **proto → `remote-player-compose` → `remote-creation` → テスト → ANDROIDX_REMOTE_MAP 更新**（AGENTS どおり）。
- **Track S**（マップの △ をすべて解消する等）は **スコープ外**。触れない。

### フェーズ 0 — 現状把握（コード変更なし）

- `composeApp` の `PodcaIntro`（または `loadDocument` 経路）で **try/catch の有無**、サーバーの **CORS**（`anyHost` か）を確認し、**1 段落で要約**する。

### フェーズ 1 — クライアント堅牢化（Track R **R4**）

- `loadDocument` / `decodePodcaDocument` を呼ぶ箇所を **try/catch** で囲み、失敗時は **既存のフォールバック UI**（例: `CallbackIntroScreen`）へ落とすか、同等のユーザー向けエラーにする。**クラッシュしない**こと。
- 詳細設計 §14 と整合させる。

### フェーズ 2 — サーバー（Track R **R5**）

- `server/src/main/kotlin/com/segnities007/yoyo/Application.kt` の CORS を **`anyHost` から脱却**し、**環境変数または設定で許可オリジンを列挙**できるようにする（開発用デフォルトは `http://localhost:8080` 等でもよいが、**本番で anyHost のまま**にしない）。
- `GET /api/podca/marketing-document` と `welcome-document` に **最大レスポンスサイズ**（例: 16 MiB）を超えたら **413 または 400** を返す。
- **`:server:build`** が通ること。

### フェーズ 3 — マーケ本流の確認（Track R **R3**、主にレビュー＋小修正）

- クライアントが **HTTP 取得を先**にし、`encodePodcaMarketingDocument` が **フォールバックのみ**であることをコードで確認。違反があれば **最小差分で修正**。

### フェーズ 4 — CI 回帰（Track R **R1-min / R2**）

- 次が **すべて成功**すること（変更後に実行）:

```bash
./gradlew remoteVerifyJvm :sdui:player:engine:jvmTest :server:build :composeApp:compileKotlinJvm --no-daemon
```

- **`:composeApp:compileKotlinJs` / `compileKotlinWasmJs`** が失敗する場合は **修正を試み、無理ならスキップして理由を報告**（CI に載せない判断を明示）。**無限に時間を使わない**。

### フェーズ 5 — ドキュメント同期

- `docs/design/SMOKE_TRACK_R.md` に、実装に合わせた **1 行以上**の追記（オフライン・フォールバック列など）。
- `docs/design/PRODUCT_STATUS_INCOMPLETE_v1.md` の表を、**満たした項目は「済」または削除**に更新（未完了だけ残す）。
- ルート `README.md` か `docs/README.md` のどちらか一方でよいので、**CORS 環境変数**や **文書サイズ上限**が分かる **1〜3 行**を追加。

### フェーズ 6 — 出力形式（必須）

最後に **Markdown で次を出力**すること:

1. **変更ファイル一覧**（パスだけ）
2. **各 Track R 項目（R3〜R6 中心）を満たしたか** Yes/No 表
3. **未完了・スキップした項目**と理由（あれば）
4. **推奨コミットメッセージ** 1 行（英語でも日本語でもよい）

### 禁止

- `sdui/marketing` の大規模ツリーを `composeApp` に複製する。
- Track S（Remote マップ全 △ 解消）に手を広げる。
- `wire_opset_version` ポリシー違反。
- 依頼と無関係なモジュールの「ついでリファクタ」。

---

## コピー用プロンプト（ここまで）

### メモ（人間用）

- **R1-full**（Android / iOS の CI）や **ブランチ保護**は GitHub 設定が必要なため、AI だけでは完結しない。プロンプト結果の表で **Human required** とマークする欄を設けてもよい。
- より狭いスコープにしたい場合は、上記プロンプトから **フェーズ 2 だけ**等を切り出して渡す。
