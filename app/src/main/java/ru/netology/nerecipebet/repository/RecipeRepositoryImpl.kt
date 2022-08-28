package ru.netology.nerecipebet.repository

import androidx.lifecycle.map
import ru.netology.nerecipebet.dto.Recipe
import ru.netology.nerecipebet.dao.RecipeDao
import ru.netology.nerecipebet.dto.toEntity
import ru.netology.nerecipebet.dto.toModel

class RecipeRepositoryImpl(
    private val dao: RecipeDao
) : RecipeRepository {
    private var nextIndexId: Long = 1
    override fun getNextIndexId(): Long {
        return nextIndexId++
    }
    override fun updateListOnMove(from: Long, to: Long, fromId: Long, toId: Long) {
        if (to < from) {
            dao.updateItemMoveDown(fromId, toId)
        } else {
            dao.updateItemMoveUp(fromId, toId)
        }
    }
    override var data = dao.getAll().map { entities ->
        entities.map { it.toModel() }
    }

    override fun getData() {
        data = dao.getAll().map { entities ->
            entities.map { it.toModel() }
        }
    }

    override fun save(recipe: Recipe) {
        if (recipe.id == RecipeRepository.NEW_ID) dao.save(recipe = recipe.toEntity())
        else recipe.categoryRecipe.let {
            dao.updateContentById(
                recipe.id, recipe.title, recipe.authorName,recipe.recipeImage,
                it, recipe.textRecipe,recipe.indexPosition
            )
        }
    }

    override fun update(recipe: Recipe) {
        save(recipe)
    }

    override fun delete(recipe: Recipe) {
        dao.removeById(recipe.id)
    }

    override fun favorite(long: Long) {
        dao.favById(long)
    }

    override fun searchText(Text: String) {
        data = dao.searchByText(Text).map { entities ->
            entities.map { it.toModel() }
        }
    }

    override fun showEuropeanCategory(type: String) {
        data = data.map { list ->
            list.filter { it.categoryRecipe != type }
        }
    }

    override fun showAsianCategory(type: String) {
        data = data.map { list ->
            list.filter { it.categoryRecipe != type}
        }
    }

    override fun showPanasianCategory(type: String) {
        data = data.map { list ->
            list.filter { it.categoryRecipe != type}
        }
    }

    override fun showEasternCategory(type: String) {
        data = data.map { list ->
            list.filter { it.categoryRecipe != type}
        }
    }

    override fun showAmericanCategory(type: String) {
        data = data.map { list ->
            list.filter { it.categoryRecipe != type}
        }
    }

    override fun showRussianCategory(type: String) {
        data = data.map { list ->
            list.filter { it.categoryRecipe != type}
        }
    }

    override fun showMediterraneanCategory(type: String) {
        data = data.map { list ->
            list.filter { it.categoryRecipe != type}
        }
    }

}
