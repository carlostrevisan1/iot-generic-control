package com.example.iot_generic_control.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.iot_generic_control.R;

public class NewSendTextFragment extends Fragment {

    public NewSendTextFragment() {
        // Required empty public constructor
    }
    public static NewSendTextFragment newInstance(String param1, String param2) {
        NewSendTextFragment fragment = new NewSendTextFragment();
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
        return inflater.inflate(R.layout.fragment_new_send_text, container, false);
    }
}