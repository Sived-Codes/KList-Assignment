package com.prashant.klist.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.prashant.klist.utils.KListConstants

@Composable
fun KListHeader(
    title: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = title,
        fontSize = KListConstants.DEFAULT_HEADER_FONT_SIZE, // ✅ Constants used
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.primary,
        modifier = modifier.padding(bottom = KListConstants.DEFAULT_ITEM_SPACING) // ✅ Constants used
    )
}
