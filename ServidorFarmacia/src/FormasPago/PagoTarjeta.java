package FormasPago;

public class PagoTarjeta extends FormaPago {
	
	private String titular;
	private String CVV;
	private String numeroTarjeta;

	public PagoTarjeta() {
		// TODO Auto-generated constructor stub
	}

	public String getTitular() {
		return titular;
	}

	public void setTitular(String titular) {
		this.titular = titular;
	}

	public String getCVV() {
		return CVV;
	}

	public void setCVV(String cVV) {
		CVV = cVV;
	}

	public String getNumeroTarjeta() {
		return numeroTarjeta;
	}

	public void setNumeroTarjeta(String numeroTarjeta) {
		this.numeroTarjeta = numeroTarjeta;
	}

}
