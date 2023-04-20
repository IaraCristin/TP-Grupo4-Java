package tp;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CargadoDeArchivos {

	public static void main(String[] args) {
		//Nos van a pasar 2 argumentos que nos indican la ruta de los archivos a procesar
		//Asumimos que nos pasan primero el de resultado y luego el del pronóstico
		String resultados = args[0];
		String pronostico = args[1];
		
		Path rutaRes;
		Path rutaProc;
		
		//Acá vamos a guardar las lineas que consigamos leer de los archivos
		List<String> lineasRes = null;
		List<String> lineasProc = null;
		
		
		//Primero vemos si las rutas que nos dieron son válidas
		//Pido un try para cada una para saber específicamente qué ruta no funciona
		try {
			rutaRes = Paths.get(resultados);
			lineasRes = Files.readAllLines(rutaRes);			
		} catch (IOException e){
			System.out.println("Ruta de resultado no válida");
			System.exit(1);	//Aviso de error
		}
		
		
		try {
			rutaProc = Paths.get(pronostico);
			lineasProc = Files.readAllLines(rutaProc);	
		} catch (IOException e){
			System.out.println("Ruta de pronóstico no válida");
			System.exit(1); //Aviso de error
		}
		
		//Supongamos que el orden de los datos es igual al moestrado en los ejemplos del PDF
		//También asumimos que los archivos son archivos .cvs, esto será usado para separar los datos de los archivos
		
		//Una vez cargados los archivos, los procesamos
		
		/* Antes de seguir, necesitamos "limpiar" los array de lineas, o sea, 
		sacarle a cada una la primer linea que indica solo características
		Ej: en resultados hay una linea que dice Equipo 1;Goles Equipo 1;Goles Equipo 2;Equipo 2
		*/
		
		lineasRes.remove(0);
		lineasProc.remove(0);
		
		
		//Armamos las rondas primero
		Ronda[] rondas = armarRondas(lineasRes);
		
		//Armamos un diccionario que tenga de clave los nombres de las personas y de valor sus pronosticos
		Map<String,Pronostico[]> personas = new HashMap<String,Pronostico[]>();
		
		//Registramos a las personas en el diccionario
		registrarPersonas(lineasProc,personas,rondas);
		
		//Una vez tenemos a cada persona con sus respectivos pronósticos, calculamos los puntos
		for(String persona: personas.keySet()) {
			System.out.println(persona + " acertó: " + calcularPuntos(personas.get(persona)));
		}
		
		
		
	}
	




	//Set es conjunto, dentro de un conjunto no se repiten los elementos, entonces aunque agreguemos 2 veces el mismo equipo, no se repite
	//Sabemos que le estamos pasando las lineas del archivo resultados
	private static Set<Equipo> equipos(List<String> lineasRes) {
		Set<Equipo> equipos = new HashSet<Equipo>();
		
		for(String linea : lineasRes) {
			String[] celdas = linea.split(",");
			Equipo equipo1 = new Equipo();
			Equipo equipo2 = new Equipo();
			//Sabemos que hay un equipo en la celda 0 y la 3
			equipo1.setNombre(celdas[1].toUpperCase());	//Conviento los nombres a todo mayúsculas para tratar de salvar los casos donde escriban distinto el mismo país
			equipo2.setNombre(celdas[4].toUpperCase());
			
			//Agregamos los equipos al conjunto
			equipos.add(equipo1);
			equipos.add(equipo2);
			
		}
		
		return equipos;
	}
	
	//Armamos los partidos
	private static Partido[] partidos(List<String> lineasRes, Set<Equipo> equipos, String nroRonda) {
		//La cantidad de lineas es igual a la cantidad de partidos jugados
		//Como es un array tenemos que inicializarlo con el tamaño adecuado, ya que este no se puede cambiar
		Partido[] partidos = new Partido[lineasRes.size()]; 
		int i = 0; //Index para recorrer el array
		
		for(String linea: lineasRes) {
			String[] celdas = linea.split(",");
			
			//Nos fijamos si el partido que estamos viendo ahora es parte de la ronda que queremos
			if (nroRonda.equals(celdas[0])) {
				
				Partido partido = new Partido();
				//Ya tenemos instanciados los equipos, así que buscamos entre los equipos "en juego" y los guardamos en el partido
				for(Equipo e: equipos) {
					//Comparamos primero el equipo 1
					if (e.getNombre().equals(celdas[1].toUpperCase()) ){
						//Si entra coincide el equipo instanciado con el equipo jugando
						partido.setEquipo1(e);
						partido.setGoles1(Integer.parseInt(celdas[2]));
						
					} else if (e.getNombre().equals(celdas[4].toUpperCase()) ) {
						partido.setEquipo2(e);
						partido.setGoles2(Integer.parseInt(celdas[3]));
					}
				}
				
				partidos[i] = partido;
				i++;
			}
			
		}
		
		return partidos;
		
	}
	
	
	//Armamos los pronosticos COMPLETAR PARA QUE USE RONDAS Y NOMBRE DE PERSONA
	private static Pronostico[] pronosticos(List<String> lineasProc, Ronda[] rondas, String persona) {
		//La cantidad de lineas es igual a la cantidad de pronosticos hechos
		//Como es un array tenemos que inicializarlo con el tamaño adecuado, ya que este no se puede cambiar
		
		
		Pronostico[] pronosticos = new Pronostico[contarPronosticosPersona(lineasProc,persona)];
		int i = 0; //Index para recorrer el array
		
		//Esto es lo que había antes pero quizás haya que cambiar la lógica
		
		for(String linea: lineasProc) {
			String[] celdas = linea.split(",");
			
			//Tenemos la linea pero necesitamos asegurarnos que sea de la persona que nos interesa
			
			if (persona.equals(celdas[0])) {
				
				//Una vez confirmado eso, armamos un pronóstico
				Pronostico pronostico = new Pronostico();
				
				//Tenemos que fijarnos en cada ronda
				for(Ronda ronda: rondas) {
					Partido[] partidos = ronda.getPartidos();
				
					//Nos fijamos de qué partido habla el pronóstico
					for(Partido p: partidos) {
						//Se ve feo pero lo que hace es comparar los equipos del partido para ver si son los mismos del pronostico
						if(p.getEquipo1().getNombre().equals(celdas[1].toUpperCase()) && p.getEquipo2().getNombre().equals(celdas[5].toUpperCase())) {
							pronostico.setPartido(p);
							pronostico.setEquipo(equipoPredicho(celdas,p));
							pronostico.setResultado(resultadoPredicho(celdas));
						}
					}
				}
				
				//Ya teniendo el pronostico armado lo agregamos al arreglo
				pronosticos[i] = pronostico;
				i++;
				
			}
			
		}
		
		return pronosticos;
	}
	
	private static Equipo equipoPredicho(String[] celdas, Partido p) {
		if(celdas[2].equals("X") || celdas[3].equals("X")) {
			return p.getEquipo1();
		} else {
			return p.getEquipo2();
		}
	}
	
	private static EnumResultado.Resultado resultadoPredicho(String[] celdas){
		if (celdas[2].equals("X") || celdas[4].equals("X")) {
			return EnumResultado.Resultado.GANADOR;
		} else  {
			return EnumResultado.Resultado.EMPATE;
		}
		
	}
	
	
	private static Ronda[] armarRondas (List<String> lineasRes) {
		
		Ronda[] rondas = new Ronda[contarRondas(lineasRes)];
		

		//Buscamos los equipos
		Set<Equipo> equipos = equipos(lineasRes);
		
		//Le ponemos su numero correspondiente a cada ronda y le agregamos los partidos
		for(int i = 0; i < rondas.length; i++) {
			rondas[i].setNro(String.valueOf(i+1));
			rondas[i].setPartidos(partidos(lineasRes,equipos,String.valueOf(i+1)));
			
		}
		
		
		return rondas;
	}
	
	private static int contarRondas (List<String> lineasRes) {
		int res = 0;
		
		for(String linea: lineasRes) {
			String[] celda = linea.split(",");
			if (Integer.parseInt(celda[0]) > res ) {
				res = Integer.parseInt(celda[0]);
			}
		}
		
		return res;
	}
	
	
	private static void registrarPersonas(List<String> lineasProc, Map<String, Pronostico[]> personas, Ronda[] rondas) {
		
		for (String linea: lineasProc) {
			String[] celda = linea.split(",");
			
			if (!personas.containsKey(celda[0])) {
				personas.put(celda[0], pronosticos(lineasProc,rondas,celda[0]));
			}
			
		}
		
	}
	
	private static int contarPronosticosPersona (List<String> lineasProc, String persona) {
		int res = 0;
		
		for(String linea: lineasProc) {
			String[] celdas = linea.split(",");
			
			if (persona.equals(celdas[0])) {
				res++;
			}
		}
		
		return res;
	}
	
	
	private static int calcularPuntos(Pronostico[] pronosticos) {
		int res = 0;
		
		for(Pronostico proc: pronosticos) {
			res += proc.puntos();
		}

		return res;
	}
}
