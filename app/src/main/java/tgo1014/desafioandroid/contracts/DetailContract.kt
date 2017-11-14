package tgo1014.desafioandroid.contracts

import tgo1014.desafioandroid.model.Shot

interface DetailContract {

    interface DetailView {
        fun showShotDetail(shot: Shot)
        fun showLoading()
        fun hideLoading()
        fun showError()
        fun getShotIdExtra()
    }

    interface DetailPresenter {
        fun onViewShowing()
        fun detach()
        fun shotObtained(idShot: Int)
    }

    interface DetailModel {
        interface OnShotRequestCompletionListener {
            fun onSucess(shot: Shot)
            fun onError(error: String)
        }

        fun requestShot(shotId: Int, listener: OnShotRequestCompletionListener)
    }
}