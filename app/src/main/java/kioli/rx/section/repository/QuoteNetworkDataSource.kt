package kioli.rx.section.repository

import kioli.rx.common.api.QuoteApi
import kioli.rx.common.entity.Quote
import kioli.rx.common.error.Error
import kioli.rx.common.functional.Either
import kioli.rx.common.functional.Try
import kioli.rx.common.functional.left
import kioli.rx.common.functional.right
import kioli.rx.common.repository.NetworkDataSource

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
