import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import com.uniandes.vynilsmobile.data.model.Album
import com.uniandes.vynilsmobile.view.AlbumDetailFragment
import org.junit.Test
import com.uniandes.vynilsmobile.R

class AlbumDetailFragmentTest {

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

        // Launch the fragment with the given album as an argument
        val scenario = launchFragmentInContainer(
            fragmentArgs = Bundle().apply {
                putSerializable("album", album)
            }
        ) { AlbumDetailFragment() }

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
        Espresso.onView(ViewMatchers.withId(R.id.btBack))
            .perform(ViewActions.click())
    }
}
