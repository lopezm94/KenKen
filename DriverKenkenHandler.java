import java.util.Scanner;


/**
*<h1>DriverKenkenHandler</h1>
*Driver de la clase KenkenHandler con varios casos de prueba.
*
*@author Juan López
*/
public class DriverKenkenHandler {
  public static Scanner in;
  public static void main(String[] args) {
    int size;
    String dificultad;
    in = new Scanner(System.in);
    KenkenHandler ke = new KenkenHandler();

    System.out.println("De que tamaño quiere el tablero? (3-9)");
    size = Integer.parseInt(in.nextLine());
    System.out.println("Que dificultad desea? (Facil, Medio o Dificil)");
    dificultad = in.nextLine();

    TableroH sol = ke.generateAndSolveKenken(size,dificultad);
    ke.solveKenken(sol);
    System.out.println(sol.getAllAreas());
    System.out.println(sol);
    System.out.println(sol.getAreas());
    System.out.println(sol.getSolucion());
  }

}
