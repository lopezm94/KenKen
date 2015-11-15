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
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
		    String line;
		    while ((line = br.readLine()) != null) {
		       if(line.contains(keyword)){
		       		profiledata = line;
		       }
		    }
		}catch{
			throw new IOException("No existeix el fitxer");
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
		Crear_directorio(".","KenKens");
		Crear_directorio(".","Games");
		if(p.get_usuari() != "invitado"){
			Crear_directorio("./Games",p.get_usuari());
		}
		Crear_archivo('.',"Ranking.txt");
		Crear_archivo('.',"Profiles.txt");
	}

	public static StringTokenizer getProfileInfo(String keyword, String dir, String file){
		//retorna una array de strings [marc,1234,20,20,100]  [0]-> usr; [1]->pass; [2:5] -> points
		StringTokenizer st = new StringTokenizer(getInfoLine(keyword,dir,file),"\\s");
		return st
	}
	public Boolean existsUser(StringTokenizer st){
		/*Comprova dins del tokenizer que no sigui null*/
		return st.countTokens() > 0;
	}
	public String getUserByToken(StringTokenizer st){
		if(st.countTokens > 0) st.nextToken();
		else return "";
	}
	public String getPassByToken(StringTokenizer st){
		int i = 0;
		if(st.countTokens > 0){
			while(st.hasMoreTokens()){
				if(i == 1) return st.nextToken();
				st.nextToken();
				++i;
			}
		}
	}
	public int[] getPuntuacio(StringTokenizer st){
		int i = 0;
		int[] punts = new int[3];
		if(st.countTokens > 0){
			while(st.hasMoreTokens()){
				if(i>1) punts[i] = Integer.parseInt(st.nextToken());
				else st.nextToken();
				++i;
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
	public StringTokenizer getPartidaHeaderInfo(String file, String username){
		/*
			getPartidaHeaderInfo retorna els valors:
			->nom de partida
			->temps de partida
			->dificultat de partida(int)
			->fitxer on es troba el kenken a rsoldre
			->mida del kenken
		*/
		return new StringTokenizer(Leer_string(file,"./"+username,"\n",4),'\n');
	}
	public int[][] getPartidaValues(String file, String username, int mida){
		int[][] caselles = new int[mida][mida];
		int i,j;
		i = j = 0;
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
		    String line;
		    while ((line = br.readLine()) != null) {
		       StringTokenizer fila = new StringTokenizer(line,"\\s");
		       while(fila.hasMoreTokens()){
		       		caselles[i][j] = Integer.parseInt(fila.nextToken());
		       		++j;
		       }
		       j = 0;
		       ++i;
		    }
		}catch{
			throw new IOException("No existeix el fitxer");
		}
		return caselles;
	}
}
