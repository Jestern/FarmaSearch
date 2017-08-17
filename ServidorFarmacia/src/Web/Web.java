package Web;

import java.util.List;

import InformacionFarmacias.Farmacia;
import InformacionFarmacias.Pregunta;
import InformacionFarmacias.Stock;
import Producto.Departamento;
import Producto.Producto;

public abstract class Web {
	
	private static String URL_IMGS = "http://52.51.20.39";
	
	private static String getHead() {
		String head = 
				"<!doctype html>"
				+ "<html>"
				+ "<head>"
				+ "<meta charset=\"utf-8\">"
				+ "<title>Farmacias App - Panel de administracion</title>"
				+ getStyle()
				+ "</head>"
				+ "<body>"
					+ "<header>"
					+ "<h1>Farmacias App - Panel de administracion</h1>"
					+ "</header>";
		
		return head;
	}
	
	private static String getStyle() {
		String style = "<style>"
				+ "@charset \"utf-8\";"
				+ "aside {"
					+ "width: 200px;"
					+ "border:#6A6A6A solid thin;"
					+ "float:left;"
					+ "padding-right: 10px;"
				+ "}"
				+ ".farmacia {"
					+ "margin-left: 220px;"
					+ "padding-left: 10px;"
					+ "border:#737373 solid thin;"
				+ "}"
				+ "section {"
				+ "}"
				+ "h2 {"
				+ "margin-left: 220px;"
				+ "}"
				+ "header {"
					+ "background: #87E6FF;"
				+ "}"
				+ ".login {"
					+ "text-align:center;"
				+ "}"
				+ "form#login {"
					+ "border:thin solid #989898;"
					+ "width:200px;"
					+ "height:200px;"
				+ "}"
				+ ".botones form {"
					+ "clear: none;"
					+ "display: inline-block;"
				+ "}"
				+ "</style>";
		
		return style;
	}
	
	private static String getSectionLogin(String context) {
		String section = "<section>"
				+ "<div class=\"login\">"
				+ "<p>"
					+ "<form method=\"post\">"
					+ "<label>Usuario</label><br>"
					+ "<input name=\"username\" type=\"text\" id=\"username\"></input>"
					+ "<br><br>"
					+ "<label>Contraseña</label><br>"
					+ "<input name=\"password\" type=\"password\" id=\"password\"></input>"
					+ "<br><br>"
					+ "<div class=\"botones\">"
					+ "<input type=\"hidden\" name=\"action\" id=\"hiddenField\" value=\"login\">"
					+ "<input type=\"submit\" name=\"submit\" id=\"submit\" value=\"Iniciar sesión\">"
					+ "</div>"
					+ "</form>"
				+ "</p>"
				+ "</div>"
				+ "</section>";
		
		return section;
	}
	
	private static String getSectionAddFarmacia(String token) {
		String section = "<section>"
				+ "<h2>Añadir Farmacia</h2>"
				+ "<div class=\"farmacia\">"
				+ "<p>"
				+ "<form method=\"post\">"
				+ "<input type=\"hidden\" name=\"action\" id=\"hiddenField\" value=\"addFarmacia\">"
				+ "<input type=\"hidden\" name=\"token\" id=\"hiddenField\" value=\"" + token + "\">"
				+ "<label>Nombre<br></label><input type=\"text\" name=\"name\">"
				+ "<label><br><br>E-mail<br></label><input type=\"text\" name=\"email\">"
				+ "<label><br><br>Teléfono<br></label><input type=\"text\" name=\"phone\">"
				+ "<label><br><br>Latitud<br></label><input type=\"text\" name=\"latitude\">"
				+ "<label><br><br>Longitud<br></label><input type=\"text\" name=\"longitude\">"
				+ "<label><br><br>Dirección<br></label><textarea name=\"address\"></textarea>"
				+ "<br><br>"
				+ "<input type=\"submit\" value=\"Enviar\"></form>"
				+ "</p></div>"
				+ "</section>";
		
		return section;
	}
	
