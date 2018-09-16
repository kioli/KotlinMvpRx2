package kioli.quote.section.mvp

import kioli.quote.common.functional.Either
import kioli.quote.common.error.Error
import kioli.quote.common.interactor.Invoker
import kioli.quote.common.interactor.UseCase
import kioli.quote.common.mvp.BasePresenter
import kioli.quote.common.entity.Quote
import kioli.quote.section.mvp.QuoteContract.Presenter
import kioli.quote.section.interactor.QuoteUseCase

internal class QuotePresenter(private val invoker: Invoker,
                              private val getQuote: UseCase<QuoteUseCase.Params, Quote>)
    : BasePresenter<QuoteContract.View>(), Presenter {

    override fun getQuote(forceNew: Boolean) {
        view?.showLoading()
        val params = QuoteUseCase.Params(forceNew)
        invoker.execute(getQuote, params, ::onQuoteArrived)
    }

    private fun onQuoteArrived(result: Either<Error, Quote>) {
        view?.hideLoading()
        result.fold(ifLeft = {
            view?.returnResultQuote(null)
        }, ifRight = {
            view?.returnResultQuote(it)
        })
    }
}