package JerseyPackage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sun.corba.se.impl.encoding.OSFCodeSetRegistry.Entry;
import com.sun.security.ntlm.Client;

import FormasPago.FormaPago;
import FormasPago.PagoCuenta;
import FormasPago.PagoTarjeta;
import FormasPago.PagoTransferencia;
import GestionDatos.DAO;
import GestionDatos.Haversine;
import InformacionFarmacias.Farmacia;
import InformacionFarmacias.Horario;
import InformacionFarmacias.Pregunta;
import InformacionFarmacias.Stock;
import Pedidos.Cesta;
import Pedidos.Factura;
import Producto.Departamento;
import Producto.Producto;
import Usuarios.Cliente;
import Usuarios.Usuario;
import Usuarios.UsuariosTranslator;
import javafx.util.Pair;
import javassist.bytecode.Descriptor.Iterator;

@Path("/")
public class ServidorRest {

	private static final float DISTANCE = 2.0f; // km

	private DAO dao;

	public ServidorRest() {
		dao = DAO.getInstance();
	}

	@GET
	@Path("/farmacias/getCercanas")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getFarmaciasCercanas(@QueryParam("latitud")double latitud, @QueryParam("longitud")double longitud){
		// lista para almacenar las farmacias cercanas
		List<Farmacia> farmaciasCercanas = new ArrayList<Farmacia>();
		// obtener la lista de farmacias del sistema
		List<Farmacia> farmacias = dao.getFarmacias();


		for(Farmacia f : farmacias) {
			// si la farmacia está a menos de 2km se sañade a la lista.
			if(Haversine.distance(latitud, longitud, f.getLatitud(), f.getLongitud()) < DISTANCE) {
				farmaciasCercanas.add(f);
			}
		}

		return Response.ok(farmaciasCercanas).build();
	}

	@GET
	@Path("/farmacias/get")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getFarmacias(){

		return Response.ok(dao.getFarmacias()).build();
	}

	@GET
	@Path("/farmacias/productos/get")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getProductos(@QueryParam("idFarmacia")String idFarmacia) {
		// mensaje por defecto si la id de la farmacia es errónea.
		Response resp = Response.noContent().entity("idFarmacia").build();
		Farmacia f = dao.getFarmacia(idFarmacia);

		// si hemos encontrado la farmacia.
		if(f != null) {
			resp = Response.ok(f.getProductosStock()).build();
		}

		return resp;
	}
	
	@GET
	@Path("/productos/get")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getProductosTodos() {
		// mensaje por defecto si la id de la farmacia es errónea.
		Response resp = Response.ok().entity(dao.getProductosTodos()).build();

		return resp;
	}

	@GET
	@Path("/farmacias/productos/departamento/get")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getProductosDepartamento(@QueryParam("idFarmacia")String idFarmacia, @QueryParam("departamento")String d) {
		// obtener el valor del departamento como un valor del enum.
		Departamento departamento = Departamento.valueOf(d);

		// mensaje por defecto si la id de la farmacia es errónea.
		Response resp = Response.noContent().entity("idFarmacia").build();
		Farmacia f = dao.getFarmacia(idFarmacia);

		// lista de productos del departamento concreto.
		List<Stock> productos = new ArrayList<Stock>();

		// si hemos encontrado la farmacia.
		if(f != null) {

			for(Stock s : f.getProductosStock()) {
				// si el departamento coincide con el pedido
				if(s.getProducto().getDepartamento().equals(departamento)) {
					productos.add(s);
				}
			}

			resp = Response.ok(productos).build();
		}

		return resp;
	}

	@GET
	@Path("/preguntas/get")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Pregunta> getFAQ(){

		return dao.getPreguntas();
	}

	@GET
	@Path("/farmacias/productos/buscar/get")
	@Produces(MediaType.APPLICATION_JSON)
	public Response searchProducto(@QueryParam("id")String idFarmacia, @QueryParam("string")String str){
		Response resp = Response.noContent().entity("idFarmacia").build();
		Farmacia f = dao.getFarmacia(idFarmacia);

		List<Stock> productos = new ArrayList<Stock>();

		if(f != null) {

			for(Stock s : f.getProductosStock()) {
				if(s.getProducto().getNombre().toUpperCase().contains(str.toUpperCase())) {
					productos.add(s);
				}
			}

			resp = Response.ok(productos).build();
		}

		return resp;
	}


