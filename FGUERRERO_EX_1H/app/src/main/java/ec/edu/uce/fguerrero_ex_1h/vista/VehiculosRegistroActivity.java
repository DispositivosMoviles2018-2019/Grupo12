package ec.edu.uce.fguerrero_ex_1h.vista;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import ec.edu.uce.fguerrero_ex_1h.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;

import ec.edu.uce.fguerrero_ex_1h.modelo.Vehiculo;

public class VehiculosRegistroActivity extends AppCompatActivity {

    Spinner dias, mes, año;
    Switch matriculado;
    CheckBox color1, color2, color3,color4;
    EditText placa, marca , costo;
    static String f_dia , f_mes , f_año;
    Date fecha_Fabricacion;
    private static boolean vmatriculado;
    private static String vplaca, vmarca , vcolor;
    private static double vcosto;
    private static ArrayList<Vehiculo> arrayVehiculos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehiculos_registro);
        placa = (EditText) findViewById(R.id.placa);
        marca = (EditText) findViewById(R.id.marca);
        costo = (EditText) findViewById(R.id.costo);
        dias = (Spinner) findViewById(R.id.dias);
        mes = (Spinner) findViewById(R.id.meses);
        año = (Spinner) findViewById(R.id.años);
        matriculado = (Switch) findViewById(R.id.matriculado);
        color1 = (CheckBox) findViewById(R.id.color1);
        color2 = (CheckBox) findViewById(R.id.color2);
        color3 = (CheckBox) findViewById(R.id.color3);
        color4 = (CheckBox) findViewById(R.id.color4);
        recuperarVehiculos();
        ArrayAdapter<CharSequence> adapter_dias = ArrayAdapter.createFromResource(this,
                R.array.dias, android.R.layout.simple_spinner_item);
        dias.setAdapter(adapter_dias);

        ArrayAdapter<CharSequence> adapter_meses = ArrayAdapter.createFromResource(this,
                R.array.meses, android.R.layout.simple_spinner_item);
        mes.setAdapter(adapter_meses);

        ArrayAdapter<CharSequence> adapter_años = ArrayAdapter.createFromResource(this,
                R.array.años, android.R.layout.simple_spinner_item);
        año.setAdapter(adapter_años);

        dias.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                f_dia = String.valueOf(dias.getSelectedItem());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                f_dia = null;
            }
        });

        mes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                f_mes = String.valueOf(mes.getSelectedItem());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                f_mes = null;
            }
        });

        año.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                f_año = String.valueOf(año.getSelectedItem());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                f_año = null;
            }
        });
    }

    public void validarDatos (View view){
        boolean verdadTextos = validarTextos();
        boolean verdadPlaca = validarPlaca();
        boolean verdadColor = validarColor();
        boolean verdadFecha = validarFecha();
        validarMatricula();

        if (!verdadTextos){
            Toast toast1 = Toast.makeText(getApplicationContext(), "Falta ingresos", Toast.LENGTH_SHORT);
            //toast1.setGravity(Gravity.CENTER, 0, 0);
            toast1.show();
        }else if (!verdadPlaca){
            Toast toast1 = Toast.makeText(getApplicationContext(), "Formato de placa erroneo", Toast.LENGTH_SHORT);
            //toast1.setGravity(Gravity.CENTER, 0, 0);
            toast1.show();
        }else if (!verdadColor){
            Toast toast1 = Toast.makeText(getApplicationContext(), "Falta registrar color", Toast.LENGTH_SHORT);
            //toast1.setGravity(Gravity.CENTER, 0, 0);
            toast1.show();
        }else if (!verdadFecha){
            Toast toast1 = Toast.makeText(getApplicationContext(), "Falta fecha", Toast.LENGTH_SHORT);
            //toast1.setGravity(Gravity.CENTER, 0, 0);
            toast1.show();
        }else {
            int dia = Integer.parseInt(f_dia);
            int mes = Integer.parseInt(f_mes) - 1;
            int año = Integer.parseInt(f_año) - 1900;
            fecha_Fabricacion = new Date(año,mes,dia);
            Vehiculo v = new Vehiculo(vplaca,vmarca,fecha_Fabricacion,vcosto,vmatriculado,vcolor);
            if (verificarVehiculo(v)){
                arrayVehiculos.add(v);
                Toast toast1 = Toast.makeText(getApplicationContext(), "Registro exitoso", Toast.LENGTH_SHORT);
                //toast1.setGravity(Gravity.CENTER, 0, 0);
                toast1.show();
            }else{
                Toast toast1 = Toast.makeText(getApplicationContext(), "El vehículo ya ha sido registrado", Toast.LENGTH_SHORT);
                //toast1.setGravity(Gravity.CENTER, 0, 0);
                toast1.show();
            }
            placa.setText(null);
            marca.setText(null);
            costo.setText(null);
            matriculado.setChecked(false);
            color1.setChecked(false);
            color2.setChecked(false);
            color3.setChecked(false);
        }
    }




    private void validarMatricula(){
        if (matriculado.isChecked()){
            vmatriculado = true;
        }else {
            vmatriculado = false;
        }
    }

    private boolean validarTextos (){
        boolean bandera = true;
        vmarca = marca.getText().toString();
        try {
            vcosto = Double.parseDouble(costo.getText().toString());
        }catch (NumberFormatException n){}
        if (vmarca.equals("")){
            bandera = false;
        }else if (vcosto == 0){
            bandera = false;
        }else if (costo.getText().toString().equals("")){
            bandera = false;
        }
        return bandera;
    }

    private boolean validarPlaca (){
        boolean bandera = false;
        boolean bandera1 = false;
        int contador = 0;
        vplaca = placa.getText().toString();
        char[] caracteres = vplaca.toCharArray();
        if(caracteres[8]%2!=0){
            Toast toast = Toast.makeText(getApplicationContext(), "el ultimo numero debe ser par", Toast.LENGTH_SHORT);
            toast.show();
        }else {
            if (caracteres.length == 8 & caracteres[3] == '-') {
                int j = 0;
                while (j < caracteres.length) {
                    if (j < 3) {
                        if (!Character.isDigit(caracteres[j])) {
                            bandera = true;
                            contador = contador + 1;
                        }
                    }
                    if (j > 3) {
                        if (Character.isDigit(caracteres[j])) {
                            bandera1 = true;
                            contador = contador + 1;
                        }
                    }
                    j = j + 1;
                }
            }
        }
        if (bandera & bandera1 & contador == 7){
            return true;
        }else{
            return false;
        }



    }

    private boolean validarColor (){
        boolean bandera = true;
        if (color1.isChecked()==true){
            vcolor = "Blanco";
        }else if (color2.isChecked()== true){
            vcolor = "Negro";
        }else if (color3.isChecked()== true){
            vcolor = "Otro";
        }else{
            bandera = false;
        }
        return bandera;
    }

    private boolean validarFecha (){

        boolean bandera = true;
        if (f_dia == null){
            bandera = false;
        }
        if (f_mes == null){
            bandera = false;
        }
        if (f_año == null){
            bandera = false;
        }
        return bandera;

    }

    private void recuperarVehiculos() {
        //Obtener el Intent que hemos recibido
        Intent intent = getIntent();
        //Recuperamos la cadena json
        String json = intent.getStringExtra("jsonVehiculos");
        //Creamos un objeto Gson
        Gson gson = new Gson();
        //Creamos un nuevo Usuario a partir de json
        Type vahiculoType = new TypeToken<ArrayList<Vehiculo>>(){}.getType();
        arrayVehiculos = gson.fromJson(json, vahiculoType);

    }

    public boolean verificarVehiculo (Vehiculo v){
        boolean bandera = true;
        try {
            for (int i = 0; i < arrayVehiculos.size(); i++) {
                if (arrayVehiculos.get(i).getPlaca().equals(v.getPlaca())) {
                    bandera = false;
                }
            }
        }catch (NullPointerException n){
            Toast toast1 = Toast.makeText(getApplicationContext(), "Falla en el Registro", Toast.LENGTH_SHORT);
            //toast1.setGravity(Gravity.CENTER, 0, 0);
            toast1.show();
        }
        return bandera;
    }

    public void enviarVehiculosNuevos(View view) {
        Gson gson = new Gson();
        String jsonVehiculosNuevos = gson.toJson(arrayVehiculos);
        //Crearemos un Intent y ahora pasaremos la cadena json
        Intent intent = new Intent(this, VehiculosActivity.class);
        //Almacenamos la cadena json en el Intent con el nombre que queramos
        intent.putExtra("jsonVehiculosNuevos", jsonVehiculosNuevos);
        //Iniciamos la Activity usando el Intent, que ya lleva el nombre
        startActivity(intent);
    }

}
