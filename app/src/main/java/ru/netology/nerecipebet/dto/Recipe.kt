package ru.netology.nerecipebet.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class Recipe(
    val id: Long,
    val title: String,
    val authorName: String,
    val recipeImage: String,
    val categoryRecipe: String,
    val textRecipe: String,
    val isFavorite: Boolean = false,
    val indexPosition: Long
) : Parcelable