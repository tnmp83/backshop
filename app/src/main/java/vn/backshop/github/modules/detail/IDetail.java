package vn.backshop.github.modules.detail;

import vn.backshop.github.helper.IView;
import vn.backshop.github.model.DetailEntity;

interface IDetail {

    interface View extends IView {
        void onUser(DetailEntity data);
    }

    interface Presenter{
        void getUser(String login);
    }
}
