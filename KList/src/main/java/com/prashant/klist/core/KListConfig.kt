package com.prashant.klist.core

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.prashant.assignment.utils.KListConstants

/**
 * Configuration data class holding all KList state
 */
data class KListConfig(
    val padding: Dp = KListConstants.DEFAULT_PADDING,
    val sections: List<KListSection> = emptyList(),
    val showDividers: Boolean = false,
    val dividerColor: Color = Color.Gray,
    val animateItems: Boolean = false,
    val clickHandler: (() -> Unit)? = null,
    val lazyListState: LazyListState? = null
)