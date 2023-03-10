# ComposeRecorder
> <b>Author: Nicola De Nicolais</b>

## 📍 Description
Android application built with Kotlin and Jetpack Compose that shows how to record the input voice and save it in .mp3 files.

## ⚡ Structure
### Jetpack Compose
#### Navigation

Navigation refers to the interactions that allow users to navigate across, into, and back out from the different pieces of content within your app. Android Jetpack's Navigation component helps you implement navigation, from simple button clicks to more complex patterns, such as app bars and the navigation drawer.


## 🛠️ Package Structure

```
com.denicks21.recorder          # Root Package
│ 
├── navigation                  # Navigation folder
│   ├── NavGraph                # Contains all of app destinations and actions.
│   └── NavScreens              # Contains a sealed class with object corresponds to a screen and its routes.
|
├── play                        # AudioPlayer folder
│   ├── AudioInterface          # Interface of audio player.
|   ├── AudioPlayer             # Audio player.
|
├── record                      # RecorderPlayer folder
│   ├── RecorderInterface       # Interface of recorder player.
|   ├── RecorderPlayer          # Recorder player.
|
├── screen                      # App screens folder
|   │   ├── HomePage            # Main page of the app.
|   │   ├── InfoPage            # Page containing information about the app and developer profile.
|   │   ├── IntroPage           # Splashscreen of the app.
│
├── ui                          # UI resources folder
│   ├── composables             # Composable components folder
|   │   ├── CustomBackPress     # Component that control and prevent back button action.
|   │   ├── CustomDrawer        # Navigation drawer menu with app screens.
|   │   ├── CustomTopBar        # Bar that represent the app name and drawer menu.
|
├── theme                       # Theme components folder
|   │   ├── Color               # Color palette used by the app.
|   │   ├── Shape               # Components shapes of Compose used by the app.
|   │   ├── Theme               # Theme used by the app.
|   │   ├── Type                # Typography styles for the fonts used by the app.
|
├── MainActivity                # Main activity
```

## 📎 Screenshots
<p float="left">
<img height="500em" src="screenshots/Screenshot01.png" title="Recorder's screen preview">
