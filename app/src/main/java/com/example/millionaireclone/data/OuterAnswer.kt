package com.example.millionaireclone.data
import kotlinx.coroutines.flow.MutableStateFlow

data class OuterAnswer(
    val id: String,
    val text: String,
    var questionId: String,
    var isChosen: Boolean = false,
    var isCorrect: Boolean,
    var votingResultPercent: MutableStateFlow<Float> = MutableStateFlow(0f),
    var visibilityMode: MutableStateFlow<OuterAnswerVisibility> = MutableStateFlow(OuterAnswerVisibility.Visible)
)

enum class OuterAnswerVisibility{
    Visible,
    NotVisibleSlideToLeft,
    NotVisibleSlideToRight
}

