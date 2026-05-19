package com.example.arogyanidhi.utils

import com.example.arogyanidhi.data.model.Scheme
import com.example.arogyanidhi.data.model.User

object EligibilityEngine {
    val allSchemes = listOf(
        Scheme(
            id = 1,
            name = "Ayushman Bharat",
            benefits = "Free treatment up to ₹5 Lakhs",
            eligibilityCriteria = "Income < 2L and BPL status",
            documents = listOf("Aadhaar Card", "BPL Card")
        ),
        Scheme(
            id = 2,
            name = "Senior Citizen Health Scheme",
            benefits = "Special elderly healthcare benefits",
            eligibilityCriteria = "Age >= 60",
            documents = listOf("Aadhaar Card", "Age Proof")
        ),
        Scheme(
            id = 3,
            name = "Karnataka Health Scheme",
            benefits = "Free rural healthcare services",
            eligibilityCriteria = "Resident of Karnataka",
            documents = listOf("Income Certificate", "Voter ID")
        )
    )

    fun checkEligibility(user: User): List<Scheme> {
        val schemes = mutableListOf<Scheme>()

        if (user.bplStatus && user.income < 200000) {
            schemes.add(allSchemes[0])
        }

        if (user.isSeniorCitizen) {
            schemes.add(allSchemes[1])
        }
        
        // Everyone in Karnataka gets this in our demo logic
        if (user.state.equals("Karnataka", ignoreCase = true)) {
            schemes.add(allSchemes[2])
        }

        return schemes
    }
}
