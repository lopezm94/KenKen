package domini;

import java.io.IOException;
import java.util.Scanner;
import domini.*;

public class DriverArea{
	public static void main (String args[]) throws IOException{
		Area a = null;
		Casilla c = null;
		Boolean end = false;
		Scanner in = new Scanner(System.in);
		while(!end){
			switch(action()){
			case 0:
				end = true;
				break;
			case 1://instancia area
				System.out.println("Introdueix id area\n");
				int id = Integer.parseInt(in.nextLine());
				System.out.println("Introdueix operacio area(1->+,2->-,3->*,4->/, )\n");
				char ca = (char)System.in.read();
				a = new Area(id,ca);
				break;
			case 2://get_operacio
				if(a == null){
					System.out.println("L'area encara no existeix\n");
				}else{
					char ch = a.get_operacio();
					System.out.println("La operacio es:"+ch+"\n");
				}
				break;
			case 3://retorna resultat real
				if(a == null){
					System.out.println("L'area encara no existeix\n");
				}else{
					int i = a.get_resultat();
					System.out.println("El resultat es:"+i+"\n");
				}
				break;
			case 4://retorna resultat actual
				if(a == null){
					System.out.println("L'area encara no existeix\n");
				}else{
					int i = a.get_resultatactual();
					System.out.println("El resultat actual es:"+i+"\n");
				}
				break;
			case 5://retorna id area
				if(a == null){
					System.out.println("L'area encara no existeix\n");
				}else{
					int i = a.get_posicio();
					System.out.println("El id es:"+i+"\n");
				}
				break;
			case 6://diu si es correcte
				if(a == null){
					System.out.println("L'area encara no existeix\n");
				}else{
					int i = a.get_resultat();
					System.out.println("El resultat es:"+i+"\n");
				}
				break;
			case 8://vas be?check
				if(a == null){
					System.out.println("L'area encara no existeix\n");
				}else{
					System.out.println("Introdueix l'entorn(nxn):\n");
					int n = Integer.parseInt(in.nextLine());
					Boolean b = a.check(n);
					System.out.println("Es pot fer?"+i+"\n");
				}
				break;
			case 9://setres
				if(a == null){
					System.out.println("L'area encara no existeix\n");
				}else{
					System.out.println("Introdueix el resultat:\n");
					int res = Integer.parseInt(in.nextLine());
					a.set_res(res);
					System.out.println("El resultat ha estat configurat com a :"+res+"\n");
				}
				break;
			case 10://calcular res act
				if(a == null){
					System.out.println("L'area encara no existeix\n");
				}else{
					a.calcular_resultatactual();
					System.out.println("El resultat actual s'ha actualitzat\n");
				}
				break;
			case 7://afegir areas
				if(a == null){
					System.out.println("L'area encara no existeix\n");
				}else{
					System.out.println("Introdueix el nombre de caselles de l'area:\n");
					int nc = Integer.parseInt(in.nextLine());
					if((a.get_operacio() == '-' || a.get_operacio() == '/') && nc > 2){
						System.out.println("Per resta i divisio nomes poden haver-hi dues caselles");
					}else{
						for(int i=0; i < nc; ++i){
							System.out.println("Crea la nova casella a introduir: ");
							System.out.println("Valor de la casella:\n");
							int valor = Integer.parseInt(in.nextLine());
	                		System.out.println("Es fija? [t/f]:\n");
	                		String b = in.nextLine();
	                		boolean fija;
	                		if (b.equals("t")) fija = true;
	                		else if (b.equals("f")) fija = false;
	                		else {
	                			System.out.println("Valor no valido");
	                			break;
	                		}
	                		System.out.println("Solucion de la Casilla:\n");
	                		int sol = Integer.parseInt(in.nextLine());
	                		Casilla c = new Casilla(valor,fija,sol);
	                		a.afegir_casella(c);
						}
					}
				}
				break;
			case 11://set valor casella
				if(a == null){
					System.out.println("L'area encara no existeix\n");
				}else{
					System.out.println("Casella num? ");
					int num = Integer.parseInt(in.nextLine());
					System.out.println("Valor de la casella:\n");
					int valor = Integer.parseInt(in.nextLine());
					Casilla c = a.get_casella(num);
					c.setValor(valor);
					a.set_casella(c,num);
					System.out.println("El valor de la casella ha sigut canviat\n");
				}
			default:
                System.out.println("Accion introducida no es correcta. Intentelo de nuevo.");
                break;
			}
			
		}
	}
	
	private static int action() throws IOException{
		System.out.println(	"1 - Crear Area amb parametres\n"+
							"2 - Obtenir operacio de l'area\n"+
							"3 - Obtenir resultat de l'area\n"+
							"4 - Obtenir resultat actual de l'area\n"+
							"5 - Obtenir ID de l'àrea\n"+
							"6 - Saber si el resultat es correcte\n"+
							"7 - Afegeix les caselles a l'area\n"+
							"8 - Saber si es possible arribar a la solucio amb les caselles entrades\n"+
							"9 - Introduir resultat area(per quan estan entrades les caselles)\n"+
							"10 - Calcular resultat actual"+
							"11 - Canviar valor casella"
							"Per sortir : 0\n");
		Scanner in = new Scanner(System.in);
		return in.nextInt();
	}
}
