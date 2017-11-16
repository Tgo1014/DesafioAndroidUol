package tgo1014.desafioandroid.presenters

import tgo1014.desafioandroid.R
import tgo1014.desafioandroid.contracts.MainContract
import tgo1014.desafioandroid.model.objects.Shot


class MainPresenterImpl(private var mainModel: MainContract.MainModel) : MainContract.MainPresenter {

    private var mainView: MainContract.MainView? = null

    private val ORDER_COMMENT = "comments"
    private val ORDER_VIEWS = "views"
    private val ORDER_MOST_RECENT = "recent"

    private var page: Int = 1
    private var selectedOrder = ORDER_VIEWS
    private var swipeRefresh = false

    override fun attachView(view: MainContract.MainView) {
        this.mainView = view
        mainView?.showLoading()

        if (mainModel.restoreShotsCache().isEmpty()) {
            requestShots(page)
            return
        }
        mainView?.hideLoading()
        mainView?.showShots(mainModel.restoreShotsCache())
    }

    override fun detachView() {
        mainView = null
    }

    override fun onDestroy(shotList: List<Shot>) {
        mainModel.saveShotsCache(shotList)
    }

    override fun onSnackBarClicked() {
        mainView?.showLoading()
        requestShots(page)
    }

    override fun loadMoreShots() {
        page++
        requestMoreShots(page)
    }

    override fun onSwipeToRefresh() {
        page = 1
        swipeRefresh = true
        requestShots(page)
    }

    override fun onOrderMenuItemClick(itemId: Int) {
        page = 1
        val oldSelectecOrder = selectedOrder

        selectedOrder = when (itemId) {
            R.id.menuMainRecent -> ORDER_MOST_RECENT
            R.id.menuMainComment -> ORDER_COMMENT
            R.id.menuMainView -> ORDER_VIEWS
            else -> selectedOrder
        }

        if (selectedOrder != oldSelectecOrder) {
            mainView?.showLoading()
            mainModel.clearShotsCache()
            requestShots(page, selectedOrder)
        }
    }

    private fun requestShots(page: Int, order: String = ORDER_VIEWS) {
        mainView?.showLoadingToolbar()
        mainModel.requestShots(page, order, object : MainContract.MainModel.OnShotsRequestCompletionListener {
            override fun onSucess(shotList: List<Shot>) {
                mainView?.hideLoading()
                mainView?.hideLoadingToolbar()
                mainView?.showShots(shotList)
                if (swipeRefresh) mainView?.refreshDone()
            }

            override fun onError(error: String) {
                mainView?.hideLoading()
                mainView?.hideLoadingToolbar()
                mainView?.showError(error)
                if (swipeRefresh) mainView?.refreshDone()
            }
        })
    }

    private fun requestMoreShots(page: Int, order: String = selectedOrder) {
        mainView?.showLoadingToolbar()
        mainModel.requestShots(page, order, object : MainContract.MainModel.OnShotsRequestCompletionListener {
            override fun onSucess(shotList: List<Shot>) {
                mainView?.hideLoadingToolbar()
                mainView?.onLoadMoreShots(shotList)
            }

            override fun onError(error: String) {
                mainView?.hideLoadingToolbar()
                mainView?.showError(error)
            }
        })
    }
}