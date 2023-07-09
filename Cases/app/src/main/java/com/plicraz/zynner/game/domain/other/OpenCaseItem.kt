package com.plicraz.zynner.game.domain.other

import com.plicraz.zynner.game.database.OpenCaseItemDB
import java.io.Serializable

data class OpenCaseItem(
    val id: Int,
    val starsAmount: Int,
    val primaryId: Int = 0
) : Serializable {
    fun convertToDB(): OpenCaseItemDB {
        return OpenCaseItemDB(
            primaryId = primaryId,
            id = id,
            starsAmount = starsAmount
        )
    }
}
