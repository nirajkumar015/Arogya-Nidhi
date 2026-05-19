package com.example.arogyanidhi.ui.home

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class DashboardItem(
    val title: String,
    val icon: ImageVector,
    val color: Color,
    val description: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToEligibility: () -> Unit,
    onNavigateToHospitals: () -> Unit,
    onNavigateToEmergency: () -> Unit,
    onNavigateToSchemes: () -> Unit,
    onNavigateToSaved: () -> Unit,
    onNavigateToChecklist: () -> Unit
) {
    val items = listOf(
        DashboardItem("Check Eligibility", Icons.Default.CheckCircle, Color(0xFF4CAF50), "See which schemes fit you"),
        DashboardItem("View Schemes", Icons.AutoMirrored.Filled.List, Color(0xFF2196F3), "Browse govt healthcare plans"),
        DashboardItem("Find Hospitals", Icons.Default.Place, Color(0xFFE91E63), "Locate nearby facilities"),
        DashboardItem("Document Checklist", Icons.Default.Done, Color(0xFF9C27B0), "Ready your documents"),
        DashboardItem("Emergency Help", Icons.Default.Warning, Color(0xFFF44336), "Quick access to SOS"),
        DashboardItem("Saved Results", Icons.Default.Favorite, Color(0xFFFF9800), "Your previous checks")
    )

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { 
                    Text(
                        "Arogya Nidhi", 
                        style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold)
                    ) 
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f),
                            MaterialTheme.colorScheme.surface
                        )
                    )
                )
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                itemsIndexed(items) { index, item ->
                    AnimatedDashboardCard(
                        item = item,
                        index = index,
                        onClick = {
                            when (item.title) {
                                "Check Eligibility" -> onNavigateToEligibility()
                                "View Schemes" -> onNavigateToSchemes()
                                "Find Hospitals" -> onNavigateToHospitals()
                                "Document Checklist" -> onNavigateToChecklist()
                                "Emergency Help" -> onNavigateToEmergency()
                                "Saved Results" -> onNavigateToSaved()
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun AnimatedDashboardCard(item: DashboardItem, index: Int, onClick: () -> Unit) {
    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        visible = true
    }

    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(animationSpec = tween(durationMillis = 500, delayMillis = index * 100)) +
                slideInVertically(animationSpec = tween(durationMillis = 500, delayMillis = index * 100)) { it / 2 }
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .clip(RoundedCornerShape(24.dp))
                .clickable { onClick() },
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(item.color.copy(alpha = 0.2f), RoundedCornerShape(12.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = null,
                        tint = item.color,
                        modifier = Modifier.size(28.dp)
                    )
                }
                
                Column {
                    Text(
                        text = item.title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = item.description,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        lineHeight = 16.sp
                    )
                }
            }
        }
    }
}
