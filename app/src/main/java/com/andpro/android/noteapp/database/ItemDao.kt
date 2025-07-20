package com.andpro.android.noteapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.andpro.android.noteapp.ItemEntity

@Dao
interface ItemDao {
    @Insert
    suspend fun addNote(_note: ItemEntity.NoteEntity)

    @Insert
    suspend fun addImage(_image: ItemEntity.ImageEntity)

    @Query("SELECT * FROM Note")
    suspend fun getNotes(): List<ItemEntity.NoteEntity>

    @Query("SELECT * FROM Image")
    suspend fun getImages(): List<ItemEntity.ImageEntity>
}