import java.util.Random;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Collections;
import java.lang.Math;

/**
*<h1>KenkenHandler</h1>
*Se encarga de crear y resolver Kenkens.
*
*@version 1.0
*@author Juan Lopez
*/
public class KenkenHandler {

	private enum Direccion {
    Left, Up, Right, Down
	}


	/**
	*Devuelve la dificultad del tablero basandose en nuestra definicion.
	*
	*@param tablero Tablero
	*@return String ["Facil"|"Medio"|"Dificil"]
	*/
	public static String getDifficulty(TableroH tablero) {
		String res;
		int count = KenkenHandler.getEmptyCells(tablero);
		if (count <= 16) {
			res = "Facil";
		}
		else if (count <= 48) {
			res = "Medio";
		}
		else {
			res = "Dificil";
		}
		return res;
	}


	public static int getEmptyCells(TableroH tablero) {
		int count = 0;
		String res;
		for (int i=0; i<tablero.size(); i++) {
      for (int j=0; j<tablero.size(); j++) {
        if (!tablero.casillaIsFija(i,j))
          count++;
      }
    }
		return count;
	}


	/**
	*Metodo que resuelve el Kenken por medio de DFS usando el BoardEngine.
	*En cada llamada se encarga de propagar un valores en la casilla de la posicion
	*(x,y) hasta encontrar encontrar un resultado.
	*
	*@param x Posicion en el eje x de la casilla.
	*@param y
	*@param be BoardEngine que usara.
	*@return Boolean
	*/
	private static Boolean DFSSolve(TableroH tablero, int x, int y, BoardEngine be) {
		if (x == tablero.size()) {
			be.storeSolution();
			return true;
		}
		Boolean done = false;
		int newy = (y+1)%tablero.size();
		int newx = x;
		if (newy == 0) newx++;
		ArrayList<Integer> domain = new ArrayList<Integer>(be.getDomain(x,y));
		Collections.shuffle(domain);
		for (Integer value : domain) {
			if (be.propagate(x,y,value))
				done = KenkenHandler.DFSSolve(tablero,newx,newy,be);
			be.depropagate(x,y);
			if (done)
				break;
		}
		return done;
	}


	/**
	*Funcion recursiva que resuelve el Kenken cuya funcion de busqueda viene
	*dada por getNext().
	*
	*@param tablero Tablero.
	*@param x Posicion en el eje X.
	*@param y Posicion en el eje Y.
	*@param ke KenkenEngine.
	*@return Boolean True si el kenken tiene solucion.
	*/
	private static Boolean domainSolve(TableroH tablero, int x, int y, KenkenEngine ke) {
		if (x == tablero.size()) {
			ke.storeSolution();
			return true;
		}
		Boolean done = false;
		ArrayList<Integer> domain = new ArrayList<Integer>(ke.getDomain(x,y));

		for (Integer value : domain) {
			if (ke.propagate(x,y,value)) {
				Pair<Integer,Integer> pos = KenkenHandler.getNext(tablero,ke);
				done = KenkenHandler.domainSolve(tablero,pos.getFirst(),pos.getSecond(),ke);
			}
			ke.depropagate(x,y);
			if (done)
				break;
		}
		return done;
	}


	/**
	*Obtiene la posicion de la siguiente casilla con dominio
	*de menor cardinalidad
	*
	*@param tablero Tablero.
	*@param ce ConstraintEngine.
	*@return Pair<Integer,Integer> Posicion de la siguiente casilla con dominio
	*de menor cardinalidad.
	*/
	private static Pair<Integer,Integer> getNext(TableroH tablero, ConstraintEngine ce) {
		int x=tablero.size(),y=tablero.size();
		int min = 0x7FFFFFFF;
		for (int i=0; i<tablero.size(); i++) {
			for (int j=0; j<tablero.size(); j++) {
				if (tablero.getCasillaVal(i,j) != -1)
					continue;
				if (tablero.getCasillaDomain(i,j).size() < min) {
					x = i;
					y = j;
					min = tablero.getCasillaDomain(i,j).size();
				}
			}
		}
		return new Pair<Integer,Integer>(x,y);
	}


	/**
	*Resuelve el Kenken y guarda la solucion dentro del tablero.
	*
	*@return Boolean true si el tablero tiene solucion, false en caso contrario.
	*/
	public static Boolean solveKenken(TableroH tablero){
		KenkenEngine ke = new KenkenEngine(tablero);
		return KenkenHandler.domainSolve(tablero,0,0,ke);
	}


