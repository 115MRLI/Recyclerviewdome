package chart.echart.recyclerviewdome.api;

/**
 * Created by L on 2017/12/26.
 */

public class ApiFactory {
    protected static final Object monitor = new Object();
    static GankAPI gankApiSingleton = null;

    public static GankAPI getGankApiSingleton() {
        synchronized (monitor) {
            if (gankApiSingleton == null) {
                gankApiSingleton = new ApiRetrofit().getGankApiService();
            }
            return gankApiSingleton;
        }
    }
}
