# Application Description

* User can register and login to the application.
* Once the user gets logged in there is two tabs in home screen.
* Home screen tab shows user's current location and weather details.
* History screen tab shows user's location fetched history.

# Open Source Library

* [Retrofit](https://square.github.io/retrofit/)
* [Glide](https://github.com/bumptech/glide)
* [Kotlin-Lifecycle](https://developer.android.com/jetpack/androidx/releases/lifecycle)
* [Room Database](https://developer.android.com/training/data-storage/room)
* [Coroutine](https://kotlinlang.org/docs/coroutines-overview.html)
* [Data binding](https://developer.android.com/topic/libraries/data-binding)

# Tech Stack ✨

* Room DataBase
* RecyclerView
* MVVM
* Data Binding
* Kotlin Lifecycle
* Kotlin Coroutines
* Hilt


* Kotlin _ver 1.7.21_
* Android Studio _ver Android Studio Electric Eel | 2022.1.1_

# Project Structure

**Common**
We have all the Utils, binding adapters and commonly used classes.

**Data**
Here we are having room database and the entities used inside our application.
We are having Repository Implementation classes over here.

**DI**
We having all the Dependency Injection implementations.

**Domain**
This layer is separating the data layer and the viewmodel.
Here we are having Repository class.

**View**
Having all the views along with there viewmodel inside the package.

# Security

* Room database encryption using SQLCipher.
* Shared Preference encryption using `androidx.security`


* Need to add the value for key(_DATABASE_PASS_) inside gradle.properties for Room database
  encryption.
