package com.prashant.klist.core

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.prashant.klist.utils.KListConstants

data class KListConfig(
    val padding: Dp = KListConstants.DEFAULT_PADDING, // ✅ Constants used
    val headerTitle: String? = null,
    val items: List<Any> = emptyList(),
    val itemContent: @Composable (Any) -> Unit = {},
    val showDividers: Boolean = false,
    val dividerColor: Color = Color.Gray,
    val animateItems: Boolean = false,
    val clickHandler: (() -> Unit)? = null,
    val lazyListState: LazyListState? = null
)
