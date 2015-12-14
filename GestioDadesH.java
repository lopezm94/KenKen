//import Excepcions.*;
//import Persistencia.Gestio_Dades;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

/**
*@version 1.0
*@author Marc Ortiz
*@author Joan Grau
*@author Juan Lopez
*/


public class GestioDadesH  extends Gestio_Dades{

	static {
		try{
			Crear_directorio(".","KenKens");
		}catch(IOException e){
			System.out.println(e.toString());
		} catch (FicheroYaExistente f) {
		}
		try{
			Crear_directorio(".","Games");
		}catch(IOException e){
			System.out.println(e.toString());
		} catch (FicheroYaExistente e) {
		}
		try{
			Crear_archivo("Profiles",".");
		}catch(IOException e){
			System.out.println(e.toString()+"2hola");
		} catch (FicheroYaExistente e) {
		}
	}

	/*
	getInfoLine sirve tanto para Ranking como para Usuarios
	Dado que tendremos dos ficheros (Profiles.txt i Ranking.txt) donde
	guardaremos los datos en formato:
	username + info
	he creado esta funcion getInfoLine para obtener a partir de una keyword (username)
	muy similar a un select(keyword) en base de datos
	*/
	private static String getInfoLine(String keyword, String dir, String file){
		String profiledata = "";
		try {
			BufferedReader br = new BufferedReader(new FileReader(dir+"/"+file));
		    String line;
		    while ((line = br.readLine()) != null) {
		       if(line.contains(keyword)){
		       		profiledata = line;
		       }
		    }
		    br.close();
		}catch(IOException e){
			System.out.println(e.toString());
		}
		return profiledata;
	}

	public static LinkedList<Pair<String,Integer>> readScores(
		String dificultad, String dir, String file) {
    Integer index = null;
    LinkedList<Pair<String,Integer>> lista = new LinkedList<Pair<String,Integer>>();

    switch (dificultad.charAt(0)) {
      case 'F':
        index = 2;
        break;
      case 'M':
        index = 3;
        break;
      case 'D':
        index = 4;
        break;
    }

		try {
			File archivo = new File(dir+"/"+file);
			BufferedReader br = new BufferedReader(new FileReader(archivo));
			String line;
			String[] info;
			while ((line = br.readLine()) != null) {
				info = line.split("\\s");
				if (info.length < 5)
					continue;
				Integer puntuacion = Integer.parseInt(info[index]);
				lista.add(new Pair<String,Integer>(info[0],puntuacion));
		    }
			br.close();
		}catch(IOException e) {
			System.out.println(e.toString());
		}

		return lista;
  }

	//Obte la linia a que es troba en el fitxer donada una keyword
	public static int getLine(String keyword, String dir, String file){
		int contador = 0;
		Boolean control = false;
		try {
			BufferedReader br = new BufferedReader(new FileReader("./"+file));
		    String line;
		    while ((line = br.readLine()) != null && !control) {
		       if(line.contains(keyword)){
		       		control = true;
		       }else{
		    	   contador++;
		       }
		    }
		    br.close();
		}catch(IOException e){
			System.out.println(e.toString());
		}
		return contador;
	}

	public static String[] getProfileInfo(String keyword, String dir, String file){
		//retorna una array de strings [marc,1234,20,20,100]  [0]-> usr; [1]->pass; [2:5] -> points
		return getInfoLine(keyword,dir,file+".txt").split("\\s");
	}
	public static Boolean existsUser(String[] st, String username){
		/*Comprova dins del tokenizer que no sigui null*/
		return st[0].equals(username);
	}
	public static String getUserByToken(String[] st){
		if(st.length > 0) return st[0];
		else return "";
	}
	public static String getPassByToken(String[] st){
		if(st.length > 0){
			return st[1];
		}
		return "";
	}
	public static int[] getPuntuacio(String[] st){
		int i = 0;
		int[] punts = new int[3];
		if(st.length > 0){
			for (i = 2; i<5; ++i) {
				punts[i] = Integer.parseInt(st[i]);
			}
		}
		return punts;
	}

	/*
		Exemple partida.txt:
		HEADER PARTIDA:
			1234
			Kenken2.txt
		VALUES PARTIDA:
			1 2 3
			3 1 2
			2 3 1
	*/
	public static String[] getPartidaHeaderInfo(String file, String username, int linea){
		/*
			getPartidaHeaderInfo retorna els valors:
			->temps de partida
			->fitxer on es troba el kenken a rsoldre
		*/
		String[] headerinfo = null;
		try{
			headerinfo = Leer_string(file,"./Games/"+username,"\n",linea).split("\n");
		}catch(IOException e){
			System.out.println(e.toString());
			return null;
		} catch (FicheroNoExiste e) {
			e.printStackTrace();
			return null;
		}
		return headerinfo;

	}
	public static int[][] getPartidaValues(String file, String username, int mida){
		int[][] caselles = new int[mida][mida];
		int i;
		i = 0;
		try (BufferedReader br = new BufferedReader(new FileReader("Games/"+username+"/"+file+".txt"))) {
		    String line;
		    while ((line = br.readLine()) != null) {
		    	if(i >= 2){
			       String[] fila = line.split("\\s");
			         for (int j=0; j<fila.length; j++){
				       	caselles[i-2][j] = Integer.parseInt(fila[j]);
				      }
		    	}
		       ++i;
		    }
		}catch(IOException e){
			System.out.println(e.toString());
		}
		return caselles;
	}

