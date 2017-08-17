package Web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import InformacionFarmacias.Farmacia;
import InformacionFarmacias.Pregunta;
import InformacionFarmacias.Stock;
import Producto.Producto;

/**
 * Servlet implementation class AdministracionWeb
 */
@WebServlet("/Web")
public class AdministracionWeb extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdministracionWeb() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		String page = request.getParameter("page");
		WebStub stub = WebStub.getInstace();
		String token = request.getParameter("token");;
		
		if(page != null && token != null && !token.equals("false")) {
		
			switch (page) {
			case "farmacias":
				List<Farmacia> farmacias = stub.getFarmacias();
				out.print( Web.getFarmaciasAdmin( request.getRequestURL().toString(), token,  farmacias) );
				
				break;
			case "addFarmacias":
				out.print( Web.getAddFarmaciasAdmin( request.getRequestURL().toString(), token ));
				break;
			
			case "addProductoFarmacia":
				out.print( Web.getAddProductoFarmacia( request.getRequestURL().toString(), token ));
				break;
			
			case "addGerenteFarmacia":
				out.print( Web.getSetGerenteFarmacia( request.getRequestURL().toString(), token ));
				break;
				
			case "productos":
				List<Producto> productos = stub.getProductos();
				out.print( Web.getProductos( request.getRequestURL().toString(), token,  productos) );
				break;
				
			case "verProductosFarmacia":
				String idFarmacia = request.getParameter("idFarmacia");
				List<Stock> productosFarmacia = stub.getProductosFarmacia(idFarmacia);
				out.print( Web.getProductosFarmacia( request.getRequestURL().toString(), token, idFarmacia, productosFarmacia) );
				break;
				
			case "addProducto":
				out.print( Web.getAddProductos( request.getRequestURL().toString(), token) );
				break;
				
			case "setGerenteFarmacia":
				out.print( Web.getSetGerenteFarmacia( request.getRequestURL().toString(), token) );
				break;
				
			case "addUsuario":
				out.print( Web.getAddUsuario( request.getRequestURL().toString(), token) );
				break;
				
			case "addPregunta":
				out.print( Web.getAddPreguntas( request.getRequestURL().toString(), token) );
				break;
				
			case "addGerente":
				out.print( Web.getAddGerente( request.getRequestURL().toString(), token) );
				break;
				
			case "addAdmin":
				out.print( Web.getAddAdministrador( request.getRequestURL().toString(), token) );
				break;
				
			case "preguntas":
				List<Pregunta> preguntas = stub.getPreguntas();
				out.print( Web.getPreguntas( request.getRequestURL().toString(), token,  preguntas) );
				break;

			default:
				break;
			}
			
		} else {
			out.print(Web.getLogin(request.getRequestURL().toString()));
		}
		
		
		
		
		out.flush();
		out.close();
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String option = request.getParameter("action");
		String resp;
		String path = "";
		WebStub stub = WebStub.getInstace();
		
		try {
			switch (option) {
			case "login":
				List<String> respuesta = stub.login(request.getParameter("username"), request.getParameter("password"));

				path = "?token=" + respuesta.get(respuesta.size() - 1) + "&page=farmacias";
				
				
				break;
			
			case "addPregunta":
				stub.addPregunta(request.getParameter("token"), request.getParameter("q"), request.getParameter("q"));
				path = "?token=" + request.getParameter("token") + "&page=preguntas";
				break;
				
			case "addFarmacia":
				stub.addFarmacia(request.getParameter("token"), request.getParameter("name"), request.getParameter("email"),
						request.getParameter("phone"), request.getParameter("latitude"), request.getParameter("longitude"),
						request.getParameter("address"));
				path = "?token=" + request.getParameter("token") + "&page=farmacias";
				break;
			case "addProducto":
				stub.addProducto(request.getParameter("token"), request.getParameter("nombre"), request.getParameter("descripcion"),
						request.getParameter("precio"), request.getParameter("codigoBarras"), request.getParameter("img"),
						request.getParameter("departamento"));
				path = "?token=" + request.getParameter("token") + "&page=productos";
				break;
			
			case "addUsuario":
				stub.addUsuario(request.getParameter("token"), request.getParameter("username"), request.getParameter("password"),
						request.getParameter("name"), request.getParameter("last"), request.getParameter("email"));
				path = "?token=" + request.getParameter("token") + "&page=addUsuario";
				break;
				
			case "addGerente":
				stub.addGerente(request.getParameter("token"), request.getParameter("username"));
				path = "?token=" + request.getParameter("token") + "&page=addUsuario";
				break;
				
			case "addAdministrador":
				stub.addAdministrador(request.getParameter("token"), request.getParameter("username"));
				path = "?token=" + request.getParameter("token") + "&page=addUsuario";
				break;
				
			case "eliminarProducto":
				stub.eliminarProducto(request.getParameter("token"), request.getParameter("idProducto"));
				path = "?token=" + request.getParameter("token") + "&page=productos";
				break;
			
			case "eliminarFarmacia":
				stub.eliminarFarmacia(request.getParameter("token"), request.getParameter("idFarmacia"));
				path = "?token=" + request.getParameter("token") + "&page=farmacias";
				break;
				
			case "addProductosFarmacia":
				stub.addProductoFarmacia(request.getParameter("token"), request.getParameter("idFarmacia"),
						request.getParameter("idProducto"), request.getParameter("unidades"));
				path = "?token=" + request.getParameter("token") + "&page=verProductosFarmacia&idFarmacia=" + request.getParameter("idFarmacia");
				break;
				
			case "setGerenteFarmacia":
				stub.eliminarFarmacia(request.getParameter("token"), request.getParameter("idFarmacia"));
				path = "?token=" + request.getParameter("token") + "&page=farmacias";
				break;
				
			case "eliminarPregunta":
				stub.eliminarPregunta(request.getParameter("token"), request.getParameter("idPregunta"));
				path = "?token=" + request.getParameter("token") + "&page=preguntas";
				break;

			default:
				path = "";
				break;
			}
			
			response.sendRedirect(request.getRequestURL().toString() + path);
			
			
		}catch(NullPointerException e) {
			response.sendRedirect(request.getRequestURL().toString());
		}
	}

}
