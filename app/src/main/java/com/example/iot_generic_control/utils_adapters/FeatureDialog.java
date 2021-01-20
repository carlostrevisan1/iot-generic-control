package com.example.iot_generic_control.utils_adapters;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.iot_generic_control.R;
import com.example.iot_generic_control.viewmodels.DeviceViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class FeatureDialog extends BottomSheetDialogFragment {

    DeviceViewModel model;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        /* Infla a view e carrega a viewmodel, seta a variavel edit como false*/
        final View view = inflater.inflate(R.layout.features_types_dialog, container, false);
        model = new ViewModelProvider(requireActivity()).get(DeviceViewModel.class);
        model.setEdit(false);

        /* Procura na view os campos de botao */
        Button new_button = view.findViewById(R.id.new_button);
        Button new_slider = view.findViewById(R.id.new_slider);
        Button new_toggle = view.findViewById(R.id.new_toggle);
        Button new_send_text = view.findViewById(R.id.new_send);

        /* Seta os listeners pra cada um dos tipos de botao que quando pressionado irá navegar para os respectivos fragmentos*/
        new_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NavController nav = Navigation.findNavController(requireActivity(), R.id.fragment);
                nav.navigate(R.id.newButtonAction);
            }
        });
        new_slider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NavController nav = Navigation.findNavController(requireActivity(), R.id.fragment);
                nav.navigate(R.id.newSliderAction);
            }
        });
        new_toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NavController nav = Navigation.findNavController(requireActivity(), R.id.fragment);
                nav.navigate(R.id.newToggleButtonAction);
            }
        });
        new_send_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NavController nav = Navigation.findNavController(requireActivity(), R.id.fragment);
                nav.navigate(R.id.newSendTextAction);
            }
        });
        return view;
    }
}
