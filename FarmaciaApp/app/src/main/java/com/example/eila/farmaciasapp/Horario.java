package com.example.eila.farmaciasapp;

public class Horario {
	
	private String diaSemana;
	private String horaAperturaManiana;
	private String horaCierreManiana;
	private String horaAperturaTarde;
	private String horaCierreTarde;

	public Horario(String d, String hAM, String hCM, String hAT, String hCT) {
		diaSemana = d;
		horaAperturaManiana = hAM;
		horaCierreManiana = hCM;
		horaAperturaTarde = hAT;
		horaCierreTarde = hCT;
	}

	public String getDiaSemana() {
		return diaSemana;
	}

	public void setDiaSemana(String diaSemana) {
		this.diaSemana = diaSemana;
	}

	public String getHoraAperturaManiana() {
		return horaAperturaManiana;
	}

	public void setHoraAperturaManiana(String horaAperturaManiana) {
		this.horaAperturaManiana = horaAperturaManiana;
	}

	public String getHoraCierreManiana() {
		return horaCierreManiana;
	}

	public void setHoraCierreManiana(String horaCierreManiana) {
		this.horaCierreManiana = horaCierreManiana;
	}

	public String getHoraAperturaTarde() {
		return horaAperturaTarde;
	}

	public void setHoraAperturaTarde(String horaAperturaTarde) {
		this.horaAperturaTarde = horaAperturaTarde;
	}

	public String getHoraCierreTarde() {
		return horaCierreTarde;
	}

	public void setHoraCierreTarde(String horaCierreTarde) {
		this.horaCierreTarde = horaCierreTarde;
	}
	public String toString(){
		return (diaSemana + " Mañana: " + horaAperturaManiana + "-" + horaCierreManiana + " Tarde: " + horaAperturaTarde + "-" + horaCierreTarde);
	}
	
	

}
