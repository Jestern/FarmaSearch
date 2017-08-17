package com.example.eila.farmaciasapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PedidosActivity extends AppCompatActivity {

    private BuscarPedidos buscPedidos;
    private ArrayList<Factura> pedidos = new ArrayList<>();
    private SparseArray<GroupPedido> groups = new SparseArray<GroupPedido>();
    private Activity act;
    Sesion sesion = Sesion.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedidos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);



        act = this;
        buscarPedidos();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.inicio, menu);
        return true;
    }


    public void buscarPedidos(){
        buscPedidos = new BuscarPedidos(this);
        buscPedidos.execute((Void) null);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(PedidosActivity.this, Inicio.class));
                return true;

        }

        return super.onOptionsItemSelected(item);
    }


    public class BuscarPedidos extends AsyncTask<Void, Void, Boolean> {

        Sesion sesion = Sesion.getInstance();
        private final Context cont;
        private String mTxtDisplay = null;

        public BuscarPedidos(Context cont){
            this.cont = cont;
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            MyStub stub = MyStub.getInstance();

            mTxtDisplay = stub.obtenerFacturas(sesion.getToken(), cont);


            if(mTxtDisplay==null){
                return false;
            }


            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            buscPedidos = null;
            if (success) {

                try {
                    JSONArray jsonArray = new JSONArray(mTxtDisplay);
                    if (!jsonArray.isNull(0)){
                        Gson gson = new Gson();
                        Factura pedido;
                        for(int i=0; i < jsonArray.length(); i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            pedido= gson.fromJson(jsonObject.toString(), Factura.class);
                            pedidos.add(pedido);

                        }

                        createData();
                        ExpandableListView listView = (ExpandableListView) findViewById(R.id.listViewPedidos);
                        MyExpandableListAdapterPedidos adapter = new MyExpandableListAdapterPedidos(act , groups);
                        listView.setAdapter(adapter);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }

        @Override
        protected void onCancelled() {
            buscPedidos = null;

        }

    }

    public void createData() {

        Factura p;
        GroupPedido group;
        Item item;

        for(int i = 0; i < pedidos.size(); i++){
            p = pedidos.get(i);
            group = new GroupPedido("Factura " + p.getNumeroFactura()
                    + "  Total: " + Float.toString(p.getTotal()) + "€ "
                    + "  Fecha:" + p.getFecha());
            ArrayList<Item> lineas = p.getLineas();
            for (int j = 0; j < lineas.size(); j++) {
                item = lineas.get(j);

                    ChildrenPedido c = new ChildrenPedido(item.getNombre(), item.getCantidad(),item.getPrecio());
                    group.children.add(c);

            }
            groups.append(i, group);

        }
    }

}
