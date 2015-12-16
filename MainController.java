//import Excepcions.*;
//import Persistencia.Gestio_Dades;
//import java.awt.Desktop;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
//import java.net.URI;
//import java.net.URISyntaxException;
import java.util.Scanner;
import java.util.Vector;

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
	TableroH tablero;
	Scanner in;
	
	public int getSolAt(int i, int j) {
		return this.gestionpart.getSol(i,j);
	}
	
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
	
	public void delete_user(){
		try {
			gestionus.DeleteUser();
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
	}
	
	
	public Boolean CrearPartida(String nomkenken, String nompartida) throws FicheroNoExiste{
		/*He posat exepcions quan no és correcte el nom del kenken*/
		if(gestionus.es_invitado() || !GestioDadesH.existeixPartida(nompartida,gestionus.getProfile().get_usuari())){
			try {
				gestionpart = new GestioPartida(nompartida,true,nomkenken,gestionus.getProfile().get_usuari());
			} catch (FicheroNoExiste e) {
				throw e;
			}
			gestionus.assignarPartida(gestionpart.getPartida());
			gestionpart.start();
			return true;
		}else{
			return false;
		}
	}

	public void load_game(String nompartida){
		try {
			gestionpart = new GestioPartida(nompartida,false,null,gestionus.getProfile().get_usuari());
		} catch (FicheroNoExiste e) {
			e.printStackTrace();
		}
		gestionus.assignarPartida(gestionpart.getPartida());
		gestionpart.start();
	}
	public void delete_game(){
		try {
			gestionpart.deleteGame();
		} catch (IOException e) {
			// TODO Auto-generated catch block
		} catch (FicheroNoExiste e) {
			// TODO Auto-generated catch block
		}
	}
	
	public Boolean genera(String nomkenken, String a, int b){
		TableroH tablero = KenkenHandler.generateAndSolveKenken(b, a);
		return GestioDadesH.guardar_kenken(tablero, nomkenken);
	}
	
	public TableroH getTab(){
		return tablero;
	}
	
	public Boolean generaMan(Generar a, String nomkenken){
		tablero = a.getTablero();
		return KenkenHandler.solveKenken(tablero);
		
	}
	
	public void guarda_gen(Generar a, String nomkenken){
		GestioDadesH.guardar_kenken(a.getTablero(), nomkenken);
	}
	
	public String getDifficulty(){
		return gestionpart.getDiff();
	}
	
	public int actualizar_punt(){
		int punt = (getVacias()*10000)/((int)getTemps()*tamany()*10);
		gestionus.afegirPunt(punt, getDifficulty());
		return punt;
	}
	
	public long getTemps(){
		return gestionpart.getTime();
	}
	
	public int getVacias(){
		return gestionpart.getVacias();
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
	
	public int area(int x, int y){
		return gestionpart.getAreaID(x, y);
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
	
	public int num(int x, int y){
		return gestionpart.getValue(x,y);
	}
	
	public int show(int x, int y){
		return gestionpart.getSol(x,y);
	}
	
	public Boolean comp(){
		return gestionpart.check();
	}
	
	public void neteja(){
		gestionpart.neteja();
	}
	
	public String areaTipo(int x, int y){
		String a = null;
		char c = gestionpart.getOperacio(x, y);
		if (c == '.') c = ' ';
		a = Character.toString(c);
		a += "  ";
		a += Integer.toString(gestionpart.getResultatArea(x, y));
		return a;
	}
	
	public String areaTipoA(int i){
		String b;
		b = Character.toString(gestionpart.getOperacioA(i));
		b += "   ";
		b += Integer.toString(gestionpart.getResultatAreaA(i));
		b += "  Area:" + Integer.toString(i);
		return b;
	}
           
    public void guest(){
    	gestionus.invitado();
    }
    
    public Boolean es_guest(){
    	return gestionus.es_invitado();
    }
    public String[] getPartides(){
    	return GestioDadesH.getPartides(gestionus.getProfile().get_usuari());
    }
    public String[] getKenKens(){
    	return GestioDadesH.getKenkens();
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
