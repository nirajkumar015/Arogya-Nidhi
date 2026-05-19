package com.example.arogyanidhi.ui.scheme

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.arogyanidhi.utils.EligibilityEngine

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SchemeListScreen(onBack: () -> Unit) {
    val schemes = EligibilityEngine.allSchemes

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Available Schemes") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(schemes) { scheme ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(scheme.name, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(scheme.benefits, style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.primary)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Criteria: ${scheme.eligibilityCriteria}", style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }
        }
    }
}
