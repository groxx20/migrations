package com.article.migrations.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.article.migrations.room.model.UserAccount

/**
 * Created by Pavel on 25/10/2020.
 **/
@Dao
abstract class UserAccountDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(userAccount: UserAccount)
}