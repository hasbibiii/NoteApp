package com.andpro.android.noteapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.andpro.android.noteapp.database.ItemRepository

class MainActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ItemRepository.initialize(this)
        setContentView(R.layout.activity_main)
    }
}