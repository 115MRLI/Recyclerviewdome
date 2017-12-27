package chart.echart.recyclerviewdome.api;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by L on 2017/12/26.
 */

public class ApiRetrofit {
    public GankAPI GankApiService;
    public static final String GANK_BASE_URL = "http://gank.io/api/";

    public GankAPI getGankApiService() {
        return GankApiService;
    }

    public ApiRetrofit() {
        OkHttpClient client = new OkHttpClient();
        Retrofit retrofit_gank = new Retrofit.Builder()
                .baseUrl(GANK_BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GankApiService = retrofit_gank.create(GankAPI.class);
    }

}
