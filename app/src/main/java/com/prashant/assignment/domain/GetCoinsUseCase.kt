package com.prashant.assignment.domain

import com.prashant.assignment.data.model.Coin
import com.prashant.assignment.data.repository.CoinRepository


/**
 * UseCase for retrieving coin data
 */
class GetCoinsUseCase(
    private val coinRepository: CoinRepository
) {

    data class GetCoinsResult(
        val topGainers: List<Coin>,
        val topLosers: List<Coin>,
        val watchlist: List<Coin>
    )

    suspend operator fun invoke(): GetCoinsResult {
        val allCoins = coinRepository.getAllCoins()

        return GetCoinsResult(
            topGainers = allCoins
                .filter { it.changePercent > 0 }
                .sortedByDescending { it.changePercent }
                .take(COINS_LIMIT),
            topLosers = allCoins
                .filter { it.changePercent < 0 }
                .sortedBy { it.changePercent }
                .take(COINS_LIMIT),
            watchlist = allCoins
                .filter { it.marketCap > MARKET_CAP_THRESHOLD }
                .take(COINS_LIMIT)
        )
    }

    companion object {
        private const val COINS_LIMIT = 10
        private const val MARKET_CAP_THRESHOLD = 1_000_000L
    }
}
