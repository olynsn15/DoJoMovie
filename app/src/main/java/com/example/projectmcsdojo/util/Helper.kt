package com.example.projectmcsdojo.util

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class Helper(
    context: Context?,
) : SQLiteOpenHelper(context, "myDB", null, 4) {

    var SQL_CREATE_TABLE_USER = "CREATE TABLE IF NOT EXISTS user(" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "phonenumber TEXT," +
            "password TEXT)"

    var SQL_DROP_TABLE_USER = "DROP TABLE IF EXISTS user"

    var SQL_CREATE_TABLE_MOVIE = "CREATE TABLE IF NOT EXISTS movie(" +
            "id TEXT PRIMARY KEY," +
            "title TEXT," +
            "image TEXT," +
            "price INTEGER)"

    var SQL_DROP_TABLE_MOVIE = "DROP TABLE IF EXISTS movie"

    var SQL_CREATE_TABLE_TRANSACTION = "CREATE TABLE IF NOT EXISTS transactions(" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "user_id INTEGER," +
            "film_id TEXT," +
            "quantity INTEGER)"

    var SQL_DROP_TABLE_TRANSACTION = "DROP TABLE IF EXISTS transactions"


    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_TABLE_USER)
        db?.execSQL(SQL_CREATE_TABLE_MOVIE)
        db?.execSQL(SQL_CREATE_TABLE_TRANSACTION)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(SQL_DROP_TABLE_USER)
        db?.execSQL(SQL_DROP_TABLE_MOVIE)
        db?.execSQL(SQL_DROP_TABLE_TRANSACTION)
        onCreate(db)
    }
}