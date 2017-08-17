package com.example.eila.farmaciasapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Eila on 08/05/2016.
 */
public class Sesion {
    private static Sesion sesion = new Sesion();
    private String user = "";
    private String token = "";
    private Double latitud;
    private Double longitud;
    private Farmacia farmaciaSeleccionada;
    private ArrayList<Farmacia> farmacias;
    private ArrayList<Stock> stock;
    private Map<String,Cesta> cestas;

    private Sesion(){
    farmacias = new ArrayList<>();
    stock = new ArrayList<>();
    cestas = new HashMap<>();

    }
    public Producto getProducto(String id){
        Producto p = null;
        for (Stock s: stock) {
            p = s.getProducto();
            if (p.getId() == id){
                return p;
            }
        }
        return null;
    }

    public Cesta getCesta(){
        Cesta cesta = cestas.get(farmaciaSeleccionada.getIdFarmacia());
        return cesta;
    }

    public ArrayList<Stock> getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock.add(stock);
    }


    public static Sesion getInstance(){
        return sesion;
    }
    public void setUser(String user){
        this.user = user;
    }
    public void setToken(String token){
        this.token = token;
    }
    public String getUser(){
        return user;
    }
    public String getToken(){
        return token;
    }
    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    public void cleanCesta(){
        cestas.clear();
    }

    public Farmacia getFarmacia(String id) {

        for (Farmacia farmacia: farmacias) {
            if(id == farmacia.getIdFarmacia())
                return farmacia;
        }

        return null;
    }


    public Farmacia getFarmaciaSeleccionada() {
        return farmaciaSeleccionada;
    }

    public void setFarmaciaSeleccionada(Farmacia farmaciaSeleccionada) {
        this.farmaciaSeleccionada = farmaciaSeleccionada;

        if(!cestas.containsKey(farmaciaSeleccionada.getIdFarmacia()))
            cestas.put(farmaciaSeleccionada.getIdFarmacia(),
                    new Cesta(farmaciaSeleccionada.getIdFarmacia()));
    }
    public void cleanFarmacias(){
        farmacias.clear();
    }
    public void cleanStock(){
        stock.clear();
    }

    public ArrayList<Farmacia> getFarmacias() {
        return farmacias;
    }

    public void addFarmacia(Farmacia farmacia) {
        this.farmacias.add(farmacia);
    }

}
