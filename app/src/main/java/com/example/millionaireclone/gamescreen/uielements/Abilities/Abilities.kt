package com.example.millionaireclone.gamescreen.uielements.Abilities

import androidx.annotation.DrawableRes
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.StartOffset
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.millionaireclone.R
import com.example.millionaireclone.gamescreen.CurrQuestionState
import com.example.millionaireclone.gamescreen.SecondLifeAbilityState
import kotlinx.coroutines.flow.MutableStateFlow

data class AbilitiesState(
    val votingAbilityAvailable: Boolean = true,
    val minusOptionsAbilityAvailable: Boolean = true,
    val callSpecialistAbilityAvailable: Boolean = true,
    val newLifeAbilityAvailable: Boolean = true,
    val resetAbilityAvailable: Boolean = true,
)

@Composable
fun AbilitiesButtons(
    onVotingAbility: () -> Unit,
    onMinusTwoAbility: () -> Unit,
    onCallAbility: () -> Unit,
    onSecondLifeAbility: () -> Unit,
    callAbilityAnswer: String,
    abilitiesState: AbilitiesState,

    currQuestionState: MutableStateFlow<CurrQuestionState>,
    secondLifeAbilityState: SecondLifeAbilityState,
) {
    val currQuestionState = currQuestionState.collectAsState().value

    val secondLifeAbilityAnim = animateColorAsState(
        label = "secondLifeAbilityAnim",
        targetValue = if (secondLifeAbilityState != SecondLifeAbilityState.CurrInUse)
            Color.Cyan
        else
            Color(0xFFFFA500),
        animationSpec = infiniteRepeatable(
            animation = tween(300),
            repeatMode = RepeatMode.Reverse,
            initialStartOffset = StartOffset(1000)
        )
    ).value

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, top = 20.dp, end = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        AbilityButton(
            icon = R.drawable.game_voting_icon,
            onClicked = onVotingAbility,
            abilityAvailable = abilitiesState.votingAbilityAvailable
        )

        AbilityButton(
            icon = R.drawable.game_minus2options_icon,
            onClicked = onMinusTwoAbility,
            abilityAvailable = abilitiesState.minusOptionsAbilityAvailable
        )

        AbilityButton(
            icon = callAnswerTextToPicture(callAbilityAnswer) ?: R.drawable.game_person_icon,
            onClicked = onCallAbility,
            abilityAvailable = abilitiesState.callSpecialistAbilityAvailable
        )

        AbilityButton(
            icon = R.drawable.game_new_life,
            onClicked = onSecondLifeAbility,
            abilityAvailable = abilitiesState.newLifeAbilityAvailable,
            borderColor = if (secondLifeAbilityState != SecondLifeAbilityState.CurrInUse)
                Color.Cyan
            else if (currQuestionState != CurrQuestionState.NotAnswered)
                secondLifeAbilityAnim
            else
                Color.Green
        )

        AbilityButton(
            icon = R.drawable.game_reset_icon,
            onClicked = {},
            abilityAvailable = abilitiesState.resetAbilityAvailable
        )
    }
}

@Preview
@Composable
fun AbilityButton(
    @DrawableRes icon: Int = R.drawable.game_person_icon,
    onClicked: () -> Unit = {},
    abilityAvailable: Boolean = true,
    borderColor : Color = Color.Cyan
) {
    Box(
        modifier = Modifier
            .size(65.dp)
    ) {
        IconButton(
            onClick = {
                if (abilityAvailable) onClicked()
            },
            modifier = Modifier
                .border(BorderStroke(5.dp, borderColor), CircleShape)
                .background(Color(0xFF001233), CircleShape)
                .fillMaxSize()
                .padding(20.dp)
        ) {
            Image(
                painter = painterResource(id = icon),
                contentDescription = null,
                modifier = Modifier.fillMaxSize()
            )

        }
        //if not available draw a red cross
        if (!abilityAvailable) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(10.dp)
                    .rotate(45f)
                    .background(Color.Red)
                    .align(Alignment.Center)
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(10.dp)
                    .rotate(-45f)
                    .background(Color.Red)
                    .align(Alignment.Center)
            )
        }
    }
}