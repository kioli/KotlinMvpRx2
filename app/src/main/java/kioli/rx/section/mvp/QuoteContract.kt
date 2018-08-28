package kioli.rx.section.mvp

import kioli.rx.common.entity.Quote
import kioli.rx.common.mvp.PresenterI
import kioli.rx.common.mvp.ViewI

internal interface QuoteContract {

    interface View : ViewI {
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

    interface Presenter : PresenterI<View> {

        /**
         * Get a quote to present to the QuoteView
         *
         * @param forceNew true if the quote to get should be a new one, false otherwise
         */
        fun getQuote(forceNew: Boolean)
    }
}