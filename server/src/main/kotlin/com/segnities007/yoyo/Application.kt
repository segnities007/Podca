package com.segnities007.yoyo

import com.podca.sdui.marketing.encodePodcaMarketingDocument
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.cors.routing.CORS
import io.ktor.server.response.respondBytes
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import io.ktor.server.http.content.staticFiles
import io.ktor.server.http.content.default
import java.io.File
import kotlinx.coroutines.runBlocking

/** Default listening port when `PODCA_SERVER_PORT` / `PORT` are unset. */
internal const val SERVER_PORT: Int = 9090

fun main() {
    val port = resolveServerPort()
    embeddedServer(Netty, port = port, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

/**
 * Default is [SERVER_PORT] (9090). Override with env `PODCA_SERVER_PORT` or `PORT` if that port is taken.
 */
private fun resolveServerPort(): Int {
    System.getenv("PODCA_SERVER_PORT")?.toIntOrNull()?.takeIf { it in 1..65535 }?.let { return it }
    System.getenv("PORT")?.toIntOrNull()?.takeIf { it in 1..65535 }?.let { return it }
    return SERVER_PORT
}

private fun resolvePodcaSiteDirectory(): File? {
    System.getenv("PODCA_SITE_ROOT")?.let { path ->
        File(path).takeIf { it.isDirectory }?.let { return it }
    }
    val candidates = listOf(
        File("composeApp/build/dist/wasmJs/productionExecutable"),
        File("../composeApp/build/dist/wasmJs/productionExecutable"),
    )
    return candidates.firstOrNull { it.isDirectory }
}

fun Application.module() {
    install(CORS) {
        allowMethod(HttpMethod.Options)
        allowMethod(HttpMethod.Get)
        allowHeader(HttpHeaders.ContentType)
        anyHost()
    }
    val siteDir = resolvePodcaSiteDirectory()
    routing {
        get("/api/health") {
            call.respondText("ok")
        }
        get("/api/podca/marketing-document") {
            val tab = call.request.queryParameters["tab"]?.toIntOrNull()?.coerceIn(0, 3) ?: 0
            val bytes = runBlocking { encodePodcaMarketingDocument(tab) }
            call.respondBytes(bytes, ContentType.Application.OctetStream)
        }
        get("/api/podca/welcome-document") {
            val bytes = encodeServerWelcomeDocument()
            call.respondBytes(bytes, ContentType.Application.OctetStream)
        }
        if (siteDir != null) {
            staticFiles("/", siteDir) {
                default("index.html")
            }
        } else {
            get("/") {
                call.respondText(
                    "Podca Ktor server is running. Build the marketing site with " +
                        "`./gradlew :composeApp:wasmJsBrowserProductionWebpack` (or `:composeApp:wasmJsBrowserDistribution`) " +
                        "and either export `PODCA_SITE_ROOT` to that folder or run from the repository root so the default " +
                        "path resolves. Try GET /api/health.",
                )
            }
        }
    }
}
