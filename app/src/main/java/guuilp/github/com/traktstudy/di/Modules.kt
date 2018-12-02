package guuilp.github.com.traktstudy.di

import androidx.room.Room
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import guuilp.github.com.traktstudy.data.TraktRepository
import guuilp.github.com.traktstudy.data.local.TraktDAO
import guuilp.github.com.traktstudy.data.local.TraktDatabase
import guuilp.github.com.traktstudy.data.local.TraktLocalDataSource
import guuilp.github.com.traktstudy.data.remote.TraktApiService
import guuilp.github.com.traktstudy.data.remote.TraktRemoteDataSource
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

val appModule = module {
    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl("api.trakt.tv")
            .addConverterFactory(get())
            .client(get())
            .build()
    }

    single { createWebService<TraktApiService>(get(), "api.trakt.tv") }

    single {
        createOkHttpClient()
    }

    single {
        TraktRepository(get(), get())
    }

    single {
        TraktRemoteDataSource(get())
    }

    single {
        TraktLocalDataSource(get())
    }

    single {
        Room.databaseBuilder(androidApplication(), TraktDatabase::class.java, "trakt-db").build()
    }

    single {
        get<TraktDatabase>().traktDAO()
    }
}

fun createOkHttpClient(): OkHttpClient {
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC
    return OkHttpClient.Builder()
        .addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("Content-Type", "application/json")
                .addHeader("trakt-api-version", "2")
                .addHeader("trakt-api-key", "c6163a5d568810d9a3d29f7a7ea4ca68914cc26379a68aaa6a57a9ab0ba50ec2")
                .build()
            chain.proceed(request)
        }
        .addInterceptor(httpLoggingInterceptor)
        .connectTimeout(10, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .readTimeout(10, TimeUnit.SECONDS)
        .build()
}

inline fun <reified T> createWebService(okHttpClient: OkHttpClient, url: String): T {
    val retrofit = Retrofit.Builder()
        .baseUrl(url)
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()
    return retrofit.create(T::class.java)
}