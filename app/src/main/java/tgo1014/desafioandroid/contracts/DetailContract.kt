package tgo1014.desafioandroid.contracts

import tgo1014.desafioandroid.base.BasePresenter
import tgo1014.desafioandroid.model.objects.Shot

interface DetailContract {

    interface DetailView {
        fun showShotDetail(shot: Shot)
        fun showError()
        fun getShotIdExtra()
        fun showLoadingToolbar()
        fun hideLoadingToolbar()
        fun onBackPressed()
    }

    interface DetailPresenter : BasePresenter<DetailView> {
        fun onDestroy(shot: Shot?)
        fun shotObtained(idShot: Int)
        fun onOptionsItemSelected(itemId: Int)
    }

    interface DetailModel {
        interface OnShotRequestCompletionListener {
            fun onSucess(shot: Shot)
            fun onError(error: String)
        }

        fun requestShot(shotId: Int, listener: OnShotRequestCompletionListener)
        fun saveShotsCache(shot: Shot?)
        fun restoreShotsCache(): Shot?
        fun clearShotsCache()
    }
}