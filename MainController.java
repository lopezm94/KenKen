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
