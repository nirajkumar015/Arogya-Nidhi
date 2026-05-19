package com.example.arogyanidhi.ui.emergency

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Call
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmergencyScreen(onBack: () -> Unit) {
    val context = LocalContext.current

    val emergencyContacts = listOf(
        "Ambulance" to "108",
        "Police" to "100",
        "Women Helpline" to "1091",
        "Health Helpline" to "104"
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Emergency SOS") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(200.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFFFEBEE))
                    .clickable {
                        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:108"))
                        context.startActivity(intent)
                    },
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        Icons.Default.Call,
                        contentDescription = "Emergency Call",
                        modifier = Modifier.size(64.dp),
                        tint = Color.Red
                    )
                    Text("SOS", color = Color.Red, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                }
            }

            Text(
                "Tap the red button for immediate Ambulance (108)",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text("Quick Dial", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)

            emergencyContacts.forEach { (name, number) ->
                EmergencyCallCard(name, number) {
                    val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$number"))
                    context.startActivity(intent)
                }
            }
        }
    }
}

@Composable
fun EmergencyCallCard(name: String, number: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
    ) {
        Row(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(name, fontWeight = FontWeight.Bold)
                Text(number, color = MaterialTheme.colorScheme.primary)
            }
            Icon(Icons.Default.Call, null, tint = MaterialTheme.colorScheme.primary)
        }
    }
}
