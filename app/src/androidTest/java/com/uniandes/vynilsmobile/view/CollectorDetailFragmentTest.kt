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
import com.uniandes.vynilsmobile.data.model.Collector
import org.junit.Rule
import org.junit.Test

class CollectorDetailFragmentTest {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(MainActivity::class.java)


    private val collector = Collector(
        collectorId = 1,
        name = "Sample Collector",
        telephone = "6878575960",
        email = "email@hotmail.com"
    )

    @Test
    fun testCollectorDetailFragmentUI() {
        // Lanzar el fragmento en un escenario de fragmento
        val scenario: FragmentScenario<CollectorDetailFragment> = launchFragmentInContainer(
            fragmentArgs = Bundle().apply {
                putSerializable("collector", collector)
            },
            themeResId = R.style.Theme_VynilsMobile
        )

        // Verify that the views are displayed correctly
        Espresso.onView(ViewMatchers.withId(R.id.collectorName))
            .check(ViewAssertions.matches(ViewMatchers.withText(collector.name)))


        Espresso.onView(ViewMatchers.withId(R.id.tvTelephone))
            .check(ViewAssertions.matches(ViewMatchers.withText(collector.telephone)))

        Espresso.onView(ViewMatchers.withId(R.id.tvEmail))
            .check(ViewAssertions.matches(ViewMatchers.withText(collector.email)))


        // Perform click action on the Back button
        Espresso.pressBack()
    }
}
