package kioli.rx.section

import android.util.Log
import io.reactivex.Flowable
import kioli.rx.api.FlowableManagerI
import kioli.rx.api.SchedulerProviderI
import kioli.rx.entity.Quote
import kioli.rx.mvp.BasePresenter
import kioli.rx.section.QuoteContract.Presenter
import kioli.rx.util.EspressoIdlingResource
import java.util.concurrent.TimeUnit

internal class QuotePresenter(private val model: QuoteContract.Model,
                              private val view: QuoteContract.View,
                              flowableManager: FlowableManagerI,
                              schedulerProvider: SchedulerProviderI)
    : BasePresenter(flowableManager, schedulerProvider), Presenter {

    private val flowableCacheKey = "flowable quote"

    init {
        view.presenter = this
    }

    override fun start() {
        getQuote(false)
    }

    override fun getQuote(forceNew: Boolean) {
        view.showLoading()
        val flowableQuote = flowableManager.cacheFlowable(flowableCacheKey, model.fetchQuote()
                .delay(1, TimeUnit.SECONDS)
                .subscribeOn(schedulerProvider.newThread())
                .observeOn(schedulerProvider.ui()), forceNew) as Flowable<Quote>

        // Make sure Espresso knows that the app is busy until the response is handled.
        EspressoIdlingResource.increment()
        disposables.add(flowableQuote.subscribe(
                { quote ->
                    handleQuoteResult(quote)
                },
                { error ->
                    Log.e("Rx2Test", "error getting quote: ${error.localizedMessage}")
                    handleQuoteResult(null)
                }))
    }

    private fun handleQuoteResult(quote: Quote?) {
        // This callback may be called twice, once for the cache and once for loading
        // the data from the server API, so we check before decrementing
        if (!EspressoIdlingResource.countingIdlingResource.isIdleNow) {
            EspressoIdlingResource.decrement()
        }
        view.hideLoading()
        view.returnResultQuote(quote)
    }
}