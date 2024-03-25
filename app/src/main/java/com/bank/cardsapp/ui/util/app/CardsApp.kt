package com.bank.cardsapp.ui.util.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import io.paperdb.Paper

@HiltAndroidApp
class CardsApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Paper.init(this)
    }
}