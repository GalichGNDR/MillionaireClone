package com.example.millionaireclone

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.millionaireclone.AppDestinations.GAME_ROUTE
import com.example.millionaireclone.AppDestinations.MENU_ROUTE
import com.example.millionaireclone.AppDestinations.SPLASH_ROUTE
import com.example.millionaireclone.gamescreen.MillionaireGameScreen
import com.example.millionaireclone.menuscreen.MillionaireMenuScreen
import com.example.millionaireclone.splashscreen.MillionaireSplashScreen

@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController(),
    navActions: AppNavActions = remember(navController) {
        AppNavActions(navController)
    }
) {
    val activity = (LocalContext.current as? Activity)

    NavHost(
        navController = navController,
        startDestination = SPLASH_ROUTE
    ) {
        composable(
            SPLASH_ROUTE
        ) {
            MillionaireSplashScreen(
                onLoaded = {
                    navActions.navigateToMenuScreen()
                },
                timeShowing = 1800L
            )
        }
        composable(
            MENU_ROUTE,
            enterTransition = {
                fadeIn(
                    tween(durationMillis = 1800)
                )
            },
            exitTransition = {
                fadeOut(
                    tween(durationMillis = 0)
                )
            }
        ) {
            BackHandler(true) {
                activity?.finish()
            }
            MillionaireMenuScreen(
                onPlayClicked = {
                    navActions.navigateToGameScreen()
                }
            )
        }
        composable(GAME_ROUTE,
        ) {
            MillionaireGameScreen()
        }
    }
}