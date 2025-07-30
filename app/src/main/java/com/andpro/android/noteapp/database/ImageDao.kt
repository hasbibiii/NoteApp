package com.andpro.android.noteapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.andpro.android.noteapp.Item

@Dao
interface ImageDao {
    @Insert
    suspend fun addImage(image: ImageEntity)

    @Query("SELECT * FROM Images")
    suspend fun getImages(): MutableList<Item.Image>
}