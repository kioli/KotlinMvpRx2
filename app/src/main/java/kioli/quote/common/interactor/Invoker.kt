package kioli.quote.common.interactor

import kioli.quote.common.functional.Either
import kioli.quote.common.error.Error

interface Invoker {

    fun <Params, Type : Any> execute(useCase: UseCase<Params, Type>,
                                     params: Params,
                                     onResult: (Either<Error, Type>) -> Unit)
}
