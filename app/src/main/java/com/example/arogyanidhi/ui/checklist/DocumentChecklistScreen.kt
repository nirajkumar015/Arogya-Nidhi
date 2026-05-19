package com.example.arogyanidhi.ui.checklist

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DocumentChecklistScreen(onBack: () -> Unit) {
    val documents = remember {
        mutableStateListOf(
            "Aadhaar Card" to false,
            "BPL Card" to false,
            "Income Certificate" to false,
            "Voter ID" to false,
            "Age Proof (SSLC/Birth Cert)" to false,
            "Address Proof" to false,
            "Recent Photographs" to false
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Document Checklist") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).padding(16.dp)) {
            Text(
                "Keep these documents ready for scheme applications.",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(documents.size) { index ->
                    val (doc, checked) = documents[index]
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = checked,
                            onCheckedChange = { documents[index] = doc to it }
                        )
                        Text(
                            text = doc,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }
            }
        }
    }
}
