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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

@Composable
fun BottomNavigationItem(logoValue: Int, route: String, navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    IconButton(
        onClick = { navController.navigate(route) },

    ) {
        Icon(
            painter = painterResource(id = logoValue),
            contentDescription = "Home",
            tint = if (currentRoute == route) MaterialTheme.colorScheme.primary else Color.LightGray
        )
    }

}
@Composable
fun ContentBottomAppBar(navController: NavHostController ,modifier: Modifier = Modifier) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxHeight(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
        // Centers the content vertically in the Row
        //modifier = Modifier.fillMaxSize().fillMaxHeight()  //.wrapContentSize()
    ) {
        //Surface(
        //    //circle shape if route is selected
        //    shape = CircleShape,
        //    color = if (currentRoute == "home") MaterialTheme.colorScheme.inversePrimary else Color.Transparent,
        //) {}
        BottomNavigationItem(R.drawable.baseline_home_24, "home", navController)
        BottomNavigationItem(R.drawable.baseline_insert_chart_24, "charts", navController)
        AddFloatingActionButton(navController,
            modifier = Modifier
                .size(30.dp)
        )
        BottomNavigationItem(R.drawable.baseline_view_list_24, "sessions", navController)
        BottomNavigationItem(R.drawable.baseline_account_circle_24, "account", navController)
    }
}
@Composable
fun AddFloatingActionButton(navController: NavHostController,modifier: Modifier = Modifier) {
    FloatingActionButton(
        onClick = { navController.navigate("create-session") },
        shape = CircleShape,
        containerColor = MaterialTheme.colorScheme.primary,
        modifier = modifier
        //.absoluteOffset(y = (0).dp) // Adjust the FAB position vertically (overlaps the BottomAppBar)
    ) {
        Icon(
            Icons.Filled.Add,
            contentDescription = "Add",
            tint = Color.White

        )
    }
}
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

            .height(100.dp) // Adjust height as needed
            //Arrange content centered vertically

            .padding(
                bottom = 25.dp,
                start = 40.dp,
                end = 40.dp
            )
            .clip(
                RoundedCornerShape(
                    //topStart = 30.dp,
                    //topEnd = 30.dp
                    40.dp
                )
            ),            //.fillMaxHeight(),
        //
        contentColor = MaterialTheme.colorScheme.onPrimary,
        containerColor = MaterialTheme.colorScheme.secondary // Background color
    ) {
        Box(
            modifier = Modifier
                    //center content vertically in the box
                .padding(top = (11).dp)
                .align(Alignment.CenterVertically),

            content =
            {
                ContentBottomAppBar(navController)
            }

        )
    }


}



@Composable
fun NavigationHost(navController: NavHostController, paddingValues: PaddingValues) {
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") { HomeScreen(navController) }
        composable("charts") { MyChartsScreen(navController) }
        composable("account") { AccountScreen(navController) }
        composable("sessions") { MySessionsScreen(navController) }
        composable("create-session") { CreateSessionScreen() }
        composable("start-session/{sessionId}/{sessionName}/{imageUrl}") { backStackEntry ->
            val sessionId = backStackEntry.arguments?.getString("sessionId")
            val sessionName = backStackEntry.arguments?.getString("sessionName") ?: ""
            val imageUrl = backStackEntry.arguments?.getString("imageUrl") ?: ""
            StartSessionScreen(sessionId, sessionName, imageUrl)
        }
    }
}


