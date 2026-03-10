package com.example.urlshortenerapp.di

import com.example.urlshortenerapp.core.HttpClientFactory
import com.example.urlshortenerapp.data.repository.UrlShortenerRepository
import com.example.urlshortenerapp.data.repository.UrlShortenerRepositoryImpl
import com.example.urlshortenerapp.data.service.UrlShortenerService
import com.example.urlshortenerapp.data.service.UrlShortenerServiceImpl
import com.example.urlshortenerapp.ui.MainViewModel
import io.ktor.client.HttpClient
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module

object MainAppDI {

    val modules = module {
        single<HttpClient> { HttpClientFactory().create() }

        factoryOf(::UrlShortenerServiceImpl) bind UrlShortenerService::class
        factoryOf(::UrlShortenerRepositoryImpl) bind UrlShortenerRepository::class
        viewModel { MainViewModel(repository = get()) }
    }
}