package com.example.iot_generic_control.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.iot_generic_control.R;


public class NewButtonFragment extends Fragment {

    public NewButtonFragment() {
        // Required empty public constructor
    }

    public static NewButtonFragment newInstance(String param1, String param2) {
        NewButtonFragment fragment = new NewButtonFragment();
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
        View view = inflater.inflate(R.layout.fragment_new_button, container, false);
        return view;
    }
}