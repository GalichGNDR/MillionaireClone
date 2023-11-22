package com.example.millionaireclone.di

import android.content.Context
import androidx.room.Room
import com.example.millionaireclone.data.RepositoryDefault
import com.example.millionaireclone.data.Repository
import com.example.millionaireclone.data.source.MillionaireDao
import com.example.millionaireclone.data.source.MillionaireDatabase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object databaseModule {

    @Singleton
    @Provides
    fun getDatabase(@ApplicationContext context: Context): MillionaireDatabase = Room.databaseBuilder(
        context,
        MillionaireDatabase::class.java,
        "MillionaireDatabase"
    ).build()

    @Provides
    fun getDao(database: MillionaireDatabase): MillionaireDao = database.getDao()
}

@Module
@InstallIn(SingletonComponent::class)
abstract class repositoryModule {

    @Singleton
    @Binds
    abstract fun getRepositoryDefault(repositoryDefault: RepositoryDefault): Repository
}









