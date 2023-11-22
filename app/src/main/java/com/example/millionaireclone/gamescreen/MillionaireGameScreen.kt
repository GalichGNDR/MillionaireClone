package com.example.millionaireclone.gamescreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.millionaireclone.gamescreen.OptionIndexes.A_OPTION_INDEX
import com.example.millionaireclone.gamescreen.OptionIndexes.B_OPTION_INDEX
import com.example.millionaireclone.gamescreen.OptionIndexes.C_OPTION_INDEX
import com.example.millionaireclone.gamescreen.OptionIndexes.D_OPTION_INDEX
import com.example.millionaireclone.gamescreen.uielements.*
import com.example.millionaireclone.gamescreen.uielements.Abilities.AbilitiesButtons
import com.example.millionaireclone.gamescreen.uielements.Abilities.CallAbilityWindow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow

object OptionIndexes {
    const val A_OPTION_INDEX = 0
    const val B_OPTION_INDEX = 1
    const val C_OPTION_INDEX = 2
    const val D_OPTION_INDEX = 3
}

enum class CurrQuestionState {
    NotAnswered,
    AnsweredRight,
    AnsweredWrong
}

@Composable
fun MillionaireGameScreen(
    viewModel: MillionaireGameScreenViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsState().value
    val isCallAbilityWindowShown = remember {
        mutableStateOf(false)
    }
    val isTransitionWindowShown = remember {
        mutableStateOf(false)
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        TopGameBar()
        QuestionWindow(
            height = 200,
            question = uiState.currItem?.question
        )
        AbilitiesButtons(
            onVotingAbility = viewModel::onVotingAbilityInUse,
            onMinusTwoAbility = viewModel::onMinusTwoAbilityInUse,
            onCallAbility = {
                viewModel.onCallAbilityInUse()
                isCallAbilityWindowShown.value = true
            },
            onSecondLifeAbility = {viewModel.changeSecondLifeAbilityStateTo(SecondLifeAbilityState.CurrInUse)},
            callAbilityAnswer = uiState.callAbilityAnswer,
            abilitiesState = uiState.abilitiesState
        )
        AnswerOptions(
            answers = uiState.currItem?.answers?: emptyList(),
            currQuestionState = uiState.currItem?.state ?: MutableStateFlow(CurrQuestionState.NotAnswered),
            updateQuestion = {isTransitionWindowShown.value = true},
            secondLifeAbilityState = uiState.secondLifeAbilityState,
            spendSecondLife = {viewModel.changeSecondLifeAbilityStateTo(SecondLifeAbilityState.BeenUsed) },
            aOptionClicked = {viewModel.fixAnswerAndChangeState(A_OPTION_INDEX)},
            bOptionClicked = {viewModel.fixAnswerAndChangeState(B_OPTION_INDEX)},
            cOptionClicked = {viewModel.fixAnswerAndChangeState(C_OPTION_INDEX)},
            dOptionClicked = {viewModel.fixAnswerAndChangeState(D_OPTION_INDEX)},
        )
    }

    if (isCallAbilityWindowShown.value) {
        CallAbilityWindow(
            answerOption = uiState.callAbilityAnswer,
            onClose = {isCallAbilityWindowShown.value = false}
        )
    }
    if (isTransitionWindowShown.value) {
        TransitionWindow(uiState.currItem!!.number)
        LaunchedEffect(isTransitionWindowShown.value) {
            delay(2000)
            viewModel.updateItem()

            delay(2000)
            isTransitionWindowShown.value = false
        }
    }
}





