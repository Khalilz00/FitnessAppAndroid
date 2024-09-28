package com.fitness.fitnessapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.fitness.fitnessapp.ui.theme.FitnessAppTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FitnessAppTheme(dynamicColor = false) {
           var showSplash by remember { mutableStateOf(true) }
            if (showSplash) {
                SplashScreen {
                    showSplash = false
                }
            } else {
                MainScreen()
            }
            }
        }
    }
}


