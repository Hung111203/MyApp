package com.myapp.demo;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    MqttHelper mqttHelper;
    heart_rates objHeartRate = new heart_rates();
    ArrayList<String> data_heartrate = new ArrayList<>();

    private Button button_steps;
    private Button button_weights;
    private Button button_blood_pressure;
    private Button button_heart_rates;
    private Button button_body_temperature;
    private Button button_sleep_duration;







        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);





        button_sleep_duration=(Button) findViewById(R.id.button_sleep_duration);
        button_sleep_duration.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                openactivity_sleep_duration();
            }
        });


        button_body_temperature=(Button) findViewById(R.id.button_body_temperature);
        button_body_temperature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openactivity_body_temp();
            }
        });

        button_heart_rates= (Button) findViewById(R.id.button_heart_rates);
        button_heart_rates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openactivity_heart_rates();
            }
        });
        button_blood_pressure=(Button) findViewById(R.id.button_blood_pressure);
        button_blood_pressure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openactivity_blood_pressure();
            }
        });

        button_weights=(Button) findViewById(R.id.button_weights);
        button_weights.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openactivity_weights();
            }
        });


        button_steps =(Button)  findViewById(R.id.button_steps);
        button_steps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               openactivity_steps();

            }
        });

    }




    public void openactivity_steps(){
        Intent intent = new Intent(this,steps.class);
        startActivity(intent);

        }
    public void openactivity_weights(){
        Intent intent = new Intent(this,weights.class);
        startActivity(intent);
    }
    public void openactivity_blood_pressure(){
        Intent intent = new Intent(this,blood_pressure.class);
        startActivity(intent);
    }
    public void openactivity_heart_rates(){
        Intent intent = new Intent(this, heart_rates.class);
        intent.putExtra("data_heartrate",data_heartrate);
        startActivity(intent);
    }
    public void openactivity_body_temp(){
        Intent intent = new Intent(this, body_temp.class);

        startActivity(intent);
    }
    public void openactivity_sleep_duration(){
        Intent intent = new Intent(this, sleep_duration.class);
        intent.putExtra("sleep_duration",data_heartrate);
        startActivity(intent);
    }
    public class MyActivity extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel("my_channel_id", "My Channel", NotificationManager.IMPORTANCE_DEFAULT);
                NotificationManager notificationManager = getSystemService(NotificationManager.class);
                notificationManager.createNotificationChannel(channel);
            }


    }


}}



