package Pedidos;

import java.util.HashMap;
import java.util.Map;

import Producto.Producto;

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
	}
	
	public void addItem(Item i) {
		this.total += i.getPrecio() * i.getCantidad();
		this.productos.put(i.getIdProducto(), i);
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
