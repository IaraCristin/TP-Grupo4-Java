package TP;

public class Pronostico {
	//Atributos
	
	private Partido partido;
	private Equipo equipo;
	private String resultado;
	
	
	//Setters
	
	public void setPartido(Partido p) {
		this.partido = p;
	}
	
	public void setEquipo(Equipo e) {
		this.equipo = e;
	}
	
	public void setResultado(String res) {
		this.resultado = res;
	}
	
	//Getters
	
	public Partido getPartido() {
		return this.partido;
	}
	
	public Equipo getEquipo() {
		return this.equipo;
	}
	
	public String getResultado() {
		return this.resultado;
	}
}