	private static String getSectionAddUsuario(String token) {
		String section = "<section>"
				+ "<h2>Añadir Usuario</h2>"
				+ "<div class=\"farmacia\">"
				+ "<p>"
				+ "<form method=\"post\">"
				+ "<input type=\"hidden\" name=\"action\" id=\"hiddenField\" value=\"addUsuario\">"
				+ "<input type=\"hidden\" name=\"token\" id=\"hiddenField\" value=\"" + token + "\">"
				+ "<label>Usuario<br></label><input type=\"text\" name=\"username\">"
				+ "<label><br><br>Contraseña<br></label><input type=\"password\" name=\"password\">"
				+ "<label><br><br>Nombre<br></label><input type=\"text\" name=\"name\">"
				+ "<label><br><br>Apellidos<br></label><input type=\"text\" name=\"last\">"
				+ "<label><br><br>E-mail<br></label><input type=\"text\" name=\"email\">"
				+ "<br><br>"
				+ "<input type=\"submit\" value=\"Enviar\"></form>"
				+ "</p></div>"
				+ "</section>";
		
		return section;
	}
	
	private static String getSectionAddGerente(String token) {
		String section = "<section>"
				+ "<h2>Añadir Gerente</h2>"
				+ "<div class=\"farmacia\">"
				+ "<p>"
				+ "<form method=\"post\">"
				+ "<input type=\"hidden\" name=\"action\" id=\"hiddenField\" value=\"addGerente\">"
				+ "<input type=\"hidden\" name=\"token\" id=\"hiddenField\" value=\"" + token + "\">"
				+ "<label>Nombre de usuario<br></label><input type=\"text\" name=\"username\">"
				+ "<br><br>"
				+ "<input type=\"submit\" value=\"Enviar\"></form>"
				+ "</p></div>"
				+ "</section>";
		
		return section;
	}
	
	private static String getSectionAddAdministrador(String token) {
		String section = "<section>"
				+ "<h2>Añadir Administrador</h2>"
				+ "<div class=\"farmacia\">"
				+ "<p>"
				+ "<form method=\"post\">"
				+ "<input type=\"hidden\" name=\"action\" id=\"hiddenField\" value=\"addAdministrador\">"
				+ "<input type=\"hidden\" name=\"token\" id=\"hiddenField\" value=\"" + token + "\">"
				+ "<label>Nombre de usuario<br></label><input type=\"text\" name=\"username\">"
				+ "<br><br>"
				+ "<input type=\"submit\" value=\"Enviar\"></form>"
				+ "</p></div>"
				+ "</section>";
		
		return section;
	}
	
	private static String getSectionSetGerenteFarmacia(String token) {
		String section = "<section>"
				+ "<h2>Añadir producto a farmacia</h2>"
				+ "<div class=\"farmacia\">"
				+ "<p>"
				+ "<form method=\"post\">"
					+ "<input type=\"hidden\" name=\"action\" id=\"hiddenField\" value=\"setGerenteFarmacia\">"
					+ "<input type=\"hidden\" name=\"token\" id=\"hiddenField\" value=\"" + token + "\">"
					+ "<label>ID farmacia<br></label><input type=\"text\" name=\"idFarmacia\">"
					+ "<label><br><br>Nombre de usuario<br></label><input type=\"text\" name=\"username\">"
				+ "<br><br>"
				+ "<input type=\"submit\" value=\"Enviar\"></form>"
				+ "</p></div>"
				+ "</section>";
		
		return section;
	}
	
	private static String getSectionAddProductoFarmacia(String token) {
		String section = "<section>"
				+ "<h2>Añadir producto a farmacia</h2>"
				+ "<div class=\"farmacia\">"
				+ "<p>"
				+ "<form method=\"post\">"
					+ "<input type=\"hidden\" name=\"action\" id=\"hiddenField\" value=\"addProductosFarmacia\">"
					+ "<input type=\"hidden\" name=\"token\" id=\"hiddenField\" value=\"" + token + "\">"
					+ "<label>ID farmacia<br></label><input type=\"text\" name=\"idFarmacia\">"
					+ "<label><br><br>ID producto<br></label><input type=\"text\" name=\"idProducto\">"
					+ "<label><br><br>Unidades<br></label><input type=\"text\" name=\"unidades\">"
				+ "<br><br>"
				+ "<input type=\"submit\" value=\"Enviar\"></form>"
				+ "</p></div>"
				+ "</section>";
		
		return section;
	}
	
	private static String getSectionAddPregunta(String token) {
		String section = "<section>"
				+ "<h2>Añadir Pregunta</h2>"
				+ "<div class=\"farmacia\">"
				+ "<p>"
				+ "<form method=\"post\">"
				+ "<input type=\"hidden\" name=\"action\" id=\"hiddenField\" value=\"addPregunta\">"
				+ "<input type=\"hidden\" name=\"token\" id=\"hiddenField\" value=\"" + token + "\">"
				+ "<label>Pregunta<br></label><textarea name=\"q\"></textarea>"
				+ "<label><br><br>Respuesta<br></label><textarea name=\"a\"></textarea>"
				+ "<br><br>"
				+ "<input type=\"submit\" value=\"Enviar\"></form>"
				+ "</p></div>"
				+ "</section>";
		
		return section;
	}
	
