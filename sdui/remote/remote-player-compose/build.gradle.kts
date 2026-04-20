import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    iosArm64()
    iosSimulatorArm64()
    jvm()
    js {
        browser()
    }

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser()
    }

    sourceSets {
        val iosMain by creating {
            dependsOn(commonMain.get())
        }
        sourceSets.named("iosArm64Main").configure { dependsOn(iosMain) }
        sourceSets.named("iosSimulatorArm64Main").configure { dependsOn(iosMain) }

        commonMain.dependencies {
            api(project(":sdui:remote:remote-core"))
            api(project(":sdui:player:engine"))
            implementation(project(":sdui:protocol"))
            implementation(libs.kotlinx.coroutinesCore)
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.ui)
        }
        jvmTest.dependencies {
            implementation(libs.kotlin.test)
            // Skiko natives required for `decodeToImageBitmap` on JVM (same stack as Canvas player).
            implementation(compose.desktop.currentOs)
        }
    }
}

extensions.configure<com.android.build.api.dsl.LibraryExtension> {
    namespace = "com.podca.sdui.remote.player.compose"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
}

tasks.matching {
    it.name == "compileKotlinJvm" ||
        it.name == "compileKotlinJs" ||
        it.name == "compileKotlinWasmJs" ||
        it.name.startsWith("compileKotlinIos")
}.configureEach {
    dependsOn(":sdui:remote:remote-core:generateProtos")
}
