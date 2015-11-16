import java.io.IOException;
import java.util.Scanner;

/**
*<h1>DriverPair</h1>
*Driver de la clase Pair con varios casos de prueba.
*
*@author Juan López
*/
public class DriverPair {

  public static Scanner in;
  public static String instrucciones =
    "La prueba se realizara sobre un Pair<String,Integer>.\n\n"
    + "Puede realizar las siguientes acciones introduciendo el numero indicado: \n"
    + "1: Crear un par vacio.\n"
    + "2: Crear un par con los dos atributos.\n"
    + "3: Asignar un valor al primer elemento del par.\n"
    + "4: Asignar un valor al segundo elemento del par.\n"
    + "5: Retorna el primer atributo del par.\n"
    + "6: Retorna el segundo atributo del par.\n"
    + "7: Retorna el estado del par"
    + "Si desea salir del test introduzca un 0. \n";

  public static void main (String[] args) throws Exception {
    //Instrucciones
    System.out.println(instrucciones);

    Pair pair = null;
    String first;
    Integer second;
    Boolean end = false;
    in = new Scanner(System.in);

    while (!end) {
      switch (Integer.parseInt(in.nextLine())) {
        case 0:
          end = true;
          break;

        case 1:
          pair = new Pair<String,Integer>();
          break;

        case 2:
          System.out.println("Introduzca el primer atributo.");
          first = in.nextLine();

          System.out.println("Introduzca el segundo atributo.");
          second = Integer.parseInt(in.nextLine());

          pair = new Pair<String,Integer>(first,second);
          break;

        case 3:
          if (pair == null)
            System.out.println("Pair no construido, creelo antes de utilizar.");
          System.out.println("Introduzca el primer atributo.");
          pair.setFirst(in.nextLine());
          break;

        case 4:
          if (pair == null)
            System.out.println("Pair no construido, creelo antes de utilizar.");
          System.out.println("Introduzca el segundo atributo.");
          pair.setSecond(Integer.parseInt(in.nextLine()));
          break;

        case 5:
          if (pair == null)
            System.out.println("Pair no construido, creelo antes de utilizar.");
          System.out.println("Primer atributo: " + pair.getFirst());
          break;

        case 6:
          if (pair == null)
            System.out.println("Pair no construido, creelo antes de utilizar.");
          System.out.println("Segundo atributo: " + pair.getSecond());
          break;

        case 7:
          if (pair == null)
            System.out.println("Pair no construido, creelo antes de utilizar.");
          else if (pair.getFirst() == null || pair.getSecond() == null)
            System.out.println("Hay un valor no inicializado, asignelo antes de utilizar.");
          else
            System.out.println("Pair: " + pair);
          break;

        default:
          System.out.println("Caso no reconocido, vuelva a intentarlo.");
          break;

      }
      System.out.println("\nInstroduzca la siguiente operacion");
    }

  }

}
