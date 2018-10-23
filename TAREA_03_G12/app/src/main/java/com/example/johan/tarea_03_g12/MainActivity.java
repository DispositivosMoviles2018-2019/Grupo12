package com.example.johan.tarea_03_g12;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.content.Intent;
import android.widget.EditText;
import android.widget.Toast;
import android.view.Gravity;

public class MainActivity extends AppCompatActivity {

    Button entry;
    EditText contra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        entry = (Button) findViewById(R.id.ingresar);
        entry.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                boolean band = validarContra();
                if (band) {
                    Intent ListSong = new Intent(MainActivity.this, FotosActivity.class);
                    startActivity(ListSong);
                }
            }
        });


    }

    public boolean validarContra (){
        contra = (EditText) findViewById(R.id.contra);
        String texto = contra.getText().toString();
        boolean bandera = false;
        if (texto.equals("JohanFreire")) {
            bandera = true;
        }else if (texto.equals("GaryAcosta")){
            bandera = true;
        }else if (texto.equals("FredyGuerrero")){
            bandera = true;
        }else if (texto.equals("CrisPazmino")){
            bandera = true;
        }else{
            Toast toast1 = Toast.makeText(getApplicationContext(), "Usuario Incorrecto", Toast.LENGTH_SHORT);
            toast1.setGravity(Gravity.CENTER, 0,0 );

            toast1.show();
        }
        return bandera;

    }


}
