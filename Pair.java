/**
*<h1>Pair</h1>
*Implementa una coleccion de un par de elementos genericos.
*
*@author Juan López
*/
public class Pair<F, S> {

  //Primer atributo
  private F first;
  //Segundo atributo
  private S second;


  /**
  *Construye un par vacio.
  */
  public Pair() {
    this.first = null;
    this.second = null;
  }


  /**
  *Construye un par inicializado con los parametros de entrada
  *
  *@param first Primer atributo del par.
  *@param second Segundo atributo del par.
  */
  public Pair(F first, S second) {
      this.first = first;
      this.second = second;
  }


  /**
  *Devuelve el primer elemento del par.
  *
  *@return F El primer elemento del par.
  */
  public F getFirst() {
    return this.first;
  }


  /**
  *Devuelve el segundo elemento del par.
  *
  *@return S El segundo elemento del par.
  */
  public S getSecond() {
    return this.second;
  }


  /**
  *Modifica el valor del primer atributo.
  *
  *@param first Nuevo valor del primer atributo.
  */
  public void setFirst(F first) {
    this.first = first;
  }


  /**
  *Modifica el valor del segundo atributo.
  *
  *@param second Nuevo valor del segundo atributo.
  */
  public void setSecond(S second) {
    this.second = second;
  }


  /**
  *Devuelve String que representa el estado interno del par.
  *
  *@return String String que representa el estado interno del par.
  */
  @Override
  public String toString() {
    return "(" + this.first.toString() + ", " + this.second.toString() + ")";
  }

}
