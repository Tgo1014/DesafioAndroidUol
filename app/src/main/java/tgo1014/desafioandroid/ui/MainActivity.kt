package tgo1014.desafioandroid.ui

import android.content.res.Configuration
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import tgo1014.desafioandroid.R
import tgo1014.desafioandroid.adapters.ShotRecyclerAdapter
import tgo1014.desafioandroid.adapters.ShotRecyclerPresenter
import tgo1014.desafioandroid.contracts.MainContract
import tgo1014.desafioandroid.listeners.GridEndlessRecyclerViewScrollListener
import tgo1014.desafioandroid.model.MainModel
import tgo1014.desafioandroid.model.Shot
import tgo1014.desafioandroid.presenters.MainPresenterImpl

class MainActivity : AppCompatActivity(), MainContract.MainView {

    private lateinit var mainPresenter: MainContract.MainPresenter
    private lateinit var mainRecyclerAdapter: ShotRecyclerAdapter
    private var mainRecyclerShotList: MutableList<Shot> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainPresenter = MainPresenterImpl(this, MainModel())
    }

    override fun onDestroy() {
        super.onDestroy()
        mainPresenter.detach()
    }

    override fun onResume() {
        super.onResume()
        mainPresenter.onViewShowing()
    }

    override fun showLoading() {
        mainRecycler.visibility = View.GONE
        mainProgressBar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        mainRecycler.visibility = View.VISIBLE
        mainProgressBar.visibility = View.GONE
    }

    override fun showShots(shotList: List<Shot>) {
        mainRecyclerShotList.addAll(shotList)
        mainRecyclerAdapter = ShotRecyclerAdapter(ShotRecyclerPresenter(mainRecyclerShotList))
        mainRecycler.adapter = mainRecyclerAdapter

        val gridLayoutManager = GridLayoutManager(this, calcImageGridSizeByOrientation())
        mainRecycler.layoutManager = gridLayoutManager

        mainRecycler.addOnScrollListener(GridEndlessRecyclerViewScrollListener(gridLayoutManager, GridEndlessRecyclerViewScrollListener.DataLoader {
            mainPresenter.loadMoreShots()
            true
        }))
    }

    override fun onLoadMoreShots(shotList: List<Shot>) {
        mainRecyclerShotList.addAll(shotList)
        mainRecyclerAdapter.notifyItemRangeInserted(mainRecyclerShotList.size - shotList.size, mainRecyclerShotList.size)
    }

    override fun showError(error: String) {
        Snackbar
                .make(findViewById(R.id.mainLinearLayout), getString(R.string.str_error) + ":" + error, Snackbar.LENGTH_INDEFINITE)
                .setAction(getString(R.string.str_try_again), { mainPresenter.onSnackBarClicked() })
                .show()
    }

    private fun calcImageGridSizeByOrientation(): Int {
        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE)
            return 3
        return 2
    }
}


