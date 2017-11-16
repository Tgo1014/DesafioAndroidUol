package tgo1014.desafioandroid.base

interface BasePresenter<V> {
    fun attachView(view: V)
    fun detachView()
}