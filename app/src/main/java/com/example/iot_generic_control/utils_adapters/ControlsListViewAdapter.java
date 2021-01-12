package com.example.iot_generic_control.utils_adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.example.iot_generic_control.R;

import java.util.ArrayList;

public class ControlsListViewAdapter extends ArrayAdapter {
    //    private ArrayList<T> pacients;
    private ArrayList<String> controls;
    private Context context;
    private int resource;

    public ControlsListViewAdapter (@NonNull Context context, int resource, @NonNull ArrayList<String> Controls_list) {
        super(context, resource, Controls_list);
        this.context = context;
        this.controls = Controls_list;
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

        String control_data = controls.get(position);
        holder.name.setText(control_data);


        return convertView;
    }
    //Classe auxiliar
    static class ViewHolder{
        TextView name;

    }
}

