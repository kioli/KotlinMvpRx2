package kioli.quote.section.interactor

import kioli.quote.common.entity.Quote
import kioli.quote.common.error.Error
import kioli.quote.common.functional.Either
import kioli.quote.common.interactor.UseCase
import kioli.quote.common.repository.CachePolicy
import kioli.quote.section.repository.QuoteRepository

internal class QuoteUseCase(private val repository: QuoteRepository)
    : UseCase<QuoteUseCase.Params, Quote>() {

    override fun run(params: Params): Either<Error, Quote> =
            when (params.forceNew) {
                true -> repository.getQuote(CachePolicy.NetworkFirst)
                false -> repository.getQuote(CachePolicy.LocalFirst)
            }

    data class Params(val forceNew: Boolean)
}