	private static String getSectionAddProducto(String token) {
		String section = "<section>"
				+ "<h2>Añadir Producto</h2>"
				+ "<div class=\"farmacia\">"
				+ "<p>"
				+ "<form method=\"post\">"
				+ "<input type=\"hidden\" name=\"action\" id=\"hiddenField\" value=\"addProducto\">"
				+ "<input type=\"hidden\" name=\"token\" id=\"hiddenField\" value=\"" + token + "\">"
				+ "<label>Nombre<br></label><input type=\"text\" name=\"nombre\">"
				+ "<label><br><br>Descripción<br></label><input type=\"text\" name=\"descripcion\">"
				+ "<label><br><br>Precio<br></label><input type=\"text\" name=\"precio\">"
				+ "<label><br><br>Código de barras<br></label><input type=\"text\" name=\"codigoBarras\">"
				+ "<label><br><br>Url de la imagen<br></label><input type=\"text\" name=\"img\">"
				+ "<label><br><br>Departamento<br></label>"
				+ "<select name=\"departamento\">";
		for(Departamento d : Departamento.values()) {
			section += "<option value=\"" + d.toString() +"\">" + d.toString() + "</option>";
		}
				section += "</select>"
				+"<br><br>"
				+ "<input type=\"submit\" value=\"Enviar\"></form>"
				+ "</p></div>"
				+ "</section>";
		
		return section;
	}
	
	private static String getFooter() {
		String footer = "</body>"
					+ "</html>";
		
		return footer;
	}
	
	private static String getSideAdmin(String context, String token) {
		String side = "<aside>"
				+ "<ul>"
				+ "<li><a href=\"" + context + "?token=" + token + "&mode=admin&page=farmacias" + "\">Farmacias</a></li>"
				+ "<li><a href=\"" + context + "?token=" + token + "&mode=admin&page=setGerenteFarmacia" + "\">Asignar gerente farmacia</a></li>"
				+ "<li><a href=\"" + context + "?token=" + token + "&mode=admin&page=addFarmacias" + "\">Añadir Farmacia</a></li>"
				+ "<li><a href=\"" + context + "?token=" + token + "&mode=admin&page=productos" + "\">Ver productos</a></li>"
				+ "<li><a href=\"" + context + "?token=" + token + "&mode=admin&page=addProductoFarmacia" + "\">Añadir producto a farmacia</a></li>"
				+ "<li><a href=\"" + context + "?token=" + token + "&mode=admin&page=addProducto" + "\">Añadir producto</a></li>"
				+ "<li><a href=\"" + context + "?token=" + token + "&mode=admin&page=preguntas" + "\">Ver preguntas</a></li>"
				+ "<li><a href=\"" + context + "?token=" + token + "&mode=admin&page=addPregunta" + "\">Añadir pregunta</a></li>"
				+ "<li><a href=\"" + context + "?token=" + token + "&mode=admin&page=addUsuario" + "\">Registrar usuario</a></li>"
				+ "<li><a href=\"" + context + "?token=" + token + "&mode=admin&page=addGerente" + "\">Añadir gerente</a></li>"
				+ "<li><a href=\"" + context + "?token=" + token + "&mode=admin&page=addAdmin" + "\">Añadir administrador</a></li>"
				+ "</ul>"
				+ "</aside>";
		
		return side;
	}
	
	private static String getSectionProductos(String baseUrl, String token, List<Producto> productos) {
		String section = "<section>"
				+ "<h2>Productos</h2>";
		
		for(Producto p : productos) {
			section += "<div class=\"farmacia\">"
					+ "<p>"
					+ "<img src=\"" + baseUrl + p.getImg() +  "\" witdth=\"200px\" height=\"200px\"><br>"
					+ "ID Producto: " + p.getId() + "<br>"
					+ "Nombre: " + p.getNombre() + "<br>"
					+ "Descripción: " + p.getDescripcion() + "<br>"
					+ "Precio: " + p.getPrecio() + " €<br>"
					+ "Código de barras: " + p.getCodigoBarras() + "<br>"
					+ "<div class=\"botones\">"
					+ "<form method=\"post\">"
						+ "<input type=\"hidden\" name=\"action\" id=\"hiddenField\" value=\"eliminarProducto\">"
						+ "<input type=\"hidden\" name=\"idProducto\" id=\"hiddenField\" value=\"" + p.getId() + "\">"
						+ "<input type=\"hidden\" name=\"token\" id=\"hiddenField\" value=\"" + token + "\">"
						+ "<input type=\"submit\" value=\"Eliminar\">"
					+ "</form>"
					+ "</div>"
					+ "</p>"
					+ "</div>";
					
		}
		
		section += "</section>";
		
		return section;
	}
	
