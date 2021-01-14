package com.example.iot_generic_control.classes;

public class ToggleButtonFeature extends BaseFeature {
    public String getValueOn() {
        return valueOn;
    }

    public void setValueOn(String valueOn) {
        this.valueOn = valueOn;
    }

    public String getValueOff() {
        return valueOff;
    }

    public void setValueOff(String valueOff) {
        this.valueOff = valueOff;
    }

    String valueOn;
    String valueOff;

    public ToggleButtonFeature(String name, String topic, int id, int device_id, String valueOn, String valueOff, String type) {
        super(name, topic, id, device_id, type);
        this.valueOn = valueOn;
        this.valueOff = valueOff;
        
    }
}
