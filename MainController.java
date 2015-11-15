import java.io.IOException;
import java.util.Scanner;

public class MainController{
	//Definició variables globals i controladors que necessitarem:
	private Perfil currentUser;
	private GestioDadesH dataEngine;
	Scanner in;
	//Login_Usuari:
	//Comprovem que l'usuari existeixi a la base de dades o sino entrar com  a convidat
	public Perfil login(){
		int control = 0;
		while(control == 0){
			System.out.println("Tens un usuari? (0-no, 1-si)");
			int te_usuari = in.nextInt();
			if(te_usuari == 1){
				System.out.println("Entra el nom d'usuari");
				String nomUser = in.next();
				System.out.println("Entra la contasenya");
				String pass = in.next();
				String[] st = dataEngine.getProfileInfo(nomUser, ".", "Profiles");
				//Control String tokenizer
				if(dataEngine.existsUser(st,nomUser)){
					//Buscar les dades al controlador gestio, si no el troba, 
					//preguntar si vol crear un nou usuari
					if(dataEngine.getPassByToken(st).equals(pass)){
						currentUser = new Perfil(nomUser,pass);
						control = 1;
					}
				}
			}else if(te_usuari == 0){
				System.out.println("Vols crear un usuari o vols entrar com a convidat? (0-convidat, 1-usuari nou) ");
				int usuari_nou = in.nextInt();
				if(usuari_nou == 0){
					currentUser = new Perfil();
					control = 1;
				}else if(usuari_nou == 1){
					System.out.println("Entra el nom d'usuari");
					String nomUser = in.next();
					System.out.println("Entra la contasenya");
					String pass = in.next();
					System.out.println("Entra la contasenya");
					String passr = in.next();
					if(pass.equals(passr)){
						String[] st = dataEngine.getProfileInfo(nomUser, ".", "Profiles"); 
						if(dataEngine.existsUser(st,nomUser)){
							System.out.println("Ja existeix un usuari amb aquest nom.");
						}else{
							try{
								GestioDadesH.Escribir_string(nomUser+" "+pass+" 0 0 0", "\n", "Profiles", ".");
							}catch(IOException e){
								System.out.println(e.toString());
							} catch (FicheroNoExiste f) {
								f.printStackTrace();
								System.out.println("fichero no existe");
							}
							try{
								GestioDadesH.Crear_directorio(nomUser,"./Games");
							}catch(IOException e){
								System.out.println(e.toString());
							} catch (FicheroYaExistente e) {
								e.printStackTrace();
							}
							currentUser = new Perfil(nomUser,pass);
							control = 1;
						}
					}else{
						System.out.println("Les dues contrassenyes han de concordar.");
					}
				}else{
					System.out.println("Has d'introduir 1 o 0.");
				}
			}else{
				System.out.println("Has d'introduir 1 o 0.");
			}
		}
		return currentUser;
	}
	public MainController(){
		in = new Scanner(System.in);
		Perfil p = new Perfil();
		dataEngine = new GestioDadesH(p);

	}
	public void new_game(){
		//Partida nova = new Partida();

	}
	public void load_game(){
		//Load an existing game
	}
	public void create_kenken(){
		//create a new kenken

	}
	public void delete_user(){
		//delete my current username
		int linea = dataEngine.getLine(currentUser.get_usuari(),".","Profiles.txt");
		try{
			Gestio_Dades.modificarString("Profiles", ".", linea, "\n", "\n");
		}catch(IOException e){
			System.out.println(e.toString());
		}catch(FicheroNoExiste f){
			System.out.println("El fichero no existe\n");
		}catch(FicheroYaExistente f1){
			System.out.println("El fichero ya existe\n");
		}
	}
	public void show_tutorial(){
		//show a simple text
		System.out.println("Tutorial per jugar:\n http://www.kenkenpuzzle.com/howto/solve");
	}
}
