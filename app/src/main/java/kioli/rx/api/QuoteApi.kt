package kioli.rx.api

import io.reactivex.Flowable
import kioli.rx.entity.Quote
import retrofit2.http.GET

/**
 * Classic Retrofit2 interface to define network endpoints
 */
internal interface QuoteApi {

    @GET("?method=getQuote&format=json&lang=en")
    fun loadQuote(): Flowable<Quote>
}