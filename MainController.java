//import Excepcions.*;
//import Persistencia.Gestio_Dades;
//import java.awt.Desktop;
import java.io.IOException;
//import java.net.URI;
//import java.net.URISyntaxException;
import java.util.Scanner;

/**
*@version 1.0
*@author Marc Ortiz
*@author Joan Grau
*@author reyes vera(interficie)
*/
public class MainController{
	private static final MainController mc = new MainController();
	
	//Definició variables globals i controladors que necessitarem:
	private GestionUsuario gestionus;
	private GestioPartida gestionpart;
	Scanner in;
	
	
	private MainController(){
		gestionus = new GestionUsuario();
	}
	
	public static MainController getInstance() {
		return mc;
	}
	
	public Boolean newUser(String user, String psw){
		try {
			gestionus.newUser(user, psw);
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return false;
		}
	}
	
	public Boolean login(String user, String psw){
		try {
			gestionus.login(user, psw);
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return false;
		}
	}
	
	public void CrearPartida(String nomkenken, String nompartida){
		gestionpart = new GestioPartida(nompartida,true,nomkenken,gestionus.getProfile().get_usuari());
		gestionus.assignarPartida(gestionpart.getPartida());
		gestionpart.start();
	}
	
	public void load_game(String nompartida){
		gestionpart = new GestioPartida(nompartida,false,null,gestionus.getProfile().get_usuari());
		gestionus.assignarPartida(gestionpart.getPartida());
		gestionpart.start();
	}
	
	public long getTemps(){
		return gestionpart.getTime();
	}
	
	public void posar_pos(int x, int y, String valor){
		gestionpart.setValue(x,y,Integer.parseInt(valor));
	}
	
	public void save(){
		gestionpart.saveGame(gestionus.getProfile());
	}
	
	public void sortir(){
		gestionpart.TancarPartida();
	}
	
<<<<<<< HEAD
	public int getCas(int x, int y){
		return currentUser.get_partida().getTauler().getCasilla(x, y).getSolucion();
	}
	
	public void actualizaRank(){
		/*int b = currentUser.     acabar<---------------------------------------
		currentUser.add_puntuacio(b);*/
	}
	
	public void imprimir(){
		imprimir_tauler();
		imprimir_solucio();
=======
	public int area(int x, int y){
		return gestionpart.getAreaID(x, y);
>>>>>>> 69cd460d61e1de72a3a0dd918a92c6acc9c6c1eb
	}
	
	public int tam(){
		return gestionpart.getNumAreas();
	}
	
	public int tamany(){
		return gestionpart.getTamany();
	}
	
	public Boolean fija(int x, int y){
		return gestionpart.fija(x, y);
	}
	
<<<<<<< HEAD
	public void guarda(String a){
		currentUser.get_partida().setTime(tiempo(0));
		dataEngine.guardarPartida(currentUser,a);
	}

	private TableroH creaTauler(String nomkenken){
		int mida = dataEngine.getMidaKenken(nomkenken);
		System.out.println("mida: " + mida);
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
					System.out.println("valor: " + valor + " Fija: " + fija + " Sol: "+ sol);
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
=======
	public int num(int x, int y){
		return gestionpart.getValue(x,y);
>>>>>>> 69cd460d61e1de72a3a0dd918a92c6acc9c6c1eb
	}
	
	public Boolean comp(){
		return gestionpart.check();
	}
	
	public String areaTipo(int x, int y){
		String a = null;
		a = Character.toString(gestionpart.getOperacio(x, y));
		a += "   ";
		a += Integer.toString(gestionpart.getResultatArea(x, y));
		return a;
	}
           
    public void guest(){
    	gestionus.invitado();
    }

}
