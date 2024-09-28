package model

import java.time.LocalDateTime

// Data Class for MyActivity on Main Page
data class Activity(
    val sessionId: Int,
    val date: String,
    val duration: Int,
    val notes: String?,
    val session_name: String,
    val session_image: String

)
