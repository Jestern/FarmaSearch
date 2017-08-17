package com.example.eila.farmaciasapp;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class Farmacia {
	
	private String idFarmacia;	
	private String nombre;	
	private String email;	
	private String direccion;
	private String telefono;	
	private Date fAlta;
	private double longitud;
	private double latitud;
	private Map<String, Horario> horarios = null;


	public Farmacia(String id, String nom, String email, String dir, String tel, double lat, double lon) {
		this.idFarmacia = id;
		this.nombre = nom;
		this.email = email;
		this.direccion = dir;
		this.telefono = tel;
		this.latitud  = lat;
		this.longitud = lon;
		this.horarios = new HashMap<>();

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

	public Date getfAlta() {
		return fAlta;
	}


	public void setfAlta(Date fAlta) {
		this.fAlta = fAlta;
	}

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
	
	public void addHorario(Horario h) {
		horarios.put(h.getDiaSemana(), h);
	}

}
