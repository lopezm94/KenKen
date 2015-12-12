import java.lang.RuntimeException;

/**
*Un Factory Constructor para Area.
*
*@author Joan Grau
*@author Juan López
*/
public class AreaBuilder {

  /**
  *Devuelve un area con el identificador pos y la operacion op
  *
  *@param pos Identificador
  *@param op Operacion
  *@return Area Area
  */
  public static Area newArea(int pos, char op) {
    switch (op) {
      case '.':
        return new NoOp(pos);
      case '+':
        return new Suma(pos);
      case '*':
        return new Multiplicacio(pos);
      case '-':
        return new Resta(pos);
      case '/':
        return new Divisio(pos);
      default:
        throw new IllegalArgumentException("Operacion no valida");
    }
  }
}
