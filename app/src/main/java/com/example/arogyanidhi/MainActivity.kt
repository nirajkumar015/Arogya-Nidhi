package com.example.arogyanidhi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.arogyanidhi.data.model.User
import com.example.arogyanidhi.ui.checklist.DocumentChecklistScreen
import com.example.arogyanidhi.ui.eligibility.EligibilityScreen
import com.example.arogyanidhi.ui.emergency.EmergencyScreen
import com.example.arogyanidhi.ui.home.HomeScreen
import com.example.arogyanidhi.ui.hospital.HospitalFinderScreen
import com.example.arogyanidhi.ui.results.ResultsScreen
import com.example.arogyanidhi.ui.saved.SavedResultsScreen
import com.example.arogyanidhi.ui.scheme.SchemeListScreen
import com.example.arogyanidhi.ui.splash.SplashScreen
import com.example.arogyanidhi.ui.theme.ArogyaNidhiTheme
import com.example.arogyanidhi.utils.EligibilityEngine
import com.example.arogyanidhi.viewmodel.SchemeViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ArogyaNidhiTheme {
                val navController = rememberNavController()
                val schemeViewModel: SchemeViewModel = viewModel()
                
                NavHost(navController = navController, startDestination = "splash") {
                    composable("splash") {
                        SplashScreen {
                            navController.navigate("home") {
                                popUpTo("splash") { inclusive = true }
                            }
                        }
                    }
                    composable("home") {
                        HomeScreen(
                            onNavigateToEligibility = { navController.navigate("eligibility") },
                            onNavigateToHospitals = { navController.navigate("hospitals") },
                            onNavigateToEmergency = { navController.navigate("emergency") },
                            onNavigateToSchemes = { navController.navigate("schemes") },
                            onNavigateToSaved = { navController.navigate("saved") },
                            onNavigateToChecklist = { navController.navigate("checklist") }
                        )
                    }
                    composable("eligibility") {
                        EligibilityScreen(
                            onBack = { navController.popBackStack() },
                            onResult = { _ ->
                                navController.navigate("results")
                            }
                        )
                    }
                    composable("results") {
                        val demoUser = User(
                            name = "User",
                            age = 25,
                            income = 100000.0,
                            bplStatus = true,
                            state = "Karnataka"
                        )
                        val schemes = EligibilityEngine.checkEligibility(demoUser)
                        ResultsScreen(
                            schemes = schemes,
                            viewModel = schemeViewModel,
                            onBack = { navController.popBackStack() }
                        )
                    }
                    composable("hospitals") {
                        HospitalFinderScreen(onBack = { navController.popBackStack() })
                    }
                    composable("emergency") {
                        EmergencyScreen(onBack = { navController.popBackStack() })
                    }
                    composable("schemes") {
                        SchemeListScreen(onBack = { navController.popBackStack() })
                    }
                    composable("saved") {
                        SavedResultsScreen(viewModel = schemeViewModel, onBack = { navController.popBackStack() })
                    }
                    composable("checklist") {
                        DocumentChecklistScreen(onBack = { navController.popBackStack() })
                    }
                }
            }
        }
    }
}
