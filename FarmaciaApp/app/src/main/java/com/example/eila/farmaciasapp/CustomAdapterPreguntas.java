package com.example.eila.farmaciasapp;

/**
 * Created by Eila on 10/05/2016.
 */
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CustomAdapterPreguntas extends BaseAdapter{

    private Context context;
    private TextView pregunta;
    private TextView respuesta;
    private ArrayList<String> preguntas;
    private ArrayList<String> respuestas;
    private Intent recargar;

    private static LayoutInflater inflater=null;
    private Activity act;
    public CustomAdapterPreguntas(Activity act, ArrayList<String> preguntas, ArrayList<String> respuestas) {
        this.act = act;
        this.preguntas = preguntas;
        this.respuestas = respuestas;
        recargar = new Intent(act , PreguntasActivity.class);

        context=act.getApplicationContext();
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return preguntas.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }


    @Override
    public View getView(final int position,View convertView, ViewGroup parent) {


        View rowView;
        rowView = inflater.inflate(R.layout.item_preguntas, null);
        pregunta =(TextView) rowView.findViewById(R.id.pregunta);
        respuesta =(TextView) rowView.findViewById(R.id.respuesta);

        pregunta.setText(preguntas.get(position));
        respuesta.setText(respuestas.get(position));


        return rowView;
    }

}