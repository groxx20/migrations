package com.article.migrations.di.module.main

import android.content.Context
import com.article.migrations.room.AppDataBase
import com.article.migrations.room.dao.UserAccountDao
import com.article.migrations.room.dao.UserDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Pavel on 25/10/2020.
 **/

@Module
class DbModule {
    @Provides
    @Singleton
    fun provideDatabase(context: Context): AppDataBase {
        return AppDataBase.getDbInstance(context)
    }


    @Provides
    @Singleton
    fun provideUserAccountDao(appDatabase: AppDataBase): UserAccountDao {
        return appDatabase.userAccountDao()
    }

    @Provides
    @Singleton
    fun provideUserDao(appDatabase: AppDataBase): UserDao {
        return appDatabase.userDao()
    }
}