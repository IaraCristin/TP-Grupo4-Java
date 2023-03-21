package TP;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

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
			return;
		}
		
		
		try {
			rutaProc = Paths.get(pronostico);
			lineasProc = Files.readAllLines(rutaProc);	
		} catch (IOException e){
			System.out.println("Ruta de pronóstico no válida");
			return;
		}
		
		
		//Una vez cargados los archivos, los procesamos
		
		/* Antes de seguir, necesitamos "limpiar" los array de lineas, o sea, 
		sacarle a cada una la primer linea que indica solo características
		Ej: en resultados hay una linea que dice Equipo 1;Goles Equipo 1;Goles Equipo 2;Equipo 2
		*/
		
		lineasRes.remove(0);
		lineasProc.remove(0);
		
		//Una vez limpio, tenemos solo los partidos cargados
		
		Partido[] partidos = new Partido[lineasRes.size()]; //Sabemos que la cantidad de lineas es igual a la cantidad de partidos
		int i = 0; //Indice para navegar el vector
		
		Ronda ronda1 = new Ronda();
		
		//Por ahora, como sabemos que es una sola ronda, podemos ponerlo ahora
		ronda1.setNro("1");
		
		Pronostico[] pronosticos = new Pronostico[lineasProc.size()]; //Sabemos que la cantidad de lineas es igual a la cantidad de predicciones
		int j = 0; //Indice para ver los pronósticos
		
		
		//Supongamos que el orden de los datos es igual al moestrado en los ejemplos del PDF
		//También asumimos que los archivos son archivos .cvs, esto será usado para separar los datos de los archivos
		
		for(String linea : lineasRes) {
			String[] celdas = linea.split(",");
			//celdas[] = [Nombre equipo 1, goles equipo 1, goles equipo 2, nombre equipo 2]
			
			
			//Armamos todos los objetos vacíos primero
			Equipo equipo1 = new Equipo();
			Equipo equipo2 = new Equipo();
			Partido partido = new Partido();
			
			
			equipo1.setNombre(celdas[0]);
			equipo2.setNombre(celdas[3]);
			
			partido.setEquipo1(equipo1);
			partido.setEquipo2(equipo2);
			partido.setGoles1( Integer.parseInt(celdas[1]) ); //Recordar que tenemos que cambiar el tipo de los numeros de string a int
			partido.setGoles2( Integer.parseInt(celdas[2]) );
			
			
			//Definimos la descripción de cada equipo decidiendo si perdieron, ganaron o empataron
			if (partido.getGoles1() > partido.getGoles2()) {
				equipo1.setDescripcion("ganador");
				equipo2.setDescripcion("perdedor");
			} else if (partido.getGoles1() < partido.getGoles2()) {
				equipo1.setDescripcion("perdedor");
				equipo2.setDescripcion("ganador");
			} else {
				equipo1.setDescripcion("empate");
				equipo2.setDescripcion("empate");
			}
			
			
			
			//Ahora solo falta definir el pronostico (lo hago adentro del for para no tener que volver a definir todo el partido y los equipos)
			for(String lineaP : lineasProc) {
				String[] celdasP = lineaP.split(";");
				
				Pronostico proc = new Pronostico();
				
				if (celdasP[1] == "X") {
					proc.setEquipo(equipo1);
					proc.setResultado("ganador"); //Gana equipo 1
					
				} else if (celdasP[3] == "X") {
					proc.setEquipo(equipo2);
					proc.setResultado("ganador"); //Gana equipo 2
				
				} else {
					proc.setEquipo(equipo1);
					proc.setResultado("empate"); //Empatan, así que no importa qué equipo ponga
				}
				
				proc.setPartido(partido);
				
				pronosticos[j] = proc;
				j++;
				
			}
			
			partidos[i] = partido;
			i++;
		}
		
		ronda1.setPartidos(partidos);
		
		//Ahora que tenemos todo cargado en los objetos, analizamos si las predicciones están correctas
		
		
		//Como solo tenemos un apostador podemos poner un único contador
		int puntos = 0;
		
		for(Pronostico p : pronosticos) {
			//Para tener una mejor lectura voy a poner variables, pero no es necesario
			Equipo e = p.getEquipo();
			Partido partido = p.getPartido();
			
			if(p.getResultado() == partido.resultado(e)) {
				puntos++;
			}
		}
		
		//Ya con esto tenemos los puntos, solo falta imprimir
		
		System.out.println("El puntaje es: " + puntos);

	}

}
