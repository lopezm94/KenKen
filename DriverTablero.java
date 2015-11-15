import java.io.File;
import java.io.FileNotFoundException;
//import java.io.IOException;
import java.util.Scanner;
//import java.io.*;


/*@version 1.0
*@author Reyes Vera
*/


public class DriverTablero {


	public static void main(String[] args) {
		Scanner input = null;
		File test = null;

		try {
			test = new File("test3");
			System.out.println("Entra tama�o KenKen:");
			input = new Scanner(test);
			int var = input.nextInt();
			int casellas;
			TableroH tablero = new TableroH(var);
			casellas = tablero.files * tablero.files;
			for (int i = 0; i < tablero.files;++i){
				for (int j = 0; j < tablero.files;++j){
					var = input.nextInt();
					Boolean var2 = input.nextBoolean();
					int var3 = input.nextInt();
					Casilla cas = new Casilla(var,var2,var3);
					tablero.setCasilla(cas,i,j);
					if (var2) --casellas;
				}
			}
			var = input.nextInt();
			while (var != -1){
<<<<<<< HEAD
				System.out.println("Quieres tener un area de tipo? (+,-,*,/)");
=======
				System.out.println("Quieres tener un �rea de tipo? (+,-,*,/)");
>>>>>>> c7467b778e02c0e80413c97674a1b80c6e3da45b
				String varS = input.next();
				char var2[] = varS.toCharArray();
				Area a = new Area(var,var2[0]);
	/*---->*/			tablero.afegirArea(a,0);
				var = input.nextInt();
			}
			int a = tablero.files * tablero.files;
			System.out.println("Que area le quieres poner a cada casilla?");
			while(a != 0){
				var = input.nextInt();
				int var2 = input.nextInt();
				int var3 = input.nextInt();
				tablero.setid(var,var2,var3);
				--a;
			}
			Boolean acabat = false;
			while (! acabat){
				int varC = casellas;
				while(varC != 0){
					var = input.nextInt();
					int var2 = input.nextInt();
					int var3 = input.nextInt();
					tablero.setCasillaVal(var,var2,var3);
					--varC;
				}

				System.out.println(tablero);

				Boolean resolv = false;
				if (tablero.tableroCheck()){
					if (tablero.numerosCheck()){
						System.out.println("El KenKen es correcto, FELICIDADES!");
						acabat = true;
					}
					else{
						System.out.println("Tienes problemas en la colocaci�n de los numeros");
						resolv = true;
					}
				}
				else{
					System.out.println("Tienes problemas con las areas, vigila tambien con los numeros!");
					resolv = true;
				}
				if (resolv){
					Boolean ok = false;
					while (! ok){
						System.out.println("Quieres ver la soluci�n del KenKen? (Si/No)");
						String varS = input.next();
						char var2[] = varS.toCharArray();
						if (var2[0] == 'S' && var2[1] == 'i'){
							String res = "";
							for (int i = 0; i < tablero.files;++i){
								for (int j = 0; j < tablero.files; ++j){
									res = res + tablero.getCasillaSol(i,j) + " ";
								}
							res = res + "\n";
							}
							System.out.println(res);
							ok = true;
							acabat = true;
						}
						else if (var2[0] == 'N' && var2[1] == 'o') ok = true;
						else System.out.println("Como?");
						}
				 }
			}
	        } catch (FileNotFoundException e) {
	          e.printStackTrace();
	        } finally {
	          if (input != null) {
	            input.close();                      // Close the file scanner.
	        }
	    }



	}

}
