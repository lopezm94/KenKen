/**
*<h1>MutableBoolean</h1>
*Implementa un wrapper de Boolean para poder modificar por referencia.
*
*@author Juan López
*/
public class MutableBoolean {

  //Valor a referenciar
  private Boolean value;


  /**
  *Constructor de MutableBoolean por defecto.
  *Asigna el valor por defecto a false.
  */
  public MutableBoolean() {
    this.value = false;
  }


  /**
  *Constructor de MutableBoolean por defecto.
  *Asigna el valor por defecto a false.
  */
  public MutableBoolean(Boolean value) {
    this.value = value;
  }


  /**
  *Hace la operacion $$ con el value pasado.
  *
  *@param value Valor con el cual hacer &&.
  */
  public void setAnd(Boolean value) {
    this.value = this.value && value;
  }


  /**
  *Hace la operacion || con el value pasado.
  *
  *@param value Valor con el cual hacer ||.
  */
  public void setOr(Boolean value) {
    this.value = this.value || value;
  }


  /**
  *Asigna value al MutableBoolean.
  *
  *@param value Valor a asignar.
  */
  public void setValue(Boolean value) {
    this.value = value;
  }


  /**
  *Obtiene el valor del MutableBoolean.
  *
  *@return Boolean Valor del MutableBoolean
  */
  public Boolean getValue() {
    return this.value;
  }


  /**
  *Comprueba que el valor del MutableBoolean sea igual a value.
  *
  *@return Boolean Devuelve true si son iguales.
  */
  public Boolean equals(Boolean value) {
    return this.value.equals(value);
  }


  /**
  *Obtiene el estado interno de la clase en formato String.
  *
  *@return String Estado interno de la clase.
  */
  public String toString() {
    return value.toString();
  }

}
