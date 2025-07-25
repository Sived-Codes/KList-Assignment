package com.prashant.klist.core

import androidx.compose.runtime.Composable

data class KListSection(
    val header: String? = null,
    val items: List<Any> = emptyList(),
    val itemContent: @Composable (Any) -> Unit = {}
)