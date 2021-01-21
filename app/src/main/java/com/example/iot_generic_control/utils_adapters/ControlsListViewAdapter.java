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
import com.example.iot_generic_control.classes.BaseFeature;

import java.util.ArrayList;

public class ControlsListViewAdapter extends ArrayAdapter {
    private ArrayList<BaseFeature> controls;
    private Context context;
    private int resource;

    public ControlsListViewAdapter (@NonNull Context context, int resource, @NonNull ArrayList<BaseFeature> Controls_list) {
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
            //Infla o layout para um elemento da lista
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(resource, parent, false);
            holder = new ViewHolder();

            holder.name = convertView.findViewById(R.id.device_name);
            holder.type = convertView.findViewById(R.id.device_type1);
            holder.topic = convertView.findViewById(R.id.device_topic1);

            convertView.setTag(holder);
        }
        else
            holder = (ViewHolder)convertView.getTag();

        //Seta os valores no layout
        BaseFeature control_data = controls.get(position);
        if(control_data.getName().isEmpty()){
            holder.name.setText("(Null)");
        }
        else{
            holder.name.setText(control_data.getName());
        }
        holder.type.setText(control_data.getType());
        holder.topic.setText(control_data.getTopic());


        return convertView;
    }
    //Classe auxiliar
    static class ViewHolder{
        TextView name;
        TextView type;
        TextView topic;
    }
}