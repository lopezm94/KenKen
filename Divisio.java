/**
*@version 1.0
*@author Joan Grau
*/

public class Divisio extends Area{
	private static final char op = '/';
	
	public Divisio(int pos){
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
		int x1 = caselles.get(0).getValor() , y1 = caselles.get(1).getValor();
		if(x1 == -1 && y1 == -1){
			b1 = true;
		}else{
			if(x1 != -1 && y1 != -1){
				b1 = (x1/y1 == res && x1%y1 == 0) || (y1/x1 == res && y1%x1 == 0);
			}else{
				b1 = false;
				if(x1 == -1){
					for(int j=1; j <= n  && b1 != true; ++j){
						b1 = (y1/j == res && y1%j == 0) || (j/y1 == res && j%y1 == 0);
					}
				}else{
					for(int j=1; j <= n  && b1 != true; ++j){
						b1 = (x1/j == res && x1%j == 0) || (j/x1 == res && j%x1 == 0);
					}
				}
			}
		}
		return b1;
	}
	
	public void calcular_resultat(){
		if(caselles.get(0).getSolucion() > caselles.get(1).getSolucion()){
			res = caselles.get(0).getSolucion() / caselles.get(1).getSolucion();
		}else{
			res = caselles.get(1).getSolucion() / caselles.get(0).getSolucion();
		}
	}
	
	public void calcular_resultatactual(){
		if(caselles.get(0).getValor() > caselles.get(1).getValor()){
			resact = caselles.get(0).getValor() / caselles.get(1).getValor();
		}else{
			resact = caselles.get(1).getValor() / caselles.get(0).getValor();
		}
	}
	
}