package com.example.iot_generic_control;

public class IOTDevice {
    String name;
    String desc;
    String brokerIP;
    String brokerPort;

    public IOTDevice(String name, String desc, String brokerIP, String brokerPort) {
        this.name = name;
        this.desc = desc;
        this.brokerIP = brokerIP;
        this.brokerPort = brokerPort;
    }

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
