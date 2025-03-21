package com.example.attractions.di

import com.example.attractions.repository.IRepository
import com.example.attractions.repository.network.Client
import com.example.attractions.repository.network.NetworkRepository
import com.example.attractions.repository.network.interceptor.LanguageInterceptor
import com.example.attractions.ui.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { LanguageInterceptor() }
    single { Client(get()) }
    single<IRepository> { NetworkRepository(get()) }

    // ViewModel
    viewModel { HomeViewModel(repository = get()) }
} 