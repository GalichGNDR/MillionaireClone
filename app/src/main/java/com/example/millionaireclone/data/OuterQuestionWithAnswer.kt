package com.example.millionaireclone.data

import com.example.millionaireclone.data.Models.Question
import com.example.millionaireclone.gamescreen.CurrQuestionState
import kotlinx.coroutines.flow.MutableStateFlow

data class OuterQuestionWithAnswer(
    val question: Question,
    var answers: List<OuterAnswer>,
    var state: MutableStateFlow<CurrQuestionState> = MutableStateFlow(CurrQuestionState.NotAnswered),
    var number: MutableStateFlow<Int> = MutableStateFlow(0)
)
