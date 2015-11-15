import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

/*@version 1.0
*@author Reyes Vera
*/

public class DriverGenerar {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner input = null;
		File test = null;

		//try {
			//test = new File("testGen");
			System.out.println("Quieres poner tama�o al KenKen?(Si/No)");
			input = new Scanner(System.in);
			boolean ok =false;
			TableroH tablero = null;
			while (! ok){
				String varS = input.next();
				char var2[] = varS.toCharArray();
				if (var2[0] == 'S' && var2[1] == 'i'){
					System.out.println("Valor:");
					int var = input.nextInt();
					tablero = new TableroH(var);
					ok = true;
				}
				else if (var2[0] == 'N' && var2[1] == 'o'){
					Random rnd = new Random();
					int var = rnd.nextInt();
					var = Math.abs(var);
					var = (var%9)+1;
					if (var < 3) var = 3;
					tablero = new TableroH(var);
					ok = true;
					System.out.println("Tama�o generado: "+ tablero.size());
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
							System.out.println("Quieres tener un �rea de tipo? (+,-,*,/)");
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
						System.out.println("Areas generadas: "+ tam);

					}
					else System.out.println("Negativos no...");
				}

				Boolean vect[] = new Boolean[tam];
				for (int i = 0; i < tam; ++i) vect[i] = false;

				System.out.println("Quieres ponerle a cada area sus casillas?(Si/No)");
				Boolean CasillasP = false;
				edita = true;
				while (! CasillasP){
					String varS = input.next();
					char var2[] = varS.toCharArray();
					if (var2[0] == 'S' && var2[1] == 'i') CasillasP = true;
					else if (var2[0] == 'N' && var2[1] == 'o'){
						CasillasP = true;
						edita = false;
					}
					else System.out.println("Como?");
				}

				int tama�o = tam;
				if (edita){
					System.out.println("Que area le quieres poner a cada casilla?");
					System.out.println("Area: " + "1 - "+ tama�o);
					int var = input.nextInt();
					int numC = tablero.size()*tablero.size();
					while (var >= 0){
						--var;
						System.out.println("Posicion x: (Min: 0, Max: " + (tablero.size()-1) + ")");
						int var2 = input.nextInt();
						System.out.println("Posicion y: (Min: 0, Max: "+ (tablero.size()-1) + ")");
						int var3 = input.nextInt();

						if (var2 >= 0 && var2 < tablero.size() && var3 >= 0 && var3 < tablero.size()){
							if (! vect[var] && var2>= 0 && var2 < tablero.size() && var3 >= 0 && var3 < tablero.size()){
								tablero.setid(var,var2,var3);
								vect[var] = true;
								--numC;
							}
							else if (vect[var]){
								Boolean toca= false;
							//	if (tablero.getAreaID(var2,var3) == -1){
									if (var2 >= 0 && var2 < tablero.size()){
										if (var3 >= 0 && var3 < tablero.size()){
											if (var == tablero.getAreaID(var2+1,var3)) toca = true;
											else if (var == tablero.getAreaID(var2+1,var3+1)) toca = true;
											else if (var == tablero.getAreaID(var2,var3+1)) toca = true;
										}
										else{
											if (var3 == tablero.size()){
												if (var == tablero.getAreaID(var2+1,var3)) toca = true;
												else if (var == tablero.getAreaID(var2+1,var3-1)) toca = true;

											}
										}
									}
									else if (var2 > 0 && var2 <= tablero.size()){
										if (var3 > 0 && var3 <= tablero.size()){
											if (var == tablero.getAreaID(var2-1,var3)) toca = true;
											else if (var == tablero.getAreaID(var2-1,var3-1)) toca = true;
											else if (var == tablero.getAreaID(var2,var3-1)) toca = true;

										}
										else{
											if (var3 == tablero.size()){
												if (var == tablero.getAreaID(var2-1,var3)) toca = true;
												else if (var == tablero.getAreaID(var2-1,var3+1)) toca = true;
											}
										}
									}
								//}
								if (toca){
									tablero.setid(var,var2,var3);
									--numC;
								}
								else System.out.println("La casilla que has puesto no toca con el area que le  corresponde o ya tiene area");
							}
							if (numC >= 0) {
								System.out.println("Area:");
								var = input.nextInt();
							}
						}
						else System.out.println("Valores incorrectos");
					}
				}
				else { //Poner area a casillas aleatorio
					--tama�o;
					int num = (tablero.size())^2;
					for (int i = 0; i < tablero.size();++i){
						for (int j = 0; j < tablero.size(); ++j){
							tablero.setid(tama�o,i,j);
							--num;
							if (tama�o != 0){
								Random rnd = new Random();
								int var = rnd.nextInt();
								var = Math.abs(var);
								if (var%tablero.size() == 0) --tama�o;
								else if (num <= tama�o) --tama�o;
							}
						}
					}
				}

				System.out.println("Quieres poner casillas fijas?(Si/No)");
				Boolean Fijas = false;
				edita = false;
				while (! Fijas){
					String varS = input.next();
					char var2[] = varS.toCharArray();
					if (var2[0] == 'S' && var2[1] == 'i'){
						Fijas = true;
						edita = true;
					}
					else if (var2[0] == 'N' && var2[1] == 'o') Fijas = true;
					else System.out.println("Como?");
				}
				if (edita){
					System.out.println("Recuerda que tu Kenken es de tama�o "+ tablero.size()+ " (Para salir teclea -1)");
					System.out.println("Valor casilla fija: (las casillas fijas no se modifican, tienes que poner un valor correcto");
					int var = input.nextInt();
					while (var != 1){
						Casilla cas = new Casilla(var,true,var);
						System.out.println("Donde la quieres poner?");
						System.out.println("Posici�n x:");
						int i = input.nextInt();
						System.out.println("Posici�n y:");
						int j = input.nextInt();
						tablero.setCasilla(cas,i,j);
						System.out.println("Valor casilla fija:");
						var = input.nextInt();
					}
				}
				System.out.println("Vamos a comprobar que tu Kenken es correcto");
				/*KenkenSolver solucion = new KenkenSolver(tablero);
				if (solucion.solveKenken()){
					System.out.println("Tu Kenken es correcto :)");
				}
				else System.out.println("Vuelve a intentarlo, tu Kenken no tiene solucion :(");
				*/
				System.out.println("Quieres resolver tu Kenken?");
				Boolean resol = false;
				edita = false;
				while (! resol){
					String varS = input.next();
					char var2[] = varS.toCharArray();
					if (var2[0] == 'S' && var2[1] == 'i'){
						resol = true;
						edita = true;
					}
					else if (var2[0] == 'N' && var2[1] == 'o') resol = true;
					else System.out.println("Como?");
				}
				if (edita){
				//	System.out.println(solucion);
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
