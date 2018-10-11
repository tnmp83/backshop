package vn.backshop.github.base.recyclerview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import vn.backshop.github.R;

public abstract class BaseAdapter<H extends RecyclerView.ViewHolder, D> extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private final int VIEW_TYPE_LOADING = 230283;

    public abstract void bindView(H holder, int position);
    public abstract H createView(ViewGroup parent, int viewType);

    private HolderListener listener;
    public void setOnItemListener(HolderListener listener){
        this.listener = listener;
    }
    public HolderListener getListener(){
        return listener;
    }

    private LayoutInflater inflate = null;
    private List<D> mList;
    public BaseAdapter(Context context){
        if(null == mList){
            mList = new ArrayList<D>();
        }
        inflate = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if(VIEW_TYPE_LOADING == viewType){
            return new LoadingHolder(getView(R.layout.loadmore_view, viewGroup));
        }
        return createView(viewGroup, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if(VIEW_TYPE_LOADING != viewHolder.getItemViewType()){
            bindView((H) viewHolder, position);
        }
    }

    @Override
    public int getItemCount() {
        if(null != mList){
            return mList.size();
        }
        return 0;
    }

    public View getView(int layoutResource, ViewGroup parent){
        return inflate.inflate(layoutResource, parent, false);
    }

    public D getItem(int position){
        if(null != mList){
            if((0 <= position) && (getItemCount() > position)){
                return mList.get(position);
            }
        }
        return null;
    }

    /**
     * Push item without load more
     * @param data
     */
    public void pushItem(List<D> data){
        if(null != data) {
            int size = data.size();
            if (0 < size) {
                int currentTotal = mList.size();
                mList.addAll(data);
                notifyItemRangeInserted(currentTotal, (currentTotal + size));
            }
        }
    }

    /**
     * Load more items
     * @param data
     * @param limit
     */
    public void pushItem(List<D> data, int limit){
        // Remove load more
        removeLoadMore();

        // Push item
        pushItem(data);

        // Add load more
        addLoadMore(data, limit);
    }

    /**
     * Using for load more
     */
    private void addLoadMore(List<D> data, int limit){
        if(null == data){
            return;
        }
        int size = data.size();
        if(size < limit){
            return;
        }

        mList.add(null);
        int index = mList.size();
        notifyItemInserted(index);
    }

    /**
     * Using for load more
     */
    private void removeLoadMore(){
        if(0 < mList.size()) {
            int index = mList.size() - 1;
            mList.remove(index);
            notifyItemRemoved(index);
        }
    }

}
