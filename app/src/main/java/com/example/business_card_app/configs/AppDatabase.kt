package com.example.business_card_app.configs

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.business_card_app.dao.NoteDao
import com.example.business_card_app.models.Note

@Database(entities = [Note::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun noteDao() : NoteDao

}