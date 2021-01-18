package com.example.iot_generic_control.fragments;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ToggleButton;

import com.example.iot_generic_control.R;
import com.example.iot_generic_control.classes.BaseFeature;
import com.example.iot_generic_control.classes.SliderFeature;
import com.example.iot_generic_control.classes.ToggleButtonFeature;
import com.example.iot_generic_control.viewmodels.DeviceViewModel;

import java.util.ArrayList;


public class NewToggleButtonFragment extends Fragment {

    DeviceViewModel model;
    private ArrayList<BaseFeature> controlsList = new ArrayList<>();
    Integer position = 0;

    public NewToggleButtonFragment() {
        // Required empty public constructor
    }


    public static NewToggleButtonFragment newInstance(String param1, String param2) {
        NewToggleButtonFragment fragment = new NewToggleButtonFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_toggle_button, container, false);
        model = new ViewModelProvider(requireActivity()).get(DeviceViewModel.class);
        controlsList = model.getFeatures().getValue();
        final EditText name = view.findViewById(R.id.toggle_button_name);
        final EditText topic = view.findViewById(R.id.toggle_button_topic);
        final EditText value1 = view.findViewById(R.id.toggle_button_valorOff);
        final EditText value2 = view.findViewById(R.id.toggle_button_valorOn);
        Button b = view.findViewById(R.id.toggle_button_ok);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setHasOptionsMenu(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(requireActivity(),
                        R.id.fragment);
                navController.navigateUp();
            }
        });
        toolbar.setTitle(model.getDevice().getValue().getName() + " - New Switch Button");

        if(model.getEdit().getValue()){
            Bundle pos = getArguments();
            position = pos.getInt("position");
            final ToggleButtonFeature buttonSetting = (ToggleButtonFeature) controlsList.get(position);
            name.setText(buttonSetting.getName());
            topic.setText(buttonSetting.getTopic());
            value1.setText( buttonSetting.getValueOn());
            value2.setText( buttonSetting.getValueOff());
        }

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String buttonName = name.getText().toString();
                String topicName = topic.getText().toString();
                String valueOff = value2.getText().toString();
                String valueOn = value1.getText().toString();
                //TODO update viewmodel list
                if(model.getEdit().getValue()){
                    saveEditToDB(controlsList.get(position).getId(), buttonName, topicName, "toggleButton", valueOff + ";" + valueOn);
                }
                else{
                    saveNewButtonToDB(buttonName, topicName, valueOff, valueOn, "toggleButton");

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
    public void saveNewButtonToDB(String name, String topic, String valueOff, String valueOn, String type){
        model.getDb().getValue().insertFeature(name, topic, type,valueOff + ";" + valueOn, model.getDevice().getValue().getId());
        //TODO save in the db and update the view model
    }
}