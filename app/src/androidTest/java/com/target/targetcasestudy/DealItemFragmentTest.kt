package com.target.targetcasestudy

import androidx.core.os.bundleOf
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.jakewharton.espresso.OkHttp3IdlingResource
import com.target.targetcasestudy.di.module.BaseUrlModule
import com.target.targetcasestudy.di.module.NetworkModule
import com.target.targetcasestudy.utils.MockResponseFileReader
import com.target.targetcasestudy.utils.launchFragment
import com.target.targetcasestudy.ui.MainActivity
import com.target.targetcasestudy.ui.dealsitem.DealItemFragment
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
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
class DealItemFragmentTest {

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
    fun testDealDetailWithoutDiscount() {
        launchFragment<DealItemFragment>(navController, R.navigation.navigation_home, R.id.fragment_deal_item, bundleOf("id" to 0))
        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(MockResponseFileReader("deal_item_success_response_one.json").content)
        mockServer?.enqueue(response)

        onView(withId(R.id.imageView)).run {
            check(matches(isDisplayed()))
        }

        onView(withId(R.id.tvPrice)).run {
            check(matches(isDisplayed()))
        }

        onView(withId(R.id.tvTittle)).run {
            check(matches(isDisplayed()))
        }

        onView(withId(R.id.tvDescription)).run {
            check(matches(isDisplayed()))
        }

        onView(withId(R.id.tvAddToList)).run {
            check(matches(isDisplayed()))
        }

        onView(withId(R.id.tvAddToCart)).run {
            check(matches(isDisplayed()))
        }

        // verify text of the view
        onView(withId(R.id.tvTittle)).run {
            check(matches(withText("non mollit veniam ex")))
        }
        onView(withId(R.id.tvPrice)).run {
            check(matches(withText("\$184.06")))
        }
        onView(withId(R.id.tvDescription)).run {
            check(matches(withSubstring("minim ad et minim ipsum duis irure pariatur")))
        }

        onView(withId(R.id.tvAddToList)).perform(ViewActions.click())

        onView(withId(com.google.android.material.R.id.snackbar_text))
            .check(matches(withText(R.string.feature_under_development)))

        onView(withId(R.id.tvAddToCart)).perform(ViewActions.click())

        onView(withId(com.google.android.material.R.id.snackbar_text))
            .check(matches(withText(R.string.feature_under_development)))
    }

    @Test
    fun testDealDetailWithDiscount() {
        launchFragment<DealItemFragment>(navController, R.navigation.navigation_home, R.id.fragment_deal_item, bundleOf("id" to 1))
        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(MockResponseFileReader("deal_item_success_response_two.json").content)
        mockServer?.enqueue(response)

        onView(withId(R.id.imageView)).run {
            check(matches(isDisplayed()))
        }

        onView(withId(R.id.tvPrice)).run {
            check(matches(isDisplayed()))
        }

        onView(withId(R.id.tvRegPrice)).run {
            check(matches(isDisplayed()))
        }

        onView(withId(R.id.tvTittle)).run {
            check(matches(isDisplayed()))
        }

        onView(withId(R.id.tvDescription)).run {
            check(matches(isDisplayed()))
        }

        onView(withId(R.id.tvAddToList)).run {
            check(matches(isDisplayed()))
        }

        onView(withId(R.id.tvAddToCart)).run {
            check(matches(isDisplayed()))
        }

        // verify text of the view
        onView(withId(R.id.tvTittle)).run {
            check(matches(withText("sint aliqua mollit duis ullamco")))
        }
        onView(withId(R.id.tvPrice)).run {
            check(matches(withText("\$7.34")))
        }
        onView(withId(R.id.tvRegPrice)).run {
            check(matches(withText("\$40.25")))
        }
        onView(withId(R.id.tvDescription)).run {
            check(matches(withSubstring("ad laboris do ad id ipsum dolore ad magna occaecat ea eu ex nisi culpa amet id")))
        }

        onView(withId(R.id.tvAddToList)).perform(ViewActions.click())

        onView(withId(com.google.android.material.R.id.snackbar_text))
            .check(matches(withText(R.string.feature_under_development)))

        onView(withId(R.id.tvAddToCart)).perform(ViewActions.click())

        onView(withId(com.google.android.material.R.id.snackbar_text))
            .check(matches(withText(R.string.feature_under_development)))
    }
}