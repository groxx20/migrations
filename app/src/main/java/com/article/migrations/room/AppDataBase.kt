package com.article.migrations.room

import android.content.Context
import androidx.annotation.VisibleForTesting
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.article.migrations.room.dao.UserAccountDao
import com.article.migrations.room.dao.UserDao
import com.article.migrations.room.model.User
import com.article.migrations.room.model.UserAccount

/**
 * Created by Pavel on 04/09/2020.
 **/

object DbHelper {
    const val DB_VERSION = 6
}

@Database(
    entities = [UserAccount::class, User::class],
    version = DbHelper.DB_VERSION,
    exportSchema = true
)

abstract class AppDataBase: RoomDatabase() {
    companion object {
        private const val DB_NAME = "migrations_db"
        private lateinit var instance: AppDataBase
        fun getDbInstance(context: Context): AppDataBase {
            return if (!this::instance.isInitialized) {
                synchronized(AppDataBase::class) {
                    Room.databaseBuilder(
                        context.applicationContext,
                        AppDataBase::class.java,
                        DB_NAME
                    )
                        .fallbackToDestructiveMigration()
                        .addMigrations(
                            *migrations
                        )
                        .build()
                }
            } else {
                instance
            }
        }

        @VisibleForTesting
        val migrations = arrayOf(
            object : Migration(1, 2) {
                override fun migrate(database: SupportSQLiteDatabase) {
                    database.execSQL("ALTER TABLE user_account ADD COLUMN nickname TEXT DEFAULT 0 NOT NULL")
                }
            },
            object : Migration(2, 3) {
                override fun migrate(database: SupportSQLiteDatabase) {
                    database.execSQL("CREATE TABLE IF NOT EXISTS `user_account_temporary` (`id` INTEGER NOT NULL, `state` INTEGER NOT NULL, `role` TEXT NOT NULL, `username` TEXT NOT NULL, PRIMARY KEY(`id`))")
                    database.execSQL("INSERT INTO user_account_temporary(id, state, role, username) SELECT id, state, role, nickname FROM user_account")
                    database.execSQL("DROP TABLE user_account")
                    database.execSQL("ALTER TABLE user_account_temporary RENAME TO user_account")
                }
            },
            object : Migration(3, 4) {
                override fun migrate(database: SupportSQLiteDatabase) {
                    database.execSQL("CREATE TABLE IF NOT EXISTS `user_account_temporary` (`id` INTEGER NOT NULL, `state` INTEGER NOT NULL, `role` TEXT NOT NULL, `username` TEXT, PRIMARY KEY(`id`))")
                    database.execSQL("INSERT INTO user_account_temporary(id, state, role, username) SELECT id, state, role, username FROM user_account")
                    database.execSQL("DROP TABLE user_account")
                    database.execSQL("ALTER TABLE user_account_temporary RENAME TO user_account")
                }
            },
            object : Migration(4, 5) {
                override fun migrate(database: SupportSQLiteDatabase) {
                    database.execSQL("CREATE TABLE IF NOT EXISTS `user_account_temporary` (`id` INTEGER NOT NULL, `state` INTEGER NOT NULL, `role` TEXT NOT NULL, PRIMARY KEY(`id`))")
                    database.execSQL("INSERT INTO user_account_temporary(id, state, role) SELECT id, state, role FROM user_account")
                    database.execSQL("DROP TABLE user_account")
                    database.execSQL("ALTER TABLE user_account_temporary RENAME TO user_account")
                }
            },
            object : Migration(5, 6) {
                override fun migrate(database: SupportSQLiteDatabase) {
                    database.execSQL("ALTER TABLE user_account ADD COLUMN user_id INTEGER DEFAULT 0 NOT NULL")
                    database.execSQL("CREATE TABLE IF NOT EXISTS `user` (`id` INTEGER NOT NULL, `name` TEXT NOT NULL, PRIMARY KEY(`id`))")
                    database.execSQL("DROP INDEX IF EXISTS index_user_account_temporary_user_id")
                    database.execSQL("CREATE TABLE IF NOT EXISTS `user_account_temporary` (`id` INTEGER NOT NULL, `state` INTEGER NOT NULL, `role` TEXT NOT NULL,`user_id` INTEGER, PRIMARY KEY(`id`), FOREIGN KEY(`user_id`) REFERENCES `user`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )")
                    database.execSQL("CREATE INDEX IF NOT EXISTS index_user_account_temporary_user_id ON user_account_temporary(user_id)")
                    database.execSQL("INSERT INTO user_account_temporary(id, state, role, user_id) SELECT id, state, role, user_id FROM user_account")
                    database.execSQL("DROP TABLE user_account")
                    database.execSQL("ALTER TABLE user_account_temporary RENAME TO user_account")
                }
            }
        )
    }

    abstract fun userAccountDao(): UserAccountDao
    abstract fun userDao(): UserDao
}