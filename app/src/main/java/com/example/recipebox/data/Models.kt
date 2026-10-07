package com.example.recipebox.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.recipebox.Recipe

// --- Network DTOs (TheMealDB API) ---
data class MealsResponse(val meals: List<MealDto>?)

data class MealDto(
    val idMeal: String,
    val strMeal: String,
    val strCategory: String?,
    val strArea: String?,
    val strMealThumb: String?,
)

// --- Room entity ---
@Entity(tableName = "recipes")
data class RecipeEntity(
    @PrimaryKey val id: String,
    val name: String,
    val category: String,
    val area: String,
    val imageUrl: String,
)

// --- Mappers ---
fun MealDto.toDomain() = Recipe(
    id = idMeal,
    name = strMeal,
    category = strCategory.orEmpty(),
    area = strArea.orEmpty(),
    imageUrl = strMealThumb.orEmpty(),
)

fun RecipeEntity.toDomain() = Recipe(id, name, category, area, imageUrl)

fun Recipe.toEntity() = RecipeEntity(id, name, category, area, imageUrl)
