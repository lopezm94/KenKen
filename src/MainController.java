
import java.util.Scanner;

public class MainController{
	//Definició variables globals i controladors que necessitarem:
	private Perfil currentUser;
	private GestioDadesH dataEngine;
	Scanner in;
	//Creació de un usuari:
	private void CrearUsuari(String usr,String pass){
		System.out.println("Entra el nou nom d'usuari");
		String nomUser = in.nextLine();
		System.out.println("Entra la contasenya");
		//String pass = in.nextLine();
		//nomUser = new Perfil(nomUser,pass);
	}
	//Login_Usuari:
	//Comprovem que l'usuari existeixi a la base de dades o sino entrar com  a convidat
	public Perfil login(){
		System.out.println("Tens un usuari? (0-no, 1-si)");
		int te_usuari = in.nextInt();
		if(te_usuari == 1){
			System.out.println("Entra el nom d'usuari");
			String nomUser = in.nextLine();
			System.out.println("Entra la contasenya");
			String pass = in.nextLine();
			if(dataEngine.Leer_string())
			//Buscar les dades al controlador gestio, si no el troba, 
			//preguntar si vol crear un nou usuari
			currentUser = new Perfil(nomUser,pass);
		}else if(te_usuari == 0){
			System.out.pritnln("Vols crear un usuari o vols entrar com a convidat? (0-convidat, 1-usuari nou) ");
			int usuari_nou = in.nextLine();
			if(usuari_nou == 0){
				user = new Perfil();
			}else if(usuari_nou == 1){
				
			}else{

			}
		}else{
			//Afegir Exception
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