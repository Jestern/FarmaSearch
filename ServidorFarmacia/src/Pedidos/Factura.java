package Pedidos;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import FormasPago.FormaPago;

public class Factura {
	
	private String numeroFactura;
	private String fecha;
	private float total;
	private ArrayList<Item> lineas;
	private FormaPago pago;

	public Factura() {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		fecha = formatter.format(new Date()).toString();
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

	public FormaPago getPago() {
		return pago;
	}

	public void setPago(FormaPago pago) {
		this.pago = pago;
	}
	
	public void addItem(Item i) {
		this.total += i.getPrecio() * i.getCantidad();
		this.lineas.add(i);
	}

}
