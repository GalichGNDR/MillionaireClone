package com.example.millionaireclone.data.source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.millionaireclone.data.Models.Answer
import com.example.millionaireclone.data.Models.Question
import com.example.millionaireclone.data.Models.QuestionWithAnswer
import kotlinx.coroutines.flow.Flow

@Dao
interface MillionaireDao {
    @Insert
    suspend fun insertQuestion(question: Question)
    @Insert
    suspend fun insertAnswers(answers: List<Answer>)
    @Transaction
    suspend fun insertQuestionWithAnswers(question: Question,answers: List<Answer>) {
        insertQuestion(question)

        answers.forEach {
            it.questionId = question.id
        }

        insertAnswers(answers)
    }


    @Delete
    suspend fun deleteQuestion(question: Question)
    @Delete
    suspend fun deleteAnswer(answer: Answer)
    @Transaction
    suspend fun deleteQuestionWithAnswers(question: Question,answers: List<Answer>) {
        deleteQuestion(question)

        for (answer in answers) {
            deleteAnswer(answer)
        }
    }

    @Query("SELECT * FROM questions")
    @Transaction
    fun getAllItems(): Flow<List<QuestionWithAnswer>>


    @Transaction
    @Query("SELECT * FROM questions WHERE id = :id")
    fun getQuestionWithAnswerById(id : String): Flow<QuestionWithAnswer>

    @Query("SELECT COUNT(id) FROM questions")
    fun getQuestionCount(): Flow<Int>

    @Query("DELETE FROM answers")
    suspend fun clearAnswers()
    @Query("DELETE FROM questions")
    suspend fun clearQuestions()
    @Transaction
    suspend fun clearQuestionsAndAnswers() {
        clearAnswers()
        clearQuestions()
    }

    @Transaction
    @Query("SELECT * FROM questions ORDER BY random() LIMIT 15")
    suspend fun getBatchOfQuestionsWithAnswer(): List<QuestionWithAnswer>
}