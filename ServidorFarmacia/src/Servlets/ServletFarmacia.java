package Servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

/**
 * Servlet implementation class ServletFarmacia
 */
@WebServlet("/ServletFarmacia")
public class ServletFarmacia extends HttpServlet {

	private final String URL = "http://localhost:8080/ServidorFarmacia/rest";

	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletFarmacia() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String option = request.getParameter("action");
		response.setContentType("text/json; charset=utf-8");
		PrintWriter out = response.getWriter();
		Client client = ClientBuilder.newClient();
		WebTarget webTarget;
		Response resp;

		try {
			switch(option) {

			case "searchProducto":
				webTarget = client.target(URL).path("/farmacias/productos/buscar/get")
				.queryParam("idFarmacia", request.getParameter("idFarmacia"))
				.queryParam("string", request.getParameter("string"));
				break;

			case "getFAQ":
				webTarget = client.target(URL).path("/preguntas/get");
				break;

			case "getProductosDepartamento":
				webTarget = client.target(URL).path("/farmacias/productos/departamento/get")
				.queryParam("idFarmacia", request.getParameter("idFarmacia"))
				.queryParam("departamento", request.getParameter("departamento"));
				break;

			case "getProductos":
				webTarget = client.target(URL).path("/farmacias/productos/get")
				.queryParam("idFarmacia", request.getParameter("idFarmacia"));
				break;
				
			case "getProductosTodos":
				webTarget = client.target(URL).path("/productos/get");
				break;

			case "getFarmaciasCercanas":
				webTarget = client.target(URL).path("/farmacias/getCercanas")
				.queryParam("latitud", request.getParameter("latitud"))
				.queryParam("longitud", request.getParameter("longitud"));
				break;

			case "getFarmacias":
				webTarget = client.target(URL).path("/farmacias/get");
				break;

			default:
				webTarget = client.target(URL).path("/farmacias/get");
				break;
			}
		} catch(NullPointerException e) {
			webTarget = client.target(URL).path("/farmacias/get");
		}

		resp = webTarget.request().get(Response.class);
		out.print(resp.readEntity(String.class));

		out.flush();
		out.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String option = request.getParameter("action");
		response.setContentType("text/json; charset=utf-8");
		PrintWriter out = response.getWriter();
		Response resp;
		MultivaluedMap<String, String> formData = new MultivaluedHashMap<String, String>();

