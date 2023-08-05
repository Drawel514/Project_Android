package com.DanielRojas.ADSI;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;


public class RegistrarFragment extends Fragment {
    //Atributos

    private EditText Rnombre;

    private EditText Rcorreo;
    private EditText Rcontraseña;
    private EditText RconfirmarContraseña;
    private Button btnEnviarR;
    private Button btnCancelarR;


    public RegistrarFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) { View view = inflater.inflate(R.layout.fragment_registrar, container, false);
        //Inicializamos las vistas
        Rnombre = view.findViewById(R.id.Rnombre);
        Rcorreo = view.findViewById(R.id.Rcorreo);
        Rcontraseña = view.findViewById(R.id.Rcontraseña);
        RconfirmarContraseña = view.findViewById(R.id.RconfirmarContraseña);

        btnEnviarR = view.findViewById(R.id.btnEnviarR);
        btnCancelarR = view.findViewById(R.id.btnCancelarR);

        btnEnviarR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Obtenemos los datos ingresados
                String nombres = Rnombre.getText().toString();
                String correo = Rcorreo.getText().toString();
                String contraseña = Rcontraseña.getText().toString();
                String confirmcontraseña = RconfirmarContraseña.getText().toString();

                //Hacemos lña verificacion de datos vacios
                if(nombres.trim().isEmpty()||correo.trim().isEmpty()||correo.trim().isEmpty()||contraseña.trim().isEmpty()||confirmcontraseña.trim().isEmpty())
                {
                    if (nombres.trim().isEmpty()){
                    Rnombre.setError("Ingrese algun nombre para continuar");
                    }
                    if (correo.trim().isEmpty()){
                    Rcorreo.setError("Debe ingresar un correo para continuar");
                    }
                    if (contraseña.trim().isEmpty()){
                    Rcontraseña.setError("Ingrese una contraseña");
                    }
                    if (confirmcontraseña.trim().isEmpty()){
                    RconfirmarContraseña.setError("Confirme la contraseña que ingreso");
                    }
                }else if (!contraseña.equals(confirmcontraseña)) {
                    Toast.makeText(getContext(), "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                }else {
                    //Enviamos los datos ingresados al servidor
                    AsyncHttpClient httpCliente = new AsyncHttpClient();
                    RequestParams parametros = new RequestParams();
                    parametros.put("nombre", nombres);
                    parametros.put("email", correo);
                    parametros.put("contra", contraseña);
                    parametros.put("contraConfirm", confirmcontraseña);


                }
            }
        });

        return view;
    }
}