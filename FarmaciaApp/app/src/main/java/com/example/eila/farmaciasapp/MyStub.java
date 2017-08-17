package com.example.eila.farmaciasapp;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Created by Eila on 07/05/2016.
 */
public class MyStub {
    private final static String url_login = "http://52.51.20.39:8080/ServidorFarmacia/ServletFarmacia";

    private static MyStub myStub = new MyStub();

    private MyStub(){}

    public static MyStub getInstance(){
        return myStub;
    }

    public String login(final String mUser,final String mPassword,Context mContext){

        String mTxtDisplay = null;
        RequestFuture<String> future = RequestFuture.newFuture();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_login,
                future,
                future){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("action","login");
                params.put("user",mUser);
                params.put("pass",mPassword);
                return params;
            }


        };

        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        requestQueue.add(stringRequest);

        try {
            mTxtDisplay = future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return mTxtDisplay;
    }

    public String register(final String mFName, final String mLName, final String mUser,final String mPassword, final String mEmail, Context mContext){

        String mTxtDisplay = null;
        RequestFuture<String> future = RequestFuture.newFuture();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_login,
                future,
                future){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("action","registro");
                params.put("name",mFName);
                params.put("last",mLName);
                params.put("user",mUser);
                params.put("pass",mPassword);
                params.put("email",mEmail);
                return params;
            }


        };

        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        requestQueue.add(stringRequest);

        try {
            mTxtDisplay = future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return mTxtDisplay;
    }

    public String getFarmacias(final double latitud, final double longitud, Context mContext){


        String mTxtDisplay = null;
        RequestFuture<String> future = RequestFuture.newFuture();

        Request stringRequest = new StringRequest(Request.Method.GET, url_login +
                "?action=getFarmaciasCercanas&latitud=" + latitud +"&longitud=" + longitud,
                future,
                future);


        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        requestQueue.add(stringRequest);

        try {

            mTxtDisplay = future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return mTxtDisplay;
    }

    public String getProductos(final String id, Context mContext){


        String mTxtDisplay = null;
        RequestFuture<String> future = RequestFuture.newFuture();

        Request stringRequest = new StringRequest(Request.Method.GET, url_login +
                "?action=getProductos&idFarmacia=" + id ,
                future,
                future);


        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        requestQueue.add(stringRequest);

        try {

            mTxtDisplay = future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return mTxtDisplay;
    }

    public String realizarPedido(final String token, final String cesta,final String formaPago,Context mContext){

        String mTxtDisplay = null;
        RequestFuture<String> future = RequestFuture.newFuture();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_login,
                future,
                future){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("action","tramitarPedido");
                params.put("tipo",formaPago);
                params.put("token",token);
                params.put("cesta",cesta);
                return params;
            }


        };

        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        requestQueue.add(stringRequest);

        try {
            mTxtDisplay = future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return mTxtDisplay;
    }

    public String obtenerPerfil(final String token,Context mContext){

        String mTxtDisplay = null;
        RequestFuture<String> future = RequestFuture.newFuture();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_login,
                future,
                future){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("action","getDatosUsuario");
                params.put("token",token);
                return params;
            }


        };

        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        requestQueue.add(stringRequest);

        try {
            mTxtDisplay = future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return mTxtDisplay;
    }

    public String modificarPerfil(final String token, final String name, final String last,
                                  final String email, final String pass, final String address,
                                  final String phone, final String date, Context mContext){

        String mTxtDisplay = null;
        RequestFuture<String> future = RequestFuture.newFuture();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_login,
                future,
                future){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("action","modificarUsuario");
                params.put("token", token);
                params.put("name", name);
                params.put("last", last);
                params.put("email", email);
                params.put("pass", pass);
                params.put("address", address);
                params.put("phone", phone);
                params.put("date", date.toString());
                return params;
            }


        };

        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        requestQueue.add(stringRequest);

        try {
            mTxtDisplay = future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return mTxtDisplay;
    }
    public String getFAQ(Context mContext){


        String mTxtDisplay = null;
        RequestFuture<String> future = RequestFuture.newFuture();

        Request stringRequest = new StringRequest(Request.Method.GET, url_login +
                "?action=getFAQ" ,
                future,
                future);


        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        requestQueue.add(stringRequest);

        try {

            mTxtDisplay = future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return mTxtDisplay;
    }

    public String obtenerFacturas(final String token,Context mContext){

        String mTxtDisplay = null;
        RequestFuture<String> future = RequestFuture.newFuture();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_login,
                future,
                future){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("action","facturasUsuario");
                params.put("token",token);
                return params;
            }


        };

        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        requestQueue.add(stringRequest);

        try {
            mTxtDisplay = future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return mTxtDisplay;
    }




}
