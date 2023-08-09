package com.DanielRojas.ADSI;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.DanielRojas.ADSI.funciones.Funciones;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {
    //Atributos
    TextView NombreUsuario, MensajeInstruccion;
    FloatingActionButton btnFloatCerrarSesion;
    ImageView imagenUsuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NombreUsuario=findViewById(R.id.NombreUsuario);
        NombreUsuario.setText("Cargando...");
        imagenUsuario=findViewById(R.id.imagenUsuario);
        MensajeInstruccion=findViewById(R.id.MensajeInstruccion);
        btnFloatCerrarSesion=findViewById(R.id.btnFloatCerrarSesion);


        AsyncHttpClient httpCliente=new AsyncHttpClient();
        RequestParams parametros=  new RequestParams();
        //obtenemos session_id
        SharedPreferences session = getSharedPreferences("session",MODE_PRIVATE);
        parametros.put("session_id",session.getString("session_id",""));

        httpCliente.post(Funciones.urlDatosUsuario(), parametros, new AsyncHttpResponseHandler() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String respuesta=new String(responseBody);
                Log.e("usuario",respuesta);
                try {
                    JSONObject res = new JSONObject(respuesta);
                    JSONObject usuario = res.getJSONObject("usuario");
                    NombreUsuario.setText(usuario.getString("username"));
                    MensajeInstruccion.setText("Bienvenido " + usuario.getString("username") + ", para empezar el proceso de filtración solo carga tu archivo de texto.");

                    Picasso.with(MainActivity.this).load(usuario.getString("imagen")).into(imagenUsuario);

                } catch (JSONException e) {
                    Log.e("Error", "Error al parsear JSON: " + e.getMessage());
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(MainActivity.this, ""+error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        btnFloatCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cerrarSesion();
            }

            private void cerrarSesion() {
                // Limpia las preferencias compartidas
                SharedPreferences.Editor editor = getSharedPreferences("session", MODE_PRIVATE).edit();
                editor.clear();
                editor.apply();

                // Redirige a la pantalla de inicio de sesión
                Intent intent = new Intent(MainActivity.this, login.class);
                startActivity(intent);
                finish(); // Cerramos la actividad actual
            }
        });
    }
}
