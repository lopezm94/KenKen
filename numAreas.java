import java.util.Scanner;

public class numAreas {
	private Scanner input;
	
	public int numArea(){
		int var = 4;
		try {
			input = new Scanner(System.in);
			System.out.println("Cuantas areas quieres poner?(0 si no quieres ponerlas tu)");
			System.out.println("Hola");

			//var = input.nextInt();
			System.out.println("Hola");
			//if (var == 0) 
		}
		finally {
	          if (input != null) {
	            input.close();                      // Close the file scanner.
	        }
	}
		return var;
	}
}
