package TP;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class LecturaDeArchivos {
	public void main (String resultados, String pronostico){
		
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
		
		for(String linea : lineasRes) {
			String[] celdas = linea.split(";");
			
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
			
			//Ahora solo falta definir el pronostico (lo hago adentro del for para no tener que volver a definir todo el partido y los equipos)
			for(String lineaP : lineasProc) {
				String[] celdasP = lineaP.split(";");
				
				Pronostico proc = new Pronostico();
				
				if (celdasP[1] == "X") {
					proc.setEquipo(equipo1);
					//proc.setResultado(//COMPLETAR); //Gana equipo 1
					
				} else if (celdasP[3] == "X") {
					proc.setEquipo(equipo2);
					//proc.setResultado(//COMPLETAR); //Gana equipo 2
				
				} else {
					proc.setEquipo(equipo1);
					//proc.setResultado(//COMPLETAR); //Empatan
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
		
		
		
	}
}
