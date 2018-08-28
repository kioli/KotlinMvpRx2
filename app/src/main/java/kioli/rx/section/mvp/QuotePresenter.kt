package kioli.rx.section.mvp

import kioli.rx.common.functional.Either
import kioli.rx.common.error.Error
import kioli.rx.common.interactor.Invoker
import kioli.rx.common.interactor.UseCase
import kioli.rx.common.mvp.BasePresenter
import kioli.rx.common.entity.Quote
import kioli.rx.section.mvp.QuoteContract.Presenter
import kioli.rx.section.interactor.QuoteUseCase

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