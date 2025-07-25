# 📦 KList Assignment Demo

A modular Jetpack Compose list builder built with Material 3 — supports headers, dividers, and dynamic item rendering. Designed for fast prototyping and clean architecture.

---

## 🚀 How to Use

### 🛠️ Step 1: Add JitPack to your build

#### In `settings.gradle.kts`:

```kotlin
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}
```

---

### 📦 Step 2: Add the KList dependency

In your `build.gradle.kts` (app level):

```kotlin
dependencies {
    implementation("com.github.Sived-Codes:KList-Assignment:<Tag>")
}
```

> Replace `<Tag>` with the latest release tag, e.g. `v1.0.0`

---

## 🧪 Example Usage

```kotlin
KList
    .padding(16.dp)
    .header("Top Gainers")
    .items(coinList) { coin ->
        KListItem(coin = coin, onClick = { selectedCoin ->
            println("Clicked: ${selectedCoin.name}")
        })
    }
    .withDividers(Color.Gray.copy(alpha = 0.2f))
    .render()
```

---

## 📋 Features

- ✅ Easy to use with Jetpack Compose
- ✅ Material3 design system
- ✅ Supports headers and dividers
- ✅ Clean and fluent builder syntax
- ✅ Clickable item support

---

## 🖼️ Preview

<img src="https://github.com/Sived-Codes/KList-Assignment/blob/main/drawables/img.png" width="250" />

> _Add screenshots or screen recordings here to show how it looks in the app._

---

## 🔗 Share This Release

You can share the library using:

```
https://jitpack.io/#Sived-Codes/KList-Assignment
```

---

## 👨‍💻 Author

**Sived Deshmukh**  
GitHub: [@Sived-Codes](https://github.com/Sived-Codes)

---

## 📝 License

This project is open source and available under the [MIT License](LICENSE).
