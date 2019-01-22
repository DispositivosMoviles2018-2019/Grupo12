package ec.edu.uce.final_2h_g12.vista;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import ec.edu.uce.final_2h_g12.R;

public class Ajustes extends AppCompatActivity {

    private Button asc;
    private Button desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajustes);
        asc = (Button) findViewById(R.id.asc);
        desc = (Button) findViewById(R.id.desc);
        asc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityAsc();
            }
        });
        desc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityDesc();
            }
        });
    }
    public void ActivityAsc(){
        Intent intent = new Intent(this, VehiculosRegistroActivity.class);
        startActivity(intent);
    }
    public void ActivityDesc(){
        Intent intent = new Intent(this, VehiculosRegistroActivity.class);
        startActivity(intent);
    }
}
