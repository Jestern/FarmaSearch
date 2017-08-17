package com.example.eila.farmaciasapp;

import java.util.ArrayList;
import java.util.Date;



public class Factura {
	
	private String numeroFactura;
	private String fecha;
	private float total;
	private ArrayList<Item> lineas;

	public Factura() {
		lineas = new ArrayList<>();
	}

	public String getNumeroFactura() {
		return numeroFactura;
	}

	public void setNumeroFactura(String numeroFactura) {
		this.numeroFactura = numeroFactura;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public float getTotal() {
		return total;
	}

	public void setTotal(float total) {
		this.total = total;
	}

	public ArrayList<Item> getLineas() {
		return lineas;
	}


	
	public void addItem(Item i) {
		this.total += i.getPrecio() * i.getCantidad();
		this.lineas.add(i);
	}

}
