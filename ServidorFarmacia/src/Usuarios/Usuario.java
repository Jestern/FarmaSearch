package Usuarios;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import Pedidos.Factura;

public abstract class Usuario {

	private String nombre;
	private String apellidos;
	private String email;
	private String username;
	private String crypto_pass;
	private String direccion;
	private String telefono;
	private String fNacimiento;
	private String fAlta;
	private ArrayList<Factura> facturas = null;
	
	public Usuario(String nombre, String apellidos, String usname, String pass, String email){
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.email = email;
		this.username = usname;
		this.crypto_pass = pass;
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		this.fAlta = formatter.format(new Date()).toString();
		this.facturas = new ArrayList<>();
	}
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}
	
	String getCrypto_pass() {
		return crypto_pass;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public boolean contraseniaCorrecta(String p) {
		return crypto_pass.equals(p);
	}

	public void setCrypto_pass(String crypto_pass) {
		this.crypto_pass = crypto_pass;
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

	public String getfNacimiento() {
		return fNacimiento;
	}

	public void setfNacimiento(String fNacimiento) {
		this.fNacimiento = fNacimiento;
	}

	public String getfAlta() {
		return fAlta;
	}
	
	public ArrayList<Factura> getFacturas() {
		return facturas;
	}

	public void setFacturas(ArrayList<Factura> facturas) {
		this.facturas = facturas;
	}
	
	public void addFactura(Factura f) {
		facturas.add(f);
	}

	public abstract boolean esGerente();
	public abstract boolean esAdministrador();
}
