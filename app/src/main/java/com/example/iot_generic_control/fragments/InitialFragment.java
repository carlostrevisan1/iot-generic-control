package com.example.iot_generic_control.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.iot_generic_control.classes.DB;
import com.example.iot_generic_control.utils_adapters.DeviceListViewAdapter;
import com.example.iot_generic_control.classes.IOTDevice;
import com.example.iot_generic_control.R;
import com.example.iot_generic_control.viewmodels.DeviceViewModel;

import java.util.ArrayList;



public class InitialFragment extends Fragment {


    DeviceListViewAdapter customAdapter;
    ArrayList<IOTDevice> devicesList = new ArrayList<>();
    DeviceViewModel model;
    DB db;

    public InitialFragment() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        /* Infla a view e a retorna para uma variavel */
        View view = inflater.inflate(R.layout.fragment_initial, container, false);
        /* Carrega a view model para a variavel model */
        model = new ViewModelProvider(requireActivity()).get(DeviceViewModel.class);
        /* Instancia o banco de dados e seta na view model o banco, para assim ser acessado em todos os fragmentos */
        db = new DB(requireContext());
        model.setDb(db);
        model.setEdit(false);
        /* Chama a funcao que carrega a lista de dispositivos do banco para a variavel 'devicesList' */
        retrieveDevices();

        /* Instancia e seta a toolbar na activity e no fragmento */
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) requireActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar();
        setHasOptionsMenu(true);

        /* Instancia a listview e seta o seu adapter, passando os dispositivos de devicesList para a listview*/
        ListView devicesListView = view.findViewById(R.id.devices_list);
        customAdapter = new DeviceListViewAdapter(requireContext(), R.layout.devices_list_layout, devicesList);
        devicesListView.setAdapter(customAdapter);

        /* Associa o menu de contexto com a listView */
        devicesListView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                MenuInflater inflater = requireActivity().getMenuInflater();
                inflater.inflate(R.menu.device_list_hold_menu, menu);
            }
        });

        /* Listener para o click dentro de um elemento da listView */
        devicesListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                notifyViewModel(devicesList.get(position));
                Navigation.findNavController(requireView()).navigate(R.id.action_initialFragment_to_deviceControlFragment);
            }
        });

        return view;
    }

    /*Infla a toolbar */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.botao_toolbar_inicial, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    /* Funcao que trata a selecao de um item presente na toolbar, no caso somente o botao com o simbolo "+" */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.settings_button) {
            Navigation.findNavController(requireView()).navigate(R.id.action_initialFragment_to_menuAddDevice);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /* Trata o menu de contexto ao segurar um item na listView */
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.delete_item:
                deleteDevice(info.position);
                devicesList.remove(info.position);
                customAdapter.notifyDataSetChanged();
                return true;
            case R.id.edit_item:
                notifyViewModel(devicesList.get(info.position));
                Navigation.findNavController(requireView()).navigate(R.id.action_initialFragment_to_menuAddDevice);
            default:
                return super.onContextItemSelected(item);
        }
    }

    /* Coleta os dispositivos cadastrados dentro do banco de dados salvando o resultado na lista de dispositivos */
    public void retrieveDevices(){

        devicesList = model.getDb().getValue().selectAllDevices();
    }

    /* Seta o device selecionado dentro da viewModel */
    private void notifyViewModel(IOTDevice newDevice) {
        model.setDevice(newDevice);
        model.setEdit(true);
    }

    /*Deleta o dispositivo na posicao "pos" da lista de dispositivos*/
    public void deleteDevice(Integer pos){
        model.getDb().getValue().deleteFrom("device", devicesList.get(pos).getId() );
    }

}
