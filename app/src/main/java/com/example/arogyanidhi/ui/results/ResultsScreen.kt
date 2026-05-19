package com.example.arogyanidhi.ui.results

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.arogyanidhi.data.local.entities.SavedResult
import com.example.arogyanidhi.data.model.Scheme
import com.example.arogyanidhi.viewmodel.SchemeViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResultsScreen(schemes: List<Scheme>, viewModel: SchemeViewModel, onBack: () -> Unit) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text("Your Recommendations") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back")
                    }
                }
            )
        }
    ) { padding ->
        if (schemes.isEmpty()) {
            EmptyResultsView(Modifier.padding(padding))
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .background(Brush.verticalGradient(listOf(Color(0xFFF1F8E9), Color.White))),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    Text(
                        "Great news! You are eligible for ${schemes.size} schemes.",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }
                itemsIndexed(schemes) { index, scheme ->
                    SchemeCard(scheme, index) {
                        viewModel.saveResult(SavedResult(userName = "User", schemeName = scheme.name))
                        scope.launch {
                            snackbarHostState.showSnackbar("Saved to history!")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SchemeCard(scheme: Scheme, index: Int, onSave: () -> Unit) {
    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { visible = true }

    AnimatedVisibility(
        visible = visible,
        enter = slideInHorizontally(animationSpec = tween(500, index * 100)) { -it } + fadeIn()
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                        Icon(Icons.Default.CheckCircle, null, tint = Color(0xFF4CAF50))
                        Spacer(Modifier.width(8.dp))
                        Text(scheme.name, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                    }
                    IconButton(onClick = onSave) {
                        Icon(Icons.Default.Favorite, "Save", tint = MaterialTheme.colorScheme.primary)
                    }
                }
                Spacer(Modifier.height(12.dp))
                Text(scheme.benefits, style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.primary)
                Spacer(Modifier.height(8.dp))
                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp), color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f))
                Spacer(Modifier.height(8.dp))
                Text("Documents required:", style = MaterialTheme.typography.labelLarge)
                Text(scheme.documents.joinToString(", "), style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}

@Composable
fun EmptyResultsView(modifier: Modifier) {
    Box(modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(Icons.Default.Info, null, modifier = Modifier.size(64.dp), tint = Color.Gray)
            Spacer(Modifier.height(16.dp))
            Text("No matching schemes found based on your current profile.")
        }
    }
}
