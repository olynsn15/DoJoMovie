package com.example.projectmcsdojo.util

import android.content.Context
import com.example.projectmcsdojo.models.Film
import com.example.projectmcsdojo.models.History
import com.example.projectmcsdojo.models.User

class DB {
    companion object {
        var userList = mutableListOf<User>()

        var HAS_SYNC_USER = false

        fun syncData(ctx: Context) {
            if(HAS_SYNC_USER) return

            var helper = Helper(ctx)
            var db = helper.readableDatabase
            var cursor = db.rawQuery("SELECT * FROM user", null)
            userList.clear()

            while (cursor.moveToNext()) {
                var user_id = cursor.getInt(0)
                var phone_number = cursor.getString(1)
                var password = cursor.getString(2)

                var temp = User(user_id, phone_number, password)
                userList.add(temp)
            }

            cursor.close()

            HAS_SYNC_USER = true
        }

        fun isPhoneRegistered(ctx: Context, phone: String): Boolean {
            val helper = Helper(ctx)
            val db = helper.readableDatabase
            val cursor = db.rawQuery("SELECT * FROM user WHERE phone_number = ?", arrayOf(phone))
            val exists = cursor.moveToFirst()
            cursor.close()
            return exists
        }

        fun insertNewUser(ctx: Context, phone_number: String, password: String) {
            var id = 1
            if (userList.isNotEmpty()) {
                id = userList.last().user_id + 1
            }

            val temp = User(id, phone_number, password)
            userList.add(temp)

            val helper = Helper(ctx)
            val db = helper.writableDatabase
            db.execSQL(
                "INSERT INTO user(phone_number, password) VALUES('$phone_number', '$password')"
            )
        }


        var LOGGED_IN_USER: User? = null
        fun login(phone_number: String, password: String) {
            for (user in userList) {
                if(user.phone_number == phone_number && user.password == password) {
                    LOGGED_IN_USER = user
                }
            }
        }

        var filmList = mutableListOf<Film>()

        var HAS_SYNC_FILMS = false

        fun syncFilms(ctx: Context) {
            if(HAS_SYNC_FILMS) return

            val helper = Helper(ctx)
            val db = helper.readableDatabase
            val cursor = db.rawQuery("SELECT * FROM film", null)
            filmList.clear()
            while (cursor.moveToNext()) {
                val film_id = cursor.getString(0)
                val film_title = cursor.getString(1)
                val film_image = cursor.getString(2)
                val film_price = cursor.getInt(3)
                filmList.add(Film(film_id, film_title, film_image, film_price))
            }
            cursor.close()
        }

        fun insertFilms(ctx: Context, film_id: String, film_title: String, film_image: String, film_price: Int) {
            val actual_film_image = getImageURL(film_id)
            val helper = Helper(ctx)
            val db = helper.writableDatabase
            db.execSQL("INSERT OR REPLACE INTO film(film_id, film_title, film_image, film_price) VALUES('$film_id', '$film_title', '$actual_film_image', $film_price)")
        }

        fun getImageURL(film_id: String): String {
            return when (film_id) {
                "MV001" -> "https://play-lh.googleusercontent.com/nQctOI_jJ9MoTPQmv4SaB6OUg8xwgEpYQT6Gfp2Qk_6wkTPudkXLRCGOh92zB-VbM6_NDdJJg0Qxbf0_DA=w240-h480-rw"
                "MV002" -> "https://cdn11.bigcommerce.com/s-6rs11v9w2d/images/stencil/270x360/products/3054/16401/FFVII_RB_AG_US__55522.1709208257.jpg?c=1"
                "MV003" -> "https://images-cdn.ubuy.co.id/6481a5a5b4dd7038984386a6-no-time-to-die-movie-poster-james.jpg"
                else -> "https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.istockphoto.com%2Fillustrations%2Fempty-image&psig=AOvVaw19izLE5k8SlCarcktYxTWx&ust=1747497615785000&source=images&cd=vfe&opi=89978449&ved=0CBQQjRxqFwoTCIjH8IauqI0DFQAAAAAdAAAAABAE"
            }
        }

        fun getFilmById(ctx: Context, film_id: String): Film? {
            syncFilms(ctx)
            return filmList.find { it.film_id == film_id }
        }

        fun insertTransaction(ctx: Context, user_id: Int, film_id: String, quantity: Int) {
            val helper = Helper(ctx)
            val db = helper.writableDatabase
            db.execSQL("INSERT INTO transactions(user_id, film_id, quantity) VALUES($user_id, '$film_id', $quantity)")
        }

        fun getTransactionHistoryForUser(ctx: Context, user_id: Int): MutableList<History> {
            val helper = Helper(ctx)
            val db = helper.readableDatabase
            val cursor = db.rawQuery("SELECT film_id, quantity FROM transactions WHERE user_id = ?", arrayOf(user_id.toString()))

            val historyList = mutableListOf<History>()
            while (cursor.moveToNext()) {
                val film_id = cursor.getString(0)
                val quantity = cursor.getInt(1)
                historyList.add(History(film_id, quantity))
            }
            cursor.close()
            return historyList
        }

    }
}