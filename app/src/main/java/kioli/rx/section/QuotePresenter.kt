package kioli.rx.section

import android.util.Log
import io.reactivex.Flowable
import kioli.rx.section.QuoteContract.QuotePresenter
import kioli.rx.section.QuoteContract.QuoteView
import kioli.rx.api.FlowableManager
import kioli.rx.api.SchedulerProvider
import kioli.rx.entity.Quote
import kioli.rx.mvp.BasePresenter
import java.util.concurrent.TimeUnit

internal class QuotePresenter(private val model: QuoteContract.QuoteModel,
                              flowableManager: FlowableManager.FlowableManagerWrapper,
                              schedulerProvider: SchedulerProvider.SchedulerProviderWrapper)
    : BasePresenter<QuoteView>(flowableManager, schedulerProvider), QuotePresenter {

    private val flowableCacheKey = "flowable quote"

    override fun getQuote(forceNew: Boolean) {
        view?.showLoading()
        val flowableQuote = flowableManager.cacheFlowable(flowableCacheKey, model.fetchQuote()
                .delay(2, TimeUnit.SECONDS)
                .subscribeOn(schedulerProvider.newThread())
                .observeOn(schedulerProvider.ui()), forceNew) as Flowable<Quote>

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
        view?.hideLoading()
        view?.returnResultQuote(quote)
    }
}