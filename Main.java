import java.util.Scanner;
import java.io.IOException;

/**
*@version 1.0
*@author Joan Grau
*@author Marc Ortiz
*/
public class Main{
	private static void print_main_menu(){
		//buscar dades_gesti� dades
		System.out.println("Escull alguna d'aquestes opcions: ");
		System.out.println("1- Crear Partida \n"+
				"2- Carregar Partida (Per usuaris registrats) \n"+
				"3- Generar KenKen  \n"+
				"4- Elimina l'usuari (Per usuaris registrats) \n"+
				"5- Tutorial sobre com jugar al KenKen\n"+
				"0- Sortir");
	}
	public static void main (String args[]) throws IOException{
		Scanner in = new Scanner(System.in);
		MainController mc = new MainController();
		Perfil user = mc.login(null, null);
		int opcio;
		int exit = 0;
		while(exit==0){
			print_main_menu();
			if(in.hasNextInt()) opcio = in.nextInt();
			else opcio = 0;
			switch(opcio){
				case 0:
					System.out.println("Sesion cerrada\n");
					exit = 1;
					break;
				case 1:
					System.out.println("Entra el nom de la partida que vols crear:");
					String st = in.next();
					System.out.println("Entra el nom del kenken que vols obrir:");
					mc.new_game(st,in.next());
					break;
				case 2:
					if(user.get_usuari().equals("invitado")){
						System.out.println("Els usuaris convidats no poden carregar partida\n");
					}else{
						System.out.println("Introdueix el nom de la partida a carregar");
						mc.load_game(in.next());
					}
					break;
				case 3:
					mc.create_kenken();
					break;
				case 4:
					if(user.get_usuari().equals("invitado")){
						System.out.println("Els usuaris convidats no poden borrar l'usuari\n");
					}else{
						mc.delete_user();
					}
					break;
				case 5:
					mc.show_tutorial();
					break;
				default:
					System.out.println("S'ha d'introduir un nombre entre 0 i 5\n");
					break;
			}
		}
	in.close();
	}
}
