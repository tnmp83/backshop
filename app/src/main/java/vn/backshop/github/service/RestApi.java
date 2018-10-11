package vn.backshop.github.service;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import vn.backshop.github.model.DetailEntity;
import vn.backshop.github.model.UserEntity;

public interface RestApi {

    @GET("users")
    Call<List<UserEntity>> restUsers(@Query("since") int index, @Query("page_size") int limit);

    @GET("users/{login}")
    Call<DetailEntity> restUser(@Path("login") String login);
}
