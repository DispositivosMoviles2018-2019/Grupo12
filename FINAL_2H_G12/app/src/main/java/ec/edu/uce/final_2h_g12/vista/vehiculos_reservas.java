package ec.edu.uce.final_2h_g12.vista;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import ec.edu.uce.final_2h_g12.R;

public class vehiculos_reservas extends AppCompatActivity {
    private Button vehiculos;
    private Button reservas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehiculos_reservas);
        vehiculos = (Button) findViewById(R.id.butvehic);
        reservas = (Button) findViewById(R.id.butreserv);
        vehiculos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               ActivityVehic();
            }
        });
        reservas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityReser();
            }
        });
    }
    public void ActivityVehic(){
        Intent intent = new Intent(this, VehiculosActivity.class);
        startActivity(intent);
    }
    public void ActivityReser(){
        Intent intent = new Intent(this, ReservasActivity.class);
        startActivity(intent);
    }
}
