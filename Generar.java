import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Random;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

/**@version 1.0
*@author Reyes Vera
*/


public class Generar {

	TableroH tablero;
	Timer timer;


	public void mida(int a){
		tablero = new TableroH(a);

		for (int i = 0;i< tablero.size();++i){
			for (int j = 0; j < tablero.size();++j){
				Casilla cas1 = new Casilla();
				tablero.setCasilla(cas1,i,j);
			}
		}
	}

	public String areaTipo(int r, int v){
		String a;
		char c = tablero.getArea(r,v).get_operacio();
		if (c == '.') c = 'F';
		a = Character.toString(c);
		a += "  ";
		a += Integer.toString(tablero.getArea(r,v).get_resultat());
		return a;
	}


	public int getNumAreas(){
		return tablero.getNumAreas();
	}

	public int getAreaID(int r, int v){
		return tablero.getAreaID(r, v);
	}

	public int getCasillaSol(int r, int v){
		return tablero.getCasillaSol(r, v);
	}

	public void cambia(TableroH a){
		tablero = a;
	}

	public int mida(){
		return tablero.getFiles();
	}
	public int num(int x, int y){
		return tablero.getCasillaSol(x, y);
	}

	public void fija(int a, int x, int y){
		tablero.setCasillaFija(a, x, y);
	}

	public void areas(int at, char op, int res){
		Area a = AreaBuilder.newArea(at,op);
		if (tablero.getNumAreas() <= at){
			tablero.afegirArea(a,res);
		}
	}

	public void colocA (int a, int x, int y){
		tablero.setId(a-1,x,y);
	}

	public void colocAreas(){
		tablero.config();
	}

	public TableroH getTablero(){
		return tablero;
	}

	public Boolean ok(){
		for (int i = 0; i < mida();++i){
			for (int j = 0; j < mida(); ++j){
				if (tablero.getAreaID(i, j) == -1) return false;
			}
		}
		return true;
	}

	TimerTask task = new TimerTask() {
		public void run() {
			Desktop enlace=Desktop.getDesktop();
	        System.out.println("Has visto las noticias de hoy? VAMOS A VER COMO ESTA EL MUNDO :)");
	        for (int i = 0; i < 1000; ++i);
	        try {
	                enlace.browse(new URI("http://www.elperiodico.com/es/"));
	        } catch (IOException | URISyntaxException e) {
	            e.getMessage();
	        }
		}

		};
    }