	@POST
	@Path("/usuarios/registrar")
	@Produces(MediaType.TEXT_PLAIN)
	public Response registrarUsuario(@FormParam("name")String n, @FormParam("last")String a, @FormParam("email")String e, @FormParam("user")String u, @FormParam("pass")String p){
		boolean existeUsuario = dao.existeUsuario(u);
		boolean existeEmail = dao.existeEmail(e);

		Response resp;

		if(existeUsuario && existeEmail) {
			resp = Response.status(-1).entity("user,email").build();
		} else if(existeUsuario) {
			resp = Response.status(-2).entity("user").build();
		} else if(existeEmail) {
			resp = Response.status(-3).entity("email").build();
		} else {
			resp = Response.status(0).entity("ok").build();
			Cliente user = new Cliente(n, a, u, p, e);
			dao.addUsuario(user);
		}

		return resp;
	}

	//Devuelve un token de la sesión del usuario
	@POST
	@Path("/usuarios/login")
	@Produces(MediaType.TEXT_PLAIN)
	public Response login(@FormParam("user")String u, @FormParam("pass")String p){

		String logged = dao.login(u, p);
		Response resp;

		if(logged.equals("false")) {
			resp = Response.status(400).entity(logged).build();
		} else {
			resp = Response.accepted().entity(logged).build();
		}

		return resp;
	}
	
	@POST
	@Path("/usuarios/loginAdmins")
	@Produces(MediaType.APPLICATION_JSON)
	public Response loginAdmins(@FormParam("user")String u, @FormParam("pass")String p){
		Usuario user = dao.getUsuario(u);
		Response resp = Response.status(400).entity("false").build();
		List<String> loggin = new ArrayList<>();
		
		if(user != null) {
			String logged = "false";
			if(user.esGerente()) {
				logged = dao.login(u, p);
				
				if(!logged.equals("false")) {
					loggin.add("gerente");
				}
				
			} else if(user.esAdministrador()) {
				
				logged = dao.login(u, p);
				
				if(!logged.equals("false")) {
					loggin.add("administrador");
				}
				
			}
			
			loggin.add(logged);
			resp = Response.accepted().entity(loggin).build();
		}

		return resp;
	}

	@POST
	@Path("/usuarios/facturas/add")
	@Produces(MediaType.TEXT_PLAIN)
	public Response tramitarPedido(@FormParam("token")String token, @FormParam("cesta")String c, @FormParam("tipo") String tipo, @FormParam("formaPago") String p){

		Response resp = Response.status(403).entity("token").build();

		if(dao.isLogged(token)) {
			FormaPago pago = null;
			
			Cesta cesta = (new Gson()).fromJson(c, Cesta.class);

			/*switch (tipo) {
			case "tarjeta":
				pago = (new Gson()).fromJson(p, PagoTarjeta.class);
				break;

			case "cuenta":
				pago = (new Gson()).fromJson(p, PagoCuenta.class);
				break;

			case "transferencia":
				pago = (new Gson()).fromJson(p, PagoTransferencia.class);
				break;
			}*/

			dao.tramitarPedido(token, cesta, pago);
			resp = Response.ok().entity("ok").build();
		}

		return resp;
	}

	@POST
	@Path("/usuarios/facturas")
	@Produces(MediaType.APPLICATION_JSON)
	public Response obtenerFacturas(@FormParam("token")String token){

		Response resp = Response.status(403).entity("token").build();

		if(dao.isLogged(token)) {
			resp = Response.ok().entity(dao.getFacturasUsuario(token)).build();
		}

		return resp;
	}

	@POST
	@Path("/usuarios/modificar")
	@Produces(MediaType.TEXT_PLAIN)
	public Response modificarDatosUsuario(@FormParam("token")String token, @FormParam("name")String n, @FormParam("last")String a, @FormParam("email")String e, @FormParam("pass")String p, @FormParam("address")String d, @FormParam("phone")String t, @FormParam("date")String f){
		Response resp = Response.status(403).entity("token").build();

		if(dao.isLogged(token)) {
			dao.modificarUsuario(token, n, a, e, p, d, t, f);
			resp = Response.ok().build();
		}

		return resp;
	}

	@POST
	@Path("/farmacias/producto")
	@Produces(MediaType.TEXT_PLAIN)
	public Response addProductoFarmacia(@FormParam("token")String token, @FormParam("idProducto")String idP, @FormParam("idFarmacia")String idF, @FormParam("unidades")String unidades){
		Response resp = Response.status(403).entity("token").build();
		Usuario user = dao.getUsuarioToken(token);

		if(user != null) {
			if(user.esAdministrador() || user.esGerente()) {

				String msg = dao.addProductoFarmacia(idF, idP, Integer.valueOf(unidades));
				resp = Response.ok().entity(msg).build();
			}
		}

		return resp;
	}

