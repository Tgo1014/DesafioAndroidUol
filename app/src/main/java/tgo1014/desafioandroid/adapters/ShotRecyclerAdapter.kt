package tgo1014.desafioandroid.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import tgo1014.desafioandroid.R
import tgo1014.desafioandroid.contracts.ShotRecyclerContract

class ShotRecyclerAdapter(private var presenter: ShotRecyclerContract.ShotRowPresenter) : RecyclerView.Adapter<ShotViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShotViewHolder {
        return ShotViewHolder(presenter, LayoutInflater.from(parent.context).inflate(R.layout.item_shot_grid, parent, false))
    }

    override fun onBindViewHolder(holder: ShotViewHolder, position: Int) {
        presenter.onBindShotRowViewAtPosition(holder, position)
    }

    override fun getItemCount(): Int {
        return presenter.getShotRowsCount()
    }
}