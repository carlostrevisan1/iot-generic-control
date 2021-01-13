package com.example.iot_generic_control.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.iot_generic_control.classes.BaseFeature;
import com.example.iot_generic_control.classes.ButtonFeature;
import com.example.iot_generic_control.classes.IOTDevice;
import com.example.iot_generic_control.classes.SendTextFeature;

import java.util.ArrayList;

public class DeviceViewModel extends ViewModel {
    MutableLiveData<IOTDevice> device = new MutableLiveData<>();
    MutableLiveData<ArrayList<BaseFeature> >base = new MutableLiveData<>();
    ArrayList<BaseFeature> teste = new ArrayList<>();

    public void setDevice(IOTDevice device){
        this.device.setValue(device);
        updateFeatures();
    }
    public LiveData<IOTDevice> getDevice(){
        return device;
    }

    public LiveData<ArrayList<BaseFeature>> getFeatures(){
        return base;
    }
    public void updateFeatures(){
        teste.clear();
        ButtonFeature botao = new ButtonFeature("teste", "teste", 1,1,"ola");
        SendTextFeature send = new SendTextFeature("teste2", "teste", 1,1,"ola");
        teste.add(botao);
        teste.add(send);
        base.setValue(teste);
    }
}
