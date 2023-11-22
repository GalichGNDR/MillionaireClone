package com.example.millionaireclone.util

import androidx.compose.runtime.Composable

@Composable
fun LoadingContent(
    isLoading: Boolean,
    loadingContent: @Composable () -> Unit,
    content: @Composable () -> Unit
) {
   if (isLoading)
       loadingContent()
    else
        content()
}