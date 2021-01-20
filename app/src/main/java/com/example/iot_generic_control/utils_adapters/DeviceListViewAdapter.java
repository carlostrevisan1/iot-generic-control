package com.example.iot_generic_control.utils_adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.example.iot_generic_control.classes.IOTDevice;
import com.example.iot_generic_control.R;

import java.util.ArrayList;

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
            holder.desc = convertView.findViewById(R.id.device1_desc);
            holder.ip = convertView.findViewById(R.id.device1_ip);
            holder.port = convertView.findViewById(R.id.device1_port);
            holder.img = convertView.findViewById(R.id.imageView);

            convertView.setTag(holder);
        }
        else
            holder = (ViewHolder)convertView.getTag();

        IOTDevice device_data = devices.get(position);
        holder.name.setText(device_data.getName());
        holder.desc.setText(device_data.getDesc());
        holder.ip.setText(device_data.getBrokerIP());
        holder.port.setText(device_data.getBrokerPort());
        ColorFilter filter = new LightingColorFilter( Color.rgb(0,200,200), Color.BLACK);
        holder.img.getDrawable().setColorFilter(filter);

        return convertView;
    }
    //Classe auxiliar
    static class ViewHolder{
        TextView name;
        TextView desc;
        TextView ip;
        TextView port;
        ImageView img;
    }
}
