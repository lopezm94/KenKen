import java.io.IOException;

public class DriverPartidaPerfil {
	private static void imprimeix_resultats(Perfil marc, Partida p){
		//Perfil:
		System.out.println("Usuari " + marc.get_usuari());
		System.out.println("Contrasenya: " + marc.get_contrasenya());
		System.out.println("Puntuacio mig: " + marc.get_puntuacio().mig);
		//Partida:
		System.out.println("Nom partida: " + p.getNomPartida());
		System.out.println("Dificultat partida: " + p.getDificultat());
		System.out.print("Partida cont� un tauler de mida: ");
		System.out.print(p.getTauler().getFiles());
	}
	public static void main(String[] args) throws IOException, FicheroNoExiste {
				Perfil marc = new Perfil("marc","holakease");
			    Partida kenken = new Partida("kenkenfacil",marc.get_usuari());
			    kenken.setDificultat(42);
			    kenken.setTime(1000);
			    kenken.setTauler(new TableroH(3));
			    marc.assignar_nova_partida(kenken);
			    Puntuacio p = new Puntuacio();
			    p.mig = 100;
			    marc.add_puntuacio(p);
			    imprimeix_resultats(marc,kenken);
		}
	}
