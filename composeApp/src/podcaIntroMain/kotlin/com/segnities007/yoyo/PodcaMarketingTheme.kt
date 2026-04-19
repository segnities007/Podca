package com.segnities007.yoyo

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val KotlinPurple = Color(0xFF7F52FF)
private val KotlinPurpleLight = Color(0xFFC8BAFF)
private val SurfaceDark = Color(0xFF19191C)
private val SurfaceVariant = Color(0xFF252528)
private val OnSurfaceMuted = Color(0xFFB0B0BC)

private val MarketingDarkScheme: ColorScheme = darkColorScheme(
    primary = KotlinPurple,
    onPrimary = Color.White,
    primaryContainer = Color(0xFF4A2F99),
    onPrimaryContainer = KotlinPurpleLight,
    secondary = KotlinPurpleLight,
    onSecondary = Color(0xFF1A1026),
    tertiary = Color(0xFF6BDDFF),
    onTertiary = Color(0xFF002633),
    background = SurfaceDark,
    onBackground = Color(0xFFF4F4F6),
    surface = SurfaceDark,
    onSurface = Color(0xFFF4F4F6),
    surfaceVariant = SurfaceVariant,
    onSurfaceVariant = OnSurfaceMuted,
    outline = Color(0xFF3D3D44),
)

/** Host theme colors only. Layout and copy are SDUI (`Podca*` → Player). */
@Composable
fun PodcaMarketingTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = MarketingDarkScheme,
        content = content,
    )
}
