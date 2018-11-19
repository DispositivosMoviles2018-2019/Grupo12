package ec.edu.uce.vista;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.johan.proyecto_final_1h_g12.R;

import java.util.ArrayList;
import java.util.Date;

import ec.edu.uce.modelo.Vehiculo;

public class VehiculosActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<Vehiculo> vehiculos = new ArrayList<>();
    ArrayList <String> mejorado = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehiculos);
        listView = (ListView) findViewById(R.id.listv);
        Vehiculo vehiculo1 = new Vehiculo("XTR-9784","Audi", new Date(2015-11-13),79990.0,true,"Negro");
        Vehiculo vehiculo2 = new Vehiculo("CCD-0789","Honda", new Date(1998-3-5),15340.0,false,"Blanco");
        vehiculos.add(vehiculo1);
        vehiculos.add(vehiculo2);
        mejorado = arreglarArray();
        ArrayAdapter adaptador = new ArrayAdapter(this,android.R.layout.simple_list_item_1,mejorado);
        listView.setAdapter(adaptador);
    }

    public ArrayList <String> arreglarArray(){
        ArrayList <String> nuevo = new ArrayList<>();
        for (int i = 0 ; i<vehiculos.size() ; i++){
            nuevo.add(vehiculos.get(i).tString());
        }
        return nuevo;
    }

    public void ingresarDato (View view){
        Intent intent = new Intent(this, VehiculosRegistroActivity.class);
        startActivity(intent);
    }

    public boolean onCreateOptionsMenu (Menu menu){

        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }


    public boolean onOptionsItemSelected (MenuItem opcion_menu){

        int id = opcion_menu.getItemId();
        if (id == R.id.ingresarDato){
            Intent intent = new Intent(this, VehiculosRegistroActivity.class);
            startActivity(intent);
            return true;
        }

        if (id == R.id.salir_menu){
            //salir(null);
            return true;
        }

        return super.onOptionsItemSelected(opcion_menu);
    }

}
