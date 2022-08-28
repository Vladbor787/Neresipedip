package ru.netology.nerecipebet.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.netology.nerecipebet.dto.RecipeEntity
import ru.netology.nerecipebet.dao.RecipeDao

@Database(
    entities = [RecipeEntity::class],
    version = 1, exportSchema = false
)
abstract class AppDb : RoomDatabase() {
    abstract val recipeDao: RecipeDao

    companion object {
        @Volatile
        private var instance: AppDb? = null

        fun getInstance(context: Context): AppDb {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context, AppDb::class.java, "appNeRecipe.db"
        )
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries().build()
    }
}