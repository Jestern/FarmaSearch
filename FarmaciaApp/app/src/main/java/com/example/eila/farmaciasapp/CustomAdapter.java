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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CustomAdapter extends BaseAdapter{

    private Context context;
    private TextView nombre;
    private TextView cantidad;
    private Button botonAdd;
    private Button botonQuit;
    private ImageButton botonDelete;
    private Intent recargar;
    private Sesion sesion;
    private Map<String, Item> cesta = new HashMap<>();
    private ArrayList<Item> p = new ArrayList<>();
    private static LayoutInflater inflater=null;
    private Activity act;
    public CustomAdapter(Activity act) {
        this.act = act;
        sesion = Sesion.getInstance();
        cesta = sesion.getCesta().getProductos();
        p = new ArrayList<>(cesta.values());
        recargar = new Intent(act , CestaActivity.class);
        context=act.getApplicationContext();
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return cesta.size();
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
        rowView = inflater.inflate(R.layout.item_cesta, null);
        nombre =(TextView) rowView.findViewById(R.id.nombreProductoCesta);
        cantidad=(TextView) rowView.findViewById(R.id.cantidadItemsCesta);
        botonAdd = (Button) rowView.findViewById(R.id.buttonAddItem);
        botonQuit = (Button) rowView.findViewById(R.id.buttonQuitItem);
        botonDelete = (ImageButton) rowView.findViewById(R.id.imageButtonEliminarItem);
        nombre.setText(p.get(position).getNombre());
        cantidad.setText(Integer.toString(p.get(position).getCantidad()));
        botonAdd.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int c = Integer.parseInt(cantidad.getText().toString());
                c++;
                cantidad.setText(Integer.toString(c));
                String id = p.get(position).getIdProducto();
                cesta.get(id).addCantidad();
                sesion.getCesta().addPrize(p.get(position).getPrecio());
                act.startActivity(recargar);
            }
        });
        botonQuit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int c = Integer.parseInt(cantidad.getText().toString());
                if(c>1) {
                    c--;
                    cantidad.setText(Integer.toString(c));
                    String id = p.get(position).getIdProducto();
                    cesta.get(id).subCantidad();
                    sesion.getCesta().subPrize(p.get(position).getPrecio());
                    act.startActivity(recargar);
                }
            }
        });
        botonDelete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int c = Integer.parseInt(cantidad.getText().toString());
                sesion.getCesta().subPrize(p.get(position).getPrecio()*c);
                if(sesion.getCesta().getTotal()<1) {
                    sesion.getCesta().setTotal(0.0f);
                }
                String id = p.get(position).getIdProducto();
                cesta.remove(id);
                act.startActivity(recargar);
            }
        });
        return rowView;
    }

}