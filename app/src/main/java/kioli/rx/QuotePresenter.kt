package kioli.rx

import android.util.Log
import io.reactivex.Flowable
import io.reactivex.disposables.Disposable
import kioli.rx.QuoteContract.QuotePresenter
import kioli.rx.QuoteContract.QuoteView
import kioli.rx.api.SchedulerProvider
import kioli.rx.entity.Quote
import kioli.rx.mvp.BasePresenter
import kioli.rx.mvp.FlowableManager
import java.util.concurrent.TimeUnit

class QuotePresenter(private val model: QuoteContract.QuoteModel)
    : BasePresenter<QuoteView>(), QuotePresenter {

    private val flowableCacheKey = "flowable quote"
    private var disposableQuote: Disposable? = null

    override fun callData() {
        view?.showLoading()
        val flowableQuote = FlowableManager.cacheFlowable(flowableCacheKey, model.callData()
                .delay(2, TimeUnit.SECONDS)
                .subscribeOn(SchedulerProvider.newThread())
                .observeOn(SchedulerProvider.ui())) as Flowable<Quote>
        disposableQuote = flowableQuote.subscribe(
                { quote ->
                    view?.hideLoading()
                    view?.showQuote(quote)
                },
                { error ->
                    Log.e("Rx2Test", "error getting quote: ${error.localizedMessage}")
                })
    }

    override fun detachView() {
        super.detachView()
        disposableQuote?.dispose()
    }
}