# TwoB – Android HR Application

TwoB is a modern **Android Human Resources (HR) application** designed to provide employees and managers with a centralized mobile platform for accessing company services and managing HR-related workflows.

The application supports **role-based experiences** for Employees and Managers and communicates with a RESTful HR backend.

The project was built with a strong focus on **scalability, maintainability, separation of concerns, predictable state management, and reusable components**, following modern Android development practices.

---

## 🏗 Architecture

The project follows a **Clean Architecture–inspired, feature-oriented architecture** with clear separation between presentation, business logic, and data access.

```text
┌──────────────────────────────┐
│          UI Layer            │
│      Jetpack Compose         │
└──────────────┬───────────────┘
               │
               ▼
┌──────────────────────────────┐
│        ViewModel             │
│      MVI / StateFlow         │
└──────────────┬───────────────┘
               │
               ▼
┌──────────────────────────────┐
│        Repository            │
│      Business/Data Flow      │
└──────────────┬───────────────┘
               │
        ┌──────┴──────┐
        ▼             ▼
┌──────────────┐ ┌──────────────┐
│ Remote Data  │ │ Local Data   │
│ Retrofit     │ │ DataStore    │
│ OkHttp       │ │              │
└──────────────┘ └──────────────┘
````

The architecture is designed to keep each layer focused on its own responsibility and reduce coupling between the UI and data sources.

### Feature-oriented structure

Features are organized independently, making the application easier to scale as new HR services are introduced.

```text
services/
├── profile/
├── resignation/
├── officialholidays/
├── companyassets/
└── ...
```

---

## 🔄 MVI & Unidirectional Data Flow

The application uses an **MVI-inspired architecture** with a unidirectional data flow.

Each feature maintains a single source of truth for its UI state.

```text
User Interaction
       │
       ▼
    Action
       │
       ▼
   ViewModel
       │
       ▼
   Repository
       │
       ▼
 API / DataStore
       │
       ▼
    UI State
       │
       ▼
   Compose UI
```

This approach provides:

* Predictable state transitions
* Single source of truth
* Clear separation between UI events and state
* Easier debugging
* Better control over loading, success, error, and empty states
* Reduced mutable state inside Composables

---

## 💉 Dependency Injection

The application uses **Koin** for Dependency Injection.

Dependencies are provided through dedicated Koin modules instead of being manually created inside UI or business logic.

Koin is responsible for providing components such as:

* ViewModels
* Repositories
* Retrofit APIs
* DataStore
* Authentication components
* Shared application dependencies

Example dependency graph:

```text
ViewModel
    ↓
Repository Interface
    ↓
Repository Implementation
    ↓
┌───────────────┬───────────────┐
│               │               │
▼               ▼               │
Retrofit     DataStore          │
│                               │
▼                               │
REST API                        │
```

This keeps the application loosely coupled and makes dependencies easier to replace, maintain, and test.

---

## 🌐 Networking

The networking layer is built using:

* **Retrofit**
* **OkHttp**
* **Kotlin Coroutines**
* **JWT Authentication**

The project separates API definitions from repository logic, keeping network communication isolated from the presentation layer.

### Authentication Interceptor

Authenticated requests use an OkHttp interceptor to automatically attach the current JWT Bearer token.

```text
Repository
    ↓
Retrofit
    ↓
OkHttp
    ↓
Authentication Interceptor
    ↓
Bearer Token
    ↓
