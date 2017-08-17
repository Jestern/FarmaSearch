package GestionDatos;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import FormasPago.FormaPago;

import java.util.List;

import InformacionFarmacias.Farmacia;
import InformacionFarmacias.Horario;
import InformacionFarmacias.Pregunta;
import Pedidos.Cesta;
import Pedidos.Factura;
import Producto.Departamento;
import Producto.Producto;
import Usuarios.Administrador;
import Usuarios.Cliente;
import Usuarios.Gerente;
import Usuarios.Usuario;

public class DAO {
	private long nFacturas;
	private Map<String, Usuario> usuarios;
	private Map<String, Usuario> emailsUsuarios;
	private Map<String, Farmacia> farmacias;
	private Map<String, Producto> productos;
	private Map<String, Usuario> usuariosActivos;
	private Map<String, Pregunta> preguntas;
	private SecureRandom random;
	
	private static DAO dao = new DAO();

	private DAO() {
		usuarios = new HashMap<>();
		emailsUsuarios = new HashMap<>();
		farmacias = new HashMap<>();
		productos = new HashMap<>();
		random = new SecureRandom();
		preguntas = new HashMap<>();
		usuariosActivos = new HashMap<>();
		nFacturas = 0;
		initialize();
	}
	
	private void initialize() {
		Administrador admin = new Administrador("admin", "admin", "admin", "admin", "admin@admin.com");
		addUsuario(admin);
		addFamacia("Farmacia Fuentes Romero", "farmacia@farmaciafuentesromero.com", "Calle Periodista Fernando Gómez de la Cruz, 57,18014 Granada", "958 15 02 14", 37.19730d, -3.62301d);
		addFamacia("Farmacia Santamaría", "farmacia@farmaciasantamaria.com", "Calle Periodista José María Carulla, 8,18014 Granada", "958 15 02 14", 37.19462d, -3.62282d);
		
		addProducto("Paracetamol 1g", "20 comprimidos EFG via oral.", "1.90", "8412345678905", "/imgs/paracetamol.jpg", "SALUD");
		addProducto("Primperan 10mg", "20 comprimidos via oral. Metoclopramida", "2.90", "8412345678906", "/imgs/primperan.jpg", "SALUD");
		addProducto("Chupete 6-18 meses", "Chupete nocturno AVENT.", "2.00", "8412347678105", "/imgs/chupete.jpg", "INFANTIL");
		addProducto("Desmaquillante de ojos", "Desmaquillante bifásico de ojos NIVEA", "4.00", "8412345678105", "/imgs/desmaquillante.jpg", "COSMETICA");
		addProducto("PediaSure Plus", "Complemento nutricional", "3.80", "8416345678105", "/imgs/pediasure.jpg", "NUTRICION");
		addProducto("Gingil Lacer", "Cepillo de dientes", "2.80", "8416945678105", "/imgs/cepillo.jpg", "HIGIENE");
		addProducto("NuxeBody", "Perfume relajante", "16.50", "8416945678115", "/imgs/perfume.jpg", "PERFUMERIA");
		addProducto("Te Verde", "Te verde de origen ecológico", "5.50", "8419945678115", "/imgs/te.jpg", "NATURAL");
		addProducto("Vaginesil", "Alivio para la sequedad vaginal.", "8.00", "8419945678115", "/imgs/vaginesil.jpg", "VIDA_INTIMA");
		
		
		List<Horario> horarios = new ArrayList<>();
		
		horarios.add(new Horario("Lunes", "8:30", "14:00", "17:00", "20:00"));
		horarios.add(new Horario("Martes", "8:30", "14:00", "17:00", "20:00"));
		horarios.add(new Horario("Miercoles", "8:30", "14:00", "17:00", "20:00"));
		horarios.add(new Horario("Jueves", "8:30", "14:00", "17:00", "20:00"));
		horarios.add(new Horario("Viernes", "8:30", "14:00", "17:00", "20:00"));
		
		Pregunta p = new Pregunta();
		Pregunta p2 = new Pregunta();
		p.setPregunta("¿Para que sirve esta aplicación?");
		p.setRespuesta("Para realizar compras en farmacias cercanas");
		p2.setPregunta("¿Esta aplicación funciona de verdad?");
		p2.setRespuesta("No, esto es una práctica para la asignatura de Desarrollo del software");
		
		addPregunta(p);
		addPregunta(p2);
		
		
		
		
		farmacias.forEach((k,v) -> {
			for(Horario h : horarios)
				v.addHorario(h);
			productos.forEach( (pk, pv) -> v.addProducto(pv, 10) );
		});
		
	}
	
