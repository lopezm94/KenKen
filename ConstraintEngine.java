import java.util.Arrays;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.Map;
import java.util.Stack;
import java.util.HashMap;
import java.util.LinkedList;
import java.lang.RuntimeException;

/**
*<h1>ConstraintEngine</h1>
*Implementa un motor de restricciones para resolver juegos de tableros.
*
*@version 2.0
*@author Juan López
*/
public abstract class ConstraintEngine {

  protected static final Boolean vertical = false;
  protected static final Boolean horizontal = true;

  protected TableroH board;
  protected ArrayList<ArrayList<HashSet<Integer>>> cellDomain;

  protected Stack<Pair<Integer,LinkedList<Integer>>> log;
  protected HashMap<Integer,LinkedList<Integer>> logTrack;

  /**
  *Construye un nuevo motor, en base a un tablero de kenken.
  *Tomando como referencia el tablero y copiando los dominions de las casillas.
  *
  *@param board Es un tablero previamente creado.
  */
  public ConstraintEngine(TableroH board) {
    this.board = board;
    this.log = new Stack<Pair<Integer,LinkedList<Integer>>>();
    this.logTrack = new HashMap<Integer,LinkedList<Integer>>();

    this.initDomain();
    this.cellDomain = new ArrayList<ArrayList<HashSet<Integer>>>(this.board.size());
    for(int i=0; i<this.board.size(); i++) {
      this.cellDomain.add(i,new ArrayList<HashSet<Integer>>(this.board.size()));
      for(int j=0; j<this.board.size(); j++) {
        this.cellDomain.get(i).add(j,new HashSet<Integer>(this.board.getCasillaDomain(i,j)));
      }
    }

    for (int i=0,limit=this.board.size()*this.board.size(); i<limit; i++) {
      this.logTrack.put(i, new LinkedList<Integer>());
    }
  }


  /**
  *Asigna la solucion de cada casilla al valor que tienen.
  */
  public void storeSolution() {
    for (int i=0; i<this.board.size(); i++) {
      for (int j=0; j<this.board.size(); j++) {
        this.board.setCasillaSol(i,j,this.board.getCasillaVal(i,j));
      }
    }
  }


  /**
  *Devuelve una coleccion con el dominio de una casilla.
  *
  *@param x Posicion de la casilla en el eje X.
  *@param y Posicion de la casilla en el eje Y.
  *@return HashSet Coleccion con el dominio de la casilla.
  */
  public HashSet<Integer> getDomain(int x, int y) {
    return this.cellDomain.get(x).get(y);
  }


  /**
  *Devuelve una coleccion con el dominio de una casilla.
  *
  *@param pos Posicion de la casilla.
  *@return HashSet Coleccion con el dominio de la casilla.
  */
  public HashSet<Integer> getDomain(Pair<Integer,Integer> pos) {
    return this.getDomain(pos.getFirst(),pos.getSecond());
  }


  /**
  *Asigna el valor dada a la casilla en la posicion (x,y).
  *Propaga el valor para reducir los dominios de las casillas en el tablero.
  *
  *@param x Posicion en el eje X de el tablero.
  *@param y Posicion en el eje Y de el tablero.
  *@param value Valor a asignar y propagar.
  *@return Boolean Devuelve false si captura alguna violacion regla,
  *true en caso contrario.
  */
  public abstract Boolean propagate(int x, int y, int value);


  /**
  *Devuelve los cambios de la propagacion.
  *
  *@param x Posicion en el eje X de el tablero.
  *@param y Posicion en el eje Y de el tablero.
  */
  public void depropagate(int x, int y) {
    if (!this.board.casillaIsFija(x,y))
      this.board.setCasillaVal(x,y,-1);

    Pair<Integer,Integer> pos;
    HashSet<Integer> aux;

    while (log.peek() != null) {
      pos = this.getMatrixPos(log.peek().getFirst());
      aux = this.getDomain(pos);
      for (Integer  value :  log.pop().getSecond())
        aux.add(value);
    }
    log.pop();
  }


  /**
	*Iniciliza el dominio de las casillas para reducir el espacio de busqueda.
	*/
	protected abstract void initDomain();


  /**
  *Cambia de una posicion matricial a una posicion lineal.
  *
  *@param x Entero de la posicion en el eje X.
  *@param y Entero de la posicion en el eje Y.
  *@return int Posicion linear.
  */
  protected int getLinearPos(int x, int y) {
    return x + y*this.board.size();
  }


  /**
  *Cambia de posicion lineal a posicion matriceal.
  *
  *@param pos Posicion lineal.
  *@return Pair<Integer,Integer> Posicion matriceal.
  */
  protected Pair<Integer,Integer> getMatrixPos(int pos) {
    int x,y;
    x = pos % this.board.size();
    y = pos/this.board.size();
    return new Pair<Integer,Integer>(x,y);
  }


  /**
  *Devuelve un string que representa el estado interno de la clase.
  *
  *@return String Estado interno de la clase.
  */
  @Override
  public String toString() {
    return
      "tablero:\n" + this.board.toString() + "\n"
      + "dominio de casillas del motor:\n" + this.cellDomain.toString() + "\n"
      + "logtrack: \n" + this.logTrack.toString() + "\n"
      + "log del motor:\n" + this.log.toString()
      + "\n";
  }
}
