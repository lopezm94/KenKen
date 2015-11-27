/**
*@version 1.0
*@author Joan Grau
*/

public class Resta extends Area{
	private static final char op = '-';
	
	public Resta(int pos){
		super(pos);
	}
	
	public String toString(){
		String res="";
		res = "pos: " + pos + ", "
				+ "op: " + op + ", "
				+ "res: " + this.res + ", "
				+ "lista: " + caselles + ", "
				+ "resact: " + resact + "\n ";
		return res;
	}
	
	public char get_operacio(){
		return op;
	}
	
	public Boolean check(int n){
		Boolean b1;
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
		return b1;
	}
	
	public void calcular_resultat(){
		res = Math.abs(caselles.get(0).getSolucion() - caselles.get(1).getSolucion());
	}
	
	public void calcular_resultatactual(){
		resact = Math.abs(caselles.get(0).getValor() - caselles.get(1).getValor());
	}
	
}