package com.prashant.klist.core

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.prashant.klist.components.KListHeader
import com.prashant.klist.utils.KListConstants

class KList private constructor(private val config: KListConfig) {

    companion object {
        // Static entry points for the DSL
        fun padding(dp: Dp): KList {
            return KList(KListConfig(padding = dp))
        }

        fun header(title: String): KList {
            return KList(KListConfig(headerTitle = title))
        }

        fun <T> items(
            list: List<T>,
            itemContent: @Composable (T) -> Unit
        ): KList {
            return KList(KListConfig(
                items = list.map { it as Any },
                itemContent = { item ->
                    @Suppress("UNCHECKED_CAST")
                    itemContent(item as T)
                }
            ))
        }
    }

    // Chainable methods
    fun padding(dp: Dp): KList {
        return KList(config.copy(padding = dp))
    }

    fun header(title: String): KList {
        return KList(config.copy(headerTitle = title))
    }

    fun <T> items(
        list: List<T>,
        itemContent: @Composable (T) -> Unit
    ): KList {
        return KList(config.copy(
            items = list.map { it as Any },
            itemContent = { item ->
                @Suppress("UNCHECKED_CAST")
                itemContent(item as T)
            }
        ))
    }

    // Bonus features
    fun clickable(onClick: () -> Unit): KList {
        return KList(config.copy(clickHandler = onClick))
    }

    fun withDividers(color: Color = Color.Gray): KList {
        return KList(config.copy(showDividers = true, dividerColor = color))
    }

    fun animated(): KList {
        return KList(config.copy(animateItems = true))
    }

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
                .let { mod ->
                    config.clickHandler?.let { handler ->
                        mod.clickable { handler() }
                    } ?: mod
                },
            verticalArrangement = Arrangement.spacedBy(KListConstants.DEFAULT_ITEM_SPACING) // ✅ Constants used
        ) {
            // Header
            config.headerTitle?.let { title ->
                item {
                    KListHeader(title = title)
                }
            }

            // Items
            items(
                items = config.items,
                key = { item -> item.hashCode() }
            ) { item ->
                Column {
                    config.itemContent(item)

                    if (config.showDividers) {
                        HorizontalDivider(
                            color = config.dividerColor,
                            thickness = KListConstants.DEFAULT_DIVIDER_THICKNESS, // ✅ Constants used
                            modifier = Modifier.padding(
                                horizontal = KListConstants.DEFAULT_PADDING, // ✅ Constants used
                                vertical = KListConstants.DEFAULT_ITEM_SPACING // ✅ Constants used
                            )
                        )
                    }
                }
            }
        }
    }
}
