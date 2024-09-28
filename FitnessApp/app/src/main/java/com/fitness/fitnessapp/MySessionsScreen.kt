package com.fitness.fitnessapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

import model.Exercise
import model.Session

var sessionList: List<Session> = listOf(
    Session(
        id = 1,
        name = "Session 1",
        image_url = "https://www.verywellfit.com/thmb/tf2Wfi_PrLmyPT8lrv52cMM3RCM=/7016x4683/filters:fill(FFDB5D,1)/making-a-concerted-effort-to-get-fit-498334765-5aa988e5ae9ab80037feb6ac.jpg",

    ),
    Session(
        id = 2,
        name = "Session 2",
        image_url = "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fwww.ericfavre.com%2Flifestyle%2Fwp-content%2Fuploads%2F2020%2F06%2Fmeilleurx-exercices-dos-1024x683.jpg&f=1&nofb=1&ipt=42d235a50f558f04440f738c9658f0c03e2db6d3f84dbb8068841f938e201985&ipo=images",

    ),
    Session(
        id = 3,
        name = "Session 3",
        image_url = "https://picsum.photos/200/300",

    ),
)
var exerciseList: List<Exercise> = listOf(

    Exercise(
        id = 1,
        name = "Exercise 1",
        type = "strength",
        muscle = "biceps",
        difficulty = "beginner",
        instructions = "Instructions for exercise 1",
        video = "https://www.youtube.com/watch?v=123",
        image = "https://picsum.photos/200/300"
    ),
    Exercise(
        id = 2,
        name = "Exercise 2",
        type = "strength",
        muscle = "biceps",
        difficulty = "beginner",
        instructions = "Instructions for exercise 2",
        video = "https://www.youtube.com/watch?v=123",
        image = "https://picsum.photos/200/300"
    ),
)
@Composable
fun SessionTitle(title: String) {
    Text(
        text = title,
        style = TextStyle(fontSize = 24.sp),
        textAlign = TextAlign.Start,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
    )
}
@Composable
fun cardContent(session: Session, expanded: Boolean, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = session.name,
            style = TextStyle(fontSize = 18.sp),
            textAlign = TextAlign.Start,
            modifier = Modifier.fillMaxWidth()
        )

        if (expanded) {
            exerciseList.forEach { exercise ->
                Text(
                    text = exercise.name,
                    style = TextStyle(fontSize = 16.sp),
                    textAlign = TextAlign.Start,
                    modifier = Modifier.fillMaxWidth()
                )

            }
            Surface(
                shape = CircleShape,
                modifier = Modifier.align(Alignment.End).width(100.dp).height(50.dp),


            ) {
                TextButton(
                    onClick = { /*TODO*/ },
                    //fit width to text
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = MaterialTheme.colorScheme.primary
                    )

                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_play_circle_24),
                        contentDescription = "Start",
                        modifier = Modifier.size(24.dp),
                    )
                    Text(
                        text = "Start",
                        style = TextStyle(fontSize = 16.sp),
                        textAlign = TextAlign.Start,
                        modifier = Modifier.fillMaxWidth().padding(start = 8.dp)
                    )
                }

            }

        }
    }
}

@Composable
fun SessionCardItem(session: Session, modifier: Modifier = Modifier) {
    //state value for expandable
    var expanded = remember { mutableStateOf(false) }
    //Expandable Card with Image and Text
    Card(
        shape = RoundedCornerShape(15.dp),
        modifier = modifier
            .fillMaxWidth()
            //.clip(RoundedCornerShape(15.dp))
            .padding(6.dp),
        onClick = { expanded.value = !expanded.value }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Image with url
            Image(
                painter = painterResource(id = R.drawable.backex),
                contentDescription = "Back Exercise Image",
                modifier = Modifier
                    .size(60.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(16.dp))


            cardContent(session, expanded.value)
        }
    }
}

@Composable
fun MySessionsScreen(navController: NavController) {
    Spacer(modifier = Modifier.width(16.dp))
    Column(
        modifier = Modifier
            .fillMaxSize()

            .statusBarsPadding()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        SessionTitle(title = "My Sessions")

            LazyColumn(modifier = Modifier.fillMaxWidth(),
            ) {
                item {
                    sessionList.forEach { session ->
                        SessionCardItem(session)
                    }
                // Add padding to scroll till the end
                Spacer(modifier = Modifier.fillMaxWidth().height(100.dp))
            }
        }
    }



}
