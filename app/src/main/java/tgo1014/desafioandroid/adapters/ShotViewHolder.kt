package tgo1014.desafioandroid.adapters

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_shot_grid.view.*
import tgo1014.desafioandroid.R
import tgo1014.desafioandroid.contracts.ShotRecyclerContract
import tgo1014.desafioandroid.ui.DetailActivity

class ShotViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), ShotRecyclerContract.ShotRowView, View.OnClickListener {

    private lateinit var imageView: ImageView
    private lateinit var presenter: ShotRecyclerContract.ShotRowPresenter

    private var SHOT_ID_EXTRA = "shotIdExtra"

    constructor(presenter: ShotRecyclerContract.ShotRowPresenter, itemView: View) : this(itemView) {
        this.presenter = presenter
        this.imageView = itemView.gridShotImage
        itemView.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        presenter.onItemClick(this@ShotViewHolder, adapterPosition)
    }

    override fun setImage(drawable: String) {
        Picasso.with(itemView.context)
                .load(drawable)
                .fit()
                .centerCrop()
                .placeholder(R.drawable.loading_animation)
                .into(imageView)
    }

    override fun startDetailActivity(idShotDetail: Int) {
        val intent = Intent(itemView.context, DetailActivity::class.java)
        intent.putExtra(SHOT_ID_EXTRA, idShotDetail)
        itemView.context.startActivity(intent)
    }
}