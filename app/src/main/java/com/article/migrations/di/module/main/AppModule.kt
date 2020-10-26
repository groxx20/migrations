package com.article.migrations.di.module.main

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Pavel on 03/09/2020.
 **/

@Module
class AppModule {

    @Provides
    @Singleton
    fun provideContext(application: Application): Context = application
}