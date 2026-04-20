package com.segnities007.yoyo

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.podca.sdui.marketing.encodePodcaMarketingDocument
import com.podca.sdui.player.PodcaPlayer
import com.podca.sdui.player.engine.PodcaAcceptedActionResult
import com.podca.sdui.player.engine.PodcaRuntime
import com.segnities007.yoyo.intro.CallbackIntroScreen

object MarketingRoutes {
    const val HOME = "home"
    const val ARCHITECTURE = "architecture"
    const val GET_STARTED = "get-started"
    const val EXAMPLES = "examples"
}

private fun routeToTab(route: String?): Int =
    when (route) {
        MarketingRoutes.HOME -> 0
        MarketingRoutes.ARCHITECTURE -> 1
        MarketingRoutes.GET_STARTED -> 2
        MarketingRoutes.EXAMPLES -> 3
        else -> 0
    }

@Composable
fun MarketingApp() {
    val navController = rememberNavController()
    val runtime = remember { PodcaRuntime() }
    var byteCache by remember { mutableStateOf<Map<Int, ByteArray>?>(null) }
    var offline by remember { mutableStateOf(false) }
    var booting by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        registerMarketingNavActions(runtime, navController)
        val client = createMarketingHttpClient()
        try {
            runCatching {
                val base = podcaMarketingApiBaseUrl()
                val fromServer = fetchAllMarketingTabs(client, base)
                byteCache = fromServer
                runtime.loadDocument(fromServer.getValue(0))
                booting = false
            }.getOrElse {
                runCatching {
                    val cache = buildMap {
                        for (tab in 0..3) {
                            put(tab, encodePodcaMarketingDocument(tab))
                        }
                    }
                    byteCache = cache
                    runtime.loadDocument(cache.getValue(0))
                    booting = false
                }.onFailure {
                    offline = true
                    booting = false
                }
            }
        } finally {
            client.close()
        }
    }

    Surface(modifier = Modifier.fillMaxSize()) {
        when {
            offline -> CallbackIntroScreen()
            booting || byteCache == null -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator()
                }
            }
            else -> {
                val cache = byteCache
                check(cache != null)
                MarketingNavHost(
                    navController = navController,
                    runtime = runtime,
                    byteCache = cache,
                    onDocumentLoadFailure = {
                        offline = true
                    },
                )
            }
        }
    }
}

private fun registerMarketingNavActions(runtime: PodcaRuntime, navController: NavHostController) {
    runtime.register("podca.nav.home") { event ->
        navController.navigateTopLevel(MarketingRoutes.HOME)
        PodcaAcceptedActionResult(eventId = event.event_id)
    }
    runtime.register("podca.nav.stack") { event ->
        navController.navigateTopLevel(MarketingRoutes.ARCHITECTURE)
        PodcaAcceptedActionResult(eventId = event.event_id)
    }
    runtime.register("podca.nav.start") { event ->
        navController.navigateTopLevel(MarketingRoutes.GET_STARTED)
        PodcaAcceptedActionResult(eventId = event.event_id)
    }
    runtime.register("podca.nav.examples") { event ->
        navController.navigateTopLevel(MarketingRoutes.EXAMPLES)
        PodcaAcceptedActionResult(eventId = event.event_id)
    }
    runtime.register("podca.remote.demo_tap") { event ->
        PodcaAcceptedActionResult(eventId = event.event_id)
    }
}

/**
 * [NavHost] でグラフを張った **後** にだけ [WebMarketingHistoryEffect] と
 * [currentBackStackEntryAsState] を使う（ローディング中に `getGraph()` しない）。
 */
@Composable
private fun MarketingNavHost(
    navController: NavHostController,
    runtime: PodcaRuntime,
    byteCache: Map<Int, ByteArray>,
    onDocumentLoadFailure: () -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        NavHost(
            navController = navController,
            startDestination = MarketingRoutes.HOME,
            modifier = Modifier.fillMaxSize(),
        ) {
            composable(MarketingRoutes.HOME) {
                PodcaPlayer(runtime = runtime, modifier = Modifier.fillMaxSize())
            }
            composable(MarketingRoutes.ARCHITECTURE) {
                PodcaPlayer(runtime = runtime, modifier = Modifier.fillMaxSize())
            }
            composable(MarketingRoutes.GET_STARTED) {
                PodcaPlayer(runtime = runtime, modifier = Modifier.fillMaxSize())
            }
            composable(MarketingRoutes.EXAMPLES) {
                PodcaPlayer(runtime = runtime, modifier = Modifier.fillMaxSize())
            }
        }
        WebMarketingHistoryEffect(navController)
    }
    val backStackEntry by navController.currentBackStackEntryAsState()
    LaunchedEffect(backStackEntry?.destination?.route, byteCache) {
        val route = backStackEntry?.destination?.route ?: MarketingRoutes.HOME
        val tab = routeToTab(route)
        runCatching {
            runtime.loadDocument(byteCache.getValue(tab))
        }.onFailure {
            onDocumentLoadFailure()
        }
    }
}

internal fun NavController.navigateTopLevel(route: String) {
    navigate(route) {
        popUpTo(graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}
