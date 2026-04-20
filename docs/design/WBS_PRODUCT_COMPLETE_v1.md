# Podca プロダクト完成 WBS（Work Breakdown Structure）v1

| 項目 | 内容 |
|------|------|
| 目的 | プロダクト完成に必要な作業を **分解可能なワークパッケージ** まで列挙する |
| 上位文書 | [BASIC_DESIGN_v1.md](./BASIC_DESIGN_v1.md)、[DETAILED_DESIGN_v1.md](./DETAILED_DESIGN_v1.md) |
| 参照 | [AGENTS.md](../../AGENTS.md)、[ANDROIDX_REMOTE_MAP.md](../../sdui/remote/ANDROIDX_REMOTE_MAP.md)、[sdui/ARCHITECTURE.md](../../sdui/ARCHITECTURE.md) |

**凡例**: 番号 `x.y.z` は階層。各末端タスクは **担当モジュール**・**成果物**が取りうる粒度。優先度はチームで `P0`（リリース必須）〜 `P3`（あればよい）を割り当てること。

**実行計画（文書完成 v1.3.1）**: [PRODUCT_COMPLETION_PLAN_v1.md](./PRODUCT_COMPLETION_PLAN_v1.md) — **フェーズ・ゲート・Track R**、CI・PR・CONTRIBUTING、§0.1 完成宣言・付録 F。  
**未完の整理（ステータス）**: [PRODUCT_STATUS_INCOMPLETE_v1.md](./PRODUCT_STATUS_INCOMPLETE_v1.md) — プロダクトは **未完成**；本 WBS は **残作業の地図**。

---

## 1.0 ガバナンス・スコープ

### 1.1 プロダクト定義

- **1.1.1** 「完成」の定義文書化（基本設計 §2 の A/B 柱＋本 WBS のどこまでを v1 リリース必須にするか）。
- **1.1.2** 対象プラットフォーム一覧の固定（`composeApp` の `android` / `ios*` / `jvm` / `js` / `wasmJs`）。
- **1.1.3** 非目標（Non-goals）の明文化（例: 独自バイナリ互換 AndroidX Wire、本番ホスティング SLA 等）。

### 1.2 プロセス・品質ゲート

- **1.2.1** PR レビューチェックリスト（proto → player → creation 順、マップ更新、テスト同梱）。
- **1.2.2** `remoteVerifyJvm` を **必須 CI** に接続（GitHub Actions / GitLab CI 等の YAML 作成・`main` 保護）。
- **1.2.3** `:sdui:player:engine:jvmTest` 等、広い SDUI の **最低回帰セット**を CI に定義。
- **1.2.4** マージ前の **マルチターゲット smoke**（例: `compileKotlinIosArm64` / `wasmJsBrowserDevelopmentWebpack` のいずれかをローテーション）。
- **1.2.5** リリースタグ付け規約（SemVer、`wire_opset_version` 変更時のリリースノート欄）。

### 1.3 課題管理

- **1.3.1** [ANDROIDX_REMOTE_MAP.md](../../sdui/remote/ANDROIDX_REMOTE_MAP.md) の **△ / —** ごとに Issue を起票し P 付け。
- **1.3.2** 広い SDUI の **未実装ノード**一覧を Issue 化（Player の `else` フォールスルーに頼る期間を限る）。

---

## 2.0 広い SDUI（Protocol → Studio → Player）

### 2.1 `:sdui:protocol`

- **2.1.1** `NodeProto` 周辺の **後方互換ポリシー**文書化（フィールド追加のみ / 意味変更時のバージョン）。
- **2.1.2** `material3.*` proto の **Compose API 対応表**の欠落行を埋める（[sdui/protocol/README.md](../../sdui/protocol/README.md) 方針）。
- **2.1.3** `foundation.*` proto 同上。
- **2.1.4** `ui.*` proto 同上。
- **2.1.5** **アクセシビリティ**関連フィールドの監査（Semantics、`contentDescription` 等）と Player 反映状況の突合。
- **2.1.6** **インタラクション**契約の監査（`ActionBindingProto`、`expects_state_update` の利用方針）。
- **2.1.7** 大ペイロード（画像 bytes 等）を proto に載せる場合の **サイズガイドライン**（詳細設計 §8 と整合）。

### 2.2 `:sdui:studio:core` および `studio` 公開 API