		try {
			switch(option) {
			
			case "tramitarPedido":
				formData.add("token", request.getParameter("token"));
				formData.add("cesta", request.getParameter("cesta"));
				formData.add("tipo", request.getParameter("tipo"));

				resp = sendDataPost(formData, "/usuarios/facturas/add");
				out.print(resp.readEntity(String.class));
				break;

			case "deletePregunta":
				formData.add("token", request.getParameter("token"));
				formData.add("q", request.getParameter("q"));

				resp = sendDataPost(formData, "/preguntas/delete");
				out.print(resp.readEntity(String.class));
				break;

			case "addPregunta":
				formData.add("token", request.getParameter("token"));
				formData.add("q", request.getParameter("q"));
				formData.add("a", request.getParameter("a"));

				resp = sendDataPost(formData, "/preguntas/add");
				out.print(resp.readEntity(String.class));
				break;

			case "addHorarioFarmacia":
				formData.add("token", request.getParameter("token"));
				formData.add("dia", request.getParameter("dia"));
				formData.add("hApM", request.getParameter("hApM"));
				formData.add("hCiM", request.getParameter("hCiM"));
				formData.add("hApT", request.getParameter("hApT"));
				formData.add("hCiT", request.getParameter("hCiT"));
				formData.add("idFarmacia", request.getParameter("idFarmacia"));

				resp = sendDataPost(formData, "/farmacias/addHorario");
				out.print(resp.readEntity(String.class));
				break;

			case "eliminarAdministrador":
				formData.add("token", request.getParameter("token"));
				formData.add("username", request.getParameter("username"));

				resp = sendDataPost(formData, "/usuarios/deleteAdministrador");
				out.print(resp.readEntity(String.class));
				break;

			case "eliminarGerente":
				formData.add("token", request.getParameter("token"));
				formData.add("username", request.getParameter("username"));

				resp = sendDataPost(formData, "/usuarios/deleteGerente");
				out.print(resp.readEntity(String.class));
				break;

			case "setGerente":
				formData.add("token", request.getParameter("token"));
				formData.add("username", request.getParameter("username"));
				formData.add("idFarmacia", request.getParameter("idFarmacia"));

				resp = sendDataPost(formData, "/farmacias/setGerente");
				out.print(resp.readEntity(String.class));
				break;

			case "addFarmacia":
				formData.add("token", request.getParameter("token"));
				formData.add("name", request.getParameter("name"));
				formData.add("email", request.getParameter("email"));
				formData.add("address", request.getParameter("address"));
				formData.add("phone", request.getParameter("phone"));
				formData.add("latitude", request.getParameter("latitude"));
				formData.add("longitude", request.getParameter("longitude"));

				resp = sendDataPost(formData, "/farmacias/add");
				out.print(resp.readEntity(String.class));
				break;

			case "addAdministrador":
				formData.add("token", request.getParameter("token"));
			    formData.add("username", request.getParameter("username"));

			    resp = sendDataPost(formData, "/usuarios/addAdministrador");
				out.print(resp.readEntity(String.class));
				break;

			case "addGerente":
				formData.add("token", request.getParameter("token"));
			    formData.add("username", request.getParameter("username"));

			    resp = sendDataPost(formData, "/usuarios/addGerente");
				out.print(resp.readEntity(String.class));
				break;

			case "eliminarFarmacia":
				formData.add("token", request.getParameter("token"));
			    formData.add("idFarmacia", request.getParameter("idFarmacia"));

			    resp = sendDataPost(formData, "/farmacias/delete");
				out.print(resp.readEntity(String.class));
				break;

			case "eliminarProducto":
				formData.add("token", request.getParameter("token"));
				formData.add("idProducto", request.getParameter("idProducto"));

				resp = sendDataPost(formData, "/productos/delete");
				out.print(resp.readEntity(String.class));
				break;

			case "eliminarProductoFarmacia":
				formData.add("token", request.getParameter("token"));
				formData.add("idProducto", request.getParameter("idProducto"));
			    formData.add("idFarmacia", request.getParameter("idFarmacia"));

			    resp = sendDataPost(formData, "/farmacias/producto/delete");
				out.print(resp.readEntity(String.class));
				break;

			case "addProducto":
				formData.add("token", request.getParameter("token"));
				formData.add("nombre", request.getParameter("nombre"));
				formData.add("descripcion", request.getParameter("descripcion"));
				formData.add("precio", request.getParameter("precio"));
				formData.add("codigoBarras", request.getParameter("codigoBarras"));
				formData.add("img", request.getParameter("img"));
				formData.add("departamento", request.getParameter("departamento"));

				resp = sendDataPost(formData, "/productos/add");
				out.print(resp.readEntity(String.class));
				break;

			case "getDatosUsuario":
				formData.add("token", request.getParameter("token"));

				resp = sendDataPost(formData, "/usuarios/getDatos");
				out.print(resp.readEntity(String.class));
				break;

			case "addProductoFarmacia":
				formData.add("token", request.getParameter("token"));
			    formData.add("idProducto", request.getParameter("idProducto"));
			    formData.add("idFarmacia", request.getParameter("idFarmacia"));
			    formData.add("unidades", request.getParameter("unidades"));

			    resp = sendDataPost(formData, "/farmacias/producto");
				out.print(resp.readEntity(String.class));
			    break;

			case "modificarUsuario":
			    formData.add("token", request.getParameter("token"));
			    formData.add("name", request.getParameter("name"));
			    formData.add("last", request.getParameter("last"));
			    formData.add("email", request.getParameter("email"));
			    formData.add("pass", request.getParameter("pass"));
			    formData.add("address", request.getParameter("address"));
			    formData.add("phone", request.getParameter("phone"));
			    formData.add("date", request.getParameter("date"));

			    resp = sendDataPost(formData, "/usuarios/modificar");
				out.print(resp.readEntity(String.class));
			    break;

			case "facturasUsuario":
			    formData.add("token", request.getParameter("token"));
			    resp = sendDataPost(formData, "/usuarios/facturas");
				out.print(resp.readEntity(String.class));
			    break;

			case "login":
			    formData.add("user", request.getParameter("user"));
			    formData.add("pass", request.getParameter("pass"));

				resp = sendDataPost(formData, "/usuarios/login");
				out.print(resp.readEntity(String.class));
				break;
				
			case "loginAdmins":
			    formData.add("user", request.getParameter("user"));
			    formData.add("pass", request.getParameter("pass"));

				resp = sendDataPost(formData, "/usuarios/loginAdmins");
				out.print(resp.readEntity(String.class));
				break;

			case "registro":
				formData.add("name", request.getParameter("name"));
			    formData.add("last", request.getParameter("last"));
			    formData.add("user", request.getParameter("user"));
			    formData.add("pass", request.getParameter("pass"));
			    formData.add("email", request.getParameter("email"));

				resp = sendDataPost(formData, "/usuarios/registrar");

				if(resp.getStatus() == 0) {

					// login
					formData.clear();
				    formData.add("user", request.getParameter("user"));
				    formData.add("pass", request.getParameter("pass"));

					resp = sendDataPost(formData, "/usuarios/login");
				}

				out.print(resp.readEntity(String.class));
				break;
			}
		} catch(NullPointerException e) {
			out.print("action");
		}

		out.flush();
		out.close();
	}

	private Response sendDataPost(MultivaluedMap<String, String> formData, String path) {
		Client client = ClientBuilder.newClient();
		WebTarget webTarget = client.target(URL).path(path);
	    Response response = webTarget.request().post(Entity.form(formData));

	    return response;
	}

}
