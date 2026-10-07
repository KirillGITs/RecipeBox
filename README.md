# RecipeBox

A small Android app for searching recipes and saving favorites offline.
Built as a learning project with a modern Android stack.

## Features
- Search recipes by name (public [TheMealDB](https://www.themealdb.com/api.php) API, no key needed)
- Save / remove favorites, stored locally in a Room database
- Favorites available offline
- Loading, empty and error states

## Tech stack
- Kotlin, Coroutines, Flow / StateFlow
- Jetpack Compose + Material 3
- MVVM (ViewModel + repository), unidirectional data flow
- Room (local DB), Retrofit + Gson (network), Coil (images)
- Unit tests (JUnit)

## Architecture
```
data/   MealApi (Retrofit) · RecipeDao/AppDatabase (Room) · RecipeRepository
ui/     RecipeViewModel · Compose screens
```
The ViewModel exposes `StateFlow`; Compose screens render state and send events up.

## Run
Open the project in Android Studio (Koala or newer), let Gradle sync, run the `app` configuration on an emulator or device (API 26+).

## Roadmap
- [ ] Migrate shared code (data + domain) to Kotlin Multiplatform
- [ ] Compose Multiplatform UI for iOS
- [ ] Recipe details screen
- [ ] ViewModel unit tests with fake repository
