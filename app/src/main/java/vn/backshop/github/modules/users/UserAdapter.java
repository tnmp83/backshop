package vn.backshop.github.modules.users;

import android.content.Context;
import android.view.ViewGroup;

import vn.backshop.github.R;
import vn.backshop.github.base.recyclerview.BaseAdapter;
import vn.backshop.github.model.UserEntity;

class UserAdapter extends BaseAdapter<UserHolder, UserEntity> {

    public UserAdapter(Context context) {
        super(context);
    }

    @Override
    public void bindView(UserHolder holder, int position) {
        holder.bindingData(getItem(position));
    }

    @Override
    public UserHolder createView(ViewGroup parent, int viewType) {
        return new UserHolder(getView(R.layout.user_holder, parent), getListener());
    }
}
