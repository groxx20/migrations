package com.article.migrations.di.module.feature

import com.article.migrations.ui.MainFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by Pavel on 03/09/2020.
 **/

@Module
abstract class MainFragmentBindingModule {

    @ContributesAndroidInjector
    abstract fun bindMainFragment(): MainFragment
}