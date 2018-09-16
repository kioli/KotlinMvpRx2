package kioli.quote.common.mvp

internal open class BasePresenter<V : ViewI> : PresenterI<V> {

    var view: V? = null

    /**
     * Attach view to the presenter
     *
     * @param view View linked to the presenter
     */
    override fun attachView(view: V) {
        this.view = view
    }

    /**
     * Detach the view from the presenter
     */
    override fun detachView() {
        this.view = null
    }
}