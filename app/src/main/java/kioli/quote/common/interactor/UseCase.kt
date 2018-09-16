package kioli.quote.common.interactor

import kioli.quote.common.functional.Either
import kioli.quote.common.error.Error

abstract class UseCase<in Params, out ReturnType> where ReturnType : Any {

    abstract fun run(params: Params): Either<Error, ReturnType>
}
