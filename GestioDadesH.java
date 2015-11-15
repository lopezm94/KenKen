import java.lang.RuntimeException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

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
			BufferedReader br = new BufferedReader(new FileReader("./"+file));
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
					Alí_el_Magrebí
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
			st[1];
		}
		return "";
	}
	public int[] getPuntuacio(String[] st){
		int i = 0;
		int[] punts = new int[3];
		if(length > 0){
			for (int i = 2; i<5; ++i) {
				punts[i] = Integer.parseInt(st[i]);
			}
		}
		return punts;
	}

	/*
		Exemple partida.txt:
		HEADER PARTIDA:
			Partida_dificil_marc
			1234
			32
			Kenken2.txt
			3
		VALUES PARTIDA:
			1 2 3
			3 1 2
			2 3 1
	*/
	public String[] getPartidaHeaderInfo(String file, String username){
		/*
			getPartidaHeaderInfo retorna els valors:
			->nom de partida
			->temps de partida
			->dificultat de partida(int)
			->fitxer on es troba el kenken a rsoldre
			->mida del kenken
		*/
		String[] headerinfo = null;
		try{
			headerinfo = Leer_string(file,"./"+username,"\n",4).split("\n"); 
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
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
		    String line;
		    while ((line = br.readLine()) != null) {
		       String[] fila = line.split("\\s");
		         for (int j=0; j<fila.length; j++)
			       	caselles[i][j] = Integer.parseInt(fila[j]);
			       }
		       ++i;
		    }
		}catch(IOException e){
			System.out.println(e.toString());
		}
		return caselles;
	}
	/*
		Exemple Partida.txt:
		3
		operacions + - * / * . +
		casella0 0 2 6
		casella1 0 3 1
		casella2 1 2 2
		casella3 1 1 4
		...
		casellax fija sol idarea --> fija (1 fija,0 no fija), sol(value), idarea(0...total areas)
	*/
	public void escriure_kenken(TableroH tauler){
		//Funcio escriptura del KenKen
	}
	public int getMidaKenken(String nomkenken, String dir){
		int mida = -1;
		try{
			mida = Integer.parseInt(Leer_string(file,"./KenKens","\n",1)); 
		}catch(IOException e){
			System.out.println(e.toString());
		} catch (FicheroNoExiste e) {
			e.printStackTrace();
		}
		return mida;
	}
	public String[] getOperacions(String nomkenken, String dir){
		String[] opnew;
		try{
			String[] op = getInfoLine("operacions", "./KenKens", nomkenken).split("\\s"); 
			opnew = new String[op.lengh-1];
			for(int i = 0; i< opnew.length;++i){
				opnew[i] = op[i+1];
			}
		}catch(IOException e){
			System.out.println(e.toString());
		} catch (FicheroNoExiste e) {
			e.printStackTrace();
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
	public int[][] getCasellaValors(String nomkenken, String dir){
		int mida = getMidaKenken(nomkenken,dir);
		int casella_values[][] = new int[mida*mida][3];
		for(int i = 0; i<mida; ++i){
			for(int j = 0; j<mida; ++j){
				String[] casellainfo = getInfoLine("casella"+(String)(i*mida+j),"./KenKens",nomkenken).split("\\s");
				for(int i = 0; i < 3; ++i){
					casilla_values[i][j] = Integer.parseInt(casellainfo[i+1]);
				}
			}
		}
		return casilla_values;
	}
}
