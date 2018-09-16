package kioli.quote.section.repository

import kioli.quote.common.api.QuoteApi
import kioli.quote.common.entity.Quote
import kioli.quote.common.error.Error
import kioli.quote.common.functional.Either
import kioli.quote.common.functional.Try
import kioli.quote.common.functional.left
import kioli.quote.common.functional.right
import kioli.quote.common.repository.NetworkDataSource

class QuoteNotFound : Error.FeatureError()

internal class QuoteNetworkDataSource(private val service: QuoteApi) : NetworkDataSource {

    override fun getQuote(): Either<Error, Quote> =
            Try {
                service.loadQuote().execute()
            }.fold(ifFailure = {
                Error.ServerError.left()
            }, ifSuccess = { response ->
                if (response.isSuccessful) {
                    val body = response.body()!!
                    body.right()
                } else QuoteNotFound().left()
            })
}
