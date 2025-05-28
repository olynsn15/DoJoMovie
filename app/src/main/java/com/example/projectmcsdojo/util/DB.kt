package com.example.projectmcsdojo.util

import android.content.Context
import com.example.projectmcsdojo.models.History
import com.example.projectmcsdojo.models.Movie
import com.example.projectmcsdojo.models.User
import java.text.SimpleDateFormat
import java.util.Date

class DB {
    //VARIABLE STATIC GLOBAL
    companion object {
        //jumlahnya sesuai jumlah table yang kita punya
        var userList = mutableListOf<User>()

        var HAS_SYNC_USER = false

        fun syncData(ctx: Context) {
            if(HAS_SYNC_USER) return

            //QUERY TABLE
            var helper = Helper(ctx)
            var db = helper.readableDatabase
            var cursor = db.rawQuery("SELECT * FROM user", null)
            userList.clear()

            while (cursor.moveToNext()) {
                var id = cursor.getInt(0)
                var phonenumber = cursor.getString(1)
                var password = cursor.getString(2)

                var temp = User(id, phonenumber, password)
                userList.add(temp)
            }

            cursor.close()

            HAS_SYNC_USER = true
        }

        //Insert data - User
        fun insertNewUser(ctx: Context, phonenumber: String, password: String) {
            var id = 1
            if(userList.size > 0) {
                userList.last().user_id + 1
            }

            //generate ID
            var temp = User(id, phonenumber, password)
            userList.add(temp)

            //insert in SQLite
            var helper = Helper(ctx)
            var db = helper.writableDatabase
            db.execSQL(
                "INSERT INTO user(phonenumber, password) " +
                        "VALUES" +
                        "('"+phonenumber+"', '"+password+"')"
            )
        }

        var LOGGED_IN_USER: User? = null
        fun login(phonenumber: String, password: String) {
            for (user in userList) {
                if(user.phonenumber == phonenumber && user.password == password) {
                    LOGGED_IN_USER = user
                }
            }
        }

        var movieList = mutableListOf<Movie>()

        var HAS_SYNC_MOVIES = false

        fun syncMovies(ctx: Context) {
            if(HAS_SYNC_MOVIES) return

            val helper = Helper(ctx)
            val db = helper.readableDatabase
            val cursor = db.rawQuery("SELECT * FROM movie", null)
            movieList.clear()
            while (cursor.moveToNext()) {
                val id = cursor.getString(0)
                val title = cursor.getString(1)
                val image = cursor.getString(2)
                val price = cursor.getInt(3)
                movieList.add(Movie(id, title, image, price))
            }
            cursor.close()
        }

        fun insertMovie(ctx: Context, id: String, title: String, image: String, price: Int) {
            val actualImage = getImageURL(id)
            val helper = Helper(ctx)
            val db = helper.writableDatabase
            db.execSQL("INSERT OR REPLACE INTO movie(id, title, image, price) VALUES('$id', '$title', '$actualImage', $price)")
        }

        fun getImageURL(movieID: String): String {
            return when (movieID) {
                "MV001" -> "https://play-lh.googleusercontent.com/nQctOI_jJ9MoTPQmv4SaB6OUg8xwgEpYQT6Gfp2Qk_6wkTPudkXLRCGOh92zB-VbM6_NDdJJg0Qxbf0_DA=w240-h480-rw"
                "MV002" -> "https://cdn11.bigcommerce.com/s-6rs11v9w2d/images/stencil/270x360/products/3054/16401/FFVII_RB_AG_US__55522.1709208257.jpg?c=1"
                "MV003" -> "https://images-cdn.ubuy.co.id/6481a5a5b4dd7038984386a6-no-time-to-die-movie-poster-james.jpg"
                else -> "https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.istockphoto.com%2Fillustrations%2Fempty-image&psig=AOvVaw19izLE5k8SlCarcktYxTWx&ust=1747497615785000&source=images&cd=vfe&opi=89978449&ved=0CBQQjRxqFwoTCIjH8IauqI0DFQAAAAAdAAAAABAE"
            }
        }

        fun getMovieById(ctx: Context, movieId: String): Movie? {
            syncMovies(ctx)  // Make sure movieList is up to date
            return movieList.find { it.id == movieId }
        }

        fun insertTransaction(ctx: Context, userId: Int, filmId: String, quantity: Int) {
            val helper = Helper(ctx)
            val db = helper.writableDatabase
            db.execSQL("INSERT INTO transactions(user_id, film_id, quantity) VALUES($userId, '$filmId', $quantity)")
        }

        fun getTransactionHistoryForUser(ctx: Context, userId: Int): MutableList<History> {
            val helper = Helper(ctx) // or pass context param if needed
            val db = helper.readableDatabase
            val cursor = db.rawQuery("SELECT film_id, quantity FROM transactions WHERE user_id = ?", arrayOf(userId.toString()))

            val historyList = mutableListOf<History>()
            while (cursor.moveToNext()) {
                val filmId = cursor.getString(0)
                val quantity = cursor.getInt(1)
                historyList.add(History(filmId, quantity))
            }
            cursor.close()
            return historyList
        }

    }
}