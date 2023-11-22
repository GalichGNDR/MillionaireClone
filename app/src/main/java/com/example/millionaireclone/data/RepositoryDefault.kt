package com.example.millionaireclone.data

import androidx.room.Query
import com.example.millionaireclone.data.Models.Answer
import com.example.millionaireclone.data.Models.Question
import com.example.millionaireclone.data.Models.QuestionWithAnswer
import com.example.millionaireclone.data.Repository
import com.example.millionaireclone.data.source.MillionaireDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepositoryDefault @Inject constructor(
    private val appDao: MillionaireDao
): Repository {

    suspend fun addItem(question: Question, answers: List<OuterAnswer>) {
        appDao.insertQuestionWithAnswers(question,answers.toInner())
    }

    fun getAllItems(): Flow<List<OuterQuestionWithAnswer>> {
        return appDao.getAllItems().map {
            it.toOuter()
        }
    }

    suspend fun deleteQuestionWithAnswers(question: Question,answers: List<OuterAnswer>) {
        appDao.deleteQuestionWithAnswers(question,answers.toInner())
    }

    suspend fun clearQuestionsAndAnswers() {
        appDao.clearQuestionsAndAnswers()
    }

    fun getQuestionWithAnswerById(id : String): Flow<OuterQuestionWithAnswer> {
        return appDao.getQuestionWithAnswerById(id).map { it.toOuter() }
    }

    fun getQuestionCount(): Flow<Int> {
        return appDao.getQuestionCount()
    }

    suspend fun getBatchOfQuestionsWithAnswer(): List<OuterQuestionWithAnswer> {
        return appDao.getBatchOfQuestionsWithAnswer().map { it.toOuter() }
    }
}