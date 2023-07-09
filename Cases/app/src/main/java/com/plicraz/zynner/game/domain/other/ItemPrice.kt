package com.plicraz.zynner.game.domain.other

import com.plicraz.zynner.R

data class ItemPrice(
    val item5: Int = 40,
    val item4: Int = 120,
    val item3: Int = 380,
    val item2: Int = 800,
    val item1: Int = 3000,
    val star1: Double = 1.0,
    val star2: Double = 1.5,
    val star3: Double = 3.0
) {
    fun getPrice(item: OpenCaseItem): Double {
        val itemPriceInstance = this
        val itemPrice = when (item.id) {
            1 -> itemPriceInstance.item1
            2 -> itemPriceInstance.item2
            3 -> itemPriceInstance.item3
            4 -> itemPriceInstance.item4
            else -> itemPriceInstance.item5
        }
        val starMultiplier = when (item.starsAmount) {
            1 -> itemPriceInstance.star1
            2 -> itemPriceInstance.star2
            else -> itemPriceInstance.star3
        }
        return itemPrice * starMultiplier
    }


    fun getImage(id: Int): Int {
        return when (id) {
            1 -> R.drawable.item_1
            2 -> R.drawable.item_2
            3 -> R.drawable.item_3
            4 -> R.drawable.item_4
            else -> R.drawable.item_5
        }
    }
}
