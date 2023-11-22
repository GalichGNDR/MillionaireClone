package com.example.millionaireclone.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class AppDispatcherDefault

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class AppCoroutineScope

@Module
@InstallIn(SingletonComponent::class)
object coroutineModules {

    @Provides
    @AppDispatcherDefault
    fun getDefaultDispatcher() = Dispatchers.Default

    @Provides
    @AppCoroutineScope
    fun getAppCoroutineScope(@AppDispatcherDefault dispatcher: CoroutineDispatcher): CoroutineScope
            = CoroutineScope(dispatcher + SupervisorJob())
}
