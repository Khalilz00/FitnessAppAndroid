package com.fitness.fitnessapp

import android.R.id
import androidx.compose.foundation.Image
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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter


data class Session(
    val imageRes: Int,
    val title: String
)

val sessionItems = listOf(
    Session(R.drawable.back, "Back"),
    Session(R.drawable.squat, "Legs"),
    Session(R.drawable.running, "Cardio")
)

@Composable
fun SessionsCarousel() {
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ){
        Text(
            text = "My Sessions",
            fontSize = 24.sp,  // Larger font size
            //fontWeight = FontWeight.Bold,  // Make the text bold
            textAlign = TextAlign.Start,  // Align text to the start
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)  // Add some space below the title
        )

        LazyRow (
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ){
            items(sessionItems){ session ->
                SessionCard(session)
            }
            item {
                AddSessionCard()
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
                painter = rememberAsyncImagePainter(session.imageRes),
                contentDescription = session.title,
                modifier = Modifier
                    .size(70.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = session.title,
                fontSize = 19.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun AddSessionCard(){
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
                modifier = Modifier.size(50.dp),
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

