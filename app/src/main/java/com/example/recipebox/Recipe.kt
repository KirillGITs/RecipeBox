package com.example.recipebox

/** Domain model used by the UI layer. */
data class Recipe(
    val id: String,
    val name: String,
    val category: String,
    val area: String,
    val imageUrl: String,
)
