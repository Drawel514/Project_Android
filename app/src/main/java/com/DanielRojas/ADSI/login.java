package com.DanielRojas.ADSI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class login extends AppCompatActivity implements InicioSesionFragment.OnLoginListener , View.OnClickListener{
    //Atributos
    Button btnAcceder;
    Button btnRegistrar;
    LinearLayout ContenedorLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        //Relacionamos los atributos con la vista
        btnAcceder = findViewById(R.id.btnAcceder);
        btnAcceder.setOnClickListener((View.OnClickListener) this);

        btnRegistrar = findViewById(R.id.btnRegistrar);
        btnRegistrar.setOnClickListener(this);


    }
    @Override
    public void onClick(View vista) {
        if (vista.getId()==R.id.btnAcceder){
            Fragment FragmentIniciar = new InicioSesionFragment();
            FragmentManager FragmentoManejadorIniciar = getSupportFragmentManager();
            FragmentTransaction FragmentTransaccIniciar = FragmentoManejadorIniciar.beginTransaction();
            FragmentTransaccIniciar.replace(R.id.ContenedorLogin, FragmentIniciar);
            FragmentTransaccIniciar.commit();

            Toast.makeText(this, "Boton Iniciar Sesion", Toast.LENGTH_SHORT).show();

        }else {
            Fragment FragmentIniciar = new RegistrarFragment();
            FragmentManager FragmentoManejadorIniciar = getSupportFragmentManager();
            FragmentTransaction FragmentTransaccIniciar = FragmentoManejadorIniciar.beginTransaction();
            FragmentTransaccIniciar.replace(R.id.ContenedorLogin, FragmentIniciar);
            FragmentTransaccIniciar.commit();

            Toast.makeText(this, "Boton Registrar", Toast.LENGTH_SHORT).show();

        }

    }
}