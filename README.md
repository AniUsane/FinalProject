<p align="center">
  <img src="https://styles.redditmedia.com/t5_6xu691/styles/communityIcon_lgr0ycdkte0d1.png" alt="Logo" width="200" height="200">
</p>
<br />
<div align="center">
  <h3 align="center"> Wanderlog-Inspired Travel App</h3>

  <p align="center">
    A modern Android application which helps you plan trips across the world and makes your travel experience easier.
    <br />
    <a href="#about-the-project"><strong>Explore the docs »</strong></a>
    <br />
    <br />
    <a href="#">View Demo</a>
    ·
    <a href="https://github.com/your_username/your_repo/issues">Report Bug</a>
    ·
    <a href="https://github.com/your_username/your_repo/issues">Request Feature</a>
  </p>
</div>

---

## 📖 Table of Contents

- [About The Project](#about-the-project)
- [Built With](#built-with)
- [Features](#features)
- [Architecture](#architecture)
- [Installation](#installation)
- [Usage](#usage)
- [Roadmap](#roadmap)
- [Contributing](#contributing)
- [License](#license)
- [Contact](#contact)
- [Acknowledgments](#acknowledgments)

---

## 🧭 About The Project

This app is a simplified version of a travel planner inspired by Wanderlog, built as a final project to demonstrate modern Android development practices. It includes:

- ✅ Login & Registration flows
- 🔒 Field-level validations
- 💾 Session management via DataStore
- 🎨 Fully built with Jetpack Compose
- 🧠 Uses MVI pattern for state management
- ⚙️ Structured using Clean Architecture
- 🌐 Backend powered by [MockAPI](https://mockapi.io/)
- 🧪 Ready for testing and scaling

---

## 🛠 Built With

- [Kotlin](https://kotlinlang.org/)
- [Jetpack Compose](https://developer.android.com/jetpack/compose)
- [Dagger Hilt](https://dagger.dev/hilt/)
- [Retrofit](https://square.github.io/retrofit/)
- [Kotlinx Serialization](https://github.com/Kotlin/kotlinx.serialization)
- [Android DataStore](https://developer.android.com/topic/libraries/architecture/datastore)
- [MockAPI](https://mockapi.io/)
- [JUnit5](https://junit.org/junit5/)
- [MockK](https://mockk.io/)

---

## 🚀 Features

- 📝 User registration and login
- 📧 Email and password validation with Regex
- 🧠 MVI architecture: ViewModel manages state, event, and effect
- 🗃️ Session is saved locally via DataStore (token, userId)
- 🎨 Clean, reactive UI with Jetpack Compose
- 📡 All network calls simulated via MockAPI
- ✨ Navigation with effects instead of direct coupling

---

## 🧱 Architecture

This project uses **Clean Architecture** + **MVI** pattern. It has 3 layers: data, domain and presentation. 


---

## 🧩 Installation

Follow these steps to set up the project locally:

1. **Clone the repository**

  ```bash
  git clone https://github.com/AniUsane/FinalProject
```
2. **Open the project**
   
```
Launch **Android Studio**,
then select **"Open an existing project"**
and navigate to the cloned folder.
```
3. **Sync Gradle**

```
Android Studio will prompt you to sync the Gradle files.
Click "Sync Now" to install all dependencies.
  ```
4. **Configure MockAPI (Optional)**
 ```
 If you're using your own MockAPI endpoints:
 - Open `AuthService.kt`
 Replace the default base URL with your MockAPI base URL
 Update any endpoint paths as needed
 ``` 
5. **Run the project**
```
Select an emulator or a connected physical device,
then click the Run ▶️ button in Android Studio.
  ```
6. ** You’re all set!**
  ```
The app should now launch on your device with
the full authentication flow enabled.
```

## ⚙️ Usage

Use this application if you are planning to go on a trip. It currently supports 
```
- 🔐 User registration and login
- 📧 Email and password validation
- 💾 Session management using DataStore
- 🧪 Simulated backend with MockAPI
- 🗺️ Planning and organizing trips
- 🏨 Booking hotels for trips
- ✍️ Creating guides for visited places
- 🌍 Multilingual support: Georgian 🇬🇪 and English 🇬🇧
```
Planned features include trip planning, hotel booking, and user reviews.

---

## 🤝 Contributing

Contributions are welcome! Follow these steps:

Fork the repository

Create your feature branch (git checkout -b feature/NewFeature)

Commit your changes (git commit -m 'Add some NewFeature')

Push to the branch (git push origin feature/NewFeature)

Open a pull request

---

## 📬 Contact

**Ani Usanetashvili**  
**Nini Toliashvili**

---
