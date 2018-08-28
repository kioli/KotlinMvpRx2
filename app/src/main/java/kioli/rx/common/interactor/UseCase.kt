package kioli.rx.common.interactor

import kioli.rx.common.functional.Either
import kioli.rx.common.error.Error

abstract class UseCase<in Params, out ReturnType> where ReturnType : Any {

    abstract fun run(params: Params): Either<Error, ReturnType>
}
