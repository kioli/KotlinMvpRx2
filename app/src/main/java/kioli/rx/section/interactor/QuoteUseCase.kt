package kioli.rx.section.interactor

import kioli.rx.common.error.Error
import kioli.rx.common.functional.Either
import kioli.rx.common.interactor.UseCase
import kioli.rx.common.repository.CachePolicy
import kioli.rx.common.entity.Quote
import kioli.rx.section.repository.QuoteRepository

internal class QuoteUseCase(private val repository: QuoteRepository) : UseCase<QuoteUseCase.Params, Quote>() {

    override fun run(params: Params): Either<Error, Quote> = when (params.forceNew) {
        true -> {
            repository.getQuote(CachePolicy.NetworkFirst)
        }
        false -> {
            repository.getQuote(CachePolicy.LocalFirst)
        }
    }

    data class Params(val forceNew: Boolean)
}