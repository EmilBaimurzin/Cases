package com.plicraz.zynner.game.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface InventoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addToDB(caseItemDB: OpenCaseItemDB)

    @Query("SELECT * FROM ${OpenCaseItemDB.TABLE_NAME}")
    fun getAll(): List<OpenCaseItemDB>

    @Query("SELECT * FROM ${OpenCaseItemDB.TABLE_NAME}" + " WHERE primaryId = :primaryId")
    fun findItem(primaryId: Int): List<OpenCaseItemDB>

    @Query("DELETE FROM ${OpenCaseItemDB.TABLE_NAME}" + " WHERE primaryId = :primaryId")
    fun deleteItem(primaryId: Int)
}