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
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PreguntasActivity extends AppCompatActivity {

    private BuscarPreguntas buscP;
    private ListView lv;
    private Activity act;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preguntas);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);



        act = this;
        buscarPreguntas();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(PreguntasActivity.this, Inicio.class));
                return true;

        }

        return super.onOptionsItemSelected(item);
    }

    public void buscarPreguntas(){
        buscP = new BuscarPreguntas(this);
        buscP.execute((Void) null);
    }

    public class BuscarPreguntas extends AsyncTask<Void, Void, Boolean> {


        private final Context cont;
        private String mTxtDisplay = null;
        ArrayList<String> preguntas;
        ArrayList<String> respuestas;
        Sesion sesion;

        public BuscarPreguntas(Context cont){
            sesion = Sesion.getInstance();
            preguntas = new ArrayList<>();
            respuestas = new ArrayList<>();
            this.cont = cont;
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            MyStub stub = MyStub.getInstance();

            mTxtDisplay = stub.getFAQ(cont);
            sesion.cleanFarmacias();

            if(mTxtDisplay==null){
                return false;
            }
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            buscP = null;
            if (success) {

                try {
                    JSONArray jsonArray = new JSONArray(mTxtDisplay);
                    if (!jsonArray.isNull(0)){

                        for(int i=0; i < jsonArray.length(); i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            preguntas.add(jsonObject.get("pregunta").toString());
                            respuestas.add(jsonObject.get("respuesta").toString());
                        }
                    }

                    lv=(ListView) findViewById(R.id.listViewPreguntas);
                    lv.setAdapter(new CustomAdapterPreguntas(act, preguntas, respuestas));
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }

        @Override
        protected void onCancelled() {
            buscP = null;

        }

    }



}
