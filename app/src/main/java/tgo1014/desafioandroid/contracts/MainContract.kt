package tgo1014.desafioandroid.contracts

import tgo1014.desafioandroid.model.Shot


interface MainContract {

    interface MainView {
        fun showLoading()
        fun hideLoading()
        fun showShots(shotList: List<Shot>)
        fun showError(error: String)
        fun onLoadMoreShots(shotList: List<Shot>)
    }

    interface MainPresenter {
        fun onViewShowing()
        fun loadMoreShots()
        fun onSnackBarClicked()
        fun detach()
    }

    interface MainModel {
        interface OnShotsRequestCompletionListener {
            fun onSucess(shotList: List<Shot>)
            fun onError(error: String)
        }

        fun requestShots(page: Int, listener: OnShotsRequestCompletionListener)
    }
}