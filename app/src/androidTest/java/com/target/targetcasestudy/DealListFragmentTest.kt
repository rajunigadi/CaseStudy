package com.target.targetcasestudy

import androidx.navigation.testing.TestNavHostController
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.jakewharton.espresso.OkHttp3IdlingResource
import com.target.targetcasestudy.di.module.BaseUrlModule
import com.target.targetcasestudy.di.module.NetworkModule
import com.target.targetcasestudy.ui.MainActivity
import com.target.targetcasestudy.ui.deals.DealListFragment
import com.target.targetcasestudy.utils.MockResponseFileReader
import com.target.targetcasestudy.utils.launchFragment
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.net.HttpURLConnection
import javax.inject.Inject

@UninstallModules(NetworkModule::class, BaseUrlModule::class)
@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
@LargeTest
class DealListFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    private val navController = TestNavHostController(ApplicationProvider.getApplicationContext())

    @get:Rule
    val activityRule = ActivityTestRule(MainActivity::class.java, true, false)

    private var mockServer: MockWebServer? = null

    @Inject
    lateinit var okHttp: OkHttpClient

    @Before
    fun setUp() {
        hiltRule.inject()
        mockServer = MockWebServer()
        mockServer?.start(8080)
        IdlingRegistry.getInstance().register(OkHttp3IdlingResource.create("okhttp", okHttp))
    }

    @After
    fun tearDown() {
        mockServer?.shutdown()
    }

    @Test
    fun dealListScreenTest() {
        launchFragment<DealListFragment>(navController, R.navigation.navigation_home, R.id.fragment_deals)
        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(MockResponseFileReader("deals_success_response.json").content)
        mockServer?.enqueue(response)

        onView(withId(R.id.recyclerView)).run {
            check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        }

        //Click on first article
        onView(withId(R.id.recyclerView)).perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        //Check that it navigates to Detail screen
        assertThat(navController.currentDestination?.id, equalTo(R.id.fragment_deal_item))
    }
}