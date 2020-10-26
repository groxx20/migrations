package com.article.migrations.di.component

import android.app.Application
import com.article.migrations.MigrationsApp
import com.article.migrations.di.module.main.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

/**
 * Created by Pavel on 03/09/2020.
 **/

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        ActivityBindingModule::class,
        DbModule::class,
        AppModule::class
    ]
)
interface AppComponent : AndroidInjector<MigrationsApp> {

    override fun inject(application: MigrationsApp)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder
        fun build(): AppComponent
    }
}