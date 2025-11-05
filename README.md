# 🎬 TMDb App – Catálogo de Filmes com Jetpack Compose

*Aplicativo Android moderno desenvolvido com Kotlin, Jetpack Compose, Retrofit e Koin*

[![Kotlin](https://img.shields.io/badge/Kotlin-2.0.21-7F52FF?logo=kotlin&logoColor=white)](https://kotlinlang.org/)  
[![Jetpack Compose](https://img.shields.io/badge/Jetpack_Compose-1.6.2-4285F4?logo=android&logoColor=white)](https://developer.android.com/jetpack/compose)  
[![TMDb API](https://img.shields.io/badge/TMDb_API-Enabled-01B4E4?logo=themoviedatabase&logoColor=white)](https://www.themoviedb.org/)  
[![Koin](https://img.shields.io/badge/Koin-DI_Framework-FF6F00?logo=kotlin&logoColor=white)](https://insert-koin.io/)  
[![License](https://img.shields.io/badge/License-MIT-green.svg)](https://opensource.org/licenses/MIT)

---

## 📱 Preview

![App Preview](https://github.com/yourusername/yourproject/assets/mockup_tmdb_app.png)

*Interface moderna construída com Jetpack Compose — listagem de filmes, tela de detalhes e trailers integrados.*

---

## 🧠 Sobre o Projeto

O **TMDb App** é um aplicativo Android moderno que consome a API do **The Movie Database (TMDb)** para exibir filmes populares, trailers e informações detalhadas.  
O projeto foi desenvolvido com foco em **boas práticas de arquitetura, desempenho e escalabilidade**, utilizando as principais bibliotecas do ecossistema Android.

---

## 🏗️ Arquitetura

O projeto segue o padrão **MVVM (Model–View–ViewModel)** combinado com o **Repository Pattern**, garantindo:

- Separação clara de responsabilidades  
- Código mais limpo e de fácil manutenção  
- Facilidade para escrever testes unitários  

| Camada      | Responsabilidade                                  | Tecnologias                                    |
|-------------|--------------------------------------------------|------------------------------------------------|
| **Model**   | Representa os dados da API e do banco local      | `data class`, Room, Retrofit                   |
| **Repository** | Media o acesso entre API e banco local          | `MoviesRepository`, `FavoriteMovieDao`        |
| **ViewModel** | Controla a lógica e estados da UI                | `MoviesViewModel`, `FavoritesViewModel`       |
| **View (UI)** | Exibição das telas                               | Jetpack Compose                               |

---

## 🧩 Principais Tecnologias e Conceitos Utilizados

### 🎨 Jetpack Compose  
- UI declarativa e moderna do Android  
- Uso de `LazyColumn`, `remember`, `LaunchedEffect` e `State`  
- Componentes customizados com animações, como o **FavoriteButton**  

### 🌐 Retrofit + Coroutines  
- Consumo da API do TMDb com `suspend fun`  
- Chamadas assíncronas otimizadas com `async/await`  
- Conversão automática de JSON com `GsonConverterFactory`  

### 💾 Room + Flow  
- Persistência local dos filmes favoritos  
- Uso de `Flow<List<FavoriteMovie>>` para atualização reativa automática  
- Quando o usuário adiciona ou remove um favorito, a UI atualiza imediatamente  

### 📜 Paging 3  
- Paginação eficiente de filmes populares  
- Carregamento incremental enquanto o usuário rola a lista  
- Integração nativa com Compose via `collectAsLazyPagingItems()`  

### 🧠 Koin (Injeção de Dependência)  
- Gerencia dependências como `Retrofit`, `Room` e `ViewModels`  
- Evita acoplamento entre classes e facilita testes  
- Módulos bem definidos:  
  - `networkModule` → fornece Retrofit e `TMDbApi`  
  - `databaseModule` → fornece Room e DAO  
  - `repositoryModule` → fornece `MoviesRepository`  
  - `viewModelModule` → fornece os ViewModels  

### ⚡ Kotlin Coroutines + Flow  
- Processamento assíncrono eficiente e não bloqueante  
- Uso de:  
  - `LaunchedEffect` → chamadas assíncronas em Compose  
  - `viewModelScope.launch` → operações com Room  
  - `async/await` → requisições paralelas à API  

---

## 🌟 Principais Funcionalidades

- ✅ Listagem de filmes populares com paginação (Paging 3)  
- ✅ Tela de detalhes com sinopse, nota e trailer (YouTube Player integrado)  
- ✅ Botão de “Favoritar” com animação e persistência local (Room + Flow)  
- ✅ Botão de “Compartilhar trailer”  
- ✅ Tela de “Favoritos” com atualização em tempo real  
- ✅ Injeção de dependência completa com Koin  
- ✅ Consumo de API otimizado com Retrofit e Coroutines  

---

## 🧩 Estrutura do Projeto

```text
com.seuprojeto
│
├── data
│   ├── local
│   │   ├── AppDatabase.kt
│   │   ├── FavoriteMovieDao.kt
│   │   └── FavoriteMovie.kt
│   ├── remote
│   │   ├── TMDbApi.kt
│   │   ├── RetrofitClient.kt
│   │   └── MoviesPagingSource.kt
│   ├── repository
│   │   └── MoviesRepository.kt
│   └── model
│       ├── Movie.kt
│       ├── MovieResponse.kt
│       ├── Video.kt
│       ├── VideoResponse.kt
│       └── MovieDetails.kt
│
├── di
│   └── AppModule.kt
│
├── ui
│   ├── movies
│   │   ├── MovieListScreen.kt
│   │   └── MoviesViewModel.kt
│   ├── details
│   │   ├── MovieDetailsScreen.kt
│   │   └── MovieDetailsViewModel.kt
│   ├── favorites
│   │   ├── FavoritesScreen.kt
│   │   └── FavoritesViewModel.kt
│   └── components
│       └── YouTubeTrailerPlayer.kt
│
├── MainActivity.kt
└── App.kt
```

## 🔑 Configuração da API Key

No arquivo `local.properties`, adicione:

###

```properties
TMDB_API_KEY=sua_chave_aqui
```

###

Obtenha uma chave gratuita em:
https://developer.themoviedb.org

No arquivo build.gradle.kts (Módulo):

###

import java.util.Properties

defaultConfig {
    val localProperties = Properties()
    val localPropertiesFile = rootProject.file("local.properties")
    if (localPropertiesFile.exists()) {
        localProperties.load(localPropertiesFile.inputStream())
    }
    val tmdbApiKey = localProperties.getProperty("TMDB_API_KEY") ?: ""
    buildConfigField("String", "TMDB_API_KEY", "\"$tmdbApiKey\"")
}

buildFeatures {
    buildConfig = true
}

###

📦 Dependências Principais

###

// Retrofit
implementation("com.squareup.retrofit2:retrofit:2.9.0")
implementation("com.squareup.retrofit2:converter-gson:2.9.0")
implementation("com.google.code.gson:gson:2.8.8")

// Coil (carregamento de imagens)
implementation("io.coil-kt:coil-compose:2.6.0")

// YouTube Player
implementation("com.pierfrancescosoffritti.androidyoutubeplayer:core:12.1.0")

// Navegação
implementation("androidx.navigation:navigation-ui-ktx:2.7.7")
implementation("androidx.navigation:navigation-compose:2.7.7")

// Ícones
implementation("androidx.compose.material:material-icons-extended:1.6.2")

// ROOM
val roomVersion = "2.6.1"
implementation("androidx.room:room-runtime:$roomVersion")
ksp("androidx.room:room-compiler:$roomVersion")
implementation("androidx.room:room-ktx:$roomVersion")

// Paging 3
implementation("androidx.paging:paging-runtime:3.3.0")
implementation("androidx.paging:paging-compose:3.3.0")

// Koin
implementation("io.insert-koin:koin-android:3.5.0")
implementation("io.insert-koin:koin-androidx-compose:3.5.0")

###

🧰 Ambiente de Desenvolvimento

Android Studio: Narwhal 4 Feature Drop (2025.1.4)
Compile SDK: 35
Linguagem: Kotlin
Arquitetura: MVVM + Repository Pattern

###

📝 Licença

Distribuído sob a licença MIT. Consulte o arquivo LICENSE para mais informações.
