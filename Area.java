package domini;

import java.util.ArrayList;
import java.lang.Math;
public class Area{

	//Atributs
	
	private int pos;//identificador area, va de 0 a nombreAreas-1
	private char op;//tipus operacio, sense op es casella sola
	private ArrayList<Casilla > caselles;//conte la llista de caselles
	private int res;//resultat real de l'area
	private int resact;
	
	
	//Metodes	
	
	public Area(int pos, char op){//tenir en compte la operacio per despres afegir caselles
		this.pos = pos;
		this.op = op;
		res = 0;
		resact = 0;
		caselles = new ArrayList<Casilla>();
	}

	public char get_operacio(){
		return op;
	}

	public int get_resultat(){
		return res;
	}

	public int get_resultatactual(){
		this.calcular_resultatactual();
		return resact;
	}
	
	public int get_posicio(){
		return pos;
	}
	
	public Boolean correcte(){
		this.calcular_resultatactual();
		return res == resact || res == -resact;
	}
	
	public void afegir_casella(Casilla cas){//es fara 1 cop si =;2 per - i /; 2 o mes * i +
		caselles.add(cas);
	}
	
	public Casilla get_casella(int i){
		return caselles.get(i);
	}
	
	public void set_casella(Casilla cas, int i){
		caselles.set(i,cas);
	}
	
	public void set_res(int res){
		this.res = res;
	}
	
	public Boolean check(int n){
		Boolean b1;
		switch(op){
			case '+':
				int suma = 0, contador = 0;
				for(int i=0; i < caselles.size(); ++i){
					if(caselles.get(i).getValor() != -1){
						suma += caselles.get(i).getValor();
					}else{
						++contador;
					}
				}
				if(contador != 0){
					if(suma >= res){
						b1 = false;
					}else{
						if(res-suma < contador || res-suma > n*contador){
							b1 = false;
						}else{
							b1 = true;
						}
					}
				}else{
					b1 = suma == res;
				}
				break;
			case '-':
				int x = caselles.get(0).getValor() , y = caselles.get(1).getValor();
				if( x != -1 && y != -1){
					b1 = res == (x-y) || res == (y-x);
				}else{
					if(x == -1 && y == -1){
						b1 = true;
					}else{
						b1 = false;
						if(x == -1){
							int maxi = Math.max(y-1,n-y);
							if(maxi >= res){
								b1 = true;
							}else{
								b1 = false;
							}
						/*	for(int i=1; i <= n && i != y && b1 != true; ++i){
								b1 = (res == (i-y)) || (res == (y-i));
							}*/
						}else{
							int maxi = Math.max(x-1, n-x);
							if(maxi >= res){
								b1 = true;
							}else{
								b1 = false;
							}
							/*for(int i=1; i <= n && i != x && b1 != true; ++i){
								b1 = (res == (i-x)) || (res == (x-i));
							}*/
						}
					}
				}
				break;
			case '*':
				int mult = 1, contador2 = 0;
				for(int i=0; i < caselles.size(); ++i){
					if(caselles.get(i).getValor() != -1){
						mult *= caselles.get(i).getValor();
					}else{
						++contador2;
					}
				}
				if(mult == res){
					b1 = true;
				}else{
					if(mult > res){
						b1 = false;
					}else{
						if(res % mult != 0){
							b1 = false;
						}else{
							b1 = recursiva_mult(res/mult, contador2,n);
						}
					}
				}
				break;
			case '/':
				int x1 = caselles.get(0).getValor() , y1 = caselles.get(1).getValor();
				if(x1 == -1 && y1 == -1){
					b1 = true;
				}else{
					if(x1 != -1 && y1 != -1){
						b1 = (x1/y1 == res && x1%y1 == 0) || (y1/x1 == res && y1%x1 == 0);
					}else{
						b1 = false;
						if(x1 == -1){
							for(int j=1; j <= n && j != y1 && b1 != true; ++j){
								b1 = (y1/j == res && y1%j == 0) || (j/y1 == res && j/y1 == 0);
							}
						}else{
							for(int j=1; j <= n && j != x1 && b1 != true; ++j){
								b1 = (x1/j == res && x1%j == 0) || (j/x1 == res && j/x1 == 0);
							}
						}
					}
				}
				
				break;
			default:
				b1 = true;
				break;
		}
		return b1;

	}
	
	public Boolean recursiva_mult(int mult, int cont, int n){
		if(cont == 1)	
		for(int i=n; i > 0; ++i){
			if(mult%i == 0){
				
			}
		}
		return true;
	}
	/*54
	public void calcular_resultat(){
		switch(op){
			case '+':
				res = 0;
				for(int i=0; i < caselles.size(); ++i){
					res += caselles.get(i).getSolucion();
				}
				break;
			case '-':
				res = caselles.get(0).getSolucion() - caselles.get(1).getSolucion();
				break;
			case '*':
				res = 1;
				for(int i=0; i < caselles.size(); ++i){
					res *= caselles.get(i).getSolucion();
				}
				break;
			case '/':
				if(caselles.get(0).getSolucion() > caselles.get(1).getSolucion()){
					res = caselles.get(0).getSolucion() / caselles.get(1).getSolucion();
				}else{
					res = caselles.get(1).getSolucion() / caselles.get(0).getSolucion();
				}
				break;
			default:
				res = caselles.get(0).getSolucion();
				break;
		}
	}*/
	
	public void calcular_resultatactual(){
		switch(op){
			case '+':
				resact = 0;
				for(int i=0; i < caselles.size(); ++i){
					resact += caselles.get(i).getValor();
				}
				break;
			case '-':
				res = caselles.get(0).getValor() - caselles.get(1).getValor();
				break;
			case '*':
				resact = 1;
				for(int i=0; i < caselles.size(); ++i){
					resact *= caselles.get(i).getValor();
				}
				break;
			case '/':
				if(caselles.get(0).getValor() > caselles.get(1).getValor()){
					resact = caselles.get(0).getValor() / caselles.get(1).getValor();
				}else{
					resact = caselles.get(1).getValor() / caselles.get(0).getValor();
				}
				break;
			default:
				resact = caselles.get(0).getValor();
				break;
		}
	}
	
}
