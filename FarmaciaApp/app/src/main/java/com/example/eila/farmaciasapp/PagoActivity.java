package com.example.eila.farmaciasapp;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import com.google.gson.Gson;

public class PagoActivity extends AppCompatActivity {

    private Button finalizar;
    private RadioButton tarjeta;
    private RadioButton transferencia;
    private RadioButton cuenta;
    private Sesion sesion = Sesion.getInstance();
    private MyStub stub = MyStub.getInstance();
    private Intent intent;
    private RealizarPedido peticion = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pago);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        intent = new Intent(PagoActivity.this, SelecionarFarmacia.class);

        tarjeta = (RadioButton) findViewById(R.id.radioButton);
        transferencia = (RadioButton) findViewById(R.id.radioButton2);
        cuenta = (RadioButton) findViewById(R.id.radioButton3);
        final Context context = this;

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Pago-" + sesion.getFarmaciaSeleccionada().getNombre());

        finalizar = (Button) findViewById(R.id.buttonFinalizar);
        finalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String formaPago;
                if(tarjeta.isSelected()){
                    formaPago = "tarjeta";
                }else if(transferencia.isSelected()){
                    formaPago = "transferencia";
                }else{
                    formaPago = "cuenta";
                }

                Gson gson = new Gson();
                String json = gson.toJson(sesion.getCesta());

                realizarPedido(formaPago, json, sesion.getToken());



            }
        });

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
            startActivity(new Intent(PagoActivity.this, Inicio.class));
            return true;
        }
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(PagoActivity.this, CestaActivity.class));
                return true;

        }

        return super.onOptionsItemSelected(item);
    }

    public void realizarPedido(String formaPago, String cesta, String token){
        peticion = new RealizarPedido(formaPago, cesta, token, this);
        peticion.execute((Void) null);
    }

    public class RealizarPedido extends AsyncTask<Void, Void, Boolean> {

        private final String formaPago;
        private final String cesta;
        private final String token;
        private final Context mContext;
        private String mTxtDisplay = null;

        RealizarPedido(String formaPago, String cesta, String token, Context context) {
            this.formaPago = formaPago;
            this.cesta = cesta;
            this.token = token;
            mContext = context;
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            MyStub stub = MyStub.getInstance();

            mTxtDisplay = stub.realizarPedido(token, cesta, formaPago, mContext);

            if (mTxtDisplay.contains("token")){
                return false;
            }

            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            peticion = null;


            if (success) {
                sesion.cleanCesta();

                startActivity(intent);

                //finish();
            }
        }

        @Override
        protected void onCancelled() {
            peticion = null;

        }
    }

}
