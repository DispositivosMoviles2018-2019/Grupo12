package com.example.johan.proyecto_final_1h_g12.ec.edu.uce.vista.uce.modelo;

/**
 * Created by JOHAN on 14/11/2018.
 */

public class Usuario {

    private String usuario;
    private String contraseña;

    public Usuario(){

    }

    public Usuario (String usuario, String contraseña){
        this.usuario = usuario;
        this.contraseña = contraseña;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }
}