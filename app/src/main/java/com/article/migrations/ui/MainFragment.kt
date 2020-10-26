package com.article.migrations.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.MainThread
import com.article.migrations.R
import com.article.migrations.room.AppDataBase
import com.article.migrations.room.DbHelper
import com.article.migrations.room.dao.UserAccountDao
import com.article.migrations.room.dao.UserDao
import com.article.migrations.room.model.User
import com.article.migrations.room.model.UserAccount
import dagger.android.support.DaggerFragment
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by Pavel on 03/09/2020.
 **/

class MainFragment : DaggerFragment() {

    companion object {
        private const val USER_ID = 10000L
        private const val USER_STATE = 5
        private const val USER_ROLE = "Admin"
        private const val USER_NICKNAME = "Random"
        private const val USER_NAME = "John"
    }

    @Inject
    lateinit var userAccountDao: UserAccountDao

    @Inject
    lateinit var userDao: UserDao

    @Inject
    lateinit var appDataBase: AppDataBase

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.fragment_main, container, false)

    @SuppressLint("CheckResult")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Completable.fromAction {
            userDao.insert(createUser())
            userAccountDao.insert(createUserAccount())
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    }

    private fun createUser() = User(USER_ID, USER_NAME)
    private fun createUserAccount() =  UserAccount(USER_ID, USER_STATE, USER_ROLE, createUser().id)
}