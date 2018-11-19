package com.example.johan.proyecto_final_1h_g12.ec.edu.uce.vista.uce.vista;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.johan.proyecto_final_1h_g12.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.example.johan.proyecto_final_1h_g12.ec.edu.uce.vista.uce.modelo.Vehiculo;

public class VehiculosActivity extends AppCompatActivity {

    private static Vehiculo vehiculo = new Vehiculo();
    int posicionEditar;
    String f_dia,f_mes,f_año;
    Date fecha_Fabricacion, fecha_antigua;
    boolean vmatriculado;
    String vplaca, vmarca , vcolor;
    double vcosto;
    ListView listView;
    Vehiculo vehiculo1 = new Vehiculo("XTR-9784","Audi", new Date(115,10,13),79990.0,true,"Negro");
    Vehiculo vehiculo2 = new Vehiculo("CCD-0789","Honda", new Date(96,2,5),15340.0,false,"Blanco");
    private static ArrayList<Vehiculo> vehiculos = new ArrayList<>();
    private static ArrayList <String> mejorado = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehiculos);
        listView = (ListView) findViewById(R.id.listv);
        if (!vehiculos.isEmpty()){
            recuperarVehiculosNuevos();
        }
        escribirList();
    }

    public ArrayList <String> arreglarArray(ArrayList<Vehiculo> ve){
        ArrayList <String> nuevo = new ArrayList<>();
        for (int i = 0 ; i<ve.size() ; i++){
            nuevo.add(ve.get(i).tString());
        }
        return nuevo;
    }

    public void escribirList (){
        try {
            if (vehiculos.isEmpty()) {
                vehiculos.add(vehiculo1);
                vehiculos.add(vehiculo2);
            }
            mejorado = arreglarArray(vehiculos);
            ArrayAdapter<String> adaptador = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,mejorado);
            listView.setAdapter(adaptador);
            adaptador.notifyDataSetChanged();
        }catch (NullPointerException ignored){}

    }

    public void actualizar (View view){
        escribirList();
    }

    public void recuperarVehiculosNuevos() {
        //Obtener el Intent que hemos recibido
        Intent intent = getIntent();
        //Recuperamos la cadena json
        String json = intent.getStringExtra("jsonVehiculosNuevos");
        //Creamos un objeto Gson
        Gson gson = new Gson();
        //Creamos un nuevo Usuario a partir de json
        Type vahiculoType = new TypeToken<ArrayList<Vehiculo>>(){}.getType();
        vehiculos = gson.fromJson(json, vahiculoType);

    }

    public void ingresarDato (){
        Intent intent = new Intent(this, VehiculosRegistroActivity.class);
        startActivity(intent);
    }



    /*
    public void escribirJson(View v){
        Gson gson = new Gson ();
        String u = usuario.getText().toString();
        String c = contraseña.getText().toString();
        Usuario nuevo = new Usuario (u,c);
        if (verificarUsuario(nuevo)){
            arrayUsuarios.add(nuevo);
            jsonString = gson.toJson(arrayUsuarios);
            IOHelper.writeToFile(this, "usuarios.txt", jsonString);
            Toast toast1 = Toast.makeText(getApplicationContext(), "Registro exitoso", Toast.LENGTH_SHORT);
            toast1.setGravity(Gravity.CENTER, 0, 0);
            toast1.show();
            usuario.setText(null);
            contraseña.setText(null);
        }else {
            Toast toast1 = Toast.makeText(getApplicationContext(), "Usuario ya Registrado", Toast.LENGTH_SHORT);
            toast1.setGravity(Gravity.CENTER, 0, 0);
            toast1.show();
            usuario.setText(null);
            contraseña.setText(null);
        }*/

    public void eliminarVehiculo(View view){
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(VehiculosActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.eliminar_vehiculo,null);
        final EditText eliminarPlaca = (EditText) mView.findViewById(R.id.eliminarPlaca);
        Button eliminar = (Button) mView.findViewById(R.id.elim);

        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        dialog.show();
        eliminar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String placaElim = eliminarPlaca.getText().toString();
                buscarPlaca(placaElim);
                dialog.cancel();

            }
        });
    }

    public void buscarPlaca (String placa){
        for (int i = 0 ; i < vehiculos.size() ; i++){
            Vehiculo v = vehiculos.get(i);
            if (placa.equals(v.getPlaca())){
                vehiculos.remove(v);
                escribirList();
            }
        }

    }

    /*
    public Vehiculo buscarObjetoVehiculo (String placa){
        Vehiculo v = new Vehiculo();
        v = null;
        for (int i = 0 ; i < vehiculos.size() ; i++){
            if (placa.equals(vehiculos.get(i).getPlaca())){
                v = vehiculos.get(i);
            }
        }
        return v;
    }*/

    public void enviarVehiculos(View view) {
        Gson gson = new Gson();
        String jsonVehiculos = gson.toJson(vehiculos);
        //Crearemos un Intent y ahora pasaremos la cadena json
        Intent intent = new Intent(this, VehiculosRegistroActivity.class);
        //Almacenamos la cadena json en el Intent con el nombre que queramos
        intent.putExtra("jsonVehiculos", jsonVehiculos);
        //Iniciamos la Activity usando el Intent, que ya lleva el nombre
        startActivity(intent);
    }
