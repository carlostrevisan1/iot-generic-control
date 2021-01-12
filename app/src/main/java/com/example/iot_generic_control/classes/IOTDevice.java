package com.example.iot_generic_control.classes;

import android.os.Parcel;

public class IOTDevice {
    String name;
    String desc;
    String brokerIP;
    String brokerPort;
    int id;


    public IOTDevice(String name, String desc, String brokerIP, String brokerPort, int id) {
        this.name = name;
        this.desc = desc;
        this.brokerIP = brokerIP;
        this.brokerPort = brokerPort;
        this.id = id;

    }

    public int getId() { return id; }

    public void setId(int id) {  this.id = id;  }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getBrokerIP() {
        return brokerIP;
    }

    public void setBrokerIP(String brokerIP) {
        this.brokerIP = brokerIP;
    }

    public String getBrokerPort() {
        return brokerPort;
    }

    public void setBrokerPort(String brokerPort) {
        this.brokerPort = brokerPort;
    }

}