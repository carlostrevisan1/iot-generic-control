package com.example.iot_generic_control.fragments;

import android.annotation.SuppressLint;
import android.graphics.Color;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;


import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.iot_generic_control.R;
import com.example.iot_generic_control.classes.BaseFeature;
import com.example.iot_generic_control.classes.ButtonFeature;
import com.example.iot_generic_control.classes.IOTDevice;
import com.example.iot_generic_control.classes.MQTT;
import com.example.iot_generic_control.classes.SendTextFeature;
import com.example.iot_generic_control.classes.SliderFeature;
import com.example.iot_generic_control.classes.ToggleButtonFeature;
import com.example.iot_generic_control.viewmodels.DeviceViewModel;



import java.util.ArrayList;


public class DeviceControlFragment extends Fragment {

    IOTDevice device;
    DeviceViewModel model;
    ArrayList<BaseFeature> featuresList = new ArrayList<>();
    MQTT mqtt;


    public DeviceControlFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_device_control, container, false);
        model = new ViewModelProvider(requireActivity()).get(DeviceViewModel.class);
        device = model.getDevice().getValue();
        retrieveFeatures();

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
        toolbar.setTitle(device.getName());

        mqtt = new MQTT(
                requireContext(),
                "tcp://" + device.getBrokerIP() + ":" + device.getBrokerPort(),
                device.getName());


        final LinearLayout layout = (LinearLayout) view.findViewById(R.id.controlLinearLayout);
        for (final BaseFeature feature: featuresList
             ) {
            switch (feature.getType()){
                case "button":
                    setupButton(new Button(requireActivity()), (ButtonFeature) feature, layout);
                    break;
                case "sendText":
                    setupSendText(new EditText(requireActivity()), new Button(requireActivity()), (SendTextFeature) feature, layout);
                    break;
                case "slider":
                    setupSlider(new SeekBar(requireActivity()), (SliderFeature) feature, layout);
                    break;
                case "toggleButton":
                    setupToggleButton(new Switch(requireActivity()), (ToggleButtonFeature) feature, layout);
                    break;
                default:
                    break;
            }
        }
       return view;
    }

    @SuppressLint("ResourceAsColor")
    public void setupButton(Button b, final ButtonFeature f, LinearLayout layout){
        //Prepara a feature "button"
        LinearLayout.LayoutParams bLayout = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        bLayout.setMargins(10,10,10,10);
        bLayout.gravity = 1;
        b.setLayoutParams(bLayout);
        b.setText(f.getName());
        b.setBackgroundResource(R.drawable.button);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Ao ser clicado um botão envia para seu tópico o seu "value"
                mqtt.publishMessage(f.getTopic(), f.getValue());

            }
        });

        layout.addView(b);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void setupSendText(final EditText t, Button b, final SendTextFeature f, LinearLayout layout){
        //Prepara a feature sendText
        LinearLayout.LayoutParams editTextLayout = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        editTextLayout.setMargins(10,10,10,10);
        editTextLayout.gravity = 1;
        t.setTextSize(22);
        t.setLayoutParams(editTextLayout);
        t.setId(View.generateViewId());
        t.setHint("Digite um texto para enviar: ");
        t.setAlpha(1);
        t.setBackgroundResource(R.drawable.custom_edittext);

        LinearLayout.LayoutParams bLayout = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        bLayout.setMargins(10,10,10,10);
        bLayout.gravity = 1;
        b.setBackgroundResource(R.drawable.button);
        b.setLayoutParams(bLayout);
        b.setText(f.getName());
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Ao ser clicado envia ao seu tópico o valor digitado
                mqtt.publishMessage(f.getTopic(), t.getText().toString());
            }
        });
        layout.addView(t);
        layout.addView(b);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setupSlider(SeekBar s, final SliderFeature f, LinearLayout layout){
        //Prepara a feature "slider"
        final TextView t = new TextView(getActivity());
        LinearLayout.LayoutParams tLayout = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        tLayout.setMargins(10,10,10,10);
        tLayout.gravity = Gravity.CENTER;
        t.setLayoutParams(tLayout);
        t.setTextSize(19);
        s.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        s.setMin(f.getStartRange());
        s.setMax(f.getLastRange());
        s.setProgress(f.getLastRange()/2);

        s.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChangedValue;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChangedValue = progress;
                t.setText(Integer.toString(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //Ao parar de deslizar um "slider" o valor é enviado ao seu tópico, a mensagem será composta da seguinte maneira: "{prefixo}{valor}{sufixo}"
                //ambos são própios da classe SliderFeature e podem ou não serem vazios
                String msg = f.getPrefix() + Integer.toString(progressChangedValue) + f.getSuffix();
                mqtt.publishMessage(f.getTopic(), msg);
            }
        });
        layout.addView(t);
        layout.addView(s);
    }


    public void setupToggleButton(final Switch t, final ToggleButtonFeature f, LinearLayout layout){
        //Prepara a feature "toggleButton"
        LinearLayout.LayoutParams bLayout = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        bLayout.setMargins(10,10,10,10);
        bLayout.gravity = 0;
        t.setLayoutParams(bLayout);
        t.setTextOff(f.getValueOff());
        t.setTextOn((f.getValueOn()));
        t.setText(f.getValueOff());
        //t.setBackgroundResource(R.drawable.custom_button);
        t.setBackgroundResource(R.drawable.custom_button);


        t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Quando é clicado envia a mensagem de acordo com o estado para que foi alterado
                if(t.isChecked()){
                    t.setText(f.getValueOn());
                    //t.setText("OFF");
                    //t.setBackgroundResource(R.drawable.custom_button);
                    //t.setTextColor(Color.parseColor("#000000"));
                    t.setBackgroundResource(R.drawable.custom_buttton_on);
                    t.setTextColor(Color.parseColor("#69967d"));
                    mqtt.publishMessage(f.getTopic(), f.getValueOn());
                }
                else{
                    t.setText(f.getValueOff());
                    t.setBackgroundResource(R.drawable.custom_button);
                    t.setTextColor(Color.parseColor("#000000"));
                    mqtt.publishMessage(f.getTopic(), f.getValueOff());
                }

            }
        });
        layout.addView(t);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.botao_engrenagem, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        if (item.getItemId() == R.id.settings_button) {
            Navigation.findNavController(requireView()).navigate(R.id.editOrControlAction);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void retrieveFeatures(){
        featuresList.clear();
        featuresList = model.getDb().getValue().selectAllFeatures(model.getDevice().getValue().getId());
        model.setFeatures(featuresList);
    }
}