	public static void guardarPartida(Perfil p,String nomkenken){
		String username = p.get_usuari();
		String nompartida = p.get_partida().getNomPartida();
		try {
			Crear_archivo(nompartida,"./Games/"+username);
		} catch (IOException e) {
		} catch (FicheroYaExistente e) {
			try {
				Borrar_archivo(nompartida,"./Games/"+username);
			} catch (FicheroNoExiste | IOException e1) {
			}
				try {
					Crear_archivo(nompartida,"./Games/"+username);
				} catch (IOException | FicheroYaExistente e1) {
				}


		}
		try {
			Escribir_string(""+p.get_partida().getTime(),"\n",nompartida,"./Games/"+username);
			Escribir_string(nomkenken,"\n",nompartida,"./Games/"+username);
			String fila =  "";
			for(int i = 0; i < p.get_partida().getTauler().size(); ++i){
				fila = "";
				for(int j = 0; j < p.get_partida().getTauler().size(); ++j){
					fila += p.get_partida().getTauler().getCasillaVal(i, j)+" ";
				}
				Escribir_string(fila,"\n",nompartida,"./Games/"+username);
			}
		} catch (IOException | FicheroNoExiste e) {
		}
	}
	/*
	Exemple Kenken.txt:
	3
	operacions + - * / * . +
	dificultat 0
	casella0 0 2 6
	casella1 0 3 1
	casella2 1 2 2
	casella3 1 1 4
	...
	casellax fija sol idarea --> fija (1 fija,0 no fija), sol(value), idarea(0...total areas)
*/

	public static Boolean guardar_kenken(TableroH tauler, String nomkenken){
		try {
			Crear_archivo(nomkenken,"./KenKens");
			try {
				Escribir_string(""+tauler.getFiles(),"\n",nomkenken,"./KenKens");
				String st = "operacions";
				for(int i=0; i < tauler.getNumAreas();++i){
					st += " "+tauler.getArea(i).get_operacio();
				}
				Escribir_string(st,"\n",nomkenken,"./KenKens");
				/*st = "dificultat";
				st += " "+tauler.calcuDif();
				Escribir_string(st,"\n",nomkenken,"./KenKens");*/
				for(int i=0; i < tauler.getFiles(); ++i){
					for(int j=0; j < tauler.getFiles(); ++j){
						st = "casella";
						int k = i*tauler.getFiles()+j;
						st += ""+k;
						if(tauler.getCasilla(i, j).getFija())	k = 1;
						else	k = 0;
						st += " "+k;
						st += " "+tauler.getCasillaSol(i, j);
						st += " "+tauler.getAreaID(i, j);
						Escribir_string(st,"\n",nomkenken,"./KenKens");
					}
				}
				return true;
			} catch (IOException | FicheroNoExiste e) {
				return false;
			}
		} catch (IOException | FicheroYaExistente e) {
			return false;
		}
	}

	public static void escriure_kenken(TableroH tauler){

	}

	//RETRN
	public static int getMidaKenken(String nomkenken){
		int mida = -1;
		try{
			mida = Integer.parseInt(Leer_string(nomkenken,"./KenKens","\n",0));
		}catch(IOException e){
			System.out.println(e.toString());
		} catch (FicheroNoExiste e) {
			e.printStackTrace();
		}
		return mida;
	}
/*	public int getDificultatKenken(String nomkenken){
		int dificultat = 0;
		String[] op = getInfoLine("dificultat", "./KenKens", nomkenken+".txt").split("\\s");
		dificultat = Integer.parseInt(op[1]);
		return dificultat;
	}*/
	public static String[] getOperacions(String nomkenken){
		String[] opnew;
		String[] op = getInfoLine("operacions", "./KenKens", nomkenken+".txt").split("\\s");
		opnew = new String[op.length-1];
		for(int i = 0; i< opnew.length;++i){
			opnew[i] = op[i+1];
		}
		return opnew;
	}

	/*
	Exemple de contingut de variable de retorn:
	1 2 3 --> casella 0 --> retorn[0]
	1 3 2
	2 2 2
	3 4 6
	1 2 6
	Per cada fila de l'element de la matriu equival a una casella, i cada columna a les seves propietats:
	- fija o no fija (1,0)
	- solucio usuari temporal
	- area a la que correspon
	*/
	public static int[][] getCasellaValors(String nomkenken){
		int mida = getMidaKenken(nomkenken);
		int casella_values[][] = new int[mida*mida][3];
		for(int i = 0; i<mida*mida; ++i){
			String[] casellainfo = getInfoLine("casella"+i,"./KenKens",nomkenken+".txt").split("\\s");
			for(int k = 0; k < 3; ++k){
				casella_values[i][k] = Integer.parseInt(casellainfo[k+1]);
			}
		}
		return casella_values;
	}

	public static String[] getPartides(String username){
		String partides = "";
		File[] files = new File("./Games/"+username).listFiles();
		for (File file:files){
			if(file.isFile() && file.getName().length() > 4){
				partides += file.getName().substring(0, file.getName().length()-4)+",";
			}
		}
		if (partides.equals("")) return null;
		return partides.substring(0,partides.length()-1).split(",");
	}
	
	public static Boolean existeixPartida(String nompartida){
		File[] files = new File("./Games/"+username).listFiles();
		for (File file:files){
			if(file.isFile() && file.getName().equals(nompartida+"txt")) return false;
		}
	}
	
	public static String[] getKenkens(){
		String kenkens = "";
		File[] files = new File("./KenKens").listFiles();
		for(File file:files){
			if(file.isFile() && file.getName().length() > 4){
				kenkens += file.getName().substring(0, file.getName().length()-4)+",";
			}
		}
		return kenkens.substring(0, kenkens.length()-1).split(",");
	}
}
