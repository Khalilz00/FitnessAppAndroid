package com.fitness.fitnessapp

import android.net.Uri
import android.widget.Toast
import android.widget.Toast.makeText
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter

@Composable
fun ProfileImagePicker()  {
    var userImageUri by remember { mutableStateOf<Uri?>(null) }

    val pickImageLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri : Uri? ->
        userImageUri = uri
    }
    var userImageBitmap by remember { mutableStateOf<android.graphics.Bitmap?>(null) }
    val context = LocalContext.current
    val takePictureLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        if (bitmap != null) {
            userImageBitmap = bitmap
        } else {
            makeText(context, "Camera Error", Toast.LENGTH_SHORT).show()
        }
    }

    var showDialog by remember { mutableStateOf(false) }

    if(showDialog) {
        AlertDialog(
            onDismissRequest = {showDialog = false },
            title = { Text(text = "Select an option")},
            text = {
                Text("Would you like to select a saved photo or take a new one?")
            },
            confirmButton = {
                Button(onClick = {
                    takePictureLauncher.launch(null)
                    showDialog = false
                }) { Text("Camera")}
            },
            dismissButton = {
                Button(onClick = {
                    pickImageLauncher.launch("image/*")
                    showDialog= false
                }) {
                    Text("Gallery")
                }
            }
        )
    }
    Image(
        painter = when {
            userImageUri != null -> rememberAsyncImagePainter(userImageUri)
            userImageBitmap != null -> rememberAsyncImagePainter(userImageBitmap)
            else -> painterResource(id = R.drawable.usericon)
        },

        contentDescription = null,
        modifier = Modifier
            .size(64.dp)
            .clip(CircleShape)
            .clickable { showDialog = true },
        contentScale = ContentScale.Crop
    )
}