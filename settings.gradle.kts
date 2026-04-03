rootProject.name = "Podca"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

// サンプルアプリ等
include(":composeApp")
include(":server")
include(":shared")

// Podca Protocol
include(":sdui:protocol")

// Podca Studio (Server-side SDK)
include(":sdui:studio:core")
include(":sdui:studio:ui-core")
include(":sdui:studio:ui-foundation")
include(":sdui:studio:ui-material3")
include(":sdui:studio:studio") // 公開 API 追加

// Podca Player (Client-side SDK)
include(":sdui:player:engine")
include(":sdui:player:ui-core")
include(":sdui:player:ui-foundation")
include(":sdui:player:ui-material3")
include(":sdui:player:player")