- **2.2.1** `PodcaStudio.encode` 経路の **エラー処理**（不正ツリー、サイクル検出があれば文書化）。
- **2.2.2** Applier／Composition 層の **回帰テスト**拡充。
- **2.2.3** Studio 側 **デバッグ用**（ツリー表示、hex dump 等）は任意だが方針決定。

### 2.3 `:sdui:studio:ui-core` / `ui-foundation` / `ui-material3`

- **2.3.1** 各 `Podca*` DSL と proto の **1:1 対応表**（欠けている composable の洗い出し）。
- **2.3.2** **未実装 DSL** の実装タスク化（Protocol 先行）。
- **2.3.3** **Modifier** チェーンの表現力ギャップ洗い出し（ZIndex、padding、clickable の順序等）。

### 2.4 `:sdui:player:engine`

- **2.4.1** `decodePodcaDocument` の **例外安全ラッパ**（設計 §14 準拠）を `PodcaRuntime` または `composeApp` 方針で統一。
- **2.4.2** `PodcaStateStore`：**部分パッチ**が必要か検討（現状はルート `encoded_root` 全差し替え）。
- **2.4.3** `PodcaActionDispatcher`：**複数ハンドラ**／ミドルウェア（ログ、計測）の要否。
- **2.4.4** **リビジョン競合**（乱序 `state_patch`）のポリシー定義と実装。

### 2.5 `:sdui:player:ui-core` / `ui-foundation` / `ui-material3`

- **2.5.1** `PodcaRenderMaterial3Node` の **ノード種別網羅**（Button、Card、Scaffold、Tab、TextField、Snackbar…）と **未対応時の UX**（プレースホルダ表示の要否）。
- **2.5.2** **テキスト入力**系（IME、キーボード）の各ターゲット動作確認。
- **2.5.3** **スクロール**・**Lazy** 系ノードの有無と実装計画。
- **2.5.4** **テーマ**（`MaterialTheme`）と SDUI の **色トークン**接続方針の完成（トークン表＋Player 実装）。

### 2.6 `:sdui:player:player`

- **2.6.1** `PodcaPlayer` の **Modifier 引数**（padding 等）の設計確定。
- **2.6.2** **ローディング／空状態**のホスト契約（SDUI 内で表現 vs ホスト `Box`）。

### 2.7 `:sdui:marketing`

- **2.7.1** 全タブ（`tab=0..3`）の **Studio ツリー**が単一ソースで保守されていることの検証。
- **2.7.2** **アクション**（タブ切替、外部リンク等）の `action_id` と `composeApp` 登録の **対応表**。
- **2.7.3** マーケ文書サイズ・画像資産の **最適化**（Remote `DRAW_IMAGE` とのバランス）。

---

## 3.0 Podca Remote（AndroidX SSoT）

共通ワークパッケージ（各機能に **繰り返し適用**）:

- **3.T.1** upstream（AndroidX `compose/remote`）の **API 意味・引数**の読み取りメモ。
- **3.T.2** `remote-core`: `.proto` フィールド／opcode、`wire_opset_version` 方針判定。
- **3.T.3** `remote-player-compose`: `Canvas` / `DrawScope` 実装、共通 `commonMain` で全ターゲットビルド。
- **3.T.4** `remote-creation`: DSL ヘルパ、**Wire 往復テスト**（`remote-creation` commonTest）。
- **3.T.5** `remote-player-compose` **jvmTest**（画像デコード等 JVM 依存がある場合）。
- **3.T.6** [ANDROIDX_REMOTE_MAP.md](../../sdui/remote/ANDROIDX_REMOTE_MAP.md) の行更新（✓/△/—、差分理由）。
- **3.T.7** `RemoteCanvasWireFixtures` に **共有 PNG** を集約（重複禁止）。

### 3.1 既存 ✓ 領域の維持

- **3.1.1** save/restore、transform、clip、基本図形、path fill/stroke（blend/alpha）、arc、line、gradient、polyline、pointer rect の **回帰テスト維持**。
- **3.1.2** `DRAW_IMAGE` の **ColorFilter 系**（tint / lighting / matrix）の組み合わせテスト拡充。

### 3.2 `drawBitmap` 周辺の残ギャップ（△）

