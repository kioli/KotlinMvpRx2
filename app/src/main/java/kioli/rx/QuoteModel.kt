package kioli.rx

import io.reactivex.Flowable
import kioli.rx.api.QuoteApi
import kioli.rx.api.ServiceGenerator
import kioli.rx.entity.Quote

class QuoteModel : QuoteContract.QuoteModel {

    override fun callData(): Flowable<Quote> {
        return ServiceGenerator.getService(QuoteApi::class.java).loadQuote()
    }
}