package com.example.iot_generic_control.classes;

//A partir da classe BaseFeature extendida essa classe faz a criação de uma feature do tipo toggle feature (um botão de ON e OFF)
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

    //Valores de ON e OFF, que podem não necessariamente serem ON/OFF mas também 1/0, etc.
    String valueOn;
    String valueOff;

    public ToggleButtonFeature(String name, String topic, int id, int device_id, String valueOn, String valueOff, String type) {
        super(name, topic, id, device_id, type);
        this.valueOn = valueOn;
        this.valueOff = valueOff;
        
    }
}
