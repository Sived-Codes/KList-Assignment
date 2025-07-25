package com.prashant.klist.core

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.prashant.assignment.data.model.Coin
import com.prashant.assignment.utils.KListConstants
import com.prashant.assignment.view.components.KListHeader


class KList private constructor(private val config: KListConfig) {

    companion object {
        //Entry point for creating a new KList instance
        val create: KList
            get() = KList(KListConfig())
    }

    /**
     * Helper function to create a section
     */
    private fun <T> createSection(
        header: String? = null,
        items: List<T> = emptyList(),
        itemContent: @Composable (T) -> Unit = {}
    ): KListSection {
        return KListSection(
            header = header,
            items = items.map { it as Any },
            itemContent = { item ->
                @Suppress("UNCHECKED_CAST")
                itemContent(item as T)
            }
        )
    }

    /**
     * Add padding to the entire list
     */
    fun padding(dp: Dp): KList {
        return KList(config.copy(padding = dp))
    }

    /**
     * Add a header with items - creates a new section
     */
    fun <T> header(
        title: String,
        items: List<T> = emptyList(),
        itemContent: @Composable (T) -> Unit = {}
    ): KList {
        return KList(config.copy(sections = config.sections + createSection(title, items, itemContent)))
    }

    /**
     * Add items without header - creates a section with items only
     */
    fun <T> items(
        list: List<T>,
        itemContent: @Composable (T) -> Unit
    ): KList {
        return KList(config.copy(sections = config.sections + createSection(null, list, itemContent)))
    }

    /**
     * Add clickable behavior to the entire list
     */
    fun clickable(onClick: () -> Unit): KList {
        return KList(config.copy(clickHandler = onClick))
    }

    /**
     * Enable dividers between items
     */
    fun withDividers(color: Color = Color.Gray): KList {
        return KList(config.copy(showDividers = true, dividerColor = color))
    }

    /**
     * Enable item animations
     */
    fun animated(): KList {
        return KList(config.copy(animateItems = true))
    }

    /**
     * Set custom LazyListState for scroll control
     */
    fun withScrollState(state: LazyListState): KList {
        return KList(config.copy(lazyListState = state))
    }

    @Composable
    fun render(modifier: Modifier = Modifier) {
        val listState = config.lazyListState ?: rememberLazyListState()

        LazyColumn(
            state = listState,
            modifier = modifier
                .fillMaxWidth()
                .padding(config.padding)
                .conditional(config.clickHandler != null) {
                    clickable { config.clickHandler?.invoke() }
                },
            verticalArrangement = Arrangement.spacedBy(KListConstants.DEFAULT_ITEM_SPACING)
        ) {
            config.sections.forEachIndexed { sectionIndex, section ->
                section.header?.let { headerTitle ->
                    item(key = "header_$sectionIndex") {
                        KListHeader(title = headerTitle)
                    }
                }

                items(
                    items = section.items,
                    key = { item ->
                        when (item) {
                            is Coin -> "coin_${item.uid}_$sectionIndex"
                            else -> "item_${item.hashCode()}_${section.items.indexOf(item)}_$sectionIndex"
                        }
                    }
                ) { item ->
                    RenderItem(
                        item = item,
                        showDivider = config.showDividers,
                        dividerColor = config.dividerColor,
                        animate = config.animateItems,
                        itemContent = section.itemContent
                    )
                }
            }
        }
    }

    /**
     * Render individual item with optional animation
     */
    @Composable
    private fun RenderItem(
        item: Any,
        showDivider: Boolean,
        dividerColor: Color,
        animate: Boolean,
        itemContent: @Composable (Any) -> Unit
    ) {
        val content: @Composable () -> Unit = {
            Column {
                itemContent(item)
                if (showDivider) {
                    Divider(
                        color = dividerColor,
                        thickness = KListConstants.DIVIDER_THICKNESS,
                        modifier = Modifier.padding(
                            horizontal = KListConstants.DIVIDER_HORIZONTAL_PADDING,
                            vertical = KListConstants.DIVIDER_VERTICAL_PADDING
                        )
                    )
                }
            }
        }
        if (animate) {
            AnimatedVisibility(
                visible = true,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                content()
            }
        } else {
            content()
        }
    }
}

/**
 * Utility to apply modifier conditionally
 */
fun Modifier.conditional(
    condition: Boolean,
    block: Modifier.() -> Modifier
): Modifier = if (condition) block() else this
