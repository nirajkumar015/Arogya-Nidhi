package com.example.arogyanidhi.data.model

data class Scheme(
    val id: Int = 0,
    val name: String,
    val benefits: String,
    val eligibilityCriteria: String,
    val documents: List<String>
)
