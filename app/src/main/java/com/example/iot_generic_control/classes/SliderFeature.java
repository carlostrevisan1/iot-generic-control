package com.example.iot_generic_control.classes;

//A partir da classe BaseFeature extendida essa classe faz a criação de uma feature do tipo slider feature
public class SliderFeature extends BaseFeature {

    //Para criar um slider precisa de um inicio, um fim, prefixo e sufixo.
    int startRange;
    int lastRange;
    String prefix;
    String suffix;

    //GET e SET para atribuição de valores
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
