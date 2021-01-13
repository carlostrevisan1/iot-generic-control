package com.example.iot_generic_control.classes;

public class SendTextFeature extends BaseFeature {
    String value;

    public SendTextFeature(String name, String topic, int id, int device_id, String value) {
        super(name, topic, id, device_id);
        this.value = value;
    }
}
