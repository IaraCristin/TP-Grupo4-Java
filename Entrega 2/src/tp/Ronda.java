package tp;

public class Ronda {
		
	//Atributos
	
	private String nro;
	private Partido[] partidos;
		
	//Setters
		
	public void setNro(String nro) {
		this.nro = nro;
	}
		
	public void setPartidos(Partido[] partidos) {
		this.partidos = partidos;
	}
		
		
	//Getters
		
	public String getNro() {
		return this.nro;
	}
		
	public Partido[] getPartidos() {
		return this.partidos;
	}
}
