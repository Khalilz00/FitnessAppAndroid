package com.fitness.fitnessapp

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardDefaults.cardColors
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.fitness.fitnessapp.ui.theme.FitnessAppTheme
import model.Activity
import network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate
import java.time.LocalDateTime

import java.time.format.DateTimeFormatter

@Composable
fun InnerCardRow(modifier: Modifier = Modifier, logoValue: Int, labelValue: String, dataValue: String) {
    Card(
        shape = RoundedCornerShape(15.dp),
        colors = cardColors(containerColor = Color.White),
        modifier = Modifier.fillMaxWidth().padding(10.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth().padding(5.dp)
        ) {
            Icon(painter = painterResource(id = logoValue), contentDescription = "Icon")
            Text(
                text = labelValue,
                style = TextStyle(fontSize = 18.sp),
                modifier = Modifier.padding(16.dp).fillMaxWidth()
            )
            Text(
                text = dataValue,
                style = TextStyle(fontSize = 18.sp),
                modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth()
            )
        }
    }
}

@Composable
fun InnerCardColumn(modifier: Modifier = Modifier, logoValue: Int, labelValue: String, dataValue: String) {
    Card(
        shape = RoundedCornerShape(15.dp),
        colors = cardColors(containerColor = Color.White),
        modifier = Modifier.fillMaxWidth().padding(10.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(10.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
            ) {
                Icon(painter = painterResource(id = logoValue), contentDescription = "Notes")
                Text(
                    text = labelValue,
                    style = TextStyle(fontSize = 18.sp),
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
            Text(
                text = dataValue,
                style = TextStyle(fontSize = 14.sp),
                modifier = Modifier.padding(start = 32.dp).fillMaxWidth()
            )
        }
    }
}

@Composable
fun ElevatedCardExample(activity: Activity) {
    var sessionImage by remember { mutableStateOf(activity.session_image) }
    var sessionName by remember { mutableStateOf(activity.session_name) }

    // Format the date string
    val formattedDate = activity.date ?: "Unknown Date"

    // Format the duration (assuming duration is in seconds)
    val hours = activity.duration / 3600
    val minutes = (activity.duration % 3600) / 60
    val seconds = activity.duration % 60
    val formattedDuration = String.format("%02d:%02d:%02d", hours, minutes, seconds)

    ElevatedCard(
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = Modifier.padding(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ) {
            Image(
                painter = rememberAsyncImagePainter(model = sessionImage ?: R.drawable.running),
                contentDescription = "Session Image",
                modifier = Modifier.size(60.dp).clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = sessionName ?: "Unknown Session",
                style = TextStyle(fontSize = 24.sp, color = Color.DarkGray),
                modifier = Modifier.padding(16.dp).fillMaxWidth(),
                textAlign = TextAlign.Left
            )
        }
        InnerCardRow(
            labelValue = "Date: ",
            dataValue = formattedDate,
            logoValue = R.drawable.baseline_date_range_24
        )
        InnerCardRow(
            labelValue = "Session duration: ",
            dataValue = formattedDuration,
            logoValue = R.drawable.baseline_schedule_24
        )
        InnerCardColumn(
            labelValue = "Notes: ",
            dataValue = activity.notes ?: "No notes available",
            logoValue = R.drawable.baseline_note_24
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MyActivity(modifier: Modifier = Modifier) {
    var activities by remember { mutableStateOf<List<Activity>>(emptyList()) }

    // Fetch activities from the API
    LaunchedEffect(Unit) {
        RetrofitClient.apiService.getActivities().enqueue(object : Callback<List<Activity>> {
            override fun onResponse(call: Call<List<Activity>>, response: Response<List<Activity>>) {
                if (response.isSuccessful) {
                    activities = response.body() ?: emptyList()
                    println("Activities fetched: ${activities.size}")
                } else {
                    println("Failed to fetch activities: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<Activity>>, t: Throwable) {
                println("Error fetching activities: ${t.message}")
            }
        })
    }

    Column(
        modifier = modifier.fillMaxWidth().padding(16.dp)
    ) {
        Text(
            text = "My Activity",
            style = TextStyle(fontSize = 24.sp),
            textAlign = TextAlign.Start,
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        )
        LazyRow(modifier = modifier.fillMaxWidth()) {
            items(activities.size) { index ->
                val item = activities[index]
                ElevatedCardExample(item)
            }
        }
    }
}


