package InformacionFarmacias;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Pedidos.Factura;
import Pedidos.Item;
import Producto.Producto;
import Usuarios.Gerente;

import java.util.ArrayList;

public class Farmacia {
	
	private String idFarmacia;	
	private String nombre;	
	private String email;	
	private String direccion;
	private String telefono;	
	//private Date fAlta;
	private double longitud;
	private double latitud;
	private Map<String, Horario> horarios = null;
	private ArrayList<Stock> productosStock = null;
	private Gerente gerente = null;
	private List<Factura> pedidos = null;

	public Farmacia(String id, String nom, String email, String dir, String tel, double lat, double lon) {
		this.idFarmacia = id;
		this.nombre = nom;
		this.email = email;
		this.direccion = dir;
		this.telefono = tel;
		this.latitud  = lat;
		this.longitud = lon;
		this.horarios = new HashMap<>();
		this.productosStock = new ArrayList<>();
		this.pedidos = new ArrayList<>();
		//this.fAlta = new Date();
	}
	
	public void addProducto(Producto p, int unidades) {
		productosStock.add(new Stock(p, unidades));
	}
	
	public String getIdFarmacia() {
		return idFarmacia;
	}

	public void setIdFarmacia(String idFarmacia) {
		this.idFarmacia = idFarmacia;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	/*
	public Date getfAlta() {
		return fAlta;
	}*/
	
	public Gerente getGerente() {
		return gerente;
	}

	public void setGerente(Gerente gerente) {
		this.gerente = gerente;
	}

	/*public void setfAlta(Date fAlta) {
		this.fAlta = fAlta;
	}*/

	public double getLongitud() {
		return longitud;
	}

	public void setLongitud(float longitud) {
		this.longitud = longitud;
	}

	public double getLatitud() {
		return latitud;
	}

	public void setLatitud(float latitud) {
		this.latitud = latitud;
	}

	public Map<String, Horario> getHorarios() {
		return horarios;
	}

	public void setHorarios(Map<String, Horario> horarios) {
		this.horarios = horarios;
	}

	public ArrayList<Stock> getProductosStock() {
		return productosStock;
	}

	public void setProductosStock(ArrayList<Stock> productosStock) {
		this.productosStock = productosStock;
	}
	
	public void deleteProducto(Producto p) {
		
		Stock stock = null;
		if(p != null) {
			for(Stock s : productosStock) {
				if(s.getProducto() == p) {
					stock = s;
				}
			}
			
			if(stock != null) {
				productosStock.remove(stock);
			}
		}
		
	}
	
	public void addHorario(Horario h) {
		horarios.put(h.getDiaSemana(), h);
	}
	
	public void addPedido(Factura f) {
		for(Item i : f.getLineas()) {
			for(Stock s : productosStock) {
				if(i.getIdProducto().equals(s.getProducto().getId())) {
					int cantidad = s.getUnidades() - i.getCantidad();
					if(cantidad < 0) {
						i.setCantidad(i.getCantidad() + cantidad);
						s.setUnidades(0);
					} else {
						s.setUnidades(cantidad);
					}
					
				}
			}
		}
		pedidos.add(f);
	}

	public List<Factura> getPedidos() {
		return pedidos;
	}

	public void setPedidos(List<Factura> pedidos) {
		this.pedidos = pedidos;
	}

}
