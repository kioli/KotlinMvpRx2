package kioli.rx.section

import io.reactivex.Flowable
import kioli.rx.entity.Quote
import kioli.rx.mvp.PresenterI
import kioli.rx.mvp.ViewI

internal interface QuoteContract {

    interface View : ViewI<Presenter> {
        /**
         * Return the result of asking for a quote to the calling QuoteView
         *
         * @param quote the requested quote
         */
        fun returnResultQuote(quote: Quote?)

        /**
         * Show the loading screen in the QuoteView
         */
        fun showLoading()

        /**
         * Hide the loading screen in the QuoteView
         */
        fun hideLoading()
    }

    interface Presenter : PresenterI {
        /**
         * Get a quote to present to the QuoteView
         *
         * @param forceNew true if the quote to get should be a new one, false otherwise
         */
        fun getQuote(forceNew: Boolean)
    }

    interface Model {
        /**
         * Retrieve a quote wrapping it in a reactive flowable
         *
         * @return a flowable containing the retrieved quote
         */
        fun fetchQuote(): Flowable<Quote>
    }
}