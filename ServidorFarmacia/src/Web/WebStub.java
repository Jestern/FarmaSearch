package Web;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.json.JSONArray;

import com.google.gson.Gson;

import InformacionFarmacias.Farmacia;
import InformacionFarmacias.Pregunta;
import InformacionFarmacias.Stock;
import Producto.Producto;

public class WebStub {
	private final String URL = "http://localhost:8080/ServidorFarmacia/ServletFarmacia";
	private static WebStub stub = new WebStub();
	private Client client = ClientBuilder.newClient();
	
	private WebStub() {
		
		
	}
	
	public static WebStub getInstace() {
		return stub;
	}
	
	public List<String> login(String user, String pass) {
		WebTarget webTarget = client.target(URL);
	    MultivaluedMap<String, String> formData = new MultivaluedHashMap<String, String>();
	    formData.add("action", "loginAdmins");
	    formData.add("user", user);
	    formData.add("pass", pass);
	    Response response = webTarget.request().post(Entity.form(formData));
	    List<String> resp = (new Gson()).fromJson(response.readEntity(String.class), List.class);
	    
	    return resp;
	}
	
	public List<Farmacia> getFarmacias() {
		
		WebTarget webTarget = client.target(URL + "?action=getFarmacias");
		
		Response response = webTarget.request().get();
		
		List<Farmacia> resp = new ArrayList<>();
		Gson gson = new Gson();
		String json = response.readEntity(String.class);
		
		JSONArray array = new JSONArray(json);
		
		for(int i = 0; i < array.length(); ++i) {
			resp.add(gson.fromJson(array.get(i).toString(), Farmacia.class));
		}
		
		return resp;
		
	}
	
	public List<Pregunta> getPreguntas() {
		
		WebTarget webTarget = client.target(URL + "?action=getFAQ");
		
		Response response = webTarget.request().get();
		
		List<Pregunta> resp = new ArrayList<>();
		Gson gson = new Gson();
		String json = response.readEntity(String.class);
		
		JSONArray array = new JSONArray(json);
		
		for(int i = 0; i < array.length(); ++i) {
			resp.add(gson.fromJson(array.get(i).toString(), Pregunta.class));
		}
		
		return resp;
		
	}
	
	public List<Producto> getProductos() {
		
		WebTarget webTarget = client.target(URL + "?action=getProductosTodos");
		
		Response response = webTarget.request().get();
		
		List<Producto> resp = new ArrayList<>();
		Gson gson = new Gson();
		String json = response.readEntity(String.class);
		
		JSONArray array = new JSONArray(json);
		
		for(int i = 0; i < array.length(); ++i) {
			resp.add(gson.fromJson(array.get(i).toString(), Producto.class));
		}
		
		return resp;
		
	}
	
	public List<Stock> getProductosFarmacia(String idFarmacia) {
		
		WebTarget webTarget = client.target(URL + "?action=getProductos&idFarmacia=" + idFarmacia);
		
		Response response = webTarget.request().get();
		
		List<Stock> resp = new ArrayList<>();
		Gson gson = new Gson();
		String json = response.readEntity(String.class);
		
		JSONArray array = new JSONArray(json);
		
		for(int i = 0; i < array.length(); ++i) {
			resp.add(gson.fromJson(array.get(i).toString(), Stock.class));
		}
		
		return resp;
		
	}
	
	public void addPregunta(String token, String q, String a) {
		WebTarget webTarget = client.target(URL);
	    MultivaluedMap<String, String> formData = new MultivaluedHashMap<String, String>();
	    formData.add("action", "addPregunta");
	    formData.add("token", token);
	    formData.add("q", q);
	    formData.add("a", a);
	    Response response = webTarget.request().post(Entity.form(formData));
	    String resp = response.readEntity(String.class);
	}
	
	public void addFarmacia(String token, String name, String email, String phone, String latitude, String longitude, String address) {
		WebTarget webTarget = client.target(URL);
	    MultivaluedMap<String, String> formData = new MultivaluedHashMap<String, String>();
	    formData.add("action", "addFarmacia");
	    formData.add("token", token);
	    formData.add("name", name);
	    formData.add("email", email);
	    formData.add("phone", phone);
	    formData.add("latitude", latitude);
	    formData.add("longitude", longitude);
	    formData.add("address", address);
	    
	    Response response = webTarget.request().post(Entity.form(formData));
	    String resp = response.readEntity(String.class);
	}
	