HR API
```

This avoids manually passing authentication tokens throughout the application.

---

## 🔐 Authentication & Session Management

Authentication is implemented using **JWT-based authentication**.

After a successful login, the application persists the required session information and restores the user's session when the application is opened again.

The authentication flow is centralized to avoid coupling individual features to authentication implementation details.

---

## 💾 Local Persistence

**Jetpack DataStore** is used for lightweight persistent application data.

The application stores only the information required across application sessions, such as:

* Authentication token
* Employee ID
* Employee name
* Machine code
* Job name
* Profile image
* User role
* Shift ID
* Selected language

DataStore is intentionally used instead of introducing a database where relational or complex local persistence is not required.

---

## 🌍 Localization

The application supports:

* English
* Arabic

Language preferences are persisted locally and used throughout the application.

The selected culture is also synchronized with supported backend API requests where localization is required.

---

## 🎨 Jetpack Compose

The UI is fully implemented using **Jetpack Compose**.

The project follows a reusable and state-driven UI approach instead of duplicating UI logic across screens.

Common UI elements are extracted into reusable components, including:

* Application headers
* Page headers
* User avatar
* Navigation components
* Loading states
* Empty states
* Error states
* Reusable UI elements

This helps maintain visual consistency while reducing duplication and making future UI changes easier.

---

## 🖼 Image Loading

Employee images are handled using **Coil**.

The image handling layer provides graceful fallbacks for:

* `null` image URLs
* Empty image URLs
* Invalid image paths
* Image loading failures

A local placeholder image is used whenever the backend does not provide a valid image.

---

## 🧩 Separation of Models

Network DTOs are separated from the models consumed by the application.

```text
API Response
     ↓
   DTO
     ↓
Repository Mapping
     ↓
Application Model
     ↓
ViewModel State
     ↓
Compose UI
```

This prevents API-specific structures from leaking into the presentation layer and makes the application more resilient to backend changes.

---

## 🛡 Error & State Handling

The application handles common asynchronous states explicitly:

```text
Loading
Success
Error
Empty
```

Network operations are handled through a centralized result/error-handling approach, preventing API and exception handling from being duplicated across UI components.

---

## 👥 Role-Based Access

The application supports multiple user roles, primarily:

* **Employee**
* **Manager**

The authenticated user's role is used to determine which information and actions are available within the application.

This allows the same application architecture to support different user experiences without duplicating the entire application flow.

---

## 🧱 Design Principles

The project applies core software engineering principles including:

* **SOLID**
* **Separation of Concerns**
* **Single Responsibility Principle**
* **Dependency Inversion**
* **Repository Pattern**
* **Unidirectional Data Flow**
* **Single Source of Truth**
* **Composition over duplication**
* **Reusable Components**
* **Feature-oriented organization**

The goal is to keep the codebase easy to understand, maintain, test, and extend.

---

## 🛠 Technology Stack

| Technology             | Purpose                  |
| ---------------------- | ------------------------ |
| Kotlin                 | Primary language         |
| Jetpack Compose        | UI                       |
| Material 3             | UI components            |
| MVI                    | State management         |
| Coroutines             | Asynchronous programming |
| Flow / StateFlow       | Reactive state           |
| ViewModel              | Presentation layer       |
| Koin                   | Dependency Injection     |
| Retrofit               | REST API communication   |
| OkHttp                 | HTTP client              |
| JWT                    | Authentication           |
| DataStore              | Local persistence        |
| Coil                   | Image loading            |
| Navigation             | Application navigation   |
| Gson                   | JSON serialization       |
| Gradle Version Catalog | Dependency management    |

---

## 📂 Project Structure

```text
com.example.twob
│
├── components/
│
├── data/
│   ├── local/
│   │   └── datastore/
│   ├── remote/
│   │   ├── api/
│   │   ├── dto/
│   │   └── auth/
│   └── repositories/
│
├── di/
│
├── navigation/
│
├── services/
│   ├── profile/
│   ├── resignation/
│   ├── officialholidays/
│   └── ...
│
└── ui/
    └── theme/
```

---

## 🎯 Engineering Goals

TwoB was developed with a focus on building an Android codebase that is:

* **Scalable**
* **Maintainable**
* **Testable**
* **Reusable**
* **Loosely coupled**
* **Easy to extend**

The architecture allows new HR services and business workflows to be introduced without tightly coupling them to existing features or infrastructure.

---

## 👨‍💻 Author

**Moamen Abdelrhman**

Android Developer
**Kotlin • Jetpack Compose • Android Architecture**
