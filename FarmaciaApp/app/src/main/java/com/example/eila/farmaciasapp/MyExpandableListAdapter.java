package com.example.eila.farmaciasapp;

/**
 * Created by Eila on 10/05/2016.
 */
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class MyExpandableListAdapter extends BaseExpandableListAdapter {

    Sesion sesion = Sesion.getInstance();
    Cesta cesta = sesion.getCesta();

    private final static String url = "http://52.51.20.39";
    private final SparseArray<Group> groups;
    public LayoutInflater inflater;
    public Activity activity;

    public MyExpandableListAdapter(Activity act, SparseArray<Group> groups) {
        activity = act;
        this.groups = groups;
        inflater = act.getLayoutInflater();
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return groups.get(groupPosition).children.get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final Children children = (Children) getChild(groupPosition, childPosition);
        TextView text = null;
        ImageView image = null;
        Button boton = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.listrow_details, null);
        }
        text = (TextView) convertView.findViewById(R.id.textViewProductoNombre);
        text.setText(children.nombre);
        text = (TextView) convertView.findViewById(R.id.textViewProductoDescripcion);
        text.setText(children.descripcion);
        text = (TextView) convertView.findViewById(R.id.textViewProductoPrecio);
        text.setText("Precio: " + Float.toString(children.precio) + "€");
        image = (ImageView) convertView.findViewById(R.id.imageViewProducto);
        image.setImageBitmap(children.imagen);
        boton = (Button) convertView.findViewById(R.id.buttonAddToCesta);
        boton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity, children.nombre + " se ha añadido a la cesta.",
                        Toast.LENGTH_SHORT).show();
                Producto p = sesion.getProducto(children.id);
                Item i = new Item();
                i.setProducto(p);
                cesta.addItem(i);

            }
        });
        /*
        convertView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity, children.nombre,
                        Toast.LENGTH_SHORT).show();
            }
        });
        */

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return groups.get(groupPosition).children.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groups.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return groups.size();
    }

    @Override
    public void onGroupCollapsed(int groupPosition) {
        super.onGroupCollapsed(groupPosition);
    }

    @Override
    public void onGroupExpanded(int groupPosition) {
        super.onGroupExpanded(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.listrow_group, null);
        }
        Group group = (Group) getGroup(groupPosition);
        ((CheckedTextView) convertView).setText(group.string);
        ((CheckedTextView) convertView).setChecked(isExpanded);
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }


}