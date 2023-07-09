package com.plicraz.zynner.game.domain.repositories

import com.plicraz.zynner.game.database.DataBase
import com.plicraz.zynner.game.domain.other.ItemPrice
import com.plicraz.zynner.game.domain.other.OpenCaseItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class InventoryRepository {
    suspend fun getItems(): MutableList<OpenCaseItem> {
        return suspendCoroutine { continuation ->
            CoroutineScope(Dispatchers.IO).launch {
                val itemsDB = DataBase.instance.dao().getAll()
                val items = itemsDB.map {
                    val converted = it.convert()
                    Pair(ItemPrice().getPrice(converted), converted)
                }
                continuation.resume((items.sortedBy { (it.first).toInt() }
                    .map { it.second }).reversed().toMutableList()  )
            }
        }
    }

    fun removeItem(itemId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            DataBase.instance.dao().deleteItem(itemId)
        }
    }
}