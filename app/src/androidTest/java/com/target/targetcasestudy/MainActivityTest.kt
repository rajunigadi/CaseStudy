package com.target.targetcasestudy

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.jakewharton.espresso.OkHttp3IdlingResource
import com.target.targetcasestudy.di.module.BaseUrlModule
import com.target.targetcasestudy.di.module.NetworkModule
import com.target.targetcasestudy.ui.MainActivity
import com.target.targetcasestudy.utils.MockResponseFileReader
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
class MainActivityTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    val activityRule = ActivityTestRule(MainActivity::class.java, true, false)

    @Inject
    lateinit var okHttp: OkHttpClient

    private var mockServer: MockWebServer? = null

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
    fun activityTest() {
        activityRule.launchActivity(null)
        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(MockResponseFileReader("deals_success_response.json").content)
        mockServer?.enqueue(response)

        onView(withId(R.id.credit_card)).run {
            check(ViewAssertions.matches(isDisplayed()))
        }

        onView(withId(R.id.credit_card)).perform(click())

        onView(withId(R.id.title)).run {
            check(ViewAssertions.matches(isDisplayed()))
        }
        onView(withId(R.id.subtitle)).run {
            check(ViewAssertions.matches(isDisplayed()))
        }
        onView(withId(R.id.card_number)).run {
            check(ViewAssertions.matches(isDisplayed()))
        }
        onView(withId(R.id.submit)).run {
            check(ViewAssertions.matches(isDisplayed()))
        }
        onView(withId(R.id.cancel)).run {
            check(ViewAssertions.matches(isDisplayed()))
        }

        onView(withId(R.id.cancel)).perform(click())

        onView(withId(R.id.credit_card)).perform(click())

        onView(withText(activityRule.activity.getString(R.string.credit_card_payment_title))).run {
            check(ViewAssertions.matches(isDisplayed()))
        }
        onView(withText(activityRule.activity.getString(R.string.credit_card_payment_subtitle))).run {
            check(ViewAssertions.matches(isDisplayed()))
        }

        onView(withHint(activityRule.activity.getString(R.string.credit_card_number_hint))).run {
            check(ViewAssertions.matches(isDisplayed()))
        }

        onView(withText(activityRule.activity.getString(R.string.cancel))).run {
            check(ViewAssertions.matches(isDisplayed()))
        }

        onView(withText(activityRule.activity.getString(R.string.submit))).run {
            check(ViewAssertions.matches(isDisplayed()))
        }

        onView(withId(R.id.card_number)).run {
            perform(typeText("4716763950111815"))
        }

        onView(withId(R.id.submit)).run {
            check(ViewAssertions.matches(isEnabled()))
        }

        onView(withId(R.id.submit)).perform(click())
    }
}