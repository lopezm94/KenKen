/*import java.lang.CloneNotSupportedException;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.ListIterator;*/


public class Pair<F, S> {
  private F first;
  private S second;

  public Pair() {
    this.first = null;
    this.second = null;
  }

  public Pair(F first, S second) {
      this.first = first;
      this.second = second;
  }

  public F getFirst() {
    return this.first;
  }

  public S getSecond() {
    return this.second;
  }

  public void setFirst(F first) {
    this.first = first;
  }

  public void setSecond(S second) {
    this.second = second;
  }

  @Override
  public String toString() {
    return "(" + this.first.toString() + ", " + this.second.toString() + ")";
  }

}
