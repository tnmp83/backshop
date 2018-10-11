package vn.backshop.github.modules.users;

import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import vn.backshop.github.R;
import vn.backshop.github.base.recyclerview.BaseHolder;
import vn.backshop.github.base.recyclerview.HolderListener;
import vn.backshop.github.model.UserEntity;

class UserHolder extends BaseHolder<UserEntity> {

    @BindView(R.id.iv_avatar)
    SimpleDraweeView ivAvatar;
    @BindView(R.id.tv_name)
    AppCompatTextView tvName;
    @BindView(R.id.tv_staff)
    AppCompatTextView tvStaff;

    private UserEntity userEntity;
    public UserHolder(@NonNull View itemView, final HolderListener<UserEntity> listener) {
        super(itemView, listener);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(null != listener) {
                    listener.itemClicked(userEntity, getAdapterPosition());
                }
            }
        });
    }

    @Override
    public void bindingData(UserEntity data) {
        userEntity = data;

        tvName.setText(data.getLogin());
        ivAvatar.setImageURI(data.getAvatar());
        if(data.isBadge()){
            tvStaff.setVisibility(View.VISIBLE);
        }else{
            tvStaff.setVisibility(View.GONE);
        }
    }
}
