package com.example.iot_generic_control.fragments;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.iot_generic_control.R;
import com.example.iot_generic_control.classes.DB;
import com.example.iot_generic_control.classes.IOTDevice;
import com.example.iot_generic_control.viewmodels.DeviceViewModel;



public class menuAddDeviceFragment extends Fragment {

    Button ok;
    DeviceViewModel model;
    public menuAddDeviceFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_menu_add_device, container, false);
        model = new ViewModelProvider(requireActivity()).get(DeviceViewModel.class);

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

        Button button = view.findViewById(R.id.ok_button);
        final EditText name = view.findViewById(R.id.device_name);
        final EditText desc = view.findViewById(R.id.device_desc);
        final EditText broker  = view.findViewById(R.id.device_broker);
        final EditText port = view.findViewById(R.id.device_port);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                IOTDevice newDevice = new IOTDevice(name.getText().toString(), desc.getText().toString(), broker.getText().toString(), port.getText().toString());
                saveToDB(newDevice);
                Navigation.findNavController(requireView()).navigateUp();
            }
        });
        return view;
    }

    void saveToDB(IOTDevice newDevice){
        model.getDb().getValue().insertDevice(newDevice.getName(), newDevice.getDesc(), newDevice.getBrokerIP(), newDevice.getBrokerPort());

    }
    
}