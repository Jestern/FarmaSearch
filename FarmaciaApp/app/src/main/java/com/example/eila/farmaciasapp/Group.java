package com.example.eila.farmaciasapp;

/**
 * Created by Eila on 10/05/2016.
 */

import java.util.ArrayList;
import java.util.List;

public class Group {

    public String string;
    public final List<Children> children = new ArrayList<Children>();

    public Group(String string) {
        this.string = string;
    }

}