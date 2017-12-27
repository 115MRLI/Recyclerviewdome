package chart.echart.recyclerviewdome.been;

import java.util.List;

/**
 * Created by L on 2017/12/26.
 */

public class MeiZhi {
    private boolean error;
    private List<Gank> results;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<Gank> getResults() {
        return results;
    }

    public void setResults(List<Gank> results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "MeiZhi{" +
                "error=" + error +
                ", results=" + results +
                '}';
    }
}
