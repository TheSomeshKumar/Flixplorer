# Flixplorer

Jetpack Compose playground project based
on [Recommended app architecture](https://developer.android.com/jetpack/guide)

Powered by

<img width="260" alt="TMDB Logo" src="https://github.com/TheSomeshKumar/Flixplorer/assets/13759258/a1bf8723-f5b7-43a7-8118-22bc1e203301">



## Features 🕹

- Follows [Guidlines on Recommended Architecture](https://developer.android.com/topic/architecture#recommended-app-arch)
- 100% Kotlin
- [Jetpack Compose](https://developer.android.com/jetpack/compose)
- [Coroutines](https://developer.android.com/kotlin/coroutines) - Coroutines provide us an easy way
  to do synchronous and asynchronous programming.
- [Flow](https://developer.android.com/kotlin/flow)
  & [StateFlow](https://developer.android.com/kotlin/flow/stateflow-and-sharedflow#stateflow) - Flow
  is a type of coroutine that emits multiple values sequentially.
- [Hilt](https://dagger.dev/hilt/) - Dependency Injection framework by Google 
- [Retrofit](https://github.com/square/retrofit) - Network client by Square
- [Material You](https://m3.material.io) and Dark theme Support - It has support for Material You & Light/Dark mode that can be toggled from preference screen
- [Paging 3](https://developer.android.com/topic/libraries/architecture/paging/v3-overview) - Pagination library for Jetpack compose 
- [DataStore](https://developer.android.com/topic/libraries/architecture/datastore) Preferences like API with coroutine & Flow support
- [Coil](https://coil-kt.github.io/coil/compose/) Image loading library for Compose
- [Detekt] (https://github.com/detekt/detekt) Code smell analysis for Kotlin projects

## Work In Progress 🚧
- ~~Animations~~✅
- UI & Unit Tests

## Screenshots 📱

<img width="260" alt="M3 Light Home" src="https://github.com/TheSomeshKumar/Flixplorer/assets/13759258/1bdeae49-4611-4c75-8a30-8a2552eb7708">
<img width="260" alt="M3 Dark Home" src="https://github.com/TheSomeshKumar/Flixplorer/assets/13759258/e47d0f99-815e-4888-8a1c-26470d41c6cc">
<img width="260" alt="M3 Dark Detail" src="https://github.com/TheSomeshKumar/Flixplorer/assets/13759258/7082b600-85ef-44bb-99f4-4a8a82a5056b"> 

### How to build on your environment

Create an API key on [The Movie DB](https://www.themoviedb.org)'s and add in your
local `local.properties` file like this

```
TMDB_KEY=<REPLACE_WITH_YOUR_API_KEY>
```
