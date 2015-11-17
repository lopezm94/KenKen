import java.util.HashSet;
import java.util.ArrayList;
import java.util.Map;
import java.util.Stack;
import java.util.HashMap;
import java.util.LinkedList;


/**
*<h1>BoardEngine</h1>
*Implementa un motor de restricciones para resolver un tablero
*donde los valores por columna y fila son unicos.
*
*@version 1.0
*@author Juan López
*/
public class BoardEngine extends ConstraintEngine {

  /**
  *Construye un nuevo motor, en base a un tablero de kenken.
  *Tomando como referencia el tablero y copiando los dominions de las casillas.
  *
  *@param board Es un tablero previamente creado.
  */
  public BoardEngine(TableroH board) {
    super(board);
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
    MutableBoolean valid = new MutableBoolean(true);

    super.board.setCasillaVal(x,y,value);

    super.logTrack.put(super.getLinearPos(x,y), new LinkedList<Integer>(super.getDomain(x,y)));
    super.getDomain(x,y).clear();
    super.getDomain(x,y).add(value);

    this.propValue(x,y,value,valid);

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


  /**
  *Propaga el valor en el eje X y el Y. . Asigna valid a falso en caso de que
  *alguna casilla no tenga dominio, sino conserva su antiguo valor.
  *
  *@param x Posicion de la casilla en el eje X.
  *@param y Posicion de la casilla en el eje Y.
  *@param value Valor a propagar en las rectas expresadas por 'x' y 'y'.
  *@param valid Booleano que indica si se ha violado alguna regla del KenKen.
  */
  private void propValue(int x, int y, int value, MutableBoolean valid) {
    //Propagacion horizontal
    for (int i=0; i<super.board.size() && valid.getValue(); i++) {
        if (i == x || !super.getDomain(i,y).contains(value))
          continue;
        super.getDomain(i,y).remove(value);
        valid.setAnd(!getDomain(i,y).isEmpty());
        super.logTrack.get(super.getLinearPos(i,y)).add(value);
    }
    //Propagacion vertical
    for (int j=0; j<super.board.size() && valid.getValue(); j++) {
        if (j == y || !super.getDomain(x,j).contains(value))
          continue;
        super.getDomain(x,j).remove(value);
        valid.setAnd(!getDomain(x,j).isEmpty());
        super.logTrack.get(super.getLinearPos(x,j)).add(value);
    }
  }

}
