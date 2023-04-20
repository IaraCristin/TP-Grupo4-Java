package tp;

public class Partido {
	
	//Atributos
	
	private Equipo equipo1;
	private Equipo equipo2;
	private int golesEquipo1;
	private int golesEquipo2;
	
	//Setters
	
	public void setEquipo1(Equipo e) {
		this.equipo1 = e;
	};
	
	public void setEquipo2(Equipo e) {
		this.equipo2 = e;
	};
		

	public void setGoles1(int goles) {
		this.golesEquipo1 = goles;
	};
	
	
	public void setGoles2(int goles) {
		this.golesEquipo2 = goles;
	};
	
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
	
	
	public EnumResultado.Resultado resultado(Equipo e) {
		if (e.getNombre() == this.equipo1.getNombre()) {
			
			if(this.golesEquipo1 > this.golesEquipo2) {
				return EnumResultado.Resultado.GANADOR;
			} else if(this.golesEquipo1 == this.golesEquipo2) {
				return EnumResultado.Resultado.EMPATE;
			} else {
				return EnumResultado.Resultado.PERDEDOR;
			}
			
		} else {
			if(this.golesEquipo2 > this.golesEquipo1) {
				return EnumResultado.Resultado.GANADOR;
			} else if(this.golesEquipo2 == this.golesEquipo1) {
				return EnumResultado.Resultado.EMPATE;
			} else {
				return EnumResultado.Resultado.PERDEDOR;
			}
		}
	}
}
