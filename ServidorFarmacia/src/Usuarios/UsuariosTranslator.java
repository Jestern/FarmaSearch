package Usuarios;

public abstract class UsuariosTranslator {
	public static Gerente transformToGerente(Usuario u) {
		Gerente g;
		
		if (u instanceof Gerente) {
			g = (Gerente)u;
			
		} else {
			g = new Gerente(u.getNombre(), u.getApellidos(), u.getUsername(), u.getCrypto_pass(), u.getEmail());
			g.setDireccion(u.getDireccion());
			g.setTelefono(u.getTelefono());
			g.setfNacimiento(u.getfNacimiento());
		}
		
		return g;
	}
	
	public static Administrador transformToAdministrador(Usuario u) {
		Administrador a;
		if (u instanceof Administrador) {
			a = (Administrador) u;
		} else {
			a = new Administrador(u.getNombre(), u.getApellidos(), u.getUsername(), u.getCrypto_pass(), u.getEmail());
			a.setDireccion(u.getDireccion());
			a.setTelefono(u.getTelefono());
			a.setfNacimiento(u.getfNacimiento());
		}
		
		return a;
	}
	
	public static Cliente transformToCliente(Usuario u) {
		Cliente c;
		
		if (u instanceof Cliente) {
			c = (Cliente) u;
			
		} else {
			c = new Cliente(u.getNombre(), u.getApellidos(), u.getUsername(), u.getCrypto_pass(), u.getEmail());
			c.setDireccion(u.getDireccion());
			c.setTelefono(u.getTelefono());
			c.setfNacimiento(u.getfNacimiento());
		}
		
		
		return c;
	}
}
