package chart.echart.recyclerviewdome.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import chart.echart.recyclerviewdome.R;
import chart.echart.recyclerviewdome.been.Gank;
import chart.echart.recyclerviewdome.been.MeiZhi;
import chart.echart.recyclerviewdome.ui.activity.PictureActivity;

/**
 * Created by L on 2017/12/26.
 */

public class GankListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<Gank> meiZhis;

    public GankListAdapter(Context context, List<Gank> meiZhis) {
        this.context = context;
        this.meiZhis = meiZhis;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.gank_itme_meizhi,parent,false);
        return new GankMeiZhiViewHolder(rootView);
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof GankMeiZhiViewHolder){
            GankMeiZhiViewHolder gankMeiZhiViewHolder = (GankMeiZhiViewHolder) holder;
            gankMeiZhiViewHolder.bindItem(meiZhis.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return meiZhis.size();
    }
    class GankMeiZhiViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.card_meizhi)
        CardView card_meizhi;
        @Bind(R.id.iv_meizhi)
        ImageView iv_meizhi;
        @Bind(R.id.tv_meizhi_title)
        TextView tv_meizhi_title;
        public GankMeiZhiViewHolder (View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
        public void bindItem(final  Gank meizhi) {
            tv_meizhi_title.setText(meizhi.getDesc());
            Glide.with(context).load(meizhi.getUrl()).centerCrop().skipMemoryCache(true).into(iv_meizhi);
            iv_meizhi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent  = PictureActivity.newIntent(context,meizhi.getUrl(),meizhi.get_id());
                    ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context,iv_meizhi,PictureActivity.TRANSIT_PIC);
                    try {
                        ActivityCompat.startActivity((Activity) context,intent,optionsCompat.toBundle());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
