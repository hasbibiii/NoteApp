package com.andpro.android.noteapp.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.andpro.android.noteapp.Item
import java.util.UUID

@Dao
interface ImageDao {
    @Insert
    suspend fun addImage(image: ImageEntity)

    @Query("SELECT * FROM Images")
    suspend fun getImages(): MutableList<ImageEntity>
}