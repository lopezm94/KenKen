import java.io.IOException;
import java.util.Scanner;

/**
*<h1>DriverOrderedMapByValue</h1>
*Driver de la clase OrderedMapByValue con varios casos de prueba.
*
*@author Juan López
*/
public class DriverOrderedMapByValue {

  public static Scanner in;
  public static String instrucciones =
    "La prueba se realizara sobre un Pair<String,Integer>.\n\n"
    + "Puede realizar las siguientes acciones introduciendo el numero indicado: \n"
    + "1: Crear OrderedMapByValue.\n"
    + "2: Agregar una llave y un valor.\n"
    + "3: Remover los elementos del Map con una llave especifica.\n"
    + "4: Dada una llave, obtener el primer par llave valor.\n"
    + "5: Retornar tamaño de map.\n"
    + "6: Retornar el estado del map.\n"
    + "Si desea salir del test introduzca un 0. \n";

  public static void main (String[] args) throws Exception {
    //Instrucciones
    System.out.println(instrucciones);

    OrderedMapByValue map = null;
    String id,aux;
    Integer value;
    Boolean end = false;
    in = new Scanner(System.in);

    while (!end) {
      switch (Integer.parseInt(in.nextLine())) {
        case 0:
          end = true;
          break;

        case 1:
          System.out.println("Introduzca 'naturalOrder' o 'reverseOrder'.");
          aux = in.nextLine();
          map = new OrderedMapByValue(aux);
          break;

        case 2:
          if (map == null)
            System.out.println("Map no construido, creelo antes de utilizar.");
          System.out.println("Introduzca la llave.");
          id = in.nextLine();

          System.out.println("Introduzca el valor.");
          value = Integer.parseInt(in.nextLine());

          map.push(id,value);
          break;

        case 3:
          if (map == null)
            System.out.println("Map no construido, creelo antes de utilizar.");
          System.out.println("Introduzca la llave.");
          id = in.nextLine();
          map.remove(id);
          break;

        case 4:
          if (map == null)
            System.out.println("Map no construido, creelo antes de utilizar.");
          System.out.println("Introduzca la llave.");
          id = in.nextLine();
          System.out.println("Pair: " + map.getPair(id));
          break;

        case 5:
          if (map == null)
            System.out.println("Map no construido, creelo antes de utilizar.");
          System.out.println("Tamaño: " + map.size());
          break;

        case 6:
          if (map == null)
            System.out.println("Map no construido, creelo antes de utilizar.");
          System.out.println("map: \n" + map);
          break;

        default:
          System.out.println("Caso no reconocido, vuelva a intentarlo.");
          break;

      }
      System.out.println("\nInstroduzca la siguiente operacion");
    }

  }

}
