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
	public static void main(){
		in = new Scanner(System.in);
		mc = new MainController();
		//Login de l'usuari
		Perfil user = mc.login();
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