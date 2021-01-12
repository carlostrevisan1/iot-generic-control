package com.example.iot_generic_control.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.iot_generic_control.classes.IOTDevice;

public class DeviceViewModel extends ViewModel {
    MutableLiveData<IOTDevice> device = new MutableLiveData<>();
    public void setDevice(IOTDevice device){
        this.device.setValue(device);
    }
    public LiveData<IOTDevice> getDevice(){
        return device;
    }
}
