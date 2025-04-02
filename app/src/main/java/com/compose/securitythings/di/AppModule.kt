package com.compose.securitythings.di

import com.compose.securitythings.data.interceptor.ApiInterceptor
import com.compose.securitythings.data.network.ApiService
import com.compose.securitythings.data.repo.CatsRepoImpl
import com.compose.securitythings.data.repo.CatsRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun providesRetrofit(
        client: OkHttpClient
    ): ApiService = Retrofit.Builder()
            .baseUrl("https://api.thecatapi.com/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)


    @Provides
    @Singleton
    fun provideOkHttp(interceptor: ApiInterceptor, loggingInterceptor: HttpLoggingInterceptor):OkHttpClient=OkHttpClient
        .Builder()
        .addInterceptor(interceptor)
        .addInterceptor(loggingInterceptor)
        .writeTimeout(30,TimeUnit.SECONDS)
        .connectTimeout(30,TimeUnit.SECONDS)
        .readTimeout(30,TimeUnit.SECONDS)
        .build()

    @Provides
    @Singleton
    fun provideLoginInterceptor():HttpLoggingInterceptor{
        return HttpLoggingInterceptor().apply {
            level=HttpLoggingInterceptor.Level.BODY
        }
    }



}

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule{
    @Binds
    @Singleton
    abstract fun providesRepository(repositoryImpl: CatsRepoImpl): CatsRepository
}