package tgo1014.desafioandroid.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*
import tgo1014.desafioandroid.R
import tgo1014.desafioandroid.contracts.DetailContract
import tgo1014.desafioandroid.model.DetailModel
import tgo1014.desafioandroid.model.Shot
import tgo1014.desafioandroid.presenters.DetailPresenterImpl

class DetailActivity : AppCompatActivity(), DetailContract.DetailView {

    private lateinit var detailPresenter: DetailContract.DetailPresenter
    private var SHOT_ID_EXTRA = "shotIdExtra"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        detailPresenter = DetailPresenterImpl(this, DetailModel())
    }

    override fun onDestroy() {
        super.onDestroy()
        detailPresenter.detach()
    }

    override fun onResume() {
        super.onResume()
        detailPresenter.onViewShowing()
    }


    override fun showShotDetail(shot: Shot) {

        supportActionBar?.title = shot.title

        Picasso.with(this)
                .load(shot.images.hidpi)
                .fit()
                .centerInside()
                .into(detailImageView)

        detailTxtComments.text = shot.comments_count.toString()
        detailTxtFavorites.text = shot.likes_count.toString()
        detailTxtViews.text = shot.views_count.toString()
    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun showError() {

    }

    override fun getShotIdExtra() {
        detailPresenter.shotObtained(intent.getIntExtra(SHOT_ID_EXTRA, -1))
    }
}
