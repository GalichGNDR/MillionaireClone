package com.example.millionaireclone.data.Models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "questions")
data class Question(
    @PrimaryKey val id: String,
    val text: String,
    @ColumnInfo(defaultValue = "")
    val rightAnswer: String
)