import java.util.Arrays;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.Map;
import java.util.Stack;
import java.util.HashMap;
import java.util.LinkedList;
import java.lang.RuntimeException;

/**
*<h1>KenkenEngine</h1>
*Implementa un motor de restricciones para resolver el juego KenKen.
*
*@version 1.0
*@author Juan López
*/
public class KenkenEngine extends ConstraintEngine {

  /**
  *Construye un nuevo motor, en base a un tablero de kenken.
  *Tomando como referencia el tablero y copiando los dominions de las casillas.
  *
  *@param board Es un tablero previamente creado.
  */
  public KenkenEngine(TableroH board) {
    super(board);
  }


  /**
	*Iniciliza el dominio de las casillas para que puedan tener cualquier valor
	*entre 1 y el tamaño del tablero.
	*/
  //Este metodo y sus llamadas internas fueron un infierno de programar.
	protected void initDomain() {
    Area area;
    Integer aux;
    Casilla tmp;
    HashMap<Casilla,HashSet<Integer>> domains =
      new HashMap<Casilla,HashSet<Integer>>();
    ArrayList<Pair<Pair<Integer,Integer>,Casilla>> order =
      new ArrayList<Pair<Pair<Integer,Integer>,Casilla>>();

    Boolean[][] col = new Boolean[super.board.size()][super.board.size()+1];
    Boolean[][] row = new Boolean[super.board.size()][super.board.size()+1];
    Boolean[][] visit = new Boolean[super.board.size()][super.board.size()];

    for (int i = 0; i<super.board.size(); i++) {
			for (int j = 0; j<super.board.size(); j++) {
        row[i][j+1] = false;
        col[i][j+1] = false;
        visit[i][j] = false;
        tmp = super.board.getCasilla(i,j);
        domains.put(tmp,new HashSet<Integer>());
			}
		}

    for (int i=0; i<super.board.size(); i++) {
      for (int j=0; j<super.board.size(); j++) {
        if (!visit[i][j]) {
          aux = super.board.getAreaID(i,j);
          area = super.board.getArea(aux);
          makeOrder(order,visit,area,aux,i,j);
          setAreaDomain(order,domains,area,col,row,0);
          order.clear();
        }
      }
    }

    for (Map.Entry<Casilla,HashSet<Integer>> entry : domains.entrySet()) {
      entry.getKey().borrarcandidatos();
      for (Integer value : entry.getValue()) {
        entry.getKey().addCan(value);
      }
    }
	}


  /**
  *Modifica el arraylist order para que tenga todas las casillas pertenecientes
  *al area pasada.
  *
  *@param order Arraylist donde se encontraran las casillas del area.
  *@param visit Matrix con el control del DFS.
  *@param area Area.
  *@param id Id del area.
  *@param i Posicion en el eje X.
  *@param j Posicion en el eje Y.
  */
  private void makeOrder(ArrayList<Pair<Pair<Integer,Integer>,Casilla>> order,
    Boolean[][] visit, Area area, Integer id, int i, int j) {
    if (i < 0 || j < 0 || i == super.board.size() || j == super.board.size())
      return;
    if (visit[i][j] || !id.equals(super.board.getAreaID(i,j)))
      return;
    visit[i][j] = true;
    order.add(new Pair<Pair<Integer,Integer>,Casilla>(
      new Pair<Integer,Integer>(i,j), super.board.getCasilla(i,j)) );
    this.makeOrder(order,visit,area,id,i+1,j);
    this.makeOrder(order,visit,area,id,i-1,j);
    this.makeOrder(order,visit,area,id,i,j+1);
    this.makeOrder(order,visit,area,id,i,j-1);
  }


  /**
  *Parea por las casillas del area y restringe el dominio en base a las restricciones
  *de fila, columna, operacion y resultado.
  *
  *@param order Arraylist donde se encuentran las casillas del area.
  *@param domains Lleva la cuenta de los dominios de las casillas.
  *@param area Area.
  *@param col Lleva el control de la restriccion de columna.
  *@param row Lleva el control de la restriccion de fila.
  *@param depth Indica por que elemento de order estamos.
  */
  private void setAreaDomain(ArrayList<Pair<Pair<Integer,Integer>,Casilla>> order,
    HashMap<Casilla,HashSet<Integer>> domains, Area area,
    Boolean[][] col, Boolean[][] row, int depth) {
    if (!area.check(super.board.size()))
      return;
    if (depth == order.size()) {
      for (Casilla casilla : area.getCasellas()) {
        domains.get(casilla).add(casilla.getValor());
      }
      return;
    }

    int i,j;
    i = order.get(depth).getFirst().getFirst();
    j = order.get(depth).getFirst().getSecond();
    Casilla casilla = order.get(depth).getSecond();
    if (casilla.getValor() == -1) {
      for (int v=1; v<=super.board.size(); v++) {
        if (col[i][v] || row[j][v])
          continue;
        casilla.setValor(v);
        col[i][v] = true;
        row[j][v] = true;
        this.setAreaDomain(order,domains,area,col,row,depth+1);
        col[i][v] = false;
        row[j][v] = false;
        casilla.setValor(-1);
      }
    }
    else {
      int v = casilla.getValor();
      if (col[i][v] || row[j][v])
        return;
      col[i][v] = true;
      row[j][v] = true;
      this.setAreaDomain(order,domains,area,col,row,depth+1);
      col[i][v] = false;
      row[j][v] = false;
    }
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
	  super.log.push(null);
	  if (super.board.casillaIsFija(x,y) && value != super.board.getCasillaVal(x, y))
		  return false;
    Area area = super.board.getAreaByPos(x,y);
    MutableBoolean valid = new MutableBoolean(true);
    HashSet<Integer> dirtyArea = new HashSet<Integer>();

    super.board.setCasillaVal(x,y,value);

    super.logTrack.put(super.getLinearPos(x,y), new LinkedList<Integer>(super.getDomain(x,y)));
    super.getDomain(x,y).clear();
    super.getDomain(x,y).add(value);

    valid.setValue(area.check(super.board.size()));
    this.propValue(x,y,value,valid,dirtyArea);
    this.propLines(valid,dirtyArea);


    //Vacia logTrack y pasarlo a log
    int pos;
    LinkedList<Integer> aux;
    for (Map.Entry<Integer,LinkedList<Integer>> cellTrack : super.logTrack.entrySet()) {
      pos = cellTrack.getKey();
      aux = cellTrack.getValue();
      if (aux.isEmpty())
        continue;
      super.log.push(new Pair<Integer,LinkedList<Integer>>(pos,aux));
      cellTrack.setValue(new LinkedList<>());
    }

    return valid.getValue();
  }

