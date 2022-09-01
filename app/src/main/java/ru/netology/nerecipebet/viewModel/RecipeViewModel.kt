package ru.netology.nerecipebet.viewModel

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nerecipebet.adapter.RecipeInteractionListener
import ru.netology.nerecipebet.db.AppDb
import ru.netology.nerecipebet.dto.FilterState
import ru.netology.nerecipebet.dto.PhotoModel
import ru.netology.nerecipebet.dto.Recipe
import ru.netology.nerecipebet.repository.RecipeRepository
import ru.netology.nerecipebet.repository.RecipeRepositoryImpl
import ru.netology.nerecipebet.utill.Event

class RecipeViewModel(application: Application) : AndroidViewModel(application),
    RecipeInteractionListener {
    private val noPhoto = PhotoModel()
    private val repository: RecipeRepository =
        RecipeRepositoryImpl(dao = AppDb.getInstance(context = application).recipeDao)
    private val _photo = MutableLiveData(noPhoto)
    val filterStateMutableLiveData = MutableLiveData(FilterState())
    private val filterSingleLiveEvent = Event<Boolean>()
    val photo: LiveData<PhotoModel>
        get() = _photo
    val data by repository::data
    var filterIsActive = false
    val favoriteFragment = Event<String>()
    val createFragment = Event<Unit>()
    val updateRecipeFragment = Event<String?>()
    val singleFragment = Event<Unit>()
    val filterFragment = Event<Unit>()
    val updateRecipe = MutableLiveData<Recipe>(null)
    val singleRecipe = MutableLiveData<Recipe?>(null)
    val feedFragment = data
    private val currentRecipe = MutableLiveData<Recipe?>(null)


    fun updateListOnMove(from: Long, to: Long, fromId: Long, toId: Long) {
        repository.updateListOnMove(from, to, fromId, toId)
    }
    fun clearFilter() {
        repository.getData()
    }
     override fun updateContent(
        id: Long,
        title: String,
        authorName: String,
        recipeImage: String,
        categoryRecipe: String,
        textRecipe: String
    ) {
        val recipe = Recipe(
            id = id,
            title = title,
            authorName = authorName,
            recipeImage = recipeImage,
            categoryRecipe = categoryRecipe,
            textRecipe = textRecipe,
            indexPosition = repository.getNextIndexId()

        )
        repository.save(recipe)
    }

    override fun onRemoveClicked(recipe: Recipe) {
        repository.delete(recipe)
    }

    override fun onEditClicked(recipe: Recipe) {
        updateRecipe.value = recipe
        updateRecipeFragment.call()
    }

    override fun onFavoriteClicked(recipeId: Long) {
        repository.favorite(recipeId)
    }

    override fun onSearchClicked(text: String) {
        repository.searchText(text)
    }

    override fun onCreateClicked() {
        createFragment.call()
    }

    override fun onSaveClicked(
        title: String,
        authorName: String,
        recipeImage: String,
        categoryRecipe: String,
        textRecipe: String
    ) {

        val recipe = Recipe(
            id = RecipeRepository.NEW_ID,
            title = title,
            authorName = authorName,
            recipeImage = recipeImage,
            categoryRecipe = categoryRecipe,
            textRecipe = textRecipe,
            indexPosition = repository.getNextIndexId()
        )
        repository.save(recipe)
        _photo.value = noPhoto
        currentRecipe.value = null
    }

    override fun onSingleRecipeClicked(recipe: Recipe) {
        singleRecipe.value = recipe
        singleFragment.call()
    }

    fun showBeefCategory(categoryRecipe: String) {
        repository.showEuropeanCategory(categoryRecipe)
        filterSingleLiveEvent.value = true
        filterIsActive = true
    }
    fun showPorkCategory(categoryRecipe: String) {
        repository.showAsianCategory(categoryRecipe)
        filterSingleLiveEvent.value = true
        filterIsActive = true
    }
    fun showLambCategory(categoryRecipe: String) {
        repository.showPanasianCategory(categoryRecipe)
        filterSingleLiveEvent.value = true
        filterIsActive = true
    }

    fun showChickenCategory(categoryRecipe: String) {
        repository.showEasternCategory(categoryRecipe)
        filterSingleLiveEvent.value = true
        filterIsActive = true
    }

    fun showSeafoodCategory(categoryRecipe: String) {
        repository.showAmericanCategory(categoryRecipe)
        filterSingleLiveEvent.value = true
        filterIsActive = true
    }

    fun showPastaCategory(categoryRecipe: String) {
        repository.showRussianCategory(categoryRecipe)
        filterSingleLiveEvent.value = true
        filterIsActive = true
    }
    fun showDessertCategory(categoryRecipe: String) {
        repository.showMediterraneanCategory(categoryRecipe)
        filterSingleLiveEvent.value = true
        filterIsActive = true
    }

    fun changePhoto(uri: Uri?) {
        _photo.value = PhotoModel(uri)
    }
    fun resetFilterCheckboxes() {
        filterStateMutableLiveData.value = FilterState()
    }
}