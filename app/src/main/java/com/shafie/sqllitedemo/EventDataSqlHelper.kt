package com.shafie.sqllitedemo

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns

class EventDataSqlHelper(context: Context) : SQLiteOpenHelper(context,
    DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "event.db"
        const val DATABASE_VERSION = 1
        const val TABLE_NAME = "users"
        const val COL_ID = BaseColumns._ID
        const val COL_NAME = "name"
        const val COL_EMAIL = "email"
        const val COL_PHONE = "phone"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val query = """CREATE TABLE $TABLE_NAME (
            $COL_ID INTEGER PRIMARY KEY AUTOINCREMENT,
            $COL_NAME TEXT,
            $COL_EMAIL TEXT,
            $COL_PHONE TEXT
        )"""
        db?.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // Implement migration logic if needed
    }
}
