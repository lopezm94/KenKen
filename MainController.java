//import Excepcions.*;
//import Persistencia.Gestio_Dades;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Scanner;

/**
*@version 1.0
*@author Marc Ortiz
*@author Joan Grau
*@author reyes vera(interficie)
*/
public class MainController{
	//Definici� variables globals i controladors que necessitarem:
	private Perfil currentUser;
	private GestioDadesH dataEngine;
	Scanner in;
	//Login_Usuari:
	//Comprovem que l'usuari existeixi a la base de dades o sino entrar com  a convidat
	private static int action(Scanner in1) throws IOException{
		System.out.println(	"1 - Introduir casella\n"+
							"2 - Comprovar solucio\n"+
							"3 - Guardar i sortir\n"+
							"4 - Mostrar solucio\n"+
							"0 - Sortir");
		int res = in1.nextInt();
		return res;
	}
	//Login_Usuari:
	//Comprovem que l'usuari existeixi a la base de dades o sino entrar com  a convidat
	private void imprimir_tauler(){
		int mida = currentUser.get_partida().getTauler().size();
		for(int i=0; i < currentUser.get_partida().getTauler().getNumAreas(); ++i){
			System.out.println(i+": "+currentUser.get_partida().getTauler().getArea(i).get_operacio()+" "+currentUser.get_partida().getTauler().getArea(i).get_resultat());
		}
		for(int i=0; i < mida; ++i ){
			for(int j=0; j < mida; ++j){
				System.out.print("|"+currentUser.get_partida().getTauler().getCasillaVal(i, j)+","+currentUser.get_partida().getTauler().getAreaID(i, j)+"|");
			}
			System.out.print("\n");
		}
		System.out.print("\n");
	}
	private void imprimir_solucio(){
		int mida = currentUser.get_partida().getTauler().size();
		for(int i=0; i < mida; ++i ){
			for(int j=0; j < mida; ++j){
				System.out.print(" "+currentUser.get_partida().getTauler().getCasillaSol(i, j));
			}
			System.out.print("\n");
		}
		System.out.print("\n");
	}

	private int tiempo(long tiempo_start){
		long time_end = System.currentTimeMillis();
		int timeextra = currentUser.get_partida().getTime();
		tiempo_start  = time_end/1000 - tiempo_start/1000;
		timeextra += (int)tiempo_start;
		return timeextra;
	}
	
	public Boolean es_fija(int x, int y){
		return currentUser.get_partida().getTauler().casillaIsFija(x, y);
	}
	
	public int numC (int x , int y){
		return currentUser.get_partida().getTauler().getCasillaVal(x, y);
	}
	
	public void posar_pos(int x, int y, int valor){
		currentUser.get_partida().getTauler().setCasillaVal(x,y,valor);
	}
	
	public int area(int x, int y){
		return currentUser.get_partida().getTauler().getAreaID(x, y);
	}
	
	public int tam(){
		return currentUser.get_partida().getTauler().getNumAreas();
	}
	
	public Boolean comp(){
		Boolean correcte = currentUser.get_partida().getTauler().tableroCheck() &&
		currentUser.get_partida().getTauler().numerosCheck();
		if(correcte) {
				return true;
		}
		return false;
	}
	
	public void imprimir(){
		imprimir_tauler();
		imprimir_solucio();
	}
	