	private static String getSectionProductosFarmacia(String baseUrl, String token, String idFarmacia, List<Stock> productos) {
		String section = "<section>"
				+ "<h2>Productos</h2>";
		
		for(Stock p : productos) {
			section += "<div class=\"farmacia\">"
					+ "<p>"
					+ "<img src=\"" + baseUrl + p.getProducto().getImg() +  "\" witdth=\"200px\" height=\"200px\"><br>"
					+ "ID Producto: " + p.getProducto().getId() + "<br>"
					+ "Nombre: " + p.getProducto().getNombre() + "<br>"
					+ "Descripción: " + p.getProducto().getDescripcion() + "<br>"
					+ "Precio: " + p.getProducto().getPrecio() + " €<br>"
					+ "Código de barras: " + p.getProducto().getCodigoBarras() + "<br>"
					+ "Unidades: " + p.getUnidades() + "<br>"
					+ "<div class=\"botones\">"
					+ "<form method=\"post\">"
						+ "<input type=\"hidden\" name=\"action\" id=\"hiddenField\" value=\"eliminarProductoFarmacia\">"
						+ "<input type=\"hidden\" name=\"idFarmacia\" id=\"hiddenField\" value=\"" + idFarmacia + "\">"
						+ "<input type=\"hidden\" name=\"idProducto\" id=\"hiddenField\" value=\"" + p.getProducto().getId() + "\">"
						+ "<input type=\"hidden\" name=\"token\" id=\"hiddenField\" value=\"" + token + "\">"
						+ "<input type=\"submit\" value=\"Eliminar de farmacia\">"
					+ "</form>"
					+ "</div>"
					+ "</p>"
					+ "</div>";
					
		}
		
		section += "</section>";
		
		return section;
	}
	
	private static String getSectionFarmaciasAdmin(String token, List<Farmacia> farmacias) {
		String section = "<section>"
				+ "<h2>Farmacias</h2>";
		
		for(Farmacia f : farmacias) {
			section += "<div class=\"farmacia\">"
					+ "<p>"
					+ "ID Farmacia: " + f.getIdFarmacia() + "<br>"
					+ "Nombre: " + f.getNombre() + "<br>"
					+ "E-mail: " + f.getEmail() + "<br>"
					+ "Dirección: " + f.getDireccion() + "<br>"
					+ "Teléfono: " + f.getTelefono() + "<br>"
					+ "Latitud: " + f.getLatitud() + " | Longitud: " + f.getLongitud() + "<br><br>"
					+ "<div class=\"botones\">"
					+ "<form method=\"post\">"
							+ "<input type=\"hidden\" name=\"action\" id=\"hiddenField\" value=\"eliminarFarmacia\">"
							+ "<input type=\"hidden\" name=\"idFarmacia\" id=\"hiddenField\" value=\"" + f.getIdFarmacia() + "\">"
									+ "<input type=\"hidden\" name=\"token\" id=\"hiddenField\" value=\"" + token + "\">"
							+ "<input type=\"submit\" value=\"Eliminar Farmacia\">"
					+ "</form>"
					+ "<form method=\"get\">"
						+ "<input type=\"hidden\" name=\"page\" id=\"hiddenField\" value=\"verProductosFarmacia\">"
						+ "<input type=\"hidden\" name=\"idFarmacia\" id=\"hiddenField\" value=\"" + f.getIdFarmacia() + "\">"
						+ "<input type=\"hidden\" name=\"token\" id=\"hiddenField\" value=\"" + token + "\">"
						+ "<input type=\"submit\" value=\"Ver productos\">"
					+ "</form>"
					/*+ "<form method=\"post\">"
						+ "<input type=\"hidden\" name=\"action\" id=\"hiddenField\" value=\"addProducto\">"
						+ "<input type=\"hidden\" name=\"idFarmacia\" id=\"hiddenField\" value=\"" + f.getIdFarmacia() + "\">"
						+ "<input type=\"hidden\" name=\"token\" id=\"hiddenField\" value=\"" + token + "\">"
						+ "<input type=\"submit\" value=\"Añadir producto\">"
					+ "</form>"
					+ "<form method=\"post\">"
						+ "<input type=\"hidden\" name=\"action\" id=\"hiddenField\" value=\"setGerente\">"
						+ "<input type=\"hidden\" name=\"idFarmacia\" id=\"hiddenField\" value=\"" + f.getIdFarmacia() + "\">"
						+ "<input type=\"hidden\" name=\"token\" id=\"hiddenField\" value=\"" + token + "\">"
						+ "<input type=\"submit\" value=\"Poner Gerente\">"
					+ "</form>"*/
					+ "</div>"
					+ "</p>"
					+ "</div>";
		}
		
		section += "</section>";
		
		return section;
	}
	
