import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.wire)
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
    
    sourceSets {
        commonMain {
            kotlin.srcDir(layout.buildDirectory.dir("generated/source/wire/release"))

            dependencies {
                api(libs.wire.runtime)
            }
        }
    }
}

extensions.configure<com.android.build.api.dsl.LibraryExtension> {
    namespace = "com.podca.sdui.protocol"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
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
        it.name == "compileKotlinJvm" ||
        it.name == "compileDebugKotlinAndroid" ||
        it.name == "compileReleaseKotlinAndroid" ||
        it.name.startsWith("compileKotlinIos")
}.configureEach {
    dependsOn("generateReleaseProtos")
}
