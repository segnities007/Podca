package com.segnities007.yoyo

import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp

internal actual fun podcaMarketingApiBaseUrl(): String = "http://10.0.2.2:$SERVER_PORT"

internal actual fun createMarketingHttpClient(): HttpClient = HttpClient(OkHttp)
