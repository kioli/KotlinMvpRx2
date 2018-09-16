package kioli.quote.common.interactor

import kioli.quote.common.error.Error
import kioli.quote.common.functional.Either
import kotlinx.coroutines.experimental.CoroutineDispatcher
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch

class UseCaseInvoker(private val dispatcher: CoroutineDispatcher) : Invoker {

    override fun <Params, Type : Any> execute(useCase: UseCase<Params, Type>,
                                              params: Params,
                                              onResult: (Either<Error, Type>) -> Unit) {
        val job = async(dispatcher) { useCase.run(params) }
        launch(UI) { onResult.invoke(job.await()) }
    }
}
