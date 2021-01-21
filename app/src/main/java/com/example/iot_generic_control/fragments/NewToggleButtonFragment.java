package com.example.iot_generic_control.fragments;

import android.graphics.Color;
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        /* Infla a view e carrega a viewmodel e a controlList*/
        View view = inflater.inflate(R.layout.fragment_new_toggle_button, container, false);
        model = new ViewModelProvider(requireActivity()).get(DeviceViewModel.class);
        controlsList = model.getFeatures().getValue();

        /* Procura na view os campos de edittext e o botao */
        final EditText name = view.findViewById(R.id.toggle_button_name);
        final EditText topic = view.findViewById(R.id.toggle_button_topic);
        final EditText value1 = view.findViewById(R.id.toggle_button_valorOff);
        final EditText value2 = view.findViewById(R.id.toggle_button_valorOn);
        Button b = view.findViewById(R.id.toggle_button_ok);

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
        toolbar.setBackgroundColor(Color.parseColor(model.getDevice().getValue().getColour()));



        /* Verifica se a chamada desse fragmento é de edicao de acordo com o valor da variavel edit dentro da viewmodel, e caso seja
         * Coloca os valores já conhecidos dentro dos edittext para que seja possivel a edicao*/
        if(model.getEdit().getValue()){
            Bundle pos = getArguments();
            position = pos.getInt("position");
            toolbar.setTitle(model.getDevice().getValue().getName() + " - Edit Switch Button");
            final ToggleButtonFeature buttonSetting = (ToggleButtonFeature) controlsList.get(position);
            name.setText(buttonSetting.getName());
            topic.setText(buttonSetting.getTopic());
            value1.setText( buttonSetting.getValueOn());
            value2.setText( buttonSetting.getValueOff());
            toolbar.setTitle(model.getDevice().getValue().getName() + " - Edit Switch Button");
        }
        else{

            toolbar.setTitle(model.getDevice().getValue().getName() + " - New Switch Button");
        }


        /* Seta um listener no botao da view que dependendo se for edicao ou nao, salva um novo Slider ou edita um*/
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

    /* De acordo com a instancia de db dentro da viewmodel da um update do controle em questao*/
    public void saveEditToDB(int id, String name, String topic, String type, String value){
        model.getDb().getValue().updateFeature(id,name,topic,type,value);
    }

    /* Salva uma nova feature no db */
    public void saveNewButtonToDB(String name, String topic, String valueOff, String valueOn, String type){
        model.getDb().getValue().insertFeature(name, topic, type,valueOff + ";" + valueOn, model.getDevice().getValue().getId());
    }
}