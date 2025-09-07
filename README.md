# DoJo Movie 🎬🍿
### Final Project Mobile Community Solution LAB

**DoJo Movie** is a sleek and intuitive Android application built using **Kotlin** that allows users to explore and purchase their favorite films. Whether you're a movie enthusiast or just looking for your next favorite watch, DoJo Movie provides a seamless movie shopping experience right from your phone.
<br><br>

## Features
- **Secure user authentication** with login, registration, and OTP verification functionality  
- **DoJo Movie Store** to browse and purchase a curated collection of films  
- **Movie detail pages** displaying film information with adjustable purchase quantity  
- **Purchase history tracking** for managing and reviewing past transactions  
- **User profile management** to view personal information  
- Built with **Kotlin** and **SQLite** for efficient Android development  
<br><br>

## Tech Stack
![Kotlin](https://img.shields.io/badge/Kotlin-%237F52FF.svg?logo=kotlin&logoColor=white)
![SQLite](https://img.shields.io/badge/SQLite-%2307405e.svg?logo=sqlite&logoColor=white)
![Figma](https://img.shields.io/badge/Figma-F24E1E?logo=figma&logoColor=white)
<br><br>

## Hi-Fi Prototype
View the final UI design on Figma:  
> [DoJo Movies Final Prototype](https://www.figma.com/design/RGKOncHL6zTDD62CmwYhdM/DoJo-Movies?node-id=0-1&t=uqvU9Rlnq6JhNYFB-1)
<br>

## Project Structure
```
DoJoMovie/
├── .gitignore
├── .idea/
│   ├── .gitignore
│   ├── compiler.xml
│   ├── deploymentTargetSelector.xml
│   ├── gradle.xml
│   ├── kotlinc.xml
│   ├── migrations.xml
│   ├── misc.xml
│   └── vcs.xml
├── app/
│   ├── .gitignore
│   ├── build.gradle.kts
│   ├── proguard-rules.pro
│   └── src/
│       ├── androidTest/
│       │   └── java/
│       │       └── com/
│       │           └── example/
│       │               └── projectmcsdojo/
│       │                   └── ExampleInstrumentedTest.kt
│       ├── main/
│       │   ├── AndroidManifest.xml
│       │   ├── java/
│       │   │   └── com/
│       │   │       └── example/
│       │   │           └── projectmcsdojo/
│       │   │               ├── adapters/
│       │   │               │   ├── FilmListAdapter.kt
│       │   │               │   └── HistoryListAdapter.kt
│       │   │               ├── FilmDetailPage.kt
│       │   │               ├── fragments/
│       │   │               │   ├── HistoryFragment.kt
│       │   │               │   ├── HomeFragment.kt
│       │   │               │   └── ProfileFragment.kt
│       │   │               ├── HomePage.kt
│       │   │               ├── LoginPage.kt
│       │   │               ├── MainActivity.kt
│       │   │               ├── models/
│       │   │               │   ├── Film.kt
│       │   │               │   ├── History.kt
│       │   │               │   └── User.kt
│       │   │               ├── RegisterPage.kt
│       │   │               ├── util/
│       │   │               │   ├── DB.kt
│       │   │               │   └── Helper.kt
│       │   │               └── VerificationPage.kt
│       │   └── res/
│       │       ├── drawable/
│       │       │   ├── back_button.png
│       │       │   ├── button_rounded_background.xml
│       │       │   ├── edittext_rounded_background.xml
│       │       │   ├── gradient_overlay.xml
│       │       │   ├── history_color.png
│       │       │   ├── history.png
│       │       │   ├── home_color.png
│       │       │   ├── home.png
│       │       │   ├── ic_android_black_24dp.xml
│       │       │   ├── ic_launcher_background.xml
│       │       │   ├── ic_launcher_foreground.xml
│       │       │   ├── logo.png
│       │       │   ├── map2.png
│       │       │   ├── navbar_history.xml
│       │       │   ├── navbar_home.xml
│       │       │   ├── navbar_profile.xml
│       │       │   ├── profile_color.png
│       │       │   ├── profile.png
│       │       │   ├── rounded_background_grey.xml
│       │       │   └── rounded_background.xml
│       │       ├── font/
│       │       │   ├── poppins_black.ttf
│       │       │   ├── poppins_bold_italic.ttf
│       │       │   ├── poppins_bold.ttf
│       │       │   ├── poppins_extrabold.ttf
│       │       │   ├── poppins_light.ttf
│       │       │   ├── poppins_medium.ttf
│       │       │   ├── poppins_regular.ttf
│       │       │   └── poppins_semibold.ttf
│       │       ├── layout/
│       │       │   ├── activity_film_detail_page.xml
│       │       │   ├── activity_home_page.xml
│       │       │   ├── activity_login_page.xml
│       │       │   ├── activity_main.xml
│       │       │   ├── activity_register_page.xml
│       │       │   ├── activity_verification_page.xml
│       │       │   ├── fragment_history.xml
│       │       │   ├── fragment_home.xml
│       │       │   ├── fragment_profile.xml
│       │       │   ├── list_item_film.xml
│       │       │   └── list_item_history.xml
│       │       ├── menu/
│       │       │   └── navbar.xml
│       │       ├── mipmap-anydpi/
│       │       │   ├── ic_launcher_round.xml
│       │       │   └── ic_launcher.xml
│       │       ├── mipmap-hdpi/
│       │       │   ├── ic_launcher_round.webp
│       │       │   └── ic_launcher.webp
│       │       ├── mipmap-mdpi/
│       │       │   ├── ic_launcher_round.webp
│       │       │   └── ic_launcher.webp
│       │       ├── mipmap-xhdpi/
│       │       │   ├── ic_launcher_round.webp
│       │       │   └── ic_launcher.webp
│       │       ├── mipmap-xxhdpi/
│       │       │   ├── ic_launcher_round.webp
│       │       │   └── ic_launcher.webp
│       │       ├── mipmap-xxxhdpi/
│       │       │   ├── ic_launcher_round.webp
│       │       │   └── ic_launcher.webp
│       │       ├── values/
│       │       │   ├── colors.xml
│       │       │   ├── strings.xml
│       │       │   └── themes.xml
│       │       ├── values-night/
│       │       │   └── themes.xml
│       │       └── xml/
│       │           ├── backup_rules.xml
│       │           └── data_extraction_rules.xml
│       └── test/
│           └── java/
│               └── com/
│                   └── example/
│                       └── projectmcsdojo/
│                           └── ExampleUnitTest.kt
├── build.gradle.kts
├── DoJoMovie/
├── gradle/
│   ├── libs.versions.toml
│   └── wrapper/
│       ├── gradle-wrapper.jar
│       └── gradle-wrapper.properties
├── gradle.properties
├── gradlew
├── gradlew.bat
├── README.md
└── settings.gradle.kts
```
<br>

## Notes
```
DoJo Movies is a semester-long final group project developed for the Mobile Community Solution LAB course
```
