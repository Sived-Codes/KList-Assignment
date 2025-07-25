package com.prashant.assignment.view.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.TrendingDown
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.prashant.assignment.data.model.Coin
import com.prashant.klist.utils.KListConstants

@Composable
fun KListItem(
    coin: Coin,
    modifier: Modifier = Modifier,
    onClick: ((Coin) -> Unit)? = null
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = KListConstants.DEFAULT_PADDING,
                vertical   = 8.dp
            )
            .let { base ->
                onClick?.let { handler ->
                    base
                        .clip(RoundedCornerShape(12.dp))
                        .clickable { handler(coin) }
                } ?: base
            },
        elevation = CardDefaults.cardElevation(2.dp),
        colors    = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        ),
        shape     = RoundedCornerShape(12.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment     = Alignment.CenterVertically
        ) {

            /* 1. नाम व सिंबल सेक्शन */
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                /* circle placeholder with first two letters of symbol */
                Surface(
                    modifier = Modifier.size(40.dp),
                    shape    = RoundedCornerShape(20.dp),
                    color    = MaterialTheme.colorScheme.primaryContainer
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text  = coin.symbol.take(2),
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }

                Column {
                    Text(
                        text  = coin.name,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text  = coin.symbol,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            /* 2. प्राइस व %-change सेक्शन */
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text  = "$${String.format("%.2f", coin.price)}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    val isGain = coin.changePercent > 0
                    Icon(
                        imageVector = if (isGain) Icons.Default.TrendingUp
                        else Icons.Default.TrendingDown,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = if (isGain) MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.error
                    )

                    Text(
                        text = "${if (isGain) "+" else ""}${String.format("%.2f", coin.changePercent)}%",
                        style = MaterialTheme.typography.bodySmall,
                        color = if (isGain) MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.error,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}
