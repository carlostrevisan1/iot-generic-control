package com.example.iot_generic_control;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

//Classe utilizada para a atividade da Splash Screen, uma tela de carregamento antes da tela principal ao abrir o app.
public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Definindo um layout para a atividade
        setContentView(R.layout.activity_splash_screen);

        //Evento de mudança de tela
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mostrarMainActivity();
            }
        }, 2000);

    }

    //Utilizada para fazer a transição de tela.
    private void mostrarMainActivity() {
        Intent intent = new Intent(
                SplashScreen.this,MainActivity.class
        );
        startActivity(intent);
        finish();
    }
}