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
    String line;
    in = new Scanner(System.in);

    System.out.println("De que tamaño quiere el tablero? (3-9)");
    size = Integer.parseInt(in.nextLine());
    System.out.println("Que dificultad desea? (Facil, Medio o Dificil)");
    dificultad = in.nextLine();

    TableroH sol = KenkenHandler.generateAndSolveKenken(size,dificultad);
    KenkenHandler.solveKenken(sol);
    sol.setResultToValue();

    System.out.println("contenido de las areas: ");
    System.out.println(sol.getAllAreas());
    System.out.println("valores: ");
    for (int i=0,cas; i<size; i++) {
      line = "";
      for (int j=0; j<size; j++) {
        if (sol.casillaIsFija(i,j))
          cas = sol.getCasillaVal(i,j);
        else
          cas = -1;
        line = line + cas + " ";
      }
      System.out.println(line);
    }
    System.out.println();
    System.out.println("areas: ");
    System.out.println(sol.getAreas());
    System.out.println("solucion: ");
    System.out.println(sol.getSolucion());
    for (int i=0,cas; i<size; i++) {
      line = "";
      for (int j=0; j<size; j++) {
        cas = sol.getCasillaVal(i,j);
        line = line + cas + " ";
      }
      System.out.println(line);
    }
    System.out.println();
    System.out.println("La dificultad es " + KenkenHandler.getDifficulty(sol));
    if (!(sol.numerosCheck() && sol.tableroCheck()))
      System.out.println("Hay algo mal");
    System.out.println(sol.getAllAreas());
  }
}