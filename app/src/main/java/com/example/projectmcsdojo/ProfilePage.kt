package com.example.projectmcsdojo

import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.projectmcsdojo.util.DB

class ProfilePage : AppCompatActivity() {

    lateinit var title:TextView
    lateinit var phoneNumber:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_profile_page)

        val textView = findViewById<TextView>(R.id.profile_title)
        val spannable = SpannableString("Profile.")

        spannable.setSpan(
            ForegroundColorSpan(Color.YELLOW),
            spannable.length - 1,
            spannable.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        textView.text = spannable

        val user = DB.LOGGED_IN_USER
        if (user == null) {
            // Handle not logged in user if needed
            return
        }

        phoneNumber = findViewById(R.id.tv_phone_number)

    }
}