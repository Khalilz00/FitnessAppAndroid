package com.fitness.fitnessapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults.cardColors
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun InnerCardRowAccountWithLogo(modifier: Modifier = Modifier, logoValue: Int, labelValue:String, dataValue: String) {

    Card(
        shape = RoundedCornerShape(15.dp),
        colors = cardColors(
            containerColor = Color.Red,
        ),

        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)


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
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "My Account",
                style = TextStyle(fontSize = 24.sp),
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )
        }
        item {
            InnerCardRowAccountWithLogo(
                logoValue = R.drawable.baseline_schedule_24,
                labelValue = "Total Time",
                dataValue = "1 hour 30 minutes"
            )
        }
        item {
            InnerCardColumnAccountWithLogo(
                logoValue = R.drawable.baseline_schedule_24,
                labelValue = "Total Time",

            )
        }
        item {
            InnerCardColumnAccountWithLogo(
                logoValue = R.drawable.baseline_schedule_24,
                labelValue = "Total Time",

            )
        }
        item {
            InnerCardColumnAccountWithLogo(
                logoValue = R.drawable.baseline_schedule_24,
                labelValue = "Total Time",

            )
        }
        item {
            InnerCardColumnAccountWithLogo(
                logoValue = R.drawable.baseline_schedule_24,
                labelValue = "Total Time",

                )
        }
        item {
            InnerCardColumnAccountWithLogo(
                logoValue = R.drawable.baseline_schedule_24,
                labelValue = "Total Time",

                )
        }
        item {
            InnerCardColumnAccountWithLogo(
                logoValue = R.drawable.baseline_schedule_24,
                labelValue = "Total Time",

                )
        }
        item {
            InnerCardColumnAccountWithLogo(
                logoValue = R.drawable.baseline_schedule_24,
                labelValue = "Total Time",

                )
        }
    }

}