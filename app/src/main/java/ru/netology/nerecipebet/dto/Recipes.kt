package ru.netology.nerecipebet.dto

internal fun RecipeEntity.toModel(): Recipe {

    return Recipe(
        id = id,
        title = title,
        authorName = authorName,
        recipeImage = recipeImage,
        categoryRecipe = categoryRecipe,
        textRecipe = textRecipe,
        isFavorite = isFavorite,
        indexPosition = indexNumber
    )
}

internal fun Recipe.toEntity() = RecipeEntity(
    id = id,
    title = title,
    authorName = authorName,
    recipeImage = recipeImage,
    categoryRecipe = categoryRecipe,
    textRecipe = textRecipe,
    isFavorite = isFavorite,
    indexNumber = indexPosition
)