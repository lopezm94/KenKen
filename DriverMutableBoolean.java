import java.io.IOException;
import java.util.Scanner;

/**
*<h1>DriverMutableBoolean</h1>
*Driver de la clase MutableBoolean con varios casos de prueba.
*
*@author Juan López
*/
public class DriverMutableBoolean {

    public static Scanner in;
    public static String instrucciones =
      "La prueba se realizara sobre un Pair<String,Integer>.\n\n"
      + "Puede realizar las siguientes acciones introduciendo el numero indicado: \n"
      + "1: Crear MutableBoolean por defecto.\n"
      + "2: Crear MutableBoolean con un valor especifico.\n"
      + "3: Hacer la operacion && con un valor especifico y guardarlo.\n"
      + "4: Hacer la operacion || con un valor especifico y guardarlo.\n"
      + "5: Asignarle un valor al MutableBoolean.\n"
      + "6: Obtener el valor actual de MutableBoolean.\n"
      + "7: Comprueba si el valor de MutableBoolean es igual a un valor especifico.\n"
      + "8: Obtener el estado del MutableBoolean.\n"
      + "Si desea salir del test introduzca un 0. \n";

  public static void main(String[] args) {
    //Instrucciones
    System.out.println(instrucciones);

    OrderedMapByValue map = null;
    Boolean value;
    MutableBoolean mb = null;
    Boolean end = false;
    in = new Scanner(System.in);

    while (!end) {
      switch (Integer.parseInt(in.nextLine())) {
        case 0:
          end = true;
          break;

        case 1:
          mb = new MutableBoolean();
          break;

        case 2:
          if (mb == null)
            System.out.println("MutableBoolean no construido, creelo antes de utilizar.");
          System.out.println("Introduzca true o false:");
          value = Boolean.parseBoolean(in.nextLine());
          mb = new MutableBoolean(value);
          break;

        case 3:
          if (mb == null)
            System.out.println("MutableBoolean no construido, creelo antes de utilizar.");
          System.out.println("Introduzca true o false:");
          value = Boolean.parseBoolean(in.nextLine());
          mb.setAnd(value);
          break;

        case 4:
          if (mb == null)
            System.out.println("MutableBoolean no construido, creelo antes de utilizar.");
          System.out.println("Introduzca true o false:");
          value = Boolean.parseBoolean(in.nextLine());
          mb.setOr(value);
          break;

        case 5:
          if (mb == null)
            System.out.println("MutableBoolean no construido, creelo antes de utilizar.");
          System.out.println("Introduzca true o false:");
          value = Boolean.parseBoolean(in.nextLine());
          mb.setValue(value);
          break;

        case 6:
          if (mb == null)
            System.out.println("MutableBoolean no construido, creelo antes de utilizar.");
          System.out.println(mb.getValue());
          break;

        case 7:
          if (mb == null)
            System.out.println("MutableBoolean no construido, creelo antes de utilizar.");
          System.out.println("Introduzca true o false:");
          value = Boolean.parseBoolean(in.nextLine());
          System.out.println(mb.equals(value));
          break;

        case 8:
          if (mb == null)
            System.out.println("MutableBoolean no construido, creelo antes de utilizar.");
          System.out.println(mb);
          break;

        default:
          System.out.println("Caso no reconocido, vuelva a intentarlo.");
          break;

      }
      System.out.println("\nInstroduzca la siguiente operacion");
    }

  }

}
