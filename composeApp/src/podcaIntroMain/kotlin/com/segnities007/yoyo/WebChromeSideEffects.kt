package com.segnities007.yoyo

import androidx.compose.runtime.Composable

/** Web ではスプラッシュ DOM の除去など。他ターゲットは no-op。 */
@Composable
expect fun WebChromeSideEffects()
