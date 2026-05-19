package com.example.arogyanidhi.data.model

data class Hospital(
    val name: String,
    val address: String,
    val city: String,
    val contact: String,
    val type: String // e.g., "General", "Specialty", "Emergency"
)
