package com.mortarifabio.marvelcharacterschallenge.characters

import android.os.Build
import com.mortarifabio.marvelcharacterschallenge.api.ApiService
import com.mortarifabio.marvelcharacterschallenge.api.MarvelApi
import com.mortarifabio.marvelcharacterschallenge.utils.Constants
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.P])
class CharactersRepositoryTest : TestCase() {

    @Test
    @Throws(Exception::class)
    fun getCharacters_ShouldReturnDataFromApi() = runBlocking {
        val mockWebServer = MockWebServer()
        val retrofit = Retrofit.Builder()
            .baseUrl(mockWebServer.url("").toString())
            .client(ApiService.getInterceptorClient())
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

        val json = "{\"code\": 200,\"status\": \"Ok\",\"copyright\": \"© 2021 MARVEL\",\"attributionText\": \"Data provided by Marvel. © 2021 MARVEL\",\"attributionHTML\": \"<a href=\\\"http://marvel.com\\\">Data provided by Marvel. © 2021 MARVEL</a>\",\"etag\": \"da246f4691c077b66c0aa384d0236a390c7a91e8\",\"data\": {\"offset\": 0,\"limit\": 20,\"total\": 1493,\"count\": 20,\"results\": [{\"id\": 1011334,\"name\": \"3-D Man\",\"description\": \"\",\"modified\": \"2014-04-29T14:18:17-0400\",\"thumbnail\": {\"path\": \"http://i.annihil.us/u/prod/marvel/i/mg/c/e0/535fecbbb9784\",\"extension\": \"jpg\"}}]}}"
        mockWebServer.enqueue(MockResponse().setBody(json))
        val marvelApi: MarvelApi = retrofit.create(MarvelApi::class.java)

        val response = marvelApi.getCharacters(Constants.Api.API_LIMIT, 0)
        val charactersList = response.body()?.data?.results
        val listSize = charactersList?.size
        val characterName = charactersList?.get(0)?.name
        val characterThumbPath = charactersList?.get(0)?.thumbnail?.path

        assertEquals(1, listSize)
        assertEquals("3-D Man", characterName)
        assertEquals("http://i.annihil.us/u/prod/marvel/i/mg/c/e0/535fecbbb9784", characterThumbPath)

        mockWebServer.shutdown()
    }
}