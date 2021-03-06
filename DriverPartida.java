import java.io.IOException;
import java.util.Scanner;


/**
*@author Marc Ortiz
*/
public class DriverPartida {
	public static void main(String[] args) throws IOException {
		boolean end = false;
		Scanner in = new Scanner(System.in);
		Perfil test = new Perfil();
		String nom;
		Partida p;
		System.out.println(	"1 - Crear una nova partida\n"+
				"2 - Assignar un tauler a una partida\n"+
				"3 - Afegir temps transcorregut a la partida\n"+
				"Per sortir : 0\n");
		while(!end){
			switch(action(in)){
			case 0:
				end = true;
				break;
			case 1: //Crear una nova partida
				System.out.println("Nom de la partida: ");
				nom = in.next();
				p = new Partida(nom,test.get_usuari());
				System.out.println("S'ha creat una partida amb nom: " + p.getNomPartida());
				break;
			case 2: //Assignar un tauler a una partida
				System.out.println("Nom de la partida: ");
				nom = in.next();
				p = new Partida(nom,test.get_usuari());
				TableroH test2 = new TableroH(9);
				p.setTauler(test2);
				System.out.println("S'ha afegit un tauler de : " + p.getTauler().size());
				break;
			case 3: //Afegir temps transcorregut a la partida
				System.out.println("Nom de la partida: ");
				nom = in.next();
				p = new Partida(nom,test.get_usuari());
				System.out.println("Temps transcorregut de la partida: ");
				int time = in.nextInt();
				p.setTime(time);
				System.out.println("Temps transcorregut: " + p.getTime());
				break;
			default:
                System.out.println("Acció no reconeguda");
                break;
			}

		}
		in.close();
	}
	private static int action(Scanner in1) throws IOException{
		int res = in1.nextInt();
		return res;
	}
}
