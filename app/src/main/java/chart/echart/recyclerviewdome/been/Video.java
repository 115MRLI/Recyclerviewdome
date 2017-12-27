package chart.echart.recyclerviewdome.been;

import java.io.Serializable;
import java.util.List;

/**
 * Created by L on 2017/12/26.
 */
public class Video implements Serializable {

    private boolean error;
    private List<Gank> results;

    public boolean isError() {
        return error;
    }

    public List<Gank> getResults() {
        return results;
    }

    @Override
    public String toString() {
        return "Video{" +
                "error=" + error +
                ", results=" + results +
                '}';
    }
}
