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

import com.example.iot_generic_control.R;
import com.example.iot_generic_control.classes.BaseFeature;
import com.example.iot_generic_control.classes.ButtonFeature;
import com.example.iot_generic_control.classes.SliderFeature;
import com.example.iot_generic_control.viewmodels.DeviceViewModel;

import java.util.ArrayList;


public class NewSliderFragment extends Fragment {
    DeviceViewModel model;
    private ArrayList<BaseFeature> controlsList = new ArrayList<>();
    Integer position = 0;
    public NewSliderFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_slider, container, false);
        model = new ViewModelProvider(requireActivity()).get(DeviceViewModel.class);
        controlsList = model.getDb().getValue().selectAllFeatures(model.getDevice().getValue().getId());
        final EditText name = view.findViewById(R.id.slider_name);
        final EditText topic = view.findViewById(R.id.slider_topic);
        final EditText value1 = view.findViewById(R.id.slider_range1);
        final EditText value2 = view.findViewById(R.id.slider_range2);
        Button b = view.findViewById(R.id.slider_ok);

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
        toolbar.setTitle(model.getDevice().getValue().getName() + " - New SeekBar");

        if(model.getEdit().getValue()){
            Bundle pos = getArguments();
            position = pos.getInt("position");
            final SliderFeature sliderSetting = (SliderFeature) controlsList.get(position);
            name.setText(sliderSetting.getName());
            topic.setText(sliderSetting.getTopic());
            value1.setText( Integer.toString(sliderSetting.getStartRange()));
            value2.setText( Integer.toString(sliderSetting.getLastRange()));
        }

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String buttonName = name.getText().toString();
                String topicName = topic.getText().toString();
                String valueStart = value1.getText().toString();
                String valueLast = value2.getText().toString();
                if(model.getEdit().getValue()){
                    saveEditToDB(controlsList.get(position).getId(), buttonName, topicName, "slider", valueStart, valueLast, ";", ";");
                    //TODO: no lugar do '";;"' precisará ser trocado por '+ ";" + prefix + ";" + suffix' que deverão ser pegos de input do usuario
                }
                else{
                    saveNewButtonToDB(buttonName, topicName, valueStart, valueLast, ";", ";");

                }
                Navigation.findNavController(requireView()).navigateUp();
            }
        });

        return view;
    }
    public void saveEditToDB(int id, String name, String topic, String type, String valueStart, String valueEnd, String prefix, String suffix){
        String value = valueStart + ";" + valueEnd + ";" + prefix + ";" + suffix;
        model.getDb().getValue().updateFeature(id,name,topic,type,value);

    }
    public void saveNewButtonToDB(String name, String topic, String start, String end, String prefix, String suffix){
        model.getDb().getValue().insertFeature(name, topic, "slider",start + ";" + end + ";" + prefix + ";" + suffix, model.getDevice().getValue().getId());
        //TODO save in the db and update the view model
    }
}