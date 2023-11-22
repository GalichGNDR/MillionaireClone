package com.example.millionaireclone.data.Models

import androidx.room.Embedded
import androidx.room.Relation
import com.example.millionaireclone.data.OuterAnswer
import com.example.millionaireclone.data.OuterQuestionWithAnswer
import com.example.millionaireclone.data.toOuter


data class QuestionWithAnswer(
    @Embedded val question: Question,
    @Relation(
        parentColumn = "id",
        entityColumn = "questionId"
    )
    val answers: List<Answer>,
)