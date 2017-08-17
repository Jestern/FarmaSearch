package com.example.eila.farmaciasapp;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;


public class PerfilActvity extends AppCompatActivity {

    private UserLoginTask mAuthTask = null;
    private GetUser mDataTask = null;

    // UI references.
    private TextView mEmailView;
    private EditText mPasswordView;
    private TextView mUserView;
    private EditText mFNameView;
    private EditText mLNameView;
    private EditText mFechaView;
    private EditText mTelefonoView;
    private EditText mDireccionView;
    private Button modificar;
    private Sesion sesion = Sesion.getInstance();
    private String email;
    private String usuario;


    //Creamos el Intent
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_actvity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mEmailView = (TextView) findViewById(R.id.perfilMail);
        mUserView = (TextView) findViewById(R.id.perfilUser);
        mPasswordView = (EditText) findViewById(R.id.perfilPass);
        mFNameView = (EditText) findViewById(R.id.perfilNombre);
        mLNameView = (EditText) findViewById(R.id.perfilApellidos);
        mFechaView = (EditText) findViewById(R.id.perfilFecha);
        mTelefonoView = (EditText) findViewById(R.id.perfilTelefono);
        mDireccionView = (EditText) findViewById(R.id.perfilDireccion);
        modificar = (Button) findViewById(R.id.buttonModificarDatos);
        intent = new Intent(PerfilActvity.this, PerfilActvity.class);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        modificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptModificar();
            }
        });

        getDatos();

        /*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        */
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(PerfilActvity.this, Inicio.class));
                return true;

        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Attempts to register the account specified by the register form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void getDatos(){
        if (mDataTask != null) {
            return;
        }
        mDataTask = new GetUser(sesion.getToken(), this);
        mDataTask.execute((Void) null);

    }
    private void attemptModificar() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mEmailView.setError(null);
        mFNameView.setError(null);
        mLNameView.setError(null);
        mUserView.setError(null);
        mPasswordView.setError(null);


        // Store values at the time of the register attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();
        String f_name = mFNameView.getText().toString();
        String l_name = mLNameView.getText().toString();
        String user = mUserView.getText().toString();
        String direccion = mDireccionView.getText().toString();
        String telefono = mTelefonoView.getText().toString();
        String fecha = mFechaView.getText().toString();


        boolean cancel = false;
        View focusView = null;

        // Check for a valid lname.
        if (TextUtils.isEmpty(l_name)) {
            mLNameView.setError(getString(R.string.error_field_required));
            focusView = mLNameView;
            cancel = true;
        }

        // Check for a valid fname.
        if (TextUtils.isEmpty(f_name)) {
            mFNameView.setError(getString(R.string.error_field_required));
            focusView = mFNameView;
            cancel = true;
        }


        // Check for a valid password.
       if(!isPasswordValid(password) && !TextUtils.isEmpty(password)){
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }



        if (cancel) {
            // There was an error; don't attempt register and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user register attempt.

            mAuthTask = new UserLoginTask(direccion,telefono,fecha, password, f_name, l_name, this);
            mAuthTask.execute((Void) null);
        }

    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        //return email.contains("@");
        return true;
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }





    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mUser;
        private final String mPassword;
        private final String mEmail;
        private final String mFName;
        private final String mFecha;
        private final String mDireccion;
        private final String mLName;
        private final String mTelf;
        private final Context mContext;
        private String mTxtDisplay = null;
        private boolean freeUser;
        private boolean freeEmail;

        UserLoginTask(String direccion,String telefono,String fecha, String password, String f_name, String l_name, Context context) {
            mUser = usuario;
            mPassword = password;
            mEmail = email;
            mFName = f_name;
            mLName = l_name;
            mContext = context;
            mTelf = telefono;
            mDireccion = direccion;
            mFecha = fecha;

        }

        @Override
        protected Boolean doInBackground(Void... params) {


            MyStub stub = MyStub.getInstance();


            mTxtDisplay = stub.modificarPerfil(sesion.getToken(),mFName,mLName, mEmail,
                    mPassword,mDireccion,mTelf,mFecha, mContext);

            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;


            if (success) {

                getDatos();
                startActivity(intent);

            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;

        }
    }

    public class GetUser extends AsyncTask<Void, Void, Boolean> {

        private final String mToken;
        private final Context mContext;
        private String mTxtDisplay = null;

        GetUser(String token, Context context) {
            mToken = token;
            mContext = context;

        }

        @Override
        protected Boolean doInBackground(Void... params) {


            MyStub stub = MyStub.getInstance();
            mTxtDisplay = stub.obtenerPerfil(mToken, mContext);
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mDataTask = null;

            if (success) {

                try {
                    JSONObject jsonObj = new JSONObject(mTxtDisplay);
                    mEmailView.setText("Email:   " + jsonObj.get("email").toString());
                    mUserView.setText("Usuario: " + jsonObj.get("username").toString());
                    email = jsonObj.get("email").toString();
                    usuario = jsonObj.get("username").toString();
                    mFNameView.setText(jsonObj.get("nombre").toString());
                    mLNameView.setText(jsonObj.get("apellidos").toString());

                    mFechaView.setText(jsonObj.get("fNacimiento").toString());
                    mTelefonoView.setText(jsonObj.get("telefono").toString());
                    mDireccionView.setText(jsonObj.get("direccion").toString());


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }

        @Override
        protected void onCancelled() {
            mDataTask = null;

        }
    }

}
