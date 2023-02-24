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


* **Kotlin** _ver 1.7.21_
* **Android Studio** _ver Android Studio Electric Eel | 2022.1.1_

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


* **Need to add the value for key(_DATABASE_PASS_) inside gradle.properties for Room database encryption.**
* **Need to add value for key(_API_key_) inside gradle.properties for Open weather API.**


# Unit test
* Written test cases for LoginViewModel RegisterViewModel, HomeViewModel, HistoryViewModel and UserRepository with test coverage above 90%.

![Screenshot from 2023-02-24 18-39-32](https://user-images.githubusercontent.com/126071022/221187177-af29393a-91db-4074-ba5d-de691e7d24c3.png)

# App Screens.



![Screenshot_20230224_184446](https://user-images.githubusercontent.com/126071022/221189271-ca7c2fbb-2400-4333-bcd8-770b832f184c.png)

![Screenshot_20230224_184554](https://user-images.githubusercontent.com/126071022/221189339-a32de48e-50e7-4076-89bd-9c94506991bd.png)

![Screenshot_20230224_184603](https://user-images.githubusercontent.com/126071022/221189367-b5f0076a-c1b6-4233-9f2f-ad71cfaae154.png)

![Screenshot_20230224_184654](https://user-images.githubusercontent.com/126071022/221189405-8124e60b-ae8a-4be4-9aa7-0d46b5a692ba.png)

![Screenshot_20230224_184704](https://user-images.githubusercontent.com/126071022/221189435-f52e5b91-8de3-4529-b977-ee34d457c491.png)

![Screenshot_20230224_184944](https://user-images.githubusercontent.com/126071022/221189465-4d58266e-ae2f-4957-a2fd-984bef0e5717.png)

![Screenshot_20230224_185034](https://user-images.githubusercontent.com/126071022/221189506-29180842-90cd-47c6-8804-c1afedc09506.png)

# Note
* As I am the only contributer I have put all my codes in main directory.
