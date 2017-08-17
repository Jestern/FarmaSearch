package com.example.eila.farmaciasapp;



public class Item {
	private String nombre;
	private float precio;
	private int cantidad;
	private String idProducto;
	public Item() {
		// TODO Auto-generated constructor stub
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public float getPrecio() {
		return precio;
	}
	public void setPrecio(float precio) {
		this.precio = precio;
	}
	public int getCantidad() {
		return cantidad;
	}
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	public String getIdProducto() {
		return idProducto;
	}
	public void setIdProducto(String idProducto) {
		this.idProducto = idProducto;
	}

	public void addCantidad(){
		cantidad++;
	}
	public void subCantidad(){
		cantidad--;
	}
	public void setProducto(Producto p) {
		nombre = p.getNombre();
		precio = p.getPrecio();
		idProducto = p.getId();
		cantidad = 1;
	}

}
