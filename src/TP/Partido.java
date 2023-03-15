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
	
	public int goles1() {
		return this.golesEquipo1;
	}
	
	public int goles2() {
		return this.golesEquipo2;
	}
	
	//Métodos
	
	//COMPLETAR CUANDO ESTÉ DEFINIDO ResultadoEnum
	
	//Asumimos que nos dan un equipo que está en el partido
//	public ResultadoEnum resultado(Equipo e) {
//		if (e == this.equipo1) {
//			
//			if (this.golesEquipo1 > this.golesEquipo2) {
//				
//				//Completar los campos para usar Enum
//				
//			} else if (this.golesEquipo1 < this.golesEquipo2) {
//				
//			} else {
//				
//			}
//			
//			
//		} else {
//			
//			if (this.golesEquipo2 > this.golesEquipo1) {
//				
//				//Completar los campos para usar Enum
//				
//			} else if (this.golesEquipo2 < this.golesEquipo1) {
//				
//			} else {
//				
//			}
//			
//		}
//	}
	
}
