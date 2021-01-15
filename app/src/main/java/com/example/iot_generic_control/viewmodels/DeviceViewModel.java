package com.example.iot_generic_control.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.iot_generic_control.classes.BaseFeature;
import com.example.iot_generic_control.classes.ButtonFeature;
import com.example.iot_generic_control.classes.DB;
import com.example.iot_generic_control.classes.IOTDevice;
import com.example.iot_generic_control.classes.SendTextFeature;

import java.util.ArrayList;

public class DeviceViewModel extends ViewModel {
    MutableLiveData<IOTDevice> device = new MutableLiveData<>();
    MutableLiveData<ArrayList<BaseFeature> >base = new MutableLiveData<>();

    MutableLiveData<Boolean> edit = new MutableLiveData<>();

    public MutableLiveData<DB> getDb() {
        return db;
    }

    public void setDb(DB db) {
        this.db.setValue(db);
    }

    MutableLiveData<DB> db = new MutableLiveData<>();

    public MutableLiveData<Boolean> getEdit() {
        return edit;
    }

    public void setEdit(Boolean edit) {
        this.edit.setValue(edit);
    }

    public void setDevice(IOTDevice device){
        this.device.setValue(device);

    }
    public void setFeatures(ArrayList<BaseFeature> featuresList){

        this.base.setValue(featuresList);
    }
    public LiveData<IOTDevice> getDevice(){
        return device;
    }

    public LiveData<ArrayList<BaseFeature>> getFeatures(){
        return base;
    }

}
