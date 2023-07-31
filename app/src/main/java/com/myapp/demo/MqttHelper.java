package com.myapp.demo;

            import android.content.Context;
            import android.util.Log;

            import org.eclipse.paho.android.service.MqttAndroidClient;
            import org.eclipse.paho.client.mqttv3.DisconnectedBufferOptions;
            import org.eclipse.paho.client.mqttv3.IMqttActionListener;
            import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
            import org.eclipse.paho.client.mqttv3.IMqttToken;
            import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
            import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
            import org.eclipse.paho.client.mqttv3.MqttException;
            import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MqttHelper {



    final String serverUri = "tcp://io.adafruit.com:1883";

    String clientId = "1112222";

    final String [] arrayTopics ={"Eir1ysk4/feeds/blood-pressure",
            "Eir1ysk4/feeds/body-temperature","Eir1ysk4/feeds/burned-calories","Eir1ysk4/feeds/heart-rates","Eir1ysk4/feeds/sleep-duration","Eir1ysk4/feeds/steps","Eir1ysk4/feeds/weight"};



    final String username = "Eir1ysk4";
    final String password = "aio_wjot67ee6SikXLGPmBbInILhPS9C";
    public MqttAndroidClient mqttAndroidClient;
    public MqttHelper(Context context, String id){
        clientId = id;
        mqttAndroidClient = new MqttAndroidClient(context, serverUri, clientId);
        mqttAndroidClient.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean b, String s) {
                Log.w("mqtt", s);
            }

            @Override
            public void connectionLost(Throwable throwable) {

            }

            @Override
            public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
                Log.d("Mqtt", "topic" + mqttMessage.toString());

            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

            }
        });
        connect();
    }

    public void setCallback(MqttCallbackExtended callback) {
        mqttAndroidClient.setCallback(callback);
    }

    public void connect(){
        MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
        mqttConnectOptions.setAutomaticReconnect(true);
        mqttConnectOptions.setCleanSession(false);
        mqttConnectOptions.setUserName(username);
        mqttConnectOptions.setPassword(password.toCharArray());

        try {

            mqttAndroidClient.connect(mqttConnectOptions, null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {

                    DisconnectedBufferOptions disconnectedBufferOptions = new DisconnectedBufferOptions();
                    disconnectedBufferOptions.setBufferEnabled(true);
                    disconnectedBufferOptions.setBufferSize(100);
                    disconnectedBufferOptions.setPersistBuffer(false);
                    disconnectedBufferOptions.setDeleteOldestMessages(false);
                    mqttAndroidClient.setBufferOpts(disconnectedBufferOptions);
                    subscribeToTopic();
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Log.w("Mqtt", "Failed to connect to: " + serverUri + exception.toString());
                }
            });


        } catch (MqttException ex){
            ex.printStackTrace();
        }
    }



    private void subscribeToTopic() {
        try {
            for (int i = 0; i < arrayTopics.length; i++) {
                IMqttToken subscribe = mqttAndroidClient.subscribe(arrayTopics[i], 0, null, new IMqttActionListener() {


                    @Override
                    public void onSuccess(IMqttToken asyncActionToken) {
                        Log.w("Mqtt", "Subscribed!");
                    }

                    @Override
                    public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                        Log.w("Mqtt", "Subscribed fail!");
                    }
                });
            }
        } catch (MqttException ex) {
            System.err.println("Exceptions subscribing");
            ex.printStackTrace();
        }
    }

    public MqttAndroidClient getMqttAndroidClient() {
        return mqttAndroidClient;
    }
}



