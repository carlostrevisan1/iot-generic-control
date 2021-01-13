package com.example.iot_generic_control.classes;

public class BaseFeature {

    String name;
    String topic;
    String type;
    int id;
    int device_id;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDevice_id() {
        return device_id;
    }

    public void setDevice_id(int device_id) {
        this.device_id = device_id;
    }

    public BaseFeature(String name, String topic, int id, int device_id, String type) {
        this.name = name;
        this.topic = topic;
        this.id = id;
        this.device_id = device_id;
        this.type = type;
    }
}