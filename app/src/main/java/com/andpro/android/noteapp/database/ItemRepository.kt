package com.andpro.android.noteapp.database

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.andpro.android.noteapp.Item
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import java.util.UUID

class ItemRepository(context: Context) {
    //    private val coroutineScope: CoroutineScope = GlobalScope
    private val database = Room.databaseBuilder(
        context = context.applicationContext,
        klass = ItemDatabase::class.java,
        name = "ItemDatabase"
    )
        .build()


    companion object {
        private var INSTANCE: ItemRepository? = null
        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = ItemRepository(context)
            } else {
                Log.d("IR", "INSTANCE is already initialized")
            }
        }

        fun getInstance(): ItemRepository {
            return INSTANCE
                ?: throw IllegalArgumentException("The initialize function did not work")
        }
    }

    suspend fun addImage(image: Item.Image) = database.imageDao().addImage(
        ImageEntity(
            id = UUID.randomUUID(),
            title = image.title,
            image = image.image,
            comment = image.comment
        )
    )

    suspend fun getImages(): MutableList<Item.Image> = database.imageDao().getImages()

    suspend fun addNote(note: Item.Note) = database.noteDao().addNote(
        NoteEntity(
            id = UUID.randomUUID(),
            title = note.title,
            content = note.content
        )
    )

    suspend fun getNotes(): MutableList<Item.Note> = database.noteDao().getNotes()

}