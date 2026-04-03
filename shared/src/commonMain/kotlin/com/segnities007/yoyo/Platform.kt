package com.segnities007.yoyo

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform