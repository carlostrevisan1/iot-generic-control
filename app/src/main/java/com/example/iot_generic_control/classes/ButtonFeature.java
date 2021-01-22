package com.example.iot_generic_control.classes;

//A partir da classe BaseFeature extendida essa classe faz a criação de uma feature do tipo botão.
public class ButtonFeature extends BaseFeature {

    //Get e set value para atribuir valores
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
