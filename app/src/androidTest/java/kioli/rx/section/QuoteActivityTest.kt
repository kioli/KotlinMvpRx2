package kioli.rx.section

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import kioli.rx.R
import org.hamcrest.Matchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
internal class QuoteActivityTest {

    /**
     * [ActivityTestRule] is a JUnit [@Rule][Rule] to launch your activity under test.
     *
     * Rules are interceptors which are executed for each test method and are important building
     * blocks of Junit tests.
     */
    @Rule
    @JvmField
    var tasksActivityTestRule = object : ActivityTestRule<QuoteActivity>(QuoteActivity::class.java) {
        /**
         * Here we can do something needed before each test
         */
        override fun beforeActivityLaunched() {
            super.beforeActivityLaunched()
            // Doing this in @Before generates a race condition.
        }
    }

    @Test
    fun launchQuoteActivity_LoadsQuote() {
        onView(withId(R.id.text)).check(matches(isCompletelyDisplayed()))
        onView(withText("")).check(matches(isCompletelyDisplayed()))
        onView(withId(R.id.loading)).check(matches(isCompletelyDisplayed()))
    }

}
