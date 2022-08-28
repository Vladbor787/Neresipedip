package ru.netology.nerecipebet.repository

import androidx.lifecycle.LiveData
import ru.netology.nerecipebet.dto.Recipe

interface RecipeRepository {
    val data: LiveData<List<Recipe>>

    fun save(recipe: Recipe)
    fun update(recipe: Recipe)
    fun delete(recipe: Recipe)
    fun favorite(long: Long)
    fun searchText(Text: String)
    fun getData()

    fun showEuropeanCategory(type: String)
    fun showAsianCategory(type: String)
    fun showPanasianCategory(type: String)
    fun showEasternCategory(type: String)
    fun showAmericanCategory(type: String)
    fun showRussianCategory(type: String)
    fun showMediterraneanCategory(type: String)

    fun updateListOnMove(from: Long, to: Long, fromId: Long, toId: Long)
    fun getNextIndexId(): Long
    companion object{
        const val NEW_ID = 0L
    }
}