package com.andpro.android.noteapp.database

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "Images")
data class ImageEntity(
    @PrimaryKey
    val id: UUID,
    val title: String,
    val comment: String,
    val image: Bitmap
) {

}