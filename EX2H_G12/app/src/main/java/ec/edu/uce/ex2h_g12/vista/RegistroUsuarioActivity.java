package ec.edu.uce.ex2h_g12.vista;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import ec.edu.uce.ex2h_g12.R;
import ec.edu.uce.ex2h_g12.controlador.IOHelper;
import ec.edu.uce.ex2h_g12.modelo.Usuario;

public class RegistroUsuarioActivity extends AppCompatActivity {

    EditText usuario;
    EditText contraseña;
    TextView texto;
    String jsonString;
    private static ArrayList<Usuario> arrayUsuariosO = new ArrayList<>();
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
        //Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+).{5,}$");
        Pattern pattern = Pattern.compile("^(?=.*\\d)(?=.*[\\u0021-\\u002b\\u003c-\\u0040])(?=.*[A-Z])(?=.*[a-z])\\S{5,}$");

        Matcher mather = pattern.matcher(c);

        if (mather.find() == false) {
            Toast toast1 = Toast.makeText(getApplicationContext(), "error contraseña no valida", Toast.LENGTH_SHORT);

            toast1.show();




       }else {
           Usuario nuevo = new Usuario(u, c);
           if (verificarUsuario(nuevo)) {
               arrayUsuarios.add(nuevo);
               jsonString = gson.toJson(arrayUsuarios);
               IOHelper.writeToFile(this, "usuarios.txt", jsonString);
               Toast toast1 = Toast.makeText(getApplicationContext(), "Registro exitoso", Toast.LENGTH_SHORT);
               //toast1.setGravity(Gravity.CENTER, 0, 0);
               toast1.show();
               usuario.setText(null);
               contraseña.setText(null);
           } else {
               Toast toast1 = Toast.makeText(getApplicationContext(), "Usuario ya Registrado", Toast.LENGTH_SHORT);
               //toast1.setGravity(Gravity.CENTER, 0, 0);
               toast1.show();
               usuario.setText(null);
               contraseña.setText(null);
           }
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
    private void recuperarUsuarios() {
        //Obtener el Intent que hemos recibido
        Intent intent = getIntent();
        //Recuperamos la cadena json
        String json = intent.getStringExtra("json");
        //Creamos un objeto Gson
        Gson gson = new Gson();
        //Creamos un nuevo Usuario a partir de json
        Type usuarioType = new TypeToken<ArrayList<Usuario>>(){}.getType();
        arrayUsuariosO = gson.fromJson(json, usuarioType);

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
