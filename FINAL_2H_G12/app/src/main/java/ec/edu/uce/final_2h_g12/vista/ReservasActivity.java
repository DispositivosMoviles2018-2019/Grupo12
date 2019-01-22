package ec.edu.uce.final_2h_g12.vista;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import ec.edu.uce.final_2h_g12.R;

public class ReservasActivity extends AppCompatActivity {
    private Button Crear;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservas);
        Crear = (Button) findViewById(R.id.crearRes);
        Crear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCrear();
            }
        });
    }

    public void ActivityCrear()
    {
        Intent intent = new Intent(this, CrearReserva.class);
        startActivity(intent);
    }
}
