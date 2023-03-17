package TP;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class LecturaDeArchivos {
	public void main (String[] args){
		//Cambie esto para tener un main más común
		//Vamos a pedir como condición que primero nos pasen la ruta al archivo resultados y como segundo al archivo pronostico
		
		//Luego ver cómo testeamos esto
		
		String resultados =  args[0]; 
		String pronostico =  args[1];
		
		Path rutaRes;
		Path rutaProc;
		
		//Primero vemos si los archivos que pedimos están en las ubicaciones dadas
		
		List<String> lineasRes = null;
		List<String> lineasProc = null;
		
		try {
			
			rutaRes = Paths.get(resultados);
			lineasRes = Files.readAllLines(rutaRes); //Tener cuidado con esto, puede necesitar más especificación el método
			
			rutaProc = Paths.get(pronostico);
			lineasProc = Files.readAllLines(rutaProc);
			
			
		} catch (IOException e){
			
			System.out.println("archivos innexistentes:");
			return;
		}
		
		//Una vez cargados los archivos, los procesamos
		//Por ahora sabemos que solo va a haber una ronda
		
		Partido[] partidos = new Partido[lineasRes.size()];
		int i = 0; //Indice para navegar el vector
		
		Ronda ronda1 = new Ronda();
		
		//Por ahora, como sabemos que es una sola ronda, podemos ponerlo ahora
		ronda1.setNro("1");
		
		Pronostico[] pronosticos = new Pronostico[lineasProc.size()];
		int j = 0; //Indice para ver los pronósticos
		
		//Supongamos que el orden de los datos es igual al moestrado en los ejemplos del PDF
		//También asumimos que los archivos son archivos .cvs
		
		/* Antes de entrar en los ciclos, necesitamos "limpiar" los array de lineas, o sea, 
		sacarle a cada una la primer linea que indica solo características
		Ej: en resultados hay una linea que dice Equipo 1;Goles Equipo 1;Goles Equipo 2;Equipo 2
		*/
		
		lineasRes.remove(0);
		lineasProc.remove(0);
		
		for(String linea : lineasRes) {
			String[] celdas = linea.split(",");
			
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
			
			
			//Analizar si esta parte funciona como corresponde
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