	public List<Producto> getProductosTodos() {
		return new ArrayList<>(productos.values());
	}
	
	public static DAO getInstance() {
		return dao;
	}
	
	public List<Farmacia> getFarmacias() {
		return new ArrayList<Farmacia>(farmacias.values());
	}
	
	public Farmacia getFarmacia(String idFarmacia) {
		return farmacias.get(idFarmacia);
	}
	
	public Map<String, Usuario> getUsuarios() {
		return usuarios;
	}
	
	public List<Pregunta> getPreguntas() {
		return new ArrayList<>(preguntas.values());
	}
	
	public void addPregunta(Pregunta p) {
		p.setId(Integer.toString(generateId(preguntas)));
		preguntas.put(Integer.toString(generateId(preguntas)), p);
	}
	
	public void deletePregunta(String p) {
		preguntas.remove(p);
	}
	
	public void addFamacia(String nom, String email, String dir, String tel, double lat, double lon) {
		int id = generateId(farmacias);
		Farmacia f = new Farmacia(Integer.toString(id), nom, email, dir, tel, lat, lon);
		
		farmacias.put(Integer.toString(generateId(farmacias)), f);
	}
	
	public String setGerenteFarmacia(String user, String idF) {
		String msg = "idFarmacia";
		Farmacia f = farmacias.get(idF);
		
		if(f != null) {
			msg = "user";
			Usuario usuario = usuarios.get(user);
			
			if(usuario.esGerente()) {
				((Gerente) usuario).setFarmacia(f);
				f.setGerente(((Gerente) usuario));
			} else {
				msg = "invalidUser";
			}
		}
		
		return msg;
	}
	
	public String deleteGerente(String u) {
		String msg = "user";
		Usuario user = usuarios.get(u);
		
		if(user != null) {
			msg = "invalidUser";
			if(user.esGerente()) {
				msg = "ok";
				Gerente g = ( (Gerente)user );
				farmacias.forEach((k, v) -> { if(v.getGerente() == g) { v.setGerente(null); } });
				usuarios.remove(u);
				String token = "";
				usuariosActivos.forEach((k, v) -> { if(v == g) { token.concat(k); } });
				usuariosActivos.remove(token);
			}
		}
		
		
		return msg;
	}
	
	public String deleteAdministrador(String u) {
		String msg = "user";
		Usuario user = usuarios.get(u);
		
		if(user != null) {
			msg = "invalidUser";
			if(user.esAdministrador()) {
				msg = "ok";
				Administrador a = ( (Administrador)user );
				usuarios.remove(u);
				String token = "";
				usuariosActivos.forEach((k, v) -> { if(v == a) { token.concat(k); } });
				usuariosActivos.remove(token);
			}
		}
		
		
		return msg;
	}
	
	public String login(String u, String p) {

		String logged = "false";
		Usuario user = this.getUsuario(u);
				
		if(user != null) {
			
			if( user.contraseniaCorrecta(p) ) {
				
				logged = this.nextSessionId();
				
				// eliminar la sesión antigua si ya estaba logueado con anterioridad.
				if(usuariosActivos.containsValue(user)) {
					String key = "";
					
					for (Map.Entry<String, Usuario> entry : usuariosActivos.entrySet()) {
						if(entry.getValue() == user) {
							key = entry.getKey();
						}
					}
					
					usuariosActivos.remove(key);
				}
				usuariosActivos.put(logged, user);
				
				
			}
			
		}
		
		return logged;
		
	}
	
	public List<Factura> getFacturasUsuario(String token) {
		Usuario user = usuariosActivos.get(token);
		List<Factura> facturas = null;
		
		if(dao.isLogged(token)) {
				facturas = user.getFacturas();
			
		}
		
		return facturas;
	}
	
	public void setUsuario(Usuario u) {
		usuarios.put(u.getUsername(), u);
	}
	
