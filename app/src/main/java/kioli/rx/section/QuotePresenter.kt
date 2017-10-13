package kioli.rx.section

import android.util.Log
import io.reactivex.Flowable
import kioli.rx.api.FlowableManagerI
import kioli.rx.api.SchedulerProviderI
import kioli.rx.entity.Quote
import kioli.rx.mvp.BasePresenter
import kioli.rx.section.QuoteContract.Presenter
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

        disposables.size()
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
        view.hideLoading()
        view.returnResultQuote(quote)
    }
}