# Podca

Server-Driven UI (SDUI) for Kotlin Multiplatform / Compose Multiplatform.

Podca lets you describe UI as a tree (nodes + actions) on the Studio side, ship it as bytes, and render it on the Player side.

## Repository Layout

- `composeApp/`: The Compose Multiplatform sample application (marketing site + SDUI demo in `podcaIntroMain/` and shared web helpers in `webMain/`).
- `sdui/`: Protocol, Studio, and Player libraries（モジュール一覧は [sdui/README.md](sdui/README.md)、ランタイム責務は [sdui/ARCHITECTURE.md](sdui/ARCHITECTURE.md)、Player の描画パイプラインは [sdui/player/README.md](sdui/player/README.md)）。
- `docs/`: プロダクトの目的・技術スタック要約と、各 README への索引（[docs/README.md](docs/README.md)）。
- `server/`: Ktor server (health API, optional static hosting of the web build).
- `iosApp/`: iOS entry point that hosts the ComposeApp UI.
- `.cursor/skills/`: Cursor 向けに置いた [Android 公式 Skills](https://github.com/android/skills)（AGP / Compose 移行 / Navigation 3 など）。エディタがプロジェクトスキルを読み込む設定なら、エージェントやインライン補助のコンテキストとして利用できます（Gemini 用の `android skills add` とは別経路）。

## Architecture (High Level)

Gradle モジュール（`settings.gradle.kts` の `include` と一致）:

- **`sdui:protocol`**: Wire / Protobuf の SDUI ノード・アクション定義。
- **`sdui:studio:*`**: オーサリング用（`Podca*` DSL でツリーを組み立て、`ByteArray` にエンコード）。公開 API は主に **`sdui:studio:studio`**。
- **`sdui:player:*`**: 再生用（バイト列をデコードして Compose で描画）。アプリから使うのは主に **`sdui:player:player`** と **`sdui:player:engine`**。

マーケティング用のサンプルツリーは **`sdui:marketing`** にまとまっています（`AGENTS.md` のデモ方針と同じ）。

## アプリの起動方法

このリポジトリで起動できるのは次の **2 つ**です（別プロセスです）。

| 何を起動するか | モジュール | 役割 |
|----------------|------------|------|
| サンプルアプリ（SDUI デモ UI） | `composeApp` | Android / iOS / Desktop / ブラウザで同じ `App()` を表示 |
| API ＋ 任意で Web 静的配信 | `server` | Ktor。`/api/health` など。サイトファイルを置けば `/` で配信も可 |

### composeApp の起動（サンプルアプリ）

**共通**: リポジトリのルートでコマンドを実行します（Windows は `gradlew.bat`）。

1. **Android**  
   - ターミナル: `./gradlew :composeApp:assembleDebug` でビルドし、Android Studio の Run で `composeApp` を選んで端末／エミュレータにインストールして起動。  
   - または Studio からそのまま Run（推奨）。

2. **デスクトップ（JVM）**  
   ```shell
   ./gradlew :composeApp:run
   ```  
   Windows: `.\gradlew.bat :composeApp:run`

3. **Web ブラウザ（開発サーバ）**  
   - Wasm（推奨）: `./gradlew :composeApp:wasmJsBrowserDevelopmentRun`  
   - JS: `./gradlew :composeApp:jsBrowserDevelopmentRun`  
   起動後、Gradle のログに出る URL（通常は `http://localhost:...`）をブラウザで開きます。

4. **iOS**  
   - `iosApp` フォルダを Xcode で開き、スキームを選んで Run。  
   - Compose の UI は `ComposeApp` フレームワーク経由で表示されます（`iosApp/iosApp/ContentView.swift`）。

### server の起動（Ktor）

サイトを配信しない場合でもサーバーだけ動かせます。

```shell
./gradlew :server:run
```

Windows: `.\gradlew.bat :server:run`  
デフォルトで **ポート 9090**（`server` モジュールの `SERVER_PORT`）。Webpack の開発サーバ（`:composeApp:wasmJsBrowserDevelopmentRun` などがよく使う **8080**）などと被りにくい番号にしてあります。

- 動作確認: `http://localhost:9090/api/health` → `ok`
- **別ポートにしたい場合**: `PODCA_SERVER_PORT=8088 ./gradlew :server:run`（Windows PowerShell: `$env:PODCA_SERVER_PORT=8088; .\gradlew.bat :server:run`）。環境変数 `PORT` でも上書きできます（1〜65535）。
- ビルド済みの Web 一式を同じプロセスで配信したい場合は、README 後半の **「Serve the build with Ktor」** と **`PODCA_SITE_ROOT`** の説明を参照してください。

---

## Marketing site (Web)

The web build is a single-page app with **hash routes** that stay in sync with in-app navigation (back/forward and deep links):

| Section | URL fragment |
|---------|----------------|
| Home | `#home` |
| Architecture | `#architecture` |
| Get started | `#get-started` |
| Examples | `#examples` |

On Android, Desktop, and iOS the same `MarketingApp()` runs without URL syncing.

Entry composable: `App()` in `composeApp/src/podcaIntroMain/kotlin/.../App.kt`.

### Development (browser)

Wasm (recommended):

```shell
./gradlew :composeApp:wasmJsBrowserDevelopmentRun
```

Windows:

```shell
.\gradlew.bat :composeApp:wasmJsBrowserDevelopmentRun
```

JS (slower, more compatible):

```shell
./gradlew :composeApp:jsBrowserDevelopmentRun
```

Windows:

```shell
.\gradlew.bat :composeApp:jsBrowserDevelopmentRun
```

### Production static output

```shell
./gradlew :composeApp:wasmJsBrowserDistribution
```

Outputs under `composeApp/build/dist/wasmJs/productionExecutable/` (includes `composeApp.js`, wasm binaries, `index.html`, and `styles.css` copied from `composeApp/src/webMain/resources/`).

For a JS production bundle, use `:composeApp:jsBrowserDistribution` (output under `composeApp/build/dist/js/productionExecutable/`).

Host that folder on any static file server (S3, nginx, GitHub Pages, Netlify, etc.). Hash routing does not require server-side path rewrites.

### Serve the build with Ktor (optional)

1. Build the Wasm distribution (see above), or run:

   ```shell
   ./gradlew :server:preparePodcaSite
   ```

   which copies artifacts into `server/build/podca-site/`.

2. Point the server at that directory and run:

   ```shell
   export PODCA_SITE_ROOT="$(pwd)/server/build/podca-site"
   ./gradlew :server:run
   ```

   If `PODCA_SITE_ROOT` is not set, the server looks for `composeApp/build/dist/wasmJs/productionExecutable` relative to the current working directory (repository root works).

- `GET /api/health` — health check.
- `GET /api/podca/marketing-document?tab=0..3` — マーケ用タブごとの SDUI ドキュメント（`application/octet-stream`）。`composeApp` の `MarketingApp` が Ktor Client で取得する本流と同じペイロード（`sdui:marketing` をサーバー上でエンコード）。
- `GET /api/podca/welcome-document` — 別系統のサンプル SDUI ペイロード（同上フォーマット）。マーケデモのタブ API ではない。

どちらもサーバー側は **Podca Studio**（`Podca*` DSL）でエンコードしており、androidx Compose UI / Material には依存しません。

## Run the sample & server (quick reference)

Launch steps for **composeApp** (Android, Desktop, Web dev server, iOS) and **server** are summarized in Japanese in the **アプリの起動方法** section (above). Web production build and Ktor static hosting stay under **Marketing site (Web)**.

## License

Apache License 2.0. See [LICENSE](./LICENSE).
