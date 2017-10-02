package kioli.rx.mvp

import io.reactivex.disposables.CompositeDisposable
import kioli.rx.api.FlowableManager
import kioli.rx.api.SchedulerProvider

internal open class BasePresenter<T : IView> constructor(
        protected val flowableManager: FlowableManager.FlowableManagerWrapper,
        protected val schedulerProvider: SchedulerProvider.SchedulerProviderWrapper)
    : IPresenter<T> {

    protected var view: T? = null
    protected val disposables: CompositeDisposable = CompositeDisposable()

    private val isViewAttached: Boolean
        get() = view != null

    override fun attachView(view: T) {
        this.view = view
    }

    override fun detachView() {
        view = null
        disposables.clear()
    }

    fun checkViewAttached() {
        if (!isViewAttached) throw MvpViewNotAttachedException()
    }

    class MvpViewNotAttachedException : RuntimeException("Call IPresenter.attachView(IView) before requesting data to the IPresenter")

}