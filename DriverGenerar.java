import java.io.File;
import java.io.FileNotFoundException;
//import java.io.FileNotFoundException;
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

		try {
			test = new File("testGen");
			System.out.println("Quieres poner tamaño al KenKen?(Si/No)");
<<<<<<< HEAD
			input = new Scanner(System.in);
			//input = new Scanner(test);

=======
			input = new Scanner(test);
		//try {
			//test = new File("testGen");
			System.out.println("Quieres poner tamaï¿½o al KenKen?(Si/No)");
			input = new Scanner(System.in);
>>>>>>> 5bd503a30e6c0dbe52058502aec09ee756f2ed30
			boolean ok =false;
			TableroH tablero = null;
			while (! ok){
				String varS = input.next();
				char var2[] = varS.toCharArray();
				if (var2[0] == 'S' && var2[1] == 'i'){
					System.out.println("Valor: (3-9)");
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
					System.out.println("Tamaño generado: "+ tablero.size());
				}
				else System.out.println("Como?");
			}
			for (int i = 0;i< tablero.size();++i){
				for (int j = 0; j < tablero.size();++j){
					Casilla cas1 = new Casilla();
					tablero.setCasilla(cas1,i,j);
				}
			}
				System.out.println("Cuantas areas quieres poner?(0 si no quieres ponerlas tu)");
				ok = false;
				int tam = 0;
				Boolean edita = false;
				Boolean rand = false;
				while (! ok){
					int var = input.nextInt();
					if (var >  0){
						edita = true;
						tam = var;
<<<<<<< HEAD
						int at = 0;
						while (at < var){
							System.out.println("Quieres tener un area de tipo? (+,-,*,/)");
=======
						while (var != 0){
							System.out.println("Quieres tener un area de tipo? (+,-,*,/)");
							System.out.println("Quieres tener un ï¿½rea de tipo? (+,-,*,/)");
>>>>>>> 5bd503a30e6c0dbe52058502aec09ee756f2ed30
							String varS = input.next();
							char var2[] = varS.toCharArray();
							if (var2[0] == '+' || var2[0] == '-' || var2[0] == '*' || var2[0] == '/'){
								
								Area a = new Area(at,var2[0]);
								System.out.println("Que resultado tiene que dar este area?");
								int var3 = input.nextInt();
								tablero.afegirArea(a,var3);
								++at;
							}
							else System.out.println("Pon un valor correcto");
						}
						ok = true;
					}
					else if (var == 0){
						rand = true;
						Random rnd = new Random();
						var = rnd.nextInt();
						var = Math.abs(var);
						int mod = tablero.size()*tablero.size();
						var = (var%mod)+1;
						int are = var;
						int pos = rnd.nextInt();
						pos = pos%tablero.size();
						pos = Math.abs(pos);
						if(pos == 0) pos =1;
						for (int i = 0; i < tablero.size(); ++i){
							for (int j = 0; j < tablero.size();++j){
								tablero.setCasillaSol(i, j, pos);
								++pos;
								if (pos > tablero.size()) pos = 1;
							}
							++pos;
							if (pos > tablero.size()) pos = 1;
						}
						tam = 0;
						while (var != 0){
							int varR = rnd.nextInt();
							varR = Math.abs(varR);
							varR = var%3;
							char c = '+';
							if (varR == 1) c = '*';
							Area a = new Area(var-1,c);
							tablero.afegirArea(a,0);
							--var;
							++tam;
						}
						--tam;
						int num = (tablero.size())^2;
						for (int i = 0; i < tablero.size();++i){
							for (int j = 0; j < tablero.size(); ++j){
								tablero.setid(tam,i,j);
								--num;
								if (tam != 0){
									int var5 = rnd.nextInt();
									var5 = Math.abs(var5);
									if (var5 < 10 && var5 > 0) --tam;
									else if (num <= tam) --tam;
								}
							}
						}
						
						tablero.colocaRes();
						
						System.out.println(tablero);
						ok = true;
						System.out.println("Areas generadas: "+ are);

					}
					else System.out.println("Negativos no...");
				}
				if (! rand){
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
<<<<<<< HEAD
	
					int tamaño = tam;
					if (edita){
						System.out.println("Que area le quieres poner a cada casilla?");
						System.out.println("Area: " + "1 - "+ tamaño);
						int var = input.nextInt();
						int numC = tablero.size()*tablero.size();
						while (numC > 0){
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
									if (tablero.getAreaID(var2,var3) == -1){
										if (var2 >= 0 && var2 < (tablero.size()-1)){
											if (var3 >= 0 && var3 < (tablero.size()-1)){
=======
					else System.out.println("Como?");
				}

				int tamaño = tam;
				if (edita){
					System.out.println("Que area le quieres poner a cada casilla?");
					System.out.println("Area: " + "1 - "+ tamaï¿½o);
					int var = input.nextInt();
					int numC = tablero.size()*tablero.size();
					while (numC > 0){
						--var;
						System.out.println("Posicion x: (Min: 0, Max: " + (tablero.size()-1) + ")");
						int var2 = input.nextInt();
						System.out.println("Posicion y: (Min: 0, Max: "+ (tablero.size()-1) + ")");
						int var3 = input.nextInt();

			/*---->*/			if (var2 >= 0 && var2 < tablero.size() && var3 >= 0 && var3 < tablero.size()){
						if (var2 >= 0 && var2 < tablero.size() && var3 >= 0 && var3 < tablero.size()){
							if (! vect[var] && var2>= 0 && var2 < tablero.size() && var3 >= 0 && var3 < tablero.size()){
								tablero.setid(var,var2,var3);
								vect[var] = true;
								--numC;
							}
							else if (vect[var]){
								Boolean toca= false;
							//	if (tablero.getAreaID(var2,var3) == -1){
									/*if (var2 >= 0 && var2 < tablero.size()){
										if (var3 >= 0 && var3 < tablero.size()-1){
=======
									if (var2 >= 0 && var2 < tablero.size()){
										if (var3 >= 0 && var3 < tablero.size()){
>>>>>>> c7467b778e02c0e80413c97674a1b80c6e3da45b
											if (var == tablero.getAreaID(var2+1,var3)) toca = true;
											else if (var == tablero.getAreaID(var2+1,var3+1)) toca = true;
											else if (var == tablero.getAreaID(var2,var3+1)) toca = true;
										}
										else{
											if (var3 == tablero.size()){
>>>>>>> 5bd503a30e6c0dbe52058502aec09ee756f2ed30
												if (var == tablero.getAreaID(var2+1,var3)) toca = true;
												else if (var == tablero.getAreaID(var2,var3+1)) toca = true;
											}
										}
										else if (var2 > 0 && var2 <= tablero.size()){
											if (var3 > 0 && var3 <= (tablero.size()-1)){
												if (var == tablero.getAreaID(var2-1,var3)) toca = true;
												else if (var == tablero.getAreaID(var2,var3-1)) toca = true;
	
											}
										}
									}
									toca = true;
									if (toca){
										tablero.setid(var,var2,var3);
										--numC;
									}
									else System.out.println("La casilla que has puesto no toca con el area que le  corresponde");
								}
								if (numC > 0) {
									System.out.println("Area:");
									var = input.nextInt();
								}
							}
							else System.out.println("Valores incorrectos");
						}
					}
					else { //Poner area a casillas aleatorio
						--tamaño;
						rand = true;
						int num = (tablero.size())^2;
						for (int i = 0; i < tablero.size();++i){
							for (int j = 0; j < tablero.size(); ++j){
								tablero.setid(tamaño,i,j);
								--num;
								if (tamaño != 0){
									Random rnd = new Random();
									int var = rnd.nextInt();
									var = Math.abs(var);
									if (var%tablero.size() == 0) --tamaño;
									else if (num <= tamaño) --tamaño;
								}
							}
						}
					}
<<<<<<< HEAD
					if (! rand){
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
							System.out.println("Recuerda que tu Kenken es de tamaño "+ tablero.size()+ " (Para salir teclea -1)");
							System.out.println("Valor casilla fija: (las casillas fijas no se modifican, tienes que poner un valor correcto");
							int var = input.nextInt();
							while (var != -1){
								System.out.println("Donde la quieres poner?");
								System.out.println("Posicion x:");
								int i = input.nextInt();
								System.out.println("Posicion y:");
								int j = input.nextInt();
								tablero.setCasillaFija(var,i,j);
								System.out.println("Valor casilla fija:");
								var = input.nextInt();
							}
						}
=======
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
					System.out.println("Recuerda que tu Kenken es de tamaï¿½o "+ tablero.size()+ " (Para salir teclea -1)");
					System.out.println("Valor casilla fija: (las casillas fijas no se modifican, tienes que poner un valor correcto");
					int var = input.nextInt();
					while (var != -1){
						System.out.println("Donde la quieres poner?");
						System.out.println("Posicion x:");
						int i = input.nextInt();
						System.out.println("Posicion y:");
						System.out.println("Posiciï¿½n x:");
						int i = input.nextInt();
						System.out.println("Posiciï¿½n y:");
						int j = input.nextInt();
						tablero.setCasillaFija(var,i,j);
						System.out.println("Valor casilla fija:");
						var = input.nextInt();
>>>>>>> 5bd503a30e6c0dbe52058502aec09ee756f2ed30
					}
				}
				
				//tablero.checkarea();
				
<<<<<<< HEAD
				System.out.println("Vamos a comprobar que tu Kenken es correcto");
=======
				System.out.println("Vamos a comprobar que tu Kenken es correcto")
>>>>>>> 5bd503a30e6c0dbe52058502aec09ee756f2ed30
				
				//System.out.println(tablero);
				
				KenkenSolver solucion = new KenkenSolver(tablero);
<<<<<<< HEAD
				Boolean solu = false;
=======
				/*KenkenSolver solucion = new KenkenSolver(tablero);
>>>>>>> c7467b778e02c0e80413c97674a1b80c6e3da45b
>>>>>>> 5bd503a30e6c0dbe52058502aec09ee756f2ed30
				if (solucion.solveKenken()){
					System.out.println("Tu Kenken es correcto :)");
					solu = true;
				}
				else System.out.println("Vuelve a intentarlo, tu Kenken no tiene solucion :(");
<<<<<<< HEAD
				if (solu){
					System.out.println("Quieres ver la solucion de tu Kenken?");
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
						String res = "";
						for (int i = 0; i < tablero.size(); ++i){
							for (int j = 0; j < tablero.size(); ++j){
								res = res + tablero.getCasillaSol(i, j) + " ";
							}
							res = res + "\n";
						}
						System.out.println(res);
=======
<<<<<<< HEAD
				
=======
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
>>>>>>> 5bd503a30e6c0dbe52058502aec09ee756f2ed30
					}
				}
		}/* catch (FileNotFoundException e) {
         e.printStackTrace();
       }*/
		finally {
	          if (input != null) {
	            input.close();                      // Close the file scanner.
	        }
	}

	}
}
