package com.example.iot_generic_control.classes;

public class ToggleButtonFeature extends BaseFeature {
    String valueOn;
    String valueOff;

    public ToggleButtonFeature(String name, String topic, int id, int device_id, String valueOn, String valueOff) {
        super(name, topic, id, device_id);
        this.valueOn = valueOn;
        this.valueOff = valueOff;
    }
}
