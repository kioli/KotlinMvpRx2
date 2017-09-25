package kioli.rx.mvp

internal interface IPresenter<in V> {

    fun attachView(view: V)

    fun detachView()
}