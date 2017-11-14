package tgo1014.desafioandroid.listeners;

/*
  Source code https://gist.github.com/fatihsokmen/5ba507e6dfff88f74a6de4ffd074cb4e
 */

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

public class GridEndlessRecyclerViewScrollListener extends RecyclerView.OnScrollListener {
    private GridLayoutManager gridLayoutManager;
    private DataLoader dataLoader;
    private int previousItemCount;
    private boolean loading;

    public GridEndlessRecyclerViewScrollListener(GridLayoutManager gridLayoutManager, DataLoader dataLoader) {
        this.gridLayoutManager = gridLayoutManager;
        this.dataLoader = dataLoader;
        reset();
    }


    @Override
    public void onScrolled(RecyclerView view, int dx, int dy) {
        if (dy > 0) {
            int itemCount = gridLayoutManager.getItemCount();

            if (itemCount != previousItemCount) {
                loading = false;
            }

            if (!loading && gridLayoutManager.findLastVisibleItemPosition() >= itemCount - 1) {
                previousItemCount = itemCount;
                loading = dataLoader.onLoadMore();
            }
        }
    }

    public void reset() {
        this.loading = false;
        this.previousItemCount = -1;
    }

    public interface DataLoader {
        boolean onLoadMore();
    }
}
