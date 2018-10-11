package vn.backshop.github.modules.detail;

import vn.backshop.github.base.BasePresenter;
import vn.backshop.github.model.DetailEntity;
import vn.backshop.github.service.MyService;
import vn.backshop.github.service.RestListener;

class DetailPresenter extends BasePresenter<IDetail.View> implements IDetail.Presenter{

    public DetailPresenter(IDetail.View view) {
        super(view);
    }

    @Override
    public void getUser(String login) {
        if(isLife()){
            getListener().showLoading();
        }
        MyService.getInstance().getUser(login, new RestListener<DetailEntity>() {
            @Override
            public void onSuccess(DetailEntity data) {
                if(isLife()){
                    getListener().onUser(data);
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
    }
}
