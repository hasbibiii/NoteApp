package com.andpro.android.noteapp

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey

sealed class ItemEntity() {
    @Entity(tableName = "Note")
    data class NoteEntity(
        @PrimaryKey
        val title: String,
        val content: String,
        val checked: Boolean
    ): ItemEntity()
    @Entity(tableName = "Image")
    data class ImageEntity(
        @PrimaryKey
        val title: String,
        val comment: String,
        val image: Bitmap,
    ): ItemEntity()
}

