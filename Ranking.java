/**
*<h1>Ranking</h1>
*Implementa un Ranking de jugadores con puntuaciones.
*
*@author Juan López
*/
public class Ranking extends OrderedMapByValue {

  /**
  *Construye un ranking vacio con la funcion de ordenamiento
  *denotada por el parametro comp. Si comp es igual a
  *"naturalOrder" entonces se ordenara de forma creciente,
  *si en cambio es igual a reverseOrder se ordena de forma
  *decreciente.
  *
  *@param comp String que representa funcion de ordenamiento.
  */
  public Ranking(String comp) {
    super(comp);
  }

  /**
  *Agrega un elemento al mapa, si ya se encuentra deja la mejor.
  *
  *@param user El identicador del usuario.
  *@param score La puntuacion del usuario.
  */
  public void push(String user, Integer score) {
    Pair<String, Integer> tmp = this.getPair(user);

    if (tmp == null) //Si no existe se agrega
      super.push(user, score);
    else { //Si ya existe, solo se coloca si el nuevo score es mejor
      if (this.comp.compare(score, tmp.getSecond()) < 0) {
        this.map.remove(tmp);
        super.push(user, score);
      }
    }
  }

  /**
  *Agrega un elemento al mapa, si ya se encuentra deja la mejor.
  *
  *@param user El identicador del usuario y su puntuacion.
  */
  public void push(Pair<String, Integer> user) {
    this.push(user.getFirst(),user.getSecond());
  }
}
