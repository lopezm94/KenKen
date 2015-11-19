import java.util.Random;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Collections;

/**
*<h1>KenkenHandler</h1>
*Se encarga de crear y resolver Kenkens.
*
*@version 1.0
*@author Reyes Vera y Juan Lopez
*/
public class KenkenHandler {

	public enum Direccion {
    Left, Up, Right, Down
	}


	/**
	*Funcion que resuelve el Kenken por medio de DFS usando el ConstraintEngine.
	*En cada llamada se encarga de propagar un valores en la casilla de la posicion
	*(x,y) hasta encontrar encontrar un resultado.
	*
	*@param x Posicion en el eje x de la casilla.
	*@param y
	*@param ke ConstraintEngine que usara.
	*@return Boolean
	*/
	private Boolean funcionRecursiva(TableroH tablero, int x, int y, ConstraintEngine ce) {
		if (x == tablero.size()) {
			ce.storeSolution();
			return true;
		}
		Boolean done = false;
		int newy = (y+1)%tablero.size();
		int newx = x;
		if (newy == 0) newx++;
		ArrayList<Integer> domain = new ArrayList<Integer>(ce.getDomain(x,y));
		if (ce instanceof BoardEngine)
			Collections.shuffle(domain);
		for (Integer value : domain) {
			if (ce.propagate(x,y,value))
				done = this.funcionRecursiva(tablero,newx,newy,ce);
			ce.depropagate(x,y);
			if (done)
				break;
		}
		return done;
	}


	/**
	*Resuelve el Kenken y guarda la solucion dentro del tablero.
	*
	*@return Boolean true si el tablero tiene solucion, false en caso contrario.
	*/
	public Boolean solveKenken(TableroH tablero){
		KenkenEngine ke = new KenkenEngine(tablero);
		return this.funcionRecursiva(tablero,0,0,ke);
		//Poner un warning de unica solucion.
	}


	/**
	*Construye un tablero Kenken desde cero.
	*
	*@param size Tamaño del nuevo tablero.
	*@param dificultad La dificultad del tablero(Facil,Medio,Dificil).
	*@return TableroH Devuelve un kenken sin resolver.
	*/
	public TableroH generateAndSolveKenken(Integer size, String dificultad) {
		TableroH tablero = new TableroH(size);
		BoardEngine be = new BoardEngine(tablero);

		this.funcionRecursiva(tablero,0,0,be);
		this.setAreas(tablero);
		this.setDifficulty(tablero,dificultad);
		return tablero;
	}


	/**
	*Elige las areas dentro del tablero.
	*
	*@param tablero Tablero.
	*/
	private void setAreas(TableroH tablero) {
		int size;
		Area area;
		Random rand = new Random();

		for (int i=0; i<tablero.size(); i++) {
			for (int j=0; j<tablero.size(); j++) {
				if (tablero.getAreaID(i,j) != -1)
					continue;
				size = rand.nextInt(8)+1;
				area = new Area(tablero.getNumAreas(),'.');
				tablero.afegirArea(area,0);
				this.randomDFS(i,j,tablero,area,size);
				this.pickOpAndRes(area);
			}
		}
	}


	/**
	*Rellena el tablero de tal forma que quede de la dificultad deseada.
	*
	*@param tablero Tablero.
	*@param dificultad Dificultad.
	*/
	private void setDifficulty(TableroH tablero, String dificultad) {
		int i,limit;
		Random rand = new Random();
		LinkedList<Casilla> casillas;

		limit = tablero.size()*tablero.size();
		if (dificultad.equals("Facil"))
			limit -= rand.nextInt(17);
		else if (dificultad.equals("Medio"))
			limit -= rand.nextInt(32)+17;
		else
			limit -= rand.nextInt(32)+49;

		i = 0;
		casillas = tablero.getAllCasillas();
		Collections.shuffle(casillas);
		for (Casilla casilla : casillas) {
			if (i++ >= limit)
				break;
			casilla.setValor(casilla.getSolucion());
			casilla.setFija(true);
		}
	}


	/**
	*Elige las delimitaciones de las areas.
	*
	*@param x Posicion en el eje X.
	*@param y Posicion en el eje Y.
	*@param tablero Tablero.
	*@param size tamaño del area a asignar.
	*/
	private void randomDFS(int x, int y, TableroH tablero, Area area, int size) {
		if (tablero.getAreaID(x,y) != -1 || area.get_tamany() == size)
			return;
		tablero.setid(tablero.getNumAreas()-1,x,y);
		ArrayList<Direccion> dirs = new ArrayList<Direccion>();
		if (x>0 && tablero.getAreaID(x-1,y) == -1)
			dirs.add(Direccion.Left);
		if (y>0 && tablero.getAreaID(x,y-1) == -1)
			dirs.add(Direccion.Up);
		if (x+1<tablero.size() && tablero.getAreaID(x+1,y) == -1)
			dirs.add(Direccion.Right);
		if (y+1<tablero.size() && tablero.getAreaID(x,y+1) == -1)
			dirs.add(Direccion.Down);

		Collections.shuffle(dirs);
		for (Direccion dir : dirs) {
			switch(dir) {
				case Left:
					this.randomDFS(x-1,y,tablero,area,size);
					break;
				case Up:
					this.randomDFS(x,y-1,tablero,area,size);
					break;
				case Right:
					this.randomDFS(x+1,y,tablero,area,size);
					break;
				default:
					this.randomDFS(x,y+1,tablero,area,size);
					break;
			}
		}
	}


	/**
	*Decide que operacion colocarle a cada area del tablero y con esa operacion
	*el correspondiente resultado.
	*
	*@param area Area.
	*/
	private void pickOpAndRes(Area area) {
		int op,size = area.get_tamany();
		Random rand = new Random();
		switch (size) {
			case 1:
				area.set_operacio('.');
				break;
			case 2:
				int a = area.get_casella(0).getSolucion();
				int b = area.get_casella(1).getSolucion();
				if (((a>b) && (a%b==0)) || ((b>a) && (b%a==0)))
					area.set_operacio('/');
				else
					area.set_operacio('-');
				break;
			default:
				op = rand.nextInt(2);
				switch(op) {
					case 0:
						area.set_operacio('+');;
						break;
					case 1:
						area.set_operacio('*');;
						break;
				}
				break;
		}
		area.calcular_resultat();
	}

}
