package kioli.rx.mvp

import io.reactivex.disposables.CompositeDisposable
import kioli.rx.api.FlowableManagerI
import kioli.rx.api.SchedulerProviderI

internal abstract class BasePresenter constructor(
        override val flowableManager: FlowableManagerI,
        override val schedulerProvider: SchedulerProviderI) : PresenterI {

    private val mDisposables = CompositeDisposable()

    override val disposables: CompositeDisposable
        get() = mDisposables
}