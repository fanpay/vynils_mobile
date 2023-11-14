package com.uniandes.vynilsmobile.view;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isSelected;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.util.Log;
import android.view.View;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.ViewAssertion;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;

import com.uniandes.vynilsmobile.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4ClassRunner.class)
public class TestMainActivity{

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityTestRule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void testToolbarTitle() {
        String expectedTitle = getApplicationContext().getResources().getString(R.string.title_albums);
        onView(withId(R.id.my_toolbar)).check(matches(isDisplayed()));
        onView(isAssignableFrom(Toolbar.class))
                .check(matches(withToolbarTitle(expectedTitle)));
    }

    private Matcher<View> withToolbarTitle(final String expectedTitle) {
        return new TypeSafeMatcher<View>() {
            @Override
            protected boolean matchesSafely(View item) {
                if (item instanceof Toolbar) {
                    Toolbar toolbar = (Toolbar) item;
                    String actualTitle = String.valueOf(toolbar.getTitle());
                    Log.d("ToolbarTest", "Actual title: " + actualTitle);
                    return expectedTitle.equals(actualTitle);
                }
                return false;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("with toolbar title: " + expectedTitle);
            }
        };
    }

    @Test
    public void testBottomNavigation() {
        onView(withId(R.id.bottom_navigation)).check(matches(isDisplayed()));

        String expectedTitleAlbums = getApplicationContext().getResources().getString(R.string.title_albums_bnb);
        onView(withId(R.id.albumFragment)).check(matches(isDisplayed()));
        onView(withId(R.id.bottom_navigation))
                .check(matches(hasDescendant(withText(expectedTitleAlbums))));

        String expectedTitleArtists = getApplicationContext().getResources().getString(R.string.title_artists_bnb);
        onView(withId(R.id.page_artistas)).check(matches(isDisplayed()));
        onView(withId(R.id.bottom_navigation))
                .check(matches(hasDescendant(withText(expectedTitleArtists))));

        String expectedTitleCollectors = getApplicationContext().getResources().getString(R.string.title_collectors_bnb);
        onView(withId(R.id.page_coleccionistas)).check(matches(isDisplayed()));
        onView(withId(R.id.bottom_navigation))
                .check(matches(hasDescendant(withText(expectedTitleCollectors))));

        onView(withId(R.id.albumFragment)).perform(click());
        onView(withId(R.id.albumsRv)).check(matches(isDisplayed()));
    }

    @Test
    public void testAlbumsNavigationFromBottomNavigationBar() {
        onView(withId(R.id.bottom_navigation)).check(matches(isDisplayed()));

        onView(withId(R.id.albumFragment)).check(matches(isDisplayed()));
        onView(withId(R.id.albumFragment)).perform(click());
        onView(withId(R.id.albumFragment)). check(matches(isSelected()));

        onView(withId(R.id.albumsRv)).check(matches(isDisplayed()));
    }

    @Test
    public void testRecyclerView() throws InterruptedException {
        onView(withId(R.id.albumsRv)).check(matches(isDisplayed()));

        //Se agrega un sleep para dar tiempo de que carguen los elementos en el RecyclerView. No es la mejor manera y se debe cambiar a futuro.
        Thread.sleep(1000);
        onView(withId(R.id.albumsRv)).check(new RecyclerViewItemCountAssertion(1));
    }

    public class RecyclerViewItemCountAssertion implements ViewAssertion {
        private final int expectedCount;

        public RecyclerViewItemCountAssertion(int expectedCount) {
            this.expectedCount = expectedCount;
        }

        @Override
        public void check(View view, NoMatchingViewException noViewFoundException) {
            if (noViewFoundException != null) {
                throw noViewFoundException;
            }

            RecyclerView recyclerView = (RecyclerView) view;
            RecyclerView.Adapter adapter = recyclerView.getAdapter();
            MatcherAssert.assertThat(adapter.getItemCount(), Matchers.greaterThanOrEqualTo(expectedCount));
        }
    }
}
