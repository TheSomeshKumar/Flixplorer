# Flickophile

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
- [Material You](https://m3.material.io) - Material Design by Google
- [Paging 3](https://developer.android.com/topic/libraries/architecture/paging/v3-overview) - Official pagination library part of the Jetpack component 
- [Coil](https://coil-kt.github.io/coil/compose/) Image loading library

## Work In Progress 🚧
- Animations
- UI & Unit Tests

## Screenshots 📱

<img width="310" alt="M3 Light Home" src="https://user-images.githubusercontent.com/13759258/228339682-fac59188-9828-4f34-822f-a993fadf347a.png"><img width="310" alt="M3 Dark Home" src="https://user-images.githubusercontent.com/13759258/228339757-03a2bf95-552c-4476-86c7-f097c2f42b76.png"><img width="310" alt="M3 Dark Detail" src="https://user-images.githubusercontent.com/13759258/228339795-46d334e8-b7c3-4bc4-9749-ff518e77ba51.png">


### How to build on your environment

Create an API key on [The Movie DB](https://www.themoviedb.org)'s and add in your
local `local.properties` file like this

```
TMDB_KEY=<REPLACE_WITH_YOUR_API_KEY>
```