	public void addProducto(String token, String nombre, String descripcion, String precio, String codigoBarras, String img, String departamento) {
		WebTarget webTarget = client.target(URL);
	    MultivaluedMap<String, String> formData = new MultivaluedHashMap<String, String>();
	    formData.add("action", "addProducto");
	    formData.add("token", token);
	    formData.add("nombre", nombre);
	    formData.add("descripcion", descripcion);
	    formData.add("precio", precio);
	    formData.add("codigoBarras", codigoBarras);
	    formData.add("img", img);
	    formData.add("departamento", departamento);
	    
	    Response response = webTarget.request().post(Entity.form(formData));
	    String resp = response.readEntity(String.class);
	}
	
	public void addUsuario(String token, String username, String password, String name, String last, String email) {
		WebTarget webTarget = client.target(URL);
	    MultivaluedMap<String, String> formData = new MultivaluedHashMap<String, String>();
	    formData.add("action", "registro");
	    formData.add("token", token);
	    formData.add("user", username);
	    formData.add("pass", password);
	    formData.add("name", name);
	    formData.add("last", last);
	    formData.add("email", email);
	    
	    Response response = webTarget.request().post(Entity.form(formData));
	    String resp = response.readEntity(String.class);
	}
	
	public void addGerente(String token, String username) {
		WebTarget webTarget = client.target(URL);
	    MultivaluedMap<String, String> formData = new MultivaluedHashMap<String, String>();
	    formData.add("action", "addGerente");
	    formData.add("token", token);
	    formData.add("username", username);
	    
	    Response response = webTarget.request().post(Entity.form(formData));
	    String resp = response.readEntity(String.class);
	}
	
	public void addAdministrador(String token, String username) {
		WebTarget webTarget = client.target(URL);
	    MultivaluedMap<String, String> formData = new MultivaluedHashMap<String, String>();
	    formData.add("action", "addAdministrador");
	    formData.add("token", token);
	    formData.add("username", username);
	    
	    Response response = webTarget.request().post(Entity.form(formData));
	    String resp = response.readEntity(String.class);
	}
	
	public void eliminarProducto(String token, String idProducto) {
		WebTarget webTarget = client.target(URL);
	    MultivaluedMap<String, String> formData = new MultivaluedHashMap<String, String>();
	    formData.add("action", "eliminarProducto");
	    formData.add("token", token);
	    formData.add("idProducto", idProducto);
	    
	    Response response = webTarget.request().post(Entity.form(formData));
	    String resp = response.readEntity(String.class);
	}
	
	public void eliminarFarmacia(String token, String idFarmacia) {
		WebTarget webTarget = client.target(URL);
	    MultivaluedMap<String, String> formData = new MultivaluedHashMap<String, String>();
	    formData.add("action", "eliminarFarmacia");
	    formData.add("token", token);
	    formData.add("idFarmacia", idFarmacia);
	    
	    Response response = webTarget.request().post(Entity.form(formData));
	    String resp = response.readEntity(String.class);
	}
	
	public void eliminarPregunta(String token, String idPregunta) {
		WebTarget webTarget = client.target(URL);
	    MultivaluedMap<String, String> formData = new MultivaluedHashMap<String, String>();
	    formData.add("action", "deletePregunta");
	    formData.add("token", token);
	    formData.add("q", idPregunta);
	    
	    Response response = webTarget.request().post(Entity.form(formData));
	    String resp = response.readEntity(String.class);
	}
	
	public void setGerenteFarmacia(String token, String idFarmacia, String username) {
		WebTarget webTarget = client.target(URL);
	    MultivaluedMap<String, String> formData = new MultivaluedHashMap<String, String>();
	    formData.add("action", "setGerente");
	    formData.add("token", token);
	    formData.add("idFarmacia", idFarmacia);
	    
	    Response response = webTarget.request().post(Entity.form(formData));
	    String resp = response.readEntity(String.class);
	}
	
	public void addProductoFarmacia(String token, String idFarmacia, String idProducto, String unidades) {
		WebTarget webTarget = client.target(URL);
	    MultivaluedMap<String, String> formData = new MultivaluedHashMap<String, String>();
	    formData.add("action", "addProductoFarmacia");
	    formData.add("token", token);
	    formData.add("idFarmacia", idFarmacia);
	    formData.add("idProducto", idProducto);
	    formData.add("unidades", unidades);
	    
	    Response response = webTarget.request().post(Entity.form(formData));
	    String resp = response.readEntity(String.class);
	}
	
	
}
