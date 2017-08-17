package com.example.eila.farmaciasapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;

import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;



public class Inicio extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Intent intent_buscar_farmacias;
    private Intent ver_perfil;
    private Intent cerrar_sesion;
    private Intent info_legal;
    private Intent faq;
    private Intent mis_pedidos;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private Context mContext;
    private GPSTracker traker;
    String latLongString;
    private BuscarFarmacias buscFar = null;
    private double manualLat, manualLong;
    Sesion sesion = Sesion.getInstance();
    private EditText mDirectionView;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        LocationManager mLocationManager = (LocationManager) this
                .getSystemService(LOCATION_SERVICE);
        ;

        ver_perfil = new Intent(Inicio.this, PerfilActvity.class);
        cerrar_sesion = new Intent(Inicio.this, LoginActivity.class);
        info_legal = new Intent(Inicio.this, LegalActivity.class);
        faq = new Intent(Inicio.this, PreguntasActivity.class);
        mis_pedidos = new Intent(Inicio.this, PedidosActivity.class);

        mDirectionView = (EditText) findViewById(R.id.inputDirection);

        intent_buscar_farmacias = new Intent(Inicio.this, SelecionarFarmacia.class);
        Button mBuscarFarmaciasButton = (Button) findViewById(R.id.button_buscar_farmacias);
        mBuscarFarmaciasButton.setOnClickListener(new ClickHander());
        Button mBuscarFarmaciasDireccionButton = (Button) findViewById(R.id.buttonBuscarFarmaciasDireccion);
        mBuscarFarmaciasDireccionButton.setOnClickListener(new ClickHander());


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Assume thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        1);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }

        mContext = this;

    }

    public class BuscarFarmacias extends AsyncTask<Void, Void, Boolean> {

        private final double lat;
        private final double lon;
        private final Context cont;
        private String mTxtDisplay = null;

        public BuscarFarmacias(double lat,double lon, Context cont){
            this.lat = lat;
            this.lon = lon;
            this.cont = cont;
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            MyStub stub = MyStub.getInstance();

            mTxtDisplay = stub.getFarmacias(lat, lon, cont);
            sesion.cleanFarmacias();

            if(mTxtDisplay==null){
                return false;
            }


            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            buscFar = null;
            if (success) {

                try {
                    JSONArray jsonArray = new JSONArray(mTxtDisplay);
                    if (!jsonArray.isNull(0)){
                        Gson gson = new Gson();
                        Farmacia farmacia;
                        for(int i=0; i < jsonArray.length(); i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            farmacia= gson.fromJson(jsonObject.toString(), Farmacia.class);
                            sesion.addFarmacia(farmacia);
                            LatLng l = new LatLng(farmacia.getLatitud(), farmacia.getLongitud());


                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                startActivity(intent_buscar_farmacias);
            }
        }

        @Override
        protected void onCancelled() {
            buscFar = null;

        }

    }


    public void getManualLocation(String address){
        Geocoder coder = new Geocoder(this);
        try {
            ArrayList<Address> adresses = (ArrayList<Address>) coder.getFromLocationName(address, 50);
            for(Address add : adresses){

                manualLong = add.getLongitude();
                manualLat  = add.getLatitude();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void buscar(){
        if (buscFar != null) {
            return;
        }

        buscFar = new BuscarFarmacias(sesion.getLatitud(), sesion.getLongitud(), this);
        buscFar.execute((Void) null);
    }


    public class ClickHander implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            int id = v.getId();
            BuscarFarmacias buscarFarmacias;
            switch (id) {
                case R.id.button_buscar_farmacias:
                    traker = new GPSTracker(mContext);
                    Location location = traker.getCurrentLocation();
                    latLongString = traker.updateWithNewLocation(location);
                    sesion.setLatitud(traker.getLatitude());
                    sesion.setLongitud(traker.getLongitude());
                    //sesion.setLatitud(37.19696);
                    //sesion.setLongitud(-3.62424);
                    buscar();

                    break;
                case R.id.buttonBuscarFarmaciasDireccion:
                    boolean cancel = false;
                    View focusView = null;
                    String direction = mDirectionView.getText().toString();
                    if (TextUtils.isEmpty(direction)) {
                        focusView = mDirectionView;
                        cancel = true;
                    }
                    if (cancel) {
                        focusView.requestFocus();
                    } else {
                       getManualLocation(direction);
                       sesion.setLatitud(manualLat);
                       sesion.setLongitud(manualLong);

                       buscar();
                    }
                    break;
            }



        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.inicio, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /*// Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
*/
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_perfil) {
            startActivity(ver_perfil);
        } else if (id == R.id.nav_pedidos) {
            startActivity(mis_pedidos);
        } else if (id == R.id.nav_frecuentes) {
            startActivity(faq);
        } else if (id == R.id.nav_legal) {
            startActivity(info_legal);
        } else if (id == R.id.nav_salir) {
            sesion.cleanStock();
            sesion.cleanCesta();
            sesion.cleanFarmacias();
            startActivity(cerrar_sesion);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
