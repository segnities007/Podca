plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.ktor)
    alias(libs.plugins.composeCompiler)
    application
}

group = "com.segnities007.yoyo"
version = "1.0.0"
application {
    mainClass.set("com.segnities007.yoyo.ApplicationKt")
    
    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

dependencies {
    implementation(projects.sdui.marketing)
    implementation(projects.sdui.studio.studio)
    implementation(libs.kotlinx.coroutinesCore)
    implementation(libs.logback)
    implementation(libs.ktor.serverCore)
    implementation(libs.ktor.serverNetty)
    implementation(libs.ktor.serverCors)
    testImplementation(libs.ktor.serverTestHost)
    testImplementation(libs.kotlin.testJunit)
}

tasks.register<Sync>("preparePodcaSite") {
    group = "distribution"
    description = "Copies the Wasm production bundle (including index.html/styles.css from copyPodcaWebStaticsWasm) into build/podca-site."
    dependsOn(":composeApp:wasmJsBrowserDistribution", ":composeApp:copyPodcaWebStaticsWasm")
    from(rootProject.layout.projectDirectory.dir("composeApp/build/dist/wasmJs/productionExecutable"))
    into(layout.buildDirectory.dir("podca-site"))
}