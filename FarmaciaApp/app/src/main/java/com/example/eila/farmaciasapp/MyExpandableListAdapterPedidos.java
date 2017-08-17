package com.example.eila.farmaciasapp;

/**
 * Created by Eila on 10/05/2016.
 */
import android.app.Activity;
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

public class MyExpandableListAdapterPedidos extends BaseExpandableListAdapter {

    Sesion sesion = Sesion.getInstance();



    private final SparseArray<GroupPedido> groups;
    public LayoutInflater inflater;
    public Activity activity;

    public MyExpandableListAdapterPedidos(Activity act, SparseArray<GroupPedido> groups) {
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
        final ChildrenPedido children = (ChildrenPedido) getChild(groupPosition, childPosition);
        TextView text = null;
        ImageView image = null;
        Button boton = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.listrow_details_pedido, null);
        }
        text = (TextView) convertView.findViewById(R.id.textViewItemNombre);
        text.setText(children.nombre);
        text = (TextView) convertView.findViewById(R.id.textViewItemCantidad);
        text.setText(Integer.toString(children.cantidad) + "uds");
        text = (TextView) convertView.findViewById(R.id.textViewItemPrecio);
        text.setText("Precio: " + Float.toString(children.precio) + "€");


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
        GroupPedido group = (GroupPedido) getGroup(groupPosition);
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