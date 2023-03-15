package TP;

public class Pronostico {
	
	//Atributos
	
	private Partido partido;
	private Equipo equipo;
	private ResultadoEnum resultado;
	
	
	//Setters
	
	public void setPartido(Partido p) {
		this.partido = p;
	}
	
	public void setEquipo(Equipo e) {
		this.equipo = e;
	}
	
	public void setResultado(ResultadoEnum res) {
		this.resultado = res;
	}
	
	//Getters
	
	public Partido getPartido() {
		return this.partido;
	}
	
	public Equipo getEquipo() {
		return this.equipo;
	}
	
	public ResultadoEnum getResultado() {
		return this.resultado;
	}
}
