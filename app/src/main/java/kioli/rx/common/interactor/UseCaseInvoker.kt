package kioli.rx.common.interactor

import kioli.rx.common.error.Error
import kioli.rx.common.functional.Either
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch

class UseCaseInvoker : Invoker {

    override fun <Params, Type : Any> execute(useCase: UseCase<Params, Type>,
                                              params: Params,
                                              onResult: (Either<Error, Type>) -> Unit) {
        val job = async(CommonPool) { useCase.run(params) }
        launch(UI) { onResult.invoke(job.await()) }
    }
}
