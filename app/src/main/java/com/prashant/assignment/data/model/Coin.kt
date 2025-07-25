package com.prashant.assignment.data.model

data class Coin(
    val uid: Long = 0L,
    val name: String = "",
    val symbol: String = "",
    val price: Double = 0.0,
    val changePercent: Double = 0.0,
    val volume: Long = 0L,
    val marketCap: Long = 0L
)