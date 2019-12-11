package com.stimednp.kadesubmission4.ui.search

import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.stimednp.kadesubmission4.R.id.*
import com.stimednp.kadesubmission4.ui.main.MainActivity
import com.stimednp.kadesubmission4.utils.EspressoIdlingResource
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


/**
 * Created by rivaldy on 12/10/2019.
 */

@RunWith(AndroidJUnit4::class)
class SearchActivityTest {
    @Rule
    @JvmField
    val acitivityRule = ActivityTestRule(MainActivity::class.java)

    @Before
    fun setUp() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.idlingResource)
    }

    @After
    fun tearDown(){
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.idlingResource)
    }

    @Test
    fun getTextSearch() {
        //show data and click position 0
        onView(withId(rv_main)).check(matches(isDisplayed()))
        onView(withId(rv_main)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        //click menu search
        onView(withId(item_search)).check(matches(isDisplayed()))
        onView(withId(item_search)).perform(click())

        //input and search "Arsenal"
        onView(withId(item_search_action)).check(matches(isDisplayed()))
        onView(isAssignableFrom(EditText::class.java)).perform(typeText("Arsenal"), pressImeActionButton())

        //show on recycler
        onView(withId(rv_search)).check(matches(isDisplayed()))

        //clear and search "Chelsea"
        onView(isAssignableFrom(EditText::class.java)).perform(clearText())
        onView(isAssignableFrom(EditText::class.java)).perform(typeText("Chelsea"), pressImeActionButton())

        //show on recycler
        onView(withId(rv_search)).check(matches(isDisplayed()))

        //clear and search "No data will show"
        onView(isAssignableFrom(EditText::class.java)).perform(clearText())
        onView(isAssignableFrom(EditText::class.java)).perform(typeText("No data will show"), pressImeActionButton())

        //text empty data show
        onView(withId(tv_empty_datas)).check(matches(isDisplayed()))

        //clear text and exit -> FINISHH :)
        onView(isAssignableFrom(EditText::class.java)).perform(clearText())
    }
}