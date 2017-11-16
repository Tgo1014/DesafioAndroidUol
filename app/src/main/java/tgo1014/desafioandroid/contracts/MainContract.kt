package tgo1014.desafioandroid.contracts

import tgo1014.desafioandroid.base.BasePresenter
import tgo1014.desafioandroid.model.objects.Shot

interface MainContract {

    interface MainView {
        fun showLoading()
        fun hideLoading()
        fun showShots(shotList: List<Shot>)
        fun showError(error: String)
        fun onLoadMoreShots(shotList: List<Shot>)
        fun refreshDone()
        fun showLoadingToolbar()
        fun hideLoadingToolbar()
    }

    interface MainPresenter : BasePresenter<MainView> {
        fun onDestroy(shotList: List<Shot>)
        fun loadMoreShots()
        fun onSnackBarClicked()
        fun onOrderMenuItemClick(itemId: Int)
        fun onSwipeToRefresh()
    }

    interface MainModel {
        interface OnShotsRequestCompletionListener {
            fun onSucess(shotList: List<Shot>)
            fun onError(error: String)
        }

        fun requestShots(page: Int, order: String, listener: OnShotsRequestCompletionListener)
        fun saveShotsCache(shotList: List<Shot>)
        fun restoreShotsCache(): List<Shot>
        fun clearShotsCache()
    }
}