	@POST
	@Path("/usuarios/getDatos")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getDatosUsuario(@FormParam("token") String token) {
		Response resp = Response.status(403).entity("token").build();

		if(dao.isLogged(token)) {
			Usuario user = dao.getUsuarioToken(token);
			resp = Response.ok().entity(user).build();
		}

		return resp;
	}

	@POST
	@Path("/productos/add")
	@Produces(MediaType.TEXT_PLAIN)
	public Response addProducto(@FormParam("token")String token, @FormParam("nombre")String nombre, @FormParam("descripcion")String descripcion, @FormParam("precio")String precio, @FormParam("codigoBarras")String codigoBarras, @FormParam("img")String img, @FormParam("departamento")String departamento) {
		Response resp = Response.status(403).entity("token").build();
		Usuario user = dao.getUsuarioToken(token);

		if(user != null) {
			if(user.esAdministrador() || user.esGerente()) {
				dao.addProducto(nombre, descripcion, precio, codigoBarras, img, departamento);
				resp = Response.accepted().entity("ok").build();
			}
		}

		return resp;
	}

	@POST
	@Path("/productos/edit")
	@Produces(MediaType.TEXT_PLAIN)
	public Response modificarProducto(@FormParam("token")String token, @FormParam("nombre")String nombre, @FormParam("descripcion")String descripcion, @FormParam("precio")String precio, @FormParam("codigoBarras")String codigoBarras, @FormParam("img")String img, @FormParam("departamento")String departamento){

		return addProducto(token, nombre, descripcion, precio, codigoBarras, img, departamento);
	}

	@POST
	@Path("/farmacias/producto/delete")
	@Produces(MediaType.TEXT_PLAIN)
	public Response eliminarProductoFarmacia(@FormParam("token")String token, @FormParam("idFarmacia")String idF, @FormParam("idProducto")String idP){
		Response resp = Response.status(403).entity("token").build();

		if(dao.isLogged(token)) {

			Usuario user = dao.getUsuarioToken(token);

			if(user.esGerente() || user.esAdministrador()) {
				String msg = dao.deleteProductoFarmacia(idF, idP);
				resp = Response.accepted().entity(msg).build();
			}

		}

		return resp;
	}

	@POST
	@Path("/productos/delete")
	@Produces(MediaType.TEXT_PLAIN)
	public Response eliminarProducto(@FormParam("token")String token, @FormParam("idProducto")String idP) {
		Response resp = Response.status(403).entity("token").build();

		if(dao.isLogged(token)) {

			Usuario user = dao.getUsuarioToken(token);

			if(user.esGerente() || user.esAdministrador()) {
				String msg = dao.deleteProducto(idP);
				resp = Response.accepted().entity(msg).build();
			}

		}

		return resp;
	}

	@POST
	@Path("/farmacias/delete")
	@Produces(MediaType.TEXT_PLAIN)
	public Response eliminarFarmacia(@FormParam("token")String token, @FormParam("idFarmacia")String idF){
		Response resp = Response.status(403).entity("token").build();

		if(dao.isLogged(token)) {

			Usuario user = dao.getUsuarioToken(token);

			if(user.esAdministrador()) {
				String msg = dao.deleteFarmacia(idF);
				resp = Response.accepted().entity(msg).build();
			}

		}

		return resp;
	}

	@POST
	@Path("/usuarios/addGerente")
	@Produces(MediaType.TEXT_PLAIN)
	public Response addGerente(@FormParam("token")String token, @FormParam("username")String u){
		Response resp = Response.status(403).entity("token").build();

		if(dao.isLogged(token)) {

			Usuario admin = dao.getUsuarioToken(token);

			if(admin.esAdministrador()) {
				Usuario user = dao.getUsuario(u);
				String msg = "username";
				if(user != null) {
					msg = "ok";
					Usuario newUser = UsuariosTranslator.transformToGerente(user);

					dao.setUsuario(newUser);
				}

				resp = Response.accepted().entity(msg).build();
			}

		}

		return resp;

	}

	@POST
	@Path("/farmacias/add")
	@Produces(MediaType.TEXT_PLAIN)
	public Response addFarmacia(@FormParam("token")String token, @FormParam("name")String nom, @FormParam("email")String email, @FormParam("address")String dir, @FormParam("phone")String tel, @FormParam("latitude")String lat, @FormParam("longitude")String lon){
		Response resp = Response.status(403).entity("token").build();

		if(dao.isLogged(token)) {

			Usuario admin = dao.getUsuarioToken(token);

			if(admin.esAdministrador()) {
				dao.addFamacia(nom, email, dir, tel, Double.valueOf(lat), Double.valueOf(lon));
				resp = Response.accepted().entity("ok").build();
			}
		}

		return resp;
	}

