package com.example.millionaireclone

import androidx.navigation.NavHostController
import com.example.millionaireclone.AppScreens.GAME_SCREEN
import com.example.millionaireclone.AppScreens.MENU_SCREEN
import com.example.millionaireclone.AppScreens.SPLASH_SCREEN

object AppScreens {
    const val SPLASH_SCREEN = "splashScreen"
    const val MENU_SCREEN = "menuScreen"
    const val GAME_SCREEN = "gameScreen"
}

object AppDestinations {
    const val SPLASH_ROUTE = "$SPLASH_SCREEN"
    const val MENU_ROUTE = "$MENU_SCREEN"
    const val GAME_ROUTE = "$GAME_SCREEN"
}

class AppNavActions(private val navController: NavHostController) {
    fun navigateToMenuScreen() {
        navController.navigate("$MENU_SCREEN")
    }
    fun navigateToGameScreen() {
        navController.navigate("$GAME_SCREEN")
    }
}