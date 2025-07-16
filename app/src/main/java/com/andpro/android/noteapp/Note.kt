package com.andpro.android.noteapp

import java.io.File

sealed class Item() {
    data class Note(
        val title: String,
        val checked: Boolean
    ): Item()

    data class Image(
        val title: String,
        val image: File
    ): Item()
}

