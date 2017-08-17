package com.example.eila.farmaciasapp;

/**
 * Created by Eila on 10/05/2016.
 */

import java.util.ArrayList;
import java.util.List;

public class GroupPedido {

    public String string;
    public final List<ChildrenPedido> children = new ArrayList<ChildrenPedido>();

    public GroupPedido(String string) {
        this.string = string;
    }

}