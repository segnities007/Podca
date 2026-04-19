package com.segnities007.yoyo.intro

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Kotlin / Koin の公式サイトのようなランディング相当の紹介 UI。
 * プロジェクト方針により、ローカル Compose はこの画面のみ。
 */
@Composable
fun CallbackIntroScreen() {
    val gradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF1B2838),
            Color(0xFF0D1117),
        ),
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(gradient),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 24.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            Text(
                text = "Podca",
                style = MaterialTheme.typography.displaySmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                ),
            )
            Text(
                text = "Compose Multiplatform 向けハイブリッド SDUI。Studio が UI ツリーを Wire で Pack し、Player が端末で再生します。",
                style = MaterialTheme.typography.titleMedium.copy(
                    color = Color(0xFFB0BEC5),
                    lineHeight = 28.sp,
                ),
            )
            CodeSnippetBlock(
                code = """
                    val runtime = PodcaRuntime()
                    runtime.loadDocument(bytesFromStudio)
                    PodcaPlayer(runtime = runtime)
                """.trimIndent(),
            )
            Text(
                text = "ビジネスロジックはローカルに置き、key / actions で配線する設計です。",
                style = MaterialTheme.typography.bodyLarge.copy(color = Color(0xFF90A4AE)),
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                HeroFeatureCard(
                    title = "Protocol",
                    body = "Protobuf で UI 契約。foundation / material3 / ui を網羅。",
                )
                HeroFeatureCard(
                    title = "Studio",
                    body = "サーバ側 DSL で PodcaNode を組み立て、encode して配信。",
                )
                HeroFeatureCard(
                    title = "Player",
                    body = "decode して Compose に復元。Action をローカルハンドラへ。",
                )
            }
        }
    }
}

@Composable
private fun CodeSnippetBlock(code: String) {
    Text(
        text = code,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(Color(0xFF161B22))
            .padding(16.dp),
        style = MaterialTheme.typography.bodyMedium.copy(
            color = Color(0xFFE6EDF3),
            fontFamily = FontFamily.Monospace,
            lineHeight = 22.sp,
        ),
    )
}

@Composable
private fun HeroFeatureCard(title: String, body: String) {
    Card(
        modifier = Modifier.widthIn(min = 200.dp, max = 280.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF21262D).copy(alpha = 0.92f),
        ),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF7C4DFF),
                ),
            )
            Text(
                text = body,
                style = MaterialTheme.typography.bodyMedium.copy(color = Color(0xFFCFD8DC)),
            )
        }
    }
}
