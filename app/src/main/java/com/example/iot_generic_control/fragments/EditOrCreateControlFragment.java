package com.example.iot_generic_control.fragments;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.iot_generic_control.R;
import com.example.iot_generic_control.utils_adapters.ControlsListViewAdapter;
import com.example.iot_generic_control.utils_adapters.DeviceListViewAdapter;

import java.util.ArrayList;


public class EditOrCreateControlFragment extends Fragment {

    private ListView controlsListView;
    private ArrayList<String> controlsList = new ArrayList<>();
    private ControlsListViewAdapter controlAdapter;

    public EditOrCreateControlFragment() {
        // Required empty public constructor
    }

    public static EditOrCreateControlFragment newInstance(String param1, String param2) {
        EditOrCreateControlFragment fragment = new EditOrCreateControlFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        controlsList.add("Buton de Teste");
        controlsList.add("Buton de Teste2");
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

        controlsListView = view.findViewById(R.id.controls_list);
        controlAdapter = new ControlsListViewAdapter(getContext(), R.layout.devices_list_layout, controlsList);
        controlsListView.setAdapter(controlAdapter);


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
        switch (item.getItemId()) {
            case R.id.plus_button:
                Navigation.findNavController(getView()).navigate(R.id.featureDialogAction);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}