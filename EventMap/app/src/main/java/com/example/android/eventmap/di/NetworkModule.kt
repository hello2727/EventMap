package com.example.android.eventmap.di

import android.content.Context
import com.example.android.eventmap.MapApplication
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Created by Jihye Noh
 * Date: 2022-01-26
 */
@Module
@Suppress("unused")
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context): MapApplication {
        return app as MapApplication
    }

    @Singleton
    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder().baseUrl(Constants.EVENT_URL).client(client)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(
        headerInterceptor: Interceptor,
        cache: Cache
    ): OkHttpClient {
        val okHttpClientBuilder = OkHttpClient().newBuilder()
        okHttpClientBuilder.apply {
            connectTimeout(CONNECTION_TIMEOUT.toLong(), TimeUnit.SECONDS)
            readTimeout(READ_TIMEOUT.toLong(), TimeUnit.SECONDS)
            writeTimeout(WRITE_TIMEOUT.toLong(), TimeUnit.SECONDS)
            cache(cache)
            addInterceptor(headerInterceptor)
        }

        return okHttpClientBuilder.build()
    }

    @Singleton
    @Provides
    fun provideHeaderInterceptor(): Interceptor {
        return Interceptor {
            val requestBuilder = it.request().newBuilder()

            // TODO: 필요에 따라 requestBuilder.addHeader(name ,  value) 작성

            it.proceed(requestBuilder.build())
        }
    }

    companion object {
        private const val READ_TIMEOUT = 30
        private const val WRITE_TIMEOUT = 30
        private const val CONNECTION_TIMEOUT = 10
        private const val CACHE_SIZE_BYTES = 10 * 1024 * 1024L // 10MB
    }
}