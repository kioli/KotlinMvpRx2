package kioli.rx.mvp

internal open class BasePresenter<T : IView> : IPresenter<T> {

    protected var view: T? = null

    private val isViewAttached: Boolean
        get() = view != null

    override fun attachView(view: T) {
        this.view = view
    }

    override fun detachView() {
        view = null
    }

    fun checkViewAttached() {
        if (!isViewAttached) throw MvpViewNotAttachedException()
    }

    class MvpViewNotAttachedException : RuntimeException("Call IPresenter.attachView(IView) before requesting data to the IPresenter")

}