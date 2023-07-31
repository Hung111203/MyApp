package com.myapp.demo;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.List;

public class weights extends AppCompatActivity {
    MqttHelper mqttHelper;
    List<PieEntry> entries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weights);

        PieChart pieChart = findViewById(R.id.pie_chart_weights);

        entries = new ArrayList<>();
        entries.add(new PieEntry(75, "weights"));

        int[] colors = {Color.GREEN, Color.WHITE};

        PieDataSet dataSet = new PieDataSet(entries, "weight");
        dataSet.setColors(colors);
        dataSet.setValueTextSize(20f); // Set the text size for value labels here

        PieData data = new PieData(dataSet);

        pieChart.setData(data);
        pieChart.invalidate();
    }
}