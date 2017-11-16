package tgo1014.desafioandroid.base

import android.support.v7.app.AppCompatActivity

abstract class BaseMvpActivity<P : BasePresenter<V>, V> : AppCompatActivity() {

    private var presenter: P? = null
    private fun getPresenterView(): V = this as V

    override fun onStart() {
        super.onStart()
        presenter?.attachView(getPresenterView())
    }

    override fun onStop() {
        presenter?.detachView()
        super.onStop()
    }

    abstract override fun onRetainCustomNonConfigurationInstance(): Any

    abstract fun attachPresenter()
}
