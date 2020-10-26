package com.article.migrations.room.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Pavel on 26/10/2020.
 **/

@Entity(tableName = "user")
data class User(
    @PrimaryKey
    val id: Long,
    var name: String
)