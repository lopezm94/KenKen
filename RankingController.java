import java.util.LinkedList;

/**
*<h1>RankingController</h1>
*Implementa un Ranking de jugadores con puntuaciones.
*
*@author Juan López
*/
public class RankingController {

  private static final String dir = ".";
  private static final String file = "Profiles.txt";
  private static final String order = "naturalOrder";

  public static LinkedList<Pair<String,Integer>> getRanking(String dificultad) {
    Ranking ranking = new Ranking(order);
    LinkedList<Pair<String,Integer>> lista = GestioDadesH.readScores(
      dificultad,dir,file);
    for (Pair<String,Integer> p : lista)
      ranking.push(p);
    return ranking.getRank();
  }
}
