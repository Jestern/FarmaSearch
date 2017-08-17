package com.example.eila.farmaciasapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;

/**
 * Created by Eila on 10/05/2016.
 */
public class Children {

    public String nombre;
    public String descripcion;
    public float precio;
    public Bitmap imagen;
    public String id;


    public Children(String nombre, String descripcion, String imagen, float precio, String id) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        DownloadImageTask dI = new DownloadImageTask(imagen);
        dI.execute();
        this.id = id;
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        String img;

        public DownloadImageTask(String img) {
            this.img = "http://52.51.20.39" + img;
        }

        protected Bitmap doInBackground(String... urls) {

            Bitmap mIcon11 = null;
            try {

                InputStream in = new java.net.URL(img).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            imagen  = result;
        }
    }
}
