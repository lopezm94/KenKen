import java.util.Arrays;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.Map;
import java.util.Stack;
import java.util.HashMap;
import java.util.LinkedList;
import java.lang.RuntimeException;
//import java.lang.CloneNotSupportedException;

/**
*<h1>ConstraintEngine</h1>
*Implementa un motor de restricciones para resolver el juego KenKen.
*
*@version 1.0
*@author Juan López
*/
public class ConstraintEngine {

  private static final Boolean vertical = false;
  private static final Boolean horizontal = true;

  private TableroH board;
  private ArrayList<ArrayList<HashSet<Integer>>> cellDomain;

  private Stack<Pair<Integer,LinkedList<Integer>>> log;
  private HashMap<Integer,LinkedList<Integer>> logTrack;

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
  *@return Boolean Devuelve false si captura alguna violacion de las reglas del
  *KenKen, true en caso contrario.
  */
  public Boolean propagate(int x, int y, int value) {

    Area area = this.board.getAreaByPos(x,y);
    MutableBoolean valid = new MutableBoolean();
    HashSet<Integer> dirtyArea = new HashSet<Integer>();

    log.push(null);

    this.board.setCasillaVal(x,y,value);

    logTrack.put(this.getLinearPos(x,y), new LinkedList<Integer>(this.getDomain(x,y)));
    this.getDomain(x,y).clear();
    this.getDomain(x,y).add(value);

    valid.setValue(area.check(this.board.size()));
    this.propValue(x,y,value,valid,dirtyArea);
    

    while (!dirtyArea.isEmpty() && valid.getValue())
      this.propLines(valid,dirtyArea);
    //System.out.println(this.toString()); /*<-------------------------------------------------------------------------------*/

    //Vacia logTrack y pasarlo a log
    int pos;
    LinkedList<Integer> aux;
    for (Map.Entry<Integer,LinkedList<Integer>> cellTrack : logTrack.entrySet()) {
      pos = cellTrack.getKey();
      aux = cellTrack.getValue();
      if (aux.isEmpty())
        continue;
      log.push(new Pair<Integer,LinkedList<Integer>>(pos,aux));
      cellTrack.setValue(new LinkedList<>());
    }

    return valid.getValue();
  }


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
  *Cambia de una posicion matricial a una posicion lineal.
  *
  *@param x Entero de la posicion en el eje X.
  *@param y Entero de la posicion en el eje Y.
  *@return int Posicion linear.
  */
  private int getLinearPos(int x, int y) {
    return x + y*this.board.size();
  }


  /**
  *Cambia de posicion lineal a posicion matriceal.
  *
  *@param pos Posicion lineal.
  *@return Pair<Integer,Integer> Posicion matriceal.
  */
  private Pair<Integer,Integer> getMatrixPos(int pos) {
    int x,y;
    x = pos % this.board.size();
    y = pos/this.board.size();
    return new Pair<Integer,Integer>(x,y);
  }


  /**
  *Propaga el valor en el eje X y el Y. Agrega dirtyArea aquella areas cuyas casillas
  *sufrieron algun cambio de dominio. Asigna valid a falso en caso de que alguna casilla
  *no tenga dominio, sino conserva su antiguo valor.
  *
  *@param x Posicion de la casilla en el eje X.
  *@param y Posicion de la casilla en el eje Y.
  *@param value Valor a propagar en las rectas expresadas por 'x' y 'y'.
  *@param valid Booleano que indica si se ha violado alguna regla del KenKen.
  *@param dirtyArea Collection que contiene aquellas areas que han sufrido algun
  *cambio de dominio.
  */
  private void propValue(int x, int y, int value, MutableBoolean valid, HashSet<Integer> dirtyArea) {
    //Propagacion horizontal
    for (int i=0; i<this.board.size() && valid.getValue(); i++) {
        if (i == x || !this.getDomain(i,y).contains(value))
          continue;
        dirtyArea.add(this.board.getAreaID(i,y));
        this.getDomain(i,y).remove(value);
        valid.setAnd(!getDomain(i,y).isEmpty());
        this.logTrack.get(this.getLinearPos(i,y)).add(value);
    }
    //Propagacion vertical
    for (int j=0; j<this.board.size() && valid.getValue(); j++) {
        if (j == y || !this.getDomain(x,j).contains(value))
          continue;
        dirtyArea.add(this.board.getAreaID(x,j));
        this.getDomain(x,j).remove(value);
        valid.setAnd(!getDomain(x,j).isEmpty());
        this.logTrack.get(this.getLinearPos(x,j)).add(value);
    }
  }


  /**
  *Propaga una linea vertical en la recta denotada por la posicion 'x', sin cambiar los
  *dominios dentro de 'area'. Si hay algun cambio de dominio se agrega la respectiva area en
  *dirtyArea. Si hay alguna violacion de reglas del kenken, valid sera negativo sino conservara
  *su antiguo valor.
  *
  *@param x Posicion de la recta vertical en el eje X.
  *@param area Area sobre la que no se propagara.
  *@param value Valor a propagar sobre la recta.
  *@param valid Indica si se ha violado alguna regla del KenKen.
  *@param dirtyArea Areas que han sufrido algun cambio de dominio.
  */
  private void propVLine(int x, Area area, int value, MutableBoolean valid, HashSet<Integer> dirtyArea) {
    for (int j=0; j<this.board.size() && valid.getValue(); j++) {
      if (this.board.areaContains(area,x,j) || !this.getDomain(x,j).contains(value))
        continue;
      this.getDomain(x,j).remove(value);
      valid.setAnd(!getDomain(x,j).isEmpty());
      dirtyArea.add(this.board.getAreaID(x,j));
      this.logTrack.get(this.getLinearPos(x,j)).add(value);
    }
  }


