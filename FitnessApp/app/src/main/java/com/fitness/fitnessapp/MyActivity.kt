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

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip

import androidx.compose.ui.graphics.Color

import androidx.compose.ui.layout.ContentScale

import androidx.compose.ui.res.painterResource


import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fitness.fitnessapp.ui.theme.FitnessAppTheme

interface ActivityData {
    val image: String
    val sessionName: String
    val date: String
    val totalTime: String
    val personalNotes: String
}
object objData  : ActivityData {
    override val image: String = "https://picsum.photos/200/300"
    override val sessionName: String = "Back session "
    override val date: String = "2021-10-10"
    override val totalTime: String = "1:30m"
    override val personalNotes: String = "Exercise for back, and bottom of back"
}

@Composable
fun InnerCardRow(modifier: Modifier = Modifier, logoValue: Int, labelValue:String, dataValue: String) {

    Card(
        shape = RoundedCornerShape(15.dp),
        colors = cardColors(
            containerColor = Color.White,
        ),

        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)


    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,

            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
        ) {
            Icon(
                painter = painterResource(id = logoValue),
                contentDescription = "Total Time",
            )
            Text(
                text = labelValue,
                style = TextStyle(fontSize = 18.sp),
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()

            )
            Text(
                text = dataValue,
                style = TextStyle(fontSize = 18.sp),
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
            )
        }
    }
}
@Composable
fun InnerCardColumn(modifier: Modifier = Modifier, logoValue: Int, labelValue:String, dataValue: String) {
    // Personal Notes
    Card(
        shape = RoundedCornerShape(15.dp),
        colors = cardColors(
            containerColor = Color.White,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)


    ) {
        // Use Column to stack elements vertically
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp) // Adjust padding
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp) // Padding for spacing between the icon/label and the personal notes
            ) {
                Icon(
                    painter = painterResource(id = logoValue),
                    contentDescription = "Notes",
                )
                Text(
                    text = labelValue,
                    style = TextStyle(fontSize = 18.sp),
                    modifier = Modifier
                        .padding(start = 8.dp)
                )
            }

            // Personal Notes text displayed under "Notes:"
            Text(
                text = dataValue,
                style = TextStyle(fontSize = 14.sp),
                modifier = Modifier
                    .padding(start = 32.dp) // Indent the personal notes to align with the text
                    .fillMaxWidth()
            )
        }
    }
}

@Composable
fun ElevatedCardExample(it: ActivityData) {

        ElevatedCard(
            elevation = CardDefaults.cardElevation(
                defaultElevation = 6.dp
            ),
            modifier = Modifier
                .padding(8.dp) // Padding inside the card
        ) {

            // Align Image with Session Name
            Row(
                verticalAlignment = Alignment.CenterVertically, // Center the image and text vertically
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp) // Adjust padding as needed
            ) {
                // Circular Image
                Image(
                    painter = painterResource(id = R.drawable.backex),
                    contentDescription = "Back Exercise Image",
                    modifier = Modifier
                        .size(60.dp) // Adjust size as needed
                        .clip(CircleShape), // Clip to circle
                    contentScale = ContentScale.Crop // Crop the image to fit the circle shape
                )

                Spacer(modifier = Modifier.width(16.dp)) // Add space between image and text

                // Session Name Text
                Text(
                    text = it.sessionName,
                    style = TextStyle(fontSize = 24.sp, color = Color.DarkGray),
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Left
                )
            }

            InnerCardRow(
                labelValue = "Date: ",
                dataValue = it.date,
                logoValue = R.drawable.baseline_date_range_24
            )
            // Rest of the data line by line // REMOVE
            InnerCardRow(
                labelValue = "Total Time: ",
                dataValue = it.totalTime,
                logoValue = R.drawable.baseline_schedule_24
            )

            // Personal Notes TO REMOVE
            InnerCardColumn(
                labelValue = "Notes: ",
                dataValue = it.personalNotes,
                logoValue = R.drawable.baseline_note_24
            )


        }
    }


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MyActivity(modifier: Modifier = Modifier) {
    val mockedData: Array<ActivityData> = Array(5) { objData }

    val pagerState = rememberPagerState { mockedData.size }

    // HorizontalPager where two full cards and half of the third are visible
    Column (
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ){
        Text(
            text = "My Activity",
            style = TextStyle(fontSize = 24.sp),
            textAlign = TextAlign.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )
        LazyRow(
            modifier = modifier

                .fillMaxWidth()
        ) {

            items(mockedData.size) { index ->
                val item = mockedData[index]
                ElevatedCardExample(item)
            }
        }
    }

}


@Preview(showBackground = true)
@Composable
fun MyActivityPreview(modifier: Modifier = Modifier) {
    FitnessAppTheme {
        MyActivity(modifier = Modifier)
    }
}