package ec.edu.uce.vista;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.example.johan.proyecto_final_1h_g12.R;

import ec.edu.uce.controlador.IOHelper;
import ec.edu.uce.modelo.Usuario;

public class RegistroUsuarioActivity extends AppCompatActivity {

    EditText usuario;
    EditText contraseña;
    TextView texto;
    String jsonString;
    private static ArrayList<Usuario> arrayUsuarios = new ArrayList<>();
    //private static Usuario nuevo = new Usuario ("Johan","jcfreireg");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuario);
        usuario = (EditText) findViewById(R.id.usuario);
        contraseña = (EditText) findViewById(R.id.contras);
        texto = (TextView) findViewById(R.id.textView6);

        //nuevo = new Usuario (usuario.getText().toString(),contraseña.getText().toString());
    }

    public void registrar (View v){

    }

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
        }

    }

    public void enviarUsuarios(View view) {
        Gson gson = new Gson();
        String json = gson.toJson(arrayUsuarios);
        //Crearemos un Intent y ahora pasaremos la cadena json
        Intent intent = new Intent(this, MainActivity.class);
        //Almacenamos la cadena json en el Intent con el nombre que queramos
        intent.putExtra("json", json);
        //Iniciamos la Activity usando el Intent, que ya lleva el nombre
        startActivity(intent);
    }

    public boolean verificarUsuario (Usuario u){
        boolean bandera = true;
        for (int i = 0 ; i<arrayUsuarios.size(); i++){
            if (arrayUsuarios.get(i).getUsuario().equals(u.getUsuario())){
                bandera = false;
            }
        }
        return bandera;
    }

    /*
    public void unserClassGSON(View view) {
        Gson gson = new Gson();
        try {
            FileInputStream is = openFileInput("usuarios.txt");
            String result = IOHelper.stringFromStream(is);
            //City city = gson.fromJson(Reader Instance, City.class);
            Usuario [] usuario1 = gson.fromJson(result, Usuario[].class);
            texto.setText("Usuario : " + usuario1[0].getUsuario() + "\n" +
                            "Contraseña: " + usuario1[0].getContraseña() + "\n" +
                            "Usuario : " + usuario1[1].getUsuario() + "\n" +
                            "Contraseña: " + usuario1[1].getContraseña());
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/
    
}
