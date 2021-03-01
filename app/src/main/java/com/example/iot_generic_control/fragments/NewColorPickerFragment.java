package com.example.iot_generic_control.fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.iot_generic_control.R;
import com.example.iot_generic_control.classes.BaseFeature;
import com.example.iot_generic_control.classes.ColorPickerFeature;
import com.example.iot_generic_control.classes.SliderFeature;
import com.example.iot_generic_control.viewmodels.DeviceViewModel;

import java.util.ArrayList;


public class NewColorPickerFragment extends Fragment {

    DeviceViewModel model;
    private ArrayList<BaseFeature> controlsList = new ArrayList<>();
    Integer position = 0;

    public NewColorPickerFragment() {
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
        View view =  inflater.inflate(R.layout.fragment_new_color_picker, container, false);
        model = new ViewModelProvider(requireActivity()).get(DeviceViewModel.class);
        controlsList = model.getDb().getValue().selectAllFeatures(model.getDevice().getValue().getId());

        /* Procura na view os campos de edittext e o botao */
        final EditText name = view.findViewById(R.id.picker_name);
        final EditText topic = view.findViewById(R.id.picker_topic);
        final EditText separator = view.findViewById(R.id.picker_separator);
        final Spinner spinner = view.findViewById(R.id.picker_spinner);
        final EditText prefix = view.findViewById(R.id.prefix);
        final EditText suffix = view.findViewById(R.id.suffix);
        Button b = view.findViewById(R.id.picker_ok);

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
        // Seta a cor da toolbar para  a  mesma do device
        toolbar.setBackgroundColor(Color.parseColor(model.getDevice().getValue().getColour()));


        /* Verifica se a chamada desse fragmento é de edicao de acordo com o valor da variavel edit dentro da viewmodel, e caso seja
         * Coloca os valores já conhecidos dentro dos edittext para que seja possivel a edicao*/
        if(model.getEdit().getValue()){
            Bundle pos = getArguments();
            position = pos.getInt("position");
            final ColorPickerFeature pickerSettings = (ColorPickerFeature) controlsList.get(position);
            name.setText(pickerSettings.getName());
            topic.setText(pickerSettings.getTopic());
            separator.setText(pickerSettings.getSeparator());
            if(pickerSettings.getColor_system().equals("RGB")){
                spinner.setSelection(0);
            }
            else{
                spinner.setSelection(1);
            }
            prefix.setText(pickerSettings.getPrefix());
            suffix.setText(pickerSettings.getSuffix());

            //Seta o titulo da toolbar para o modo de edicao
            toolbar.setTitle(model.getDevice().getValue().getName() + " - Edit ColorPicker");
        }
        else{
            //Seta o titulo da toolbar para o modo de nova feature
            toolbar.setTitle(model.getDevice().getValue().getName() + " - New ColorPicker");
        }

        /* Seta um listener no botao da view que dependendo se for edicao ou nao, salva um novo Slider ou edita um*/
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String buttonName = name.getText().toString();
                String topicName = topic.getText().toString();
                String separatorr = separator.getText().toString();
                String colorsys = spinner.getSelectedItem().toString();
                String prefixx = prefix.getText().toString();
                String suffixx = suffix.getText().toString();
                Log.d("AAAAAAAAA",colorsys);

                //checa se os campos nao estao nulos e caso estejam mostra um toast indicando o problema
                if(buttonName.isEmpty() || topicName.isEmpty()){
                    Toast.makeText(requireContext(),R.string.invalid_input, Toast.LENGTH_LONG).show();
                }
                else if(separatorr.contains(";")){
                    Toast.makeText(requireContext(),"O separador \";\" não pode ser utilizado!", Toast.LENGTH_LONG).show();
                }
                else if(prefixx.contains(";") || suffixx.contains(";")){
                    Toast.makeText(requireContext(),"\";\" não pode ser utilizado!", Toast.LENGTH_LONG).show();
                }
                else {
                    if(colorsys.equals("RGB") && separatorr.isEmpty()){
                        Toast.makeText(requireContext(),"Dado o tipo RGB, um separador para os valores é necessário!", Toast.LENGTH_LONG).show();
                    }
                    else{
                        if (model.getEdit().getValue()) {
                            saveEditToDB(controlsList.get(position).getId(), buttonName, topicName, "colorPicker", separatorr, colorsys, prefixx, suffixx);
                        } else {
                            saveNewButtonToDB(buttonName, topicName, separatorr, colorsys, prefixx, suffixx);
                        }
                        Navigation.findNavController(requireView()).navigateUp();
                    }
                }
            }
        });
        return view;
    }
    /* De acordo com a instancia de db dentro da viewmodel da um update do controle em questao*/
    public void saveEditToDB(int id, String name, String topic, String type, String separator, String colorsys, String prefix, String suffix){
        String value = colorsys + ";" + separator + ";" + prefix + ";" + suffix;
        model.getDb().getValue().updateFeature(id,name,topic,type,value);
    }

    /* Salva uma nova feature no db */
    public void saveNewButtonToDB(String name, String topic, String separator, String colorsys, String prefix, String suffix){
        model.getDb().getValue().insertFeature(name, topic, "colorPicker",colorsys + ";" + separator + ";" + prefix + ";" + suffix,
                model.getDevice().getValue().getId());
    }
}