package vn.backshop.github.base;

public abstract class BasePresenter<V> {

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


    // Separator by PAGE and LIMIT
    private int lzPage;
    public int getPage(){
        return lzPage;
    }
    // Call when load data success, before binding to view
    public boolean lzNextPage(int size, int limit) {
        if((0 == size) && (0 == lzPage)){
            return false;
        }
        if (limit > size) {
            lzPage = -1;
        } else {
            lzPage = lzPage + 1;
        }
        return true;
    }
}
