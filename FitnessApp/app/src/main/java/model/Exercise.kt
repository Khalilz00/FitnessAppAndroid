package model

// Classe de données pour un exercice
data class Exercise(
    val id: Int,                 // ID unique de l'exercice
    val name: String,            // Nom de l'exercice
    val type: String,            // Type d'exercice (ex: "strength")
    val muscle: String,          // Muscle ciblé (ex: "biceps")
    val difficulty: String,      // Niveau de difficulté (ex: "beginner")
    val instructions: String,    // Instructions de l'exercice
    val video: String?,          // Lien vidéo (nullable car il peut être null)
    val image: String?           // Lien vers l'image (nullable car il peut être null)
)
