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
*@author Reyes Vera y Juan Lopez
*/
public class KenkenHandler {

	public enum Direccion {
    Left, Up, Right, Down
	}


	/**
	*Soluciona un tablero.
	*
	*@param tablero Tablero.
	*@param ce ConstraintEngine.
	*@return Boolean Devuelve true si tiene solucion.
	*/
	private Boolean solve(TableroH tablero, ConstraintEngine ce) {
		if (ce instanceof BoardEngine) {
			return DFSSolve(tablero,0,0,(BoardEngine)ce);
		}
		else if (ce instanceof KenkenEngine) {
			return domainSolve(tablero,0,0,(KenkenEngine)ce);
		}
		else
			return false;
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
	private Boolean DFSSolve(TableroH tablero, int x, int y, BoardEngine be) {
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
				done = this.DFSSolve(tablero,newx,newy,be);
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
	private Boolean domainSolve(TableroH tablero, int x, int y, KenkenEngine ke) {
		if (x == tablero.size()) {
			ke.storeSolution();
			return true;
		}
		Boolean done = false;
		ArrayList<Integer> domain = new ArrayList<Integer>(ke.getDomain(x,y));
		/*
		if (domain.size() > 1)
			tablero.setCasillaFija(tablero.getCasillaSol(x,y),x,y);
		*/
		for (Integer value : domain) {
			if (ke.propagate(x,y,value)) {
				Pair<Integer,Integer> pos = this.getNext(tablero,ke);
				done = this.domainSolve(tablero,pos.getFirst(),pos.getSecond(),ke);
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
	private Pair<Integer,Integer> getNext(TableroH tablero, ConstraintEngine ce) {
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
	public Boolean solveKenken(TableroH tablero){
		KenkenEngine ke = new KenkenEngine(tablero);
		return this.solve(tablero,ke);
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

		this.solve(tablero,be);
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

		limit = Math.max(limit,tablero.size());

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
