package com.example.projectmcsdojo.models

data class User(
    var user_id: Int, // Sequence (1..2..3..N)
    var phone_number: String,
    var password: String,
)
