package vn.backshop.github.modules.detail;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.facebook.drawee.view.SimpleDraweeView;
import butterknife.BindView;
import butterknife.OnClick;
import vn.backshop.github.R;
import vn.backshop.github.base.BaseFragment;
import vn.backshop.github.helper.CommonUtil;
import vn.backshop.github.model.DetailEntity;

/**
 * @author phuongtnm
 */
public class DetailFragment extends BaseFragment implements IDetail.View{

    public static DetailFragment newInstance(String login){
        Bundle bundle = new Bundle();
        bundle.putString("login", login);

        DetailFragment fragment = new DetailFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Bundle bundle = getArguments();
        if(null != bundle){
            if(bundle.containsKey("login")){
                mLogin = bundle.getString("login");
            }
        }
    }

    @BindView(R.id.iv_avatar)
    SimpleDraweeView ivAvatar;
    @BindView(R.id.tv_name)
    AppCompatTextView tvName;
    @BindView(R.id.tv_user)
    AppCompatTextView tvUser;
    @BindView(R.id.tv_location)
    AppCompatTextView tvLocation;
    @BindView(R.id.tv_staff)
    AppCompatTextView tvStaff;
    @BindView(R.id.tv_link)
    AppCompatTextView tvLink;

    private DetailPresenter presenter;
    private String mLogin;

    @Override
    public int layoutResource() {
        return R.layout.detail_fragment;
    }

    @Override
    public void createView(Bundle savedInstanceState) {
        presenter = new DetailPresenter(this);
        presenter.getUser(mLogin);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.destroyView();
    }

    @Override
    public void onUser(DetailEntity data) {
        ivAvatar.setImageURI(data.getAvatar());
        tvName.setText(data.getName());
        tvUser.setText(data.getLogin());
        tvLocation.setText(data.getLocation());
        if(data.isBadge()){
            tvStaff.setVisibility(View.VISIBLE);
        }
        bindLink(data.getBlog());
    }
    private void bindLink(final String link){
        tvLink.setText(link);
        CommonUtil.getInstance().spannableString(getContext(), tvLink, R.color.blue, new CommonUtil.SpannableListener() {
            @Override
            public String onClicked(boolean isClicked) {
                if(isClicked){
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(link));
                    startActivity(i);
                }
                return link;
            }
        });
    }

    @OnClick(R.id.bt_close)
    void closeClicked(){
        getActivity().onBackPressed();
    }

    @Override
    public void showLoading() {
        // TODO Show dialog loading
    }

    @Override
    public void hideLoading() {
        // TODO Hide dialog login
    }

    @Override
    public void alertMessage(String message) {
        // TODO Show alert message
    }
}
