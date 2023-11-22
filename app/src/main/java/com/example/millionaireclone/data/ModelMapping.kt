package com.example.millionaireclone.data

import com.example.millionaireclone.data.Models.Answer
import com.example.millionaireclone.data.Models.QuestionWithAnswer

fun Answer.toOuter() = OuterAnswer(
    id = this.id,
    questionId = this.questionId,
    text = this.text,
    isChosen = false,
    isCorrect = isCorrect
)

fun OuterAnswer.toInner() = Answer(
    id = this.id,
    questionId = this.questionId,
    text = this.text,
    isCorrect = isCorrect
)
@JvmName("innerToOuterAnswer")
fun List<Answer>.toOuter() = map(Answer::toOuter)

@JvmName("outerToInnerAnswer")
fun List<OuterAnswer>.toInner() = map(OuterAnswer::toInner)

fun QuestionWithAnswer.toOuter() = OuterQuestionWithAnswer(
    question = question,
    answers = answers.toOuter()
)

fun OuterQuestionWithAnswer.toInner() = QuestionWithAnswer(
    question = question,
    answers = answers.toInner()
)

@JvmName("innerToOuterQuestionWithAnswer")
fun List<QuestionWithAnswer>.toOuter() = map(QuestionWithAnswer::toOuter)

@JvmName("outerToInnerQuestionWithAnswer")
fun List<OuterQuestionWithAnswer>.toInner() = map(OuterQuestionWithAnswer::toInner)

