/**
*@author Marc Ortiz
**/

public class GestionUsuario extends Transaction{
	private Perfil perfilActual;
	public void login(String username, String password) throws IOException{
		Perfil p;
		String[] st = dataEngine.getProfileInfo(nomUser, ".", "Profiles");
		if(dataEngine.existsUser(st,username)){
			if(dataEngine.getPassByToken(st).equals(password)){
				p = new Perfil(username,password);
			}else{
				throw new IOException("Contrassenya o usuari incorrectes, torna a provar");
			}
		}
		perfilActual = p;
	}
	public void invitado(){
		perfilActual = new Perfil();
	}
	public Perfil newUser(String usuari, String password) throws IOException{
		String[] st = dataEngine.getProfileInfo(usuari, ".", "Profiles");
		if(dataEngine.existsUser(st,usuari)){
			throw new IOException("Ja existeix un usuari amb aquest nom");
		}else{
			try{
				dataEngine.Escribir_string(usuari+" "+password+" 0 0 0", "\n", "Profiles", ".");
			}catch(IOException e){} 
			catch (FicheroNoExiste f) {}
			try{
				dataEngine.Crear_directorio(usuari,"./Games");
			}catch(IOException e){} 
			catch (FicheroYaExistente e) {}
			perfilActual = new Perfil(usuari,password);
		}
	}
	public void DeleteUser() throws IOException{
		int linea = dataEngine.getLine(perfilActual.get_usuari(),".","Profiles.txt");
		try{
			Gestio_Dades.modificarString("Profiles", ".", linea, "\n", "\n");
		}catch(IOException e){
			throw new IOException("Errors eliminant l'usuari");
		}catch(FicheroNoExiste f){
		}catch(FicheroYaExistente f1){
			
		}
	}
	public void assignarTauler(Partida p){
		perfilActual.assignar_nova_partida(p);
	}
	public Perfil getProfile(){
		return perfilActual;
	}
}

