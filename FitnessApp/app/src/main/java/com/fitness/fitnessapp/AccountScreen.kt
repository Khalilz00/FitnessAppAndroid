package com.fitness.fitnessapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults.cardColors
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController


@Composable
fun SliderHeight() {
    var sliderPosition = remember { mutableFloatStateOf(0f) }
    Column {
        Slider(
            value = sliderPosition.value,
            onValueChange = { sliderPosition.value = it },
            colors = SliderDefaults.colors(
                thumbColor = MaterialTheme.colorScheme.secondary,
                activeTrackColor = MaterialTheme.colorScheme.secondary,
                inactiveTrackColor = MaterialTheme.colorScheme.secondaryContainer,
            ),
            steps = 100,
            valueRange = 0f..50f
        )
        Text(text = sliderPosition.toString())
    }
}


@Composable
fun AccountCardItem(labelValue: String, logoValue: Int, modifier: Modifier = Modifier) {
    //state value for expandable
    var expanded = remember { mutableStateOf(false) }
    //Expandable Card with Image and Text
    Card(
        shape = RoundedCornerShape(15.dp),
        modifier = modifier
            .fillMaxWidth(),
            //.clip(RoundedCornerShape(15.dp))
            //.padding(6.dp),
        onClick = { expanded.value = !expanded.value }

    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            // Image with url
            Image(
                painter = painterResource(id = logoValue),
                contentDescription = logoValue.toString(),
                modifier = Modifier
                    .size(50.dp)
                    .clip(RoundedCornerShape(15.dp))
                    .padding(4.dp),

                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(6.dp))


            Text(
                text = labelValue,
                style = TextStyle(fontSize = 18.sp),
                textAlign = TextAlign.Start,
                modifier = Modifier.fillMaxWidth().padding(start = 8.dp)
            )


        }
    }
}

@Composable
fun AccountExpandableCardItem(labelValue: String, logoValue: Int, modifier: Modifier = Modifier) {
    //state value for expandable
    var expanded = remember { mutableStateOf(false) }
    //Expandable Card with Image and Text
    Card(
        shape = RoundedCornerShape(15.dp),
        modifier = modifier
            .fillMaxWidth(),
        //.clip(RoundedCornerShape(15.dp))
        //.padding(6.dp),
        onClick = { expanded.value = !expanded.value }

    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            // Image with url
            Image(
                painter = painterResource(id = logoValue),
                contentDescription = logoValue.toString(),
                modifier = Modifier
                    .size(50.dp)
                    .clip(RoundedCornerShape(15.dp))
                    .padding(4.dp),

                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(6.dp))


            Text(
                text = labelValue,
                style = TextStyle(fontSize = 18.sp),
                textAlign = TextAlign.Start,
                modifier = Modifier.fillMaxWidth().padding(start = 8.dp)
            )



        }
        if (expanded.value) {
            // Content expanded
            SliderHeight()
        }
    }
}


@Composable
fun InnerCardColumnAccountWithLogo(modifier: Modifier = Modifier, logoValue: Int, labelValue:String) {
    // Personal Notes
    Card(
        shape = RoundedCornerShape(15.dp),
        colors = cardColors(
            containerColor = Color.LightGray,
        ),
        modifier = Modifier
            .fillMaxWidth()



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
                    modifier = Modifier.padding(start = 5.dp),
                    painter = painterResource(id = logoValue),
                    contentDescription = logoValue.toString()
                )
                Text(
                    text = labelValue,
                    style = TextStyle(fontSize = 18.sp),
                    modifier = Modifier
                        .padding(start = 12.dp)
                )
            }

        }
    }
}

@Composable
fun AccountScreen(navController: NavController){

    Spacer(modifier = Modifier.width(16.dp))
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()

            .statusBarsPadding()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        item {
            Text(
                text = "My Account",
                style = TextStyle(fontSize = 24.sp),
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 6.dp)
            )
        }
        item {
            Column(

            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp, top = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ProfileImagePicker()

                    Spacer(modifier = Modifier.width(16.dp))

                    Text(
                        text = "John Doe",
                        fontSize = 20.sp
                    )
                }
            }
        }


        item {
            AccountCardItem(
                logoValue = R.drawable.baseline_manage_accounts_24,
                labelValue = "Modify my personal information",

            )
        }
        item {
            AccountCardItem(
                logoValue = R.drawable.baseline_schedule_24,
                labelValue = "Total Time",

            )
        }
        item {
            AccountExpandableCardItem(
                logoValue = R.drawable.fitness,
                labelValue = "My Body Type",

                )
        }
        item {
            AccountCardItem(
                logoValue = R.drawable.baseline_privacy_tip_24,
                labelValue = "Privacy",

                )
        }
        item {
            AccountCardItem(
                logoValue = R.drawable.baseline_help_center_24,
                labelValue = "Help Center",

                )
        }

        item {
            Spacer(modifier = Modifier.fillMaxWidth().height(100.dp))
        }
    }

}