package com.mortarifabio.marvelcharacterschallenge.api

import com.mortarifabio.marvelcharacterschallenge.utils.ApiKeys.API_PRIVATE_KEY
import com.mortarifabio.marvelcharacterschallenge.utils.ApiKeys.API_PUBLIC_KEY
import com.mortarifabio.marvelcharacterschallenge.utils.Constants.Api.API_BASE_URL
import com.mortarifabio.marvelcharacterschallenge.utils.Constants.Api.API_HASH_LABEL
import com.mortarifabio.marvelcharacterschallenge.utils.Constants.Api.API_KEY_LABEL
import com.mortarifabio.marvelcharacterschallenge.utils.Constants.Api.API_ORDER
import com.mortarifabio.marvelcharacterschallenge.utils.Constants.Api.API_ORDER_LABEL
import com.mortarifabio.marvelcharacterschallenge.utils.Constants.Api.API_TIMESTAMP_LABEL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.math.BigInteger
import java.security.MessageDigest
import java.util.concurrent.TimeUnit

object ApiService {

    val marvelApi: MarvelApi = getMarvelApiClient().create(MarvelApi::class.java)

    private fun getMarvelApiClient(): Retrofit {
        return Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .client(getInterceptorClient())
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
    }

    //todo: remover loggingInterceptor
    private fun getInterceptorClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val timestamp = getTimestamp()
        val hash = getHash(timestamp)
        val interceptor = OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .addInterceptor { chain ->
                    val url = chain.request().url.newBuilder()
                            .addQueryParameter(API_ORDER_LABEL, API_ORDER)
                            .addQueryParameter(API_TIMESTAMP_LABEL, timestamp)
                            .addQueryParameter(API_KEY_LABEL, API_PUBLIC_KEY)
                            .addQueryParameter(API_HASH_LABEL, hash)
                            .build()
                    val newRequest = chain.request().newBuilder().url(url).build()
                    chain.proceed(newRequest)
                }
        return interceptor.build()
    }

    private fun getTimestamp(): String {
        val ts = System.currentTimeMillis() / 1000
        return ts.toString()
    }

    private fun getHash(timestamp: String): String {
        return md5("$timestamp$API_PRIVATE_KEY$API_PUBLIC_KEY")
    }

    private fun md5(input: String): String {
        val md = MessageDigest.getInstance("MD5").digest(input.toByteArray())
        return BigInteger(1, md).toString(16).padStart(32, '0')
    }
}