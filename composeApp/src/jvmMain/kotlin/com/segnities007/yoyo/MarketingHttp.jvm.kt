package com.segnities007.yoyo

import io.ktor.client.HttpClient
import io.ktor.client.engine.java.Java

internal actual fun podcaMarketingApiBaseUrl(): String = "http://127.0.0.1:$SERVER_PORT"

internal actual fun createMarketingHttpClient(): HttpClient = HttpClient(Java)
