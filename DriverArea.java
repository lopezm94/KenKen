
import java.io.IOException;
import java.util.Scanner;

public class DriverArea{
	public static void main (String args[]) throws IOException{
		Area a = null;
		Boolean end = false;
		Scanner in = new Scanner(System.in);
		while(!end){
			switch(action(in)){
			case 0:
				end = true;
				break;
			case 1://instancia area
				System.out.println("Introdueix id area\n");
				int id = in.nextInt();
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
					a.calcular_resultat();
					int ai = a.get_resultat();
					System.out.println("El resultat es:"+ai+"\n");
				}
				break;
			case 4://retorna resultat actual
				if(a == null){
					System.out.println("L'area encara no existeix\n");
				}else{
					a.calcular_resultatactual();
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
					Boolean corr = a.correcte();
					System.out.println("El resultat es:"+corr+"\n");
				}
				break;
			case 8://vas be?check
				if(a == null){
					System.out.println("L'area encara no existeix\n");
				}else{
					System.out.println("Introdueix l'entorn(nxn):\n");
					int n =  in.nextInt();
					Boolean b = a.check(n);
					System.out.println("Es pot fer?"+b+"\n");
				}
				break;
			case 9://setres
				if(a == null){
					System.out.println("L'area encara no existeix\n");
				}else{
					System.out.println("Introdueix el resultat:\n");
					int res =  in.nextInt();
					a.set_res(res);
					System.out.println("El resultat ha estat configurat com a :"+res+"\n");
				}
				break;
			case 7://afegir areas
				if(a == null){
					System.out.println("L'area encara no existeix\n");
				}else{
					int nc;
					if(a.get_operacio() == '-' || a.get_operacio() == '/'){
						nc = 2;
					}else{
						System.out.println("Introdueix el nombre de caselles de l'area:\n");
						nc = in.nextInt();
					}
					for(int i=0; i < nc; ++i){
						System.out.println("Crea la nova casella a introduir: ");
						System.out.println("Valor de la casella:\n");
						int valor =  in.nextInt();
	                	System.out.println("Es fija? [t=1/f=0]:\n");
	                	int b = in.nextInt();
	                	Boolean fija;
	                	if (b == 1) fija = true;
	                	else if (b == 0) fija = false;
	                	else {
	                		System.out.println("Valor no valido");
	                		break;
	                	}
	                	System.out.println("Solucion de la Casilla:\n");
	                	int sol =  in.nextInt();
	                	Casilla c = new Casilla(valor,fija,sol);
	                	a.afegir_casella(c);
					}
					a.calcular_resultat();
				}
				break;
			case 10://set valor casella
				if(a == null){
					System.out.println("L'area encara no existeix\n");
				}else{
					System.out.println("Casella num? ");
					int num =  in.nextInt();
					System.out.println("Valor de la casella:\n");
					int valor =  in.nextInt();
					Casilla c = a.get_casella(num);
					c.setValor(valor);
					a.set_casella(c,num);
					System.out.println("El valor de la casella ha sigut canviat\n");
					a.calcular_resultatactual();
				}
				break;
			case 11://print valors caselles
				if(a == null){
					System.out.println("L'area encara no existeix\n");

				}else{
					for(int i=0; i < a.get_tamany(); ++i){
						Casilla c1 = a.get_casella(i);
						System.out.println("Valor: "+c1.getValor()+"\nSolucio: "+c1.getSolucion()+"\n");
					}
				}
				break;
			default:
                System.out.println("Accion introducida no es correcta. Intentelo de nuevo.");
                break;
			}
			
		}
		in.close();
	}
	
	private static int action(Scanner in1) throws IOException{
		System.out.println(	"1 - Crear Area amb parametres\n"+
							"2 - Obtenir operacio de l'area\n"+
							"3 - Obtenir resultat de l'area\n"+
							"4 - Obtenir resultat actual de l'area\n"+
							"5 - Obtenir ID de l'àrea\n"+
							"6 - Saber si el resultat es correcte\n"+
							"7 - Afegeix les caselles a l'area\n"+
							"8 - Saber si es possible arribar a la solucio amb les caselles entrades\n"+
							"9 - Introduir resultat area(per quan estan entrades les caselles)\n"+
							"10 - Canviar valor casella\n"+
							"11 - Ensenyar els valors de caselles\n"+
							"Per sortir : 0\n");
		int res = in1.nextInt();
		return res;
	}
}
