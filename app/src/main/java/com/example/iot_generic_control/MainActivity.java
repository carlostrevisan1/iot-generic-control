package com.example.iot_generic_control;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;

import android.os.Bundle;
import android.view.MenuItem;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

//Classe utilizada para a criação inicial do aplicativo por isso "Main", uma atividade principal, a primeira.
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Definindo o layout para a atividade
        setContentView(R.layout.activity_main);
    }


}