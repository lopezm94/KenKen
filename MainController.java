import java.io.IOException;
import java.util.Scanner;

/*@version 1.0
*@author Marc Ortiz 
*@author Joan Grau
*/

public class MainController{
	//DefiniciÃ³ variables globals i controladors que necessitarem:
	private Perfil currentUser;
	private GestioDadesH dataEngine;
	Scanner in;
	private static int action(Scanner in1) throws IOException{
		System.out.println(	"1 - Introduir casella\n"+
							"2 - solucionar\n"+
							"3 - Guardar i sortir\n");
		int res = in1.nextInt();
		return res;
	}
	//Login_Usuari:
	//Comprovem que l'usuari existeixi a la base de dades o sino entrar com  a convidat
	private void play(){
		int opcio;
		Boolean end = false;
		while(!end){
			switch(action(in)){
			case 0:
				end = true;
				break;
			case 1:
				//introduir casella
				System.out.println("Introdueix el valor de la casella: \n
									posx,posy,valor_nou (ex: 0,2,8).
					");
				String elements = in.next();
				int x = elements.split(",")[0];
				int y = elements.split(",")[1];
				int valor = elements.split(",")[2];
				currentUser.get_partida().getTauler().setCasillaVal(x,y,valor);
				break;
			case 2:
				Boolean correcte = currentUser.get_partida().getTauler().tableroCheck() && 
									currentUser.get_partida().getTauler().numerosCheck();
				if(correcte) 
					System.out.println("KenKen correcte! Felicitats");
					end = true;
				else
					System.out.println("KenKen incorrecte!");
				break;
			case 3:
				guardarPartida();
				end = true;
				break;
			default:
                System.out.println("Accion introducida no es correcta");
                break;
			}
			
		}
		in.close();
	}
	
	private TableroH creaTauler(String nomkenken){
		int mida = dataEngine.getMidaKenken(nomkenken);
		TableroH tablero = new TableroH(mida);
		try{
			int[][] caselles;
			caselles = dataEngine.getCasellaValors(nomkenken);
			for (int i = 0; i < mida;++i){
				for (int j = 0; j < mida;++j){
					int valor = -1;
					Boolean fija;
					int sol = caselles[i*mida+j][1];
					if(caselles[i*mida+j][0] == 1){
						fija = true;
						valor = sol;
					}else{
						fija = false;
					}
					Casilla cas = new Casilla(valor,fija,sol);
					tablero.setCasilla(cas,i,j);
				}
			}
			/*Creem cada àrea*/
			String[] operacions = dataEngine.getOperacions(nomkenken);
			int total_areas = operacions.length;
			int idarea = 0;
			while (idarea < total_areas){
				String varS = operacions[idarea];
				char var2[] = varS.toCharArray();
				Area a = new Area(idarea,var2[0]);
				tablero.afegirArea(a,0);
				++idarea;
			}
			for(int i = 0;i < mida; i++){
				for(int j=0; j < mida; j++){
					idarea = caselles[i*mida+j][2];
					tablero.setid(idarea, i, j);
				}
			}
			tablero.colocaRes();
		} finally {
	        if (in != null) {
	        in.close();
	        }// Close the file scanner.
	    }
		return tablero;
	}
	

	
	private void omplirTauler(TableroH tauler, String nomficher){
		int mida = tauler.size();
		int caselles[][] = dataEngine.getPartidaValues(nomficher,currentUser.get_usuari(),mida);
		for(int i=0; i < mida; ++i){
			for(int j=0; j < mida; ++j){
				tauler.setCasillaVal(i, j, caselles[i][j]);
			}
		}
	}
	
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
	public void new_game(String nompartida, String nomkenken){
		//pre: current user ja està inicialitzat
		Partida nova = new Partida(nompartida,currentUser.get_usuari());
		currentUser.assignar_nova_partida(nova);
		TableroH tablero = creaTauler(nomkenken);
		nova.setTauler(tablero);
		play();
	}
	
	public void load_game(String nomsaved){
		Partida load = new Partida(nomsaved,currentUser.get_usuari());
		currentUser.assignar_nova_partida(load);
		String st[] = dataEngine.getPartidaHeaderInfo(nomsaved,currentUser.get_usuari());
		load.setTime(Integer.parseInt(st[0]));
		TableroH tauler = creaTauler(st[1]);
		omplirTauler(tauler,nomsaved);
		load.setTauler(tauler);
		play();
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
