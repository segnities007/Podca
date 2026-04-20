plugins {
    // this is necessary to avoid the plugins to be loaded multiple times
    // in each subproject's classloader
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.androidKotlinMultiplatformLibrary) apply false
    alias(libs.plugins.composeHotReload) apply false
    alias(libs.plugins.composeMultiplatform) apply false
    alias(libs.plugins.composeCompiler) apply false
    alias(libs.plugins.kotlinJvm) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false
    alias(libs.plugins.ktor) apply false
    alias(libs.plugins.wire) apply false
}

/** Podca Remote SSoT: Wire round-trip (`remote-creation`), loop expand (`remote-core`), JVM PNG decode (`remote-player-compose`). */
tasks.register("remoteVerifyJvm") {
    group = "verification"
    description =
        "Runs :sdui:remote:remote-core:jvmTest, :sdui:remote:remote-creation:jvmTest, :sdui:remote:remote-player-compose:jvmTest (see sdui/remote/README.md)."
    dependsOn(
        project(":sdui:remote:remote-core").tasks.named("jvmTest"),
        project(":sdui:remote:remote-creation").tasks.named("jvmTest"),
        project(":sdui:remote:remote-player-compose").tasks.named("jvmTest"),
    )
}