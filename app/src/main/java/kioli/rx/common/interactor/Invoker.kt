package kioli.rx.common.interactor

import kioli.rx.common.functional.Either
import kioli.rx.common.error.Error

interface Invoker {

    fun <Params, Type : Any> execute(useCase: UseCase<Params, Type>,
                                     params: Params,
                                     onResult: (Either<Error, Type>) -> Unit)
}
