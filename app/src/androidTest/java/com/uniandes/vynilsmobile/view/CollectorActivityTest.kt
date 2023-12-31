package com.uniandes.vynilsmobile.view


import android.view.View
import android.widget.Toast
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.uniandes.vynilsmobile.R
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class CollectorActivityTest {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun navigateToCollectorFragmentFromBottomNavigationMenu(){
        Thread.sleep(Toast.LENGTH_LONG * 2000L)

        val bottomNavigationItemView = onView(
            allOf(
                withId(R.id.collectorsFragment),
                withContentDescription(R.string.title_collectors),
                isDescendantOfA(
                    isDescendantOfA(withId(R.id.bottom_navigation))
                ),
                isDisplayed()
            )
        )
        bottomNavigationItemView.perform(click())
    }
    @Test
    fun checkFirstTextInCollectorRecyclerView() {
        Thread.sleep(Toast.LENGTH_LONG * 2000L)

        onView(withIndex(withId(R.id.textView1), 0)).perform(scrollTo())
        onView(withIndex(withId(R.id.textView1), 0)).check(matches(isDisplayed()))
        onView(withIndex(withId(R.id.textView1), 0)).check(matches(withText(Matchers.not(""))))
    }
    @Test
    fun checkSecondTextInCollectorRecyclerView() {
        Thread.sleep(Toast.LENGTH_LONG * 1000L)

        onView(withIndex(withId(R.id.textView2), 0)).perform(scrollTo())
        onView(withIndex(withId(R.id.textView2), 0)).check(matches(isDisplayed()))
        onView(withIndex(withId(R.id.textView2), 0)).check(matches(withText(Matchers.not(""))))
    }
    @Test
    fun checkThirdTextInCollectorRecyclerView() {
        Thread.sleep(Toast.LENGTH_LONG * 1000L)

        onView(withIndex(withId(R.id.textView3), 0)).perform(scrollTo())
        onView(withIndex(withId(R.id.textView3), 0)).check(matches(isDisplayed()))
        onView(withIndex(withId(R.id.textView3), 0)).check(matches(withText(Matchers.not(""))))
    }

    @Test
    fun checkFirstViewColorInAlbumRecyclerView() {
        Thread.sleep(Toast.LENGTH_LONG * 1000L)

        onView(withIndex(withId(R.id.collectorItemSeparator), 0)).perform(scrollTo())
        onView(withIndex(withId(R.id.collectorItemSeparator), 0)).check(matches(isDisplayed()))
    }

    private fun withIndex(matcher: Matcher<View>, index: Int): Matcher<View> {
        return object : TypeSafeMatcher<View>() {
            var currentIndex = 0
            override fun describeTo(description: Description) {
                description.appendText("with index: ")
                description.appendValue(index)
                matcher.describeTo(description)
            }

            override fun matchesSafely(view: View): Boolean {
                return matcher.matches(view) && currentIndex++ == index
            }
        }
    }
}
