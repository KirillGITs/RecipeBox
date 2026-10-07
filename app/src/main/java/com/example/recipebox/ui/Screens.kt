package com.example.recipebox.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.recipebox.Recipe

@Composable
fun SearchScreen(
    state: SearchState,
    favoriteIds: Set<String>,
    onQueryChange: (String) -> Unit,
    onSearch: () -> Unit,
    onToggleFavorite: (Recipe) -> Unit,
) {
    Column(Modifier.fillMaxSize().padding(16.dp)) {
        OutlinedTextField(
            value = state.query,
            onValueChange = onQueryChange,
            label = { Text("Search recipes (e.g. pasta)") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = { onSearch() }),
            modifier = Modifier.fillMaxWidth(),
        )
        Box(Modifier.fillMaxSize().padding(top = 12.dp), contentAlignment = Alignment.Center) {
            when {
                state.loading -> CircularProgressIndicator()
                state.error != null -> Text(state.error, color = MaterialTheme.colorScheme.error)
                state.searched && state.results.isEmpty() -> Text("No recipes found.")
                state.results.isEmpty() -> Text("Type a dish name and press search.")
                else -> RecipeList(state.results, favoriteIds, onToggleFavorite)
            }
        }
    }
}

@Composable
fun FavoritesScreen(
    favorites: List<Recipe>,
    onToggleFavorite: (Recipe) -> Unit,
) {
    Box(Modifier.fillMaxSize().padding(16.dp), contentAlignment = Alignment.Center) {
        if (favorites.isEmpty()) {
            Text("No favorites yet. Tap the heart on a recipe to save it.")
        } else {
            RecipeList(favorites, favorites.map { it.id }.toSet(), onToggleFavorite)
        }
    }
}

@Composable
private fun RecipeList(
    recipes: List<Recipe>,
    favoriteIds: Set<String>,
    onToggleFavorite: (Recipe) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        items(recipes, key = { it.id }) { recipe ->
            RecipeCard(recipe, recipe.id in favoriteIds, onToggleFavorite)
        }
    }
}

@Composable
private fun RecipeCard(
    recipe: Recipe,
    isFavorite: Boolean,
    onToggleFavorite: (Recipe) -> Unit,
) {
    Card(Modifier.fillMaxWidth()) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                model = recipe.imageUrl,
                contentDescription = recipe.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(96.dp),
            )
            Column(Modifier.weight(1f).padding(12.dp)) {
                Text(recipe.name, style = MaterialTheme.typography.titleMedium)
                Text("${recipe.category} • ${recipe.area}", style = MaterialTheme.typography.bodySmall)
            }
            IconButton(onClick = { onToggleFavorite(recipe) }) {
                Icon(
                    imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = if (isFavorite) "Remove from favorites" else "Add to favorites",
                )
            }
        }
    }
}
