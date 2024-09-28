package com.fitness.fitnessapp

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column



import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

@Composable
fun MainScreen(){
    val navController = rememberNavController()
    Scaffold(

        bottomBar = { BottomNavigationBar(navController) },

        content = { paddingValues ->
            NavigationHost(navController = navController, paddingValues = paddingValues)
        }

    )
}
@Composable
fun BottomNavigationBar(navController: NavHostController) {
    BottomAppBar(
        modifier = Modifier
            .height(80.dp) // Adjust height as needed
            .padding(bottom = 15.dp, start = 40.dp, end = 40.dp)
            .clip(RoundedCornerShape(40.dp)),
        contentColor = MaterialTheme.colorScheme.onPrimary,
        containerColor = Color.Gray  // Background color
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically  // Ensure icons are centered vertically
        ) {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route

            // Home Button
            TextButton(onClick = { navController.navigate("home") }) {
                Icon(
                    imageVector = Icons.Filled.Home,
                    contentDescription = "Home",
                    modifier = Modifier.size(30.dp),  // Increase icon size
                    tint = if (currentRoute == "home") MaterialTheme.colorScheme.primary else Color.LightGray
                )
            }

            // Sessions Button
            TextButton(onClick = { navController.navigate("sessions") }) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_view_list_24),
                    contentDescription = "Sessions",
                    modifier = Modifier.size(30.dp),  // Increase icon size
                    tint = if (currentRoute == "sessions") MaterialTheme.colorScheme.primary else Color.LightGray
                )
            }

            // Account Button
            TextButton(onClick = { navController.navigate("account") }) {
                Icon(
                    imageVector = Icons.Filled.AccountCircle,
                    contentDescription = "Account",
                    modifier = Modifier.size(30.dp),  // Increase icon size
                    tint = if (currentRoute == "account") MaterialTheme.colorScheme.primary else Color.LightGray
                )
            }
        }
    }
}

@Composable
fun NavigationHost(navController: NavHostController, paddingValues: PaddingValues) {
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") { HomeScreen(navController) }
        composable("account") { AccountScreen(navController) }
        composable("sessions") { MySessionsScreen(navController) }
        composable("create-session") { CreateSessionScreen() }
        composable("start-session/{sessionId}") { backStackEntry ->
            val sessionId = backStackEntry.arguments?.getString("sessionId")
            StartSessionScreen(sessionId)
        }
    }
}

