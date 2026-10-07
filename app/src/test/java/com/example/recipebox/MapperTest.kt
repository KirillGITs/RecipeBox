package com.example.recipebox

import com.example.recipebox.data.MealDto
import com.example.recipebox.data.toDomain
import org.junit.Assert.assertEquals
import org.junit.Test

class MapperTest {

    @Test
    fun `dto is mapped to domain model`() {
        val dto = MealDto("1", "Carbonara", "Pasta", "Italian", "http://img")
        val recipe = dto.toDomain()
        assertEquals(Recipe("1", "Carbonara", "Pasta", "Italian", "http://img"), recipe)
    }

    @Test
    fun `null fields become empty strings`() {
        val dto = MealDto("2", "Soup", null, null, null)
        val recipe = dto.toDomain()
        assertEquals("", recipe.category)
        assertEquals("", recipe.imageUrl)
    }
}
