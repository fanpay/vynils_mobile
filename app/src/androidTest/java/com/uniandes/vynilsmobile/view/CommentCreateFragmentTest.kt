package com.uniandes.vynilsmobile.view


import android.widget.Toast
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
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
import java.lang.Thread.sleep

@LargeTest
@RunWith(AndroidJUnit4::class)
class CommentCreateFragmentTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun navigateToCommentCreateFragmentFromAlbumDetailFragment(){
        val bottomNavigationItemView = onView(
            Matchers.allOf(
                withId(R.id.albumFragment),
                withContentDescription(R.string.title_albums),
                isDescendantOfA(
                    isDescendantOfA(withId(R.id.bottom_navigation))
                ),
                isDisplayed()
            )
        )
        bottomNavigationItemView.perform(click())

        sleep(Toast.LENGTH_LONG * 2000L)

        val recyclerView = onView(
            Matchers.allOf(
                withId(R.id.albumsRv),
                isDisplayed()
            )
        )

        recyclerView.perform(actionOnItemAtPosition<androidx.recyclerview.widget.RecyclerView.ViewHolder>(0, click()))

        sleep(Toast.LENGTH_LONG * 2000L)
        onView(withId(R.id.commentButton)).perform(scrollTo())
        onView(withId(R.id.commentButton)).check(matches(isDisplayed()))
        onView(withId(R.id.commentButton)).perform(click())
    }
    @Test
    fun testCommentCreation() {
        sleep(Toast.LENGTH_LONG * 2000L)

        onView(withId(R.id.editTextRating)).check(matches(isDisplayed()))
        onView(withId(R.id.editTextRating)).perform(typeText("4"), closeSoftKeyboard())
        onView(withId(R.id.editTextComment)).perform(typeText("Test comment"), closeSoftKeyboard())

        onView(withId(R.id.buttonCreateComment)).perform(click())

        sleep(Toast.LENGTH_LONG * 1000L)
        onView(withId(R.id.albumName)).check(matches(isDisplayed()))
    }
}
