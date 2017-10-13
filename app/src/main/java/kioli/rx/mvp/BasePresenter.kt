package kioli.rx.mvp

import io.reactivex.disposables.CompositeDisposable
import kioli.rx.api.FlowableManagerI
import kioli.rx.api.SchedulerProviderI

internal abstract class BasePresenter constructor(
        protected val flowableManager: FlowableManagerI,
        protected val schedulerProvider: SchedulerProviderI) : PresenterI {

    protected val disposables: CompositeDisposable = CompositeDisposable()

}