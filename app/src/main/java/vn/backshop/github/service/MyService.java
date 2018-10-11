package vn.backshop.github.service;

import android.content.Context;
import android.util.Log;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import vn.backshop.github.MainApp;
import vn.backshop.github.model.DetailEntity;
import vn.backshop.github.model.UserEntity;

/**
 * Now using retrofit2
 * Feature can use retrofit2 combine with rxjava2
 * @author phuongtnm
 */
public class MyService{

    private final String BASE_URL = "https://api.github.com/";

    private static MyService instance;
    public static MyService getInstance(){
        synchronized (MyService.class){
            if(instance == null){
                instance = new MyService(MainApp.getInstance().getApplicationContext());
            }
        }
        return instance;
    }

    private Context context;
    private MyService(Context context) {
        this.context = context;
        initService();
    }

    private RestApi restApi;
    /**
     * Create an instance of Retrofit object
     * */
    private void initService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        restApi = retrofit.create(RestApi.class);
    }

    public void getUsers(int index, final int limit, final RestListener<List<UserEntity>> listener){
        restApi.restUsers(index, limit).enqueue(new Callback<List<UserEntity>>() {
            @Override
            public void onResponse(Call<List<UserEntity>> call, Response<List<UserEntity>> response) {
                Log.d("retrofit", "Response List: " + response.code());
                if(null != listener){
                    listener.onSuccess(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<UserEntity>> call, Throwable t) {
                if(null != listener){
                    listener.onError(t.getMessage());
                }
            }
        });
    }

    public void getUser(String login, final RestListener listener){
        restApi.restUser(login).enqueue(new Callback<DetailEntity>() {
            @Override
            public void onResponse(Call<DetailEntity> call, Response<DetailEntity> response) {
                Log.d("retrofit", "Response Detail: " + response.code());
                if(null != listener){
                    listener.onSuccess(response.body());
                }
            }

            @Override
            public void onFailure(Call<DetailEntity> call, Throwable t) {
                if(null != listener){
                    listener.onError(t.getMessage());
                }
            }
        });
    }
}
