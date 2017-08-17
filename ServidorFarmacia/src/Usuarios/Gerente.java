package Usuarios;

import InformacionFarmacias.Farmacia;

public class Gerente extends Usuario {
	
	private Farmacia farmacia = null;

	public Gerente(String nombre, String apellidos, String usname, String pass, String email) {
		super(nombre, apellidos, usname, pass, email);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean esGerente() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean esAdministrador() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public void setFarmacia(Farmacia f){
		this.farmacia = f;
	}

}
