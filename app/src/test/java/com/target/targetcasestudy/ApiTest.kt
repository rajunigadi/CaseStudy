package com.target.targetcasestudy

import com.target.targetcasestudy.data.repository.network.Api
import com.target.targetcasestudy.data.repository.network.RepoImpl
import com.target.targetcasestudy.data.repository.network.RxErrorHandlingCallAdapterFactory
import com.target.targetcasestudy.utils.MockResponseFileReader
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.*
import org.junit.Assert.assertEquals
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection
import java.util.concurrent.TimeUnit

class ApiTest {

    private var mockServer: MockWebServer? = null

    private val client = OkHttpClient.Builder()
        .connectTimeout(1, TimeUnit.SECONDS)
        .readTimeout(1, TimeUnit.SECONDS)
        .writeTimeout(1, TimeUnit.SECONDS)
        .build()

    private val api = Retrofit.Builder()
        .baseUrl("http://localhost:8080/")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxErrorHandlingCallAdapterFactory.create())
        .build()
        .create(Api::class.java)

    private var repo = RepoImpl(api)

    @Before
    fun setUp() {
        mockServer = MockWebServer()
        mockServer?.start(8080)
    }

    @After
    fun tearDown() {
        mockServer?.shutdown()
    }

    @Test
    fun `300 error`() {
        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_MULT_CHOICE)
        mockServer?.enqueue(response)
        assertEquals("HTTP/1.1 300 Redirection", response.status)
    }

    @Test
    fun `400 error`() {
        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_NOT_FOUND)
        mockServer?.enqueue(response)
        assertEquals("HTTP/1.1 404 Client Error", response.status)
    }

    @Test
    fun `500 error`() {
        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_INTERNAL_ERROR)
        mockServer?.enqueue(response)
        assertEquals("HTTP/1.1 500 Server Error", response.status)
    }

    @Test
    fun apiSuccessTest() {
        // Assign
        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(MockResponseFileReader("deals_success_response.json").content)
        mockServer?.enqueue(response)

        // Actual
        val actualResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(repo.deals().blockingGet().toString())

        // Assert
        assertEquals(response.status, actualResponse.status)
    }

    @Test
    fun apiFailureTest() {
        mockServer?.dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                return MockResponse().throttleBody(1024, 5, TimeUnit.SECONDS)
            }
        }
    }
}