package ru.netology.nerecipebet.dto.stepData
import kotlinx.serialization.Serializable

@Serializable
data class Step(
    val idStep: Long,
    val idRecipe: Long,
    val stepText: String,
    val picture: String = ""
)
