package com.example.attractions

import android.app.Application
import com.example.attractions.di.appModule
import com.example.attractions.manager.SharePreferenceManager
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        
        // 初始化 SharePreferenceManager
        SharePreferenceManager.init(this)

        startKoin {
            androidContext(this@BaseApplication)
            modules(appModule)
        }
    }
}