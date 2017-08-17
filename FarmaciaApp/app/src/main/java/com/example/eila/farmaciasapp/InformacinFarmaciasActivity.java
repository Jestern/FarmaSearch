package com.example.eila.farmaciasapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.Map;

public class InformacinFarmaciasActivity extends AppCompatActivity {

    Sesion sesion = Sesion.getInstance();
    private TextView nombre;
    private TextView telefono;
    private TextView direccion;
    private TextView email;
    private TextView horario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacin_farmacias);


        nombre = (TextView) findViewById(R.id.Fnombre);
        telefono = (TextView) findViewById(R.id.Ftlf);
        direccion = (TextView) findViewById(R.id.Fdir);
        email = (TextView) findViewById(R.id.Femail);
        horario = (TextView) findViewById(R.id.Fhor);

        Farmacia farmacia = sesion.getFarmaciaSeleccionada();
        nombre.setText(farmacia.getNombre());
        telefono.setText("Teléfono: " + farmacia.getTelefono());
        direccion.setText("Dirección: " + farmacia.getDireccion());
        email.setText("Email: "+ farmacia.getEmail());

        Map<String, Horario> horarios = farmacia.getHorarios();

        String hor = horarios.get("Lunes") + "\n" + horarios.get("Martes") + "\n"
                + horarios.get("Miercoles") + "\n" + horarios.get("Jueves") + "\n"
                + horarios.get("Viernes");
        horario.setText(hor);

    }


}
