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

	public StringTokenizer getProfileInfo(String keyword, String dir, String file){
		//retorna una array de strings [marc,1234,20,20,100]  [0]-> usr; [1]->pass; [2:5] -> points
		StringTokenizer st = new StringTokenizer(getInfoLine(keyword,dir,file)," ");
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
}
