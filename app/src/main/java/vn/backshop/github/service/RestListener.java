package vn.backshop.github.service;

public interface RestListener<D> {
    void onSuccess(D data);
    void onError(String message);
}
