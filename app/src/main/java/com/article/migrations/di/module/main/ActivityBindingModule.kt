package com.article.migrations.di.module.main

import com.article.migrations.di.module.feature.MainActivityBindingModule
import com.article.migrations.di.module.feature.MainFragmentBindingModule
import com.article.migrations.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by Pavel on 03/09/2020.
 **/

@Module
abstract class ActivityBindingModule {

    @ContributesAndroidInjector(modules = [
        MainActivityBindingModule::class,
        MainFragmentBindingModule::class
    ])
    abstract fun bindMainActivity(): MainActivity
}