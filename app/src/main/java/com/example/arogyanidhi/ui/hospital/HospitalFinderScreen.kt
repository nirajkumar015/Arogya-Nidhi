package com.example.arogyanidhi.ui.hospital

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.arogyanidhi.data.model.Hospital

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HospitalFinderScreen(onBack: () -> Unit) {
    val context = LocalContext.current
    var searchQuery by remember { mutableStateOf("") }
    
    val allHospitals = listOf(
        Hospital("Jayadeva Hospital", "Bannerghatta Main Rd", "Bengaluru", "080 2297 7400", "Cardiology"),
        Hospital("Victoria Hospital", "Fort Rd, near City Market", "Bengaluru", "080 2670 1150", "General"),
        Hospital("K.R. Hospital", "Sayyaji Rao Rd", "Mysuru", "0821 242 0333", "General"),
        Hospital("KMC Hospital", "Light House Hill Rd", "Mangaluru", "0824 244 5858", "Multi-specialty"),
        Hospital("SDM Hospital", "Dharwad-Hubli Rd", "Dharwad", "0836 247 7777", "Emergency")
    )

    val filteredHospitals = allHospitals.filter {
        it.name.contains(searchQuery, ignoreCase = true) || it.city.contains(searchQuery, ignoreCase = true)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Find Hospitals") },
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
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Search by hospital name or city") },
                leadingIcon = { Icon(Icons.Default.Search, null) },
                shape = RoundedCornerShape(12.dp),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(filteredHospitals) { hospital ->
                    HospitalCard(hospital) {
                        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${hospital.contact}"))
                        context.startActivity(intent)
                    }
                }
            }
        }
    }
}

@Composable
fun HospitalCard(hospital: Hospital, onCall: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(hospital.name, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                    Text(hospital.type, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.primary)
                }
                IconButton(onClick = onCall, modifier = Modifier.background(MaterialTheme.colorScheme.primaryContainer, RoundedCornerShape(12.dp))) {
                    Icon(Icons.Default.Call, "Call", tint = MaterialTheme.colorScheme.primary)
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.LocationOn, null, modifier = Modifier.size(16.dp), tint = Color.Gray)
                Spacer(modifier = Modifier.width(4.dp))
                Text("${hospital.address}, ${hospital.city}", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}
