package com.example.eila.farmaciasapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class CestaActivity extends AppCompatActivity {

    private ListView lv;
    private TextView total;
    private Sesion sesion = Sesion.getInstance();
    private Intent realizarPedido;
    private Button realizar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cesta);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        realizarPedido = new Intent(CestaActivity.this, PagoActivity.class);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        lv=(ListView) findViewById(R.id.listViewCesta);
        lv.setAdapter(new CustomAdapter(this));

        total = (TextView) findViewById(R.id.totalCesta);
        total.setText("Total: " + Float.toString(sesion.getCesta().getTotal()) + "€" );

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Cesta-" + sesion.getFarmaciaSeleccionada().getNombre());

        realizar = (Button) findViewById(R.id.buttonPedido);
        realizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sesion.getCesta().getTotal() != 0.0f){
                    startActivity(realizarPedido);
                }else{
                    Snackbar.make(view, "Su cesta está vacía. No se puede tramitar un pedido.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });





    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.inicio, menu);
        getMenuInflater().inflate(R.menu.farmacia, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(CestaActivity.this, Inicio.class));
            return true;
        }
        if (id == R.id.action_farmacia) {
            startActivity(new Intent(CestaActivity.this, InformacinFarmaciasActivity.class));
            return true;
        }
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(CestaActivity.this, ProductosActivity.class));
                return true;



        }

        return super.onOptionsItemSelected(item);
    }

}
