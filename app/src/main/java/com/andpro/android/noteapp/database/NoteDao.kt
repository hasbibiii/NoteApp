package com.andpro.android.noteapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.andpro.android.noteapp.Item
import java.util.UUID

@Dao
interface NoteDao {
    @Insert
    suspend fun addNote(note: NoteEntity)

    @Query("SELECT * FROM Notes")
    suspend fun getNotes(): MutableList<NoteEntity>
}