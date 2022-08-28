package ru.netology.nerecipebet.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction

import ru.netology.nerecipebet.dto.RecipeEntity

@Dao
interface RecipeDao {
    @Query("SELECT * FROM recipes ORDER BY indexNumber DESC")
    fun getAll(): LiveData<List<RecipeEntity>>
    @Query("SELECT * FROM recipes ORDER BY indexNumber DESC")
    fun getData(): LiveData<List<RecipeEntity>>
    @Insert
    fun save(recipe: RecipeEntity)
    @Insert
    fun insertData(data: List<RecipeEntity>)

    @Query("UPDATE recipes SET " +
            "title = :title," +
            "authorName = :authorName, " +
            "recipeImage = :recipeImage, " +
            "categoryRecipe = :categoryRecipe," +
            "textRecipe = :textRecipe," +
            "indexNumber = :indexNumber"+
            " WHERE id = :id")
    fun updateContentById(
        id: Long, title: String, authorName: String, recipeImage:String,
        categoryRecipe: String, textRecipe: String,indexNumber:Long
    )

    @Query(
        """
        UPDATE recipes SET
        isFavorite = CASE WHEN isFavorite THEN 0 ELSE 1 END
        WHERE id = :id
        """
    )
    fun favById(id: Long)

    @Query("DELETE FROM recipes WHERE id = :id")
    fun removeById(id: Long)

    @Query("SELECT * FROM recipes WHERE categoryRecipe = :categoryRecipe")
    fun getEuropean(categoryRecipe: String): LiveData<List<RecipeEntity>>

    @Query("SELECT * FROM recipes WHERE title LIKE '%' || :text || '%'")
    fun searchByText(text: String): LiveData<List<RecipeEntity>>

    @Query(
        "UPDATE recipes SET " +
                "indexNumber = indexNumber + 1 " +
                "WHERE id = :fromId"
    )
    fun updateListItemMoveUpFirst(fromId: Long)

    @Query(
        "UPDATE recipes SET " +
                "indexNumber = indexNumber - 1 " +
                "WHERE id = :toId"
    )
    fun updateListItemMoveUpSecond(toId: Long)

    @Transaction
    fun updateItemMoveUp(fromId: Long, toId: Long) {
        updateListItemMoveUpFirst(fromId)
        updateListItemMoveUpSecond(toId)
    }
    @Query(
        "UPDATE recipes SET " +
                "indexNumber = indexNumber - 1 " +
                "WHERE id = :fromId"
    )
    fun updateListItemMoveDownFirst(fromId: Long)

    @Query(
        "UPDATE recipes SET " +
                "indexNumber = indexNumber + 1 " +
                "WHERE id = :toId"
    )
    fun updateListItemMoveDownSecond(toId: Long)

    @Transaction
    fun updateItemMoveDown(fromId: Long, toId: Long) {
        updateListItemMoveDownFirst(fromId)
        updateListItemMoveDownSecond(toId)
    }


}