/*
    public void enviarObjetoVehiculo(View view, Vehiculo ve) {
        //Crearemos un Intent y ahora pasaremos la cadena json
        Intent intent = new Intent(this, EditarVehiculosActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("vehiculo",ve);
        intent.putExtras(bundle);
        startActivity(intent);
    }*/

    public void editarVehiculo(View view){

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(VehiculosActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.editar_vehiculo,null);
        final TextView placa = (TextView) mView.findViewById(R.id.textView);
        final TextView fecha1 = (TextView) mView.findViewById(R.id.fecha);
        final EditText marca = (EditText) mView.findViewById(R.id.marca1);
        final EditText costo = (EditText) mView.findViewById(R.id.costo1);
        final EditText placaBuscar = (EditText) mView.findViewById(R.id.ingresoPlaca);
        final Spinner dias = (Spinner) mView.findViewById(R.id.dias1);
        final Spinner mes = (Spinner) mView.findViewById(R.id.meses1);
        final Spinner año = (Spinner) mView.findViewById(R.id.años1);
        final Switch matriculado = (Switch) mView.findViewById(R.id.matriculado1);
        final RadioButton color1 = (RadioButton) mView.findViewById(R.id.color11);
        final RadioButton color2 = (RadioButton) mView.findViewById(R.id.color21);
        final RadioButton color3 = (RadioButton) mView.findViewById(R.id.color31);
        Button edit = (Button) mView.findViewById(R.id.registro1);
        Button buscar = (Button) mView.findViewById(R.id.buscar);
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

        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        dialog.show();
        buscar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String p = placaBuscar.getText().toString();
                boolean bandera = false;
                for (int i = 0 ; i < vehiculos.size() ; i++){
                    if (p.equals(vehiculos.get(i).getPlaca())){
                        bandera = true;
                        vplaca = vehiculos.get(i).getPlaca();
                        posicionEditar = i;
                        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
                        fecha_antigua = vehiculos.get(i).getFecFabricacion();
                        String fecha = formato.format(fecha_antigua);
                        placa.setText("Placa N°:  " + vehiculos.get(i).getPlaca());
                        marca.setText(vehiculos.get(i).getMarca());
                        costo.setText(Double.toString(vehiculos.get(i).getCosto()));
                        fecha1.setText(fecha);
                        if (vehiculos.get(i).getColor().equals("Blanco")){
                            color1.setChecked(true);
                        }else if (vehiculos.get(i).getColor().equals("Negro")){
                            color2.setChecked(true);
                        }else if (vehiculos.get(i).getColor().equals("Otro")){
                            color3.setChecked(true);
                        }

                        if (vehiculos.get(i).isMatriculado()){
                            matriculado.setChecked(true);
                        }
                    }
                }
                if (!bandera){
                    Toast toast1 = Toast.makeText(getApplicationContext(), "Placa incorrecta", Toast.LENGTH_SHORT);
                    //toast1.setGravity(Gravity.CENTER, 0, 0);
                    toast1.show();
                }

            }
        });
        edit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
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

                if (bandera){
                    int dia = Integer.parseInt(f_dia);
                    int mes = Integer.parseInt(f_mes) - 1;
                    int año = Integer.parseInt(f_año) - 1900;
                    fecha_Fabricacion = new Date(año,mes,dia);
                }else{
                    fecha_Fabricacion = fecha_antigua;
                }

                if (matriculado.isChecked()){
                    vmatriculado = true;
                }else {
                    vmatriculado = false;
                }

                if (color1.isChecked()){
                    vcolor = "Blanco";
                }else if (color2.isChecked()){
                    vcolor = "Negro";
                }else if (color3.isChecked()){
                    vcolor = "Otro";
                }
                vmarca = marca.getText().toString();
                vcosto = Double.parseDouble(costo.getText().toString());
                if (vmarca != null & vcosto != 0){
                    Vehiculo vehic = new Vehiculo(vplaca,vmarca,fecha_Fabricacion,vcosto,vmatriculado,vcolor);
                    vehiculos.remove(posicionEditar);
                    vehiculos.add(posicionEditar,vehic);
                    escribirList();
                    dialog.cancel();
                }else{
                    Toast toast1 = Toast.makeText(getApplicationContext(), "Faltan datos", Toast.LENGTH_SHORT);
                    //toast1.setGravity(Gravity.CENTER, 0, 0);
                    toast1.show();
                }


            }
        });
    }




    @Override
    public boolean onCreateOptionsMenu (Menu menu){

        getMenuInflater().inflate(R.menu.menu_vehiculo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item){
        int id = item.getItemId();
        if (id == R.id.añadir){
            enviarVehiculos(null);
            return true;
        }

        if (id == R.id.editar){
            //salir(null);
            editarVehiculo(null);
            return true;
        }

        if (id == R.id.eliminar){
            eliminarVehiculo(null);
            //salir(null);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



}
