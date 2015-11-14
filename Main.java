public class Main{
	private Scanner in;
	private MainController mc;
	private static void print_main_menu(){
		//buscar dades_gestió dades
		System.out.println("Escull alguna d'aquestes opcions: ");
		String text = "Aquest text conté les opcions del menú");
		System.out.println(text);
		//Falta cridar a la gestió de dades per recollir text menu
	}

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
	public static void main(){
		in = new Scanner(System.in);
		mc = new MainController();
		//decideix si és un convidat o un usuari normal i crea el perfil
		Perfil p = entrar_usuari(in);
		//L'usuari escull la opció en el menú:
		print_main_menu();
		int opcio = in.nextLine();
		switch(opcio){
			case 0:

			case 1:
			case 2:
			case 3:
			case 4:
		}
	}
}