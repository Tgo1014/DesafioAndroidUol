package tgo1014.desafioandroid.presenters

import tgo1014.desafioandroid.contracts.MainContract
import tgo1014.desafioandroid.model.Shot


class MainPresenterImpl(private var mainView: MainContract.MainView?, private var mainModel: MainContract.MainModel) : MainContract.MainPresenter {

    var page: Int = 1

    override fun loadMoreShots() {
        page++
        requestMoreShots(page)
    }

    override fun onViewShowing() {
        mainView?.showLoading()
        requestShots(page)
    }

    override fun onSnackBarClicked() {
        mainView?.showLoading()
        requestShots(page)
    }

    override fun detach() {
        mainView = null
    }

    private fun requestShots(page: Int) {
        mainModel.requestShots(page, object : MainContract.MainModel.OnShotsRequestCompletionListener {
            override fun onSucess(shotList: List<Shot>) {
                mainView?.hideLoading()
                mainView?.showShots(shotList)
            }

            override fun onError(error: String) {
                mainView?.hideLoading()
                mainView?.showError(error)
            }
        })
    }

    private fun requestMoreShots(page: Int) {
        mainModel.requestShots(page, object : MainContract.MainModel.OnShotsRequestCompletionListener {
            override fun onSucess(shotList: List<Shot>) {
                mainView?.onLoadMoreShots(shotList)
            }

            override fun onError(error: String) {
                mainView?.showError(error)
            }
        })
    }
}