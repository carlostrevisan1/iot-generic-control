package com.example.iot_generic_control.classes;

//A partir da classe BaseFeature extendida essa classe faz a criação de uma feature do tipo send text feature (uma caixa de texto para digitação).
public class SendTextFeature extends BaseFeature {

    //Get e set value para atribuir valores
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    String value;

    public SendTextFeature(String name, String topic, int id, int device_id, String value, String type) {
        super(name, topic, id, device_id, type);
        this.value = value;
    }
}
