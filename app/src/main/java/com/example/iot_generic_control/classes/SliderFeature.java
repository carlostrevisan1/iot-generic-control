package com.example.iot_generic_control.classes;

public class SliderFeature extends BaseFeature {
    int startRange;
    int lastRange;

    public SliderFeature(String name, String topic, int id, int device_id, int start, int last) {
        super(name, topic, id, device_id);
        this.startRange = start;
        this.lastRange = last;
    }
}
