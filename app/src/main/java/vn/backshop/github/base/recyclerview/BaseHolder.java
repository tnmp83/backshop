package vn.backshop.github.base.recyclerview;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.ButterKnife;

public abstract class BaseHolder<T> extends RecyclerView.ViewHolder{

    public abstract void bindingData(T data);

    private HolderListener<T> listener;
    public BaseHolder(@NonNull View itemView, HolderListener<T> listener) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.listener = listener;
    }


}
