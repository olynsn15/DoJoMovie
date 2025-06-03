package com.example.projectmcsdojo

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.telephony.SmsManager
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.projectmcsdojo.databinding.ActivityVerificationPageBinding

class VerificationPage : AppCompatActivity() {
    lateinit var binding: ActivityVerificationPageBinding
    lateinit var verification: TextView
    lateinit var caption : TextView
    lateinit var code : EditText
    lateinit var btnContinue : Button
    lateinit var resend : TextView

    lateinit var smsManager: SmsManager
    var generatedOTP: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityVerificationPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val phoneNumber = intent.getStringExtra("phone_number")

        btnContinue = binding.btnContinue
        caption = binding.tvCaption
        verification = binding.tvOtp
        code = binding.etCode
        resend = binding.tvClickresend

        val textView = binding.tvCode
        val spannable = SpannableString("OTP Verification.")

        spannable.setSpan(
            ForegroundColorSpan(Color.YELLOW),
            spannable.length - 1,
            spannable.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        textView.text = spannable

        smsManager = SmsManager.getDefault()

        if (phoneNumber != null) {
            generatedOTP = generateOTP()
            checkSendSMSPermission(phoneNumber, "Your OTP code is : $generatedOTP")
        }

        btnContinue.setOnClickListener {
            val enteredCode = code.text.toString()

            if(enteredCode.isEmpty()) {
                Toast.makeText(this, "Please input the OTP code", Toast.LENGTH_SHORT).show()
            }
            else if (enteredCode == generatedOTP){
                Toast.makeText(this, "OTP verified!", Toast.LENGTH_SHORT).show()
                val i = Intent(this@VerificationPage, LoginPage::class.java)
                startActivity(i)
                finish()
            }
            else{
                Toast.makeText(this, "Invalid OTP, try again", Toast.LENGTH_SHORT).show()
            }
        }

        resend.setOnClickListener {
            if (phoneNumber != null) {
                generatedOTP = generateOTP()
                checkSendSMSPermission(phoneNumber!!, "Your new OTP code is : $generatedOTP")
                Toast.makeText(this, "OTP resent!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun generateOTP(): String {
        return (100000..999999).random().toString()
    }

    fun checkSendSMSPermission(phoneNumber: String, message: String) {
        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.SEND_SMS), 100)
        }
        else{
            sendSMS(phoneNumber, message)
        }
    }

    fun sendSMS(phoneNumber: String, message: String) {
        smsManager.sendTextMessage(phoneNumber, null, message, null, null)
    }
}