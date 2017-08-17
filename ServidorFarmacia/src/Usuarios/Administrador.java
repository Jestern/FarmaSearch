package Usuarios;

public class Administrador extends Usuario {

	public Administrador(String nombre, String apellidos, String usname, String pass, String email) {
		super(nombre, apellidos, usname, pass, email);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean esGerente() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean esAdministrador() {
		// TODO Auto-generated method stub
		return true;
	}

}
