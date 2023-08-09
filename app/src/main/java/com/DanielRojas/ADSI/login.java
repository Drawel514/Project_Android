package com.DanielRojas.ADSI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.DanielRojas.ADSI.funciones.Funciones;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class login extends AppCompatActivity implements View.OnClickListener{
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

        //miramos si existe un session_id
        SharedPreferences session = getSharedPreferences("session",MODE_PRIVATE);
        String session_id = session.getString("session_id","");
        if (!session_id.isEmpty()){
            Toast.makeText(this, "Verificando sesión...", Toast.LENGTH_SHORT).show();
            AsyncHttpClient httpCliente=new AsyncHttpClient();
            RequestParams parametros=  new RequestParams();
            parametros.put("action","verificariniciarsesion");
            parametros.put("session_id",session_id);

            httpCliente.post(Funciones.urlIniciarSesion(), parametros, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    String respuesta=new String(responseBody);
                    try {
                        validarSesion(statusCode,respuesta);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    Toast.makeText(login.this, ""+error.toString(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void validarSesion(int statusCode, String r) throws JSONException {
        JSONObject respuesta=new JSONObject(r);
        String validacion=respuesta.getString("validacion");
        if (validacion.equals("true")){
            //lo mando al inicio
            Intent intento = new Intent(login.this,MainActivity.class);
            startActivity(intento);
        }
        //Si el false no se retorna nada, queda en la misma vista
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