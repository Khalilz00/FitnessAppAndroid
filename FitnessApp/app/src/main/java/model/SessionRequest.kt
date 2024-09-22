package model

data class SessionRequest(
    val sessionTitle: String,
    val exercises: List<Int>
)
