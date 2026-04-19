package com.segnities007.yoyo

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import kotlinx.browser.window

@Composable
actual fun WebMarketingHistoryEffect(navController: NavController) {
    val entry by navController.currentBackStackEntryAsState()

    LaunchedEffect(Unit) {
        val raw = window.location.hash.removePrefix("#").substringBefore("?").trim()
        if (raw.isEmpty() || raw == "/") return@LaunchedEffect
        val target = normalizeMarketingRoute(raw) ?: return@LaunchedEffect
        if (navController.currentDestination?.route != target) {
            navController.navigateTopLevel(target)
        }
    }

    DisposableEffect(navController) {
        val listener: (org.w3c.dom.events.Event) -> Unit = {
            val raw = window.location.hash.removePrefix("#").substringBefore("?").trim()
            val target = normalizeMarketingRoute(raw) ?: MarketingRoutes.HOME
            if (navController.currentDestination?.route != target) {
                navController.navigateTopLevel(target)
            }
        }
        window.addEventListener("popstate", listener)
        onDispose { window.removeEventListener("popstate", listener) }
    }

    LaunchedEffect(entry?.destination?.route) {
        val route = entry?.destination?.route ?: return@LaunchedEffect
        val desired = "#$route"
        if (window.location.hash != desired) {
            window.history.replaceState(null, "", desired)
        }
    }
}

private fun normalizeMarketingRoute(raw: String): String? {
    val key = raw.trimStart('/')
    return when (key) {
        MarketingRoutes.HOME,
        MarketingRoutes.ARCHITECTURE,
        MarketingRoutes.GET_STARTED,
        MarketingRoutes.EXAMPLES,
        -> key
        else -> null
    }
}
