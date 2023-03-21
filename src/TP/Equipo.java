package TP;

public class Equipo {
	//Atributos
	
	private String nombre;
	private String descripción;
		
		
	//Setters
		
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
		
	public void setNombre(){};
		
	public void setDescripcion(String desc) {
		this.descripción = desc;
	}
		
	public void setDescripcion(){};

	//Getters
		
	public String getNombre() {
		return this.nombre;
	}
		
	public String getDescripcion() {
		return this.descripción;
	}
}
