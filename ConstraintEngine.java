import java.util.Arrays;
import java.util.HashSet;
import java.util.TreeSet;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.LinkedList;
import java.lang.Math;
import java.lang.RuntimeException;
import java.lang.CloneNotSupportedException;

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
  private TreeSet<Pair<Integer,Pair<Integer,Integer>>> nextCell;
  private HashMap<Integer,Pair<Integer,Pair<Integer,Integer>>> objectMap;


  /**
  *Construye un nuevo motor, en base a un tablero de kenken.
  *Tomando como referencia el tablero y copiando los dominions de las casillas.
  *
  *@param board Es un tablero previamente creado.
  */
  public ConstraintEngine(TableroH board) {
    this.board = board;
    this.cellDomain = new ArrayList<ArrayList<HashSet<Integer>>>();
    this.objectMap = new HashMap<Integer,Pair<Integer,Pair<Integer,Integer>>>();
    this.nextCell = new TreeSet<Pair<Integer,Pair<Integer,Integer>>>();

    Integer first;
    Pair<Integer,Integer> second;
    Pair<Integer,Pair<Integer,Integer>> object;
    for(int i=0; i<this.board.size(); i++) {
      //Inicializar filas HashSet
      this.cellDomain.add(i,new ArrayList<HashSet<Integer>>());
      for(int j=0; j<this.board.size(); j++) {

        Casilla cell = this.board.getCasilla(i,j);

        //Inicializar HashSet
        this.cellDomain.get(i).add(j,new HashSet<Integer>(this.board.getCasillaDomain(i,j)));

        if (cell.getFija())
          continue;

        //Inicializar TreeSet
        first = this.getDomain(i,j).size(); //asumo que se creara un nevo objeto Integer
        second = new Pair<Integer,Integer>(i,j);
        object = new Pair<Integer,Pair<Integer,Integer>>(first,second);
        this.nextCell.add(object);

        //Mapea el objeto para poder modificarlo despues
        this.objectMap.put(this.getLinearPos(i,j),object);
      }
    }
  }


  //La sustituira undo en el futuro
  //confia es que no se quitara una casilla random
  //Solo se agrega para mantener en una caja negra a la recursividad.
  /**
  *Pone el valor a 0 en la casilla de la posicion solicitada.
  *
  *@param x Posicion de la casilla en el eje X.
  *@param y Posicion de la casilla en el eje Y.
  */
  public void removeValue(int x, int y) {
    this.board.setCasillaVal(x,y,-1);
  }


  /**
  *Retorna la posicion de la casilla no asignada con el dominio mas pequeño.
  *
  *@return Pair<Integer,Integer> Posicion de la casilla no asignada con el dominio mas pequeño.
  */
  public Pair<Integer,Integer> getNextCell() {
    if (this.nextCell.isEmpty())
      return null;
    Pair<Integer,Integer> pos;
    Pair<Integer,Pair<Integer,Integer>> aux;
    aux = this.nextCell.pollFirst();
    pos = aux.getSecond();
    objectMap.remove(this.getLinearPos(pos));
    return pos;
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
    MutableBoolean valid = new MutableBoolean();
    HashSet<Integer> dirtyArea = new HashSet<Integer>();
    Area area = this.board.getAreaByPos(x,y);

    if (this.nextCell.contains(this.getLinearPos(x,y))
      || this.board.getCasillaVal(x,y) != -1)
      throw new RuntimeException("No se puede cambiar una casilla ya asignada");

    this.board.setCasillaVal(x,y,value);

    this.getDomain(x,y).clear();
    this.getDomain(x,y).add(value);

    valid.setValue(area.check(this.board.size()));
    this.propValue(x,y,value,valid,dirtyArea);

    while (!dirtyArea.isEmpty() && valid.getValue())
      this.propLines(valid,dirtyArea);

    return valid.getValue();
  }


  /*
  *Cambia de una posicion matricial a una posicion linear.
  *
  *@param x Entero de la posicion en el eje X.
  *@param y Entero de la posicion en el eje Y.
  *@return int Posicion linear.
  */
  private int getLinearPos(int x, int y) {
    return x + y*this.board.size();
  }


  private HashSet<Integer> getDomain(int x, int y) {
    return this.cellDomain.get(x).get(y);
  }


  /*
  *Cambia de una posicion matricial a una posicion linear.
  *
  *@param pair Par que contiene la posicion matricial.
  *@return int Posicion linear.
  */
  private int getLinearPos(Pair<Integer,Integer> pair) {
    return this.getLinearPos(pair.getFirst(),pair.getSecond());
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
    //Propagacion vertical
    for (int i=0; i<this.board.size() && valid.getValue(); i++) {
        if (i == x || !this.getDomain(i,y).contains(value))
          continue;
        dirtyArea.add(this.board.getAreaID(i,y));
        this.getDomain(i,y).remove(value);
        valid.setAnd(!getDomain(i,y).isEmpty());
        this.updateNextCell(i,y);
    }
    //Propagacion horizontal
    for (int j=0; j<this.board.size() && valid.getValue(); j++) {
        if (j == y || !this.getDomain(x,j).contains(value))
          continue;
        dirtyArea.add(this.board.getAreaID(x,j));
        this.getDomain(x,j).remove(value);
        valid.setAnd(!getDomain(x,j).isEmpty());
        this.updateNextCell(x,j);
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
      this.updateNextCell(x,j);
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
      this.updateNextCell(i,y);
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
            throw new RuntimeException("Find lines esta mal");
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
        if (!this.board.areaContains(area,i,j))
          continue;
        aux = this.board.getCasillaVal(i,j);
        if (taken[aux] && (lines.get(aux) == null || lines.get(aux) != i))
          lines.remove(aux);
        else {
          taken[aux] = true;
          lines.put(aux,i);
        }
      }
    }
    for (Map.Entry<Integer,Integer> line : lines.entrySet()) {
      tmp = new Pair<Integer,Integer>(line.getKey(),line.getValue());
      res.add(new Pair<Pair<Integer, Integer>, Boolean>(tmp,ConstraintEngine.vertical));
    }

    //Horizontal lines
    lines.clear();
    Arrays.fill(taken,false);
    for (int j=0; j<this.board.size(); j++) {
      for (int i=0; i<this.board.size(); i++) {
        if (!this.board.areaContains(area,i,j))
          continue;
        aux = this.board.getCasillaVal(i,j);
        if (taken[aux] && (lines.get(aux) == null || lines.get(aux) != j))
          lines.remove(aux);
        else {
          taken[aux] = true;
          lines.put(aux,j);
        }
      }
    }
    for (Map.Entry<Integer,Integer> line : lines.entrySet()) {
      tmp = new Pair<Integer,Integer>(line.getKey(),line.getValue());
      res.add(new Pair<Pair<Integer, Integer>, Boolean>(tmp,ConstraintEngine.horizontal));
    }

    return res;
  }

  /*
  *Le resta una unidad al campo que representa el tamaño del dominio de la
  *casilla solicitada. Luego de restarse se actualizan las posiciones de
  *menor a mayor ocurrencias.
  *
  *@param x Posicion de la casilla en el eje X.
  *@param y Posicion de la casilla en el eje Y.
  */
  private void updateNextCell(int x, int y) {
    Integer aux = this.getLinearPos(x,y);
    Pair<Integer,Pair<Integer,Integer>> object = objectMap.get(aux);
    nextCell.remove(object);
    object.setFirst(object.getFirst()-1);
    nextCell.add(object);
  }

  /**
  *Clona el motor de restricciones.
  *
  *@Override Obejct.clone().
  *@throws CloneNotSupportedException.
  *@return ConstraintEngine Retorna un clon del motor.
  */
  @Override
  public ConstraintEngine clone() {
    try {
      ConstraintEngine newEngine = (ConstraintEngine) super.clone();
      newEngine.board = this.board;
      newEngine.cellDomain = new ArrayList<ArrayList<HashSet<Integer>>>();
      newEngine.objectMap = new HashMap<Integer,Pair<Integer,Pair<Integer,Integer>>>();
      newEngine.nextCell = new TreeSet<Pair<Integer,Pair<Integer,Integer>>>();

      Integer first;
      Pair<Integer,Integer> second;
      Pair<Integer,Pair<Integer,Integer>> object;
      for(int i=0; i<newEngine.board.size(); i++) {
        //Inicializar filas HashSet
        newEngine.cellDomain.add(i,new ArrayList<HashSet<Integer>>());
        for(int j=0; j<newEngine.board.size(); j++) {

          Casilla cell = newEngine.board.getCasilla(i,j);

          //Inicializar HashSet
          newEngine.cellDomain.get(i).add(j, new HashSet<Integer>(this.getDomain(i,j)));

          //Inicializar TreeSet
          first = newEngine.getDomain(i,j).size(); //asumo que se creara un nevo objeto Integer
          second = new Pair<Integer,Integer>(i,j);
          object = new Pair<Integer,Pair<Integer,Integer>>(first,second);
          newEngine.nextCell.add(object);

          //Mapea el objeto para poder modificarlo despues
          newEngine.objectMap.put(newEngine.getLinearPos(i,j),object);
        }
      }
      return newEngine;
    } catch (CloneNotSupportedException e) {
      throw new InternalError(e.toString());
    }
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
      + "nextCell:\n" + this.nextCell.toString() + "\n"
      + "objectMap:\n" + this.objectMap.toString() + "\n";
  }
}
