# Crypto.com assignment

## Project Structure

Clean Architecture + MVVM

**Data Layer:**

- Repository implementations
- Network request(Fake)
- Database operations

**Domain Layer:**

- UseCases: Business logic
- Repository interfaces
- Models

**Presentation Layer:**

- ViewModels: UI<->data linking logic
- View: Fragment, Activity, Adapters...

## Libraries

Dependency Injection: Koin
Database: Room SQLite
Asynchronous Tasks: Coroutines
UI: ViewBinding, View based UI,
Testing: JUnit, Mockk, Truth, Turbine

## Testing

**Test Coverage:**
Highlights:

- ViewModels 100% classes coverage
- Repositories 100% classes coverage
- UseCases: 100% classes coverage

Details:
![img.png](img.png)

## Video

[Screen_recording_20240421.webm](Screen_recording_20240421.webm)

## APK

[app-debug.apk](app-debug.apk)