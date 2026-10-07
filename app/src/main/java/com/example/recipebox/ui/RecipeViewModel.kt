package com.example.recipebox.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.recipebox.Recipe
import com.example.recipebox.data.RecipeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class SearchState(
    val query: String = "",
    val loading: Boolean = false,
    val results: List<Recipe> = emptyList(),
    val error: String? = null,
    val searched: Boolean = false,
)

class RecipeViewModel(private val repository: RecipeRepository) : ViewModel() {

    private val _state = MutableStateFlow(SearchState())
    val state: StateFlow<SearchState> = _state.asStateFlow()

    val favorites: StateFlow<List<Recipe>> = repository.favorites
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    fun onQueryChange(query: String) = _state.update { it.copy(query = query) }

    fun search() {
        val query = _state.value.query.trim()
        if (query.isEmpty()) return
        viewModelScope.launch {
            _state.update { it.copy(loading = true, error = null) }
            repository.search(query)
                .onSuccess { list ->
                    _state.update { it.copy(loading = false, results = list, searched = true) }
                }
                .onFailure {
                    _state.update { it.copy(loading = false, error = "Could not load recipes. Check your connection.") }
                }
        }
    }

    fun toggleFavorite(recipe: Recipe) {
        viewModelScope.launch { repository.toggleFavorite(recipe) }
    }

    class Factory(private val repository: RecipeRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T = RecipeViewModel(repository) as T
    }
}
