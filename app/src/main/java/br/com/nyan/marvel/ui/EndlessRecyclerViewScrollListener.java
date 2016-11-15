package br.com.nyan.marvel.ui;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public abstract class EndlessRecyclerViewScrollListener extends RecyclerView.OnScrollListener
{
    private int visibleThreshold = 5;
    private int previousTotalItemCount = 0;
    private boolean loading = true;
    private RecyclerView.LayoutManager mLayoutManager;

    public EndlessRecyclerViewScrollListener(LinearLayoutManager layoutManager)
    {
        this.mLayoutManager = layoutManager;
    }

    public EndlessRecyclerViewScrollListener(GridLayoutManager layoutManager)
    {
        this.mLayoutManager = layoutManager;
        visibleThreshold = visibleThreshold * layoutManager.getSpanCount();
    }

    @Override
    public void onScrolled(RecyclerView view, int dx, int dy)
    {
        int lastVisibleItemPosition = 0;
        int totalItemCount = mLayoutManager.getItemCount();

        if (mLayoutManager instanceof GridLayoutManager)
        {
            lastVisibleItemPosition = ((GridLayoutManager) mLayoutManager)
                    .findLastVisibleItemPosition();
        }
        else if (mLayoutManager instanceof LinearLayoutManager)
        {
            lastVisibleItemPosition = ((LinearLayoutManager) mLayoutManager)
                    .findLastVisibleItemPosition();
        }

        if (totalItemCount < previousTotalItemCount)
        {
            this.previousTotalItemCount = totalItemCount;
            if (totalItemCount == 0)
            {
                this.loading = true;
            }
        }

        if (loading && (totalItemCount > previousTotalItemCount))
        {
            loading = false;
            previousTotalItemCount = totalItemCount;
        }

        if (!loading && (lastVisibleItemPosition + visibleThreshold) > totalItemCount)
        {
            onLoadMore(view);
            loading = true;
        }
    }

    public void reset()
    {
        this.previousTotalItemCount = 0;
        this.loading = true;
    }

    public abstract void onLoadMore(RecyclerView view);
}