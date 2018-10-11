package vn.backshop.github.modules.users;

import android.util.Log;

import java.util.List;

import vn.backshop.github.base.BasePresenter;
import vn.backshop.github.model.UserEntity;
import vn.backshop.github.service.MyService;
import vn.backshop.github.service.RestListener;

/**
 * All logic at here
 * @author phuongtnm
 */
class UserPresenter extends BasePresenter<IUser.View> implements IUser.Presenter{
    private static final int LIMIT = 5;

    public UserPresenter(IUser.View view) {
        super(view);
    }

    @Override
    public void getUsers() {
        if(isLife()){
            getListener().showLoading();
        }

        MyService.getInstance().getUsers(0, new RestListener<List<UserEntity>>() {
            @Override
            public void onSuccess(List<UserEntity> data) {
                if(isLife()){
                    getListener().onUsers(data, LIMIT);
                    getListener().hideLoading();
                }
            }

            @Override
            public void onError(String message) {
                if(isLife()){
                    getListener().alertMessage(message);
                    getListener().hideLoading();
                }
            }
        });


        // NOTE: Comment and skip testing, because response code is 403
        /*Log.d("retrofit", "page " + getPage());
        MyService.getInstance().getUsers(getPage(), LIMIT, new RestListener<List<UserEntity>>() {
            @Override
            public void onSuccess(List<UserEntity> data) {
                if(isLife()){
                    boolean isNext = lzNextPage(data.size(), LIMIT);
                    Log.d("retrofit", "page " + isNext + " = " + data.size());
                    if(isNext){
                        getListener().onUsers(data, LIMIT);
                    }
                    getListener().hideLoading();
                }
            }

            @Override
            public void onError(String message) {
                if(isLife()){
                    getListener().alertMessage(message);
                    getListener().hideLoading();
                }
            }
        });*/
    }
}
