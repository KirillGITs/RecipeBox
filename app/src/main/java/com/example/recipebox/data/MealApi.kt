package com.example.recipebox.data

import retrofit2.http.GET
import retrofit2.http.Query

interface MealApi {
    @GET("search.php")
    suspend fun search(@Query("s") query: String): MealsResponse
}
