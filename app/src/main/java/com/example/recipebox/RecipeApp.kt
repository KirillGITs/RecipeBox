package com.example.recipebox

import android.app.Application
import androidx.room.Room
import com.example.recipebox.data.AppDatabase
import com.example.recipebox.data.MealApi
import com.example.recipebox.data.RecipeRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/** Simple manual dependency container (no DI framework, to keep the project easy to read). */
class RecipeApp : Application() {

    val repository: RecipeRepository by lazy {
        val api = Retrofit.Builder()
            .baseUrl("https://www.themealdb.com/api/json/v1/1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MealApi::class.java)
        val db = Room.databaseBuilder(this, AppDatabase::class.java, "recipes.db").build()
        RecipeRepository(api, db.recipeDao())
    }
}
