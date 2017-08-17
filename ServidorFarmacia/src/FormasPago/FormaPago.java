package FormasPago;

public abstract class FormaPago {
	
	private String titular;
	private String numeroCuenta;

	public FormaPago() {
		// TODO Auto-generated constructor stub
	}

	public String getTitular() {
		return titular;
	}

	public void setTitular(String titular) {
		this.titular = titular;
	}

	public String getNumeroCuenta() {
		return numeroCuenta;
	}

	public void setNumeroCuenta(String numeroCuenta) {
		this.numeroCuenta = numeroCuenta;
	}

	
}
