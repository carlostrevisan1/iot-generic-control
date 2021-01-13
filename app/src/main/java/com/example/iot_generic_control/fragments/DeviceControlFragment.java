package com.example.iot_generic_control.fragments;

import android.os.Bundle;

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
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.iot_generic_control.R;
import com.example.iot_generic_control.classes.IOTDevice;
import com.example.iot_generic_control.viewmodels.DeviceViewModel;

import java.util.ArrayList;

public class DeviceControlFragment extends Fragment {

    IOTDevice device;
    DeviceViewModel model;
    public DeviceControlFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
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
        device = model.getDevice().getValue();
        toolbar.setTitle(device.getName());
        //the layout on which you are working

        final LinearLayout layout = (LinearLayout) view.findViewById(R.id.controlLinearLayout);

        Button btnTag = new Button(requireActivity());
        btnTag.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        btnTag.setText("Button");

        Button btn2 = new Button(requireActivity());
        btn2.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        btn2.setText("Bot");

        ArrayList<Button> listBota = new ArrayList<>();

        listBota.add(btnTag);
        listBota.add(btn2);

        for (final Button b: listBota) {
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(requireContext(), b.getText(), Toast.LENGTH_SHORT).show();
                }
            });
            layout.addView(b);
        }
        //add button to the layout


        //set the properties for button


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
}