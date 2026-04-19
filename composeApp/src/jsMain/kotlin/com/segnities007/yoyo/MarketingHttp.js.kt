package com.segnities007.yoyo

import io.ktor.client.HttpClient
import io.ktor.client.engine.js.Js
import kotlinx.browser.document
import org.w3c.dom.HTMLMetaElement

internal actual fun podcaMarketingApiBaseUrl(): String {
    val el = document.querySelector("meta[name=podca-api-base]") as? HTMLMetaElement
    return el?.content?.takeIf { it.isNotBlank() } ?: "http://127.0.0.1:$SERVER_PORT"
}

internal actual fun createMarketingHttpClient(): HttpClient = HttpClient(Js)
