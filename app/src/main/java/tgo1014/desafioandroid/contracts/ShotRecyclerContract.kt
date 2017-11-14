package tgo1014.desafioandroid.contracts

import tgo1014.desafioandroid.adapters.ShotViewHolder

interface ShotRecyclerContract {

    interface ShotRowView {
        fun setImage(drawable: String)
        fun startDetailActivity(idShotDetail: Int)
    }

    interface ShotRowPresenter {
        fun onBindShotRowViewAtPosition(holder: ShotViewHolder, position: Int)
        fun getShotRowsCount(): Int
        fun onItemClick(holder: ShotViewHolder, adapterPosition: Int)
    }
}