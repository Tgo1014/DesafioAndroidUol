package tgo1014.desafioandroid.adapters

import tgo1014.desafioandroid.contracts.ShotRecyclerContract
import tgo1014.desafioandroid.model.objects.Shot

class ShotRecyclerPresenter(private var shotList: List<Shot>) : ShotRecyclerContract.ShotRowPresenter {

    override fun onItemClick(holder: ShotViewHolder, adapterPosition: Int) {
        holder.startDetailActivity(shotList[adapterPosition].id)
    }

    override fun onBindShotRowViewAtPosition(holder: ShotViewHolder, position: Int) {
        holder.setImage(shotList[position].images.normal)
    }

    override fun getShotRowsCount(): Int {
        return shotList.size
    }
}