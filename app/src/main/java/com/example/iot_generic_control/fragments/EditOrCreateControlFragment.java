package com.example.iot_generic_control.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.example.iot_generic_control.R;
import com.example.iot_generic_control.classes.BaseFeature;
import com.example.iot_generic_control.classes.IOTDevice;
import com.example.iot_generic_control.utils_adapters.ControlsListViewAdapter;
import com.example.iot_generic_control.utils_adapters.DeviceListViewAdapter;
import com.example.iot_generic_control.viewmodels.DeviceViewModel;

import java.util.ArrayList;


public class EditOrCreateControlFragment extends Fragment {

    private ArrayList<BaseFeature> controlsList = new ArrayList<>();
    private ControlsListViewAdapter controlAdapter;
    DeviceViewModel model;
    IOTDevice  device;

    public EditOrCreateControlFragment() {
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
        View view = inflater.inflate(R.layout.fragment_edit_or_create_control, container, false);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setHasOptionsMenu(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(getActivity(),
                        R.id.fragment);
                navController.navigateUp();
            }
        });
        model = new ViewModelProvider(requireActivity()).get(DeviceViewModel.class);
        controlsList = model.getFeatures().getValue();
        device = model.getDevice().getValue();
        toolbar.setTitle(device.getName() + " - Edit Mode");


        ListView controlsListView = view.findViewById(R.id.controls_list);
        controlAdapter = new ControlsListViewAdapter(requireContext(), R.layout.devices_list_layout, controlsList);
        controlsListView.setAdapter(controlAdapter);

        controlsListView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

                MenuInflater inflater = requireActivity().getMenuInflater();
                inflater.inflate(R.menu.device_list_hold_menu, menu);
            }
        });

        controlsListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//                Navigation.findNavController(requireView()).navigate(R.id.action_initialFragment_to_deviceControlFragment);
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
        // Handle item selection
        if (item.getItemId() == R.id.plus_button) {
            Navigation.findNavController(requireView()).navigate(R.id.featureDialogAction);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.delete_item:
                deleteControl();
                controlsList.remove(info.position);
                controlAdapter.notifyDataSetChanged();
                return true;
            case R.id.edit_item:

//                Navigation.findNavController(requireView()).navigate(R.id.initialToEditAction);
            default:
                return super.onContextItemSelected(item);
        }
    }

    public void deleteControl(){
        //TODO Apagar do DB
    }
}