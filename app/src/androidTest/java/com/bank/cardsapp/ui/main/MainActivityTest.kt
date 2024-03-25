package com.bank.cardsapp.ui.main


import android.R
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.bank.cardsapp.ui.main.MainActivity
import org.hamcrest.Matchers
import org.hamcrest.core.IsInstanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun mainActivityTest() {
        val view = Espresso.onView(
            Matchers.allOf(
                withId(R.id.statusBarBackground),
                withParent(IsInstanceOf.instanceOf(FrameLayout::class.java)),
                isDisplayed()
            )
        )
        view.check(matches(isDisplayed()))

        val view2 = Espresso.onView(
            Matchers.allOf(
                withId(R.id.navigationBarBackground),
                withParent(IsInstanceOf.instanceOf(FrameLayout::class.java)),
                isDisplayed()
            )
        )
        view2.check(matches(isDisplayed()))

        val composeView = Espresso.onView(
            Matchers.allOf(
                withParent(
                    Matchers.allOf(
                        withId(R.id.content),
                        withParent(IsInstanceOf.instanceOf(LinearLayout::class.java))
                    )
                ),
                isDisplayed()
            )
        )
        composeView.check(matches(isDisplayed()))
    }
}
