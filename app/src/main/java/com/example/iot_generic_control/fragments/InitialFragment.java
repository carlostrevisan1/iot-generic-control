package com.example.iot_generic_control.fragments;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.iot_generic_control.utils_adapters.DeviceListViewAdapter;
import com.example.iot_generic_control.classes.IOTDevice;
import com.example.iot_generic_control.R;
import com.example.iot_generic_control.viewmodels.DeviceViewModel;

import java.util.ArrayList;
import java.util.Objects;


public class InitialFragment extends Fragment {

    DeviceListViewAdapter customAdapter;
    ArrayList<IOTDevice> devicesList = new ArrayList<>();
    DeviceViewModel model;

    public InitialFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        retrieveDevices();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_initial, container, false);

        model = new ViewModelProvider(requireActivity()).get(DeviceViewModel.class);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        assert activity != null;
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar();
        setHasOptionsMenu(true);

        ListView devicesListView = view.findViewById(R.id.devices_list);
        customAdapter = new DeviceListViewAdapter(requireContext(), R.layout.devices_list_layout, devicesList);
        devicesListView.setAdapter(customAdapter);

        devicesListView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

                MenuInflater inflater = requireActivity().getMenuInflater();
                inflater.inflate(R.menu.device_list_hold_menu, menu);
            }
        });

        devicesListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                notifyViewModel(devicesList.get(position));
                Navigation.findNavController(requireView()).navigate(R.id.action_initialFragment_to_deviceControlFragment);
            }
        });

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.botao_toolbar_inicial, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.plus_button) {
            Navigation.findNavController(requireView()).navigate(R.id.action_initialFragment_to_menuAddDevice);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.delete_item:
                devicesList.remove(info.position);
                customAdapter.notifyDataSetChanged();
                return true;
            case R.id.edit_item:
                notifyViewModel(devicesList.get(info.position));
                Navigation.findNavController(requireView()).navigate(R.id.initialToEditAction);
            default:
                return super.onContextItemSelected(item);
        }
    }

    public void retrieveDevices(){
        devicesList.add(new IOTDevice("Carlos", "Dispositivo BRABO", "192.168.0.0", "8080", 0));
        devicesList.add(new IOTDevice("Carleeeeeos", "Dispositivo BRABO", "192.168.0.0", "8080", 1));
        devicesList.add(new IOTDevice("ee", "Dispositivo BRABO", "192.168.0.0", "8080", 2));
    }

    private void notifyViewModel(IOTDevice newDevice) {
        model.setDevice(newDevice);
    }

}
