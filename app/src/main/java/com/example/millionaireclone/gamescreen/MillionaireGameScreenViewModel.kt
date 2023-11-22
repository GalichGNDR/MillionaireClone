package com.example.millionaireclone.gamescreen

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.millionaireclone.data.Models.Answer
import com.example.millionaireclone.data.Models.Question
import com.example.millionaireclone.data.Models.QuestionWithAnswer
import com.example.millionaireclone.data.OuterAnswer
import com.example.millionaireclone.data.OuterAnswerVisibility
import com.example.millionaireclone.data.OuterQuestionWithAnswer
import com.example.millionaireclone.data.RepositoryDefault
import com.example.millionaireclone.di.AppDispatcherDefault
import com.example.millionaireclone.gamescreen.uielements.Abilities.AbilitiesState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.random.Random

data class UiState(
    val isLoading: Boolean = false,
    val currItem: OuterQuestionWithAnswer? = null,
    val isUpdating: Boolean = false,
    val callAbilityAnswer: String = "",
    val secondLifeAbilityState: SecondLifeAbilityState = SecondLifeAbilityState.NotBeenUsed,
    val abilitiesState: AbilitiesState = AbilitiesState()
)

@HiltViewModel
class MillionaireGameScreenViewModel @Inject constructor(
    private val repository: RepositoryDefault,
    @AppDispatcherDefault val dispatcher: CoroutineDispatcher
) : ViewModel() {
    private val _currItems = MutableStateFlow<List<OuterQuestionWithAnswer>>(emptyList()) //not in uiState

    private val  _isLoading = MutableStateFlow(false)
    private val _currItem = MutableStateFlow<OuterQuestionWithAnswer?>(null)

    private val _callAbilityAnswer = MutableStateFlow("")
    private val _secondLifeAbilityState = MutableStateFlow(SecondLifeAbilityState.NotBeenUsed)
    private val _abilitiesState = MutableStateFlow(AbilitiesState())

    private val _currItemRightAnswer = _currItem.map { it?.question?.rightAnswer ?: "" }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = ""
        )

    val uiState = combine(_isLoading,_currItem,_callAbilityAnswer,_secondLifeAbilityState,_abilitiesState) {
            isLoading,currItem,callAbilityAnswer,secondLifeAbilityState,abilitiesState->
        UiState(
            isLoading = isLoading,
            currItem = currItem,
            callAbilityAnswer = callAbilityAnswer,
            secondLifeAbilityState = secondLifeAbilityState,
            abilitiesState = abilitiesState
        )
    }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = UiState()
        )

    init {
        loadNext15Items()
    }
    private fun loadNext15Items() {
        viewModelScope.launch(dispatcher) {
            _currItems.update {
                repository.getBatchOfQuestionsWithAnswer()
            }
            _currItem.value = if (_currItems.value.isNotEmpty()) {
                val currItem = _currItems.value[0]
                currItem.answers = _currItems.value[0].answers.shuffled()
                currItem.number.value = 0

                currItem
            } else
                null
        }
    }
    fun updateItem() {
        val newItemIndex = _currItem.value!!.number.value+1

        if (_currItems.value.size > newItemIndex) {

            val nextItem = _currItems.value[newItemIndex]
            nextItem.answers = nextItem.answers.shuffled()
            nextItem.number.value = newItemIndex

            _currItem.value = nextItem

            if (_callAbilityAnswer.value != "") {
                _abilitiesState.value = _abilitiesState.value.copy(
                    callSpecialistAbilityAvailable = false
                )
            }
        }
    }

    fun fixAnswerAndChangeState(optionIndex: Int) {
        _currItem.value!!.answers[optionIndex].isChosen = true

        changeCurrQuestionState(optionIndex)

    }
    private fun changeCurrQuestionState(optionIndex: Int) {
        val rightAnswer = _currItemRightAnswer.value
        val userAnswer = uiState.value.currItem!!.answers[optionIndex].text

        if (rightAnswer == userAnswer) {
            _currItem.value!!.state.value = CurrQuestionState.AnsweredRight
        } else {
            _currItem.value!!.state.value = CurrQuestionState.AnsweredWrong
        }
    }

    fun onVotingAbilityInUse() {
        val rightAnswer = _currItemRightAnswer.value

        for (answer in _currItem.value!!.answers) {
            val rand = if (answer.text == rightAnswer) {
                0.65f + Random.nextFloat() % 0.15f
            } else {
                0.15f + Random.nextFloat() % 0.5f
            }

            answer.votingResultPercent.value = rand
        }

        _abilitiesState.value = _abilitiesState.value.copy(
            votingAbilityAvailable = false
        )
    }

    fun onMinusTwoAbilityInUse() {
        val rightAnswer = _currItemRightAnswer.value

        var lastVisibilityMode: OuterAnswerVisibility? = null
        var count = 0
        for (answer in _currItem.value!!.answers.shuffled()) {
            if (answer.text == rightAnswer || count >= 2) continue

            if (lastVisibilityMode == OuterAnswerVisibility.NotVisibleSlideToLeft) {
                answer.visibilityMode.value = OuterAnswerVisibility.NotVisibleSlideToRight
                lastVisibilityMode = OuterAnswerVisibility.NotVisibleSlideToRight
            } else {
                answer.visibilityMode.value = OuterAnswerVisibility.NotVisibleSlideToLeft
                lastVisibilityMode = OuterAnswerVisibility.NotVisibleSlideToLeft
            }

            count++
        }

        _abilitiesState.value = _abilitiesState.value.copy(
            minusOptionsAbilityAvailable = false
        )
    }

    fun onCallAbilityInUse() {
        val letters = listOf("A","B","C","D")
        for (i in 0 .. _currItem.value!!.answers.lastIndex) {
            if (_currItem.value!!.answers[i].text == _currItemRightAnswer.value) {
                _callAbilityAnswer.value = letters[i]
            }
        }
    }
    fun changeSecondLifeAbilityStateTo(state: SecondLifeAbilityState) {
        if (state == SecondLifeAbilityState.BeenUsed && _secondLifeAbilityState.value == SecondLifeAbilityState.CurrInUse ||
            state == SecondLifeAbilityState.CurrInUse && _secondLifeAbilityState.value == SecondLifeAbilityState.NotBeenUsed)
            _secondLifeAbilityState.value = state

    }


    /*init {
        viewModelScope.launch(dispatcher) {
            repository.clearQuestionsAndAnswers()
        }
    }*/
    /*init {
        viewModelScope.launch(dispatcher) {
            val question = Question(
                id = UUID.randomUUID().toString(),
                text = "Who is The President of Ukraine?",
                rightAnswer = "Zelensky"
            )
            repository.addItem(
                question,
                listOf(
                    OuterAnswer(
                        id = UUID.randomUUID().toString(),
                        text = "Zelensky",
                        questionId = question.id,
                        isCorrect = true
                    ),
                    OuterAnswer(
                        id = UUID.randomUUID().toString(),
                        text = "Putin",
                        questionId = question.id,
                        isCorrect = false
                    ),
                    OuterAnswer(
                        id = UUID.randomUUID().toString(),
                        text = "Xi",
                        questionId = question.id,
                        isCorrect = false
                    ),
                    OuterAnswer(
                        id = UUID.randomUUID().toString(),
                        text = "I am",
                        questionId = question.id,
                        isCorrect = false
                    ),
                )
            )
        }
    }*/
}

enum class SecondLifeAbilityState {
    NotBeenUsed,
    CurrInUse,
    BeenUsed
}