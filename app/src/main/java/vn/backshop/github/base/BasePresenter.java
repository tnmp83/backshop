package vn.backshop.github.base;

public class BasePresenter<V> {

    private V listener;
    public BasePresenter(V v){
        listener = v;
    }

    public V getListener(){
        return listener;
    }

    public void destroyView() {
        listener = null;
    }

    public boolean isLife(){
        if(null != listener){
            return true;
        }
        return false;
    }

}
