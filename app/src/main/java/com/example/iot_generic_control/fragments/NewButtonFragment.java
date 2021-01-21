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
        // Infla o layout e carrega a viewmodel e a lista de controles
        View view = inflater.inflate(R.layout.fragment_new_button, container, false);
        model = new ViewModelProvider(requireActivity()).get(DeviceViewModel.class);
        controlsList = model.getDb().getValue().selectAllFeatures(model.getDevice().getValue().getId());

        /* Seta a toolbar e habilita o botao de voltar nela*/
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


        /* Procura na view os elementos que serao utilizados*/
        final EditText name = view.findViewById(R.id.button_name);
        final EditText topic = view.findViewById(R.id.button_topic);
        final EditText value = view.findViewById(R.id.button_value);
        Button b = view.findViewById(R.id.button_ok_button);

        /* Checa se é pra ser uma pagina para editar um controle e assim seta os elementos com os valores ja cadastrados*/
        if(model.getEdit().getValue()){
            Bundle pos = getArguments();
            position = pos.getInt("position");
            final ButtonFeature buttonSetting = (ButtonFeature) controlsList.get(position);
            name.setText(buttonSetting.getName());
            topic.setText(buttonSetting.getTopic());
            value.setText( buttonSetting.getValue());
            toolbar.setTitle(model.getDevice().getValue().getName() + " - Edit Button");
        }
        else{
            toolbar.setTitle(model.getDevice().getValue().getName() + " - New Button");
        }

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* Pega os valores que estao nas views e salva no bancco*/
                String buttonName = name.getText().toString();
                String topicName = topic.getText().toString();
                String valueValue = value.getText().toString();
                //TODO update viewmodel list
                if(model.getEdit().getValue()){

                   saveEditToDB(controlsList.get(position).getId(), buttonName, topicName, "button", valueValue);
                }
                else{
                    saveNewButtonToDB(buttonName, topicName, valueValue, "button");

                }
                Navigation.findNavController(requireView()).navigateUp();
            }
        });

        return view;
    }
    /* Faz um Update de um controle no banco*/
    public void saveEditToDB(int id, String name, String topic, String type, String value){
        model.getDb().getValue().updateFeature(id,name,topic,type,value);
        //TODO update in the DB and update viewmodel with new information

    }

    /* Salva um novo controle no banco*/
    public void saveNewButtonToDB(String name, String topic, String value, String type){
        model.getDb().getValue().insertFeature(name, topic, type,value, model.getDevice().getValue().getId());
        //TODO save in the db and update the view model
    }
}