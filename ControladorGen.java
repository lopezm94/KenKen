import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;
import java.util.Timer;

/**
*@version 1.0
*@author Reyes Vera
*/

public class ControladorGen {

		
		
		Scanner in = new Scanner(System.in);

		
		public int tamano(){
			int var = 0;
				//in = new Scanner(System.in);
				System.out.println("Quieres poner tamano al KenKen?(Si/No)");

				boolean ok =false;
				while (! ok){
					String varS = in.next();
					char var2[] = varS.toCharArray();
					if (var2[0] == 'S' && var2[1] == 'i'){
						System.out.println("Valor: (3-9)");
						var = in.nextInt();
						ok = true;
					}
					else if (var2[0] == 'N' && var2[1] == 'o'){
						Random rnd = new Random();
						var = rnd.nextInt();
						var = Math.abs(var);
						var = (var%9)+1;
						if (var < 3) var = 3;
						ok = true;
						System.out.println("tamano generado: "+ var);
					}
					else System.out.println("Como?");
				}
				
		return var;
	}

	public int numAreas(){
		int var = 0;
			System.out.println("Cuantas areas quieres poner?(0 si no quieres ponerlas tu)");
			var = in.nextInt();
		return var;
	}
	
	public Area tipoArea(int at, char op){
			Area a = AreaBuilder.newArea(at,op);
			return a;
	
	}
	
	public int resArea(){
		//in = new Scanner(System.in);
		System.out.println("Que resultado tiene que dar este area?");
		return in.nextInt();
	}
	
	public String dif(){
		//in = new Scanner(System.in);
	    System.out.println("Que dificultad desea? (Facil, Medio o Dificil)");
		return in.nextLine();
	}
	
	public Boolean Casillas(int var){
		Boolean edita = false;
			//in = new Scanner(System.in);
			if (var == 1) System.out.println("Quieres ponerle a cada area sus casillas?(Si/No)");
			if (var == 2) System.out.println("Quieres poner casillas fijas?(Si/No)");
			edita = true;
			while (! edita){
				String varS = in.next();
				char var2[] = varS.toCharArray();
				if (var2[0] == 'S' && var2[1] == 'i') edita = true;
				else if (var2[0] == 'N' && var2[1] == 'o'){
					edita = true;
				}
				else System.out.println("Como?");
		}
		return edita;
	}
	
	public TableroH modTab(TableroH tablero){
		int tamano = tablero.getNumAreas();
			Boolean vect[] = new Boolean[tablero.getNumAreas()];
			for (int i = 0; i < tablero.getNumAreas(); ++i) vect[i] = false;
			//in = new Scanner(System.in);
			System.out.println("Que area le quieres poner a cada casilla?");
			System.out.println("Area: " + "1 - "+ tamano);
			int var = in.nextInt();
			int numC = tablero.size()*tablero.size();
			while (numC > 0){
				--var;
				System.out.println("Posicion x: (Min: 0, Max: " + (tablero.size()-1) + ")");
				int var2 = in.nextInt();
				System.out.println("Posicion y: (Min: 0, Max: "+ (tablero.size()-1) + ")");
				int var3 = in.nextInt();
	
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
						var = in.nextInt();
					}
				}
				else System.out.println("Valores incorrectos");
			}
		return tablero;
	}
	
	public TableroH modCas(TableroH tablero){
			//in = new Scanner(System.in);
		System.out.println("Recuerda que tu Kenken es de tamano "+ tablero.size()+ " (Para salir teclea -1)");
		System.out.println("Valor casilla fija: (las casillas fijas no se modifican, tienes que poner un valor correcto");
		int var = in.nextInt();
		while (var != -1){
			System.out.println("Donde la quieres poner?");
			System.out.println("Posicion x:");
			int i = in.nextInt();
			System.out.println("Posicion y:");
			int j = in.nextInt();
			tablero.setCasillaFija(var,i,j);
			System.out.println("Valor casilla fija:");
			var = in.nextInt();
		}
		return tablero;
	}
}
	