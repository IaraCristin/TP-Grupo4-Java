package TP;


public class Partido {
	
	//Atributos
	
	//No tengo que importar la clase Equipo o Enum porque son parte del mismo paquete
	
	private Equipo equipo1;
	private Equipo equipo2;
	private int golesEquipo1;
	private int golesEquipo2;
	
	//Setters
	
	public void setEquipo1(Equipo e) {
		this.equipo1 = e;
	};
	
	public void setEquipo1() {};
	
	public void setEquipo2(Equipo e) {
		this.equipo2 = e;
	};
	
	public void setEquipo2() {};
	

	public void setGoles1(int goles) {
		this.golesEquipo1 = goles;
	};
	
	public void setGoles1() {};
	
	public void setGoles2(int goles) {
		this.golesEquipo2 = goles;
	};
	
	public void setGoles2() {};
	
	//Getters
	
	public Equipo getEquipo1() {
		return this.equipo1;
	}
	
	public Equipo getEquipo2() {
		return this.equipo2;
	}
	
	public int getGoles1() {
		return this.golesEquipo1;
	}
	
	public int getGoles2() {
		return this.golesEquipo2;
	}
	
	//Métodos
	
		//Ya que estoy guardando el resultado en la descripción del equipo, lo puedo sacar de ahí
	
	//Asumimos que nos dan un equipo que es parte del partido
	public String resultado(Equipo e) {
		if (e.getNombre() == this.equipo1.getNombre()) {
			return this.equipo1.getDescripcion();
		} else {
			return this.equipo2.getDescripcion();
		}
	}
	
	
}
