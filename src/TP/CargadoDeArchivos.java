package TP;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import TP.EnumResultado.Resultado;

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
		
		//Vemos qué equipos están en el torneo y los instanciamos
		Set<Equipo> equipos = equipos(lineasRes);
		
		//Luego con esto queremos armar los partidos
		Partido[] partidos = partidos(lineasRes,equipos);
		
		Ronda ronda1 = new Ronda();
		
		//Por ahora, como sabemos que es una sola ronda, podemos ponerlo ahora
		ronda1.setNro("1");
		ronda1.setPartidos(partidos);
		
		
		//Ahora tenemos que armar los pronósticos
		Pronostico[] pronosticos = pronosticos(lineasProc,partidos); 
		
		//Ahora que tenemos todo cargado en los objetos, sumamos los puntos
		
		
		//Como solo tenemos un apostador podemos poner un único contador
		int puntos = 0;
		
		for(Pronostico p : pronosticos) {
			puntos += p.puntos();
		}
		
		//Ya con esto tenemos los puntos, solo falta imprimir
		
		System.out.println("El puntaje es: " + puntos);

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
			equipo1.setNombre(celdas[0].toUpperCase());	//Conviento los nombres a todo mayúsculas para tratar de salvar los casos donde escriban distinto el mismo país
			equipo2.setNombre(celdas[3].toUpperCase());
			
			//Agregamos los equipos al conjunto
			equipos.add(equipo1);
			equipos.add(equipo2);
			
		}
		
		return equipos;
	}
	
	//Armamos los partidos
	private static Partido[] partidos(List<String> lineasRes, Set<Equipo> equipos) {
		//La cantidad de lineas es igual a la cantidad de partidos jugados
		//Como es un array tenemos que inicializarlo con el tamaño adecuado, ya que este no se puede cambiar
		Partido[] partidos = new Partido[lineasRes.size()]; 
		int i = 0; //Index para recorrer el array
		
		for(String linea: lineasRes) {
			String[] celdas = linea.split(",");
			Partido partido = new Partido();
			//Ya tenemos instanciados los equipos, así que buscamos entre los equipos "en juego" y los guardamos en el partido
			for(Equipo e: equipos) {
				//Comparamos primero el equipo 1
				if (e.getNombre().equals(celdas[0].toUpperCase()) ){
					//Si entra coincide el equipo instanciado con el equipo jugando
					partido.setEquipo1(e);
					partido.setGoles1(Integer.parseInt(celdas[1]));
					
				} else if (e.getNombre().equals(celdas[3].toUpperCase()) ) {
					partido.setEquipo2(e);
					partido.setGoles2(Integer.parseInt(celdas[2]));
				}
			}
			
			partidos[i] = partido;
			i++;
			
		}
		
		return partidos;
		
	}
	
	
	//Armamos los pronosticos
	private static Pronostico[] pronosticos(List<String> lineasProc, Partido[] partidos) {
		//La cantidad de lineas es igual a la cantidad de pronosticos hechos
		//Como es un array tenemos que inicializarlo con el tamaño adecuado, ya que este no se puede cambiar
		Pronostico[] pronosticos = new Pronostico[lineasProc.size()];
		int i = 0; //Index para recorrer el array
		
		for(String linea: lineasProc) {
			String[] celdas = linea.split(",");
			Pronostico pronostico = new Pronostico();
			
			//Nos fijamos de qué partido habla el pronóstico
			for(Partido p: partidos) {
				//Se ve feo pero lo que hace es comparar los equipos del partido para ver si son los mismos del pronostico
				if(p.getEquipo1().getNombre().equals(celdas[0].toUpperCase()) && p.getEquipo2().getNombre().equals(celdas[4].toUpperCase())) {
					pronostico.setPartido(p);
					pronostico.setEquipo(equipoPredicho(celdas,p));
					pronostico.setResultado(resultadoPredicho(celdas));
				}
			}
			
			//Ya teniendo el pronostico armado lo agregamos al arreglo
			pronosticos[i] = pronostico;
			i++;
			
		}
		
		return pronosticos;
	}
	
	private static Equipo equipoPredicho(String[] celdas, Partido p) {
		if(celdas[1].equals("X") || celdas[2].equals("X")) {
			return p.getEquipo1();
		} else {
			return p.getEquipo2();
		}
	}
	
	private static Resultado resultadoPredicho(String[] celdas){
		if (celdas[1].equals("X") || celdas[3].equals("X")) {
			return Resultado.GANADOR;
		} else  {
			return Resultado.EMPATE;
		}
		
	}
}
