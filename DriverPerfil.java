import java.io.IOException;
import java.util.Scanner;

public class DriverPerfil {
	public static void main(String[] args) throws IOException {
		Boolean end = false;
		Scanner in = new Scanner(System.in);
		while(!end){
			switch(action(in)){
			case 0:
				end = true;
				break;
			case 1: //Crear un perfil convidat:
				System.out.println("Creació de un usuari convidat:");
				Perfil p = new Perfil();
				System.out.println("Username: " + p.get_usuari());
				System.out.println("Contrasenya: " + p.get_contrasenya());
				break;
			case 2: //Crear un nou perfil amb usuari conegut:
				System.out.println("Escull el nom d'usuari i contrasenya amb el que carregar el perfil:");
				System.out.println("Entra el nom d'usuari:");
				String user = in.next();
				System.out.println("Entra la contrasenya:");
				String pssw = in.next();
				Perfil p = new Perfil(user,pssw);
				System.out.println("Nom d'usuari del perfil: " + p.get_usuari());
				System.out.println("Contrasenya bdel perfil: " + p.get_contrasenya());
				break;
			case 3: //Crear perfil + assignarli una partida al perfil
				System.out.println("Escull el nom d'usuari i contrasenya amb el que carregar el perfil:");
				System.out.println("Entra el nom d'usuari:");
				String user = in.next();
				System.out.println("Entra la contrasenya:");
				String pssw = in.next();
				Perfil p = new Perfil(user,pssw);
				Partida game = new Partida("Partida_prova_"+p.get_usuari(),p);
				p.assignar_nova_partida(game);
				System.out.println("Nom de la partida assignada: ");
				System.out.println(p.getPartida().getNomPartida());
				break;
			case 4: //Afegir puntuacio al perfil:
				System.out.println("Escull el nom d'usuari i contrasenya amb el que carregar el perfil:");
				System.out.println("Entra el nom d'usuari:");
				String user = in.next();
				System.out.println("Entra la contrasenya:");
				String pssw = in.next();
				Perfil p = new Perfil(user,pssw);
				System.out.println("Entra les puntuacions a afegir a l'usuari:");
				System.out.println("Puntuacio de kenkens fàcils:");
				int pfa = in.nextInt();
				System.out.println("Puntuacio de kenkens mig:");
				int pmi = in.nextInt();
				System.out.println("Puntuacio de kenkens dificils:");
				int pdi = in.nextInt();
				System.out.print(
						"Puntuacio facil: " + p.get_puntuacio().facil + "\n" +
						"Puntuacio mig: " + p.get_puntuacio().mig + "\n" +
						"Puntuacio dificil :" + p.get_puntuacio().dificil + "\n"
					);
				break;
			default:
                System.out.println("Acció no reconeguda");
                break;
			}
			
		}
		in.close();
	}
	private static int action(Scanner in1) throws IOException{
		System.out.println(	"1 - Crear un nou perfil convidat\n"+
							"2 - Crear un nou perfil amb usuari conegut\n"+
							"3 - Crear perfil + assignarli una partida al perfil\n"+
							"4 - Afegir puntuacio al perfil\n"+
							"Per sortir : 0\n");
		int res = in1.nextInt();
		return res;
	}
}