	@POST
	@Path("/farmacias/setGerente")
	@Produces(MediaType.TEXT_PLAIN)
	public Response setGerenteFarmacia(@FormParam("token")String token, @FormParam("username")String u, @FormParam("idFarmacia")String idF){
		Response resp = Response.status(403).entity("token").build();

		if(dao.isLogged(token)) {

			Usuario admin = dao.getUsuarioToken(token);

			if(admin.esAdministrador()) {
				String msg = dao.setGerenteFarmacia(u, idF);
				resp = Response.accepted().entity(msg).build();
			}

		}

		return resp;
	}

	@POST
	@Path("/usuarios/addAdministrador")
	@Produces(MediaType.TEXT_PLAIN)
	public Response addAdministrador(@FormParam("token")String token, @FormParam("username")String u) {
		Response resp = Response.status(403).entity("token").build();

		if(dao.isLogged(token)) {

			Usuario admin = dao.getUsuarioToken(token);

			if(admin.esAdministrador()) {
				Usuario user = dao.getUsuario(u);
				String msg = "username";
				if(user != null) {
					msg = "ok";
					Usuario newUser = UsuariosTranslator.transformToAdministrador(user);

					dao.setUsuario(newUser);
				}

				resp = Response.accepted().entity(msg).build();
			}

		}

		return resp;
	}

	@POST
	@Path("/usuarios/deleteGerente")
	@Produces(MediaType.TEXT_PLAIN)
	public Response eliminarGerente(@FormParam("token")String token, @FormParam("username")String u){
		Response resp = Response.status(403).entity("token").build();

		if(dao.isLogged(token)) {

			Usuario admin = dao.getUsuarioToken(token);

			if(admin.esAdministrador()) {
				String msg = dao.deleteGerente(u);
				resp = Response.accepted().entity(msg).build();
			}

		}

		return resp;
	}

	@POST
	@Path("/usuarios/deleteAdministrador")
	@Produces(MediaType.TEXT_PLAIN)
	public Response eliminarAdministrador(@FormParam("token")String token, @FormParam("username")String u){
		Response resp = Response.status(403).entity("token").build();

		if(dao.isLogged(token)) {

			Usuario admin = dao.getUsuarioToken(token);

			if(admin.esAdministrador()) {
				String msg = dao.deleteAdministrador(u);
				resp = Response.accepted().entity(msg).build();
			}

		}

		return resp;
	}

	@POST
	@Path("/farmacias/addHorario")
	@Produces(MediaType.TEXT_PLAIN)
	public Response addHorarioFarmacia(@FormParam("token")String token, @FormParam("dia")String dia, @FormParam("hApM")String hApM, @FormParam("hCiM")String hCiM, @FormParam("hApT")String hApT, @FormParam("hCiT")String hCiT, @FormParam("idFarmacia")String idF){
		Response resp = Response.status(403).entity("token").build();

		if(dao.isLogged(token)) {

			Usuario user = dao.getUsuarioToken(token);

			if(user.esAdministrador() || user.esGerente()) {
				Horario h = new Horario(dia, hApM, hCiM, hApT, hCiT);
				String msg = dao.addHorarioFarmacia(idF, h);
				resp = Response.accepted().entity(msg).build();
			}

		}

		return resp;
	}

	@POST
	@Path("/preguntas/add")
	@Produces(MediaType.TEXT_PLAIN)
	public Response addPregunta(@FormParam("token")String token, @FormParam("q")String q, @FormParam("a")String a) {
		Response resp = Response.status(403).entity("token").build();

		if(dao.isLogged(token)) {

			Usuario user = dao.getUsuarioToken(token);

			if(user.esAdministrador()) {
				Pregunta p = new Pregunta(q, a);
				dao.addPregunta(p);
				resp = Response.accepted().entity("ok").build();
			}

		}

		return resp;
	}

	@POST
	@Path("/preguntas/delete")
	@Produces(MediaType.TEXT_PLAIN)
	public Response deletePregunta(@FormParam("token")String token, @FormParam("q")String q) {
		Response resp = Response.status(403).entity("token").build();

		if(dao.isLogged(token)) {

			Usuario user = dao.getUsuarioToken(token);

			if(user.esAdministrador()) {
				dao.deletePregunta(q);
				resp = Response.accepted().entity("ok").build();
			}

		}

		return resp;
	}


}
