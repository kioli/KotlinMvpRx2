package kioli.rx.section

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.intent.rule.IntentsTestRule
import android.support.test.espresso.matcher.ViewMatchers.*
import kioli.rx.BlockingUseCaseInvoker
import kioli.rx.R
import kioli.rx.anything
import kioli.rx.common.entity.Quote
import kioli.rx.common.functional.right
import kioli.rx.common.interactor.Invoker
import kioli.rx.common.interactor.UseCase
import kioli.rx.section.interactor.QuoteUseCase
import kioli.rx.section.mvp.QuoteActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.kodein.di.generic.bind
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton
import org.mockito.Mock
import org.mockito.Mockito.doReturn
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
internal class QuoteActivityTest {

    @Mock
    private lateinit var getQuote: QuoteUseCase

    @Rule
    @JvmField
    var activityRule: IntentsTestRule<QuoteActivity> =
            IntentsTestRule(QuoteActivity::class.java, true, false)

    @Rule
    @JvmField
    val overridesRule: OverridesRule = OverridesRule {
        bind<Invoker>(overrides = true) with singleton { BlockingUseCaseInvoker() }
        bind<UseCase<QuoteUseCase.Params, Quote>>(overrides = true) with provider { getQuote }
    }

    @Test
    fun loadQuoteFromCache() {
        val quote = Quote(text = "hey gorgeous")
        doReturn(quote.right()).`when`(getQuote).run(anything())

        activityRule.launchActivity(null)

        onView(withId(R.id.text)).check(matches(isCompletelyDisplayed()))
        onView(withText(quote.text)).check(matches(isCompletelyDisplayed()))
    }

}
