package ru.netology.nerecipebet.adapter
import ru.netology.nerecipebet.dto.Recipe

interface RecipeInteractionListener {
    fun onRemoveClicked(recipe: Recipe)
    fun onEditClicked(recipe: Recipe)
    fun onFavoriteClicked(recipeId: Long)
    fun onSearchClicked(text: String)
    fun onCreateClicked()
    fun updateContent(
        id: Long, title: String,
        authorName: String, recipeImage: String,
        categoryRecipe: String, textRecipe: String)
    fun onSaveClicked(
        title: String,
        authorName: String, recipeImage: String, categoryRecipe: String, textRecipe: String)
    fun onSingleRecipeClicked(recipe: Recipe)
}