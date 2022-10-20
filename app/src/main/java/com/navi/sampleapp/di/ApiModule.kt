package com.navi.sampleapp.di

import android.content.Context
import com.navi.sampleapp.model.PullRequestApi
import com.navi.sampleapp.model.PullRequestService
import com.navi.sampleapp.util.NetworkConnectionInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    private val BASE_URL = "https://api.github.com/"

    @Provides
    fun provideOkHttpClient(
        @ApplicationContext context: Context,
    ): OkHttpClient =
        OkHttpClient.Builder().apply {
            interceptors().add(NetworkConnectionInterceptor(context))
        }.build()

    @Provides
    fun provideRequestApi(okHttpClient: OkHttpClient): PullRequestApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(PullRequestApi::class.java)
    }

    @Provides
    fun providePullRequestService(pullRequestApi: PullRequestApi): PullRequestService {
        return PullRequestService(pullRequestApi)
    }
}