- **3.2.1** **Path への `ColorFilter`**（ワイヤ設計：path 単位の colorFilter vs グローバル）。
- **3.2.2** **`PathEffect` / dash**（verb 列拡張、player の `PathEffect` 生成）。
- **3.2.3** **汎用 `RemotePaint` バンドル**の要否判断（AndroidX `usePaint` との整合、直列フィールドとの移行戦略）。
- **3.2.4** 上記の **creation ヘルパ**と **ドキュメント**（proto コメント、マップ）。

### 3.3 `drawText` / `drawTextRun`（△）

- **3.3.1** **非 Android** での `includeFontPadding` 相当の制御（CMP API 調査 → 可能ならワイヤ追加）。
- **3.3.2** **ピクセル一致**を目標にするか、**ベストエフォート**で固定するかの合意とゴールデンテスト方針。
- **3.3.3** `DRAW_TEXT_RUN` の **RTL・Bidi** 境界ケーステスト。

### 3.4 `drawTweenPath`（△）

- **3.4.1** AndroidX **`Paint` バンドル**に相当するフィールド集合の **ギャップ分析**。
- **3.4.2** stroke 以外（fill）の tween 要否。
- **3.4.3** **remote float** からの `t` 更新時の **recomposition** コスト評価。

### 3.5 `drawRoundedPolygon` / morph（△）

- **3.5.1** **非凸**の扱いを **✓ に近づける**か、**文書化されたフォールバックのまま**にするかの製品判断。
- **3.5.2** graphics-shapes 真円 fillet との **差分許容**または実装拡張。

### 3.6 `drawTextOnPath` / circle / anchored（△）

- **3.6.1** AndroidX **`flags` 全文**のワイヤ化スコープ決定。
- **3.6.2** **Alignment/Placement** enum のマッピング表。
- **3.6.3** **曲線 path** 上のテキスト（現状サブセット外）のロードマップ。

### 3.7 `drawConditionally`（△）

- **3.7.1** AndroidX **RemoteFloat トークン**との **意味的一致**を取るか、Podca サブセットのまま **マップに明記**するかのクローズ。
- **3.7.2** **NaN 比較**の追加テスト（operand flags 全組み合わせ）。

### 3.8 `drawToOffscreenBitmap`（△）

- **3.8.1** **RemoteBitmap** 相当の **ライフサイクル**（GPU 解放）を追うか否かの判断。
- **3.8.2** プール **LRU** 挙動の **負荷テスト**（メモリ上限環境）。

### 3.9 `loop`（△）

- **3.9.1** 「CONDITIONAL 内 LOOP は展開コストが減らない」**リンター**または Studio 側警告の検討。
- **3.9.2** `loop_expand_max_repeat_per_block` の **運用推奨値**ドキュメント。

### 3.10 `startStateLayout` / `endStateLayout`（△）

- **3.10.1** **RecordingModifier バイト列**を将来載せるか、Semantics サブセットで **v1 完了**とするかの判断。
- **3.10.2** **アクティブ id 未設定**時のデフォルト解決の **E2E テスト**（`composeApp`）。

### 3.11 `remote.Node`（糖衣）

- **3.11.1** 糖衣 → op 列の **機械的変換**が必要かの判断とスパイク。
- **3.11.2** 糖衣のみに表現を増やさない **レビュールール**運用。

### 3.12 横断タスク

- **3.12.1** **ヒットテスト**（pointer）と clip/transform の **結合テスト**。
- **3.12.2** **Wasm/JS** での **Float精度**・**ImageBitmap** 差分の既知問題表。
- **3.12.3** **iOS** での **Canvas** 制限の既知問題表。
- **3.12.4** `SupportedWireOpsetVersion` とサーバー encode の **バージョン協調**（将来の capability 交渉の素地）。

---

## 4.0 サンプルアプリ・サーバー・共有

### 4.1 `:composeApp`

- **4.1.1** **全ターゲット**の `assemble` / `browserDevelopment` の手順をルート README に一本化。
- **4.1.2** `MarketingHttp` expect/actual の **本番 URL**・**開発 URL**・**エミュレータ**向け設定表。
- **4.1.3** **`loadDocument` try/catch** とフォールバック（`CallbackIntroScreen`）の **全タブ**検証。
- **4.1.4** **Web**: URL 同期（`WebMarketingHistory` 等）と **ブラウザ戻る**の E2E。
- **4.1.5** **Deep link** 要否と実装範囲。
- **4.1.6** **ProGuard / R8**（Android リリース）ルールの整備。
- **4.1.7** **App Store / Play** 提出を見据えたアイコン・権限・プライバシー文言（プロダクト方針次第）。

