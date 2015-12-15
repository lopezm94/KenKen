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
	
	public TableroH genera(){
		Scanner input = null;

		try {
			
			ControladorGen crida = new ControladorGen();
			
			tablero = new TableroH(crida.tamano());
					
			for (int i = 0;i< tablero.size();++i){
				for (int j = 0; j < tablero.size();++j){
					Casilla cas1 = new Casilla();
					tablero.setCasilla(cas1,i,j);
				}
			}
			
			int var = crida.numAreas();
			
			
				Boolean ok = false;
				int tam = var;
				Boolean edita = false;
				Boolean rand = false;
				while (! ok){
					if (var >  0){
						edita = true;
						int at = 0;
						while (at < var){
							Area a = crida.tipoArea(at);
							int res = crida.resArea();
							tablero.afegirArea(a,res);
							++at;
						}
					ok = true;
					}
					else if (var == 0){
					    tablero = KenkenHandler.generateAndSolveKenken(tablero.size(),crida.dif());
					    rand = true;
						ok = true;
					}
				}
				if (! rand){
					Boolean vect[] = new Boolean[tam];
					for (int i = 0; i < tam; ++i) vect[i] = false;
	
					int tamano = tam;
					if (crida.Casillas(1)){
						tablero = crida.modTab(tablero);
					}
					else { //Poner area a casillas aleatorio
						--tamano;
						rand = true;
						int num = (tablero.size())^2;
						for (int i = 0; i < tablero.size();++i){
							for (int j = 0; j < tablero.size(); ++j){
								tablero.setid(tamano,i,j);
								--num;
								if (tamano != 0){
									Random rnd = new Random();
									var = rnd.nextInt();
									var = Math.abs(var);
									if (var%tablero.size() == 0) --tamano;
									else if (num <= tamano) --tamano;
								}
							}
						}
					}
					
					
					if (! rand){
						if (crida.Casillas(2)){
							tablero = crida.modCas(tablero);
						}
					}
				}
				
				//tablero.checkarea();
				
/*----->*/				System.out.println("Vamos a comprobar que tu Kenken es correcto");
				
				//System.out.println(tablero);
				
				Boolean solu = false;
				
				timer = new Timer();
				timer.schedule(task, 10000);
				//pondremos tiempo a la hora de hacer la interficie grafica para que no se haga muy pesado el tiempo de espera de KenKens grandes
				if (KenkenHandler.solveKenken(tablero)){
					timer.cancel();
					System.out.println("Tu Kenken es correcto :)");
					solu = true;
				}
	/*------>*/			else System.out.println("Vuelve a intentarlo, tu Kenken no tiene solucion :(");

				//System.out.println(tablero);
				
				
				if (solu){
					System.out.println("Quieres ver la solucion de tu Kenken?");
					Boolean resol = false;
					edita = false;
					while (! resol){
						String varS = input.next();
						char var2[] = varS.toCharArray();
						if (var2[0] == 'S' && var2[1] == 'i'){
							resol = true;
							edita = true;
						}
						else if (var2[0] == 'N' && var2[1] == 'o') resol = true;
						else System.out.println("Como?");
					}
					if (edita){
						String res = "";
						for (int i = 0; i < tablero.size(); ++i){
							for (int j = 0; j < tablero.size(); ++j){
								res = res + tablero.getCasillaSol(i, j) + " ";
							}
							res = res + "\n";
						}
						System.out.println(res);
					}
				}
		}
		finally {
	          if (input != null) {
	            input.close();                      // Close the file scanner.
	        }
	}
		return tablero;

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
