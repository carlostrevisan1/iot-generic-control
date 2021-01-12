package com.example.iot_generic_control;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import java.util.ArrayList;
import java.util.List;

public class DeviceListViewAdapter extends ArrayAdapter {
//    private ArrayList<T> pacients;
    private ArrayList<IOTDevice> devices;
    private Context context;
    private int resource;

    public DeviceListViewAdapter(@NonNull Context context, int resource, @NonNull ArrayList<IOTDevice> devices_list) {
        super(context, resource, devices_list);
        this.context = context;
        this.devices = devices_list;
        this.resource = resource;
    }

    //Utilizando a classe auxiliar ViewHolder, faz a ligacao dos dados com o layout do adapter
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        ViewHolder holder;
        if(convertView==null){
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(resource, parent, false);
            holder = new ViewHolder();

            holder.name = convertView.findViewById(R.id.device_name);

            convertView.setTag(holder);
        }
        else
            holder = (ViewHolder)convertView.getTag();

        IOTDevice device_data = devices.get(position);
        holder.name.setText(device_data.getName());


        return convertView;
    }
    //Classe auxiliar
    static class ViewHolder{
        TextView name;

    }
}
