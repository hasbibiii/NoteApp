package com.andpro.android.noteapp.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "Notes")
data class NoteEntity(
    @PrimaryKey
    val id: UUID,
    val title: String,
    val content: String
) {
}