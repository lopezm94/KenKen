
import java.io.IOException;
import java.util.Scanner;
import java.util.StringTokenizer;

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
				String nomUser = in.nextLine();
				System.out.println("Entra la contasenya");
				String pass = in.nextLine();
				StringTokenizer st = dataEngine.getProfileInfo(nomUser, ".", "Profiles.txt"); 
				if(dataEngine.existsUser(st)){
					//Buscar les dades al controlador gestio, si no el troba, 
					//preguntar si vol crear un nou usuari
					currentUser = new Perfil(nomUser,pass);
					control = 1;
				}
			}else if(te_usuari == 0){
				System.out.println("Vols crear un usuari o vols entrar com a convidat? (0-convidat, 1-usuari nou) ");
				int usuari_nou = in.nextInt();
				if(usuari_nou == 0){
					currentUser = new Perfil();
					control = 1;
				}else if(usuari_nou == 1){
					System.out.println("Entra el nom d'usuari");
					String nomUser = in.nextLine();
					System.out.println("Entra la contasenya");
					String pass = in.nextLine();
					System.out.println("Entra la contasenya");
					String passr = in.nextLine();
					if(pass == passr){
						StringTokenizer st = dataEngine.getProfileInfo(nomUser, ".", "Profiles.txt"); 
						if(dataEngine.existsUser(st)){
							System.out.println("Ja existeix un usuari amb aquest nom.");
						}else{
							try{
								dataEngine.Escribir_string(nomUser+" "+pass+" 0 0 0", "\n", "Profiles.txt", ".");
							}catch(IOException e){
								System.out.println(e.toString());
							} catch (FicheroNoExiste f) {
								f.printStackTrace();
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
		//Start a new game
	}
	public void load_game(){
		//Load an existing game
	}
	public void create_kenken(){
		//create a new kenken

	}
	public void delete_user(){
		//delete my current username

	}
	public void show_tutorial(){
		//show a simple text
	}
}