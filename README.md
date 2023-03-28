# Flickophile

Playground project based
on [Recommended app architecture](https://developer.android.com/jetpack/guide)

Powered by

<img width="260" alt="TMDB Logo" src="https://user-images.githubusercontent.com/13759258/213716452-837c217d-49ac-442c-b7eb-5f1a76dff614.png">

## Features 🕹

- 100% Kotlin
- Follows [Recommended MVVM Architecture](https://developer.android.com/jetpack/guide)
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
- [Coil](https://coil-kt.github.io/coil/compose/)

## Work In Progress 🚧
- Animations
- UI & Unit Tests

## Screenshot 📱

<img width="310" alt="M3 Dark Home Screen" src="https://user-images.githubusercontent.com/13759258/217895345-e633e053-0a20-49e2-a247-dfc575a0dcc1.png"><img width="310" alt="M3 Light Home Screen" src="https://user-images.githubusercontent.com/13759258/217895353-369ad9b1-ec82-4f13-b9a9-b97ff1d12c90.png">

### How to build on your environment

Create an API key on [The Movie DB](https://www.themoviedb.org)'s and add in your
local `local.properties` file like this

```
TMDB_KEY=<REPLACE_WITH_YOUR_API_KEY>
```