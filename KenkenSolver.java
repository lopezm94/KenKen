import java.util.LinkedList;

/**
*<h1>KenkenSolver</h1>
*Se encarga de resolver un Kenken y guardar la solucion en el tablero.
*
*@version 1.0
*@author Reyes Vera y Juan Lopez
*/
public class KenkenSolver {

	//Referencia al tablero pasado en la construccion.
	private TableroH tablero;

	/**
	*Construye un KenkenSolver para el tablero pasado.
	*/
	public KenkenSolver(TableroH tablero){
		this.tablero = tablero;
		this.initDomain();
	}


	/**
	*Resuelve el Kenken y guarda la solucion dentro del tablero.
	*
	*@return Boolean true si el tablero tiene solucion, false en caso contrario.
	*/
	public Boolean solveKenken(){
		ConstraintEngine ce = new ConstraintEngine(tablero);
		return this.FuncionRecursiva(0,0,ce);
	}


	/**
	*Funcion que resuelve el Kenken por medio de DFS usando el ConstraintEngine.
	*En cada llamada se encarga de propagar un valores en la casilla de la posicion
	*(x,y) hasta encontrar encontrar un resultado.
	*
	*@param x Posicion en el eje x de la casilla.
	*@param y
	*@param ce ConstraintEngine que usara.
	*@return Boolean
	*/
	private Boolean FuncionRecursiva(int x, int y, ConstraintEngine ce) {
		if (y == this.tablero.size()) {
			ce.storeSolution();
			return true;
		}
		Boolean check = false;
		Boolean done = false;
		int newx = (x+1)%this.tablero.size();
		int newy = y;
		if (newx == 0) newy++;

		for (Integer value : new LinkedList<Integer>(ce.getDomain(x,y))) {
			check = ce.propagate(x, y, value);
			if (check)
				done = this.FuncionRecursiva(newx,newy,ce);
			ce.depropagate(x,y);
			if (done) break;
		}
		return done;
	}


	/**
	*Iniciliza el dominio de las casillas para que puedan tener cualquier valor
	*entre 1 y el tamaño del tablero.
	*/
	private void initDomain(){
		for (int i = 0; i < tablero.size(); ++i){
			for (int j = 0; j < tablero.size();++j){
				for (int k = 1; k <= tablero.size();++k){
					tablero.getCasilla(i,j).addCan(k);
				}
			}
		}
	}

}
