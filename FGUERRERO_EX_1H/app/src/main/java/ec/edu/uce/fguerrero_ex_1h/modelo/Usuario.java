package ec.edu.uce.fguerrero_ex_1h.modelo;

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
