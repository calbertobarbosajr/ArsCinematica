package com.calberto_barbosa_jr.arscinematica

import android.app.Application
import com.calberto_barbosa_jr.arscinematica.di.databaseModule
import com.calberto_barbosa_jr.arscinematica.di.networkModule
import com.calberto_barbosa_jr.arscinematica.di.repositoryModule
import com.calberto_barbosa_jr.arscinematica.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(
                listOf(
                    networkModule,
                    databaseModule,
                    repositoryModule,
                    viewModelModule
                )
            )
        }
    }
}