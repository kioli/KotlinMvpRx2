package kioli.rx.mvp

interface IPresenter<in V> {

    fun attachView(view: V)

    fun detachView()
}