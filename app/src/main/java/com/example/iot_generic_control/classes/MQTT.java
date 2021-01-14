package com.example.iot_generic_control.classes;

import android.content.Context;
import android.util.Log;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MQTT {
    MqttAndroidClient mqtt;
    final private String PRODUCTKEY = "a11xsrW****";
    final private String DEVICENAME = "paho_android";
    final private String DEVICESECRET = "tLMT9QWD36U2SArglGqcHCDK9rK9****";
    String clientId, userName, passWord;

    /* Obtain the MQTT connection information clientId, username, and password. */
    AiotMqttOption aiotMqttOption = new AiotMqttOption().getMqttOption(PRODUCTKEY, DEVICENAME, DEVICESECRET);

    public MQTT(Context context, String ip_address, String device_name){
        if (aiotMqttOption == null) {
            Log.e("option_error", "device info error");
        }
        else {
            clientId = aiotMqttOption.getClientId();
            userName = aiotMqttOption.getUsername();
            passWord = aiotMqttOption.getPassword();
        }

        /* Create an MqttConnectOptions object and configure the username and password. */
        MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
        mqttConnectOptions.setUserName(userName);
        mqttConnectOptions.setPassword(passWord.toCharArray());
        mqtt = new MqttAndroidClient(context, ip_address, device_name);
        mqtt.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {
                Log.i("MQTT", "connection lost");
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                Log.i("MQTT", "topic: " + topic + ", msg: " + new String(message.getPayload()));
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
                Log.i("MQTT", "msg delivered");
            }
        });

        /* Establish an MQTT connection */
        try {
            mqtt.connect(mqttConnectOptions, null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Log.i("MQTT", "connect succeed");
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Log.i("MQTT", "connect failed");
                }
            });

        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void publishMessage(String topic, String payload) {
        try {
            if (mqtt.isConnected() == false) {
                mqtt.connect();
            }

            MqttMessage message = new MqttMessage();
            message.setPayload(payload.getBytes());
            message.setQos(0);
            mqtt.publish(topic, message,null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Log.i("MQTT", "publish succeed! ");
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Log.i("MQTT", "publish failed!");
                }
            });
        } catch (MqttException e) {
            Log.e("MQTT EXCEPTION", e.toString());
            e.printStackTrace();
        }
    }
}
