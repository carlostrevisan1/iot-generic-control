package com.example.iot_generic_control.classes;

public class ButtonFeature extends BaseFeature {
    String value;

    public ButtonFeature(String name, String topic, int id, int device_id, String value) {
        super(name, topic, id, device_id);
        this.value = value;
    }
}
