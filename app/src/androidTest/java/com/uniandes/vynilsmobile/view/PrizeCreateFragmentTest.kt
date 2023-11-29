package com.uniandes.vynilsmobile.view


 import android.widget.Toast
 import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.uniandes.vynilsmobile.R
import org.hamcrest.Matchers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class PrizeCreateFragmentTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun navigateToPrizeCreateFragmentFromCollectorFragment(){
        Thread.sleep(Toast.LENGTH_LONG * 2000L)

        val bottomNavigationItemView = onView(
            Matchers.allOf(
                withId(R.id.collectorsFragment),
                withContentDescription(R.string.title_collectors),
                isDescendantOfA(
                    isDescendantOfA(withId(R.id.bottom_navigation))
                ),
                isDisplayed()
            )
        )
        bottomNavigationItemView.perform(click())

        Thread.sleep(Toast.LENGTH_LONG * 2000L)

        val floatingActionButton = onView(
            Matchers.allOf(
                withId(R.id.floating_add_prize),
                isDisplayed()
            )
        )
        floatingActionButton.perform(click())
    }
    @Test
    fun testPrizeCreation() {
        Thread.sleep(Toast.LENGTH_LONG * 2000L)
        // Wait for the fragment to be loaded
        onView(withId(R.id.editTextName)).check(matches(isDisplayed()))

        // Enter album information
        onView(withId(R.id.editTextName)).perform(typeText("Test Prize"), closeSoftKeyboard())
        onView(withId(R.id.editTextDescription)).perform(typeText("Test description"), closeSoftKeyboard())
        onView(withId(R.id.editTextOrganization)).perform(typeText("Test ORG"), closeSoftKeyboard())

        onView(withId(R.id.buttonCreatePrize)).perform(click())

        Thread.sleep(Toast.LENGTH_LONG * 2000L)
        onView(withId(R.id.collectorsFragment)).check(matches(isDisplayed()))
    }
}
