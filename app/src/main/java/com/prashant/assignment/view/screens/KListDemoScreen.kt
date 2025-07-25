package com.prashant.assignment.view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.prashant.assignment.R
import com.prashant.assignment.data.model.Coin
import com.prashant.assignment.view.theme.AssignmentTheme
import com.prashant.assignment.viewmodels.DemoType
import com.prashant.assignment.viewmodels.KListDemoUiState
import com.prashant.assignment.viewmodels.KListDemoViewModel
import com.prashant.klist.components.KListItem
import com.prashant.klist.core.KList

@Composable
fun KListDemoScreen(
    uiState: KListDemoUiState,
    onDemoItemClick: (DemoType) -> Unit,
    onCoinClick: (Coin) -> Unit,
    modifier: Modifier = Modifier
) {
    val advancedListState = rememberLazyListState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {



        Box(modifier = Modifier.weight(1f)) {
            if (uiState.advancedDemoCoins.isNotEmpty()) {
                val gainers = uiState.advancedDemoCoins.filter { it.changePercent > 0 }
                val losers = uiState.advancedDemoCoins.filter { it.changePercent < 0 }
                KList.create
                    .padding(8.dp)
                    .withScrollState(advancedListState)
                    .animated()
                    .header(stringResource(R.string.top_gainers), gainers) { coin ->
                        KListItem(
                            coin = coin,
                            onClick = onCoinClick
                        )
                    }
                    .header(stringResource(R.string.top_losers), losers) { coin ->
                        KListItem(
                            coin = coin,
                            onClick = onCoinClick
                        )
                    }
                    .clickable { onDemoItemClick(DemoType.ADVANCED) }
                    .render(modifier = Modifier.fillMaxSize())
            } else if (!uiState.isLoading && uiState.error == null) {
                Text(
                    text = stringResource(R.string.no_data_available),
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }

        if (uiState.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        uiState.error?.let { error ->
            ErrorText(
                message = error,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            )
        }
    }
}

@Composable
fun ErrorText(message: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.padding(horizontal = 16.dp, vertical = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.errorContainer
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Info,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.error
            )
            Spacer(Modifier.width(8.dp))
            Text(
                text = message,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
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

@Preview(showBackground = true)
@Composable
fun KListDemoScreenPreview_Data() {
    val sampleCoins = listOf(
        Coin(uid = 1L, name = "Bitcoin", symbol = "BTC", price = 60000.0, changePercent = 2.5, volume = 0, marketCap = 0),
        Coin(uid = 2L, name = "Ethereum", symbol = "ETH", price = 3000.0, changePercent = -1.2, volume = 0, marketCap = 0),
        Coin(uid = 3L, name = "Cardano", symbol = "ADA", price = 0.5, changePercent = 8.1, volume = 0, marketCap = 0)
    )
    AssignmentTheme {
        KListDemoScreen(
            uiState = KListDemoUiState(
                basicDemoCoins = sampleCoins.take(2),
                advancedDemoCoins = sampleCoins
            ),
            onDemoItemClick = {},
            onCoinClick = {},
            modifier = Modifier.fillMaxSize()
        )
    }
}