package com.DanielRojas.ADSI.funciones;

public class Funciones {
    static String urlApi="https://juegossena.azurewebsites.net/api/";
    static public String urlIniciarSesion(){
        return urlApi+"iniciosesionapi/";
    }
    static public String urlRegistrarUsuario(){return urlApi+"registrarusuarionapi/";}
    static public String urlDatosUsuario(){return urlApi+"datosusuarioapi/";}
}
