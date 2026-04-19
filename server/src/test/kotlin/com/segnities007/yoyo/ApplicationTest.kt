package com.segnities007.yoyo

import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlin.test.*

class ApplicationTest {

    @Test
    fun testRoot() = testApplication {
        application {
            module()
        }
        val response = client.get("/api/health")
        assertEquals(HttpStatusCode.OK, response.status)
        assertEquals("ok", response.bodyAsText())
    }

    @Test
    fun testPodcaWelcomeDocument() = testApplication {
        application {
            module()
        }
        val response = client.get("/api/podca/welcome-document")
        assertEquals(HttpStatusCode.OK, response.status)
        assertTrue(response.bodyAsBytes().isNotEmpty())
    }

    @Test
    fun testPodcaMarketingDocumentTabs() = testApplication {
        application {
            module()
        }
        for (tab in 0..3) {
            val response = client.get("/api/podca/marketing-document") {
                parameter("tab", tab)
            }
            assertEquals(HttpStatusCode.OK, response.status, "tab=$tab")
            assertTrue(response.bodyAsBytes().isNotEmpty(), "tab=$tab")
        }
    }
}