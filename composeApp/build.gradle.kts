import org.gradle.api.file.DuplicatesStrategy
import org.gradle.api.tasks.Copy
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeHotReload)
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    
    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }
    
    jvm()
    
    js {
        browser()
        binaries.executable()
    }
    
    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser()
        binaries.executable()
    }
    
    sourceSets {
        val iosMain by creating {
            dependsOn(commonMain.get())
        }
        sourceSets.named("iosArm64Main").configure { dependsOn(iosMain) }
        sourceSets.named("iosSimulatorArm64Main").configure { dependsOn(iosMain) }

        val podcaIntroMain by creating {
            dependsOn(commonMain.get())
            kotlin.srcDir("src/podcaIntroMain/kotlin")
            dependencies {
                implementation(projects.sdui.player.player)
                implementation(projects.sdui.studio.studio)
                implementation(projects.sdui.marketing)
                implementation(libs.navigation.compose)
                implementation(libs.ktor.clientCore)
                implementation(libs.kotlinx.coroutinesCore)
            }
        }
        val webMain by creating {
            dependsOn(podcaIntroMain)
            kotlin.srcDir("src/webMain/kotlin")
            resources.srcDir("src/webMain/resources")
            dependencies {
                implementation(libs.compose.runtime)
                implementation(libs.navigation.compose)
                implementation(libs.kotlinx.browser)
            }
        }
        iosMain.dependsOn(podcaIntroMain)
        androidMain.get().dependsOn(podcaIntroMain)
        jvmMain.get().dependsOn(podcaIntroMain)
        sourceSets.named("jsMain").configure {
            dependsOn(podcaIntroMain)
            dependsOn(webMain)
        }
        sourceSets.named("wasmJsMain").configure {
            dependsOn(podcaIntroMain)
            dependsOn(webMain)
        }

        androidMain.dependencies {
            implementation(libs.compose.uiToolingPreview)
            implementation(libs.androidx.activity.compose)
            implementation(libs.ktor.clientOkHttp)
        }
        iosMain.dependencies {
            implementation(libs.ktor.clientDarwin)
        }
        sourceSets.named("jsMain").configure {
            dependencies {
                implementation(libs.ktor.clientJs)
            }
        }
        sourceSets.named("wasmJsMain").configure {
            dependencies {
                implementation(libs.ktor.clientJsWasmJs)
            }
        }
        commonMain.dependencies {
            implementation(libs.compose.runtime)
            implementation(libs.compose.foundation)
            implementation(libs.compose.material3)
            implementation(libs.compose.ui)
            implementation(libs.compose.components.resources)
            implementation(libs.compose.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)
            implementation(projects.shared)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
        jvmMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutinesSwing)
            implementation(libs.ktor.clientJava)
        }
    }
}

val copyPodcaWebStaticsWasm by tasks.registering(Copy::class) {
    group = "build"
    description = "Copies index.html and styles.css next to the Wasm production bundle."
    from(layout.projectDirectory.dir("src/webMain/resources")) {
        include("index.html", "styles.css")
    }
    into(layout.buildDirectory.dir("dist/wasmJs/productionExecutable"))
}

tasks.named("wasmJsBrowserProductionWebpack") {
    finalizedBy(copyPodcaWebStaticsWasm)
}

val copyPodcaWebStaticsJs by tasks.registering(Copy::class) {
    group = "build"
    description = "Copies index.html and styles.css next to the JS production bundle."
    from(layout.projectDirectory.dir("src/webMain/resources")) {
        include("index.html", "styles.css")
    }
    into(layout.buildDirectory.dir("dist/js/productionExecutable"))
}

tasks.named("jsBrowserProductionWebpack") {
    finalizedBy(copyPodcaWebStaticsJs)
}

tasks.withType<Copy>().configureEach {
    if (name == "wasmJsProcessResources" || name == "jsProcessResources") {
        duplicatesStrategy = DuplicatesStrategy.INCLUDE
    }
    // jsMain と wasmJsMain の両方が webMain に依存するため、メタデータ用リソース集約で同一パスが二重になる
    if (name == "metadataWebMainProcessResources") {
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    }
}

android {
    namespace = "com.segnities007.yoyo"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.segnities007.yoyo"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    debugImplementation(libs.compose.uiTooling)
}

// AGP 9.0 can wire optimizeReleaseResources to a missing processed-res directory
// when minification is disabled. Skip the optimizer for this release build.
tasks.matching { it.name == "optimizeReleaseResources" }.configureEach {
    enabled = false
}

compose.desktop {
    application {
        mainClass = "com.segnities007.yoyo.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "com.segnities007.yoyo"
            packageVersion = "1.0.0"
        }
    }
}
