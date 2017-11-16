package tgo1014.desafioandroid.presenters

import tgo1014.desafioandroid.contracts.DetailContract
import tgo1014.desafioandroid.model.objects.Shot

class DetailPresenterImpl(private var detailModel: DetailContract.DetailModel) : DetailContract.DetailPresenter {

    private var detailView: DetailContract.DetailView? = null

    override fun attachView(view: DetailContract.DetailView) {
        this.detailView = view
        detailView?.showLoadingToolbar()
        detailView?.getShotIdExtra()
    }

    override fun detachView() {
        this.detailView = null
    }

    override fun onDestroy(shot: Shot?) {
        detailModel.saveShotsCache(shot)
    }

    override fun shotObtained(idShot: Int) {
        requestShot(idShot)
    }

    override fun onOptionsItemSelected(itemId: Int) {
        when (itemId) {
            android.R.id.home -> detailView?.onBackPressed()
        }
    }

    private fun requestShot(shotId: Int) {
        if (detailModel.restoreShotsCache() != null) {
            detailView?.hideLoadingToolbar()
            detailView?.showShotDetail(detailModel.restoreShotsCache()!!)
            return
        }

        detailModel.requestShot(shotId, object : DetailContract.DetailModel.OnShotRequestCompletionListener {
            override fun onSucess(shot: Shot) {
                detailView?.hideLoadingToolbar()
                detailView?.showShotDetail(shot)
            }

            override fun onError(error: String) {
                detailView?.hideLoadingToolbar()
                detailView?.showError()
            }
        })
    }
}