	private static String getSectionPreguntas(String token, List<Pregunta> preguntas) {
		String section = "<section>"
				+ "<h2>Preguntas</h2>";
		
		for(Pregunta p : preguntas) {
			section += "<div class=\"farmacia\">"
					+ " <p>"
					+ "<span class=\"resaltar\">Pregunta: </span>" + p.getPregunta() + "<br><br>"
					+ "Respuesta: " + p.getRespuesta() + "<br><br>"
					+ "<form method=\"post\">"
					+ "<input type=\"hidden\" name=\"action\" id=\"hiddenField\" value=\"eliminarPregunta\">"
						+ "<input type=\"hidden\" name=\"idPregunta\" id=\"hiddenField\" value=\"" + p.getId() + "\">"
						+ "<input type=\"hidden\" name=\"token\" id=\"hiddenField\" value=\"" + token + "\">"
						+ "<input type=\"submit\" value=\"Eliminar\">"
					+ "</form>"
					+ "</p>"
					+ "</div>";
		}
		
		section += "</section>";
		
		return section;
	}
	
	public static String getLogin(String context) {
		String html = getHead()
				+ getSectionLogin(context)
				+ getFooter();
		
		return html;
	}
	
	public static String getFarmaciasAdmin(String context, String token, List<Farmacia> farmacias) {
		String html = getHead() + getSideAdmin(context, token) + getSectionFarmaciasAdmin(token, farmacias) + getFooter();
		
		return html;
	}
	
	public static String getAddFarmaciasAdmin(String context, String token) {
		String html = getHead() + getSideAdmin(context, token) + getSectionAddFarmacia(token) + getFooter();
		
		return html;
	}
	
	public static String getAddProductos(String context, String token) {
		String html = getHead() + getSideAdmin(context, token) + getSectionAddProducto(token) + getFooter();
		
		return html;
	}
	
	public static String getAddUsuario(String context, String token) {
		String html = getHead() + getSideAdmin(context, token) + getSectionAddUsuario(token) + getFooter();
		
		return html;
	}
	
	public static String getProductos(String context, String token, List<Producto> productos) {
		//String baseUrl = context.substring(0, context.indexOf("/", 9));
		
		String html = getHead() + getSideAdmin(context, token) + getSectionProductos(URL_IMGS, token, productos) + getFooter();
		
		return html;
	}
	
	public static String getProductosFarmacia(String context, String token, String idFarmacia, List<Stock> productos) {
		//String baseUrl = context.substring(0, context.indexOf("/", 9));
		
		String html = getHead() + getSideAdmin(context, token) + getSectionProductosFarmacia(URL_IMGS, token, idFarmacia, productos) + getFooter();
		
		return html;
	}
	
	public static String getPreguntas(String context, String token, List<Pregunta> preguntas) {
		String html = getHead() + getSideAdmin(context, token) + getSectionPreguntas(token, preguntas) + getFooter();
		
		return html;
	}
	
	public static String getAddPreguntas(String context, String token) {
		String html = getHead() + getSideAdmin(context, token) + getSectionAddPregunta(token) + getFooter();
		
		return html;
	}
	
	public static String getAddProductoFarmacia(String context, String token) {
		String html = getHead() + getSideAdmin(context, token) + getSectionAddProductoFarmacia(token) + getFooter();
		
		return html;
	}
	
	public static String getSetGerenteFarmacia(String context, String token) {
		String html = getHead() + getSideAdmin(context, token) + getSectionSetGerenteFarmacia(token) + getFooter();
		
		return html;
	}
	
	public static String getAddGerente(String context, String token) {
		String html = getHead() + getSideAdmin(context, token) + getSectionAddGerente(token) + getFooter();
		
		return html;
	}
	
	public static String getAddAdministrador(String context, String token) {
		String html = getHead() + getSideAdmin(context, token) + getSectionAddAdministrador(token) + getFooter();
		
		return html;
	}
}
