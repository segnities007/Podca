package com.segnities007.yoyo

import com.podca.sdui.marketing.encodePodcaMarketingDocument
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.ApplicationCall
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
import java.net.URI
import kotlinx.coroutines.runBlocking

/** Default listening port when `PODCA_SERVER_PORT` / `PORT` are unset. */
internal const val SERVER_PORT: Int = 9090
internal const val DEFAULT_PODCA_DOCUMENT_MAX_BYTES: Int = 16 * 1024 * 1024
private const val PODCA_CORS_ALLOWED_ORIGINS_ENV: String = "PODCA_CORS_ALLOWED_ORIGINS"
private const val PODCA_DOCUMENT_MAX_BYTES_ENV: String = "PODCA_DOCUMENT_MAX_BYTES"
private val DEFAULT_PODCA_CORS_ALLOWED_ORIGINS: List<String> =
    listOf(
        "http://localhost:8080",
        "http://127.0.0.1:8080",
        "http://localhost:9090",
        "http://127.0.0.1:9090",
    )

internal data class PodcaCorsOrigin(
    val hostWithOptionalPort: String,
    val scheme: String,
)

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

internal fun resolvePodcaCorsAllowedOrigins(raw: String? = System.getenv(PODCA_CORS_ALLOWED_ORIGINS_ENV)): List<PodcaCorsOrigin> {
    val source = if (raw.isNullOrBlank()) DEFAULT_PODCA_CORS_ALLOWED_ORIGINS else raw.split(',')
    val parsed =
        source
            .map { it.trim() }
            .filter { it.isNotEmpty() }
            .map { value ->
                val uri = runCatching { URI(value) }.getOrElse {
                    throw IllegalArgumentException(
                        "Invalid origin '$value' in $PODCA_CORS_ALLOWED_ORIGINS_ENV. Use CSV like http://localhost:8080,https://podca.example.com.",
                    )
                }
                val scheme = uri.scheme?.lowercase()
                require(scheme == "http" || scheme == "https") {
                    "Invalid origin '$value' in $PODCA_CORS_ALLOWED_ORIGINS_ENV. Scheme must be http or https."
                }
                val host = uri.host?.trim().orEmpty()
                require(host.isNotEmpty()) {
                    "Invalid origin '$value' in $PODCA_CORS_ALLOWED_ORIGINS_ENV. Host is required."
                }
                val hostWithPort =
                    if (uri.port >= 0) {
                        "$host:${uri.port}"
                    } else {
                        host
                    }
                PodcaCorsOrigin(
                    hostWithOptionalPort = hostWithPort,
                    scheme = scheme,
                )
            }
            .distinct()
    require(parsed.isNotEmpty()) {
        "$PODCA_CORS_ALLOWED_ORIGINS_ENV must contain at least one valid origin."
    }
    return parsed
}

internal fun resolvePodcaDocumentMaxBytes(raw: String? = System.getenv(PODCA_DOCUMENT_MAX_BYTES_ENV)): Int {
    if (raw.isNullOrBlank()) return DEFAULT_PODCA_DOCUMENT_MAX_BYTES
    return raw.toIntOrNull()?.takeIf { it > 0 }
        ?: throw IllegalArgumentException(
            "$PODCA_DOCUMENT_MAX_BYTES_ENV must be a positive integer byte size.",
        )
}

private suspend fun ApplicationCall.respondPodcaDocumentBytes(
    bytes: ByteArray,
    maxDocumentBytes: Int,
) {
    if (bytes.size > maxDocumentBytes) {
        respondText(
            text = "Podca document exceeds max size ($maxDocumentBytes bytes).",
            status = HttpStatusCode.PayloadTooLarge,
        )
        return
    }
    respondBytes(bytes, ContentType.Application.OctetStream)
}

internal fun Application.module(
    corsAllowedOrigins: List<PodcaCorsOrigin> = resolvePodcaCorsAllowedOrigins(),
    maxDocumentBytes: Int = resolvePodcaDocumentMaxBytes(),
) {
    install(CORS) {
        allowMethod(HttpMethod.Options)
        allowMethod(HttpMethod.Get)
        allowHeader(HttpHeaders.ContentType)
        corsAllowedOrigins.forEach { origin ->
            allowHost(origin.hostWithOptionalPort, schemes = listOf(origin.scheme))
        }
    }
    val siteDir = resolvePodcaSiteDirectory()
    routing {
        get("/api/health") {
            call.respondText("ok")
        }
        get("/api/podca/marketing-document") {
            val tab = call.request.queryParameters["tab"]?.toIntOrNull()?.coerceIn(0, 3) ?: 0
            val bytes = runBlocking { encodePodcaMarketingDocument(tab) }
            call.respondPodcaDocumentBytes(
                bytes = bytes,
                maxDocumentBytes = maxDocumentBytes,
            )
        }
        get("/api/podca/welcome-document") {
            val bytes = encodeServerWelcomeDocument()
            call.respondPodcaDocumentBytes(
                bytes = bytes,
                maxDocumentBytes = maxDocumentBytes,
            )
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