	private void play(String nomkenken){
		long time_start;
		time_start = System.currentTimeMillis();
		Boolean end = false;
		imprimir_tauler();
		while(!end){
			try {
				switch(action(in)){
				case 0:
					end = true;
					break;
				case 1:
					//introduir casella
					System.out.println("Introdueix el valor de la casella: \n"+
										"posx,posy,valor_nou (ex: 0,2,8)");
					int x = in.nextInt();
					int y = in.nextInt();
					int valor = in.nextInt();
					currentUser.get_partida().getTauler().setCasillaVal(x,y,valor);
					imprimir_tauler();
					System.out.println("Temps: "+tiempo(time_start));
					break;
				case 2:
					Boolean correcte = currentUser.get_partida().getTauler().tableroCheck() &&
										currentUser.get_partida().getTauler().numerosCheck();
					if(correcte) {
						System.out.println("KenKen correcte! Felicitats");
						System.out.println("Temps total: "+tiempo(time_start));
						end = true;
					}else{
						System.out.println("KenKen incorrecte!");
					}
					break;
				case 3:
					System.out.println("Temps: "+tiempo(time_start));
					currentUser.get_partida().setTime(tiempo(time_start));
					dataEngine.guardarPartida(currentUser,nomkenken);
					end = true;
					break;
				case 4:
					imprimir_solucio();
					break;
				default:
				    System.out.println("Accion introducida no es correcta");
				    break;
				}
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		in.close();
	}
	
	public int tamany(){
		return currentUser.get_partida().getTauler().getFiles();
	}
	
	public String nomKenken(){
		return currentUser.get_partida().getNomKenken();
	}
	
	public void guarda(String a){
		currentUser.get_partida().setTime(tiempo(0));
		dataEngine.guardarPartida(currentUser,a);
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
			/*Creem cada area*/
			String[] operacions = dataEngine.getOperacions(nomkenken);
			int total_areas = operacions.length;
			int idarea = 0;
			while (idarea < total_areas){
				String varS = operacions[idarea];
				char var2[] = varS.toCharArray();
				Area a = AreaBuilder.newArea(idarea,var2[0]);
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
	      /*  if (in != null) {
	        in.close();
	        }// Close the file scanner.*/
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
           
        public void guest(){
               currentUser = new Perfil();
        }
        
        public Boolean user_exists(String a){
        	String[] st = dataEngine.getProfileInfo(a, ".", "Profiles");
            if(dataEngine.existsUser(st,a)){
            	return true;
            }	
            return false;
        }
        
        
        public Perfil login_reg(String a, String b, String c) throws IOException{
            String nomUser = a;
            String pass = b;
            String passr = c;
            if(pass.equals(passr)){
                    String[] st = dataEngine.getProfileInfo(nomUser, ".", "Profiles");
                    if(dataEngine.existsUser(st,nomUser)){
                    }else{
                            try{
                                    GestioDadesH.Escribir_string(nomUser+" "+pass+" 0 0 0", "\n", "Profiles", ".");
                            }catch(IOException e){
                                    System.out.println(e.toString());
                            } catch (FicheroNoExiste f) {
                                    f.printStackTrace();
                            }
                            try{
                                    GestioDadesH.Crear_directorio(nomUser,"./Games");
                            } catch (FicheroYaExistente e) {
                                    e.printStackTrace();
                            }
                            currentUser = new Perfil(nomUser,pass);
            }
                   
        }
            return currentUser; 
        }
        
        public Boolean user_ok(String a, String b){
        	String[] st = dataEngine.getProfileInfo(a, ".", "Profiles");
        	if(dataEngine.existsUser(st,a)){
				if(dataEngine.getPassByToken(st).equals(b)){
					return true;
				}
        	}
        	return false;
        }
        
	public Perfil login(String a, String b){
		int control = 0;
		while(control == 0){
			//System.out.println("Tens un usuari? (0-no, 1-si)");
			//int te_usuari = in.nextInt();
			//if(te_usuari == 1){
				//System.out.println("Entra el nom d'usuari");
				//String nomUser = in.next();
				//System.out.println("Entra la contasenya");
				//String pass = in.next();
				String[] st = dataEngine.getProfileInfo(a, ".", "Profiles");
				//Control String tokenizer
				if(dataEngine.existsUser(st,a)){
					//Buscar les dades al controlador gestio, si no el troba,
					//preguntar si vol crear un nou usuari
					if(dataEngine.getPassByToken(st).equals(b)){
						currentUser = new Perfil(a,b);
						control = 1;
					}
				//}
			}/*else if(te_usuari == 0){
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
			}*/
		}
		return currentUser;
	}
	public MainController(){
		in = new Scanner(System.in);
		Perfil p = new Perfil();
		dataEngine = new GestioDadesH();

	}
	public void new_game(String nompartida, String nomkenken){
		//pre: current user ja esta inicialitzat
		Partida nova = new Partida(nompartida,currentUser.get_usuari(),nomkenken);
		currentUser.assignar_nova_partida(nova);
		TableroH tablero = creaTauler(nomkenken);
		nova.setTauler(tablero);
		//play(nomkenken); /*cambiar para inter*/
	}
	
	public String areaTipo(int x, int y){
		String a = null;
		a = Character.toString(currentUser.get_partida().getTauler().getArea(x, y).get_operacio());
		a += "   ";
		a += Integer.toString(currentUser.get_partida().getTauler().getArea(x, y).get_resultat());
		return a;
	}
	
	public void genera(String nomkenken, String a, int b){
		KenkenHandler ke = new KenkenHandler();
		TableroH tablero = ke.generateAndSolveKenken(b,a);
		dataEngine.guardar_kenken(tablero,nomkenken);
	}
	public Boolean generaMan(Generar a,String nomkenken){
		TableroH tablero = a.getTablero();
		KenkenHandler ke = new KenkenHandler();
		ke.solveKenken(tablero);
		return true;
	}
	
	public void guarda_gen (Generar a, String nomkenken){
		TableroH tablero = a.getTablero();
		dataEngine.guardar_kenken(tablero,nomkenken);
	}
	
	public void load_game(String nomsaved){
		Partida load = new Partida(nomsaved,currentUser.get_usuari(),nomKenken());
		currentUser.assignar_nova_partida(load);
		String st[] = dataEngine.getPartidaHeaderInfo(nomsaved,currentUser.get_usuari(),0);
		load.setTime(Integer.parseInt(st[0]));
		String st1[] = dataEngine.getPartidaHeaderInfo(nomsaved,currentUser.get_usuari(),1);
		TableroH tauler = creaTauler(st1[0]);
		omplirTauler(tauler,nomsaved);
		load.setTauler(tauler);
		//play(st1[0]);   /*cambiar para inter*/
		//Load an existing game
	}


	public void create_kenken(){
		Generar g = new Generar();
		TableroH th =g.genera();
		System.out.println("Quieres guardar y jugar a tu KenKen? (0-1)");
		if(in.nextInt() == 1){
			System.out.println("Introduce el nombre del kenken:");
			String st = in.next();
			System.out.println("Introduce el nombre de tu partida:");
			String s2t = in.next();
			Partida nova = new Partida(s2t,currentUser.get_usuari(),nomKenken());
			currentUser.assignar_nova_partida(nova);
			nova.setTauler(th);
			dataEngine.guardar_kenken(th,st);
			play(st);
		}
	}
	
	
	public void delete_user(){
		//delete my current username
		int linea = dataEngine.getLine(currentUser.get_usuari(),".","Profiles.txt");
		try{
			Gestio_Dades.modificarString("Profiles", ".", linea, "\n", "\n");
		}catch(IOException e){
			//System.out.println(e.toString());
		}catch(FicheroNoExiste f){
			//System.out.println("El fichero no existe\n");
		}catch(FicheroYaExistente f1){
			//System.out.println("El fichero ya existe\n");
		}
	}
	public void show_tutorial(){
		//show a simple text
		//System.out.println("Tutorial per jugar:\n http://www.kenkenpuzzle.com/howto/solve");
		
		Desktop enlace=Desktop.getDesktop();
		try {
            enlace.browse(new URI("http://www.kenkenpuzzle.com/howto/solve"));
		} catch (IOException | URISyntaxException e) {
			e.getMessage();
		}
	}
}
