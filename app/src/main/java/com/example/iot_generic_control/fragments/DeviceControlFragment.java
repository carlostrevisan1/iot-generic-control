package com.example.iot_generic_control.fragments;

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
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.iot_generic_control.R;
import com.example.iot_generic_control.classes.BaseFeature;
import com.example.iot_generic_control.classes.ButtonFeature;
import com.example.iot_generic_control.classes.IOTDevice;
import com.example.iot_generic_control.classes.SendTextFeature;
import com.example.iot_generic_control.classes.SliderFeature;
import com.example.iot_generic_control.classes.ToggleButtonFeature;
import com.example.iot_generic_control.viewmodels.DeviceViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class DeviceControlFragment extends Fragment {

    IOTDevice device;
    DeviceViewModel model;
    ArrayList<BaseFeature> featuresList = new ArrayList<>();
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

        model = new ViewModelProvider(requireActivity()).get(DeviceViewModel.class);
        retrieveFeatures();
        device = model.getDevice().getValue();
        toolbar.setTitle(device.getName());

        final LinearLayout layout = (LinearLayout) view.findViewById(R.id.controlLinearLayout);
        for (BaseFeature feature: featuresList
             ) {
            switch (feature.getType()){
                case "button":
                    Button b = new Button(requireActivity());
                    b.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    b.setText(feature.getName());
                    layout.addView(b);

                    break;
                case "sendText":
                    EditText t = new EditText(requireActivity());
                    t.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    t.setId(View.generateViewId());
                    Button bu = new Button(requireActivity());
                    bu.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    bu.setText(feature.getName());
                    layout.addView(t);
                    layout.addView(bu);

                    break;
                case "slider":
                    SliderFeature slider = (SliderFeature) feature;
                    SeekBar s = new SeekBar(requireActivity());
                    s.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    s.setMin(slider.getStartRange());
                    s.setMax(slider.getLastRange());
                    layout.addView(s);
                    break;
                case "toggleButton":
                    ToggleButtonFeature toggle = (ToggleButtonFeature) feature;
                    ToggleButton to = new ToggleButton(requireActivity());
                    to.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    to.setTextOff(toggle.getValueOff());
                    to.setTextOn((toggle.getValueOn()));
                    to.setText("0");
                    layout.addView(to);
                    break;
                default:
                    break;
            }

        }

       return view;
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.botao_toolbar_inicial, menu);
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
        ButtonFeature bteste = new ButtonFeature("Botao", "teste", 1, 2, "teste","button");
        SendTextFeature sendteste = new SendTextFeature("EnviarTexto", "teste", 1, 2, "teste", "sendText");
        SliderFeature sliderteste = new SliderFeature("Botao", "teste", 1, 2, 1, 10, "slider");
        ToggleButtonFeature toggleTeste = new ToggleButtonFeature("Togglinho", "teste", 1, 2, "1", "0", "toggleButton");
        featuresList.add(bteste);
        featuresList.add(sendteste);
        featuresList.add(sliderteste);
        featuresList.add(toggleTeste);
        model.setFeatures(featuresList);
    }
}