  /**
  *Propaga una linea horizontal en la recta denotada por la posicion 'y', sin cambiar los
  *dominios dentro de 'area'. Si hay algun cambio de dominio se agrega la respectiva area en
  *dirtyArea. Si hay alguna violacion de reglas del kenken, valid sera negativo sino conservara
  *su antiguo valor.
  *
  *@param y Posicion de la recta horizontal en el eje Y.
  *@param area Area sobre la que no se propagara.
  *@param value Valor a propagar sobre la recta.
  *@param valid Indica si se ha violado alguna regla del KenKen.
  *@param dirtyArea Areas que han sufrido algun cambio de dominio.
  */
  private void propHLine(int y, Area area, int value, MutableBoolean valid, HashSet<Integer> dirtyArea) {
    for (int i=0; i<this.board.size() && valid.getValue(); i++) {
      if (this.board.areaContains(area,i,y) || !this.getDomain(i,y).contains(value))
        continue;
      this.getDomain(i,y).remove(value);
      valid.setAnd(!getDomain(i,y).isEmpty());
      dirtyArea.add(this.board.getAreaID(i,y));
      this.logTrack.get(this.getLinearPos(i,y)).add(value);
    }
  }


  /**
  *Detecta y propaga valores implicitos de un area que se puedan propagar.
  *Las llamadas "Lines" en todo el programa.
  *
  *@param valid Indica si se ha violado alguna regla del KenKen.
  *@param dirtyArea Areas que han sufrido algun cambio de dominio.
  */
  private void propLines(MutableBoolean valid, HashSet<Integer> dirtyArea) {

    Area area;
    int pos,value;
    Boolean direction;
    Pair<Integer, Integer> tuple;
    LinkedList<Pair<Pair<Integer, Integer>, Boolean>> packOfLines;

    for (int i=0; i<this.board.getNumAreas() && valid.getValue(); i++) {
      if (!dirtyArea.contains(i))
        continue;
      dirtyArea.remove(i);
      area = this.board.getArea(i);
      packOfLines = this.findLines(area);
      for (Pair<Pair<Integer, Integer>, Boolean> line : packOfLines) {
        tuple = line.getFirst();
        pos = tuple.getFirst();
        value = tuple.getSecond();
        direction = line.getSecond();
        if (direction.equals(ConstraintEngine.vertical))
            propVLine(pos,area,value,valid,dirtyArea);
        else if (direction.equals(ConstraintEngine.horizontal))
            propHLine(pos,area,value,valid,dirtyArea);
        else
            throw new RuntimeException("Direccion no reconocida"); //**************************BORRAR***
      }
    }
  }


  /**
  *Encuentra valores implicitos y su direccion de propagacion dentro de una area.
  *
  *@param area Area donde se quieren buscar dichos valores y direcciones.
  *@return LinkedList<Pair<Pair<Integer, Integer>, Boolean>> En orden: posicion
  *de la recta, el valor a propagar y su por ultimo su direccion expresado con un
  *Booleano.
  */
  private LinkedList<Pair<Pair<Integer, Integer>, Boolean>> findLines(Area area) {
    int aux;
    Pair<Integer,Integer> tmp;
    Boolean[] taken = new Boolean[this.board.size()+1];
    HashMap<Integer,Integer> lines = new HashMap<Integer,Integer>();
    LinkedList<Pair<Pair<Integer, Integer>, Boolean>> res = new LinkedList<Pair<Pair<Integer, Integer>, Boolean>>();

    //Vertical lines
    lines.clear();
    Arrays.fill(taken,false);
    for (int i=0; i<this.board.size(); i++) {
      for (int j=0; j<this.board.size(); j++) {
        aux = this.board.getCasillaVal(i,j);
        if (!this.board.areaContains(area,i,j) || aux == -1)
          continue;
        //Si ha sido tomado y ha sido removido o es una linea distinta.
        if (taken[aux] && (lines.get(aux) == null || lines.get(aux) != i)) {
          lines.remove(aux);
        }
        else {
          taken[aux] = true;
          lines.put(aux,i);
        }
      }
    }
    for (Map.Entry<Integer,Integer> line : lines.entrySet()) {
      tmp = new Pair<Integer,Integer>(line.getValue(),line.getKey());
      res.add(new Pair<Pair<Integer, Integer>, Boolean>(tmp,ConstraintEngine.vertical));
    }

    //Horizontal lines
    lines.clear();
    Arrays.fill(taken,false);
    for (int j=0; j<this.board.size(); j++) {
      for (int i=0; i<this.board.size(); i++) {
		 aux = this.board.getCasillaVal(i,j);
	     if (!this.board.areaContains(area,i,j) || aux == -1)
	        continue;
        //Si ha sido tomado y ha sido removido o es una linea distinta.
        if (taken[aux] && (lines.get(aux) == null || lines.get(aux) != j)) {
          lines.remove(aux);
        }
        else {
          taken[aux] = true;
          lines.put(aux,j);
        }
      }
    }
    for (Map.Entry<Integer,Integer> line : lines.entrySet()) {
      tmp = new Pair<Integer,Integer>(line.getValue(),line.getKey());
      res.add(new Pair<Pair<Integer, Integer>, Boolean>(tmp,ConstraintEngine.horizontal));
    }

    return res;
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
