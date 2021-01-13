package com.example.iot_generic_control.classes;

public class ButtonFeature extends BaseFeature {
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    String value;

    public ButtonFeature(String name, String topic, int id, int device_id, String value, String type) {
        super(name, topic, id, device_id, type);
        this.value = value;
    }
}
