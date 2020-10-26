package com.article.migrations.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.article.migrations.room.model.User

/**
 * Created by Pavel on 26/10/2020.
 **/
@Dao
abstract class UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(user: User)
}