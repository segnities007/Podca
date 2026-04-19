package com.segnities007.yoyo

import io.ktor.client.HttpClient

/** Base URL of the Podca Ktor server (no trailing slash), e.g. `http://127.0.0.1:9090`. */
internal expect fun podcaMarketingApiBaseUrl(): String

internal expect fun createMarketingHttpClient(): HttpClient
