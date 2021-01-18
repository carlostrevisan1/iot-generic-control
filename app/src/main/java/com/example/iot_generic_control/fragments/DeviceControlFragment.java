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
import android.widget.ToggleButton;

import com.example.iot_generic_control.R;
import com.example.iot_generic_control.classes.BaseFeature;
import com.example.iot_generic_control.classes.ButtonFeature;
import com.example.iot_generic_control.classes.DB;
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
        LinearLayout.LayoutParams bLayout = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        bLayout.setMargins(10,10,10,10);
        bLayout.gravity = 1;
        b.setLayoutParams(bLayout);
        b.setText(f.getName());
        b.setBackgroundResource(R.drawable.button);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(requireContext(), f.getValue(), Toast.LENGTH_SHORT).show();
                mqtt.publishMessage(f.getTopic(), f.getValue());

            }
        });

        layout.addView(b);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void setupSendText(final EditText t, Button b, final SendTextFeature f, LinearLayout layout){
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
                Toast.makeText(requireContext(), t.getText().toString(), Toast.LENGTH_SHORT).show();
                mqtt.publishMessage(f.getTopic(), t.getText().toString());


            }
        });
        layout.addView(t);
        layout.addView(b);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setupSlider(SeekBar s, final SliderFeature f, LinearLayout layout){
        final TextView t = new TextView(getActivity());
        t.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        s.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        s.setMin(f.getStartRange());
        s.setMax(f.getLastRange());
        s.setProgress(f.getLastRange()/2);

        s.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChangedValue = 0;

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
               Toast.makeText(requireContext(), Integer.toString(progressChangedValue), Toast.LENGTH_SHORT).show();
                mqtt.publishMessage(f.getTopic(), Integer.toString(progressChangedValue));

            }
        });
        layout.addView(t);
        layout.addView(s);
    }


    public void setupToggleButton(final Switch t, final ToggleButtonFeature f, LinearLayout layout){
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
                if(t.isChecked()){
                    Toast.makeText(requireContext(), t.getTextOff(), Toast.LENGTH_SHORT).show();
                    t.setText(f.getValueOn());
                    //t.setText("OFF");
                    //t.setBackgroundResource(R.drawable.custom_button);
                    //t.setTextColor(Color.parseColor("#000000"));
                    t.setBackgroundResource(R.drawable.custom_buttton_on);
                    t.setTextColor(Color.parseColor("#69967d"));
                    mqtt.publishMessage(f.getTopic(), f.getValueOn());
                }
                else{
                    Toast.makeText(requireContext(), t.getTextOff(), Toast.LENGTH_SHORT).show();
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
        if (item.getItemId() == R.id.plus_button) {
            Navigation.findNavController(requireView()).navigate(R.id.editOrControlAction);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void retrieveFeatures(){
        featuresList.clear();

        /*ButtonFeature bteste = new ButtonFeature("", "teste", 1, 2, "teste","button");
        ButtonFeature bteste2 = new ButtonFeature("Botao", "teste", 1, 2, "teste2","button");
        ButtonFeature bteste3 = new ButtonFeature("Botao", "teste", 1, 2, "Bis, vc é mto tonto","button");
        SendTextFeature sendteste = new SendTextFeature("Enviar Texto", "teste", 1, 2, "teste", "sendText");
        SliderFeature sliderteste = new SliderFeature("Botao", "teste", 1, 2, 1, 10, "slider");
        ToggleButtonFeature toggleTeste = new ToggleButtonFeature("Togglinho", "teste", 1, 2, "ON", "OFF", "toggleButton");
        SliderFeature sliderteste2 = new SliderFeature("tt", "teste", 1, 2, 1, 20, "slider");
        SliderFeature sliderteste3 = new SliderFeature("2222", "teste", 1, 2, 1, 5, "slider");
        SendTextFeature sendteste2 = new SendTextFeature("Enviar Texto", "teste", 1, 2, "teste", "sendText");
        SendTextFeature sendteste3 = new SendTextFeature("Enviar Texto", "teste", 1, 2, "teste", "sendText");*/


       /*featuresList.add(bteste);
        featuresList.add(bteste2);
        featuresList.add(bteste3);
        featuresList.add(sendteste);
        featuresList.add(sendteste2);
        featuresList.add(sendteste3);
        featuresList.add(sliderteste);
        featuresList.add(sliderteste2);
        featuresList.add(sliderteste3);
        featuresList.add(toggleTeste);*/

        featuresList = model.getDb().getValue().selectAllFeatures(model.getDevice().getValue().getId());

        model.setFeatures(featuresList);
    }
}