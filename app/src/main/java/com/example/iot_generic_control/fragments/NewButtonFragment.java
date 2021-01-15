package com.example.iot_generic_control.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.iot_generic_control.R;
import com.example.iot_generic_control.classes.BaseFeature;
import com.example.iot_generic_control.classes.ButtonFeature;
import com.example.iot_generic_control.viewmodels.DeviceViewModel;

import java.util.ArrayList;


public class NewButtonFragment extends Fragment {

    DeviceViewModel model;
    private ArrayList<BaseFeature> controlsList = new ArrayList<>();
    Integer position = 0;

    public NewButtonFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_button, container, false);
        final EditText name = view.findViewById(R.id.button_name);
        final EditText topic = view.findViewById(R.id.button_topic);
        final EditText value = view.findViewById(R.id.button_value);
        Button b = view.findViewById(R.id.button_ok_button);
        model = new ViewModelProvider(requireActivity()).get(DeviceViewModel.class);
        controlsList = model.getFeatures().getValue();

        if(model.getEdit().getValue()){
            Bundle pos = getArguments();
            position = pos.getInt("position");
            final ButtonFeature buttonSetting = (ButtonFeature) controlsList.get(position);
            name.setText(buttonSetting.getName());
            topic.setText(buttonSetting.getTopic());
            value.setText( buttonSetting.getValue());
        }

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String buttonName = name.getText().toString();
                String topicName = topic.getText().toString();
                String valueValue = value.getText().toString();
                //TODO update viewmodel list
                if(model.getEdit().getValue()){

                   // saveEditToDB(controlsList.get(position).getId(), buttonName, topicName, "button", valueValue);
                }
                else{
                    //saveNewButtonToDB(buttonName, topicName, valueValue, "button");

                }
                Navigation.findNavController(requireView()).navigateUp();
            }
        });

        return view;
    }
    public void saveEditToDB(int id, String name, String topic, String type, String value){
        model.getDb().getValue().updateFeature(id,name,topic,type,value);
        //TODO update in the DB and update viewmodel with new information

    }
    public void saveNewButtonToDB(String name, String topic, String value, String type){
        model.getDb().getValue().insertFeature(name, topic, type,value, model.getDevice().getValue().getId());
        //TODO save in the db and update the view model
    }
}