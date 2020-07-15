package com.ramonguimaraes.bitcoinbaron

class Coin {

    fun calcular(value: Double, actualPrice: Double): Double {
        return value / actualPrice
    }

}