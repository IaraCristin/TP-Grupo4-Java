package TP;

import TP.EnumResultado.Resultado;

public class Pronostico {
	//Atributos
	
	private Partido partido;
	private Equipo equipo;
	private Resultado resultado;
	
	
	//Setters
	
	public void setPartido(Partido p) {
		this.partido = p;
	}
	
	public void setEquipo(Equipo e) {
		this.equipo = e;
	}
	
	public void setResultado(Resultado res) {
		this.resultado = res;
	}
	
	//Getters
	
	public Partido getPartido() {
		return this.partido;
	}
	
	public Equipo getEquipo() {
		return this.equipo;
	}
	
	public Resultado getResultado() {
		return this.resultado;
	}
	
	//Metodos
	public int puntos() {
		if (resultado == partido.resultado(equipo)) {
			return 1;
		} else {
			return 0;
		}
	}
}
