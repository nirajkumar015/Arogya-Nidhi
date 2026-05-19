package com.example.arogyanidhi.ui.saved

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.arogyanidhi.data.local.entities.SavedResult
import com.example.arogyanidhi.viewmodel.SchemeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SavedResultsScreen(viewModel: SchemeViewModel, onBack: () -> Unit) {
    val results by viewModel.allResults.observeAsState(initial = emptyList())

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Saved Results") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back")
                    }
                }
            )
        }
    ) { padding ->
        if (results.isEmpty()) {
            Box(Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Default.Info, null, modifier = Modifier.size(64.dp), tint = Color.LightGray)
                    Text("No saved results yet", color = Color.Gray)
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(padding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(results) { result ->
                    SavedResultCard(result)
                }
            }
        }
    }
}

@Composable
fun SavedResultCard(result: SavedResult) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(result.schemeName, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
                Text("User: ${result.userName}", style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}
