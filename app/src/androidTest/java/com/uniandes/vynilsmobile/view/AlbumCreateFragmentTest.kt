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
class AlbumCreateFragmentTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun navigateToAlbumCreateFragmentFromAlbumFragment(){
        Thread.sleep(Toast.LENGTH_LONG * 2000L)

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

        Thread.sleep(Toast.LENGTH_LONG * 2000L)

        val floatingActionButton = onView(
            Matchers.allOf(
                withId(R.id.floating_add_album),
                isDisplayed()
            )
        )
        floatingActionButton.perform(click())
    }
    @Test
    fun testAlbumCreation() {
        Thread.sleep(Toast.LENGTH_LONG * 2000L)
        // Wait for the fragment to be loaded
        onView(withId(R.id.editNombreAlbum)).check(matches(isDisplayed()))

        // Enter album information
        onView(withId(R.id.editNombreAlbum)).perform(typeText("Test Album"), closeSoftKeyboard())
        onView(withId(R.id.editDescripcionAlbum)).perform(typeText("Test description"), closeSoftKeyboard())
        onView(withId(R.id.editCoverAlbum)).perform(typeText("http://example.com/cover.jpg"), closeSoftKeyboard())
        onView(withId(R.id.editFechaEstrenoAlbum)).perform(click())

        // Simula la selección de una fecha (puedes ajustar según sea necesario)
        onView(withId(android.R.id.button1)).perform(click())
        onView(withId(R.id.editFechaEstrenoAlbum)).perform(replaceText("2023-11-28"))

        onView(withId(R.id.albumGeneros)).perform(click())
        onView(withText("Rock")).perform(click())
        onView(withId(R.id.etiquetasGrabaciones)).perform(click())
        onView(withText("EMI")).perform(click())

        onView(withId(R.id.btnCrearAlbum)).perform(scrollTo(), click())

        Thread.sleep(Toast.LENGTH_LONG * 2000L)
        onView(withId(R.id.albumFragment)).check(matches(isDisplayed()))
    }
}
