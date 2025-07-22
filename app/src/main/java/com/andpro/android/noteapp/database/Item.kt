package com.andpro.android.noteapp

import android.graphics.Bitmap
sealed class Item() {

    data class Note(
        val title: String,
        val content: String,
        val checked: Boolean
    ): Item()

    data class Image(
        val title: String,
        val comment: String,
        val image: Bitmap,
    ): Item()
}

