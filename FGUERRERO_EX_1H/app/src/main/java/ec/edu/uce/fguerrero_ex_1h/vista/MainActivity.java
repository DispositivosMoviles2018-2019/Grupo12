package ec.edu.uce.fguerrero_ex_1h.vista;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ec.edu.uce.fguerrero_ex_1h.R;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import ec.edu.uce.fguerrero_ex_1h.modelo.Usuario;

public class MainActivity extends AppCompatActivity {

    Button registrar,ingresar;
    EditText contras,usuario;
    private static ArrayList<Usuario> arrayUsuariosMain = new ArrayList<>();

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
       /* try{
            recuperarUsuarios();
            for (int i = 0 ; i< arrayUsuariosMain.size(); i++) {
                if ((usuario.getText().toString().equals(arrayUsuariosMain.get(i).getUsuario())) &
                        (contras.getText().toString().equals(arrayUsuariosMain.get(i).getContraseña()))) {*/
                    Intent intent = new Intent(this, VehiculosActivity.class);
                    startActivity(intent);
                    usuario.setText(null);
                    contras.setText(null);
               /* }else{
                    Toast toast = Toast.makeText(getApplicationContext(), "Usuario incorrecto", Toast.LENGTH_SHORT);
                    //toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            }
        }catch(NullPointerException e){
            Toast toast1 = Toast.makeText(getApplicationContext(), "Registrese para poder ingresar", Toast.LENGTH_SHORT);
            //toast1.setGravity(Gravity.CENTER, 0, 0);
            toast1.show();
        }

*/
    }

    private void recuperarUsuarios() {
        //Obtener el Intent que hemos recibido
        Intent intent = getIntent();
        //Recuperamos la cadena json
        String json = intent.getStringExtra("json");
        Toast toas = Toast.makeText(getApplicationContext(), "json", Toast.LENGTH_SHORT);
        //toast1.setGravity(Gravity.CENTER, 0, 0);
        toas.show();
        //Creamos un objeto Gson
        Gson gson = new Gson();
        //Creamos un nuevo Usuario a partir de json
        Type usuarioType = new TypeToken<ArrayList<Usuario>>(){}.getType();
        arrayUsuariosMain = gson.fromJson(json, usuarioType);

    }

}
