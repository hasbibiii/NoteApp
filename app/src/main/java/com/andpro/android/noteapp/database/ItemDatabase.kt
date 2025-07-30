package com.andpro.android.noteapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@TypeConverters(BitmapTypeConverter::class)
@Database(entities = [ImageEntity::class, NoteEntity::class], version = 1)
abstract class ItemDatabase : RoomDatabase() {
    abstract fun imageDao(): ImageDao
    abstract fun noteDao(): NoteDao
}