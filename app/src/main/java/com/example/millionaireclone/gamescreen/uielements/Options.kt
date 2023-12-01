package com.example.millionaireclone.gamescreen.uielements

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.repeatable
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.millionaireclone.R
import com.example.millionaireclone.data.OuterAnswer
import com.example.millionaireclone.data.OuterAnswerVisibility
import com.example.millionaireclone.gamescreen.CurrQuestionState
import com.example.millionaireclone.gamescreen.SecondLifeAbilityState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@Composable
fun AnswerOptions(
    answers: List<OuterAnswer>,
    currQuestionState: MutableStateFlow<CurrQuestionState>,
    secondLifeAbilityState: SecondLifeAbilityState,
    spendSecondLife: () -> Unit,
    updateQuestion: () -> Unit,
    aOptionClicked: () -> Unit,
    bOptionClicked: () -> Unit,
    cOptionClicked: () -> Unit,
    dOptionClicked: () -> Unit
) {
    val emptyAnswer = OuterAnswer("", "", "",false,false)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 30.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        AnswerOption(
            icon = R.drawable.big_a_orange,
            answer = answers.elementAtOrElse(0){emptyAnswer},
            currQuestionState = currQuestionState,
            secondLifeAbilityState = secondLifeAbilityState,
            spendSecondLife = spendSecondLife,
            onClicked = aOptionClicked,
            updateQuestion = updateQuestion
        )
        AnswerOption(
            icon = R.drawable.big_b_orange,
            answer = answers.elementAtOrElse(1){emptyAnswer},
            currQuestionState = currQuestionState,
            secondLifeAbilityState = secondLifeAbilityState,
            spendSecondLife = spendSecondLife,
            onClicked = bOptionClicked,
            updateQuestion = updateQuestion
        )
        AnswerOption(
            icon = R.drawable.big_c_orange,
            answer = answers.elementAtOrElse(2){emptyAnswer},
            currQuestionState = currQuestionState,
            secondLifeAbilityState = secondLifeAbilityState,
            spendSecondLife = spendSecondLife,
            onClicked = cOptionClicked,
            updateQuestion = updateQuestion,
        )
        AnswerOption(
            icon = R.drawable.big_d_orange,
            answer = answers.elementAtOrElse(3){emptyAnswer},
            currQuestionState = currQuestionState,
            secondLifeAbilityState = secondLifeAbilityState,
            spendSecondLife = spendSecondLife,
            onClicked = dOptionClicked,
            updateQuestion = updateQuestion
        )
    }
}

@Composable
fun AnswerOption(
    @DrawableRes icon: Int,
    answer: OuterAnswer,
    currQuestionState: MutableStateFlow<CurrQuestionState>,
    secondLifeAbilityState: SecondLifeAbilityState,
    spendSecondLife: () -> Unit,
    onClicked: () -> Unit,
    updateQuestion: () -> Unit
) {
    val currQuestionStateVal = currQuestionState.collectAsState().value

    val loseWinBorderColor = animateColorAsState(
        targetValue = if (answer.isChosen && currQuestionStateVal != CurrQuestionState.NotAnswered && answer.isCorrect)
            Color.Green
        else if (answer.isChosen && currQuestionStateVal != CurrQuestionState.NotAnswered && !answer.isCorrect)
            Color.Red
        else
            Color.Cyan,
        label = "loseWinBorderColor",
        animationSpec = repeatable(
            iterations = 10,
            animation = tween(durationMillis = 300),
            repeatMode = RepeatMode.Reverse
        ),
    ).value

    val votings = answer.votingResultPercent.collectAsState().value
    val votingBoxDivisionValue = animateFloatAsState(
        targetValue = if (votings == 0f) 0f else votings,
        label = "votingBoxWidth",
        animationSpec = tween(
            durationMillis = 1000,
            easing = EaseOut
        )
    )

    val visibilityMode = answer.visibilityMode.collectAsState().value
    Box(
        modifier = Modifier
            .padding(bottom = 20.dp)
            .fillMaxWidth()
            .height(65.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                if (currQuestionStateVal == CurrQuestionState.NotAnswered && secondLifeAbilityState == SecondLifeAbilityState.CurrInUse) {
                    onClicked()
                }

                if (currQuestionStateVal != CurrQuestionState.NotAnswered && secondLifeAbilityState == SecondLifeAbilityState.CurrInUse) {
                    onClicked()
                    /* Spend second life as the last one.
                    * Because otherwise we don't know which one answer is the next one after using second life.
                    * SecondLifeAbilityState.beenUsed doesn't help. Probably should fix that in the future, but it works */
                    spendSecondLife()

                    CoroutineScope(Dispatchers.Default).launch {
                        delay(3000)
                        updateQuestion()
                    }
                }
                else if (currQuestionStateVal == CurrQuestionState.NotAnswered && secondLifeAbilityState != SecondLifeAbilityState.CurrInUse) {
                    onClicked()
                    CoroutineScope(Dispatchers.Default).launch {
                        delay(3000)
                        updateQuestion()
                    }
                }

            }
    ) {
        AnimatedVisibility(
            visible = visibilityMode == OuterAnswerVisibility.Visible,
            exit = slideOutHorizontally(
                targetOffsetX = {
                    it * if (visibilityMode == OuterAnswerVisibility.NotVisibleSlideToLeft) -1 else 1
                },
                animationSpec = tween(
                    durationMillis = 500,
                    easing = LinearEasing
                )
            )
        ) {
            Row(Modifier.fillMaxSize()) {
                Image(
                    painter = painterResource(id = icon),
                    contentDescription = null,
                    modifier = Modifier
                        .border(
                            BorderStroke(
                                5.dp,
                                if (currQuestionStateVal != CurrQuestionState.NotAnswered)
                                    loseWinBorderColor
                                else
                                    Color.Cyan
                            ),
                            CircleShape
                        )
                        .background(Color(0xFF001233), CircleShape)
                        .padding(20.dp)
                        .fillMaxHeight()
                )
                BoxWithConstraints(
                    modifier = Modifier
                        .border(
                            BorderStroke(
                                5.dp,
                                if (currQuestionStateVal != CurrQuestionState.NotAnswered)
                                    loseWinBorderColor
                                else
                                    Color.Cyan
                            ),
                            CircleShape
                        )
                        .background(Color(0xFF001233), CircleShape)
                        .padding(10.dp)
                        .fillMaxSize(),
                    contentAlignment = Alignment.CenterStart
                ) {

                    Box(
                        modifier = Modifier
                            .background(Color(0xfffb7d17), CircleShape)
                            .fillMaxHeight()
                            .width(
                                maxWidth * votingBoxDivisionValue.value
                            )
                    )
                    Text(text = answer.text,
                        Modifier
                            .zIndex(1f)
                            .padding(start = 5.dp))
                }
            }
        }
    }


    LaunchedEffect(currQuestionStateVal) {
        if (currQuestionStateVal == CurrQuestionState.AnsweredRight) {
            spendSecondLife()

            delay(3000)
            updateQuestion()
        }
    }
}