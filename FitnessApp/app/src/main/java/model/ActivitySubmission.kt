package model


data class ActivitySubmission(
    val sessionId: Int,
    val date: String,
    val duration: Int,
    val notes: String? = null
)