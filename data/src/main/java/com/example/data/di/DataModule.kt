package com.example.data.di

import androidx.room.Room
import com.example.data.db.AppDatabase
import com.example.data.db.MainDao
import com.example.data.network.NetworkApi
import com.example.data.repository.MainRepositoryImpl
import com.example.domain.repository.MainRepository
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val dataModule = module {
    single<NetworkApi> {
        Retrofit.Builder()
            .baseUrl("https://gitlab.65apps.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
            .create(NetworkApi::class.java)
    }

    factory<MainRepository> {
        MainRepositoryImpl(get(), get())
    }

    factory<OkHttpClient> {
        OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .build()
    }

    single<AppDatabase> {
        Room.databaseBuilder(get(), AppDatabase::class.java, "database").build()
    }

    single<MainDao> {
        val db: AppDatabase = get()
        db.mainDao()
    }
}