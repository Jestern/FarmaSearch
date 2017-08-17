package Usuarios;

import java.util.ArrayList;

import Pedidos.Factura;

public class Cliente extends Usuario {

	public Cliente(String nombre, String apellidos, String usname, String pass, String email) {
		super(nombre, apellidos, usname, pass, email);
	}

	@Override
	public boolean esGerente() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean esAdministrador() {
		// TODO Auto-generated method stub
		return false;
	}

}