### 4.2 `:server`

- **4.2.1** **本番 CORS**（`anyHost()` の撤廃、許可オリジンリスト）。
- **4.2.2** **レート制限**・**最大レスポンスサイズ**（詳細設計 §8 の推奨値の実装）。
- **4.2.3** **ヘルスチェック**（`/api/health`）の監視接続例。
- **4.2.4** **静的配信**（`PODCA_SITE_ROOT`）の **Dockerfile** またはデプロイマニフェスト。
- **4.2.5** **TLS** 終端（リバプロ vs Ktor）の運用設計。

### 4.3 `:shared`

- **4.3.1** 定数・URL・ビルドフラグの **単一ソース化**レビュー。
- **4.3.2** クライアント／サーバーで **重複する encode** 経路がないことの確認（AGENTS 準拠）。

---

## 5.0 品質保証（QA）

### 5.1 テストピラミッド

- **5.1.1** **単体**: remote-core / creation / player-engine / 各 renderer。
- **5.1.2** **契約**: Wire round-trip、**ゴールデン** op 列のスナップショット（採用する場合）。
- **5.1.3** **統合**: `server` 起動＋`composeApp` jvm から HTTP で取得→decode（可能なら）。
- **5.1.4** **手動**: 全タブ・全ターゲットの **スモークシート**（表形式）。

### 5.2 非機能テスト

- **5.2.1** **大ドキュメント**（子ノード数・深さ上限近傍）の **メモリ・時間**計測。
- **5.2.2** **Remote** 大 `LOOP` 展開時の **フレーム落ち**計測（JVM/Desktop）。
- **5.2.3** **アクセシビリティ**監査（TalkBack / VoiceOver の対象範囲）。

---

## 6.0 セキュリティ・プライバシー・法令

- **6.1** **SDUI バイト列の署名**（任意）と改ざん検知の要否。
- **6.2** **PII** を `NodeProto` に載せない方針とサーバー側マスキング。
- **6.3** **依存ライブラリ**の脆弱性スキャン（OWASP Dependency-Check 等）の CI 化。
- **6.4** ライセンス表（`LICENSE`、サードパーティ NOTICE）の完成。

---

## 7.0 ドキュメント・開発者体験

- **7.1** ルート README の **クイックスタート**（clone → server → wasm ビルド → 閲覧）の **15 分以内**検証。
- **7.2** **トラブルシューティング**（ポート占有、CORS、Wasm キャッシュ）。
- **7.3** **CONTRIBUTING**（ブランチ戦略、コミットメッセージ、必須 Gradle タスク）。
- **7.4** **ADR**（Architecture Decision Record）フォルダの導入と最初の 5 本（Remote 3 モジュール、Hybrid SDUI、サーバー本流、wire_opset、糖衣位置づけ）。
- **7.5** **API ドキュメント**（Dokka）の公開要否とホスティング。

---

## 8.0 リリース・運用

- **8.1** **バージョン番号**（ライブラリ `sdui:*` を Maven に出すか、モノレポのみか）。
- **8.2** **CHANGELOG** の運用。
- **8.3** **サンプル server** の **デプロイ手順**（ステージング URL を README に記載するか）。
- **8.4** **ロールバック**手順（`wire_opset_version` 不一致時のサーバー／クライアント互換）。

---

## 9.0 掃除・負債

- **9.1** `server/bin/` 等 **生成物のコミット**があれば `.gitignore` 整理と削除。
- **9.2** **非推奨 API** の列挙と削除スケジュール。
- **9.3** **警告**（compiler warnings）のゼロ化計画（remote-core 生成コード除く方針含む）。

---

## 10.0 WBS 運用

- **10.1** 本ファイルを **四半期ごと**に見直し、完了行に ~~打ち消し線~~ または「Done yyyy-mm-dd」を追記。
- **10.2** Issue ツール（GitHub Issues 等）の **ラベル**（`area:remote` `area:protocol` …）と本 WBS 番号の **相互リンク**。

---

## 改訂履歴

| 版 | 日付 | 変更 |
|----|------|------|
| v1.0 | 2026-04-19 | 初版。ガバナンス・広い SDUI・Remote（マップ行ベース）・composeApp/server・QA・セキュリティ・Doc・リリース・負債・運用。 |
