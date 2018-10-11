package vn.backshop.github.modules.users;

import java.util.List;

import vn.backshop.github.helper.IView;
import vn.backshop.github.model.UserEntity;

interface IUser {

    interface View extends IView {
        void onUsers(List<UserEntity> list, int limit);
    }

    interface Presenter{
        void getUsers();
    }
}
