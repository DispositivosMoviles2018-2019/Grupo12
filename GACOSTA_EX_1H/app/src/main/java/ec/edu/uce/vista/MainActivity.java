package ec.edu.uce.vista;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.johan.proyecto_final_1h_g12.R;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

import ec.edu.uce.modelo.Usuario;

public class MainActivity extends AppCompatActivity {

    Button registrar,ingresar;
    EditText contras,usuario;
    private static Usuario[] arrayUsuariosMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ingresar = (Button) findViewById(R.id.ingresar);
        registrar = (Button) findViewById(R.id.registrar);
        usuario = (EditText) findViewById(R.id.usuario);
        contras = (EditText) findViewById(R.id.contras);

        registrar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent ListSong = new Intent(MainActivity.this, RegistroUsuarioActivity.class);
                startActivity(ListSong);

            }
        });

    }

    public void ingresar (View view){
        recuperarUsuarios();

        for (int i = 0 ; i< arrayUsuariosMain.length; i++){
            if ((usuario.getText().toString().equals(arrayUsuariosMain[i].getUsuario()))&
                    (contras.getText().toString().equals(arrayUsuariosMain[i].getContraseña()))){
                Intent intent = new Intent(this, VehiculosActivity.class);
                startActivity(intent);

            }
        }

    }

    private void recuperarUsuarios() {
        //Obtener el Intent que hemos recibido
        Intent intent = getIntent();
        //Recuperamos la cadena json
        String json = intent.getStringExtra("json");
        //Creamos un objeto Gson
        Gson gson = new Gson();
        //Creamos un nuevo Usuario a partir de json
        arrayUsuariosMain = gson.fromJson(json, Usuario[].class);

    }

}
