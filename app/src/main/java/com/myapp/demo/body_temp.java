package com.myapp.demo;


import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class body_temp extends AppCompatActivity {
     LineChart lineChart;
     MqttHelper mqttHelper;
     List<Entry> entries1;

    private List<String> xValues;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_body_temp);

        lineChart = findViewById(R.id.body_temp);
        Description description = new Description();


        lineChart.setDescription(description);
        lineChart.getAxisRight().setEnabled(false);

        xValues = Arrays.asList("Time");



        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xValues));
        xAxis.setAxisMinimum(0f);
        xAxis.setAxisMaximum(20f);
        xAxis.setAxisLineWidth(2f);
        xAxis.setLabelCount(10);

        YAxis yAxis = lineChart.getAxisLeft();

        yAxis.setAxisMinimum(10f);
        yAxis.setAxisMaximum(100f);
        yAxis.setAxisLineWidth(2f);
        yAxis.setLabelCount(10);


        entries1 = new ArrayList<>();
        entries1.add(new Entry(0, 60f));
        entries1.add(new Entry(1, 50f));
        entries1.add(new Entry(2, 55f));
        entries1.add(new Entry(3, 44f));
        entries1.add(new Entry(4, 75f));
        entries1.add(new Entry(5, 52f));
        entries1.add(new Entry(6, 77f));
        entries1.add(new Entry(7, 65f));
        entries1.add(new Entry(8, 64f));
        entries1.add(new Entry(9, 62f));
        entries1.add(new Entry(10, 53f));
        entries1.add(new Entry(11, 70f));
        entries1.add(new Entry(12, 63f));
        entries1.add(new Entry(13, 75f));
        entries1.add(new Entry(14, 65f));
        entries1.add(new Entry(15, 61f));
        entries1.add(new Entry(16, 66f));
        entries1.add(new Entry(17, 58f));
        entries1.add(new Entry(18, 67f));



        LineDataSet dataSet1 = new LineDataSet(entries1, "heart rates");
        dataSet1.setColor(getResources().getColor(android.R.color.black));


        LineData lineData = new LineData(dataSet1);
        lineChart.setData(lineData);
        lineChart.invalidate();
        startMQTT();
    }
    public void startMQTT() {
        mqttHelper = new MqttHelper(this, "hhhh");
        mqttHelper.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean reconnect, String serverURI) {

            }

            @Override
            public void connectionLost(Throwable cause) {

            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                Log.d("Mqtt", topic + "4 ***" + message.toString());
                entries1.remove(0);
                int i =0;
                for(i = 0; i < entries1.size();i++){
                    entries1.get(i).setX(entries1.get(i).getX()-1);
                }
                entries1.add(new Entry(i,Float.parseFloat(message.toString())));

                lineChart.invalidate();
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

            }
        });
    }
}