	public Usuario getUsuarioToken(String token) {
		return usuariosActivos.get(token);
	}
	
	public String addHorarioFarmacia(String idF, Horario h) {
		String msg = "idFarmacia";
		Farmacia f = farmacias.get(idF);
		
		if(f != null) {
			msg = "ok";
			f.addHorario(h);
		}
		
		return msg;
	}
	
	public void modificarUsuario(String token, String n, String a, String e, String p, String d, String t, String f) {
		Usuario user = usuariosActivos.get(token);
		
		if(user != null) {
				if(!"".equals(n)) {
					user.setNombre(n);
				}
				if(!"".equals(a)) {
				user.setApellidos(a);
				}
				if(!"".equals(e)) {
				user.setEmail(e);
				}
				if(!"".equals(p)) {
				user.setCrypto_pass(p);
				}
				if(!"".equals(d)) {
				user.setDireccion(d);
				}
				if(!"".equals(t)) {
				user.setTelefono(t);
				}
				if(!"".equals(f)) {
				user.setfNacimiento(f);
				}
		}
	}
	
	public String addProductoFarmacia(String idFarmacia, String idProducto, int unidades) {
		Farmacia f = farmacias.get(idFarmacia);
		String msg = "idFarmacia";
		if(f != null) {
			Producto p = productos.get(idProducto);
			msg = "idProducto";
			if(p != null) {
				msg = "ok";
				f.addProducto(p, unidades);
			}
		}
		
		return msg;
	}
	
	public String deleteProductoFarmacia(String idF, String idP) {
		String msg = "idFarmacia";
		Farmacia f = farmacias.get(idF);
		
		if(f != null) {
			msg = "ok";
			f.deleteProducto(productos.get(idP));
		}
		
		return msg;
	}
	
	public String deleteProducto(String idP) {
		String msg = "idProducto";
		Producto p = productos.get(idP);
		
		if(p != null) {
			msg = "ok";
			farmacias.forEach( (k, v) -> v.deleteProducto(p) );
			productos.remove(idP);
		}
		
		return msg;
	}
	
	public String deleteFarmacia(String idF) {
		String msg = "idFarmacia";
		
		Farmacia f = farmacias.remove(idF);
		
		if(f != null) {
			msg = "ok";
		}
		
		return msg;
		
	}
	
	public boolean isLogged(String token) {
		return ( (usuariosActivos.get(token) == null ) ? false : true );
	}
	
	public boolean existeUsuario(String user) {
		return ( ( usuarios.get(user) == null ) ? false : true );
	}
	
	public boolean existeEmail(String email) {
		return ( ( emailsUsuarios.get(email) == null ) ? false : true );
	}
	
	public void addUsuario(Usuario u) {
		boolean existeUsuario = this.existeUsuario(u.getUsername());
		boolean existeEmail = this.existeEmail(u.getEmail());
		
		if( !(existeUsuario || existeEmail) ) {
			usuarios.put(u.getUsername(), u);
			emailsUsuarios.put(u.getEmail(), u);
		}
		
	}
	
	public Usuario getUsuario(String username) {
		return usuarios.get(username);
	}
	
	public void addProducto(String nombre, String descripcion, String precio, String codigoBarras, String img, String departamento) {
		
		int id = generateId(productos);
		
		Producto p = new Producto(Integer.toString(id), nombre, descripcion, Float.valueOf(precio), codigoBarras, img, Departamento.valueOf(departamento));
		productos.put(p.getId(), p);
	
	}
	
	public String nextSessionId() {
	    return new BigInteger(130, random).toString(32);
	}
	
	public void tramitarPedido(String token, Cesta c, FormaPago p) {
		Usuario user = usuariosActivos.get(token);
		Farmacia far = farmacias.get(c.getIdFarmacia());
		
		if(user != null &&  far != null) {
			Factura f = new Factura();
			f.setNumeroFactura(Long.toString(nFacturas++));
			f.setPago(p);
			
			c.getProductos().forEach((k,v) -> f.addItem(v));
			
			far.addPedido(f);
			user.addFactura(f);
		}
	}
	
	private int generateId(Map<String, ? extends Object> map) {
		int id = map.size();
		
		while(map.get(Integer.toString(id)) != null) {
			++id;
		}
		
		return id;
	}
	

}
