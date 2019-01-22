package ec.edu.uce.ex2h_g12.modelo;



import android.support.annotation.NonNull;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Vehiculo implements Serializable {

    private String placa;
    private String marca;
    private Date fecFabricacion;
    private double costo;
    private boolean matriculado;
    private String color;
 //   private Bitmap foto;
    private boolean estado;
    private Integer tipo;

    public Vehiculo (){
    }

    public Vehiculo (String placa, String marca, Date fecha, double costo, boolean matriculado, String color, boolean estado,Integer tipo){
        this.placa = placa;
        this.marca = marca;
        this.fecFabricacion = fecha;
        this.costo = costo;
        this.matriculado = matriculado;
        this.color = color;

        this.estado= estado;
        this.tipo = tipo;
    }

    public String getPlaca() {
        return placa;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public Integer getTipo() {
        return tipo;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }


    public String getMarca() {
        return marca;
    }

    public Date getFecFabricacion() {
        return fecFabricacion;
    }

    public double getCosto() {
        return costo;
    }

    public boolean isMatriculado() {
        return matriculado;
    }

    public String getColor() {
        return color;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setFecFabricacion(Date fecFabricacion) {
        this.fecFabricacion = fecFabricacion;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public void setMatriculado(boolean matriculado) {
        this.matriculado = matriculado;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String tString (){
        String mat;
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        String fecha = formato.format(fecFabricacion);
        if (matriculado){
            mat = "Si";
        }else{
            mat = "No";
        }
        return "Placa: " + placa + "\n" +
                "Marca: " + marca + "\n" +
                "Costo: " + Double.toString(costo) + "\n" +
                "Color: " + color + "\n" +
                "Matriculado: " + mat + "\n"+
                "Fecha de Fábrica: " + fecha + "\n";
    }


}