	/**
	*Construye un tablero Kenken desde cero.
	*
	*@param size Tamaño del nuevo tablero.
	*@param dificultad La dificultad del tablero(Facil,Medio,Dificil).
	*@return TableroH Devuelve un kenken sin resolver.
	*/
	public static TableroH generateAndSolveKenken(Integer size, String dificultad) {
		TableroH tablero = new TableroH(size);
		BoardEngine be = new BoardEngine(tablero);

		KenkenHandler.DFSSolve(tablero,0,0,be);
		KenkenHandler.setAreas(tablero);
		KenkenHandler.setDifficulty(tablero,dificultad);
		return tablero;
	}


	/**
	*Elige las areas dentro del tablero.
	*
	*@param tablero Tablero.
	*/
	private static void setAreas(TableroH tablero) {
		Random rand = new Random();

		for (int i=0; i<tablero.size(); i++) {
			for (int j=0; j<tablero.size(); j++) {
				if (tablero.getAreaID(i,j) != -1)
					continue;
				int size;
				char op;
				Area area;
				LinkedList<Pair<Integer,Integer>> bag = new LinkedList<Pair<Integer,Integer>>();
				size = rand.nextInt(6)+1;
				KenkenHandler.randomDFS(i,j,tablero,bag,size);
				op = KenkenHandler.pickOpAndRes(tablero,bag);
				area = AreaBuilder.newArea(tablero.getNumAreas(),op);
				for (Pair<Integer, Integer> b : bag) {
					area.afegir_casella(
						tablero.getCasilla(b.getFirst(),b.getSecond())
					);
				}
				tablero.afegirArea(area,0);
				area.calcular_resultat();
			}
		}
	}


	/**
	*Rellena el tablero de tal forma que quede de la dificultad deseada.
	*
	*@param tablero Tablero.
	*@param dificultad Dificultad.
	*/
	private static void setDifficulty(TableroH tablero, String dificultad) {
		int i,limit,minimum,maximum;
		Random rand = new Random();
		LinkedList<Casilla> casillas;

		limit = tablero.size()*tablero.size();
		if (dificultad.equals("Facil"))
			limit -= rand.nextInt(17);
		else if (dificultad.equals("Medio"))
			limit -= rand.nextInt(32)+17;
		else
			limit -= rand.nextInt(32)+49;

		switch (tablero.size()){
			case 3:
				minimum = 0;
				maximum = 0;
				break;
			case 4:
				minimum = 0;
				maximum = 4;
				break;
			case 5:
				minimum = 0;
				maximum = 9;
				break;
			case 6:
				minimum = 0;
				maximum = 20;
				break;
			case 7:
				minimum = 0;
				maximum = 33;
				break;
			case 8:
				minimum = 7;
				maximum = 48;
				break;
			case 9:
				minimum = 9;
				maximum = 65;
				break;
			default:
				minimum = tablero.size();
				maximum = tablero.size()/3;
				break;
		}
		limit = Math.max(limit,minimum);
		limit = Math.min(limit,maximum);

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
	private static void randomDFS(int x, int y, TableroH tablero, LinkedList<Pair<Integer,Integer>> bag, int size) {
		if (tablero.getAreaID(x,y) != -1 || bag.size() == size)
			return;
		tablero.setId(tablero.getNumAreas(),x,y);
		bag.add(new Pair<Integer,Integer>(x,y));
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
					KenkenHandler.randomDFS(x-1,y,tablero,bag,size);
					break;
				case Up:
					KenkenHandler.randomDFS(x,y-1,tablero,bag,size);
					break;
				case Right:
					KenkenHandler.randomDFS(x+1,y,tablero,bag,size);
					break;
				default:
					KenkenHandler.randomDFS(x,y+1,tablero,bag,size);
					break;
			}
		}
	}


	/**
	*Decide que operacion colocarle a cada area del tablero y con esa operacion
	*el correspondiente resultado.
	*
	*@param bag Area.
	*/
	private static char pickOpAndRes(TableroH tablero, LinkedList<Pair<Integer,Integer>> bag) {
		int op;
		char res;
		Random rand = new Random();
		switch (bag.size()) {
			case 1:
				res = '.';
				break;
			case 2:
				int a = tablero.getCasilla(bag.get(0).getFirst(),bag.get(0).getSecond()).getSolucion();
				int b = tablero.getCasilla(bag.get(1).getFirst(),bag.get(1).getSecond()).getSolucion();
				if (((a>b) && (a%b==0)) || ((b>a) && (b%a==0)))
					res = '/';
				else
					res = '-';
				break;
			default:
				op = rand.nextInt(2);
				switch(op) {
					case 0:
						res = '+';
						break;
					default:
						res = '*';
						break;
				}
				break;
		}
		return res;
	}

}
