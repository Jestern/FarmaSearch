package com.example.eila.farmaciasapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.InputStream;

/**
 * Created by Eila on 10/05/2016.
 */
public class ChildrenPedido {

    public String nombre;
    public int cantidad;
    public float precio;



    public ChildrenPedido(String nombre, int cantidad,float precio) {
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.precio = precio*cantidad;

    }

}
