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
import android.widget.Toast;

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
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        /* Infla a view e carrega a viewmodel e a controlList*/
        View view = inflater.inflate(R.layout.fragment_new_slider, container, false);
        model = new ViewModelProvider(requireActivity()).get(DeviceViewModel.class);
        controlsList = model.getDb().getValue().selectAllFeatures(model.getDevice().getValue().getId());

        /* Procura na view os campos de edittext e o botao */
        final EditText name = view.findViewById(R.id.slider_name);
        final EditText topic = view.findViewById(R.id.slider_topic);
        final EditText value1 = view.findViewById(R.id.slider_range1);
        final EditText value2 = view.findViewById(R.id.slider_range2);
        final EditText prefix = view.findViewById(R.id.prefix);
        final EditText suffix = view.findViewById(R.id.suffix);
        Button b = view.findViewById(R.id.slider_ok);

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
            toolbar.setTitle(model.getDevice().getValue().getName() + " - Edit SeekBar");
            final SliderFeature sliderSetting = (SliderFeature) controlsList.get(position);
            name.setText(sliderSetting.getName());
            topic.setText(sliderSetting.getTopic());
            value1.setText( Integer.toString(sliderSetting.getStartRange()));
            value2.setText( Integer.toString(sliderSetting.getLastRange()));
            prefix.setText(sliderSetting.getPrefix());
            suffix.setText(sliderSetting.getSuffix());

            //Seta o titulo da toolbar para o modo de edicao
            toolbar.setTitle(model.getDevice().getValue().getName() + " - Edit SeekBar");
        }
        else{
            //Seta o titulo da toolbar para o modo de nova feature
            toolbar.setTitle(model.getDevice().getValue().getName() + " - New SeekBar");
        }

        /* Seta um listener no botao da view que dependendo se for edicao ou nao, salva um novo Slider ou edita um*/
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String buttonName = name.getText().toString();
                String topicName = topic.getText().toString();
                String valueStart = value1.getText().toString();
                String valueLast = value2.getText().toString();
                String prefixx = prefix.getText().toString();
                String suffixx = suffix.getText().toString();

                //checa se os campos nao estao nulos e caso estejam mostra um toast indicando o problema
                if(buttonName.isEmpty() || topicName.isEmpty() ||valueStart.isEmpty()
                        || valueLast.isEmpty() || !isBigger(valueLast,valueStart)){
                    Toast.makeText(requireContext(),R.string.invalid_input, Toast.LENGTH_LONG).show();
                }
                else if(prefixx.contains(";") || suffixx.contains(";")){
                    Toast.makeText(requireContext(),"\";\" não pode ser utilizado!", Toast.LENGTH_LONG).show();
                }
                else {
                    if (model.getEdit().getValue()) {
                        saveEditToDB(controlsList.get(position).getId(), buttonName, topicName, "slider", valueStart, valueLast, prefixx, suffixx);
                    } else {
                        saveNewButtonToDB(buttonName, topicName, valueStart, valueLast, prefixx, suffixx);
                    }
                    Navigation.findNavController(requireView()).navigateUp();
                }
            }
        });

        return view;
    }

    /* De acordo com a instancia de db dentro da viewmodel da um update do controle em questao*/
    public void saveEditToDB(int id, String name, String topic, String type, String valueStart, String valueEnd, String prefix, String suffix){
        String value = valueStart + ";" + valueEnd + ";" + prefix + ";" + suffix;
        model.getDb().getValue().updateFeature(id,name,topic,type,value);
    }

    /* Salva uma nova feature no db */
    public void saveNewButtonToDB(String name, String topic, String start, String end, String prefix, String suffix){
        model.getDb().getValue().insertFeature(name, topic, "slider",start + ";" + end + ";" + prefix + ";" + suffix, model.getDevice().getValue().getId());
    }

    /* Retorna true caso seja um inteiro, e false caso contrario*/
    boolean isInt(String text){
        try{
            Integer.parseInt(text);
            return true;
        }
        catch(NumberFormatException e) {
            return false;
        }
    }

    /* Compara duas strings que possam ser convertidas para inteiro, e retorna true caso a primeira passada como parametro seja maior*/
    boolean isBigger(String a, String b){
        if(Integer.parseInt(a) > Integer.parseInt(b)){
            return true;
        }
        else{
            return false;
        }
    }
}