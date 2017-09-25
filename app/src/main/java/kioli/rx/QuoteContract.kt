package kioli.rx

import io.reactivex.Flowable
import kioli.rx.entity.Quote
import kioli.rx.mvp.IPresenter
import kioli.rx.mvp.IView

internal interface QuoteContract {

    interface QuoteView : IView {
        fun showQuote(quote: Quote)
        fun showLoading()
        fun hideLoading()
    }

    interface QuotePresenter : IPresenter<QuoteView> {
        fun getQuote(forceNew: Boolean)
    }

    interface QuoteModel {
        fun fetchQuote(): Flowable<Quote>
    }
}