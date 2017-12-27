package chart.echart.recyclerviewdome.ui;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import chart.echart.recyclerviewdome.R;
import chart.echart.recyclerviewdome.api.ApiRetrofit;
import chart.echart.recyclerviewdome.api.GankAPI;

import chart.echart.recyclerviewdome.been.Gank;
import chart.echart.recyclerviewdome.been.MeiZhi;
import chart.echart.recyclerviewdome.ui.adapter.GankListAdapter;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends Activity {
    public GankAPI GankApiService;
    private List<Gank> meizhis ;
    private RecyclerView recycler;
    private SwipeRefreshLayout mRefreshLayout;
    private MainActivity context;
    private int page = 1;
    private  GankListAdapter gankListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inintView();
        reqData();
    }
    //初始化控件，绑定事件
    private void inintView(){
        context = this;
        meizhis = new ArrayList<Gank>();
        recycler = (RecyclerView)findViewById(R.id.recycler);
        mRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        ApiRetrofit retrofit = new ApiRetrofit();
        GankApiService = retrofit.getGankApiService();
        StaggeredGridLayoutManager linearLayoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        recycler.setLayoutManager(linearLayoutManager);
        gankListAdapter = new GankListAdapter(context,meizhis);
        recycler.setAdapter(gankListAdapter);
        mRefreshLayout.setColorSchemeResources(R.color.refresh_progress_1,
                R.color.refresh_progress_2,R.color.refresh_progress_3);
        mRefreshLayout.setProgressViewOffset(true, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24,getResources().getDisplayMetrics()));
        //下拉刷新
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                meizhis.clear();
                page = 1;
                reqData();

            }
        });
        //下拉加载
        recycler.addOnScrollListener(new RecyclerView.OnScrollListener() {

            boolean isSlidingToLast = false;
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                //得到当前显示的最后一个item的view
                View lastChildView = recyclerView.getLayoutManager().getChildAt(recyclerView.getLayoutManager().getChildCount()-1);
                //得到lastChildView的bottom坐标值
                int lastChildBottom = lastChildView.getBottom();
                //得到Recyclerview的底部坐标减去底部padding值，也就是显示内容最底部的坐标
                int recyclerBottom =  recyclerView.getBottom()-recyclerView.getPaddingBottom();
                //通过这个lastChildView得到这个view当前的position值
                int lastPosition  = recyclerView.getLayoutManager().getPosition(lastChildView);

                //判断lastChildView的bottom值跟recyclerBottom
                //判断lastPosition是不是最后一个position
                //如果两个条件都满足则说明是真正的滑动到了底部
                if(lastChildBottom == recyclerBottom && lastPosition == recyclerView.getLayoutManager().getItemCount()-1 ){
//                    Toast.makeText(context, "滑动到底了", Toast.LENGTH_SHORT).show();
                    //加载更多功能的代码
                    page++;
                    reqData();
                    //停止刷新
                }
            }

        });
    }
    //请求网络数据
    private void reqData(){
        Call<MeiZhi> call = GankApiService.getMeizhiData(page);
        call.enqueue(new Callback<MeiZhi>() {
            @Override
            public void onResponse(Call<MeiZhi> call, Response<MeiZhi> response) {
//                Log.e("===","return:"+response.body().getResults().toString());
                List<Gank> meizhis2 = response.body().getResults();
                for (int i = 0;i<meizhis2.size();i++){
                    meizhis.add(meizhis2.get(i));
                }
                setData();
                mRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<MeiZhi> call, Throwable t) {
                Log.e("===",t.toString()+"失败");
            }
        });
    }
    private void setData(){
        gankListAdapter.notifyDataSetChanged();
    }
}
