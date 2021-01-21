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
import android.widget.Toast;

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
        final EditText color = view.findViewById(R.id.device_color);

        /* Se o valor de Edit for verdadeiro, entao é para carregar os valores do device passado e possibilitando editar, se nao é uma operacao de adicionar um novo device*/
        if(model.getEdit().getValue()){
            name.setText(model.getDevice().getValue().getName());
            desc.setText(model.getDevice().getValue().getDesc());
            broker.setText(model.getDevice().getValue().getBrokerIP());
            port.setText(model.getDevice().getValue().getBrokerPort());
            color.setText(model.getDevice().getValue().getColour());
        }
        /* Seta o Listener de click no botao de ok*/
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                /* Pega as informacoes que estao nos elementos que sao salvas no banco e logo apos
                * o app navega para o fragmento anterior */
                //checa se os campos nao estao nulos e caso estejam mostra um toast indicando o problema
                if(name.getText().toString().isEmpty() || desc.getText().toString().isEmpty() || desc.getText().toString().isEmpty()
                        || broker.getText().toString().isEmpty() || port.getText().toString().isEmpty() || color.getText().toString().isEmpty()){
                    Toast.makeText(requireContext(),R.string.invalid_input, Toast.LENGTH_LONG).show();
                }
                else{

                    if(model.getEdit().getValue()){
                        /* Atualiza o dispositivo no banco*/
                        model.getDb().getValue().updateDevice(model.getDevice().getValue().getId(), name.getText().toString(), desc.getText().toString(),
                                                              broker.getText().toString(),  port.getText().toString(), color.getText().toString());                     }
                    else{
                        /*Salva no Banco um Objeto do tipo IOTDevice */
                        model.getDb().getValue().insertDevice(name.getText().toString(), desc.getText().toString(), broker.getText().toString(),
                                                              port.getText().toString(), color.getText().toString()); //TODO: Pegar input de cor
                    }
                    model.setEdit(false);
                    Navigation.findNavController(requireView()).navigateUp();
                }
            }
        });
        return view;
    }

    
}