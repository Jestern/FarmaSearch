package com.example.eila.farmaciasapp;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SelecionarFarmacia extends AppCompatActivity implements OnMapReadyCallback,
        GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    private Intent intent;
    private Sesion sesion = Sesion.getInstance();
    private Farmacia farmacia_seleccionada;
    private Button botonSeleccionar;
    private TextView nombre;
    private TextView direccion;
    private TextView telefono;
    private TextView email;
    private BuscarProductos buscPro = null;


    private Map<LatLng,String> marcadores = new HashMap<LatLng, String>();
    private SupportMapFragment mapFragment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_selecionar_farmacia);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        intent  = new Intent(SelecionarFarmacia.this,ProductosActivity.class);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);



        botonSeleccionar = (Button) findViewById(R.id.buttonElegirFarmacia);
        botonSeleccionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buscar();
            }
        });

        nombre = (TextView)  findViewById(R.id.nombre_farmacia);
        email = (TextView)  findViewById(R.id.email_farmacia);
        telefono = (TextView)  findViewById(R.id.telefono_farmacia);
        direccion = (TextView)  findViewById(R.id.direccion_farmacia);


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.inicio, menu);
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
            startActivity(new Intent(SelecionarFarmacia.this, Inicio.class));
            return true;
        }
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(SelecionarFarmacia.this, Inicio.class));
                return true;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        ArrayList<Farmacia> farmacias = sesion.getFarmacias();
        LatLng l;
        for(Farmacia f: farmacias){
            l = new LatLng(f.getLatitud(), f.getLongitud());
            marcadores.put(l,f.getIdFarmacia());
            mMap.addMarker(new MarkerOptions().position(l).title(f.getNombre()));
        }

        // Add a marker in Sydney and move the camera
        LatLng centro = new LatLng(sesion.getLatitud(), sesion.getLongitud());
        //LatLng centro = new LatLng(37.19696, -3.62424);
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(centro));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(centro.latitude, centro.longitude), 15.0f));
        googleMap.setOnMarkerClickListener(this);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        //Iniciamos la nueva actividad
        LatLng l = marker.getPosition();
        mMap.moveCamera(CameraUpdateFactory.newLatLng(l));
        String id = marcadores.get(l);
        farmacia_seleccionada = sesion.getFarmacia(id);
        sesion.setFarmaciaSeleccionada(farmacia_seleccionada);

        botonSeleccionar.setVisibility(View.VISIBLE);
        nombre.setText(farmacia_seleccionada.getNombre());
        telefono.setText(farmacia_seleccionada.getTelefono());
        direccion.setText(farmacia_seleccionada.getDireccion());
        email.setText(farmacia_seleccionada.getEmail());


        return true;
    }

    public class BuscarProductos extends AsyncTask<Void, Void, Boolean> {

        private final String id;
        private final Context cont;
        private String mTxtDisplay = null;

        public BuscarProductos(String id, Context cont){
            this.id = id;
            this.cont = cont;
        }

        @Override
        protected Boolean doInBackground(Void... params) {


            MyStub stub = MyStub.getInstance();

            mTxtDisplay = stub.getProductos(id, cont);
            sesion.cleanStock();

            if(mTxtDisplay==null){
                return false;
            }


            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            buscPro = null;
            if (success) {

                try {
                    JSONArray jsonArray = new JSONArray(mTxtDisplay);
                    if (!jsonArray.isNull(0)){
                        Gson gson = new Gson();
                        Stock stock;
                        for(int i=0; i < jsonArray.length(); i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            stock= gson.fromJson(jsonObject.toString(), Stock.class);
                            sesion.setStock(stock);

                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                startActivity(intent);
            }
        }

        @Override
        protected void onCancelled() {
            buscPro = null;

        }

    }

    public void buscar(){
        if (buscPro != null) {
            return;
        }

        buscPro = new BuscarProductos(farmacia_seleccionada.getIdFarmacia(), this);
        buscPro.execute((Void) null);
    }

}
