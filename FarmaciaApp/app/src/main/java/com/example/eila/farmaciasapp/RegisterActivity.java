package com.example.eila.farmaciasapp;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

public class RegisterActivity extends AppCompatActivity {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;

    // UI references.
    private EditText mEmailView;
    private EditText mRePasswordView;
    private EditText mUserView;
    private EditText mFNameView;
    private EditText mLNameView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    //Creamos el Intent
    private Intent intent;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        // Set up the login form.
        mEmailView = (EditText) findViewById(R.id.email_register);

        intent  = new Intent(RegisterActivity.this, Inicio.class);

        mFNameView = (EditText) findViewById(R.id.nombre);
        mLNameView = (EditText) findViewById(R.id.apellidos);
        mUserView = (EditText) findViewById(R.id.nombreUsuario);
        mPasswordView = (EditText) findViewById(R.id.reg_password);
        mRePasswordView = (EditText) findViewById(R.id.rep_pass);

        Button mRegisterButton = (Button) findViewById(R.id.registro);
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });



        mLoginFormView = findViewById(R.id.register_form);
        mProgressView = findViewById(R.id.register_progress);

    }






    /**
     * Attempts to register the account specified by the register form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mEmailView.setError(null);
        mFNameView.setError(null);
        mLNameView.setError(null);
        mUserView.setError(null);
        mPasswordView.setError(null);
        mRePasswordView.setError(null);

        // Store values at the time of the register attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();
        String password_rep = mRePasswordView.getText().toString();
        String f_name = mFNameView.getText().toString();
        String l_name = mLNameView.getText().toString();
        String user = mUserView.getText().toString();

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

        // Check for a valid mail.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        }

        // Check for a valid password.
        if (TextUtils.isEmpty(password_rep)) {
            mRePasswordView.setError(getString(R.string.error_field_required));
            focusView = mRePasswordView;
            cancel = true;
        }
        if (TextUtils.isEmpty(password)) {
            mPasswordView.setError(getString(R.string.error_field_required));
            focusView = mPasswordView;
            cancel = true;
        } else if(!password.equals(password_rep)){
            mRePasswordView.setError(getString(R.string.error_pass_no_equals));
            focusView = mRePasswordView;
            cancel = true;
        }else if(!isPasswordValid(password)){
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid user.
        if (TextUtils.isEmpty(user)) {
            mUserView.setError(getString(R.string.error_field_required));
            focusView = mUserView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt register and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user register attempt.
            showProgress(true);
            mAuthTask = new UserLoginTask(user, password, email, f_name, l_name, this);
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
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }





    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
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
        private final String mLName;
        private final Context mContext;
        private String mTxtDisplay = null;
        private boolean freeUser;
        private boolean freeEmail;

        UserLoginTask(String user, String password, String email, String f_name, String l_name, Context context) {
            mUser = user;
            mPassword = password;
            mEmail = email;
            mFName = f_name;
            mLName = l_name;
            mContext = context;
            freeEmail = true;
            freeUser = true;
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            /*
             si usuario esta cogido mTxtDisplay = user
             si email esta cogido mTxtDisplay = email
             si usuario e email estan cogidos mTxtDisplay = user,email

             */

            MyStub stub = MyStub.getInstance();

            mTxtDisplay = stub.register(mFName,mLName,mUser, mPassword, mEmail, mContext);
            Log.i("my task", mTxtDisplay);

            if (mTxtDisplay.contains("user") && mTxtDisplay.contains("email")){
                freeEmail = false;
                freeUser = false;
                return false;

            }else if(mTxtDisplay.contains("user")){
                freeUser = false;
                return false;

            }else if(mTxtDisplay.contains("email")){
                freeEmail = false;
                return false;

            }

            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {
                //Iniciamos la nueva actividad
                Sesion sesion = Sesion.getInstance();
                sesion.setToken(mTxtDisplay);
                sesion.setUser(mUser);
                startActivity(intent);

                //finish();
            } else {

                if (!freeUser && !freeEmail){
                    mEmailView.setError(getString(R.string.error_not_free_user_and_mail));
                    mEmailView.requestFocus();
                }else if(!freeUser){
                    mUserView.setError(getString(R.string.error_not_free_user));
                    mUserView.requestFocus();

                }else if(!freeEmail){
                    mEmailView.setError(getString(R.string.error_not_free_mail));
                    mEmailView.requestFocus();

                }

            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
}

