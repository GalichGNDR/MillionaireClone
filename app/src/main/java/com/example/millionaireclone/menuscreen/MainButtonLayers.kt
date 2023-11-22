package com.example.millionaireclone.menuscreen

import androidx.annotation.DrawableRes
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.millionaireclone.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@Composable
fun MainButtonsFirstLayer(
    onPlayClicked: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 30.dp, end = 30.dp)
    ) {
        MainIconButton(
            iconRes = R.drawable.volume_up,
            borderStroke = BorderStroke(8.dp, Color.Cyan),
            shape = CircleShape,
            backgroundColor = Color.Black,
            allPadding = 15,
            size = 45,
            onClick = {

            }
        )
        Spacer(modifier = Modifier.weight(1f))
        MainIconButton(
            iconRes = R.drawable.play_arrow,
            borderStroke = BorderStroke(8.dp, Color.Green),
            backgroundColor = Color.Black,
            shape = CircleShape ,
            allPadding = 10,
            size = 90,
            onClick = onPlayClicked
        )
        Spacer(modifier = Modifier.weight(1f))
        MainIconButton(
            iconRes = R.drawable.question_mark,
            borderStroke = BorderStroke(8.dp, Color.Cyan),
            backgroundColor = Color.Black,
            shape = CircleShape ,
            allPadding = 15,
            size = 45,
            onClick = {}
        )
    }
}
@Composable
fun MainButtonsSecondLayer(
    buttonSize: Int,
    buttonPadding: Int
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 60.dp, end = 60.dp)
    ) {
        MainIconButton(
            iconRes = R.drawable.first_place_ratings,
            borderStroke = BorderStroke(8.dp, Color.Cyan),
            backgroundColor = Color.Black,
            shape = CircleShape ,
            allPadding = 20,
            size = 32,
            onClick = {}
        )
        MainIconButton(
            iconRes = R.drawable.trophy_cup_rewards,
            borderStroke = BorderStroke(8.dp, Color.Cyan),
            backgroundColor = Color.Black,
            shape = CircleShape ,
            allPadding = buttonPadding,
            size = buttonSize,
            onClick = {}
        )
    }
}
@Composable
fun MainButtonsLastLayer() {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .absoluteOffset(y = (-35).dp)
    ) {
        MainIconButton(
            iconRes = R.drawable.person,
            borderStroke = BorderStroke(8.dp, Color.Green),
            backgroundColor = Color.Black,
            shape = CircleShape ,
            allPadding = 15,
            size = 45,
            onClick = {}
        )
    }
}

@Composable
fun MainIconButton(
    @DrawableRes iconRes: Int,
    borderStroke: BorderStroke,
    backgroundColor: Color,
    shape: Shape,
    allPadding: Int,
    size: Int,
    onClick: () -> Unit
) {
    // Fore pressing animation without any affect besides that
    val interactionSource = remember() { MutableInteractionSource() }
    val isPressed = interactionSource.collectIsPressedAsState()

    var scale = animateFloatAsState(
        targetValue = if (isPressed.value) 0.9f else 1f,
        label = "",
        animationSpec = tween(durationMillis = 150)
    )

    Box(
        modifier = Modifier
            .size((size + allPadding * 2).dp)
            .scale(scale.value)
    ) {
        IconButton(
            interactionSource = interactionSource,
            onClick = onClick,
            modifier = Modifier
                .border(borderStroke, CircleShape)
                .background(backgroundColor, shape)
                .padding(allPadding.dp)
                .size(size.dp)
        ) {
            Image(painter = painterResource(id = iconRes), contentDescription = null, Modifier.fillMaxSize())
        }
    }

}