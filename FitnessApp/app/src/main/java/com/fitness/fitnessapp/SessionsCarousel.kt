package com.fitness.fitnessapp

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import model.Session
import network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


@Composable
fun SessionsCarousel(navController: NavController) {
    val context = LocalContext.current
    var sessions by remember { mutableStateOf<List<Session>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        RetrofitClient.apiService.getSessions().enqueue(object : Callback<List<Session>>{
            override fun onResponse(call: Call<List<Session>>, response: Response<List<Session>>){
                if (response.isSuccessful){
                    sessions = response.body() ?: emptyList()
                } else {
                    Toast.makeText(context, "Failed to fetch sessions", Toast.LENGTH_SHORT).show()
                }
                isLoading = false
            }
            override fun onFailure(call: Call<List<Session>>, t: Throwable){
                Toast.makeText(context , "Error: ${t.message}",Toast.LENGTH_SHORT).show()
                isLoading = false
            }
        })
    }
    // UI: Loading spinner while data is being fetched
    if (isLoading) {
        CircularProgressIndicator(modifier = Modifier.padding(16.dp))

    } else {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "My Sessions",
                fontSize = 24.sp,  // Larger font size
                //fontWeight = FontWeight.Bold,  // Make the text bold
                textAlign = TextAlign.Start,  // Align text to the start
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)  // Add some space below the title
            )

            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(sessions) { session ->
                    SessionCard(session)
                }
                item {
                    AddSessionCard(onClick = {
                        navController.navigate("create-session")
                    })
                }
            }
        }
    }
}


@Composable
fun SessionCard(session: Session){
    Card(
        modifier = Modifier
            .width(130.dp)
            .height(150.dp)
            .padding(8.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.squat),
                contentDescription = session.name,
                modifier = Modifier
                    .size(70.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = session.name,
                fontSize = 19.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun AddSessionCard(onClick: ()-> Unit){
    Card(
        modifier = Modifier
            .width(130.dp)
            .height(150.dp)
            .padding(8.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {

        Column (
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){
            Spacer(modifier = Modifier.height(12.dp))
            Icon(
                painter = painterResource(id = R.drawable.baseline_add_circle_24),
                contentDescription = "Add Session",
                modifier = Modifier
                    .size(50.dp)
                    .clickable { onClick() },
                tint = MaterialTheme.colorScheme.primary


            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Add Session",
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}

