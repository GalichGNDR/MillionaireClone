package com.example.millionaireclone.data.source

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.millionaireclone.data.Models.Answer
import com.example.millionaireclone.data.Models.Question

@Database(
    entities = [Question::class,Answer::class],
    version = 1,
    exportSchema = true,
)
abstract class MillionaireDatabase : RoomDatabase() {
    abstract fun getDao(): MillionaireDao
}