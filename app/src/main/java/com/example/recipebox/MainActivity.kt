package com.example.recipebox

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.recipebox.ui.FavoritesScreen
import com.example.recipebox.ui.RecipeViewModel
import com.example.recipebox.ui.SearchScreen

class MainActivity : ComponentActivity() {

    private val viewModel: RecipeViewModel by viewModels {
        RecipeViewModel.Factory((application as RecipeApp).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                val state by viewModel.state.collectAsStateWithLifecycle()
                val favorites by viewModel.favorites.collectAsStateWithLifecycle()
                var tab by remember { mutableIntStateOf(0) }

                Scaffold(
                    bottomBar = {
                        NavigationBar {
                            NavigationBarItem(
                                selected = tab == 0,
                                onClick = { tab = 0 },
                                icon = { Icon(Icons.Default.Search, contentDescription = null) },
                                label = { Text("Search") },
                            )
                            NavigationBarItem(
                                selected = tab == 1,
                                onClick = { tab = 1 },
                                icon = { Icon(Icons.Default.Favorite, contentDescription = null) },
                                label = { Text("Favorites") },
                            )
                        }
                    },
                ) { padding ->
                    val modifier = Modifier.padding(padding)
                    androidx.compose.foundation.layout.Box(modifier) {
                        if (tab == 0) {
                            SearchScreen(
                                state = state,
                                favoriteIds = favorites.map { it.id }.toSet(),
                                onQueryChange = viewModel::onQueryChange,
                                onSearch = viewModel::search,
                                onToggleFavorite = viewModel::toggleFavorite,
                            )
                        } else {
                            FavoritesScreen(favorites, viewModel::toggleFavorite)
                        }
                    }
                }
            }
        }
    }
}
