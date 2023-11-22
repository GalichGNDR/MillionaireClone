package com.example.millionaireclone.menuscreen

import android.view.MotionEvent
import androidx.annotation.DrawableRes
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import com.example.millionaireclone.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun MillionaireMenuScreen(
    viewModel: MillionaireMenuScreenViewModel = hiltViewModel(),
    onPlayClicked: () -> Unit
) {
    val isLoading = viewModel.isLoading.collectAsState().value

    BoxWithConstraints(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {

        Image(
            painter = painterResource(id = R.drawable.millionaire_app_icon),
            contentDescription = null,
            modifier = Modifier
                .size(450.dp)
                .padding(top = 50.dp)
        )
        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(maxHeight / 2)
                    .align(Alignment.BottomCenter)
            ) {
                Text(
                    text = "ЗАГРУЗКА",
                    fontSize = 35.sp,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .align(Alignment.Center)
                )
            }
        } else {

            AdditionalUpperButtons(
                buttonSize = 70,
                buttonPadding = 10
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(maxHeight / 2)
                    .align(Alignment.BottomCenter)
            ) {
                MainMenuButtons(onPlayClicked)
            }
        }
    }
}

@Composable
fun AdditionalUpperButtons(
    buttonSize: Int,
    buttonPadding: Int
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(
            onClick = { /*TODO*/},
            Modifier
                .size(buttonSize.dp)
                .padding(buttonPadding.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.no_ads_icon_button),
                contentDescription = null,
            )
        }

        IconButton(
            onClick = { /*TODO*/},
            Modifier
                .size(buttonSize.dp)
                .padding(buttonPadding.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.language_russian_flag),
                contentDescription = null,
            )
        }
    }
}


@Composable
fun MainMenuButtons(
    onPlayClicked: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(top = 20.dp)
            .fillMaxSize()
    ) {
        MainButtonsFirstLayer(onPlayClicked)

        MainButtonsSecondLayer(32,20)

        MainButtonsLastLayer()
    }
}

