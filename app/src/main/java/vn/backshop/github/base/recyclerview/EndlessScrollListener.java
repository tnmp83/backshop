package vn.backshop.github.base.recyclerview;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.widget.AbsListView;

public abstract class EndlessScrollListener extends RecyclerView.OnScrollListener {
    // The minimum amount of items to have below your current scroll position
    // before loading more.
    private int visibleThreshold = 5;
    // The current offset index of data you have loaded
    private int currentPage = 0;
    // The total number of items in the dataset after the last load
    private int previousTotalItemCount = 0;
    // True if we are still waiting for the last set of data to load.
    private boolean loading = true;
    // Sets the starting page index
    private int startingPageIndex = 0;
    // Scroll state
    private int scrollY = 0;
    private int itemCount = 0;
    private boolean isSending = false;
    private static final int SCROLL_HEIGHT = 500;
    private int topVisibleItemPosition = 0;
    RecyclerView.LayoutManager mLayoutManager;

    public EndlessScrollListener(LinearLayoutManager layoutManager) {
        this.mLayoutManager = layoutManager;
    }

    public EndlessScrollListener(GridLayoutManager layoutManager) {
        this.mLayoutManager = layoutManager;
        visibleThreshold = visibleThreshold * layoutManager.getSpanCount();
    }

    public EndlessScrollListener(StaggeredGridLayoutManager layoutManager) {
        this.mLayoutManager = layoutManager;
        visibleThreshold = visibleThreshold * layoutManager.getSpanCount();
    }

    private int getLastVisibleItem(int[] lastVisibleItemPositions) {
        int maxSize = 0;
        for (int i = 0; i < lastVisibleItemPositions.length; i++) {
            if (i == 0) {
                maxSize = lastVisibleItemPositions[i];
            } else if (lastVisibleItemPositions[i] > maxSize) {
                maxSize = lastVisibleItemPositions[i];
            }
        }
        return maxSize;
    }

    private int getTopVisibleItem(int[] topVisibleItemPosition) {
        int minSize = 0;
        for (int i = 0; i < topVisibleItemPosition.length; i++) {
            if (i == 0) {
                minSize = topVisibleItemPosition[i];
            } else if (topVisibleItemPosition[i] < minSize) {
                minSize = topVisibleItemPosition[i];
            }
        }
        return minSize;
    }

    // This happens many times a second during a scroll, so be wary of the code you place here.
    // We are given a few useful parameters to help us work out if we need to load some more data,
    // but first we check if we are waiting for the previous load to finish.
    @Override
    public void onScrolled(RecyclerView view, int dx, int dy) {
        // Get scroll by Y
        scrollY = dy;
        int lastVisibleItemPosition = 0;
        int totalItemCount = mLayoutManager.getItemCount();
        itemCount = totalItemCount;
        if (mLayoutManager instanceof StaggeredGridLayoutManager) {
            int[] lastVisibleItemPositions = ((StaggeredGridLayoutManager) mLayoutManager).findLastVisibleItemPositions(null);
            int[] topVisibleItemPositions = ((StaggeredGridLayoutManager) mLayoutManager).findFirstVisibleItemPositions(null);
            // get maximum element within the list
            lastVisibleItemPosition = getLastVisibleItem(lastVisibleItemPositions);
            topVisibleItemPosition = getTopVisibleItem(topVisibleItemPositions);
        } else if (mLayoutManager instanceof LinearLayoutManager) {
            lastVisibleItemPosition = ((LinearLayoutManager) mLayoutManager).findLastVisibleItemPosition();
            topVisibleItemPosition = ((LinearLayoutManager) mLayoutManager).findFirstVisibleItemPosition();
        } else if (mLayoutManager instanceof GridLayoutManager) {
            lastVisibleItemPosition = ((GridLayoutManager) mLayoutManager).findLastVisibleItemPosition();
            topVisibleItemPosition = ((GridLayoutManager) mLayoutManager).findFirstVisibleItemPosition();
        }

        // If the total item count is zero and the previous isn't, assume the
        // list is invalidated and should be reset back to initial state
        if (totalItemCount < previousTotalItemCount) {
            this.currentPage = this.startingPageIndex;
            this.previousTotalItemCount = totalItemCount;
            if (totalItemCount == 0) {
                this.loading = true;
            }
        }
        // If it’s still loading, we check to see if the dataset count has
        // changed, if so we conclude it has finished loading and update the current page
        // number and total item count.
        if (loading && (totalItemCount > previousTotalItemCount)) {
            loading = false;
            previousTotalItemCount = totalItemCount;
        }

        // If it isn’t currently loading, we check to see if we have breached
        // the visibleThreshold and need to reload more data.
        // If we do need to reload some more data, we execute onLoadMore to fetch the data.
        // threshold should reflect how many total columns there are too
        if (!loading && (lastVisibleItemPosition + visibleThreshold) > totalItemCount) {
            currentPage++;
            onLoadMore(currentPage, totalItemCount);
            loading = true;
        }
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);

        if (newState == AbsListView.OnScrollListener.SCROLL_STATE_FLING) {
            if (!isSending) {
//                boolean isTouching = false;
//                if (0 < scrollY) {
//                    // Scrolling up
//                    onScrollUp();
//                } else {
//                    // Scrolling down
//                    handleScrollState();
////                    if (SCROLL_HEIGHT < totalScrollY) {
////                        onScrollUp();
////                    } else {
////                        onScrollDown();
////                    }
//                }
                handleScrollState();
                isSending = true;
            }
        } else if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
            isSending = false;
        } else {
            //DebugLog.showError("SCROLL_STATE_IDLE - SCROLL_STATE_IDLE - SCROLL_STATE_IDLE - SCROLL_STATE_IDLE");
            handleScrollState();
//            if(SCROLL_HEIGHT < totalScrollY){
//                onScrollUp();
//            }else{
//                onScrollDown();
//            }
        }
    }


    private void handleScrollState() {
        if (7 < itemCount) {
            if (0 < scrollY) {
                // Scrolling up
                onScrollUp();
            } else {
                // Scrolling down
                onScrollDown();
            }
        }
        if (0 == topVisibleItemPosition) {
            if (0 >= scrollY) {
                onScrollTop();
            }
        }
    }


//    private void handleScrollUpDown(int totalY, int dy){
//        if(0 < dy){
//            // Scrolling up
//            onScrollUp();
//        }else{
//            // Scrolling down
//            onScrollDown();
//        }
//
//        scrollY = scrollY + dy;
//
//        if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
//            isSending = false;
//            if(!isTouching){
//                if((500 < scrollY) && (0 < dy)){
//                    isSending = true;
//                }
//                isTouching = true;
//            }
//        } else if (AbsListView.OnScrollListener.SCROLL_STATE_FLING == newState) {
//            if(!isSending){
//
//                isSending = true;
//            }
//        }
//    }

    // Defines the process for actually loading more data based on page
    public abstract void onLoadMore(int page, int totalItemsCount);

    void onScrollUp() {
    }

    void onScrollDown() {
    }

    void onScrollTop() {
    }

    // Call this method whenever performing new searches
    void resetState() {
        this.currentPage = this.startingPageIndex;
        this.previousTotalItemCount = 0;
        this.loading = true;
    }
}
