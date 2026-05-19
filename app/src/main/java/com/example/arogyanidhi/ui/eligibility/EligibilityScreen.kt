package com.example.arogyanidhi.ui.eligibility

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.arogyanidhi.data.model.User
import com.example.arogyanidhi.utils.EligibilityEngine

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EligibilityScreen(onBack: () -> Unit, onResult: (Int) -> Unit) {
    var name by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var income by remember { mutableStateOf("") }
    var isBpl by remember { mutableStateOf(false) }
    
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Eligibility Quiz") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(
                    Brush.verticalGradient(
                        listOf(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.2f), Color.White)
                    )
                )
                .verticalScroll(scrollState)
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text(
                "Find your health benefits",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            
            Text(
                "Answer a few questions to see which government schemes you can apply for.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            QuizField(label = "What is your name?", value = name, onValueChange = { name = it })
            QuizField(label = "How old are you?", value = age, onValueChange = { age = it }, isNumber = true)
            QuizField(label = "Your monthly income (₹)?", value = income, onValueChange = { income = it }, isNumber = true)

            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)),
                shape = RoundedCornerShape(16.dp)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp).fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text("BPL Card Holder?", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                        Text("Below Poverty Line status", style = MaterialTheme.typography.bodySmall)
                    }
                    Switch(checked = isBpl, onCheckedChange = { isBpl = it })
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    val user = User(
                        name = name,
                        age = age.toIntOrNull() ?: 0,
                        income = income.toDoubleOrNull() ?: 0.0,
                        bplStatus = isBpl,
                        state = "Karnataka"
                    )
                    val eligibleSchemes = EligibilityEngine.checkEligibility(user)
                    onResult(eligibleSchemes.size)
                },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(16.dp),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
            ) {
                Text("Check My Eligibility", style = MaterialTheme.typography.titleMedium)
            }
        }
    }
}

@Composable
fun QuizField(label: String, value: String, onValueChange: (String) -> Unit, isNumber: Boolean = false) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(label, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold)
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            singleLine = true,
            keyboardOptions = if (isNumber) KeyboardOptions(keyboardType = KeyboardType.Number) else KeyboardOptions.Default,
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            )
        )
    }
}
