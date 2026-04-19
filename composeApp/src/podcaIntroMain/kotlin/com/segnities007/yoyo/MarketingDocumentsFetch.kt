package com.segnities007.yoyo

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.statement.bodyAsBytes
import io.ktor.http.isSuccess

internal suspend fun fetchAllMarketingTabs(client: HttpClient, baseUrl: String): Map<Int, ByteArray> {
    val root = baseUrl.trimEnd('/')
    return (0..3).associateWith { tab -> fetchMarketingTabBytes(client, root, tab) }
}

private suspend fun fetchMarketingTabBytes(client: HttpClient, baseUrl: String, tab: Int): ByteArray {
    val response = client.get("$baseUrl/api/podca/marketing-document") {
        parameter("tab", tab)
    }
    check(response.status.isSuccess()) { "HTTP ${response.status} tab=$tab" }
    return response.bodyAsBytes()
}
