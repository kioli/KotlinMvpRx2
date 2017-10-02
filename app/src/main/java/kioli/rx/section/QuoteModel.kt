package kioli.rx.section

import io.reactivex.Flowable
import kioli.rx.api.QuoteApi
import kioli.rx.api.ServiceGenerator
import kioli.rx.entity.Quote

internal class QuoteModel(private val serviceGeneratorWrapper: ServiceGenerator.ServiceGeneratorWrapper)
    : QuoteContract.QuoteModel {

    override fun fetchQuote(): Flowable<Quote> {
        return serviceGeneratorWrapper.getService(QuoteApi::class.java).loadQuote()
    }
}