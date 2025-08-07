package com.andpro.android.noteapp.database

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.andpro.android.noteapp.Item
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import java.util.UUID

class ItemRepository(context: Context) {
    private val coroutineScope: CoroutineScope = GlobalScope
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

    var idToNote: MutableMap<UUID, Item.Note> = mutableMapOf()
    var titleToID: MutableMap<String, UUID> = mutableMapOf()
    var idToImage: MutableMap<UUID, Item.Image> = mutableMapOf()

    suspend fun addImage(image: Item.Image) = database.imageDao().addImage(
        ImageEntity(
            id = UUID.randomUUID(),
            title = image.title,
            image = image.image,
            comment = image.comment
        )
    )

    suspend fun getImages(): MutableList<Item.Image> {
        val imageEntities = database.imageDao().getImages()
        var imagereturnlist: MutableList<Item.Image> = mutableListOf()
        for (i in imageEntities) {
            val image = Item.Image(i.title, i.comment, i.image)
            idToImage[i.id] = image
            titleToID[i.title] = i.id
            imagereturnlist.add(image)
        }
        return imagereturnlist
    }

    suspend fun addNote(note: Item.Note) = database.noteDao().addNote(
        NoteEntity(
            id = UUID.randomUUID(),
            title = note.title,
            content = note.content
        )
    )

    fun getImage(id: UUID): Item.Image {
        return idToImage[id]!!
    }

    fun getNote(id: UUID): Item.Note {
        return idToNote[id]!!
    }

    suspend fun getNotes(): MutableList<Item.Note> {
        var notereturnlist: MutableList<Item.Note> = mutableListOf()
        val noteEntities = database.noteDao().getNotes()
        for (i in noteEntities) {
            val note = Item.Note(i.title, i.content)
            idToNote[i.id] = note
            titleToID[i.title] = i.id
            notereturnlist.add(note)
        }
        return notereturnlist
    }
}