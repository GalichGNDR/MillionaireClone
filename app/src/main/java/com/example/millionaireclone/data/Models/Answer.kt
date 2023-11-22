package com.example.millionaireclone.data.Models

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.Ignore
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "answers")
data class Answer(
    @PrimaryKey val id: String,
    val text: String,
    var questionId: String,
    @ColumnInfo(defaultValue = "0")
    var isCorrect: Boolean = false,
)

