package vn.backshop.github.modules.users;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;

import java.util.List;

import butterknife.BindView;
import vn.backshop.github.R;
import vn.backshop.github.base.BaseFragment;
import vn.backshop.github.base.recyclerview.EndlessScrollListener;
import vn.backshop.github.base.recyclerview.HolderListener;
import vn.backshop.github.helper.Transport;
import vn.backshop.github.model.UserEntity;
import vn.backshop.github.modules.detail.DetailFragment;

public class UserFragment extends BaseFragment implements IUser.View {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private LinearLayoutManager layoutManager;
    private UserPresenter presenter;
    private UserAdapter adapter;
    private EndlessScrollListener scrollListener;

    @Override
    public int layoutResource() {
        return R.layout.users_fragment;
    }

    @Override
    public void createView(Bundle savedInstanceState) {
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        initLoadMore(layoutManager);

        adapter = new UserAdapter(getContext());
        recyclerView.setAdapter(adapter);
        adapter.setOnItemListener(holderListener);

        presenter = new UserPresenter(this);
        presenter.getUsers();
    }

    @Override
    public void onUsers(List<UserEntity> list, int limit) {
        adapter.pushItem(list, limit);
    }

    @Override
    public void showLoading() {
        // TODO Show loading
    }

    @Override
    public void hideLoading() {
        // TODO Hide loading
    }

    @Override
    public void alertMessage(String message) {
        // TODO Show alert message
    }

    private HolderListener<UserEntity> holderListener = new HolderListener<UserEntity>() {
        @Override
        public void itemClicked(UserEntity data, int position) {
            Transport.getInstance().addFragment(getFragmentManager(), DetailFragment.newInstance(data.getLogin()), R.id.wrap_content, true);
        }
    };

    // Handle load more
    private void initLoadMore(LinearLayoutManager layoutManager){
        scrollListener = new EndlessScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                Log.d("retrofit", "page: " + page);
                presenter.getUsers();
            }
        };
        recyclerView.addOnScrollListener(scrollListener);
    }
}
