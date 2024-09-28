package model

import java.time.LocalDateTime

data class ActivitySubmission(
    val sessionId: Int,
    val date: LocalDateTime,
    val duration: Int,
    val notes: String? = null
)