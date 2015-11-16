import java.util.Comparator;
import java.util.LinkedList;
import java.util.ListIterator;

/**
*<h1>OrderedMapByValue</h1>
*Implementa un Map de llave String a Valor, las llaves pueden
*no ser unicas. Los elementos se encuentran ordenados
*por valor de forma ascendente o descendente
*dependiendo del parametro puesto a la hora de construir.s
*
*@author Juan López
*/
public class OrderedMapByValue {

  //Es el comparador que se usara
  protected Comparator<Integer> comp;
  //Lista de map
  protected LinkedList<Pair<String, Integer> > map;


  /**
  *Construye un mapa vacio con la funcion de ordenamiento
  *denotada por el parametro comp. Si comp es igual a
  *"naturalOrder" entonces se ordenara de forma creciente,
  *si en cambio es igual a reverseOrder se ordena de forma
  *decreciente.
  *
  *@param comp String que representa funcion de ordenamiento.
  */
  public OrderedMapByValue(String comp) {
    this.map = new LinkedList<Pair<String, Integer> >();
    switch(comp) {
      case "reverseOrder":
        this.comp = Comparator.<Integer>reverseOrder();
        break;
      default:
        this.comp = Comparator.<Integer>naturalOrder();
        break;
    }
  }


  /**
  *Agrega un elemento al mapa permitiendo las repeticiones de llaves y
  *manteniendo el orden de la coleccion.
  *
  *@param user El identicador del usuario.
  *@param score La puntuacion del usuario.
  */
  public void push(String user, Integer score) {
    int tmp;
    ListIterator<Pair<String,Integer> > it = this.listIterator();

    while (it.hasNext()) {
      tmp = it.next().getSecond();
      if (this.comp.compare(score,tmp) < 0) {
        it.previous();
        break;
      }
    }

    it.add(new Pair<String, Integer>(user, score));
  }


  /**
  *Remueve todas las instancias de los elementos con la llave
  *user del Map.
  *
  *@param user El identificador del usuario.
  */
  public void remove(String user) {
    Pair<String, Integer> tmp;
    ListIterator<Pair<String,Integer> > it = this.listIterator();
    while (it.hasNext()) {
      tmp = it.next();
      if (user.equals(tmp.getFirst()))
        it.remove();
    }
  }

  //Devuelve un iterador
  /**
  *Devuelve un Iterador sobre los elemento del Map.
  *
  *@return ListIterator<Pair<String,Integer>> Iterador del Map.
  */
  public ListIterator<Pair<String,Integer>> listIterator() {
    return this.map.listIterator();
  }


  /**
  *Busca el primer Pair asociado al identificador del usuario.
  *
  *@return Pair<String, Integer> Devuelve el primer par asociado
  *al identificador del usuario. En caso de no estar presente
  *devuelve null.
  */
  public Pair<String, Integer> getPair(String user) {

    Pair<String, Integer> tmp;
    ListIterator<Pair<String,Integer> > it = this.listIterator();

    while (it.hasNext()) {
      tmp = it.next();
      if (user.equals(tmp.getFirst()))
        return tmp;
    }
    return null;
  }


  /**
  *Numero de elementos en el Map
  *
  *@return int Devuelve el numero de elementos en el mapa.
  */
  public int size() {
    return this.map.size();
  }

  //Imprime el contenido de la lista del map.
  /**
  *Crea un String que represente los contenidos del Map.
  *
  *@return String String que representa los contenidos del Map.
  */
  public String toString() {
    return this.map.toString();
  }

}
