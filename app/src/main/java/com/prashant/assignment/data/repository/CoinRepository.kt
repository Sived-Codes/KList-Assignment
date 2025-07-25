package com.prashant.assignment.data.repository

import com.prashant.assignment.data.model.Coin
import kotlinx.coroutines.delay

/**
 * Repository for managing coin data
 */
class CoinRepository {

    /**
     * Get all coins from data source
     */
    suspend fun getAllCoins(): List<Coin> {
        // Simulate network delay
        delay(NETWORK_DELAY_MS)

        return getSampleCoins()
    }

    /**
     * Get coins by category
     */
    suspend fun getCoinsByCategory(category: CoinCategory): List<Coin> {
        val allCoins = getAllCoins()

        return when (category) {
            CoinCategory.TOP_GAINERS -> allCoins
                .filter { it.changePercent > 0 }
                .sortedByDescending { it.changePercent }

            CoinCategory.TOP_LOSERS -> allCoins
                .filter { it.changePercent < 0 }
                .sortedBy { it.changePercent }

            CoinCategory.HIGH_VOLUME -> allCoins
                .sortedByDescending { it.volume }
        }
    }

    private fun getSampleCoins(): List<Coin> {
        return listOf(
            Coin(1L, "Bitcoin", "BTC", 45000.0, 5.2, 1200000L, 850_000_000_000L),
            Coin(2L, "Ethereum", "ETH", 3000.0, 3.8, 800000L, 360_000_000_000L),
            Coin(3L, "Cardano", "ADA", 1.2, 8.1, 500000L, 38_000_000_000L),
            Coin(4L, "Dogecoin", "DOGE", 0.08, -2.5, 300000L, 10_000_000_000L),
            Coin(5L, "Ripple", "XRP", 0.65, -1.8, 250000L, 32_000_000_000L),
            Coin(6L, "Polkadot", "DOT", 25.0, 0.5, 150000L, 23_000_000_000L),
            Coin(7L, "Litecoin", "LTC", 180.0, 2.1, 200000L, 12_000_000_000L),
            Coin(8L, "Chainlink", "LINK", 28.0, 6.4, 140000L, 13_000_000_000L),
            Coin(9L, "Stellar", "XLM", 0.27, -0.9, 180000L, 7_000_000_000L),
            Coin(10L, "Uniswap", "UNI", 20.0, 1.3, 160000L, 9_500_000_000L),
            Coin(11L, "Solana", "SOL", 110.0, 4.7, 210000L, 42_000_000_000L),
            Coin(12L, "Avalanche", "AVAX", 50.0, 3.2, 130000L, 15_000_000_000L),
            Coin(13L, "VeChain", "VET", 0.11, -1.0, 100000L, 6_500_000_000L),
            Coin(14L, "Monero", "XMR", 160.0, 0.9, 90000L, 2_800_000_000L),
            Coin(15L, "Tezos", "XTZ", 3.5, 2.6, 95000L, 3_100_000_000L),
            Coin(16L, "Algorand", "ALGO", 0.85, -0.3, 89000L, 2_700_000_000L)
        )
    }

    enum class CoinCategory {
        TOP_GAINERS,
        TOP_LOSERS,
        HIGH_VOLUME
    }

    companion object {
        private const val NETWORK_DELAY_MS = 1000L
    }
}
