package com.example.recipebox.data

import com.example.recipebox.Recipe
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RecipeRepository(
    private val api: MealApi,
    private val dao: RecipeDao,
) {
    val favorites: Flow<List<Recipe>> = dao.observeAll().map { list -> list.map { it.toDomain() } }

    suspend fun search(query: String): Result<List<Recipe>> = runCatching {
        api.search(query).meals.orEmpty().map { it.toDomain() }
    }

    suspend fun toggleFavorite(recipe: Recipe) {
        if (dao.count(recipe.id) > 0) dao.delete(recipe.id) else dao.insert(recipe.toEntity())
    }
}
