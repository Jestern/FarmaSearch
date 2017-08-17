package com.example.eila.farmaciasapp;



public class Stock {
	
	private int unidades;
	private Producto producto;

	public Stock(Producto p, int unidades) {
		this.producto = p;
		this.unidades = unidades;
	}
	
	public int getUnidades() {
		return unidades;
	}
	
	public void setUnidades(int u) {
		unidades = u;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

}
