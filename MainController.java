public class MainController{
	/*La classe MainController serà el Controlador general encarregat del funcionament de totes les parts*/
	private static Perfil entrar_usuari(){
		/*Retornem l'usuari*/
		Perfil user;
		System.out.println("Tens un usuari? (0-no, 1-si");
		int te_usuari = in.nextLine();
		if(te_usuari == 1){
			System.out.println("Entra el nom d'usuari");
			String nomUser = in.nextLine();
			System.out.println("Entra la contasenya");
			String pass = in.nextLine();
			//Buscar les dades al controlador gestio
			user = new Perfil(nomUser,pass);
		}else if(te_usuari == 0){
			System.out.pritnln("Vols crear un usuari o vols entrar com a convidat? (0-convidat, 1-usuari nou ");
			int usuari_nou = in.nextLine();
			if(usuari_nou == 0){
				//Creem un perfil convidat
				user = new Perfil();
			}else if(usuari_nou == 1){
				//Gestió dades + retornar el perfil
				System.out.println("Entra el nou nom d'usuari");
				String nomUser = in.nextLine();
				System.out.println("Entra la contasenya");
				String pass = in.nextLine();
				user = new Perfil(nomUser,pass);
			}else{

			}
		}else{
			//Afegir Exception
		}
		return user;
	}
	public MainController(){

	}
}