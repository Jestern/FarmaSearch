package com.example.eila.farmaciasapp;

import java.util.HashMap;
import java.util.Map;



public class Cesta {
	private String idFarmacia;
	private Map<String, Item> productos = null;
	private float total;
	
	
	public Cesta() {
		productos = new HashMap<>();
		total = 0.0f;
	}
	
	public Cesta(String idFarmacia) {
		this.idFarmacia = idFarmacia;
		productos = new HashMap<>();
		total = 0.0f;
	}
	
	public void addItem(Item i) {
		if(!productos.containsKey(i.getIdProducto())) {
			this.total += i.getPrecio() * i.getCantidad();
			this.productos.put(i.getIdProducto(), i);
		}
	}

	public void addPrize(float prize){
		total += prize;
	}
	public void subPrize(float prize){
		total -= prize;
	}

	public float getTotal() {
		return total;
	}

	public void setTotal(float p){
		total = p;
	}

	public String getIdFarmacia() {
		return idFarmacia;
	}

	public void setIdFarmacia(String idFarmacia) {
		this.idFarmacia = idFarmacia;
	}

	public Map<String, Item> getProductos() {
		return productos;
	}

	public void setProductos(Map<String, Item> productos) {
		this.productos = productos;
	}
	
	
}
