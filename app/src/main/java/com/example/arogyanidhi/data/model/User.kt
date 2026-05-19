package com.example.arogyanidhi.data.model

data class User(
    val name: String,
    val age: Int,
    val income: Double,
    val bplStatus: Boolean,
    val state: String,
    val isSeniorCitizen: Boolean = age >= 60
)
