package com.DanielRojas.ADSI;

import android.app.DownloadManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.DanielRojas.ADSI.funciones.Funciones;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.Header;


public class InicioSesionFragment extends Fragment {
    //Atributos
     private EditText InContraseña;
    private EditText InNombreUsuario;
    private Button btnEnviar;
    private Button btnCancelar;

    public InicioSesionFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {View view = inflater.inflate(R.layout.fragment_inicio_sesion, container, false);
        //Inicializamos las vistas
        InNombreUsuario = view.findViewById(R.id.InNombreUsuario);
        InContraseña = view.findViewById(R.id.InContraseña);

        btnEnviar = view.findViewById(R.id.btnEnviar);
        btnCancelar = view.findViewById(R.id.btnCancelar);

        //Relacionamos los botones con el metodo
        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Obtenemos los datos Ingresados
            String nombreUsuario = InNombreUsuario.getText().toString();
            String contraseña = InContraseña.getText().toString();

                // Verificamos si los campos están vacíos
                if (nombreUsuario.trim().isEmpty() || contraseña.trim().isEmpty()) {
                    if (nombreUsuario.trim().isEmpty()){
                        InNombreUsuario.setError("Debe ingresar un nombre de usuario para continuar");
                    }
                    if (contraseña.trim().isEmpty()){
                        InContraseña.setError("Debe ingresar la contraseña para continuar");
                    }
                }else{
                    AsyncHttpClient httpCliente=new AsyncHttpClient();
                    RequestParams parametros=  new RequestParams();
                    parametros.put("action","iniciarsesion");
                    parametros.put("username",nombreUsuario);
                    parametros.put("password",contraseña);

                    httpCliente.post(Funciones.urlIniciarSesion(), parametros, new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                            String respuesta=new String(responseBody);
                            Log.e("respuesta",respuesta);
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                            Toast.makeText(getContext(), ""+error.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        return view;
    }
}

