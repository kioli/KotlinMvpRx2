package kioli.rx.mvp

import io.reactivex.disposables.CompositeDisposable
import kioli.rx.api.FlowableManagerI
import kioli.rx.api.SchedulerProviderI

internal interface PresenterI {

    val flowableManager: FlowableManagerI
    val schedulerProvider: SchedulerProviderI
    val disposables: CompositeDisposable

    fun start()
}