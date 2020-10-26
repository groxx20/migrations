package com.article.migrations.room.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

/**
 * Created by Pavel on 07/09/2020.
 **/

@Entity(
    tableName = "user_account",
    foreignKeys = [ForeignKey(
        entity = User::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("user_id"),
        onDelete = CASCADE
    )]
)
data class UserAccount(
    @PrimaryKey
    val id: Long,
    var state: Int,
    var role: String,
    @ColumnInfo(name = "user_id", index = true)
    var userId: Long
)