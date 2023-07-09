package com.plicraz.zynner.game.common

import android.app.Application
import com.plicraz.zynner.game.database.DataBase

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        DataBase.init(this)
    }
}