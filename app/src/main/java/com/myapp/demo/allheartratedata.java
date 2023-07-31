package com.myapp.demo;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class allheartratedata extends AppCompatActivity{
    GraphView HeartRateGraph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_heart_rate_data);
        HeartRateGraph = findViewById(R.id.AllHeartRateGraph);


        HeartRateGraph.getViewport().setMinY(70);
        HeartRateGraph.getViewport().setMaxY(190);
        HeartRateGraph.getViewport().setYAxisBoundsManual(true);
        setupBlinkyTimer();
    }

    private void showDataOnGraph(LineGraphSeries<DataPoint> series, GraphView graph){
        if(graph.getSeries().size() > 0){
            graph.getSeries().remove(0);
        }
        graph.addSeries(series);
        series.setDrawDataPoints(true);
        series.setDataPointsRadius(10);
    }

    private void getDataFromThingSpeak() {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request.Builder builder = new Request.Builder();
        String apiURL = "https://io.adafruit.com/api/v2/Eir1ysk4/feeds/heart-rates/data";
        Request request = builder.url(apiURL).build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                String jsonString = response.body().string();
                try {
                    JSONArray jsonArray = new JSONArray(jsonString);
                    DataPoint[] dp = new DataPoint[jsonArray.length()];

                    for (int i = 0; i < jsonArray.length(); i++) {
                        dp[i] = new DataPoint(i ,jsonArray.getJSONObject(i).getDouble("value"));
                    }

                    LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dp);

                    showDataOnGraph(series, HeartRateGraph);

                } catch (Exception e) {}
            }
        });
    }
    private void setupBlinkyTimer(){
        Timer mTimer = new Timer();
        TimerTask mTask = new TimerTask() {
            @Override
            public void run() {
                getDataFromThingSpeak();
            }
        };
        mTimer.schedule(mTask, 2000, 5000);
    }
}
