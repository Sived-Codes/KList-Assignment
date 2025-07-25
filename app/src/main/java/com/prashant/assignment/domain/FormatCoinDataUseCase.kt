package com.prashant.assignment.domain

import com.prashant.assignment.data.model.Coin
import java.text.NumberFormat
import java.util.*

/**
 * UseCase for formatting coin data for display
 */
class FormatCoinDataUseCase {

    data class FormattedCoin(
        val coin: Coin,
        val formattedPrice: String,
        val formattedChange: String,
        val formattedVolume: String,
        val formattedMarketCap: String
    )

    operator fun invoke(coins: List<Coin>): List<FormattedCoin> {
        return coins.map { coin ->
            FormattedCoin(
                coin = coin,
                formattedPrice = formatPrice(coin.price),
                formattedChange = formatPercentChange(coin.changePercent),
                formattedVolume = formatVolume(coin.volume),
                formattedMarketCap = formatMarketCap(coin.marketCap)
            )
        }
    }

    private fun formatPrice(price: Double): String {
        return when {
            price >= 1.0 -> String.format("$%.2f", price)
            else -> String.format("$%.4f", price)
        }
    }

    private fun formatPercentChange(change: Double): String {
        val sign = if (change >= 0) "+" else ""
        return "$sign${String.format("%.2f", change)}%"
    }

    private fun formatVolume(volume: Long): String {
        return when {
            volume >= BILLION_THRESHOLD -> String.format("%.1fB", volume / BILLION_THRESHOLD.toDouble())
            volume >= MILLION_THRESHOLD -> String.format("%.1fM", volume / MILLION_THRESHOLD.toDouble())
            volume >= THOUSAND_THRESHOLD -> String.format("%.1fK", volume / THOUSAND_THRESHOLD.toDouble())
            else -> volume.toString()
        }
    }

    private fun formatMarketCap(marketCap: Long): String {
        return when {
            marketCap >= BILLION_THRESHOLD -> String.format("$%.1fB", marketCap / BILLION_THRESHOLD.toDouble())
            marketCap >= MILLION_THRESHOLD -> String.format("$%.1fM", marketCap / MILLION_THRESHOLD.toDouble())
            else -> NumberFormat.getCurrencyInstance(Locale.US).format(marketCap)
        }
    }

    companion object {
        private const val THOUSAND_THRESHOLD = 1_000L
        private const val MILLION_THRESHOLD = 1_000_000L
        private const val BILLION_THRESHOLD = 1_000_000_000L
    }
}
