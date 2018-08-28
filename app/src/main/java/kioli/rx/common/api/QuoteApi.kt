package kioli.rx.common.api

import kioli.rx.common.entity.Quote
import retrofit2.Call
import retrofit2.http.GET

/**
 * Classic Retrofit2 interface to define network endpoints
 */
internal interface QuoteApi {

    @GET("?method=getQuote&format=json&lang=en")
    fun loadQuote(): Call<Quote>
}