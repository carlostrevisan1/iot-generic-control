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

        /* Infla e associa a view a uma variavel, carrega a viewModel para a variavel model*/
        View view = inflater.inflate(R.layout.fragment_menu_add_device, container, false);
        model = new ViewModelProvider(requireActivity()).get(DeviceViewModel.class);

        /* Seta a toolbar e configura o botao de voltar para que volte ao fragmento anterior */
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

        /* Carrega os elementos da view para as variaveis*/
        Button button = view.findViewById(R.id.ok_button);
        final EditText name = view.findViewById(R.id.device_name);
        final EditText desc = view.findViewById(R.id.device_desc);
        final EditText broker  = view.findViewById(R.id.device_broker);
        final EditText port = view.findViewById(R.id.device_port);
        /* Seta o Listener de click no botao de ok*/
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                /* Pega as informacoes que estao nos elementos carregando uma instancia do objeto IOTDevice que é passado pra uma funcao que o salva no banco e logo apos
                * o app navega para o fragmento anterior */
                IOTDevice newDevice = new IOTDevice(name.getText().toString(), desc.getText().toString(), broker.getText().toString(), port.getText().toString());
                saveToDB(newDevice);
                Navigation.findNavController(requireView()).navigateUp();
            }
        });
        return view;
    }

    /*Salva no Banco um Objeto do tipo IOTDevice */
    void saveToDB(IOTDevice newDevice){
        model.getDb().getValue().insertDevice(newDevice.getName(), newDevice.getDesc(), newDevice.getBrokerIP(), newDevice.getBrokerPort());

    }
    
}