package com.example.projectmcsdojo.fragments

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.projectmcsdojo.LoginPage
import com.example.projectmcsdojo.R
import com.example.projectmcsdojo.util.DB

class ProfileFragment : Fragment() {

    private lateinit var title: TextView
    private lateinit var phoneNumber: TextView
    private lateinit var logout: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        title = view.findViewById(R.id.profile_title)

        val spannable = SpannableString("Profile.")
        spannable.setSpan(
            ForegroundColorSpan(Color.YELLOW),
            spannable.length - 1,
            spannable.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        title.text = spannable

        val user = DB.LOGGED_IN_USER
        if (user == null) {
            // Handle not logged in user if needed
            return view
        }

        phoneNumber = view.findViewById(R.id.tv_phone_number)

        phoneNumber.text = user.phonenumber.toString()

        logout = view.findViewById(R.id.btnLogOut)

        logout.setOnClickListener {
            showLogoutConfirmation()
        }

        return view
    }

    private fun showLogoutConfirmation() {
        AlertDialog.Builder(requireContext())
            .setTitle("Log Out")
            .setMessage("Are you sure you want to log out?")
            .setPositiveButton("Yes") { _, _ -> logOut() }
            .setNegativeButton("Cancel", null)
            .show()
    }

    fun logOut() {
        // Clear the logged-in user
        DB.LOGGED_IN_USER = null

        // Navigate back to the LoginActivity
        val context = activity ?: return
        val i = Intent(context, LoginPage::class.java)
        i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(i)
    }
}
