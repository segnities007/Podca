package com.segnities007.yoyo

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController

@Composable
@Preview
fun App() {
    WebChromeSideEffects()
    PodcaMarketingTheme {
        MarketingApp()
    }
}

@Composable
expect fun WebMarketingHistoryEffect(navController: NavController)
