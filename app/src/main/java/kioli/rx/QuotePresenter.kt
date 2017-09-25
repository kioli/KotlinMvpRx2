package kioli.rx

import android.util.Log
import io.reactivex.Flowable
import io.reactivex.disposables.Disposable
import kioli.rx.QuoteContract.QuotePresenter
import kioli.rx.QuoteContract.QuoteView
import kioli.rx.api.FlowableManager
import kioli.rx.api.SchedulerProvider
import kioli.rx.entity.Quote
import kioli.rx.mvp.BasePresenter
import java.util.concurrent.TimeUnit

internal class QuotePresenter(private val model: QuoteContract.QuoteModel)
    : BasePresenter<QuoteView>(), QuotePresenter {

    private val flowableCacheKey = "flowable quote"
    private var disposableQuote: Disposable? = null

    override fun detachView() {
        super.detachView()
        disposableQuote?.dispose()
    }

    override fun getQuote(forceNew: Boolean) {
        view?.showLoading()
        val flowableQuote = FlowableManager.cacheFlowable(flowableCacheKey, model.fetchQuote()
                .delay(2, TimeUnit.SECONDS)
                .subscribeOn(SchedulerProvider.newThread())
                .observeOn(SchedulerProvider.ui()), forceNew) as Flowable<Quote>

        disposableQuote = flowableQuote.subscribe(
                { quote ->
                    handleQuoteResult(quote)
                },
                { error ->
                    Log.e("Rx2Test", "error getting quote: ${error.localizedMessage}")
                    handleQuoteResult(null)
                })
    }

    private fun handleQuoteResult(quote: Quote?) {
        view?.hideLoading()
        view?.returnResultQuote(quote)
    }
}