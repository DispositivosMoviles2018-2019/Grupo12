package ec.edu.uce.fguerrero_ex_1h.modelo;

/**
 * Created by JOHAN on 14/11/2018.
 */

import java.io.Serializable;
import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Vehiculo implements Serializable {

    private String placa;
    private String marca;
    private Date fecFabricacion;
    private double costo;
    private boolean matriculado;
    private String color;

    public Vehiculo (){
    }

    public Vehiculo (String placa, String marca, Date fecha, double costo, boolean matriculado, String color){
        this.placa = placa;
        this.marca = marca;
        this.fecFabricacion = fecha;
        this.costo = costo;
        this.matriculado = matriculado;
        this.color = color;
    }

    public String getPlaca() {
        return placa;
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
