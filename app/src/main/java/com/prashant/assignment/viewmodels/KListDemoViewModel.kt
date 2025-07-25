package com.prashant.assignment.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prashant.assignment.data.model.Coin
import com.prashant.assignment.data.repository.CoinRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel for KList demo screen
 */
class KListDemoViewModel(
    private val coinRepository: CoinRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(KListDemoUiState())
    val uiState: StateFlow<KListDemoUiState> = _uiState.asStateFlow()

    init {
        loadDemoData()
    }

    fun onDemoItemClick(demoType: DemoType) {
        _uiState.value = _uiState.value.copy(selectedDemo = demoType)
    }

    fun onCoinClick(coin: Coin) {
        _uiState.value = _uiState.value.copy(selectedCoin = coin)
    }

    fun onRefreshDemo() {
        loadDemoData()
    }

    private fun loadDemoData() {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isLoading = true)

                val coins = coinRepository.getAllCoins()

                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    basicDemoCoins = coins.take(BASIC_DEMO_LIMIT),
                    advancedDemoCoins = coins,
                    animatedDemoCoins = coins.shuffled().take(ANIMATED_DEMO_LIMIT)
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Failed to load demo data"
                )
            }
        }
    }

    companion object {
        private const val BASIC_DEMO_LIMIT = 5
        private const val ANIMATED_DEMO_LIMIT = 8
    }
}

/**
 * UI state for demo screen
 */
data class KListDemoUiState(
    val basicDemoCoins: List<Coin> = emptyList(),
    val advancedDemoCoins: List<Coin> = emptyList(),
    val animatedDemoCoins: List<Coin> = emptyList(),
    val selectedDemo: DemoType? = null,
    val selectedCoin: Coin? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

enum class DemoType {
    BASIC,
    ADVANCED,
    ANIMATED
}
