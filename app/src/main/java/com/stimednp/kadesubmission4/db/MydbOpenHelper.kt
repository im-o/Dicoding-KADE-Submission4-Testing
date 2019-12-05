package com.stimednp.kadesubmission4.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.stimednp.kadesubmission4.model.Favorites.Companion.ID
import com.stimednp.kadesubmission4.model.Favorites.Companion.ID_EVENT
import com.stimednp.kadesubmission4.model.Favorites.Companion.INT_SCOREA
import com.stimednp.kadesubmission4.model.Favorites.Companion.INT_SCOREH
import com.stimednp.kadesubmission4.model.Favorites.Companion.STR_BADGEA
import com.stimednp.kadesubmission4.model.Favorites.Companion.STR_BADGEH
import com.stimednp.kadesubmission4.model.Favorites.Companion.STR_DATEEV
import com.stimednp.kadesubmission4.model.Favorites.Companion.STR_EVENT
import com.stimednp.kadesubmission4.model.Favorites.Companion.STR_LEAGUE
import com.stimednp.kadesubmission4.model.Favorites.Companion.STR_SPORT
import com.stimednp.kadesubmission4.model.Favorites.Companion.STR_TEAMA
import com.stimednp.kadesubmission4.model.Favorites.Companion.STR_TEAMH
import com.stimednp.kadesubmission4.model.Favorites.Companion.STR_TIMEEV
import com.stimednp.kadesubmission4.model.Favorites.Companion.TABLE_FAVORITE
import org.jetbrains.anko.db.*

/**
 * Created by rivaldy on 11/28/2019.
 */

object MydbOpenHelper {
    class MydbOpenHelperLast(ctx: Context) : ManagedSQLiteOpenHelper(ctx, "favorite_lastevent.db", null, 1) {
        companion object {
            private var instance: MydbOpenHelperLast? = null
            @Synchronized
            fun getInstance(context: Context): MydbOpenHelperLast {
                if (instance == null) {
                    instance = MydbOpenHelperLast(context.applicationContext)
                }
                return instance as MydbOpenHelperLast
            }
        }

        override fun onCreate(db: SQLiteDatabase) { //create table
            createTable(db)
        }

        override fun onUpgrade(db: SQLiteDatabase, oldV: Int, newV: Int) { //upgrade table
            db.dropTable(TABLE_FAVORITE, true)
        }
    }

    //acces property context
    val Context.databaseLast: MydbOpenHelperLast
        get() = MydbOpenHelperLast.getInstance(applicationContext)

    class MydbOpenHelperNext(ctx: Context) : ManagedSQLiteOpenHelper(ctx, "favorite_nextevent.db", null, 1) {
        companion object {
            private var instance: MydbOpenHelperNext? = null
            @Synchronized
            fun getInstance(context: Context): MydbOpenHelperNext {
                if (instance == null) {
                    instance = MydbOpenHelperNext(context.applicationContext)
                }
                return instance as MydbOpenHelperNext
            }
        }

        override fun onCreate(db: SQLiteDatabase) {
            createTable(db)
        }

        override fun onUpgrade(db: SQLiteDatabase, oldV: Int, newV: Int) {
            db.dropTable(TABLE_FAVORITE, true)
        }
    }

    //acces property context
    val Context.databaseNext: MydbOpenHelperNext
        get() = MydbOpenHelperNext.getInstance(applicationContext)

    //create table
    private fun createTable(db: SQLiteDatabase) {
        db.createTable(
            TABLE_FAVORITE, true,
            ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            ID_EVENT to TEXT + UNIQUE,
            STR_DATEEV to TEXT,
            STR_TIMEEV to TEXT,
            STR_EVENT to TEXT,
            STR_SPORT to TEXT,
            STR_LEAGUE to TEXT,
            STR_TEAMH to TEXT,
            STR_TEAMA to TEXT,
            INT_SCOREH to INTEGER,
            INT_SCOREA to INTEGER,
            STR_BADGEH to TEXT,
            STR_BADGEA to TEXT
        )
    }
}
