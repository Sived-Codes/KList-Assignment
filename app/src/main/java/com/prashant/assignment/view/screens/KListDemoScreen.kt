package com.prashant.assignment.view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.prashant.assignment.data.model.Coin
import com.prashant.assignment.view.components.KListItem
import com.prashant.assignment.viewmodels.DemoType
import com.prashant.assignment.viewmodels.KListDemoUiState
import com.prashant.assignment.viewmodels.KListDemoViewModel
import com.prashant.klist.core.KList

@Composable
fun KListDemoScreen(
    uiState: KListDemoUiState,
    onDemoItemClick: (DemoType) -> Unit,
    onCoinClick: (Coin) -> Unit,
    modifier: Modifier = Modifier
) {
    val tabTitles = listOf("Top Gainers", "All Coins", "Top Losers")
    var selectedTabIndex by remember { mutableIntStateOf(0) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        Text(
            text = "KList Demo App",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 12.dp).padding(10.dp)
        )

        if (uiState.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
            return@Column
        }

        if (uiState.error != null) {
            ErrorText(message = uiState.error)
            return@Column
        }

        // Tabs
        TabRow(
            selectedTabIndex = selectedTabIndex,
            contentColor = MaterialTheme.colorScheme.primary,
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ) {
            tabTitles.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    text = {
                        Text(
                            text = title,
                            fontWeight = if (selectedTabIndex == index) FontWeight.SemiBold else FontWeight.Normal
                        )
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        when (selectedTabIndex) {
            0 -> {
                val gainers = uiState.advancedDemoCoins.filter { it.changePercent > 0 }
                if (gainers.isNotEmpty()) {
                    KList
                        .padding(10.dp)
                        .header("Top Gainers")
                        .items(gainers) { coin ->
                            KListItem(coin = coin, onClick = onCoinClick)
                        }
                        .render()
                } else {
                    EmptyState("No top gainers found.")
                }
            }

            1 -> {
                if (uiState.advancedDemoCoins.isNotEmpty()) {
                    KList
                        .padding(12.dp)
                        .header("All Cryptocurrencies")
                        .items(uiState.advancedDemoCoins) { coin ->
                            KListItem(coin = coin, onClick = onCoinClick)
                        }
                        .withDividers(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f))
                        .render()
                } else {
                    EmptyState("No coins available.")
                }
            }

            2 -> {
                val losers = uiState.advancedDemoCoins.filter { it.changePercent < 0 }
                if (losers.isNotEmpty()) {
                    KList
                        .padding(8.dp)
                        .header("Market Losers")
                        .items(losers) { coin ->
                            KListItem(coin = coin, onClick = onCoinClick)
                        }
                        .clickable { onDemoItemClick(DemoType.ADVANCED) }
                        .render()
                } else {
                    EmptyState("No top losers found.")
                }
            }
        }
    }
}


@Composable
fun EmptyState(message: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = message,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center
        )
    }
}


@Composable
fun ErrorText(message: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Filled.Info,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.error
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.error
                ),
                textAlign = TextAlign.Start,
                modifier = Modifier.weight(1f)
            )
        }
    }
}


@Composable
fun KListDemoScreenForViewModel(
    viewModel: KListDemoViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    KListDemoScreen(
        uiState = uiState,
        onDemoItemClick = viewModel::onDemoItemClick,
        onCoinClick = viewModel::onCoinClick,
        modifier = modifier
    )
}
