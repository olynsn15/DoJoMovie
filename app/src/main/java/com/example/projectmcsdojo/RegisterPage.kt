package com.example.projectmcsdojo

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.projectmcsdojo.util.DB

class RegisterPage : AppCompatActivity() {
    lateinit var phonenumber_text: EditText
    lateinit var password: EditText
    lateinit var passwordconfirm: EditText
    lateinit var btnRegist: Button
    lateinit var register: TextView
    lateinit var login: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register_page)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(v.paddingLeft, systemBars.top, v.paddingRight, 0)
            insets
        }

        phonenumber_text = findViewById(R.id.et_phone)
        password = findViewById(R.id.et_pwd)
        passwordconfirm = findViewById(R.id.et_pwdconfirm)
        btnRegist = findViewById(R.id.btn_regist)
        register = findViewById(R.id.tv_register)
        login = findViewById(R.id.tv_clicklog)

        btnRegist.setOnClickListener {
            if(validateForm(phonenumber_text, password, passwordconfirm)){
                signUp()
            }
        }

        login.setOnClickListener {
            val i = Intent(this@RegisterPage, LoginPage::class.java)
            startActivity(i)
        }

        val spannable = SpannableString("Register.")

        spannable.setSpan(
            ForegroundColorSpan(Color.YELLOW),
            spannable.length - 1,
            spannable.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        register.text = spannable
    }

    private fun validateForm(phonenumber: EditText, password:EditText, passwordconfirm:EditText): Boolean {
        val phone = phonenumber.text.toString().trim()
        val pwd = password.text.toString().trim()
        val pwdconfirm = passwordconfirm.text.toString().trim()

        if(phone.isEmpty()) {
            Toast.makeText(this, "Phone number is required", Toast.LENGTH_SHORT).show()
            return false
        }
        if (DB.isPhoneRegistered(this@RegisterPage, phone)) {
            Toast.makeText(this, "Phone number already registered", Toast.LENGTH_SHORT).show()
            return false
        }
        if(pwd.isEmpty()) {
            Toast.makeText(this, "Password is required", Toast.LENGTH_SHORT).show()
            return false
        }
        if(pwd.length < 8) {
            Toast.makeText(this, "Password needs to be at least 8 characters", Toast.LENGTH_SHORT).show()
            return false
        }
        if(pwdconfirm.isEmpty()) {
            Toast.makeText(this, "Password confirmation is required", Toast.LENGTH_SHORT).show()
            return false
        }
        if(pwdconfirm != pwd){
            Toast.makeText(this, "Confirmation password does not match", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }

    fun signUp() {
        val phonenumber = phonenumber_text.text.toString()
        val password = password.text.toString()

        DB.insertNewUser(this@RegisterPage, phonenumber, password)

        val i = Intent(this@RegisterPage, VerificationPage::class.java)
        i.putExtra("phone_number", phonenumber)
        startActivity(i)
        finish()
    }
}