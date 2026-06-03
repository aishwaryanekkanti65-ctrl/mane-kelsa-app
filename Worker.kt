package com.example.manekelsa

data class Worker(
    var name: String,
    var skill: String,
    var wage: String,
    var phone: String,
    var rating: Float = 0f,
    var available: Boolean = true
)