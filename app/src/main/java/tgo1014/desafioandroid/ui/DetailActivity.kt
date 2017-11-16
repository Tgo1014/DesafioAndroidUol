package tgo1014.desafioandroid.ui

import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.Html
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*
import tgo1014.desafioandroid.R
import tgo1014.desafioandroid.base.BaseMvpActivity
import tgo1014.desafioandroid.contracts.DetailContract
import tgo1014.desafioandroid.model.DetailModelImpl
import tgo1014.desafioandroid.model.objects.Shot
import tgo1014.desafioandroid.presenters.DetailPresenterImpl

class DetailActivity : BaseMvpActivity<DetailContract.DetailPresenter, DetailContract.DetailView>(), DetailContract.DetailView {

    private var detailPresenter: DetailContract.DetailPresenter? = null
    private var SHOT_ID_EXTRA = "shotIdExtra"
    private var shot: Shot? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setSupportActionBar(detailToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val upArrow: Drawable = resources.getDrawable(R.drawable.abc_ic_ab_back_material)
        upArrow.setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_ATOP)
        supportActionBar?.setHomeAsUpIndicator(upArrow)

        attachPresenter()
    }

    override fun onDestroy() {
        super.onDestroy()
        detailPresenter?.detachView()
        detailPresenter?.onDestroy(shot)
    }

    override fun showLoadingToolbar() {
        detailTollbarProgressBar.visibility = View.VISIBLE
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        detailPresenter?.onOptionsItemSelected(item.itemId)
        return true
    }

    override fun hideLoadingToolbar() {
        detailTollbarProgressBar.visibility = View.GONE
    }

    override fun attachPresenter() {
        detailPresenter = lastCustomNonConfigurationInstance as DetailContract.DetailPresenter?
        if (detailPresenter == null) {
            detailPresenter = DetailPresenterImpl(DetailModelImpl())
        }
        detailPresenter?.attachView(this)
    }

    override fun showShotDetail(shot: Shot) {

        this.shot = shot
        supportActionBar?.title = shot.title

        detailTollbarProgressBar.visibility = View.VISIBLE

        Picasso.with(this)
                .load(shot.images.hidpi)
                .fit()
                .centerInside()
                .placeholder(R.drawable.dribbble_logo)
                .into(detailImageView, object : Callback {
                    override fun onSuccess() {
                        detailTollbarProgressBar.visibility = View.GONE
                    }

                    override fun onError() {
                        detailTollbarProgressBar.visibility = View.GONE
                        Toast.makeText(this@DetailActivity, "Unable to load image", Toast.LENGTH_SHORT).show()
                    }
                })

        Picasso.with(this)
                .load(shot.user.avatar_url)
                .fit()
                .centerInside()
                .placeholder(R.drawable.dribbble_logo)
                .into(detailAvatar)

        detailTxtComments.text = shot.comments_count.toString()
        detailTxtFavorites.text = shot.likes_count.toString()
        detailTxtViews.text = shot.views_count.toString()
        detailUserName.text = shot.user.name
        detailUserUsername.text = shot.user.username
        detailDescription.text = Html.fromHtml(shot.description ?: "")
    }

    override fun onRetainCustomNonConfigurationInstance(): Any = detailPresenter!!

    override fun showError() {

    }

    override fun getShotIdExtra() {
        detailPresenter?.shotObtained(intent.getIntExtra(SHOT_ID_EXTRA, -1))
    }
}
