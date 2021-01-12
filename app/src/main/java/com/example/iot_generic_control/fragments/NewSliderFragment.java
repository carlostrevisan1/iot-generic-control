package com.example.iot_generic_control.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.iot_generic_control.R;


public class NewSliderFragment extends Fragment {

    public NewSliderFragment() {
        // Required empty public constructor
    }

    public static NewSliderFragment newInstance(String param1, String param2) {
        NewSliderFragment fragment = new NewSliderFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_slider, container, false);
    }
}