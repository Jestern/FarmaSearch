package com.example.eila.farmaciasapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.Menu;
import android.widget.ExpandableListView;
import android.widget.ImageView;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class ProductosActivity extends AppCompatActivity {

    private SparseArray<Group> groups = new SparseArray<Group>();
    private Sesion sesion = Sesion.getInstance();
    private Intent intentIrACesta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        intentIrACesta = new Intent(ProductosActivity.this, CestaActivity.class);
        setSupportActionBar(toolbar);



        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Productos-" + sesion.getFarmaciaSeleccionada().getNombre());

        createData();
        ExpandableListView listView = (ExpandableListView) findViewById(R.id.listView);
        MyExpandableListAdapter adapter = new MyExpandableListAdapter(this,
                groups);
        listView.setAdapter(adapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intentIrACesta);
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
            startActivity(new Intent(ProductosActivity.this, Inicio.class));
            return true;
        }
        if (id == R.id.action_farmacia) {
            startActivity(new Intent(ProductosActivity.this, InformacinFarmaciasActivity.class));
            return true;
        }
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(ProductosActivity.this, SelecionarFarmacia.class));
                return true;

        }

        return super.onOptionsItemSelected(item);
    }

    public void createData() {

        ArrayList<Stock> stock = sesion.getStock();
        Producto p;
        Group group = new Group("TODOS LOS PRODUCTOS");
        for (int i = 0; i < stock.size(); i++) {
            p = stock.get(i).getProducto();
            Children c = new Children(p.getNombre(), p.getDescripcion(), p.getImg(), p.getPrecio(), p.getId());
            group.children.add(c);
        }
        groups.append(0, group);

        int j = 1;
        for(Departamento d : Departamento.values()){
            group = new Group(d.toString());
            for (int i = 0; i < stock.size(); i++) {
                p = stock.get(i).getProducto();
                if (p.getDepartamento().equals(d)) {
                    Children c = new Children(p.getNombre(), p.getDescripcion(), p.getImg(), p.getPrecio(), p.getId());
                    group.children.add(c);
                }
            }
            groups.append(j, group);
        ++j;
        }
    }






}
