package com.andpro.android.noteapp.database

import android.app.Application

class ItemApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        ItemRepository.initialize(this)
    }
}