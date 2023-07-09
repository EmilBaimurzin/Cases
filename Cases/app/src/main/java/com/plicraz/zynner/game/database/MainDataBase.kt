package com.plicraz.zynner.game.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [OpenCaseItemDB::class], version = MainDataBase.DB_VERSION, exportSchema = true)
abstract class MainDataBase : RoomDatabase() {

    abstract fun dao(): InventoryDao

    companion object {
        const val DB_VERSION = 1
        const val DB_NAME = "MainDataBase"
    }
}