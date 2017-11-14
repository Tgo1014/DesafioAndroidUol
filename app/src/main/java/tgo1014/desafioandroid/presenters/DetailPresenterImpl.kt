package tgo1014.desafioandroid.presenters

import tgo1014.desafioandroid.contracts.DetailContract
import tgo1014.desafioandroid.model.Shot

class DetailPresenterImpl(private var detailView: DetailContract.DetailView?, private var detailModel: DetailContract.DetailModel) : DetailContract.DetailPresenter {

    override fun shotObtained(idShot: Int) {
        requestShot(idShot)
    }

    override fun onViewShowing() {
        detailView?.showLoading()
        detailView?.getShotIdExtra()
    }

    override fun detach() {
        detailView = null
    }

    fun requestShot(shotId: Int) {
        detailModel.requestShot(shotId, object : DetailContract.DetailModel.OnShotRequestCompletionListener {
            override fun onSucess(shot: Shot) {
                detailView?.hideLoading()
                detailView?.showShotDetail(shot)
            }

            override fun onError(error: String) {
                detailView?.hideLoading()
                detailView?.showError()
            }
        })
    }
}