package com.uniandes.vynilsmobile.view

import android.os.Bundle
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.uniandes.vynilsmobile.R
import com.uniandes.vynilsmobile.data.model.Artist
import org.junit.Rule
import org.junit.Test

class ArtistDetailFragmentTest {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    private val artist = Artist(
        artistId = 1,
        name = "Sample Artist",
        image = "https://example.com/artist_image.jpg",
        description = "This is a sample artist",
        birthDate = "1990-01-01"
    )

    @Test
    fun testArtistDetailFragmentUI() {
        val scenario: FragmentScenario<ArtistDetailFragment> = launchFragmentInContainer(
            fragmentArgs = Bundle().apply {
                putSerializable("artist", artist)
            },
            themeResId = R.style.Theme_VynilsMobile
        )

        Espresso.onView(ViewMatchers.withId(R.id.artistName))
            .check(ViewAssertions.matches(ViewMatchers.withText(artist.name)))

        Espresso.onView(ViewMatchers.withId(R.id.artistImage))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.onView(ViewMatchers.withId(R.id.tvDescription))
            .check(ViewAssertions.matches(ViewMatchers.withText(artist.description)))

        Espresso.onView(ViewMatchers.withId(R.id.tvBirthDate))
            .check(ViewAssertions.matches(ViewMatchers.withText(artist.birthDate)))

        Espresso.pressBack()
    }
}
