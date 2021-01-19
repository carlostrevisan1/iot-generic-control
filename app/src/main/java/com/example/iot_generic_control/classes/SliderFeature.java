package com.example.iot_generic_control.classes;

public class SliderFeature extends BaseFeature {
    int startRange;
    int lastRange;
    String prefix;
    String suffix;

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public int getStartRange() {
        return startRange;
    }

    public void setStartRange(int startRange) {
        this.startRange = startRange;
    }

    public int getLastRange() {
        return lastRange;
    }

    public void setLastRange(int lastRange) {
        this.lastRange = lastRange;
    }

    public String getPrefix() { return prefix; }

    public String getSuffix() { return suffix; }


    public SliderFeature(String name, String topic, int id, int device_id, int start, int last, String type, String prefix, String suffix) {
        super(name, topic, id, device_id,type);
        this.startRange = start;
        this.lastRange = last;
        this.prefix = prefix;
        this.suffix = suffix;
    }
}
