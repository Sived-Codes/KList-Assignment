package com.prashant.klist.utils

import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import com.prashant.assignment.core.KListConfig

/**
 * Extension functions for enhanced KList functionality
 */

/**
 * Extension for default item padding following Material Design guidelines
 */
fun Modifier.defaultItemPadding(): Modifier {
    return this.padding(KListConstants.DEFAULT_PADDING)
}

/**
 * Extension function to validate KList configuration
 */
fun KListConfig.isValid(): Boolean {
    return sections.isNotEmpty() && sections.all { section ->
        section.header != null || section.items.isNotEmpty()
    }
}

/**
 * Extension function to get total item count across all sections
 */
fun KListConfig.getTotalItemCount(): Int {
    return sections.sumOf { it.items.size }
}