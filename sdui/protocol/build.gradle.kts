import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidKotlinMultiplatformLibrary)
    alias(libs.plugins.wire)
}

kotlin {
    androidLibrary {
        namespace = "com.podca.sdui.protocol"
        compileSdk = libs.versions.android.compileSdk.get().toInt()
        minSdk = libs.versions.android.minSdk.get().toInt()
        withHostTestBuilder {}
        compilations.configureEach {
            compileTaskProvider.configure {
                compilerOptions {
                    jvmTarget.set(JvmTarget.JVM_11)
                }
            }
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
        commonMain {
            dependencies {
                api(libs.wire.runtime)
            }
        }
    }
}

wire {
    sourcePath {
        srcDir("src/commonMain/proto")
    }
    kotlin {
    }
}

tasks.matching {
    it.name == "compileKotlinMetadata" ||
        it.name.startsWith("compileCommonMainKotlinMetadata") ||
        it.name == "compileKotlinJvm" ||
        it.name == "compileAndroidMain" ||
        it.name.startsWith("compileKotlinIos") ||
        it.name == "compileKotlinJs" ||
        it.name == "compileKotlinWasmJs" ||
        it.name.startsWith("compileKotlinJs") ||
        it.name.startsWith("compileKotlinWasmJs")
}.configureEach {
    dependsOn("generateProtos")
}
