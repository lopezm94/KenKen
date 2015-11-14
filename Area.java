package domini;

import java.util.ArrayList;

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
		return resact;
	}

	public int get_posicio(){
		return pos;
	}

	public Boolean correcte(){
		return res == resact || res == -resact;
	}

	public void afegir_casella(Casilla cas){//es fara 1 cop si =;2 per - i /; 2 o mes * i +
		caselles.add(cas);
	}

	public Boolean check(int n){
		switch(op){
			Boolean b1;
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
				int x = caselles.get(i).getValor() , y = caselles.get(i).getValor();
				if( x != -1 and y != -1){
					b1 = res == (x-y) || res == (y-x);
				}else{
					if(x == -1 and y == -1){
						b1 = true;
					}else{
						if(x == -1){
							if(res-y
						}
					}
				}
				break;
			case '*':
				int mult = 1, contador = 0;
				for(int i=0; i < caselles.size(); ++i){
					if(caselles.get(i).getValor() != -1){
						mult *= caselles.get(i).getValor();
					}else{
						++contador;
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
							b1 = recursiva_mult(contador,res/mult);
						}
					}
				}
				break;
			case '/':
				break;
			default:
				b1 = true;
				break;
			return b1;
		}
	}

	public Boolean recursiva_mult(int pos, int x){

	}


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
	}

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
