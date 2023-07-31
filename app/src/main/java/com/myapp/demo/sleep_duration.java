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



    public class sleep_duration extends AppCompatActivity {
         LineChart lineChart;

        private List<String> xValues;
        MqttHelper mqttHelper;
        List<Entry> entries1;



        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_sleep_duration);

            lineChart = findViewById(R.id.chart_sleep_duration);
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

            yAxis.setAxisMinimum(0f);
            yAxis.setAxisMaximum(12f);
            yAxis.setAxisLineWidth(2f);
            yAxis.setLabelCount(10);


             entries1 = new ArrayList<>();
            entries1.add(new Entry(0, 4f));
            entries1.add(new Entry(1, 5f));
            entries1.add(new Entry(2, 7f));
            entries1.add(new Entry(3, 8f));
            entries1.add(new Entry(4, 4f));
            entries1.add(new Entry(5, 7f));
            entries1.add(new Entry(6, 6));
            entries1.add(new Entry(7, 8f));
            entries1.add(new Entry(8, 11f));
            entries1.add(new Entry(9, 6f));




            LineDataSet dataSet1 = new LineDataSet(entries1, "Sleep duration");
            dataSet1.setColor(getResources().getColor(android.R.color.black));


            LineData lineData = new LineData(dataSet1);
            lineChart.setData(lineData);
            lineChart.invalidate();
            startMQTT();
        }
        public void startMQTT() {
            mqttHelper = new MqttHelper(this, "dsfadsfd");
            mqttHelper.setCallback(new MqttCallbackExtended() {
                @Override
                public void connectComplete(boolean reconnect, String serverURI) {

                }

                @Override
                public void connectionLost(Throwable cause) {

                }

                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    Log.d("Mqtt", topic + "2 ***" + message.toString());
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