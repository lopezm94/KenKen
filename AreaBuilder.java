import java.lang.RuntimeException;

/**
*@author Joan Grau
*@author Juan López
*/
public class AreaBuilder {
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
