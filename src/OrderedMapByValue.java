import java.util.Comparator;
import java.util.LinkedList;
import java.util.ListIterator;

public abstract class OrderedMapByValue {
  //Es el comparador que se usara
  protected Comparator<Integer> comp;
  //Lista de map
  protected LinkedList<Pair<String, Integer> > map;

  //naturalOrder: ordena ascendentemente
  //reverseOrder: ordena descendentemente
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

  //Agrega un entrada al map sin importar repeticiones de usuarios.
  //Si no quieres que se permitan las repeticiones, implementar el tuyo
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

  //Quita todas las instancias de un usuario de la tabla
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
  public ListIterator<Pair<String,Integer>> listIterator() {
    return this.map.listIterator();
  }

  //Busca el primer pair asociado al nombre del usuario.
  //Retorna el objeto si lo encuentra.
  //Retorna null si no lo encuentra.
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

  //Retorna el tamaño de la lista del map.
  public int size() {
    return this.map.size();
  }

  //Imprime el contenido de la lista del map.
  public String toString() {
    return this.map.toString();
  }

}
