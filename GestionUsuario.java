/**
*@author Marc Ortiz
**/
import java.io.IOException;

public class GestionUsuario{
	private Perfil perfilActual;
	public void login(String username, String password) throws IOException{
		Perfil p;
		String[] st = GestioDadesH.getProfileInfo(username, ".", "Profiles");
		if(GestioDadesH.existsUser(st,username)){
			if(GestioDadesH.getPassByToken(st).equals(password)){
				p = new Perfil(username,password);
			}else{
				throw new IOException("Contrassenya o usuari incorrectes, torna a provar");
			}
		}else{
			p = null;
			throw new IOException("No existeix l'usuari");
		}
		perfilActual = p;
	}
	public void invitado(){
		perfilActual = new Perfil();
	}
	public void newUser(String usuari, String password) throws IOException{
		String[] st = GestioDadesH.getProfileInfo(usuari, ".", "Profiles");
		if(GestioDadesH.existsUser(st,usuari)){
			throw new IOException("Ja existeix un usuari amb aquest nom");
		}else{
			try{
				GestioDadesH.Escribir_string(usuari+" "+password+" 0 0 0", "\n", "Profiles", ".");
			}catch(IOException e){} 
			catch (FicheroNoExiste f) {}
			try{
				GestioDadesH.Crear_directorio(usuari,"./Games");
			}catch(IOException e){} 
			catch (FicheroYaExistente e) {}
			perfilActual = new Perfil(usuari,password);
		}
	}
	public void DeleteUser() throws IOException{
		int linea = GestioDadesH.getLine(perfilActual.get_usuari(),".","Profiles.txt");
		try{
			Gestio_Dades.modificarString("Profiles", ".", linea, "\n", "\n");
		}catch(IOException e){
			throw new IOException("Errors eliminant l'usuari");
		}catch(FicheroNoExiste f){
		}catch(FicheroYaExistente f1){
			
		}
	}
	public void assignarPartida(Partida p){
		perfilActual.assignar_nova_partida(p);
	}
	public Perfil getProfile(){
		return perfilActual;
	}
}

