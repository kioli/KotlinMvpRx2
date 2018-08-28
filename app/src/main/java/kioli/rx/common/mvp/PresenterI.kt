package kioli.rx.common.mvp

internal interface PresenterI<in V : ViewI> {

    /**
     * Attach view to the presenter
     *
     * @param view View linked to the presenter
     */
    fun attachView(view: V)

    /**
     * Detach the view from the presenter
     */
    fun detachView()
}