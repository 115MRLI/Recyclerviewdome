package chart.echart.recyclerviewdome.api;


import chart.echart.recyclerviewdome.been.MeiZhi;
import chart.echart.recyclerviewdome.been.Video;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by L on 2017/12/26.
 */

public interface GankAPI {
    @GET("data/福利/10/{page}")
    Call<MeiZhi> getMeizhiData(@Path("page") int page);
    @GET("data/休息视频/10/{page}")
    Call<Video> getVideoData(@Path("page") int page);
}
