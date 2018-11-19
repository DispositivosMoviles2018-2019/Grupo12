package ec.edu.uce.vista;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.example.johan.proyecto_final_1h_g12.R;

import java.util.Date;

public class VehiculosRegistroActivity extends AppCompatActivity {

    Spinner dias, mes, año;
    Switch matriculado;
    RadioButton color1, color2, color3;
    EditText placa, marca , costo;
    static int f_dia,f_mes,f_año;
    Date fecha_Fabricacion;
    boolean vmatriculado;
    private static String vplaca, vmarca , vcolor1, vcolor2, vcolor3;
    private static double vcosto;

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
        color1 = (RadioButton) findViewById(R.id.color1);
        color2 = (RadioButton) findViewById(R.id.color2);
        color3 = (RadioButton) findViewById(R.id.color3);
        ArrayAdapter<CharSequence> adapter_dias = ArrayAdapter.createFromResource(this,
                R.array.dias, android.R.layout.simple_spinner_item);
        dias.setAdapter(adapter_dias);

        ArrayAdapter<CharSequence> adapter_meses = ArrayAdapter.createFromResource(this,
                R.array.meses, android.R.layout.simple_spinner_item);
        mes.setAdapter(adapter_meses);

        ArrayAdapter<CharSequence> adapter_años = ArrayAdapter.createFromResource(this,
                R.array.años, android.R.layout.simple_spinner_item);
        año.setAdapter(adapter_años);
    }

    public void validarDatos (View view){
        boolean verdadMatricula = validarMatricula();
        boolean verdadTextos = validarTextos();
        boolean verdadPlaca = validarPlaca();
        boolean verdadColor = validarColor();
        boolean verdadFecha = validarFecha();

        if (!verdadMatricula){
            Toast toast1 = Toast.makeText(getApplicationContext(), "Falta Matriculación", Toast.LENGTH_SHORT);
            toast1.setGravity(Gravity.CENTER, 0, 0);
            toast1.show();
        }else if (!verdadTextos){
            Toast toast1 = Toast.makeText(getApplicationContext(), "Falta ingresos", Toast.LENGTH_SHORT);
            toast1.setGravity(Gravity.CENTER, 0, 0);
            toast1.show();
        }else if (!verdadPlaca){
            Toast toast1 = Toast.makeText(getApplicationContext(), "Formato de placa erroneo", Toast.LENGTH_SHORT);
            toast1.setGravity(Gravity.CENTER, 0, 0);
            toast1.show();
        }else if (!verdadColor){
            Toast toast1 = Toast.makeText(getApplicationContext(), "Falta registrar color", Toast.LENGTH_SHORT);
            toast1.setGravity(Gravity.CENTER, 0, 0);
            toast1.show();
        }else if (!verdadFecha){
            Toast toast1 = Toast.makeText(getApplicationContext(), "Falta fecha", Toast.LENGTH_SHORT);
            toast1.setGravity(Gravity.CENTER, 0, 0);
            toast1.show();
        }else {
            Toast toast1 = Toast.makeText(getApplicationContext(), "Registro exitoso", Toast.LENGTH_SHORT);
            toast1.setGravity(Gravity.CENTER, 0, 0);
            toast1.show();
            placa.setText(null);
            marca.setText(null);
            costo.setText(null);
            matriculado.setChecked(false);
            color1.setChecked(false);
            color2.setChecked(false);
            color3.setChecked(false);
        }
    }




    private boolean validarMatricula(){
        boolean matri;
        if (matriculado.isChecked()== true){
            matri = true;
            vmatriculado = true;
        }else {
            matri = false;
        }
        return matri;
    }

    private boolean validarTextos (){
        boolean bandera = true;
        vmarca = marca.getText().toString();
        vcosto = Double.parseDouble(costo.getText().toString());
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
        vplaca = placa.getText().toString();
        char[] caracteres = vplaca.toCharArray();
        if (caracteres.length == 7) {
            int j = 0;
            while (j < caracteres.length) {
                if (j < 3) {
                    if (Character.isDigit(caracteres[j])) {
                        bandera = true;
                    }
                }
                if (j > 2) {
                    if (!Character.isDigit(caracteres[j])) {
                        bandera = true;
                    }
                }
                j = j + 1;
            }
        }
        return bandera;
    }

    private boolean validarColor (){
        boolean bandera = true;
        if (color1.isChecked()==true){
            vcolor1 = "Blanco";
        }else if (color2.isChecked()== true){
            vcolor2 = "Negro";
        }else if (color3.isChecked()== true){
            vcolor3 = "Otro";
        }else{
            bandera = false;
        }
        return bandera;
    }

    private boolean validarFecha (){

        boolean bandera = true;
        dias.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                f_dia = Integer.parseInt(parent.getItemAtPosition(position).toString());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                f_mes = Integer.parseInt(parent.getItemAtPosition(position).toString());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        año.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                f_año = Integer.parseInt(parent.getItemAtPosition(position).toString());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        if (f_dia == 0){
            bandera = false;
        }else if (f_mes == 0){
            bandera = false;
        }else if (f_año == 0){
            bandera = false;
        }else {
            fecha_Fabricacion = new Date(f_dia, f_mes, f_año);
        }
        return bandera;

    }

}
