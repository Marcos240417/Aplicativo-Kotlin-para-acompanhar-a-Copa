package com.example.mycopa.data.data.di

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import com.example.mycopa.data.data.repository.MatchesRepository
import com.example.mycopa.data.data.repository.MatchesRepositoryImpl
import com.example.mycopa.data.data.source.MatchesDataSource
import com.example.mycopa.data.local.source.MatchDataSourceLocal
import com.example.mycopa.data.remote.services.MatchesServices
import com.example.mycopa.data.remote.source.MatchDataSourceRemote
import com.example.mycopa.domain.usecase.DisableNotificationUseCase
import com.example.mycopa.domain.usecase.EnableNotificationUseCase
import com.example.mycopa.domain.usecase.GetMatchesUseCase
import com.example.mycopa.featureview.features.MainViewModel
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// Extensão para o DataStore (Persistência)
private val Context.dataStore by preferencesDataStore(name = "matches_prefs")

// 1. Definição de Rede e Persistência
val networkModule = module {
    single { androidContext().dataStore }

    single {
        GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
            .create()
    }

    single {
        val logging = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
        OkHttpClient.Builder().addInterceptor(logging).build()
    }

    single<MatchesServices> {
        Retrofit.Builder()
            .baseUrl("https://digitalinnovationone.github.io/copa-2022-android/")
            .addConverterFactory(GsonConverterFactory.create(get()))
            .client(get())
            .build()
            .create(MatchesServices::class.java)
    }
}

// 2. Definição de Fontes de Dados (Data Sources)
val dataSourceModule = module {
    single<MatchesDataSource.Remote> { MatchDataSourceRemote(get()) }
    single<MatchesDataSource.Local> { MatchDataSourceLocal(get()) }
}

// 3. Definição de Repositórios e Casos de Uso
val repositoryModule = module {
    single<MatchesRepository> {
        MatchesRepositoryImpl(
            localDataSource = get(),
            remoteDataSource = get()
        )
    }

    // RESOLUÇÃO DO CRASH: Registrando os Use Cases necessários
    factory { GetMatchesUseCase(get()) }
    factory { EnableNotificationUseCase(get()) }  // Corrigido: Agora o Koin encontra este tipo
    factory { DisableNotificationUseCase(get()) } // Corrigido: Resolve o erro do Logcat
}

// 4. Definição de ViewModels
val viewModelModule = module {
    // Garanta que os parâmetros (get(), get(), get()) correspondam ao construtor da MainViewModel
    viewModel { MainViewModel(get(), get(), get()) }
}

// Lista unificada para o startKoin na sua Application class
val appModule = listOf(
    networkModule,
    dataSourceModule,
    repositoryModule,
    viewModelModule
)