  /*************************************************************************************************************************
  /**
  *Dada el id de un area, devuelve las casillas que pertenecen al area con sus posiciones
  *correspondientes.
  *
  *@param Area Area.
  *@param id Id del area.
  *@return Boolean Checkeo de que el area cumple con los dominios de las casillas.
  */
  /*private Boolean checkArea(Area area, int id) {
    LinkedList<Pair<Pair<Integer,Integer>,HashSet<Integer>>> push = new LinkedList<Pair<Pair<Integer,Integer>,HashSet<Integer>>>();
    for (int i=0; i<this.board.size(); i++) {
      for (int j=0; j<this.board.size(); j++) {
        if (this.board.getAreaID(i,j) == id) {
          push.add(
            new Pair<Pair<Integer,Integer>,HashSet<Integer>>(
              new Pair<Integer,Integer>(i,j),
              this.getDomain(i,j)
            )
          );
        }
      }
    }

   // return area.domainCheck(push);
  }*/
  /*************************************************************************************************************************


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
    for (int i=0; i<super.board.size() && valid.getValue(); i++) {
        if (i == x || !super.getDomain(i,y).contains(value))
          continue;
        dirtyArea.add(super.board.getAreaID(i,y));
        super.getDomain(i,y).remove(value);
        valid.setAnd(!getDomain(i,y).isEmpty());
        super.logTrack.get(super.getLinearPos(i,y)).add(value);
    }
    //Propagacion vertical
    for (int j=0; j<super.board.size() && valid.getValue(); j++) {
        if (j == y || !super.getDomain(x,j).contains(value))
          continue;
        dirtyArea.add(super.board.getAreaID(x,j));
        super.getDomain(x,j).remove(value);
        valid.setAnd(!getDomain(x,j).isEmpty());
        super.logTrack.get(super.getLinearPos(x,j)).add(value);
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
    for (int j=0; j<super.board.size() && valid.getValue(); j++) {
      if (super.board.areaContains(area,x,j) || !super.getDomain(x,j).contains(value))
        continue;
      super.getDomain(x,j).remove(value);
      valid.setAnd(!getDomain(x,j).isEmpty());
      dirtyArea.add(super.board.getAreaID(x,j));
      super.logTrack.get(super.getLinearPos(x,j)).add(value);
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
    for (int i=0; i<super.board.size() && valid.getValue(); i++) {
      if (super.board.areaContains(area,i,y) || !super.getDomain(i,y).contains(value))
        continue;
      super.getDomain(i,y).remove(value);
      valid.setAnd(!getDomain(i,y).isEmpty());
      dirtyArea.add(super.board.getAreaID(i,y));
      super.logTrack.get(super.getLinearPos(i,y)).add(value);
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

    while (!dirtyArea.isEmpty() && valid.getValue()) {
      for (int i=0; i<super.board.getNumAreas() && valid.getValue(); i++) {
        if (!dirtyArea.contains(i))
          continue;
        dirtyArea.remove(i);
        area = super.board.getArea(i);
        packOfLines = this.findLines(area);
        for (Pair<Pair<Integer, Integer>, Boolean> line : packOfLines) {
          tuple = line.getFirst();
          pos = tuple.getFirst();
          value = tuple.getSecond();
          direction = line.getSecond();
          if (direction.equals(ConstraintEngine.vertical))
              this.propVLine(pos,area,value,valid,dirtyArea);
          else if (direction.equals(ConstraintEngine.horizontal))
              this.propHLine(pos,area,value,valid,dirtyArea);
          else
              throw new RuntimeException("Direccion no reconocida");
        }
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
    Boolean[] taken = new Boolean[super.board.size()+1];
    HashMap<Integer,Integer> lines = new HashMap<Integer,Integer>();
    LinkedList<Pair<Pair<Integer, Integer>, Boolean>> res = new LinkedList<Pair<Pair<Integer, Integer>, Boolean>>();

    //Vertical lines
    lines.clear();
    Arrays.fill(taken,false);
    for (int i=0; i<super.board.size(); i++) {
      for (int j=0; j<super.board.size(); j++) {
        aux = super.board.getCasillaVal(i,j);
        if (!super.board.areaContains(area,i,j) || aux == -1)
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
    for (int j=0; j<super.board.size(); j++) {
      for (int i=0; i<super.board.size(); i++) {
     aux = super.board.getCasillaVal(i,j);
       if (!super.board.areaContains(area,i,j) || aux == -1)
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

}
