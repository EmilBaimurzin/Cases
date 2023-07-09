package com.plicraz.zynner.game.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.plicraz.zynner.game.domain.other.OpenCaseItem

@Entity(tableName = OpenCaseItemDB.TABLE_NAME)
data class OpenCaseItemDB(
    @PrimaryKey(autoGenerate = true)
    val primaryId: Int,
    val id: Int,
    val starsAmount: Int
) {
    fun convert(): OpenCaseItem {
        return OpenCaseItem(
            id = id,
            primaryId = primaryId,
            starsAmount = starsAmount
        )
    }
    companion object {
        const val TABLE_NAME = "INVENTORY"
    }
}
