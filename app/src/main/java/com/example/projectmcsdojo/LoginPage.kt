package com.example.projectmcsdojo

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.projectmcsdojo.util.DB

class LoginPage : AppCompatActivity() {
    lateinit var phonenumber: EditText
    lateinit var password: EditText
    lateinit var btnlogin: Button
    lateinit var regist: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login_page)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(v.paddingLeft, systemBars.top, v.paddingRight, 0)
            insets
        }

        phonenumber = findViewById(R.id.et_phone)
        password = findViewById(R.id.et_pw)
        btnlogin = findViewById(R.id.btn_login)
        regist = findViewById(R.id.tv_clickreg)

        btnlogin.setOnClickListener {
            if (validateForm(phonenumber, password)){
                Log.i("Test Credentials", "Phone Number : $phonenumber and Password : $password")
                signIn()
            }

            else{
                Toast.makeText(this@LoginPage, "Failed to Login to your Account!", Toast.LENGTH_SHORT).show()
            }
        }

        regist.setOnClickListener {
            val i = Intent(this@LoginPage, RegisterPage::class.java)
            startActivity(i)
        }

        val textView = findViewById<TextView>(R.id.tv_login)
        val spannable = SpannableString("Login.")

        spannable.setSpan(
            ForegroundColorSpan(Color.YELLOW),
            spannable.length - 1,
            spannable.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        textView.text = spannable

    }

    private fun validateForm(phonenumber: EditText, password:EditText): Boolean {
        val phone = phonenumber.text.toString().trim()
        val pwd = password.text.toString().trim()

        if(phone.isEmpty()) {
            Toast.makeText(this, "Phone number is required", Toast.LENGTH_SHORT).show()
            return false
        }
        if(pwd.isEmpty()) {
            Toast.makeText(this, "Password is required", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }

    fun signIn() {
        val phonenumber = phonenumber.text.toString()
        val password = password.text.toString()

        DB.syncData(this)
        DB.login(phonenumber, password)
        if(DB.LOGGED_IN_USER == null) {
            Toast.makeText(this, "Login failed: User not found", Toast.LENGTH_SHORT).show()
            Log.e("Login", "Failed login: DB.LOGGED_IN_USER is null")
            return
        }

        Toast.makeText(this, "Login success: Welcome!", Toast.LENGTH_SHORT).show()
        val i = Intent(this@LoginPage, HomePage::class.java)
        startActivity(i)
        finish()
    }

}