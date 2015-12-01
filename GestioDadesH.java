//import Excepcions.*;
//import Persistencia.Gestio_Dades;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
*@version 1.0
*@author Marc Ortiz
*@author Joan Grau
*/


public class GestioDadesH  extends Gestio_Dades{
	/*
	getInfoLine sirve tanto para Ranking como para Usuarios
	Dado que tendremos dos ficheros (Profiles.txt i Ranking.txt) donde
	guardaremos los datos en formato:
	username + info
	he creado esta funcion getInfoLine para obtener a partir de una keyword (username)
	muy similar a un select(keyword) en base de datos
	*/
	private String getInfoLine(String keyword, String dir, String file){
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

	//Obt� la l�nia a que es troba en el fitxer donada una keyword
	public int getLine(String keyword, String dir, String file){
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

	public GestioDadesH(Perfil p){
		//Creem els fitxers necessaris, si existeixen llavors no es crearan, si no existeixen es crearan.
		/*
			Create if not exist --> all txt database files and dir's needed.
			Directory structure:
			./
				./KenKens
					Kenken1.txt
					KenKen2.txt
					KenKen3.txt
				->Profiles.txt
				./Games
					MarcOrtiz
						Game1.txt
						Game2.txt
						Game3.txt
					Pepe
						Game1.txt
					Al�_el_Magreb�
						Game1.txt
				->Ranking.txt

		*/
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
			Crear_archivo("Ranking",".");
		}catch(IOException e){
			System.out.println(e.toString()+"hola");
		} catch (FicheroYaExistente e) {
		}
		try{
			Crear_archivo("Profiles",".");
		}catch(IOException e){
			System.out.println(e.toString()+"2hola");
		} catch (FicheroYaExistente e) {
		}
	}

	public String[] getProfileInfo(String keyword, String dir, String file){
		//retorna una array de strings [marc,1234,20,20,100]  [0]-> usr; [1]->pass; [2:5] -> points
		return getInfoLine(keyword,dir,file+".txt").split("\\s");
	}
	public Boolean existsUser(String[] st, String username){
		/*Comprova dins del tokenizer que no sigui null*/
		return st[0].equals(username);
	}
	public String getUserByToken(String[] st){
		if(st.length > 0) return st[0];
		else return "";
	}
	public String getPassByToken(String[] st){
		if(st.length > 0){
			return st[1];
		}
		return "";
	}
	public int[] getPuntuacio(String[] st){
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
	public String[] getPartidaHeaderInfo(String file, String username, int linea){
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
	public int[][] getPartidaValues(String file, String username, int mida){
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

	public void guardarPartida(Perfil p,String nomkenken){
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
	
	public void guardar_kenken(TableroH tauler, String nomkenken){
		try {
			Crear_archivo(nomkenken,"./KenKens");
		} catch (IOException | FicheroYaExistente e) {
		}
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
		} catch (IOException | FicheroNoExiste e) {
			e.printStackTrace();
		}
	}
	
	public void escriure_kenken(TableroH tauler){

	}

	//RETRN
	public int getMidaKenken(String nomkenken){
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
	public String[] getOperacions(String nomkenken){
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
	public int[][] getCasellaValors(String nomkenken){
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

}
