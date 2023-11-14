package com.uniandes.vynilsmobile.view

import android.os.Bundle
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.uniandes.vynilsmobile.R
import com.uniandes.vynilsmobile.data.model.Album
import org.junit.Rule
import org.junit.Test

class AlbumDetailFragmentTest {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(MainActivity::class.java)


    private val album = Album(
        albumId = 1,
        name = "Sample Album",
        cover = "https://example.com/album_cover.jpg",
        releaseDate = "2023-01-01",
        description = "This is a sample album",
        genre = "Rock",
        recordLabel = "Sample Records"
    )

    @Test
    fun testAlbumDetailFragmentUI() {
        // Lanzar el fragmento en un escenario de fragmento
        val scenario: FragmentScenario<AlbumDetailFragment> = launchFragmentInContainer(
            fragmentArgs = Bundle().apply {
                putSerializable("album", album)
            },
            themeResId = R.style.Theme_VynilsMobile
        )

        // Verify that the views are displayed correctly
        Espresso.onView(ViewMatchers.withId(R.id.albumName))
            .check(ViewAssertions.matches(ViewMatchers.withText(album.name)))

        Espresso.onView(ViewMatchers.withId(R.id.albumImage))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.onView(ViewMatchers.withId(R.id.tvDescription))
            .check(ViewAssertions.matches(ViewMatchers.withText(album.description)))

        Espresso.onView(ViewMatchers.withId(R.id.tvReleaseDate))
            .check(ViewAssertions.matches(ViewMatchers.withText(album.releaseDate)))

        Espresso.onView(ViewMatchers.withId(R.id.tvGenre))
            .check(ViewAssertions.matches(ViewMatchers.withText(album.genre)))

        // Perform click action on the Back button
        Espresso.pressBack()
    }
}
