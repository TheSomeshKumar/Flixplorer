# Flixplorer

Jetpack Compose playground project based
on [Recommended app architecture](https://developer.android.com/jetpack/guide)

Powered by

<img width="260" alt="TMDB Logo" src="https://user-images.githubusercontent.com/13759258/213716452-837c217d-49ac-442c-b7eb-5f1a76dff614.png">

## Features 🕹

- Follows [Guidlines on Recommended Architecture](https://developer.android.com/jetpack/guide)
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

## Work In Progress 🚧
- ~~Animations~~✅
- UI & Unit Tests

## Screenshots 📱

<img width="260" alt="M3 Light Home" src="https://user-images.githubusercontent.com/13759258/236567050-9500cea2-d946-4c02-9ad6-cdcc10649d80.png"><img width="260" alt="M3 Dark Home" src="https://user-images.githubusercontent.com/13759258/236567103-eedd9dba-7d3a-44ed-8914-76c5d9060735.png"><img width="260" alt="M3 Dark Detail" src="https://user-images.githubusercontent.com/13759258/236567137-653bec70-df46-4010-ad68-692f0825048a.png"> 

### How to build on your environment

Create an API key on [The Movie DB](https://www.themoviedb.org)'s and add in your
local `local.properties` file like this

```
TMDB_KEY=<REPLACE_WITH_YOUR_API_KEY>
```
