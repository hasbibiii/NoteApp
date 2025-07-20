package com.andpro.android.noteapp.database

import android.content.Context
import androidx.room.Room
import com.andpro.android.noteapp.ItemEntity

class ItemRepository private constructor(context: Context) {

    private val database = Room.databaseBuilder(
        context = context.applicationContext,
        klass = ItemDatabase::class.java,
        name = "Item database"
    ).build()

    companion object {
        private var INSTANCE: ItemRepository? = null
        fun initialize(_context: Context) {
            if (INSTANCE == null) {
                INSTANCE = ItemRepository(_context)
            }
        }

        fun getInstance(): ItemRepository {
            return INSTANCE ?: throw IllegalStateException()
        }
    }

    suspend fun addNote(_note: ItemEntity.NoteEntity) = database.itemDao().addNote(_note)
    suspend fun addImage(_image: ItemEntity.ImageEntity) = database.itemDao().addImage(_image)
    suspend fun getNotes(): List<ItemEntity.NoteEntity> = database.itemDao().getNotes()
    suspend fun getImages(): List<ItemEntity.ImageEntity> = database.itemDao().getImages()
}