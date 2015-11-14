package domini;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

public class DriverGenerar {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner input = null;
		File test = null;
		
		//try {
			//test = new File("testGen");
			System.out.println("Quieres poner tamaño al KenKen?(Si/No)");
			input = new Scanner(System.in);
			boolean ok =false;
			TableroH tablero = null;
			while (! ok){
				String varS = input.next();
				char var2[] = varS.toCharArray();
				if (var2[0] == 'S' && var2[1] == 'i'){
					int var = input.nextInt();
					tablero = new TableroH(var);
					ok = true;
				}
				else if (var2[0] == 'N' && var2[1] == 'o'){
					Random rnd = new Random();
					int var = rnd.nextInt();
					var = Math.abs(var);
					var = (var%9)+1;
					tablero = new TableroH(var);
					ok = true;
				}
				else System.out.println("Como?");
			}
				System.out.println("Cuantas areas quieres poner?(0 si no quieres ponerlas tu)");
				ok = false;
				int tam = 0;
				Boolean edita = false;
				while (! ok){
					int var = input.nextInt();
					if (var >  0){
						edita = true;
						tam = var;
						while (var != 0){
							System.out.println("Quieres tener un área de tipo? (+,-,*,/)");
							String varS = input.next();
							char var2[] = varS.toCharArray();
							if (var2[0] == '+' || var2[0] == '-' || var2[0] == '*' || var2[0] == '/'){
								Area a = new Area(var-1,var2[0]);
								tablero.afegirArea(a);
								--var;
							}
							else System.out.println("Pon un valor correcto");
						}
						ok = true;
					}
					else if (var == 0){
						Random rnd = new Random();
						var = rnd.nextInt();
						var = Math.abs(var);
						int mod = tablero.size()*tablero.size();
						var = (var%mod)+1;
						tam = var;
						while (var != 0){
							var = rnd.nextInt();
							var = Math.abs(var);
							var = var%4;
							char c = '+';
							if (var == 1) c = '-';
							else if (var == 2) c = '*';
							else if (var == 3) c = '/';
	
							Area a = new Area(var-1,c);
							tablero.afegirArea(a);
							--var;
						}
						ok = true;
					}
					else System.out.println("Negativos no...");
				}
				Boolean vect[] = new Boolean[tam];
				for (int i = 0; i < tam; ++i) vect[i] = false;
				if (edita){
					System.out.println("Que area le quieres poner a cada casilla?(-1 para parar)");
					System.out.println("Area:");
					int var = input.nextInt();
					while (var != -1){
						--var;
						System.out.println("Posicion x: (0," + (tablero.size()-1) + ")");
						int var2 = input.nextInt();
						System.out.println("Posicion y:" + (tablero.size()-1) + ")");
						int var3 = input.nextInt();
						
						if (! vect[var] && var2>= 0 && var2 < tablero.size() && var3 >= 0 && var3 < tablero.size()){
							tablero.setid(var,var2,var3);
							vect[var] = true;
						}
						else if (vect[var]){
							Boolean toca= false;
							if (var2 >= 0 && var2 < tablero.size()){
								if (var3 >= 0 && var3 < tablero.size()){
									if (var == tablero.getAreaID(var2+1,var3)) toca = true;
									else if (var == tablero.getAreaID(var2+1,var3+1)) toca = true;
									else if (var == tablero.getAreaID(var2,var3+1)) toca = true;
									else if (var == tablero.getAreaID(var2+1,var3-1)) toca = true;
								}
								else{
									if (var3 == tablero.size()){
										if (var == tablero.getAreaID(var2+1,var3)) toca = true;
									}
								}
							}
							else if (var2 > 0 && var2 <= tablero.size()){
								if (var3 > 0 && var3 <= tablero.size()){
									if (var == tablero.getAreaID(var2-1,var3)) toca = true;
									else if (var == tablero.getAreaID(var2-1,var3-1)) toca = true;
									else if (var == tablero.getAreaID(var2,var3-1)) toca = true;
									else if (var == tablero.getAreaID(var2-1,var3+1)) toca = true;
		
								}
								else{
									if (var3 == tablero.size()){
										if (var == tablero.getAreaID(var2-1,var3)) toca = true;
									}
								}
							}
							if (toca){
								tablero.setid(var,var2,var3);
							}
							else System.out.println("La casilla que has puesto no toca con el area que le  corresponde");
						}
						System.out.println("Area:");
						var = input.nextInt();
					}
				}
				else { //Poner casillas aleatorias
					
				}
			
		//}
		/*catch (FileNotFoundException e) {
	          e.printStackTrace();
	        } finally {
	          if (input != null) {
	            input.close();                      // Close the file scanner.
	        }*/
	}

	}
//}
