package kioli.rx

import kioli.rx.common.error.Error
import kioli.rx.common.functional.Either
import kioli.rx.common.interactor.Invoker
import kioli.rx.common.interactor.UseCase
import kotlinx.coroutines.experimental.runBlocking

/**
 * Blocking implementation of the invoker.
 * Blocks the current thread to complete the invoked UseCase by using runBlocking {}.
 */
class BlockingUseCaseInvoker : Invoker {

    override fun <Params, Type : Any> execute(useCase: UseCase<Params, Type>,
                                              params: Params,
                                              onResult: (Either<Error, Type>) -> Unit) {
        onResult.invoke(runBlocking { useCase.run(params) })
    }
}
